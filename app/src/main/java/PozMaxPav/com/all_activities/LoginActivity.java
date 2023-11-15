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

public class LoginActivity extends AppCompatActivity {

    private Button enter, register;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        // Проверка наличия логина и пароля в SharedPreferences
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
        boolean hasLoginAndPassword = sharedPreferencesUtils.hasLoginAndPassword(this);

        if (hasLoginAndPassword && sharedPreferencesUtils.isLoggedIn(this)) {
            startMainScreen();
            return;
        }

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        enter = (Button)findViewById(R.id.enter);
        register = (Button)findViewById(R.id.register);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model model = new Model();
                String ourEmail = String.valueOf(email.getText());
                String ourPassword = String.valueOf(password.getText());
                String result = model.inputValidation(LoginActivity.this, ourEmail, ourPassword);

                if (result.equals("Вход выполнен")) {
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.setLoggedIn(LoginActivity.this, true);
                    startMainScreen();
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

    private void startMainScreen() {
        Intent intent = new Intent(LoginActivity.this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
