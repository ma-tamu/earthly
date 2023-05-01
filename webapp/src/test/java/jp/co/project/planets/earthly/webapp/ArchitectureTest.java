package jp.co.project.planets.earthly.webapp;

import static com.tngtech.archunit.core.importer.ImportOption.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.AccessTarget;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(packages = "jp.co.project.planets.earthly", importOptions = DoNotIncludeTests.class)
public class ArchitectureTest {

    /** StringUtils間違いの制御 */
    @ArchTest
    static final ArchRule DEPEND_PACKAGE_STRING_UTILS_ONLY_APACHE_COMMONS_LANG = classes().should()
            .onlyDependOnClassesThat(canDependOnlyPackage("org.apache.commons.lang3", "StringUtils"));
    static final ArchCondition<JavaClass> MAPPER_UPDATE_METHOD_CALLED_FORM_SERVICE_TRANSACTIONAL_METHOD_CONDITION = new ArchCondition<>(
            "Mapperクラスの更新系メソッドが@Transactionalの付いたサービスメソッドから呼び出されていること。") {
        @Override
        public void check(final JavaClass item, final ConditionEvents events) {
            if (!item.getName().contains("Mapper")) {
                return;
            }
            for (final var call : item.getMethodCallsToSelf()) {
                // 更新のメソッド名でない場合は対象外とする
                if (!isMybatisUpdatePrefixMethod(call.getTarget())) {
                    continue;
                }
                final var originMethodList = extractOrigin(call.getOrigin());
                for (final var originMethod : originMethodList) {
                    if (!originMethod.isAnnotatedWith(Transactional.class)) {
                        final var message = String.format("%s メソッドを呼び出している %s メソッドに@Transactionalが付いていません。",
                                call.getTarget().getFullName(),
                                originMethod.getFullName());
                        events.add(SimpleConditionEvent.violated(call, message));
                    }
                }
            }
        }
    };
    /** Mapperの更新系メソッドを呼び出すServiceに@Transactionalが付いていることを確認するルール */
    @ArchTest
    static final ArchRule MAPPER_UPDATE_METHOD_CALLED_FORM_SERVICE_TRANSACTIONAL_METHOD = classes()
            .should(MAPPER_UPDATE_METHOD_CALLED_FORM_SERVICE_TRANSACTIONAL_METHOD_CONDITION);
    /** フィールドインジェクションを制御 */
    @ArchTest
    private static final ArchRule NO_FIELD_INJECTION = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    static DescribedPredicate<? super JavaClass> canDependOnlyPackage(final String packageName,
            final String className) {
        return new DescribedPredicate<>("StringUtilsは、org.apache.commons.lang3のみ利用可能です。") {
            @Override
            public boolean test(final JavaClass input) {
                if (!input.getSimpleName().equals(className)) {
                    return true;
                }
                return input.getPackageName().equals(packageName);
            }
        };
    }

    /**
     * メソッド名がinsert、update、deleteから始まるメソッド名か
     *
     * @param target
     *            MethodCallTarget
     * @return true: 始まる false:始まらない
     */
    static boolean isMybatisUpdatePrefixMethod(final AccessTarget.MethodCallTarget target) {
        if (target.getName().startsWith("insert")) {
            return true;
        }
        if (target.getName().startsWith("update")) {
            return true;
        }
        return target.getName().startsWith("delete");
    }

    static List<JavaCodeUnit> extractOrigin(final JavaCodeUnit javaCodeUnit) {

        final var methodList = new ArrayList<JavaCodeUnit>();
        // 呼び出し元を取得
        final var accessesToSelfSet = javaCodeUnit.getAccessesToSelf();
        for (final var javaAccess : accessesToSelfSet) {
            // 呼び出し元がServiceクラスでない場合は、呼び出し元まで遡る
            if (!javaAccess.getOriginOwner().getName().contains("Service")) {
                methodList.addAll(extractOrigin(javaAccess.getOrigin()));
                continue;
            }
            if (javaAccess.getOrigin().getModifiers().contains(JavaModifier.PRIVATE)) {
                methodList.addAll(extractOrigin(javaAccess.getOrigin()));
                continue;
            }
            methodList.add(javaAccess.getOrigin());
        }
        return methodList;
    }

    /**
     * 標準出力の制御
     *
     * @param classes
     *            java class
     */
    @ArchTest
    void noAccessToStandardStreamsAsMethod(final JavaClasses classes) {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
    }
}
