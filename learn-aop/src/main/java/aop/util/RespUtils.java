package aop.util;

import aop.vo.RespToF;

public class RespUtils {
    public static final String SUCCESS = "0000";
    public static final String FAILURE = "9999";

    public static RespToF setResponse(String statusCode) {
        RespToF respToF = new RespToF();
        respToF.setStatusCode(statusCode);
        if (statusCode.equals("0000")) {
            respToF.setMessage("操作成功");
        } else {
            respToF.setMessage("操作失败");
        }
        return respToF;
    }

    public static <T> RespToF<T> setResponse(String statusCode, T t) {
        RespToF<T> respToF = new RespToF<>();
        respToF.setStatusCode(statusCode);

        if (statusCode.equals("0000")) {
            respToF.setMessage("操作成功");
        } else {
            respToF.setMessage("操作失败");
        }

        respToF.setData(t);
        return respToF;
    }
}
