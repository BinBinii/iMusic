package com.studio.music.song.socket;

import com.alibaba.fastjson.JSONObject;
import com.studio.music.song.command.CreateGroupRequestPacket;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * 创建群号ChannelHandler组件
 * @author holiday
 * 2020-11-12
 */
@Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket>{

	public static CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
	
	private CreateGroupRequestHandler () {
		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
		List<String> userIdList = createGroupRequestPacket.getUserIdList();
		List<String> nameList = new ArrayList<>();
		ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
		channelGroup.add(ctx.channel());
		nameList.add(SessionUtils.getUser(ctx.channel()).getNickname());
		for (String userId : userIdList) {
			Channel channel = SessionUtils.getChannel(userId);
			TbUser user = SessionUtils.getUser(channel);
			if (channel != null) {
				channelGroup.add(channel);
				nameList.add(user.getNickname());
			}
		}
		String groupId = UUID.randomUUID().toString();
		// 绑定群Id 和 channelgroup的映射
		SessionUtils.bindChannelGroup(groupId, channelGroup);
		ByteBuf byteBuf = getByteBuf(ctx, groupId, nameList);
		channelGroup.writeAndFlush(new TextWebSocketFrame(byteBuf));
	}
	
	public ByteBuf getByteBuf(ChannelHandlerContext ctx, String groupId, List<String> nameList) {
		ByteBuf bytebuf = ctx.alloc().buffer();
		JSONObject data = new JSONObject();
		data.put("type", 4);
		data.put("status", 200);
		data.put("groupId", groupId);
		data.put("nameList", nameList);
		byte []bytes = data.toJSONString().getBytes(Charset.forName("utf-8"));
		bytebuf.writeBytes(bytes);
		return bytebuf;
	}
}
