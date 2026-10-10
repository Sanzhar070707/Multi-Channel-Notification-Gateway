package sender;

public class SmsMessageSender implements MessageSender {
    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        String fullMessage = title != null && !title.isEmpty() ? title + ": " + body : body;
        if (fullMessage.length() > 160) {
            System.err.println("[SMS Ошибка] Превышен лимит 160 символов (" + fullMessage.length() + ")");
            return false;
        }
        System.out.println("[SMS] Отправка на " + recipient + ": " + fullMessage);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}