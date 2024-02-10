package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import PozMaxPav.com.R;
import PozMaxPav.com.all_activities.gamesActivities.TicTacToeActivity;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_games);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        Button back_button = findViewById(R.id.back_button);
        Button back_to_home = findViewById(R.id.back_to_home);
        Button ticTac = findViewById(R.id.tictac);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GamesActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });

        ticTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesActivity.this, TicTacToeActivity.class);
                startActivity(intent);
            }
        });

    }
}
