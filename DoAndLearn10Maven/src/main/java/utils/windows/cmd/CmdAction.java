package utils.windows.cmd;

/**
 * @author zhyu
 * @date 2019-05-27 16:35:06
 * 以结果为导向自定义业务逻辑的地方
 */
public interface CmdAction {
    boolean run(Result result);
}
