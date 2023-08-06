package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import PozMaxPav.com.R;
import PozMaxPav.com.all_activities.database.AppDatabase;
import PozMaxPav.com.all_activities.database.MyApp;
import PozMaxPav.com.all_activities.database.User;
import PozMaxPav.com.all_activities.database.UserDao;
import PozMaxPav.com.view.Controller;

public class SleepActivity extends AppCompatActivity {

    private AppDatabase appDatabase; // объявляем переменную
    private String fellAsleepString, wokeUpString;
    private Button fellAsleep,wokeUp,statistics,back_button_sleep;
    private TextView fellAsleepView,wokeUpView,resultSleep,test;
    private Chronometer chronometer; // Декларируем Chronometer
    private final ArrayList<String> resultArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);

        // Инициализируем экземляр базы данных
        appDatabase = ((MyApp) getApplication()).getAppDatabase();

        // Получаем экземпляр базы данных с помощбю метода getInstance
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        chronometer = findViewById(R.id.chronometer);
        addListenerOnButton();
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
        Controller controller = new Controller();
        fellAsleep = findViewById(R.id.fellAsleep);
        wokeUp = findViewById(R.id.wokeUp);
        statistics = findViewById(R.id.statistics);
        back_button_sleep = findViewById(R.id.back_button_sleep);
        fellAsleepView = findViewById(R.id.fellAsleepView);
        wokeUpView = findViewById(R.id.wokeUpView);
        resultSleep = findViewById(R.id.resultSleep);

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

                fellAsleepString = controller.fixTime();
                String string = "Заснул: " + fellAsleepString;
                fellAsleepView.setText(string);

                printSleepView(fellAsleepString);

                // Запускаем Chronometer
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                // Устанавливаем Chronometer видимым
                chronometer.setVisibility(View.VISIBLE);
            }
        });


        wokeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wokeUpString = controller.fixTime();
                String string = "Проснулся: " + wokeUpString;
                wokeUpView.setText(string);

                printSleepView2(wokeUpString);

                // Останавливаем Chronometer
                chronometer.stop();
                // Делаем Chronometer невидимым после остановки
                chronometer.setVisibility(View.GONE);
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


    private void printSleepView(String result) {
        resultArray.add(result);
    }


    private void printSleepView2(String result) {
        resultArray.add(result);
        if (resultArray.size() >= 2) {
            // Выполняем вставку нового пользователя в базу данных в фоновом потоке
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String first = resultArray.get(0);
                    String second = resultArray.get(1);
//                    String result = result();

                    // Получаем DAO для работы с таблицей пользователей
                    UserDao userDao = appDatabase.getUserDao();

                    // Получаем список всех пользователей из базы данных
                    List<User> users = userDao.getAllUsers();

                    // Создаем нового пользователя и вставляем его в базу данных с уникальным id
                    User newUser = new User();
                    newUser.setId(users.size() + 1); // Устанавливаем уникальный id
                    newUser.setSleep1(first);
                    newUser.setSleep2(second);
//                    newUser.setSleep3(result);
                    insertOrUpdateUser(newUser);

                    result();
//                    resultSleep.setText(result);

                    // Очищаем список после обработки
                    resultArray.clear();
                }
            });
            executor.shutdown();
        }
    }


//    private String result(){
//        String first = resultArray.get(0);
//        String second = resultArray.get(1);
//
//        LocalTime time1 = LocalTime.parse(first);
//        LocalTime time2 = LocalTime.parse(second);
//
//        long differenceInMinutes = ChronoUnit.MINUTES.between(time1, time2);
//        return String.valueOf(differenceInMinutes);
//    }



    private void result(){

        String first = resultArray.get(0);
        String second = resultArray.get(1);

        LocalTime time1 = LocalTime.parse(first);
        LocalTime time2 = LocalTime.parse(second);

        long differenceInMinutes = ChronoUnit.MINUTES.between(time1, time2);
        String differenceAsString = String.valueOf(differenceInMinutes);
        String string = "Спал: " + differenceAsString + " минут";
        resultSleep.setText(string);

        resultArray.clear();

    }

}
