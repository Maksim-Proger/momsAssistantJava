package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;

public class MomProfileActivity extends BaseActivity {

    private Button back_button;
    private EditText editName,editSurname,editPatronymic,editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momprofile);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        back_button = findViewById(R.id.back_button);
        // Находим наши Edit поля
        editName = findViewById(R.id.editName);
        String name = SharedPreferencesUtils.getKeyName(this);
        if (name != null) {
            editName.setText(name);
        }

        editSurname = findViewById(R.id.editSurname);
        String surname = SharedPreferencesUtils.getKeySurname(this);
        if (surname != null) {
            editSurname.setText(surname);
        }

        editPatronymic = findViewById(R.id.editPatronymic);
        String patronymic = SharedPreferencesUtils.getKeyPatronymic(this);
        if (patronymic != null) {
            editPatronymic.setText(patronymic);
        }

        editEmail = findViewById(R.id.editEmail);
        String email = SharedPreferencesUtils.getKeyEmail(this);
        if (email != null) {
            editEmail.setText(email);
        }

        editPassword = findViewById(R.id.editPassword);
        String password = SharedPreferencesUtils.getKeyPassword(this);
        if (password != null) {
            editPassword.setText(password);
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
