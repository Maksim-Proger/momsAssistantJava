package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class SleepStatistics extends AppCompatActivity {

    private Button back_button_statistics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleepstatistics);

        addListenerOnButton();
    }

    private void addListenerOnButton() {

        back_button_statistics = findViewById(R.id.back_button_statistics);

        back_button_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
