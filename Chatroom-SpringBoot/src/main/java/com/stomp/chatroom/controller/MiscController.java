package com.stomp.chatroom.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stomp.chatroom.websocket.WebSocketSessions;

@RestController
public class MiscController {
	@Autowired
	private WebSocketSessions sessions; 
	
	@SuppressWarnings("deprecation")
	@PostMapping("/img2base64")
	public String addData(@RequestParam("uploadedImg") MultipartFile imgPartFile, HttpServletResponse response, Model model) throws Exception {
		if(imgPartFile.getContentType().contains("image")&&imgPartFile.getSize()<524288000) {
			// Print DEBUG
			System.out.println("[DEBUG] [" + new Date().toLocaleString() + "] File uploaded, type: " + imgPartFile.getContentType() + " | size: " + imgPartFile.getSize() + " [MiscController.java]");
			
			// Resize image to less than 200px * 200px
			BufferedImage sourceImage = ImageIO.read(imgPartFile.getInputStream());
			Image thumbnail = null;
			if(sourceImage.getHeight()>sourceImage.getWidth()) {
				thumbnail = sourceImage.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);
			}else {
				thumbnail = sourceImage.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
			}
			BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
			bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
			ByteArrayOutputStream imgOut = new ByteArrayOutputStream(1000);
			ImageIO.write(bufferedThumbnail, "jpeg", imgOut);
			imgOut.flush();
		
			byte[] imgByte = imgOut.toByteArray();
			imgOut.close();
		
			// Base64Encode to display on webpage directly
			String imgEncoded = Base64.encodeBase64String(imgByte);

			// Return base64 String
			return imgEncoded;
		}else {
			// Print DEBUG
			System.out.println("[DEBUG] [" + new Date().toLocaleString() + "] File error, type: " + imgPartFile.getContentType() + " | size: " + imgPartFile.getSize() + " [MiscController.java]");
			return null;
		}
	}
	
	@GetMapping(value = "/checkUnique")
	public boolean checkUnique(@RequestParam("chatName") String chatName) {
		ArrayList<String> userList = sessions.getAllUsers();
		for(String user:userList) {
			if(chatName.equals(user)){
				return false;
			}	
		}
		return true;
	}
}
