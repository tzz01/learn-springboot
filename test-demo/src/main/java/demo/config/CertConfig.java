package demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Enumeration;

@Configuration
@ConfigurationProperties("openbank")
public class CertConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String myCertPath;
    /** 农行公钥证书路径 */
    private String abcPubCertPath;
    /** 第三方自身证书保护密码 */
    private String myCertPwd;

    private String keyStore;

    private PrivateKey privateKey;
    /** 第三方证书公钥 */
    private PublicKey publicKey;
    /** 农行网关公钥证书 */
    private PublicKey abcPublicKey;

    public String getMyCertPath() {
        return myCertPath;
    }

    public void setMyCertPath(String myCertPath) {
        this.myCertPath = myCertPath;
    }

    public String getAbcPubCertPath() {
        return abcPubCertPath;
    }

    public void setAbcPubCertPath(String abcPubCertPath) {
        this.abcPubCertPath = abcPubCertPath;
    }

    public String getMyCertPwd() {
        return myCertPwd;
    }

    public void setMyCertPwd(String myCertPwd) {
        this.myCertPwd = myCertPwd;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getAbcPublicKey() {
        return abcPublicKey;
    }

    public void setAbcPublicKey(PublicKey abcPublicKey) {
        this.abcPublicKey = abcPublicKey;
    }

    @Bean
    public CertConfig config(@Autowired CertConfig certConfig) throws IOException {
        String myCertPath = certConfig.getMyCertPath();
        String abcPubCertPath = certConfig.getAbcPubCertPath();
        String myCertPwd = certConfig.getMyCertPwd();

        logger.info("myCertPath: " + myCertPath);
        logger.info("myCertPwd: " + myCertPwd);
        logger.info("abcPubCertPath: " + abcPubCertPath);

        initRawSignService(myCertPath, abcPubCertPath, myCertPwd);
        return certConfig;
    }

    /**
     * 初始化服务，从证书中获取私钥和公钥
     * @param myCertPath     第三方自身证书路径
     * @param myCertPwd      第三方自身证书保护密码
     * @param abcPubCertPath 农行公钥证书路径
     */
    public void initRawSignService(String myCertPath, String abcPubCertPath, String myCertPwd) throws IOException {
        ClassPathResource myCertResource = new ClassPathResource(myCertPath);
        FileInputStream fileInputStream = new FileInputStream(myCertResource.getFile());

        ClassPathResource abcPubCertResource = new ClassPathResource(abcPubCertPath);

        FileInputStream bais = new FileInputStream(abcPubCertResource.getFile());

        //获取第三方证书私钥
        String keyAlis = "";
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            FileInputStream fileInputStream = new FileInputStream(myCertPath);
            char[] nPassword = null;
            if (myCertPwd != null && !myCertPwd.isEmpty()) {
                nPassword = myCertPwd.toCharArray();
            }
            logger.debug("私钥密码=" + Arrays.toString(nPassword));
            keyStore.load(fileInputStream, nPassword);
            fileInputStream.close();
            logger.debug("keystore type=" + keyStore.getType());

            Enumeration<String> enumeration = keyStore.aliases();
            if (enumeration.hasMoreElements()) {
                keyAlis = (String) enumeration.nextElement();
                logger.debug("alias=[" + keyAlis + "]");
            }
            logger.debug("is key entry=" + keyStore.isKeyEntry(keyAlis));
            privateKey = (PrivateKey) keyStore.getKey(keyAlis, nPassword);

            //获取第三方证书公钥
            X509Certificate certificate = null;
            String keyAlias = "";
            enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                keyAlias = enumeration.nextElement();
                if (keyStore.isKeyEntry(keyAlias)) {
                    certificate = (X509Certificate) keyStore.getCertificate(keyAlias);
                }
            }
            publicKey = certificate.getPublicKey();

            // 农行网关公钥证书
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
//            String pfxPath = abcPubCertPath;
//            FileInputStream bais = new FileInputStream(pfxPath);
            X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(bais);
            abcPublicKey = cert.getPublicKey();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
