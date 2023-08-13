package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.users.Mums;

public class RegistrationActivity extends AppCompatActivity {

    private Button back_button,buttonReg;
    private EditText editName,editSurname,editPatronymic,editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        back_button = findViewById(R.id.back_button);
        buttonReg = findViewById(R.id.buttonReg);

        editName = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        editPatronymic = findViewById(R.id.editPatronymic);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = String.valueOf(editName.getText());
                String surname = String.valueOf(editName.getText());
                String patronymic = String.valueOf(editName.getText());
                String email = String.valueOf(editName.getText());
                String password = String.valueOf(editName.getText());
                
                Mums mum = new Mums(name,surname,patronymic,email,password);
            }
        });

    }
}
