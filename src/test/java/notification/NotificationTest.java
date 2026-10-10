package notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import sender.*;
import java.util.List;

public class NotificationTest {

    private TelegramMessageSender telegram;
    private EmailMessageSender email;
    private SmsMessageSender sms;
    private PushNotificationSender push;

    @BeforeEach
    void setUp() {
        telegram = new TelegramMessageSender();
        email = new EmailMessageSender();
        sms = new SmsMessageSender();
        push = new PushNotificationSender();
    }

    // 1. Успешная доставка (Successful delivery)
    @Test
    void testSuccessfulDelivery() {
        Notification notification = new UrgentNotification(telegram, sms);
        assertDoesNotThrow(() -> notification.send("@user", "Тест успешной отправки"));
    }

    // 2. Недоступный провайдер (Unavailable provider)
    @Test
    void testUnavailableProvider() {
        telegram.setAvailable(false);
        assertFalse(telegram.isAvailable(), "Telegram должен быть недоступен");
    }

    // 3. Fallback механизм (Fallback)
    @Test
    void testFallbackMechanism() {
        telegram.setAvailable(false);
        UrgentNotification notification = new UrgentNotification(telegram, sms);
        assertDoesNotThrow(() -> notification.send("@user", "Срочное сообщение с fallback"));
    }

    // 4. Ограничение длины SMS в 160 символов (SMS length restriction)
    @Test
    void testSmsLengthRestrictionValid() {
        boolean result = sms.sendMessage("+77011234567", "Тема", "Короткое сообщение");
        assertTrue(result, "Сообщение короче 160 символов должно отправляться успешно");
    }

    @Test
    void testSmsLengthRestrictionExceeded() {
        String longBody = "А".repeat(155);
        boolean result = sms.sendMessage("+77011234567", "Тема", longBody);
        assertFalse(result, "Сообщение превышающее 160 символов должно возвращать false (ошибка)");
    }

    // 5. Зашифрованное сообщение (Encrypted message)
    @Test
    void testEncryptedNotification() {
        Notification encrypted = new EncryptedNotification(email);
        assertDoesNotThrow(() -> encrypted.send("sec@mail.com", "Секретные данные"));
    }

    // 6. Пакетное уведомление (Batch notification)
    @Test
    void testBatchNotification() {
        List<String> recipients = List.of("+77010000001", "+77010000002");
        ScheduledBatchNotification batch = new ScheduledBatchNotification(sms, recipients);
        assertDoesNotThrow(() -> batch.sendBatch("Рассылка для всех"));
    }

    // 7. Смена провайдера на лету (Runtime sender switching)
    @Test
    void testRuntimeSenderSwitching() {
        Notification notification = new UrgentNotification(telegram, sms);
        assertEquals("Telegram", notification.getSender().getChannelName());

        notification.setSender(email);
        assertEquals("Email", notification.getSender().getChannelName(), "Провайдер должен измениться на Email");
    }

    // 8. Новый PushNotificationSender
    @Test
    void testNewPushNotificationSender() {
        Notification marketing = new MarketingNotification(push);
        assertDoesNotThrow(() -> marketing.send("DeviceToken123", "Акция!"));
        assertEquals("Push", push.getChannelName());
    }

    // 9. Новый MarketingNotification
    @Test
    void testNewMarketingNotificationType() {
        Notification marketingNotif = new MarketingNotification(email);
        assertDoesNotThrow(() -> marketingNotif.send("client@mail.com", "Скидка 20%"));
    }

    // 10. Сценарий с ошибкой (Failure scenario)
    @Test
    void testFailureScenarioWhenAllUnavailable() {
        telegram.setAvailable(false);
        UrgentNotification notification = new UrgentNotification(telegram, telegram);
        assertDoesNotThrow(() -> notification.send("@user", "Аварийное сообщение"));
    }
}