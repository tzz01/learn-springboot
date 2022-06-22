package aop.aspect;

import aop.vo.RespToF;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * 对除获取临时用户外方法的输入参数进行解密
 */
@Aspect
@Component
public class MsgSm4ProcessAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("@annotation(msgSm4Process)")
    @SuppressWarnings("unchecked")
    public RespToF messageDecrypt(ProceedingJoinPoint joinPoint, MsgSm4Process msgSm4Process) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object temp_arg = null;
        for (int i = 0; i < args.length; i++) {
            temp_arg = args[i];
            System.out.println(temp_arg);
        }

        RespToF respToF = (RespToF) joinPoint.proceed(args);
        logger.debug("respToF: {}", respToF);

        String cipherText = sm4Encrypt(new ObjectMapper().writeValueAsString(respToF.getData()));
        respToF.setData(cipherText);
        logger.debug("respToF: {}", respToF);

        return respToF;
    }

    private String sm4Encrypt(String plainText) {
        byte[] a = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(a);
    }
}
