package jp.co.project.planets.earthly.webapp.test.util;

import static jp.co.project.planets.earthly.webapp.test.constant.OAuthClientConstant.*;

import java.util.Arrays;
import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.OauthClient;

public final class OAuthClientGenerator {

    public static OauthClient generate(final OAuthClientBuilder builder) {
        return new OauthClient(builder.getId(), builder.getName(), builder.getClientId(), builder.getSecret(), null,
                "NULL", null, "NULL", false);
    }

    public static List<OauthClient> generate(final OAuthClientBuilder... builders) {
        return Arrays.stream(builders).map(OAuthClientGenerator::generate).toList();
    }
}
