package com.studio.music.song.command;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MessageRequestPacket extends Packet{

	private String toUserId;

	private String fromUserId;
	
	private String message;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	private String fileType;
			
	public MessageRequestPacket() {
		super();
	}

	public MessageRequestPacket(String toUserId, String message) {
		super();
		this.toUserId = toUserId;
		this.message = message;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public Byte getCommand() {
		// TODO Auto-generated method stub
		return Command.MESSAGE_REQUEST;
	}

}
