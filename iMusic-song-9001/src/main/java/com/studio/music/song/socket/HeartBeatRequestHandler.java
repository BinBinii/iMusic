package com.studio.music.song.socket;

import com.alibaba.fastjson.JSONObject;
import com.studio.music.song.command.HeartBeatRequestPacket;
import com.studio.music.song.command.HeartBeatResponsePacket;
import com.studio.music.song.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket>{

	public static HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
	
	private HeartBeatRequestHandler () {
		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf byteBuf = getBuf(ctx);
		SessionUtils.expireChannel(ctx.channel());
		ctx.channel().writeAndFlush(new TextWebSocketFrame(byteBuf));
	}
	
	public ByteBuf getBuf(ChannelHandlerContext ctx) {
		 ByteBuf byteBuf = ctx.alloc().buffer();
		 JSONObject data = new JSONObject();
		 data.put("type", new HeartBeatResponsePacket().getCommand());
		 data.put("status", 200);
		 byte bytes[] = data.toJSONString().getBytes();
		 byteBuf.writeBytes(bytes);
		 return byteBuf;
	}

}
