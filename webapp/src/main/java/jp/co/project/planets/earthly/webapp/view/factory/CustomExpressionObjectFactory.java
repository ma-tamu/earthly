package jp.co.project.planets.earthly.webapp.view.factory;

import jp.co.project.planets.earthly.webapp.view.Pagination;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Set;

public class CustomExpressionObjectFactory implements IExpressionObjectFactory {


    private final Pagination pagination;

    private static final Set<String> EXPRESSION_SET = Set.of(Pagination.DIALECT_NAME);

    public CustomExpressionObjectFactory() {
        this.pagination = new Pagination();
    }

    @Override

    public Set<String> getAllExpressionObjectNames() {
        return EXPRESSION_SET;
    }

    @Override
    public Object buildObject(final IExpressionContext context, final String expressionObjectName) {

        return switch (expressionObjectName) {
            case Pagination.DIALECT_NAME -> pagination;
            default -> null;
        };
    }

    @Override
    public boolean isCacheable(final String expressionObjectName) {
        return false;
    }
}
