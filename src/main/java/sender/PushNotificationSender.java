package sender;

public class PushNotificationSender implements MessageSender {
    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        System.out.println("[Push] Push-уведомление для устройства " + recipient + " | " + title + ": " + body);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getChannelName() {
        return "Push";
    }
}