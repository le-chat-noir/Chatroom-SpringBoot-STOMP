package com.stomp.chatroom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	// Session attribute handler
	@RequestMapping(path = "/Chatroom", method = RequestMethod.GET)
	public String checkSession(HttpSession session) {
		try {
			if (session.getAttribute("chatName") != null && session != null) {
				return "Chatroom";
			} else if (session.getAttribute("nickName") != null && session != null) {
				session.setAttribute("chatName", session.getAttribute("nickName"));
				return "Chatroom";
			} else {
				return "chatroom-portal";
			}
		} catch (Exception e) {
			return "chatroom-portal";
		}
	}

	// Session attribute setter
	@RequestMapping(path = "/Chatroom", method = RequestMethod.POST)
	public String guestJoinChat(@RequestParam("guest") String guest, @RequestParam("chatName") String chatName, HttpSession session) {
		if (guest.equals("(訪客)")) {
			String userName = chatName + guest;
			session.setAttribute("chatName", userName);
			return "Chatroom";
		} else {

			return "chatroom-portal";

		}
	}
	
	// DEBUGGER
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String setRoot() {
		return "chatroom-portal";
	}
	
	// DEBUGGER
	@RequestMapping(path = "/DEBUG", method = RequestMethod.GET)
	public String callDebug() {
		return "DEBUG";
	}

	// DEBUGGER
	@RequestMapping(path = "/createSession", method = RequestMethod.GET)
	public String createSession(HttpServletRequest request) {
		request.getSession().setAttribute("chatName", request.getParameter("debugName"));
		return "Chatroom";
	}
}
