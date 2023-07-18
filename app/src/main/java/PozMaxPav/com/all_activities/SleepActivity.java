package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import PozMaxPav.com.R;

public class SleepActivity extends AppCompatActivity {

    private Button fellAsleep,wokeUp;
    private TextView viewSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);
        addListenerOnButton();
    }


    private void addListenerOnButton() {
        fellAsleep = (Button)findViewById(R.id.fellAsleep);
        wokeUp = (Button)findViewById(R.id.wokeUp);
        viewSleep = (TextView)findViewById(R.id.viewSleep);

        fellAsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date firstData = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String currentTime = sdf.format(firstData);
                String newText = "Заснул в: " + currentTime;
                viewSleep.setText(newText);
            }
        });

        wokeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
