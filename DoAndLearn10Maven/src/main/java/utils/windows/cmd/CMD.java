package utils.windows.cmd;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhyu
 * @date 2019-05-27 14:31:29
 * 定义了cmd 功能,
 * 执行命令,添加下一条命令列,处理器
 */
public interface CMD {

    Result run(long timeOut);
    Result run();

    boolean nextCmd(CMD cmd);
    boolean nextCmdList(List<CMD> list);

    boolean nextAction(CmdAction cmdAction);

    public List<CMD> getNextCmds();

    boolean addNextCMD(CMD cmd);

    void setTimeout(long time, TimeUnit unit);

}
