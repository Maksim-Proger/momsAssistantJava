package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class GamesActivity extends AppCompatActivity {

    private Button back_button_games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_games);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        back_button_games = findViewById(R.id.back_button_games);

        back_button_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
