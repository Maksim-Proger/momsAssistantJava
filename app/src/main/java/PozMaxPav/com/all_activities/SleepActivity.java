package PozMaxPav.com.all_activities;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.database.AppDatabase;
import PozMaxPav.com.model.database.MyApp;
import PozMaxPav.com.model.database.User;
import PozMaxPav.com.model.database.UserDao;
import PozMaxPav.com.model.helperClasses.NotificationClass;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.helperClasses.TimerService;
import PozMaxPav.com.model.helperClasses.addNewTime.AddTimeLogic;

public class SleepActivity extends BaseActivity implements AddTimeLogic.ListenerInterface {

    private AppDatabase appDatabase;
    private String fellAsleepString, wokeUpString;
    private Button fellAsleep,wokeUp,statistics,back_button_sleep,pause,cont,addButton;
    private TextView fellAsleepView,wokeUpView,resultSleep,testtesttets,text_view_timeSinceLastSleep;
    private TextView timer;
    private LocalBroadcastManager localBroadcastManager;
    private NotificationClass notificationClass;
    private AddTimeLogic addTimeLogic;
    private int selectedTimeFlag = 1; // Флаг для отслеживания выбора времени (1 или 2)
    private String firstSelectedTime = "";
    private String secondSelectedTime = "";
    private Handler handler = new Handler();
    private static final long DELAY = 1000;


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

        // выводи сохраненную переменную
        fellAsleepView = findViewById(R.id.fellAsleepView);
        String returnAsleep = SharedPreferencesUtils.getKeyAsleep(this);
        if (returnAsleep != null) {
            String newReturnAsleep = "Заснул: " + returnAsleep;
            fellAsleepView.setText(newReturnAsleep);
        }

        // время бодровствования
        text_view_timeSinceLastSleep = findViewById(R.id.text_view_timeSinceLastSleep);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String wakingTime = SharedPreferencesUtils.getKeyDifferenceTime(SleepActivity.this);
                if (wakingTime != null) {
                    String wakingTimeResult = "Ваш малыш не спит:\n" + wakingTime;
                    text_view_timeSinceLastSleep.setText(wakingTimeResult);
                    handler.postDelayed(this, DELAY);
                }
            }
        }, DELAY);

        // Создаем экземпляр AddTimeLogic
        addTimeLogic = new AddTimeLogic(SleepActivity.this,SleepActivity.this);
        testtesttets = findViewById(R.id.testtesttets);

        // Создаем экземпляр NotificationClass при инициализации Activity
        notificationClass = new NotificationClass(this);

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
        wokeUpView = findViewById(R.id.wokeUpView);
        resultSleep = findViewById(R.id.resultSleep);
        addButton = findViewById(R.id.addButton);

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

                // Сохраняем fellAsleepString в SharedPreferences
                SharedPreferencesUtils.saveKeyAsleep(SleepActivity.this, fellAsleepString);

                // Очищаем значения переменных wakingTime и differenceTime
                SharedPreferencesUtils.removeWakingTime(SleepActivity.this);
                SharedPreferencesUtils.removeDifferenceTime(SleepActivity.this);

                // Создаем уведомление
                notificationClass.showNotification();

                // Тестируем определение времени поле вывода удалил
//                String testTime = fellAsleepString;
//                testClock.setText(model.checkTime(testTime));

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

                // записываем wokeUpString
                SharedPreferencesUtils.saveKeyAwoke(SleepActivity.this, wokeUpString);

                // записываем wokeUpString для бодрствования
                SharedPreferencesUtils.saveWakingTime(SleepActivity.this, wokeUpString);

                printSleepView2();

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Вызываем showTimePickerDialog для первого времени
                selectedTimeFlag = 1;
                addTimeLogic.showTimePickerDialog();
            }
        });
    }


    private void printSleepView2() {
        String asleep = SharedPreferencesUtils.getKeyAsleep(SleepActivity.this);
        String awoke = SharedPreferencesUtils.getKeyAwoke(SleepActivity.this);
        if (asleep != null && awoke != null) {
            // Выполняем вставку нового пользователя в базу данных в фоновом потоке
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String result = result();

                    // Получаем текущую дату как дату создания группы
                    String groupDate = getCurrentDate();

                    // Создаем нового пользователя и вставляем его в базу данных с уникальным id
                    User newUser = new User();
                    newUser.setDate(groupDate); // Устанавливаем дату создания группы
                    newUser.setSleep1(asleep);
                    newUser.setSleep2(awoke);
                    newUser.setSleep3(result);
                    insertOrUpdateUser(newUser);
                    resultSleep.setText(result);

                    // удаляем значения переменных asleep и awoke
                    SharedPreferencesUtils.removeAll(SleepActivity.this);
                }
            });
            executor.shutdown();
        }
    }

    private String result(){
        String first = SharedPreferencesUtils.getKeyAsleep(SleepActivity.this);
        String second = SharedPreferencesUtils.getKeyAwoke(SleepActivity.this);

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

    @Override
    public void listenerMethod(String selectedTime) {
        // сохраняем первое время
        if (selectedTimeFlag == 1) {
            firstSelectedTime = selectedTime;
            // После выбора первого времени вызываем showTimePickerDialog для второго времени
            selectedTimeFlag = 2;
            addTimeLogic.showTimePickerDialog();
        } else {
            // Сохраняем второе время
            secondSelectedTime = selectedTime;
        }
        if (!firstSelectedTime.isEmpty() && !secondSelectedTime.isEmpty()) {
            saveNewSleep();
        }
    }

    // доработать этот метод
    public void saveNewSleep() {
        if (!firstSelectedTime.isEmpty() && !secondSelectedTime.isEmpty()) {
            LocalTime firstTime = LocalTime.parse(firstSelectedTime);
            LocalTime secondTime = LocalTime.parse(secondSelectedTime);
            long differenceInMinutes = ChronoUnit.MINUTES.between(firstTime, secondTime);
            String resultSelectedTime = String.valueOf(differenceInMinutes);

            if (firstTime != null && secondTime != null) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        User newUser = new User();
                        newUser.setDate(getCurrentDate());
                        newUser.setSleep1(firstSelectedTime);
                        newUser.setSleep2(secondSelectedTime);
                        newUser.setSleep3(resultSelectedTime);
                        insertOrUpdateUser(newUser);

                        // Сброс значений после сохранения
                        firstSelectedTime = "";
                        secondSelectedTime = "";
                    }
                });
            }
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

}