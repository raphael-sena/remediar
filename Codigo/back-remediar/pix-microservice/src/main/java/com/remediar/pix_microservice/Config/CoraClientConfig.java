package com.remediar.pix_microservice.Config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

@Configuration
public class CoraClientConfig {

    static {
        // Registra o provedor Bouncy Castle se ainda não estiver registrado
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Value("${cora.mtls.cert-path:}")
    private Resource certPath;

    @Value("${cora.mtls.key-path:}")
    private Resource keyPath;

    @Bean
    public RestTemplate mtlsRestTemplate() {
        try {
            // Verifica se os certificados estão disponíveis
            if (certPath == null || keyPath == null || !certPath.exists() || !keyPath.exists()) {
                System.out.println("Certificados mTLS não encontrados. Usando RestTemplate padrão.");
                return new RestTemplate();
            }
            
            Certificate certificate = loadCertificate();
            PrivateKey privateKey = loadPrivateKeyWithBC();

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry("client-cert", privateKey, "".toCharArray(), new Certificate[]{certificate});

            SSLContext sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(keyStore, "".toCharArray())
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(
                            PoolingHttpClientConnectionManagerBuilder.create()
                                    .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
                                    .build()
                    ).build();

            return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        } catch (Exception e) {
            System.out.println("Erro ao configurar mTLS RestTemplate: " + e.getMessage() + ". Usando RestTemplate padrão.");
            return new RestTemplate();
        }
    }

    private Certificate loadCertificate() throws Exception {
        try (var inputStream = certPath.getInputStream()) {
            return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
        }
    }

    private PrivateKey loadPrivateKeyWithBC() throws Exception {
        try (PEMParser pemParser = new PEMParser(new InputStreamReader(keyPath.getInputStream()))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair) {
                return converter.getKeyPair((PEMKeyPair) object).getPrivate();
            }
            return converter.getPrivateKey((org.bouncycastle.asn1.pkcs.PrivateKeyInfo) object);
        }
    }
}
