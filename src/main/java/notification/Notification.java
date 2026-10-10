package notification;

import sender.MessageSender;

public abstract class Notification {
    protected MessageSender sender; // Ссылка на реализацию (тот самый Bridge)

    protected Notification(MessageSender sender) {
        this.sender = sender;
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }

    public MessageSender getSender() {
        return sender;
    }

    public abstract void send(String recipient, String message);
}