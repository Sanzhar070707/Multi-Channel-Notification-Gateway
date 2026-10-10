package notification;

import sender.MessageSender;

public class UrgentNotification extends Notification {
    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender, MessageSender fallbackSender) {
        super(sender);
        this.fallbackSender = fallbackSender;
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentMessage = "[URGENT] " + message;

        // Проверяем доступность основного провайдера
        if (sender.isAvailable()) {
            boolean success = sender.sendMessage(recipient, "Срочное уведомление", urgentMessage);
            if (success) return;
        }

        System.out.println("[Warning] Основной канал " + sender.getChannelName() + " недоступен. Переключение на резервный канал...");
        if (fallbackSender != null && fallbackSender.isAvailable()) {
            fallbackSender.sendMessage(recipient, "Срочное уведомление (Fallback)", urgentMessage);
        } else {
            System.err.println("[Error] Резервный канал также недоступен!");
        }
    }
}