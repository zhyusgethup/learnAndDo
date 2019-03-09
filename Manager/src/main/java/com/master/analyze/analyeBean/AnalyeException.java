package com.master.analyze.analyeBean;

import lombok.Getter;

public class AnalyeException extends RuntimeException{
    @Getter
    private int code;
    public AnalyeException(String msg, int code){
        super(msg);
        this.code = code;
    }
    public AnalyeException(String msg){
        super(msg);
        this.code = 2;
    }


}
