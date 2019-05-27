package utils.windows.cmd;

/**
 * @author zhyu
 * @date 2019-05-27 16:39:55
 * cmd 的执行结果
 */
public class Result {
    private String msg;
    private boolean isOk;
    private int code;

    public Result() {
    }

    public Result(String msg, boolean isOk, int code) {
        this.msg = msg;
        this.isOk = isOk;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", isOk=" + isOk +
                ", code=" + code +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
