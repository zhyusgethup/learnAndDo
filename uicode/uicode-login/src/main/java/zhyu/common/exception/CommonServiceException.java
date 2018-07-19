package zhyu.common.exception;

/***
 * 该异常的信息会被发送到前端,请注意
 */
public class CommonServiceException extends Exception{
    private String message;

    public CommonServiceException() {
        this.message = "网络错误, 请稍后再试";
    }

    public CommonServiceException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
