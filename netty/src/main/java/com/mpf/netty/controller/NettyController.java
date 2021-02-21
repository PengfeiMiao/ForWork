package com.mpf.netty.controller;

import com.mpf.netty.netty.client.NettyClient;
import com.mpf.netty.netty.protocol.protobuf.MessageBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/21 10:27
 * @description:
 * @since: 1.0.20
 */
@RestController
public class NettyController {
    @Autowired
    private NettyClient nettyClient;

    @GetMapping("/netty/send")
    public String sendNetty(@RequestParam String msg) {
        MessageBase.Message message = new MessageBase.Message()
                .toBuilder().setCmd(MessageBase.Message.CommandType.NORMAL)
                .setContent(msg)
                .setRequestId(UUID.randomUUID().toString()).build();
        nettyClient.sendMsg(message);
        return "netty send ok";
    }
}
