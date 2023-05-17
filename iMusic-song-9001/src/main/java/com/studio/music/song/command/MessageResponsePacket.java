package com.studio.music.song.command;


import com.studio.music.song.model.pojo.TbUser;

public class MessageResponsePacket extends Packet{
    /**
     * 这条消息是谁发的
     */
    private TbUser fromUser;
	
	private String message;
		
	public TbUser getFromUser() {
		return fromUser;
	}

	public void setFromUser(TbUser fromUser) {
		this.fromUser = fromUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Byte getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
