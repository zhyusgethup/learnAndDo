package utils.windows.cmd.impl;

import org.apache.maven.shared.utils.StringUtils;
import utils.io.ReadUtils;
import utils.windows.cmd.CMD;
import utils.windows.cmd.CmdAction;
import utils.windows.cmd.CmdExceptionEnum;
import utils.windows.cmd.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhyu
 * @date 2019-05-27 14:59:08
 */
public abstract class AbsCmd implements CMD {
    private String name;
    private String cmdstr;
    private CmdAction action;
    private List<CMD> nextCmds;

    private long time;
    private TimeUnit unit;

    protected AbsCmd(String name, String cmdstr, CmdAction action, List<CMD> nextCmds) {
        if (StringUtils.isEmpty(cmdstr))
            //抛出异常
            CmdExceptionEnum.CMD_STR_EMPTY.throwWrong();
        if (StringUtils.isEmpty(name)) {
            name = cmdstr;
        }
        this.name = name;
        this.cmdstr = cmdstr;
        this.action = action;
        this.nextCmds = nextCmds;
    }

    public boolean addNextCMD(CMD cmd) {
        if(null == nextCmds){
            nextCmds = new ArrayList<>();
        }
        return nextCmds.add(cmd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmdstr() {
        return cmdstr;
    }

    public void setCmdstr(String cmdstr) {
        this.cmdstr = cmdstr;
    }

    public CmdAction getAction() {
        return action;
    }

    public void setAction(CmdAction action) {
        this.action = action;
    }

    public List<CMD> getNextCmds() {
        return nextCmds;
    }

    public void setNextCmds(List<CMD> nextCmds) {
        this.nextCmds = nextCmds;
    }

    public void setTimeout(long time, TimeUnit unit){
        this.time = time;
        this.unit = unit;
    }

    public long getDefaulttimeOut() {
        return defaulttimeOut;
    }

    public void setDefaulttimeOut(long defaulttimeOut) {
        this.defaulttimeOut = defaulttimeOut;
    }

    private long defaulttimeOut = 2000L;

    @Override
    public Result run(long timeOut) {
        Process ipconfig = null;
        Result re = new Result();
        try {
            ipconfig = Runtime.getRuntime().exec(cmdstr);
            InputStream in = null;
            ipconfig.waitFor(1000, null == unit?TimeUnit.MILLISECONDS:unit);
            if (ipconfig.getInputStream().available() == 0) {
                in = ipconfig.getErrorStream();
                re.setOk(false);
            } else {
                in = ipconfig.getInputStream();
                re.setOk(true);
            }
            re.setMsg(ReadUtils.getWordFromInputStreamAndAnayseCharset(in).toString());
            ipconfig.destroy();
            return re;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return re;
    }

    @Override
    public Result run() {
        Result re = run(defaulttimeOut);
        action.run(re);
        if(null != nextCmds && 0 != nextCmds.size())
            nextCmds.get(0).run();
        return re;
    }

    @Override
    public boolean nextCmd(CMD cmd) {
        return false;
    }

    @Override
    public boolean nextCmdList(List<CMD> list) {
        return false;
    }

    @Override
    public boolean nextAction(CmdAction cmdAction) {
        return false;
    }
}
