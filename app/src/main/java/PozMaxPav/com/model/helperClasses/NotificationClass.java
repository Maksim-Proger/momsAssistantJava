package PozMaxPav.com.model.helperClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import java.util.Timer;
import java.util.TimerTask;
import PozMaxPav.com.R;

public class NotificationClass {
    private static final String CHANNEL_ID = "Channel_id";
    private static final int NOTIFICATION_ID = 2;
    private Context context;
    private Timer timer;

    // делаем builder членом класса. Это нужно чтобы изменять существующее уведомление
    // и не создавать каждый раз новое.
    private NotificationCompat.Builder builder;
    private int minutesPassed = 0;

    public NotificationClass(Context context) {
        this.context = context;
        createNotificationChannel(); // Вызываем создание канала уведомлений один раз
        startNotificationTimer(); // Запускаем таймер для обновления уведомления
    }

    private void startNotificationTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                minutesPassed++; // Увеличиваем количество минут
                updateNotification(); // Обновляем текст уведомления
            }
        };
        timer.schedule(timerTask, 60000, 60000);
    }

    public void stopNotificationTimer() {
        if (timer != null) {
            timer.cancel();
        }
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
                .setContentText("Спит уже: " + minutesPassed + correctWord())
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
        builder.setContentText("Спит уже: " + minutesPassed + correctWord());
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private String correctWord() {
        if (minutesPassed % 10 == 1) {
            return " минуту";
        } else if (minutesPassed % 10 > 1 && minutesPassed % 10 < 5) {
            return " минуты";
        } else {
            return " минут";
        }
    }
}
