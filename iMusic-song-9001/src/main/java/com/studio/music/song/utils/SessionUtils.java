package com.studio.music.song.utils;

import com.alibaba.fastjson.JSON;
import com.studio.music.song.contacts.RedisContacts;
import com.studio.music.song.model.pojo.TbUser;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class SessionUtils {

	@Autowired
	public SessionUtils(RedisTemplate<String, Object> redisTemplate) {
		SessionUtils.redisTemplate = redisTemplate;
	}

	private static RedisTemplate<String, Object> redisTemplate;

	/**
	 * userID 映射 连接channel
	 */
	private static Map<Integer, Channel> userIdChannelMap = new ConcurrentHashMap<>();
	
	/**
	 * groupId ---> channelgroup 群聊ID和群聊ChannelGroup映射
	 */
	private static Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

	private static Map<String, BlockingQueue<Integer>> groupIdMusicMap = new ConcurrentHashMap<>();

	public static void bindChannel(Integer groupId ,TbUser user, Channel channel) {
		redisTemplate.opsForValue().set(RedisContacts.USER_SESSION + ":" + user.getId() + "", JSON.toJSONString(channel));
		redisTemplate.expire(RedisContacts.USER_SESSION + ":" + user.getId() + "", RedisContacts.USER_SESSION_EXPIRE, TimeUnit.SECONDS);
		channel.attr(Attributes.SESSION).set(user);
	}
	
	public static void unbind(Channel channel) {
		if (hasLogin(channel)) {
			Integer userId = getUser(channel).getId();
			redisTemplate.delete(RedisContacts.USER_SESSION + ":" + userId + "");
			channel.attr(Attributes.SESSION).set(null);
		}
	}

	public static void expireChannel(Channel channel) {
		if (hasLogin(channel)) {
			Integer userId = getUser(channel).getId();
			redisTemplate.expire(RedisContacts.USER_SESSION + ":" + userId + "", RedisContacts.USER_SESSION_EXPIRE, TimeUnit.SECONDS);
		}
	}
	
	public static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION);
	}
	
	public static TbUser getUser(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}
	
	public static Channel getChannel(String userId) {
		return userIdChannelMap.get(userId);
	}
	/**
	 * 绑定群聊Id  群聊channelGroup
	 * @param groupId
	 * @param channelGroup
	 */
	public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
		groupIdChannelGroupMap.put(groupId, channelGroup);
	}

	public static ChannelGroup getChannelGroup(String groupId) {
		return groupIdChannelGroupMap.get(groupId);
	}
}
