package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import PozMaxPav.com.R;
import PozMaxPav.com.all_activities.gamesActivities.TicTacToeActivity;

public class GamesActivity extends AppCompatActivity {

    private Button back_button_games, ticTac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_games);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        back_button_games = findViewById(R.id.back_button_games);
        ticTac = findViewById(R.id.tictac);

        back_button_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
