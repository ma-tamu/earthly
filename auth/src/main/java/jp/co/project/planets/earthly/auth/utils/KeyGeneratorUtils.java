package jp.co.project.planets.earthly.auth.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

/**
 * key generator utils
 */
public final class KeyGeneratorUtils {

    /**
     * private new instances key generator utils
     */
    private KeyGeneratorUtils() {
    }

    /**
     * シークレットキーを生成
     *
     * @return シークレットキー
     */
    public static SecretKey generateSecretKey() {
        try {
            return KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * RSA キー生成
     *
     * @return RSA キー
     */
    public static KeyPair generateRsaKey() {
        try {
            final var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Ecキーの生成
     *
     * @return KeyPair
     */
    public static KeyPair generateEcKey() {
        final var ellipticCurve = new EllipticCurve(new ECFieldFp(
                new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
                new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
                new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
        final var ecPoint = new ECPoint(
                new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
                new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"));
        final var ecParameterSpec = new ECParameterSpec(ellipticCurve, ecPoint,
                new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"), 1);

        try {
            final var keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(ecParameterSpec);
            return keyPairGenerator.generateKeyPair();
        } catch (final NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
