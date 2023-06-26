package jp.co.project.planets.earthly.common.logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.yaml.snakeyaml.Yaml;

import jp.co.project.planets.earthly.aws.client.MailClient;
import jp.co.project.planets.earthly.common.model.dto.MailContentDto;
import jp.co.project.planets.earthly.schema.db.entity.PasswordToken;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;

/**
 * mail logic
 */
@Component
public class MailLogic {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final MailClient mailClient;

    public MailLogic(final UserRepository userRepository, final CompanyRepository companyRepository,
            final MailClient mailClient) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.mailClient = mailClient;
    }

    @Async
    public void postUserCreationNotification(final String userId) {
        final var createdUser = userRepository.findByPrimaryKey(userId)
                .orElseThrow(() -> new RuntimeException("user not found."));
        final var company = companyRepository.findByPrimaryKey(createdUser.getCompanyId())
                .orElseThrow(() -> new RuntimeException("company not found."));
        final var locale = Locale.forLanguageTag(createdUser.getLanguage());
        final var mailTemplate = getMailTemplate(locale, "user", "created");
        final var body = new ST(mailTemplate.body()).add("name", createdUser.getName())
                .add("loginId", createdUser.getLoginId()).add("company", company.getName()).render();
        mailClient.post(createdUser.getMail(), mailTemplate.subject(), body);
    }

    @Async
    public void postPasswordRestNotification(final String baseUrl, final PasswordToken passwordToken) {

        final var user = userRepository.findByPrimaryKey(passwordToken.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found."));
        final var locale = Locale.forLanguageTag(user.getLanguage());
        final var mailTemplate = getMailTemplate(locale, "forgot", "password");
        final var body = new ST(mailTemplate.body()).add("name", user.getName()).add("baseUrl", baseUrl)
                .add("token", passwordToken.getToken()).render();
        mailClient.post(user.getMail(), mailTemplate.subject(), body);
    }

    /**
     * メールテンプレートを取得
     * 
     * @param locale
     *            ロケール
     * @param kind
     *            メール種別
     * @param type
     *            メールタイプ
     * @return メールテンプレート
     */
    private MailContentDto getMailTemplate(final Locale locale, final String kind, final String type) {
        final var mailConfig = loadMailConfig(locale);
        final var map = (Map<String, Object>) mailConfig.get(kind);
        final var content = (Map<String, String>) map.get(type);
        final var bodyPath = (String) content.get("body");
        try (final var inputStream = new ClassPathResource(bodyPath).getInputStream()) {
            final var body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return new MailContentDto(content.get("subject"), body);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * メール設定読み込む
     *
     * @param locale
     *            ロケール
     * @return 設定マップ
     */
    private Map<String, Object> loadMailConfig(final Locale locale) {
        final var confPath = "mail/" + locale.getLanguage() + "/.conf";
        try (final var inputStream = new ClassPathResource(confPath).getInputStream()) {
            return new Yaml().loadAs(inputStream, Map.class);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
