package com.stomp.chatroom.model;

// This is the message body
public class Message {
	private String from;
	private String text;
	private String img64;
	private String target;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImg64() {
		return img64;
	}

	public void setImg64(String img64) {
		this.img64 = img64;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "Message{" + "from='" + from + "\'" + ", text='" + text + "\'" + ", img64='" + img64 + "\'" + ", img64='" + img64 + "\'" +  ", target='" + target + "\'" + "}";
	}
}
