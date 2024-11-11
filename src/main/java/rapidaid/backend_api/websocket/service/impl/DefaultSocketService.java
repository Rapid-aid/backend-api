package rapidaid.backend_api.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.websocket.message.Message;
import rapidaid.backend_api.websocket.message.MessageType;
import rapidaid.backend_api.websocket.service.WebSocketService;

@Service
public class DefaultSocketService implements WebSocketService {
    private final SimpMessagingTemplate template;

    @Autowired
    public DefaultSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void notify(String emergencyId) {
        Message message = new Message(MessageType.emergencyNotification, emergencyId);
        System.out.println("Sending message: " + message);
        template.convertAndSend("/topic/emergency", message);
    }
}
