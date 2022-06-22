package controller;

import demo.DemoApplication;
import demo.config.AbcConfig;
import demo.config.CertConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.security.PrivateKey;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    CertConfig certConfig;

    @Autowired
    AbcConfig abcConfig;

    @org.junit.Test
    public void test() throws IOException {
        PrivateKey privateKey = certConfig.getPrivateKey();
        System.out.println(privateKey.toString());
        System.out.println(certConfig.getPublicKey());
        System.out.println(certConfig.getAbcPublicKey());

        System.out.println(certConfig);
    }

    @org.junit.Test
    public void test01() {
        if (abcConfig.isFlag()) {
            System.out.println(abcConfig.isFlag());
        }
    }

}
