package notification;

import sender.MessageSender;
import java.util.Base64;

public class EncryptedNotification extends Notification {
    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        String encrypted = Base64.getEncoder().encodeToString(message.getBytes());
        System.out.println("[Encrypted] Исходное сообщение зашифровано в Base64");
        sender.sendMessage(recipient, "Защищенное уведомление", encrypted);
    }
}