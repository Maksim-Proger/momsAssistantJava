package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;

public class Assistant extends AppCompatActivity {

    private TextView viewAssistant;
    private EditText editAssistant;
    private Button buttonAssistant,back_button_assistant;

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
        back_button_assistant = findViewById(R.id.back_button_assistant);

        // Инициализируем экземпляр класса Controller
        Model model = new Model();

        buttonAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(editAssistant.getText());
                viewAssistant.setText(model.assistantMethod(getApplicationContext(),text));
                editAssistant.setText("");
            }
        });

        back_button_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
