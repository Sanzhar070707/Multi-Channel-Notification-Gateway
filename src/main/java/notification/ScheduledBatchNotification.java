package notification;

import sender.MessageSender;
import java.util.List;

public class ScheduledBatchNotification extends Notification {
    private List<String> recipients;

    public ScheduledBatchNotification(MessageSender sender, List<String> recipients) {
        super(sender);
        this.recipients = recipients;
    }

    @Override
    public void send(String recipient, String message) {
        sender.sendMessage(recipient, "Пакетное уведомление", message);
    }

    public void sendBatch(String message) {
        int total = recipients.size();
        int successful = 0;
        int failed = 0;

        for (String rec : recipients) {
            boolean res = sender.sendMessage(rec, "Массовая рассылка", message);
            if (res) successful++;
            else failed++;
        }

        System.out.println("\n--- Результаты пакетной рассылки ---");
        System.out.println("Total: " + total);
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
    }
}