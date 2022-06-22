package demo;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static String getSignPlainText(MultiValueMap<String, String> request) {
        MultiValueMap<String, String > map = new LinkedMultiValueMap<>();
        StringBuilder signPlainText = new StringBuilder();
        for (Map.Entry<String, List<String>> pi: request.entrySet()){
            System.out.println(pi);
            if (pi != null && pi.getValue() != null && !pi.getValue().equals("")
                    && !pi.getKey().trim().toLowerCase().equals("sign") && !pi.getKey().trim().toLowerCase().equals("code")
                    && !pi.getKey().trim().toLowerCase().equals("msg")) {
                map.add(pi.getKey(), pi.getValue().toString());
            }
        }
        TreeSet<String> treeSet = new TreeSet<>(map.keySet());
        for (String key : treeSet) {
            signPlainText.append(map.get(key)).append("@");
        }
        if (signPlainText.length() - 1 >= 0) {
            signPlainText.deleteCharAt(signPlainText.length() - 1);
        }
        return signPlainText.toString();
    }

    public static void dataBind(MultiValueMap<String, String> map) {
//        JSONObject request = new JSONObject(); /* 时间戳*/
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = format.format(date);
        /* GUID随机数*/
        String uuidstr = UUID.randomUUID().toString().replace("-", "");
        map.add("appid", "openBankConfig.getAppid()");
        map.add("biz_data", "openBankConfig.getBizData()");
        map.add("sign_type", "openBankConfig.getSignType()");
        map.add("timestamp", timestamp);
        map.add("encrypt_data", "openBankConfig.getEncryptData()");
        map.add("encrypt_type", "openBankConfig.getEncryptType()");
        map.add("nonce", uuidstr);
    }

    public static void main(String[] args) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        dataBind(request);
        System.out.println(request);


        String plainText = getSignPlainText(request);
        System.out.println(plainText);
    }
}
