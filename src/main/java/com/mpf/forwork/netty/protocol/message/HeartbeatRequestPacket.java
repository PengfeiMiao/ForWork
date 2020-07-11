package com.mpf.forwork.netty.protocol.message;

import lombok.Data;

import com.mpf.forwork.netty.protocol.message.command.Command;


/**
 * @author pjmike
 * @create 2018-10-25 16:12
 */
@Data
public class HeartbeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
