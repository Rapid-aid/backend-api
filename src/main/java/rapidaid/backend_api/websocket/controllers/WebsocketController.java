package rapidaid.backend_api.websocket.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class WebsocketController {
    @MessageMapping("/unit")
    @SendTo("/topic/confirmations")
    public String send(String message, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");


        return "aaa";
    }

}
