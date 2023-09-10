package PozMaxPav.com.all_activities.games;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.LogicForTicTacToe;

public class TicTacToeActivity extends AppCompatActivity {

    private final LogicForTicTacToe logic = new LogicForTicTacToe(this);
    private Button back_button;
    private Button[] buttons = new Button[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lotteryDialog();
        findButtons();

    }

    private void findButtons() {
        for (int i = 0; i < buttons.length; i++) {
            int buttonId = getResources().getIdentifier("bt" + (i + 1), "id",
                    getPackageName());
            buttons[i] = findViewById(buttonId);
            logic.addNewButton(buttons[i]);
        }
    }

    private void playGame() {

    }


    private void lotteryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Сейчас мы разыграем кто будет ходить первым")
                .setPositiveButton("Орёл", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        runLottery("Орёл");
                    }
                })
                .setNegativeButton("Решка", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        runLottery("Решка");
                    }
                })
                .show();
    }

    /**
     *  метод runLotteryInBackground выполняет лотерею в фоновом потоке
     */
    private void runLottery(final String choice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = logic.isLottery(choice);
                lotteryResult(result);
            }
        }).start();
    }

    /**
     * отображает результат в главном потоке с помощью runOnUiThread
     * @param result
     */
    private void lotteryResult(final boolean result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    Toast.makeText(TicTacToeActivity.this, "Ура! Вы ходите (Х). ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TicTacToeActivity.this, "Вы ходите (0).", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void playerMove() {
        // Обработчик нажатия на кнопку
        for (Button button: logic.idButton) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logic.playerMove(button);
                }
            });
        }
    }

    private void botMove() {

    }




}
