package utils.windows.cmd;


/**
 * @author zhyu
 * @date 2019-05-27 15:06:21
 */
public enum CmdExceptionEnum {

    CMD_STR_EMPTY(1, "没有可执行命令"),
    ;
    private int code;
    private String msg;

    public void throwWrong(){
        throw new CmdException(this);
    }

    CmdExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public class CmdException extends RuntimeException {
        private int code;
        public  CmdException(CmdExceptionEnum exceptionEnum) {
            super(exceptionEnum.getMsg());
            this.code = exceptionEnum.getCode();
        }
    }
}


