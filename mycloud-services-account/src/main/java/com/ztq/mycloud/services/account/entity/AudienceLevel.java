package com.ztq.mycloud.services.account.entity;

public class AudienceLevel {
	private String name;
	private int roomId;
	private int level;
	
	public AudienceLevel() {

	}

	public AudienceLevel(String name, int roomId, int level) {
		this.name = name;
		this.roomId = roomId;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "AudienceLevel [name=" + name + ", roomId=" + roomId + ", level=" + level + "]";
	}
}
