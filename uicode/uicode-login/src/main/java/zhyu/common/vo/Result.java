package zhyu.common.vo;

import java.util.Map;

public class Result {
    public Result(){}
    private String code;
    private String message;
    private Map<String, Object> result;

    public Result(Map<String, Object> result) {
        this.result = result;
    }

    public Result(String code, String message, Map<String, Object> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static Result getFailResult() {
        return new Result("01","fail");
    }

    public static Result getFailResult(String info) {
        return new Result("01",info);
    }

    public  static Result getSuccessResult(Map<String, Object> result) {
        return new Result("00","ok",result);
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public  static Result getSuccessResult() {
        return new Result("00","ok");

    }
    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
