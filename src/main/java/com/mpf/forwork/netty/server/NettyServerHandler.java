package com.mpf.forwork.netty.server;

import com.mpf.forwork.messageobj.Message;
import com.mpf.forwork.netty.protocol.message.HeartbeatResponsePacket;
import com.mpf.forwork.netty.protocol.protobuf.MessageBase;
import com.mpf.forwork.service.kafka.KafkaProducer;
import com.mpf.forwork.staticobject.CommonStatic;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/11 9:08
 */
@Slf4j
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<MessageBase.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBase.Message msg) throws Exception {
        if (msg.getCmd().equals(MessageBase.Message.CommandType.HEARTBEAT_REQUEST)) {
            log.info("收到客户端发来的心跳消息：\n{}", msg.toString());
            //回应pong
            ctx.writeAndFlush(new HeartbeatResponsePacket());
        } else if (msg.getCmd().equals(MessageBase.Message.CommandType.NORMAL)) {
            log.info("收到客户端的业务消息：\n{}", msg.toString());
            CommonStatic.MESSAGE = msg.getContent();
        }
    }
}

