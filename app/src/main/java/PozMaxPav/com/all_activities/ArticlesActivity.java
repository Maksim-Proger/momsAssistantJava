package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class ArticlesActivity extends AppCompatActivity {

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_articles);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
