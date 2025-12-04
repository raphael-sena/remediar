package com.remediar.pix_microservice.utils;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;

public class PemUtils {

    static {
        // Adiciona o BouncyCastle caso não esteja presente
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * Converte uma chave privada em formato PEM (PKCS#1 ou PKCS#8) para um objeto PrivateKey.
     *
     * @param pemKey Conteúdo da chave privada no formato PEM como String
     * @return Objeto PrivateKey
     * @throws IOException Se houver erro na leitura do conteúdo PEM
     */
    public static PrivateKey parsePrivateKey(String pemKey) throws IOException {
        try (StringReader sr = new StringReader(pemKey);
             PEMParser pemParser = new PEMParser(sr)) {

            Object obj = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (obj instanceof PEMKeyPair pemKeyPair) {
                KeyPair kp = converter.getKeyPair(pemKeyPair);
                return kp.getPrivate();
            }

            if (obj instanceof PrivateKeyInfo privateKeyInfo) {
                return converter.getPrivateKey(privateKeyInfo);
            }

            throw new IllegalArgumentException("Formato PEM não suportado: " + obj.getClass().getName());
        } catch (Exception e) {
            throw new IOException("Erro ao converter chave PEM: " + e.getMessage(), e);
        }
    }
}
