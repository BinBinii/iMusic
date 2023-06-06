package com.studio.music.song.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.studio.music.song.command.GroupMessageRequestPacket;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbUser;
import com.studio.music.song.model.vo.GroupMusicVo;
import com.studio.music.song.model.vo.MusicVo;
import com.studio.music.song.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 发送群消息handler组件
 * @author holiday
 * 2020-11-16
 */
@Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket>{

//	public static GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

	private RedisTemplate<String, Object> redisTemplate;

	private TbGroupMapper tbGroupMapper;

	private TbUserMapper tbUserMapper;
	
	public GroupMessageRequestHandler(RedisTemplate redisTemplate, TbGroupMapper tbGroupMapper, TbUserMapper tbUserMapper) {
		this.redisTemplate = redisTemplate;
		this.tbGroupMapper = tbGroupMapper;
		this.tbUserMapper = tbUserMapper;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
		// TODO Auto-generated method stub
		Integer groupId = groupMessageRequestPacket.getToGroupId();
		Integer userId = groupMessageRequestPacket.getUserId();
		Integer operate = groupMessageRequestPacket.getOperate();
		Integer musicId = groupMessageRequestPacket.getMusicId();
		TbGroup tbGroup = tbGroupMapper.selectById(groupId);
		TbUser tbUser = tbUserMapper.selectById(userId);
		if (tbGroup != null) {
			String json = String.valueOf(redisTemplate.opsForValue().get(RedisContacts.GROUP_MUSICS_PRE + ":" + groupId + ""));
			if (json.equals("null")) {
				GroupMusicVo groupMusicVo = new GroupMusicVo();
				List<MusicVo> musicVos = new ArrayList<>();
				MusicVo musicVo = new MusicVo();
				musicVo.setNickname(tbUser.getNickname())
						.setMusicId(musicId);
				musicVos.add(musicVo);
				groupMusicVo.setGroupId(groupId)
						.setMusicVos(musicVos);
				redisTemplate.opsForValue().set(RedisContacts.GROUP_MUSICS_PRE + ":" + groupId + "", JSON.toJSONString(groupMusicVo));
			} else {
				GroupMusicVo groupMusicVo = JSON.parseObject(json, GroupMusicVo.class);
				if (operate.equals(1)) {
					// 加入歌曲

					MusicVo musicVo = new MusicVo();
					musicVo.setNickname(tbUser.getNickname())
							.setMusicId(musicId);
					groupMusicVo.getMusicVos().add(musicVo);
				}
				else if (operate.equals(2)) {
					// 跳过歌曲
					groupMusicVo.getMusicVos().remove(0);
				}
				else if (operate.equals(3)) {
					// 移除歌曲
				}
				redisTemplate.opsForValue().set(RedisContacts.GROUP_MUSICS_PRE + ":" + groupId + "", JSON.toJSONString(groupMusicVo));
				// 发送消息给群组成员

			}
		}

//		TbGroup tbGroup =
//		String groupId = groupMessageRequestPacket.getToGroupId();
//		String fileType = groupMessageRequestPacket.getFileType();
//		ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
//		List<String> nameList = new ArrayList<>();
//		for (Channel channel : channelGroup) {
//			TbUser user = SessionUtils.getUser(channel);
//			nameList.add(user.getNickname());
//		}
//		if (channelGroup != null) {
//			TbUser user = SessionUtils.getUser(ctx.channel());
//			ByteBuf byteBuf = getByteBuf(ctx, groupId, groupMessageRequestPacket.getMessage(), user, fileType, nameList);
//			channelGroup.remove(ctx.channel());//发送方不需要自己再收到消息
//			channelGroup.writeAndFlush(new TextWebSocketFrame(byteBuf));
//			channelGroup.add(ctx.channel()); //发送完消息再添加回去 ---todo 是否有更好得方式
//		}
	}
	
	public ByteBuf getByteBuf(ChannelHandlerContext ctx, String groupId, String message, 
			                  TbUser fromUser, String fileType, List<String> nameList) {
		ByteBuf byteBuf = ctx.alloc().buffer();
		JSONObject data = new JSONObject();
		data.put("type", 10);
		data.put("status", 200);
		JSONObject params = new JSONObject();
		params.put("message", message);
		params.put("fileType", fileType);
		params.put("fromUser", fromUser);
		params.put("groupId", groupId);
		Collections.reverse(nameList);
		params.put("nameList", nameList);
		data.put("params", params);
		byte []bytes = data.toJSONString().getBytes(Charset.forName("utf-8"));
		byteBuf.writeBytes(bytes);
		return byteBuf;
	}
}
