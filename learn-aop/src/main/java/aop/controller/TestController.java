package aop.controller;

import aop.aspect.FlushLogin;
import aop.aspect.MsgSm2Process;
import aop.aspect.MsgSm4Process;
import aop.entity.User;
import aop.entity.UserAndKey;
import aop.service.RedisService;
import aop.util.RespUtils;
import aop.vo.RespToF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@FlushLogin
public class TestController {
    @Autowired
    RedisService redisService;

    /**
     * 这个方法主要是测试返回报文加密
     * @return
     */
    @GetMapping("/getuser")
    public RespToF addUser() {
        String username = "username";
        String password = "password";
        User user = new User(username, password);
        String sm4Key = UUID.randomUUID().toString().replaceAll("-", "");
        UserAndKey userAndKey = new UserAndKey(user, sm4Key);
        redisService.saveValue(username, userAndKey, 15);
        Map<String, String> map = new HashMap<>();
        map.put("name", username);
        map.put("pwd", password);
        map.put("key", sm4Key);
        return RespUtils.setResponse(RespUtils.SUCCESS, map);
    }

    /**
     * 这个方法主要是测试接收报文解密，返回报文加密
     * @return
     */
    @MsgSm4Process
    @GetMapping("/testsm4")
    public RespToF testSm4(String password, @RequestParam String username) {
        UserAndKey userAndKey = (UserAndKey) redisService.getValue(username);
        return RespUtils.setResponse(RespUtils.SUCCESS, userAndKey);
    }

    @MsgSm2Process
    @GetMapping("/testsm2")
    public RespToF testSm2(@RequestParam String username) {
        UserAndKey userAndKey = (UserAndKey) redisService.getValue(username);
        return RespUtils.setResponse(RespUtils.SUCCESS);
    }


    @GetMapping("/test3")
    public RespToF testFlushLogin(@RequestParam String username) {
        UserAndKey userAndKey = (UserAndKey) redisService.getValue(username);
        System.out.println("testFlushLogin");
        return RespUtils.setResponse(RespUtils.SUCCESS);
    }

}
