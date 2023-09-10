package PozMaxPav.com.model;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class LogicForTicTacToe {

    public ArrayList<Button> idButton = new ArrayList<>();
    private final Random random = new Random();
    private final String CROSS_SIGN = "X";
    private final String ZERO_SIGN = "0";
    private Context context;

    public boolean lottery;

    public LogicForTicTacToe(Context context) {
        this.context = context;
    }

    public boolean isLottery(String choice) {
        String[] options = new String[] {"Орёл", "Решка"};
        int result = random.nextInt(options.length);

        if (options[result].equals(choice)) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewButton(Button button) {
        idButton.add(button);
    }

    public void botMove() {
        if (lottery) {
            int choiceBot = random.nextInt(idButton.size());
            Button button = idButton.get(choiceBot);
            if (button.getText().toString().isEmpty()) {
                button.setText(ZERO_SIGN);
            } else {
                button.setText(CROSS_SIGN);
            }
        }
    }

    public void playerMove(Button button) {
        if (lottery) {
            if (idButton.contains(button)) {
                button.setText(CROSS_SIGN);
            } else {
                button.setText(ZERO_SIGN);
            }
        }
    }

}
