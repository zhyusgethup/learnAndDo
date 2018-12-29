package com.cellsgame.command.enter;

import com.cellsgame.HuyuMessageHandler;
import com.cellsgame.command.AccessInterface;
import com.cellsgame.command.Cmd;
import com.cellsgame.command.paramHandler.ParamParseHandlerManager;
import com.cellsgame.gateway.core.Connection;

import java.util.*;

public class CmdEnter implements Enter{
    private HuyuMessageHandler msg;
    private ParamParseHandlerManager paramParseHandlerManager;
    public CmdEnter(HuyuMessageHandler msg) {
        this.msg = msg;
        paramParseHandlerManager = new ParamParseHandlerManager();
    }
    @Override
    public void start(Connection connection, int cmd, Map map) {
        Scanner sc = new Scanner(System.in);
        System.out.println(cmd + " " + map);
        if (cmd == 10001 || (cmd == 20101 && !map.get("co").equals("0"))) {
            System.out.println("cmd 扫行模式  请输入name 和 account 空格分开");
            String name = sc.next();
            String account = sc.next();
            Map data = new HashMap();
            data.put("serverId", 20180802);
            data.put("accountId", account);
            data.put("name", name);
            msg.sendMessage(connection, 20101, data);
        } else {
            AccessInterface accessInterface = null;
            while (null == accessInterface) {
                System.out.println("请输入一行命令 用空格隔开第一个单元为cmd， 后面为参数 如 110001 1");
                String line = sc.nextLine();
                String[] words = line.split(" ");
                try {
                    accessInterface = parseStringToAccessInterface(words);
                    if (null == accessInterface) {
                        continue;
                    }
                }catch(Exception e){
                    System.out.println(e);
                    continue;
                }
                msg.sendMessage(connection, accessInterface.getCmd(), accessInterface.getData());
            }
        }
    }
    public  AccessInterface parseStringToAccessInterface(String[] words) {
        String cmdd = words[0];
        Integer cmds = Integer.valueOf(cmdd);
        Cmd cmd = Cmd.getCmdByNumber(cmds);
        Map <String, Object> data  = new HashMap<>();
        List<String> paL = cmd.getPaL();
        if(paL.size() > words.length - 1)
            return null;
        for(int i = 0; i < paL.size(); i++) {
            String name = paL.get(i);
            Object typeObj = cmd.getParams().get(name);
            if(typeObj instanceof  Class) {
                Class type = (Class)typeObj;
                data.put(name,paramParseHandlerManager.parse(words[i + 1],type,name));
            }else {
                data.put(paL.get(i), typeObj);
            }
        }
        AccessInterface accessInterface = new AccessInterface(cmd.getCmd(),data);
        return accessInterface;
    }


}
