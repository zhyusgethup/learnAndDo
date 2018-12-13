package dataStruct.combine;

import java.util.List;

/***
 * 出自公司的chat 系统,
 * １．能快速找到玩家Ａ－玩家Ｂ的聊天记录
 * ２．聊天记录方便上限管理和遍历清除
 * ３．要求能快速拉取最近的聊天（玩家Ａ对所有玩家），并附带一条Ａ－？的最近聊天记录
 * ４．要求能快速找到玩家的离线消息．
 */
public class Chat {

}
class Player {
    int id;
    List<Integer> nearChat;
}
class Cache {
    Table<>
}
