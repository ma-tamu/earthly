package jp.co.project.planets.earthly.auth.security.oauth2;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import jp.co.project.planets.earthly.auth.utils.KeyGeneratorUtils;


import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * jwk set
 */
public final class Jwks {

    /**
     * private new instances jwks
     */
    private Jwks() {
    }

    /**
     * generate rsa key
     *
     * @return ras key
     */
    public static RSAKey generateRsa() {
        final var keyPair = KeyGeneratorUtils.generateRsaKey();
        final var publicKey = (RSAPublicKey) keyPair.getPublic();
        final var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    /**
     * generate ec
     *
     * @return ec key
     */
    public static ECKey generateEc() {
        final var keyPair = KeyGeneratorUtils.generateEcKey();
        final var publicKey = (ECPublicKey) keyPair.getPublic();
        final var privateKey = (ECPrivateKey) keyPair.getPrivate();
        final var curve = Curve.forECParameterSpec(publicKey.getParams());
        return new ECKey.Builder(curve, publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    /**
     * generate secret
     *
     * @return secret
     */
    public static OctetSequenceKey generateSecret() {
        final var secretKey = KeyGeneratorUtils.generateSecretKey();
        return new OctetSequenceKey.Builder(secretKey).keyID(UUID.randomUUID().toString()).build();
    }
}
