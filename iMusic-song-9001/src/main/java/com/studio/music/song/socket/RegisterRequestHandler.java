package com.studio.music.song.socket;

import com.studio.music.song.command.RegisterRequestPacket;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.utils.SessionUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 注册 绑定用户channel
 * @author holiday
 * date 2020-11-04
 */
@Sharable
public class RegisterRequestHandler extends SimpleChannelInboundHandler<RegisterRequestPacket>{

	public static RegisterRequestHandler INSTANCE = new RegisterRequestHandler();
	
	private RegisterRequestHandler() {
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestPacket registerRequestPacket) throws Exception {
		// TODO 加入群组
		/**
		 * 1. 判断redis是否有该群组会话
		 * 若无会话则创建会话，群组会话生命周期为15分钟
		 * 如果有会话则加入群组
		 */
		SessionUtils.bindChannel(registerRequestPacket.getGroupId(), registerRequestPacket.getUser(), ctx.channel());
//		SessionUtils.bindChannel(loginUser, ctx.channel());
		if (SessionUtils.hasLogin(ctx.channel())) {
			System.out.println("该用户已登录");
		}
		// 查询是否有未签收的信息
		
//     ByteBuf buffer = getByteBuf(ctx);      
//     ctx.channel().writeAndFlush(new TextWebSocketFrame(buffer));
	}
	
}
