package com.stomp.chatroom.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stomp.chatroom.model.Message;
import com.stomp.chatroom.model.OutputMessage;
import com.stomp.chatroom.websocket.MsgTemplate;


// 給系統HTTP POST Call 用的...
// DEBUG用，目前沒用
@RestController
@RequestMapping(value = "/system", method = RequestMethod.POST)
public class DebugSystemController {

    @Autowired
    private MsgTemplate template;

    // system broadcast call?
    @RequestMapping("/broadcast")
    public OutputMessage broadcast(@RequestBody Message message) {
        OutputMessage outputMessage = new OutputMessage(new Date().toString(), message);

        // send function enter here:
        template.broadcast(outputMessage);
        // -------
        
        
        return outputMessage;
    }

    @RequestMapping("/send/{user}")
    public OutputMessage broadcast(@PathVariable("user") String user, @RequestBody Message message) {
        OutputMessage outputMessage = new OutputMessage(new Date().toString(), message);
        template.sendMsgToUser(user, outputMessage);
        return outputMessage;
    }

}
