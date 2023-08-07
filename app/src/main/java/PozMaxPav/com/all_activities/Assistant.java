package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;

public class Assistant extends AppCompatActivity {

    private TextView viewAssistant;
    private EditText editAssistant;
    private Button buttonAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        viewAssistant = findViewById(R.id.viewAssistent);
        editAssistant = findViewById(R.id.editAssistent);
        buttonAssistant = findViewById(R.id.buttonAssistent);

        buttonAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(editAssistant.getText());
                String answer = "В данный момент я ничего не умею. Но я уверен, что в ближайшем " +
                        "будущем я стану очень умным. Так что приходи чуть позже)))";
                switch (text) {
                    case "Что ты умеешь?":
                    case "что ты умеешь?":
                        viewAssistant.setText(answer);
                        break;
                    case "Привет":
                    case "привет":
                        viewAssistant.setText("И тебе привет!");
                        break;
                    case "Как тебя зовут?":
                    case "как тебя зовут?":
                        viewAssistant.setText("Сложно сказать. Я очень многогранная личность.");
                        break;
                    case "Пока":
                    case "пока":
                        viewAssistant.setText("Пока. Я буду ждать тебя тут.");
                        break;
                }
                editAssistant.setText("");
            }
        });


    }
}
