package jp.co.project.planets.earthly.common.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * totp logic
 */
@Component
public class TotpLogic {

    private static final String ISSUER = "Planet Systems";

    private static final int DIGITS = 6;
    private static final int PERIOD = 30;
    private static final String OTPAUTH_FORMAT = "otpauth://%s/%s?secret=%s&issuer=%s&algorithm=%s&digits=" + DIGITS
            + "&period=" + PERIOD;
    private static final int IMAGE_SIZE = 350;

    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private static final int RECOVERY_CODE_COUNT = 12;
    private static final int RECOVERY_CODE_LENGTH = 6;
    private final Base64 base64Codec = new Base64();
    private final SecureRandom random = new SecureRandom();

    public String generateSecret() {
        final int numCharacters = 32;
        final byte[] bytes = new byte[(numCharacters * 5) / 8];
        random.nextBytes(bytes);
        return new String(base64Codec.encode(bytes));
    }

    public List<String> generateRecoveryCode() {
        return IntStream.range(1, RECOVERY_CODE_COUNT).mapToObj(cnt -> {
            final var builder = new StringBuilder();
            for (int i = 0; i < RECOVERY_CODE_LENGTH; i++) {
                builder.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
            }
            return builder.toString();
        }).toList();
    }

    public String generateQrImage(final String loginId, final String secret) {
        final var encodeIssuer = urlEncode(ISSUER);
        final var otpauthUri = OTPAUTH_FORMAT.formatted(encodeIssuer, urlEncode(loginId), urlEncode(secret),
                encodeIssuer, urlEncode("SHA1"));
        final var writer = new QRCodeWriter();

        try {
            final var bitMatrix = writer.encode(otpauthUri, BarcodeFormat.QR_CODE, IMAGE_SIZE, IMAGE_SIZE);
            final var pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            final var encodedData = new String(base64Codec.encode(pngOutputStream.toByteArray()));
            return String.format("data:%s;base64,%s", "image/png", encodedData);
        } catch (final WriterException | IOException e) {
            throw new RuntimeException("QR Code generated failed.", e);
        }

    }

    private String urlEncode(final String plaintext) {
        return URLEncoder.encode(plaintext, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public boolean verifyCode(final String code, final String secret) {
        final var epochSecond = Instant.now().getEpochSecond();
        final long currentBucket = Math.floorDiv(epochSecond, PERIOD);
        return IntStream.range(-1, 1).anyMatch(i -> checkCode(code, secret, currentBucket + i));
    }

    @VisibleForTesting
    boolean checkCode(final String code, final String secret, final long counter) {
        try {
            final byte[] hash = generateHash(secret, counter);
            final var actualCode = getDigitsFromHash(hash);
            return StringUtils.equals(code, actualCode);
        } catch (final InvalidKeyException | NoSuchAlgorithmException e) {
            return false;
        }
    }

    private byte[] generateHash(final String key, final long counter)
            throws InvalidKeyException, NoSuchAlgorithmException {
        final byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        // Create a HMAC-SHA1 signing key from the shared key
        final var codec = new Base32();
        final byte[] decodedKey = codec.decode(key);
        final var signKey = new SecretKeySpec(decodedKey, "HmacSHA1");
        final var mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);

        // Create a hash of the counter value
        return mac.doFinal(data);
    }

    private String getDigitsFromHash(final byte[] hash) {
        final int offset = hash[hash.length - 1] & 0xF;

        long truncatedHash = 0;

        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, DIGITS);

        // Left pad with 0s for a n-digit code
        return String.format("%0" + DIGITS + "d", truncatedHash);
    }
}
