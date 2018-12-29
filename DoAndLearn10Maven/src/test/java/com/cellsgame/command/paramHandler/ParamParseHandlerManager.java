package com.cellsgame.command.paramHandler;


import java.util.ArrayList;
import java.util.List;

public class ParamParseHandlerManager {
    private List<ParamParseHandler> handlerList = new ArrayList<>();
    {
        handlerList.add(new StringHandler());
        handlerList.add(new IntegerHandler());
        handlerList.add(new ListHandler());
        handlerList.add(new MapHandler());
    }
    private ParamParseHandler defaultHandler = new DefaultHandler();

    public List<ParamParseHandler> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<ParamParseHandler> handlerList) {
        this.handlerList = handlerList;
    }

    public ParamParseHandler getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(ParamParseHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public Object parse(String param, Class type, String name) {
        for(ParamParseHandler handler: handlerList) {
            if(handler.match(type)) {
                try {
                    return handler.parse(param);
                }catch (Exception e) {
                    throw new RuntimeException("参数解析器：" + handler.getName() + "解析参数[" + name + "]异常   type = " + type + "   words = " + param  );
                }
            }
        }
        if(null != defaultHandler)
            try {
                return defaultHandler.parse(param);
            }catch (Exception e){
                throw new RuntimeException("参数解析器：" + defaultHandler.getName() + "解析参数[" + name + "]异常   type = " + type + "   words = " + param  );
            }
        throw new RuntimeException("参数[" + name + "]没有找到对应的参数解析器 -- type :" + type);
    }
}
