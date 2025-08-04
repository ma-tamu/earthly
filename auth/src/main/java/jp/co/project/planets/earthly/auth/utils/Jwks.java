package jp.co.project.planets.earthly.auth.utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.core.io.ClassPathResource;

import com.nimbusds.jose.jwk.RSAKey;

public final class Jwks {

    private Jwks() {
    }

    public static PrivateKey loadPrivateKey(final String privateKeyPath) throws Exception {

        try (final var pem = new PEMParser(
                new FileReader(new ClassPathResource(privateKeyPath).getFile(), StandardCharsets.UTF_8))) {
            final var privateKeyInfo = (PrivateKeyInfo) pem.readObject();
            final var keyParameters = (ECPrivateKeyParameters) PrivateKeyFactory.createKey(privateKeyInfo);
            final var parameters = keyParameters.getParameters();
            final var spec = new ECNamedCurveSpec(StringUtils.EMPTY, parameters.getCurve(), parameters.getG(),
                    parameters.getN(), parameters.getH());
            final var keySpec = new ECPrivateKeySpec(keyParameters.getD(), spec);
            final var keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            return keyFactory.generatePrivate(keySpec);
        }
    }

    public static PublicKey loadPublicKey(final String publicKeyPath) throws Exception {

        try (final var pem = new PEMParser(
                new FileReader(new ClassPathResource(publicKeyPath).getFile(), StandardCharsets.UTF_8))) {
            final var keyInfo = (SubjectPublicKeyInfo) pem.readObject();
            final var key = (ECPublicKeyParameters) PublicKeyFactory.createKey(keyInfo);
            final var parameters = key.getParameters();
            final var parameterSpec = new ECParameterSpec(parameters.getCurve(), parameters.getG(), parameters.getN(),
                    parameters.getH());
            final var keySpec = new ECPublicKeySpec(key.getQ(), parameterSpec);
            final var keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            return keyFactory.generatePublic(keySpec);
        }
    }

    public static RSAKey generateRsa() {
        final var publicKey = loadRsaPublicKye();
        final var privateKey = loadRsaPrivateKey();
        final var keyPair = new KeyPair(publicKey, privateKey);
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }

    static PublicKey loadRsaPublicKye() {
        try (final var publicPem = new PEMParser(
                new FileReader(new ClassPathResource("pem/rsa/public.pem").getFile(), StandardCharsets.UTF_8))) {
            final var keyInfo = (SubjectPublicKeyInfo) publicPem.readObject();
            final var key = (RSAKeyParameters) PublicKeyFactory.createKey(keyInfo);
            final var publicKeySpec = new RSAPublicKeySpec(key.getModulus(), key.getExponent());
            final var keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (final IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    static PrivateKey loadRsaPrivateKey() {
        try (final var privatePem = new PEMParser(
                new FileReader(new ClassPathResource("pem/rsa/private.pem").getFile(), StandardCharsets.UTF_8))) {
            final var privateKeyInfo = (PrivateKeyInfo) privatePem.readObject();
            final var keyParameter = (RSAPrivateCrtKeyParameters) PrivateKeyFactory.createKey(privateKeyInfo);
            final var rsaPrivateKeySpec = new RSAPrivateKeySpec(keyParameter.getModulus(), keyParameter.getExponent());
            final var keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
            return keyFactory.generatePrivate(rsaPrivateKeySpec);
        } catch (final IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
