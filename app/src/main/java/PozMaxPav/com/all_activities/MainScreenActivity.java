package PozMaxPav.com.all_activities;

import androidx.appcompat.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.helperClasses.GeneralNotificationClass;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.mainmenu.Category;

public class MainScreenActivity extends BaseActivity {

    private ImageButton sleep_button, diary_button, assistant_button, button_show_popup_menu;
    private TextView fieldName, textViewMainScreen;
    private Handler handler = new Handler();
    private static final long DELAY = 1000;

    // тестируем новые уведомления
    private GeneralNotificationClass generalNotificationClass;
    private Model model = new Model();


    // Проверка наличия уведомлений
    private boolean hasNotificationPermission() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        return notificationManager.areNotificationsEnabled();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        if (!hasNotificationPermission()) {
            // Запрос на утправку уведомлений
            showPermissionDialog();
        }

        // Получение и вывод имени пользователя и вывод в поле fieldName
        fieldName = findViewById(R.id.fieldName);
        String name = SharedPreferencesUtils.getKeyName(this);
        if (name != null) {
            String welcome = "Привет " + name;
            fieldName.setText(welcome);
        }

        // тестируем новые уведомления
        generalNotificationClass = new GeneralNotificationClass(MainScreenActivity.this, "Пора спать");

        // время бодровствования
        textViewMainScreen = findViewById(R.id.textViewMainScreen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTextViewMainScreen();


                // тестируем новые уведомления
//                String wakingTime = SharedPreferencesUtils.
//                        getKeyDifferenceTime(MainScreenActivity.this);
//                if (model.checkTimeLastSleep(wakingTime)) {
//                    generalNotificationClass.showNotification();
//                }


                handler.postDelayed(this, DELAY);
            }
        }, DELAY);

        addListenerOnButton();
    }


//    private String timeSinceLastSleep() {
//        String time = SharedPreferencesUtils.getKeyWakingTime(MainScreenActivity.this);
//        String stringDifferenceTimeView = "";
//        if (time != null) {
//
//            // Тестируем решение проблемы с переходом через полночь
//            String timeWithDate = LocalDate.now() + "T" + time;
//            LocalDateTime  awokeTime = LocalDateTime.parse(timeWithDate);
//            LocalDateTime currentTime = LocalDateTime.now();
//
//            if (currentTime.isBefore(awokeTime)) {
//                // Перешли через полночь, обновляем дату бодрствования
//                awokeTime = awokeTime.minusDays(1);
//            }
//
//            Duration durationDifferenceTime = Duration.between(awokeTime, currentTime);
//            long hours = durationDifferenceTime.toHours();
//            long minutes = durationDifferenceTime.toMinutes() - (hours * 60); // вычитаем минуты, учтенные как часы
//            String stringDifferenceTime = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
//            SharedPreferencesUtils.saveDifferenceTime(MainScreenActivity.this, stringDifferenceTime); // записываем в SharedPreference
//
//            // переменная для вывода
//            if (hours != 0) {
//                stringDifferenceTimeView =
//                        hours + model.correctWordHours(hours) + " " + minutes + model.correctWordMinutes(minutes);
//            } else {
//                stringDifferenceTimeView = minutes + model.correctWordMinutes(minutes);
//            }
//        }
//        return stringDifferenceTimeView;
//    }


    private void updateTextViewMainScreen() {
//        String wakingTime = timeSinceLastSleep();
        String time = SharedPreferencesUtils.getKeyWakingTime(MainScreenActivity.this);
        String wakingTime = model.timeSinceLastSleep(time, MainScreenActivity.this);

        if (wakingTime != null) {
            String wakingTimeResult = "Ваш малыш не спит:\n" + wakingTime;
            textViewMainScreen.setText(wakingTimeResult);
        }
    }


    private void addListenerOnButton() {
        Model model = new Model();
        sleep_button = findViewById(R.id.sleep_button);
        diary_button = findViewById(R.id.diary_button);
        assistant_button = findViewById(R.id.assistant_button);
        button_show_popup_menu = findViewById(R.id.button_show_popup_menu);

        sleep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainScreenActivity.this, SleepActivity.class
                );
                startActivity(intent);
            }
        });

        diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainScreenActivity.this, DiaryActivity.class
                );
                startActivity(intent);
            }
        });

        assistant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, Assistant.class);
                startActivity(intent);
            }
        });

        button_show_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Category> categories = new ArrayList<>();
                categories.add(new Category(2,"Профиль малыша", ChildrenProfileActivity.class));
                categories.add(new Category(3, "Профиль мамы", MomProfileActivity.class));
                categories.add(new Category(4,"Давай поиграем", GamesActivity.class));
                categories.add(new Category(5,"Сон", SleepActivity.class));
                categories.add(new Category(6,"Полезные статьи", ArticlesActivity.class));
                categories.add(new Category(7,"Дневник малыша", DiaryActivity.class));
                categories.add(new Category(8,"Статистика сна", SleepStatistics.class));

                model.showPopupMenu(MainScreenActivity.this,view,categories);
            }
        });
    }

    // Запрос на отправку уведомлений
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Разрешение на отправку уведомлений")
                .setMessage("Вы хотите предоставить разрешение на отправку уведомлений, это " +
                        "необходимо для выполнения важных функций приложения?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openNotificationSettings();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }

    private void openNotificationSettings() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }

}