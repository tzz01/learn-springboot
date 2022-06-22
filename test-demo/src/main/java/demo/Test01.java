package demo;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test01 {
    public static void main(String[] args) throws InterruptedException {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        Thread.sleep(1000);
        LocalDateTime localDateTime2 = LocalDateTime.now();

        Duration duration = Duration.between(localDateTime1, localDateTime2);
        long mills = duration.toMillis();

        System.out.println(localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        if (mills > 999) {
            System.out.println("big");
        }


        String str = "fsdjifjsaidfasdpng";
        String suffix = str.substring(str.indexOf(".") + 1);
        System.out.println(suffix);

    }
}
