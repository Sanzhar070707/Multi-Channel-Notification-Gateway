package sender;

public class KafkaEventSender implements MessageSender {
    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        System.out.println("[Kafka] Публикация события в топик для " + recipient + " с данными: " + body);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}