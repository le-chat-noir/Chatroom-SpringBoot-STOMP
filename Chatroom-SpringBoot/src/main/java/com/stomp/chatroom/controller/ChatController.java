package com.stomp.chatroom.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.stomp.chatroom.model.Message;
import com.stomp.chatroom.model.OutputMessage;
import com.stomp.chatroom.websocket.MsgTemplate;
import com.stomp.chatroom.websocket.WebSocketSessions;

@SuppressWarnings("deprecation")
@Controller
public class ChatController {

	@Autowired
	private WebSocketSessions sessions;

	@Autowired
	private MsgTemplate template;
	

	// Handle message broadcast from users
	
	@MessageMapping("/chat")
	public void sendMsg(final Message message) throws Exception {
		if(message.getImg64()==null&&(message.getText()==null||message.getText()=="")) {
			// Print DEBUG
			System.out.println("[DEBUG] [" + new Date().toLocaleString() + "] User Broadcast empty message, text: " + message.getText() + " | img64: " + message.getImg64() + " [ChatController.java]");
		}else {
			// Print DEBUG
			System.out.println("[DEBUG] [" + new Date().toLocaleString() + "] User Broadcast, text: " + message.getText() + " | img64: " + message.getImg64() + " [ChatController.java]");

			// Generate time stamp
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
			final String time = dateFormat.format(new Date());

			// Send message + time stamp
			template.sendMsgTo(MsgTemplate.BROADCAST_DESTINATION, new OutputMessage(time, message));
		}
	}

	@MessageMapping("/userList")
	@SendTo(MsgTemplate.USERLIST_DESTINATION)
	public String updateUserList() throws Exception {
		// Print DEBUG
		System.out.println(
				"[DEBUG] [" + new Date().toLocaleString() + "] New user request for user lists. [ChatController.java]");

		// Construct user list
		JSONArray jArray = new JSONArray();
		for (String users : sessions.getAllUsers()) {
			JSONObject jObject = new JSONObject();
			jObject.put("user", users);
			jArray.put(jObject);
		}
		// Send JSON
		return jArray.toString();
	}

	@MessageMapping("/toUser")
	public void sendUserMsg(final Message message) throws Exception {
		// Print DEBUG
		System.out.println("[DEBUG] [" + new Date().toLocaleString() + "] user:" + message.getFrom() + " sends message to target: " + message.getTarget() + " [ChatController.java]");

		// Generate time stamp
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
		final String time = dateFormat.format(new Date());

		String sender = message.getFrom();
		if (!sender.contains("訪客")) {
			String target = message.getTarget();
			template.sendMsgToUser(target, new OutputMessage(time, message));
		} else {
			// Print DEBUG
			System.out.println("[DEBUG] [" + new Date().toLocaleString()
					+ "] guest trying to send private message. [ChatController.java]");

			Message sysMsg = new Message();
			sysMsg.setFrom("SYSTEM");
			sysMsg.setText("無效的訊息: 訪客無私訊功能");
			template.sendMsgToUser(sender, new OutputMessage(time, sysMsg));
		}

	}
	

}
