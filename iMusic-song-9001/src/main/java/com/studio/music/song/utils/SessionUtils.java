package com.studio.music.song.utils;

import com.studio.music.song.model.pojo.TbUser;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtils {
	/**
	 * userID 映射 连接channel
	 */
	private static Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
	
	/**
	 * groupId ---> channelgroup 群聊ID和群聊ChannelGroup映射
	 */
	private static Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

	private static Map<String, BlockingQueue<Integer>> groupIdMusicMap = new ConcurrentHashMap<>();
	
	public static void bindChannel(TbUser user, Channel channel) {
		userIdChannelMap.put(user.getId(), channel);
		channel.attr(Attributes.SESSION).set(user);
	}
	
	public static void unbind(Channel channel) {
		if (hasLogin(channel)) {
			userIdChannelMap.remove(getUser(channel).getId());
			channel.attr(Attributes.SESSION).set(null);
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
