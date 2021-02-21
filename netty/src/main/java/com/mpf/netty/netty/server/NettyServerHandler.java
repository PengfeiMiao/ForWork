package com.mpf.netty.netty.server;

import com.mpf.netty.messageobj.Message;
import com.mpf.netty.netty.protocol.message.HeartbeatResponsePacket;
import com.mpf.netty.netty.protocol.protobuf.MessageBase;
import com.mpf.netty.staticobject.CommonStatic;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

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

