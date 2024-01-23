package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.helperClasses.sharedPreference.SharedPreferencesUtils;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editName,editSurname,editPatronymic,editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        Button back_button = findViewById(R.id.back_button);
        Button buttonReg = findViewById(R.id.buttonReg);
        Button back_to_home = findViewById(R.id.back_to_home);

        editName = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        editPatronymic = findViewById(R.id.editPatronymic);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Model model = new Model();

                String name = String.valueOf(editName.getText());
                String surname = String.valueOf(editSurname.getText());
                String patronymic = String.valueOf(editPatronymic.getText());
                String email = String.valueOf(editEmail.getText());
                String password = String.valueOf(editPassword.getText());

                String result = model.checkValidation(name,surname,patronymic,email,password);
                
//                Mums mum = new Mums(name,surname,patronymic,email,password); // пока не задействовал, НО надо !!!

                if (result.equals("Пользователь зарегистрирован!")) {

                    // Сохраняем данные пользователя
                    saveCredentials(name,surname,patronymic,email,password);

                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Метод сохранения данных пользователя
    private void saveCredentials(String name, String surname,
                                 String patronymic, String email, String password) {
        SharedPreferencesUtils.saveCredentials(this, name,
                surname, patronymic, email, password);
    }
}
