package rapidaid.backend_api.websocket.message;

public class Message {
    private MessageType messageType;
    private String content;

    public Message(MessageType messageType, String content) {
        this.messageType = messageType;
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
