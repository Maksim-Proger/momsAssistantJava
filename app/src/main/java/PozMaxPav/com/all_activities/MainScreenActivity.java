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

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.mainmenu.Category;

public class MainScreenActivity extends BaseActivity {

    private ImageButton sleep_button, diary_button, assistant_button, button_show_popup_menu;
    private TextView fieldName, textViewMainScreen;
    private Handler handler = new Handler();
    private static final long DELAY = 5000;

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

        // время бодровствования
        textViewMainScreen = findViewById(R.id.textViewMainScreen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTextViewMainScreen();
                handler.postDelayed(this, DELAY);
            }
        }, DELAY);

        addListenerOnButton();
    }


    private void timeSinceLastSleep() {
        String time = SharedPreferencesUtils.getKeyWakingTime(MainScreenActivity.this);
        if (time != null) {
            LocalTime awokeTime = LocalTime.parse(time);
            LocalTime localTime = LocalTime.now();
            long differenceTime = ChronoUnit.MINUTES.between(awokeTime, localTime);

            String stringDifferenceTime = String.valueOf(differenceTime);
            SharedPreferencesUtils.saveDifferenceTime(MainScreenActivity.this, stringDifferenceTime);
        }
    }


    private void updateTextViewMainScreen() {
        timeSinceLastSleep();
        String wakingTime = SharedPreferencesUtils.getKeyDifferenceTime(MainScreenActivity.this);
        if (wakingTime != null) {
            String string = wakingTime + " мин";
            textViewMainScreen.setText(string);
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