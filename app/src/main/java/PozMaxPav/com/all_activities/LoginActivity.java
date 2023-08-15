package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.view.Controller;

public class LoginActivity extends AppCompatActivity {

    private Button enter, register;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        enter = (Button)findViewById(R.id.enter);
        register = (Button)findViewById(R.id.register);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller controller = new Controller();
                String ourEmail = String.valueOf(email.getText());
                String ourPassword = String.valueOf(password.getText());
                String result = controller.inputValidation(LoginActivity.this, ourEmail, ourPassword);

                if (result.equals("Вход выполнен")){
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(
                            LoginActivity.this, MainScreenActivity.class
                    );
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        LoginActivity.this, RegistrationActivity.class
                );
                startActivity(intent);
            }
        });
    }

// Вспомогательный метод для очистки SharedPreferences
//    private void clear(){
//        SharedPreferencesUtils.clear(this);
//    }

}
