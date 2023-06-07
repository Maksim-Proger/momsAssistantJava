package PozMaxPav.com.all_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import PozMaxPav.com.R;

public class MainScreenActivity extends AppCompatActivity {

    private Button sleep_button, diary_button, assistant_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        addListenerOnButton();

    }

    private void addListenerOnButton() {
        sleep_button = (Button)findViewById(R.id.sleep_button);
        diary_button = (Button)findViewById(R.id.diary_button);
        assistant_button = (Button)findViewById(R.id.assistant_button);

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


    }

}