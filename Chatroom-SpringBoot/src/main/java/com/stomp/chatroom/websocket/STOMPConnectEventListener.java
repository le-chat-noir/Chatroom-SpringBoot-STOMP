package com.stomp.chatroom.websocket;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * 連線事件監聽
 */
@SuppressWarnings("deprecation")
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebSocketSessions sessions;

//	@Autowired
//	private MsgTemplate template;

	
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String user = accessor.getNativeHeader("user").get(0);
		String sessionId = accessor.getSessionId();
		sessions.registerSessionId(user, sessionId);

		System.out.println("[INFO] ["+ new Date().toLocaleString() + "] User joins connection pool [STOMPConnectEventListener.java]");

		// This part is temporarily disabled, user list update will be perform upon new user join and initiated by user
		// Otherwise new join user will not receive this broadcast
//		// Construct JSON
//		JSONArray jArray = new JSONArray();
//		for(String users:sessions.getAllUsers()) {
//			JSONObject jObject = new JSONObject();
//			jObject.put("user", users);
//			jArray.put(jObject);
//		}
//        
//		// Send JSON
//		template.listUpdateBroadcast(jArray.toString());

	}

}
