package jp.co.project.planets.earthly.aws.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SESプロパティ
 */
@Component
@ConfigurationProperties("aws.ses")
public class SesPropitiates {

    /** 利用有無 */
    private Boolean enable;

    /** localstackのエンドポイント */
    private String endpoint;

    /** 送信元 */
    private String from;

    /** リージョン */
    private String region;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
