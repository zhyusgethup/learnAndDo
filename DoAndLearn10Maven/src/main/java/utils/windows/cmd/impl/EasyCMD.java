package utils.windows.cmd.impl;

import utils.windows.cmd.CMD;
import utils.windows.cmd.CmdAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyu
 * @date 2019-05-27 16:47:50
 */
public class EasyCMD extends AbsCmd{
    protected EasyCMD(String name, String cmdstr, CmdAction action, List<CMD> nextCmds) {
        super(name, cmdstr, action, nextCmds);
    }
    public EasyCMD(String cmdstr) {
        super(null, cmdstr, new DefaultCmdAction(), new ArrayList<>());
    }
}
