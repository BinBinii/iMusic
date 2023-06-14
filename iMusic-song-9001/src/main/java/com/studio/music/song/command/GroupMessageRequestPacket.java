package com.studio.music.song.command;

public class GroupMessageRequestPacket extends Packet{

	private Integer toGroupId;

	private Integer userId;
	
	private Integer operate;

	private Integer musicId;

	
	public GroupMessageRequestPacket() {
		
	}
	
	public GroupMessageRequestPacket(Integer toGroupId, Integer userId, Integer operate, Integer musicId) {
		super();
		this.toGroupId = toGroupId;
		this.userId = userId;
		this.operate = operate;
		this.musicId = musicId;
	}

	public Integer getToGroupId() {
		return toGroupId;
	}

	public void setToGroupId(Integer toGroupId) {
		this.toGroupId = toGroupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public Integer getMusicId() {
		return musicId;
	}

	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	@Override
	public Byte getCommand() {
		// TODO Auto-generated method stub
		return Command.GROUP_MESSAGE_REQUEST;
	}

}
