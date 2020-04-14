package com.stomp.chatroom.websocket;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
@SuppressWarnings("deprecation")
@Service
public class MsgTemplate {

    public static final String BROADCAST_DESTINATION = "/topic/messages";
    public static final String USERLIST_DESTINATION = "/topic/userList";
    private static final String USER_TOPIC = "/subscribe";

    @Autowired
    private WebSocketSessions sessions;

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMsgTo(String destination, Object msg) {
        template.convertAndSend(destination, msg);
    }

    public void sendMsgToUser(String user, Object msg) {
        sessions.getSessionIds(user).forEach(sessionId -> {
            template.convertAndSendToUser(sessionId, USER_TOPIC, msg);
        });
    }

    
    // This is for System initiated broadcast on /messages channel 
   
	public void broadcast(Object msg) {
    	// Print DEBUG
    	System.out.println("[DEBUG] ["+ new Date().toLocaleString() + "] Process called for broadcast [MsgTemplate.java]");
    	
        sendMsgTo(BROADCAST_DESTINATION, msg);
    }
    
    
    // This is for sending user list over to broadcast::/userList channel upon user list update
    public void listUpdateBroadcast(Object msg) {
    	// Print DEBUG
    	System.out.println("[DEBUG] ["+ new Date().toLocaleString() + "] Process called for broadcast over userList channel [MsgTemplate.java]");
        
    	sendMsgTo(USERLIST_DESTINATION, msg);
    }

}
