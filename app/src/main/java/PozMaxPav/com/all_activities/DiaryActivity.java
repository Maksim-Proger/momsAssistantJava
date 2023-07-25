package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class DiaryActivity extends AppCompatActivity {

    private Button back_button_diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        back_button_diary = findViewById(R.id.back_button_diary);
        
        back_button_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        DiaryActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}
