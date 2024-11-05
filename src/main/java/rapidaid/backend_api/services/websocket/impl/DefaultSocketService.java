package rapidaid.backend_api.services.websocket.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.services.websocket.WebSocketService;

@Service
public class DefaultSocketService implements WebSocketService {
    private final SimpMessagingTemplate template;

    @Autowired
    public DefaultSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void notify(String emergencyId) {
        template.convertAndSend("/topic/emergency", emergencyId);
    }
}
