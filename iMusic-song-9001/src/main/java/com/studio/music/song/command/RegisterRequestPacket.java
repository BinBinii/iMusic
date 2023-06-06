package com.studio.music.song.command;

import com.studio.music.song.model.pojo.TbUser;

public class RegisterRequestPacket extends Packet{

	private Integer groupId;
    	
	private TbUser user;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
			
	public TbUser getUser() {
		return user;
	}

	public void setUser(TbUser user) {
		this.user = user;
	}

	@Override
	public Byte getCommand() {
		// TODO Auto-generated method stub
		return Command.REGISTER_REQUEST;
	}

}
