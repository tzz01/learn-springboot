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
import java.util.Base64;

/**
 * 对获取临时用户的返回结果进行加密
 */
@Aspect
@Component
public class MsgSm2ProcessAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("@annotation(msgSm2Process)")
    @SuppressWarnings("unchecked")
    public RespToF messageDecrypt(ProceedingJoinPoint joinPoint, MsgSm2Process msgSm2Process) throws Throwable {
        RespToF respToF = (RespToF) joinPoint.proceed();
        logger.debug("respToF: {}", respToF);

        String cipherText = sm2Encrypt(new ObjectMapper().writeValueAsString(respToF.getData()));
        respToF.setData(cipherText);
        logger.debug("respToF: {}", respToF);

        return respToF;
    }

    private String sm2Encrypt(String plainText) {
        byte[] a = plainText.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(a);
    }
}
