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
import java.util.HashMap;

import PozMaxPav.com.R;
import PozMaxPav.com.view.Controller;

public class SleepActivity extends AppCompatActivity {

    private String fellAsleepString, wokeUpString;
    private Button fellAsleep,wokeUp,statistics,back_button_sleep;
    private TextView fellAsleepView,wokeUpView,resultSleep,test;
    private Chronometer chronometer; // Декларируем Chronometer
    private final ArrayList<String> resultArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);
        chronometer = findViewById(R.id.chronometer);
        addListenerOnButton();
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
        if (resultArray.size() >= 2){
            result();
        }
    }


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
