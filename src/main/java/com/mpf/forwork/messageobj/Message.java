package com.mpf.forwork.messageobj;

import lombok.Data;

import java.util.Date;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/10 9:57
 */
@Data
public class Message {

    private long id;

    private String msg;

    private Date sendTime;

    public Message(){

    }

    public Message(String msg){
        this.msg = msg;
    }
}
