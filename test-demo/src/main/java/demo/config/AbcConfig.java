package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("abc")
public class AbcConfig {
    private String ddd;
    private String fff;
    private String dAddF;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public String getdAddF() {
        return dAddF;
    }

    public void setdAddF(String dAddF) {
        this.dAddF = dAddF;
    }

    @Bean
    public AbcConfig dF(@Autowired AbcConfig abcConfig) {
        String d = abcConfig.getDdd();
        String f = abcConfig.getFff();
        init(d, f);
        return abcConfig;
    }

    public void init(String d, String f) {
        dAddF = d + f;
    }
}
