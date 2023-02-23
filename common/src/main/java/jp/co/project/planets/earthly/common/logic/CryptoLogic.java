package jp.co.project.planets.earthly.common.logic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

/**
 * crypto logic
 */
@Component
public class CryptoLogic {

    /**
     * encode SHA-256
     * 
     * @param plainText
     *            平文
     * @return 暗号化した文字列
     */
    public String encodeSHA256(final String plainText) {
        try {
            final var digest = MessageDigest.getInstance("SHA-256");
            final var bytes = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
