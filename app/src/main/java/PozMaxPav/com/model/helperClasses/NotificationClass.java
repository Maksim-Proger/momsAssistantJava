package PozMaxPav.com.model.helperClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import androidx.core.app.NotificationCompat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import PozMaxPav.com.R;

public class NotificationClass {
    private static final String CHANNEL_ID = "Channel_id";
    private static final int NOTIFICATION_ID = 2;
    private Context context;

    // делаем builder членом класса. Это нужно чтобы изменять существующее уведомление
    // и не создавать каждый раз новое.
    private NotificationCompat.Builder builder;

    // для логики отсчета сна
    String notificationTime;
    private Handler handler = new Handler();
    private static final long DELAY = 5000; // 5 секунд в миллисекундах

    public NotificationClass(Context context) {
        this.context = context;
        createNotificationChannel(); // Вызываем создание канала уведомлений один раз
        startNotificationTimer(); // Запускаем таймер для обновления уведомления
    }

    private void startNotificationTimer() {

        updateNotification();
        // Запустите обновление каждые 5 секунд
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateNotification();
                // Повторно запустите обновление через 5 секунд
                handler.postDelayed(this, DELAY); // разобраться с этой строкой
            }
        }, DELAY);

    }

    public void createNotificationChannel() {
        CharSequence name = "My Channel";
        String description = "Описание канала уведомлений";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        // установливаем для канала звуковой сигнал в null:
//        channel.setSound(null, null);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void showNotification() {
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Продолжительность сна")
                .setContentText("Спит уже: " + notificationTime + correctWord())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(null)
                .setAutoCancel(true);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        // указывает системе отобразить звуковое оповещение только один раз, и игнорировать
        // последующие обновления уведомления.
        builder.setOnlyAlertOnce(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void updateNotification() {
        String timerStart = SharedPreferencesUtils.getKeyAsleep(context);

        if (timerStart != null) {
            LocalTime time1 = LocalTime.parse(timerStart);
            LocalTime timerStop = LocalTime.now();

            long differenceInMinutes = ChronoUnit.MINUTES.between(time1, timerStop);
            notificationTime = String.valueOf(differenceInMinutes);

            if (builder != null) {
                builder.setContentText("Спит уже: " + notificationTime + correctWord());
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        }
    }

    private String correctWord() {
        int newNotificationTime = 0;

        if (notificationTime != null) {
            newNotificationTime = Integer.parseInt(notificationTime);
        }

        if (newNotificationTime % 10 == 1 && newNotificationTime % 100 != 11) {
            return " минуту";
        } else if ((newNotificationTime % 10 > 2 && newNotificationTime % 10 <= 4) &&
                (newNotificationTime % 100 < 10 || newNotificationTime % 100 >= 20)) {
            return " минуты";
        } else {
            return " минут";
        }
    }
}
