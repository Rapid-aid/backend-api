package rapidaid.backend_api.services;

import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    public void notify(String emergencyId) {
        System.out.println(emergencyId);
    }
}
