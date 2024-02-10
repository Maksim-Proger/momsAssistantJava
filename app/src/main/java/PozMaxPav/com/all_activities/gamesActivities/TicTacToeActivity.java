package PozMaxPav.com.all_activities.gamesActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import PozMaxPav.com.R;
import PozMaxPav.com.all_activities.MainScreenActivity;
import PozMaxPav.com.model.logicForGames.LogicForTicTacToe;

public class TicTacToeActivity extends AppCompatActivity {

    private final Button[] buttons = new Button[25];
    private boolean gameOver = false;
    private final LogicForTicTacToe logic = new LogicForTicTacToe(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        fillArrayButtons();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                playGame();
            }
        });
        thread.start();

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
                Intent intent = new Intent(TicTacToeActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });

    }


    /**
     * Находим все наши кнопки и заполняем ArrayList
     */
    private void fillArrayButtons(){
        for (int i = 0; i < buttons.length; i++) {
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("bt" + (i + 1), "id", getPackageName());
            buttons[i] = findViewById(buttonId);
            logic.addNewButton(buttons[i]);
        }
    }

    private void playGame() {
        while (!gameOver) {
            boolean win = logic.checkWin();
            player();
            gameOver = logic.checkState();
            if (gameOver) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TicTacToeActivity.this, "Ничья!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

            if (win) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TicTacToeActivity.this, "Победа!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

            bot();
            gameOver = logic.checkState();
        }
    }

    private void player() {
        for (final Button button: logic.idButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logic.playerLogic(button);
                }
            });
        }
    }

    private void bot() {
        logic.botLogic();
    }
}
