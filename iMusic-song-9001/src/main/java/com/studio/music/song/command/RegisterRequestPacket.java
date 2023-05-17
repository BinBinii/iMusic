package com.studio.music.song.command;

import com.studio.music.song.model.pojo.TbUser;

public class RegisterRequestPacket extends Packet{
    	
	private TbUser user;
			
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
