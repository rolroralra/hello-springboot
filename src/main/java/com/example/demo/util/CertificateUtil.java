package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;
import sun.security.provider.X509Factory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
@Component("CertificateUtil")
public class CertificateUtil {
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
    private final char FILE_SEPERATOR_CHAR = File.separatorChar;
    private final char[] PASSPHRASE = "changeit".toCharArray();

    public String getCertificate(String host, int port) throws Exception {
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

            BASE64Encoder encoder = new BASE64Encoder();
            String certificateString = new StringBuilder()
                    .append(X509Factory.BEGIN_CERT)
                    .append("\n")
                    .append(encoder.encode(certificate.getEncoded()))
                    .append("\n")
                    .append(X509Factory.END_CERT)
                    .append("\n")
                    .toString();

            return certificateString;

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
