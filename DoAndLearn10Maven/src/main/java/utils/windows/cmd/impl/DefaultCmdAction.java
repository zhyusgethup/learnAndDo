package utils.windows.cmd.impl;

import utils.windows.cmd.CMD;
import utils.windows.cmd.CmdAction;
import utils.windows.cmd.Result;

import java.util.List;

/**
 * @author zhyu
 * @date 2019-05-27 16:49:51
 */
public class DefaultCmdAction implements CmdAction {
    @Override
    public boolean run(Result result) {
        System.out.println(result.getMsg());
        return result.isOk();
    }
}
