package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.users.Mums;
import PozMaxPav.com.view.Controller;

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

                Controller controller = new Controller();

                String name = String.valueOf(editName.getText());
                String surname = String.valueOf(editSurname.getText());
                String patronymic = String.valueOf(editPatronymic.getText());
                String email = String.valueOf(editEmail.getText());
                String password = String.valueOf(editPassword.getText());

                String result = controller.checkValidation(name,surname,patronymic,email,password);
                
//                Mums mum = new Mums(name,surname,patronymic,email,password); // пока не задействовал, НО надо !!!

                if (result.equals("Пользователь зарегистрирован!")) {

                    // Сохраняем данные пользователя
                    saveCredentials(name,surname,patronymic,email,password);

                    Intent intent = new Intent(RegistrationActivity.this, MainScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveCredentials(String name, String surname,
                                 String patronymic, String email, String password) {
        SharedPreferencesUtils.saveCredentials(this, name,
                surname, patronymic, email, password);
    }
}
