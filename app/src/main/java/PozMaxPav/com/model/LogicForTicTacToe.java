package PozMaxPav.com.model;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class LogicForTicTacToe {

    public ArrayList<Button> idButtons = new ArrayList<>();
    private final Random random = new Random();
    private final String CROSS_SIGN = "X";
    private final String ZERO_SIGN = "0";
    private Context context;
    private boolean isPlayerTurn = true;

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void togglePlayerTurn() {
        isPlayerTurn = !isPlayerTurn;
    }

    public LogicForTicTacToe(Context context) {
        this.context = context;
    }

    public void addNewButton(Button button) {
        idButtons.add(button);
    }

    public void playerLogic(Button button) {
        if (isPlayerTurn) {
            if (idButtons.contains(button)) {
                if (button.getText().toString().isEmpty()) {
                    button.setText(CROSS_SIGN);
                    togglePlayerTurn();
                } else {
                    Toast.makeText(context, "Это поле уже занято!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void botLogic() {
        if (!isPlayerTurn) {
            int choice = random.nextInt(idButtons.size());
            Button button = idButtons.get(choice);
            if (button.getText().toString().isEmpty()) {
                button.setText(ZERO_SIGN);
                togglePlayerTurn();
            }
        }
    }

    public boolean checkState() {
        for (Button button : idButtons) {
            if (button.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWin() {

        int rows = 5; // кол-во строк
        int columns = 5; // кол-во столбцов
        int winCombination = 4; // выигрышная комбинация

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column <= columns - winCombination; column++) {
                boolean isWin = true;
                for (int i = 0; i < winCombination; i++) {
                    if (!idButtons.get(row * rows + column + i).getText().toString().equals(CROSS_SIGN)) {
                        isWin = false;
                        break;
                    }
                }
                if (isWin) {
                    return true;
                }
            }
        }

        return false;
    }

}
