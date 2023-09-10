package PozMaxPav.com.all_activities;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.database.AppDatabase;
import PozMaxPav.com.model.database.MyApp;
import PozMaxPav.com.model.database.User;
import PozMaxPav.com.model.database.UserDao;
import PozMaxPav.com.model.helperClasses.TimerService;

public class SleepActivity extends AppCompatActivity {

    private AppDatabase appDatabase; // объявляем переменную
    private String fellAsleepString, wokeUpString;
    private Button fellAsleep,wokeUp,statistics,back_button_sleep,pause,cont;
    private TextView fellAsleepView,wokeUpView,resultSleep,testClock;
    private TextView timer;
    private LocalBroadcastManager localBroadcastManager;
    private final ArrayList<String> resultArray = new ArrayList<>();

    // Регистрируем BroadcastReceiver для обновления времени из сервиса
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long elapsedMillis = intent.getLongExtra("elapsedMillis", 0);
            updateTimer(elapsedMillis);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);

        timer = findViewById(R.id.timer);

        // Инициализируем экземляр базы данных
        appDatabase = ((MyApp) getApplication()).getAppDatabase();

        // Получаем экземпляр базы данных с помощбю метода getInstance
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        // Инициализируем LocalBroadcastManager
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        addListenerOnButton();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        localBroadcastManager.registerReceiver(broadcastReceiver,
                new IntentFilter("UPDATE_TIME"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void insertOrUpdateUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(new InsertOrUpdateUserCalleble(user));
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    public class InsertOrUpdateUserCalleble implements Callable<Void> {
        private User user;
        public InsertOrUpdateUserCalleble(User user) {
            this.user = user;
        }
        @Override
        public Void call() throws Exception {
            // Получаем DAO для работы с таблицей пользователей
            UserDao userDao = appDatabase.getUserDao();
            // Получаем пользователя с указанным ID из базы данных (если существует)
            User existingUser = userDao.getUserById(user.getId());

            if (existingUser != null) {
                // Если пользователь существует, обновляем его данные
                userDao.updateUser(user);
            } else {
                // Если пользователь не существует, вставляем его в базу данных
                userDao.insertUser(user);
            }
            return null;
        }
    }


    private void addListenerOnButton() {
        Model model = new Model();
        fellAsleep = findViewById(R.id.fellAsleep);
        wokeUp = findViewById(R.id.wokeUp);
        statistics = findViewById(R.id.statistics);
        back_button_sleep = findViewById(R.id.back_button_sleep);
        pause = findViewById(R.id.pause);
        cont = findViewById(R.id.cont);
        fellAsleepView = findViewById(R.id.fellAsleepView);
        wokeUpView = findViewById(R.id.wokeUpView);
        resultSleep = findViewById(R.id.resultSleep);
        testClock = findViewById(R.id.testClock);

        back_button_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SleepActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });

        fellAsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fellAsleepView.setText(""); // очищаем поле для повторного нажатия
                wokeUpView.setText(""); // очищаем поле для повторного нажатия
                resultSleep.setText(""); // очищаем поле для повторного нажатия

                fellAsleepString = model.fixTime();
                String string = "Заснул: " + fellAsleepString;
                fellAsleepView.setText(string);



                // Тестируем определение времени
                String testTime = fellAsleepString;
                testClock.setText(model.checkTime(testTime));



                printSleepView(fellAsleepString);

                // Запускаем секундомер
                timer.setVisibility(View.VISIBLE);
                Intent serviceIntent = new Intent(SleepActivity.this, TimerService.class);
                serviceIntent.setAction(TimerService.ACTION_START);
                startService(serviceIntent);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Приостанавливаем работу секундомера
                Intent serviceIntent = new Intent(SleepActivity.this, TimerService.class);
                serviceIntent.setAction(TimerService.ACTION_PAUSE);
                startService(serviceIntent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Продолжаем работу секундомера
                Intent serviceIntent = new Intent(SleepActivity.this, TimerService.class);
                serviceIntent.setAction(TimerService.ACTION_RESUME);
                startService(serviceIntent);
            }
        });

        wokeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wokeUpString = model.fixTime();
                String string = "Проснулся: " + wokeUpString;
                wokeUpView.setText(string);

                printSleepView2(wokeUpString);

                // Останавливаем секундомер
                timer.setVisibility(View.GONE);
                Intent serviceIntent = new Intent(SleepActivity.this, TimerService.class);
                stopService(serviceIntent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        SleepActivity.this, SleepStatistics.class
                );
                startActivity(intent);
            }
        });
    }


    private void printSleepView(String first) {
        resultArray.add(first);
    }


    private void printSleepView2(String second) {
        resultArray.add(second);
        if (resultArray.size() >= 2) {
            // Выполняем вставку нового пользователя в базу данных в фоновом потоке
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String first = resultArray.get(0);
                    String second = resultArray.get(1);
                    String result = result();

                    // Получаем DAO для работы с таблицей пользователей
                    UserDao userDao = appDatabase.getUserDao();

                    // Получаем список всех пользователей из базы данных
                    List<User> users = userDao.getAllUsers();

                    // Создаем нового пользователя и вставляем его в базу данных с уникальным id
                    User newUser = new User();
                    newUser.setId(users.size() + 1); // Устанавливаем уникальный id
                    newUser.setSleep1(first);
                    newUser.setSleep2(second);
                    newUser.setSleep3(result);
                    insertOrUpdateUser(newUser);
                    resultSleep.setText(result);

                    // Очищаем список после обработки
                    resultArray.clear();
                }
            });
            executor.shutdown();
        }
    }


    private String result(){
        String first = resultArray.get(0);
        String second = resultArray.get(1);

        LocalTime time1 = LocalTime.parse(first);
        LocalTime time2 = LocalTime.parse(second);

        long differenceInMinutes = ChronoUnit.MINUTES.between(time1, time2);
        return String.valueOf(differenceInMinutes);
    }

    private void updateTimer(long elapsedMillis) {
        int seconds = (int) (elapsedMillis / 1000); // Переводим миллисекунды в секунды
        int minutes = seconds / 60; // Получаем количество минут
        int hours = minutes / 60; // Получаем количество часов
        seconds %= 60; // Получаем количество секунд, оставшихся после вычитания минут
        minutes %= 60; // Получаем количество минут, оставшихся после вычитания часов
        String time = String.format(
                Locale.getDefault(), "%02d:%02d:%02d",
                hours, minutes, seconds); // Форматируем время в "чч:мм:сс"
        timer.setText(time); // Устанавливаем отформатированное время в TextView
    }

}