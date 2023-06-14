package com.studio.music.song.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.studio.music.song.command.GroupMessageRequestPacket;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.mapper.TbGroupMapper;
import com.studio.music.song.mapper.TbGroupToUserMapper;
import com.studio.music.song.mapper.TbUserMapper;
import com.studio.music.song.model.pojo.TbGroup;
import com.studio.music.song.model.pojo.TbGroupToUser;
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

	private TbGroupToUserMapper tbGroupToUserMapper;

	private TbUserMapper tbUserMapper;
	
	public GroupMessageRequestHandler(RedisTemplate redisTemplate, TbGroupMapper tbGroupMapper, TbUserMapper tbUserMapper, TbGroupToUserMapper tbGroupToUserMapper) {
		this.redisTemplate = redisTemplate;
		this.tbGroupMapper = tbGroupMapper;
		this.tbUserMapper = tbUserMapper;
		this.tbGroupToUserMapper = tbGroupToUserMapper;
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
					for (MusicVo musicVo:groupMusicVo.getMusicVos()) {
						if (musicVo.getMusicId().equals(musicId)) {
							groupMusicVo.getMusicVos().remove(musicVo);
						}
					}
				}
				else if (operate.equals(4)) {
					// 更改歌曲时间

				}
				redisTemplate.opsForValue().set(RedisContacts.GROUP_MUSICS_PRE + ":" + groupId + "", JSON.toJSONString(groupMusicVo));
				// 发送消息给群组成员
				List<Channel> channels = SessionUtils.getChannelGroup(groupId);
				for (Channel channel:channels) {
					ByteBuf byteBuf = getByteBuf(ctx, groupId,  tbUser, operate, groupMusicVo.getMusicVos());
					channel.writeAndFlush(new TextWebSocketFrame(byteBuf));
				}
			}
		}
	}
	
	public ByteBuf getByteBuf(ChannelHandlerContext ctx, Integer groupId,
			                  TbUser fromUser, Integer operateType, List<MusicVo> musicVos) {
		ByteBuf byteBuf = ctx.alloc().buffer();
		JSONObject data = new JSONObject();
		data.put("type", 10);
		data.put("status", 200);
		JSONObject params = new JSONObject();
		params.put("operateType", operateType);
		params.put("fromUser", fromUser);
		params.put("groupId", groupId);
		params.put("data", musicVos);
		data.put("params", params);
		byte []bytes = data.toJSONString().getBytes(Charset.forName("utf-8"));
		byteBuf.writeBytes(bytes);
		return byteBuf;
	}
}
