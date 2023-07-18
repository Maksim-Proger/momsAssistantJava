package PozMaxPav.com.all_activities;

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
import PozMaxPav.com.R;
import PozMaxPav.com.view.Controller;

public class SleepActivity extends AppCompatActivity {

    private String fellAsleepString, wokeUpString;
    private Button fellAsleep,wokeUp;
    private TextView viewSleep;
    private Chronometer chronometer; // Декларируем Chronometer

    private ArrayList<String> resultArray = new ArrayList<>();
    private ArrayList<String> beautyResultArray = new ArrayList<>();

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
        viewSleep = (TextView)findViewById(R.id.viewSleep);

        fellAsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fellAsleepString = controller.fixTime();
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
                printSleepView2(wokeUpString);

                // Останавливаем Chronometer
                chronometer.stop();

                // Делаем Chronometer невидимым после остановки
                chronometer.setVisibility(View.GONE);
            }
        });
    }


    private void printSleepView(String result) {
        resultArray.add(result);

        String newResult = "Заснул: " + result;
        beautyResultArray.add(newResult);
        String fff = beautyResultArray.toString().replace("[", "").
                replace("]", "").replace(",", "");
        viewSleep.setText(fff);
    }


    private void printSleepView2(String result) {
        resultArray.add(result);

        String newResult = "Проснулся: " + result;
        beautyResultArray.add("\n");
        beautyResultArray.add(newResult);
        viewSleep.setText(beautyResultArray.toString());
        result();
    }


    private void result(){
        String first = resultArray.get(0);
        String second = resultArray.get(1);

        LocalTime time1 = LocalTime.parse(first);
        LocalTime time2 = LocalTime.parse(second);

        long differenceInMinutes = ChronoUnit.MINUTES.between(time1, time2);
        String differenceAsString = String.valueOf(differenceInMinutes);
        String newResult = "Спал: " + differenceAsString + " минут";
        beautyResultArray.add("\n");
        beautyResultArray.add(newResult);
        String fff = beautyResultArray.toString().replace("[", "").
                replace("]", "").replace(",", "");
        viewSleep.setText(fff);
        resultArray.clear();
        beautyResultArray.clear();
    }

}
