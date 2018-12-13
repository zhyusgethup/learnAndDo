package dataStruct.combine;

import com.google.common.collect.Table;

import java.util.List;
import java.util.Map;

/***
 * 出自公司的chat 系统,
 * １．能快速找到玩家Ａ－玩家Ｂ的聊天记录
 * ２．聊天记录方便上限管理和遍历清除
 * ３．要求能快速拉取最近的聊天（玩家Ａ对所有玩家），并附带一条Ａ－？的最近聊天记录
 * ４．要求能快速找到玩家的离线消息．
 */
public class Chat {

    public Map<String, Object> getOutLineMsg(int pid) {
        return null;
    }

    public void appendMsgToNearList(int pid) {

    }
}
class Player {
    private int id;
    private List<Integer> nearChat;
}
class Cache {
    private Table<Integer, Integer, ChatVo> cache;//\\\\ 原版的唯一Cache


    private Map<Integer,ChatMsgVo> allMsg;// 所有消息   这么做要改变以ChatVO为核心的持久化方式,持久化直接以ChatMsgVO为核心持久化
    private Table<Integer,Integer,List<Integer>> cacheChatVoIndex;// 双向聊天记录缓存
    private Map<Integer,List<Integer>> cacheOutLineIndex; //单向离线消息缓存
}
class ChatMsgVo {
    private int sender,receiver;private long cre;private  String msg;
}
class ChatVo {
    private  List<ChatMsgVo> list;
    private int pp1,pp2;
}
class Dao<ChatVo> {}
