package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CertificateTests {
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
    private final char FILE_SEPERATOR_CHAR = File.separatorChar;
    private final char[] PASSPHRASE = "changeit".toCharArray();

    @Test
    public void test() throws Exception {
        String host = "www.naver.com";
        int port = 443;

        try {
            File dir = new File(System.getProperty("java.home") + FILE_SEPERATOR_CHAR + "lib" + FILE_SEPERATOR_CHAR + "security");
            File file = new File(dir, "cacerts");
            InputStream in = new FileInputStream(file);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(in, PASSPHRASE);
            in.close();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            X509TrustManager defaultTrustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];
            SavingTrustManager trustManager = new SavingTrustManager(defaultTrustManager);
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            sslSocket.setSoTimeout(10000);
            sslSocket.startHandshake();
            sslSocket.close();

            X509Certificate[] chain = trustManager.chain;
            if (chain == null) {
                log.error("Could not obtain server certificate chain");
                throw new Exception("Could not obtain server certificate chain");
            }

            X509Certificate certificate = chain[0];

            ByteArrayResource resource = new ByteArrayResource(certificate.getEncoded());
            System.out.println(new String(certificate.getEncoded()));
            System.out.println(certificate.getEncoded().length);
            System.out.println(resource.contentLength());
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + host + ".cer" + "\"")
//                    .body(resource);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte data : bytes) {
            builder.append(String.format("%02X ", data));
        }
        return builder.toString();
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int b : bytes) {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4]);
            sb.append(HEXDIGITS[b & 15]);
            sb.append(' ');
        }
        return sb.toString();
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        public X509Certificate[] getAcceptedIssuers() {
            // This change has been done due to the following resolution advised for Java 1.7+
            // http://infposs.blogspot.kr/2013/06/installcert-and-java-7.html
            return new X509Certificate[0];
            //throw new UnsupportedOperationException();
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }
}
