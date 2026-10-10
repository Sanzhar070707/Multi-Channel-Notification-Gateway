import notification.*;
import sender.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ GATEWAY С ПАТТЕРНОМ BRIDGE ===\n");

        // Создаем провайдеры
        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();
        SmsMessageSender sms = new SmsMessageSender();
        PushNotificationSender push = new PushNotificationSender();

        // Сценарий 1: UrgentNotification + Telegram
        System.out.println("--- Сценарий 1: UrgentNotification + Telegram ---");
        Notification urgentTelegram = new UrgentNotification(telegram, sms);
        urgentTelegram.send("@user123", "Системный сбой сервера!");

        // Сценарий 2: EncryptedNotification + Email
        System.out.println("\n--- Сценарий 2: EncryptedNotification + Email ---");
        Notification encryptedEmail = new EncryptedNotification(email);
        encryptedEmail.send("admin@company.com", "Конфиденциальные данные пароля");

        // Сценарий 3: ScheduledBatchNotification + SMS
        System.out.println("\n--- Сценарий 3: ScheduledBatchNotification + SMS ---");
        List<String> group = List.of("+77011234567", "+77029876543");
        ScheduledBatchNotification batchSms = new ScheduledBatchNotification(sms, group);
        batchSms.sendBatch("Завтра в 10:00 состоится собрание.");

        // Сценарий 4: Runtime switching: Telegram -> Email
        System.out.println("\n--- Сценарий 4: Runtime switching (Telegram -> Email) ---");
        Notification flexibleNotif = new UrgentNotification(telegram, sms);
        System.out.println("Отправка через Telegram:");
        flexibleNotif.send("user_tele", "Привет через телеграм");

        System.out.println("Меняем провайдер на Email «на лету» без изменения класса уведомления:");
        flexibleNotif.setSender(email);
        flexibleNotif.send("user_email@mail.com", "Привет через email");

        // Сценарий 5: Telegram unavailable -> fallback to SMS
        System.out.println("\n--- Сценарий 5: Telegram недоступен -> fallback на SMS ---");
        telegram.setAvailable(false); // Выключаем телеграм
        Notification fallbackNotif = new UrgentNotification(telegram, sms);
        fallbackNotif.send("@ivan", "Срочное сообщение при упавшем Telegram!");
        telegram.setAvailable(true); // Включаем обратно

        // Сценарий 6: MarketingNotification + PushNotificationSender
        System.out.println("\n--- Сценарий 6: MarketingNotification + Push ---");
        Notification marketingPush = new MarketingNotification(push);
        marketingPush.send("Device_Token_XYZ", "Скидка 50% на все курсы!");
    }
}