package com.stomp.chatroom.websocket;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * 斷線事件監聽
 */
@SuppressWarnings("deprecation")
@Component
public class STOMPDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebSocketSessions sessions;
	
	@Autowired
	private MsgTemplate template;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = accessor.getSessionId();
		sessions.removeSessionId(sessionId);

		// Print DEBUG
		System.out.println("[DEBUG] ["+ new Date().toLocaleString() + "] User leaved connection pool [STOMPDisconnectEventListener.java]");

		// Construct JSON
		JSONArray jArray = new JSONArray();
		for (String users : sessions.getAllUsers()) {
			JSONObject jObject = new JSONObject();
			jObject.put("user", users);
			jArray.put(jObject);
		}

		// Send JSON to update user list upon user leave 
		template.listUpdateBroadcast(jArray.toString());

//		LogHelper.logInfo(logger, "user logout, sessionId:{}", sessionId);
//		LogHelper.logInfo(logger, sessions.toString());
	}

}
