package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class ArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_articles);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        Button back_button = findViewById(R.id.back_button);
        Button back_to_home = findViewById(R.id.back_to_home);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticlesActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });


    }
}
