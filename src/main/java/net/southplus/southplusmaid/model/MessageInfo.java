package net.southplus.southplusmaid.model;

public class MessageInfo {
    private  MessageType type;
    private String message;

    public MessageInfo(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
