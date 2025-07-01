package jp.co.project.planets.earthly.auth.utils;

import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECPrivateKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.core.io.ClassPathResource;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static PrivateKey loadPrivateKey(final String privateKeyPath) throws Exception {

        try (final var pem = new PEMParser(new FileReader(new ClassPathResource(privateKeyPath).getFile()))) {
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

        try (final var pem = new PEMParser(new FileReader(new ClassPathResource(publicKeyPath).getFile()))) {
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
}
