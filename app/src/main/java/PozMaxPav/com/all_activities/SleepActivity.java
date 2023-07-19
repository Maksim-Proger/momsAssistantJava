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
    private Button fellAsleep,wokeUp,statistics;
    private TextView fellAsleepView,wokeUpView,resultSleep,test;
    private Chronometer chronometer; // Декларируем Chronometer
    private final ArrayList<String> resultArray = new ArrayList<>();
    private HashMap<Integer, ArrayList<String>> hashMap = new HashMap<>();
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        addListenerOnButton();
    }


    private void addListenerOnButton() {
        Controller controller = new Controller();
        fellAsleep = (Button)findViewById(R.id.fellAsleep);
        wokeUp = (Button)findViewById(R.id.wokeUp);
        statistics = (Button)findViewById(R.id.statistics);
        fellAsleepView = (TextView)findViewById(R.id.fellAsleepView);
        wokeUpView = (TextView)findViewById(R.id.wokeUpView);
        resultSleep = (TextView)findViewById(R.id.resultSleep);

        fellAsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        result();
    }

    private void result(){

        //записываем данные в наш HashMap
        test = (TextView)findViewById(R.id.test);
        hashMap.put(num,resultArray);
        num++;
        test.setText(hashMap.toString());




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
