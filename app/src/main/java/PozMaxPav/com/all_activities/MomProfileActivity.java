package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.sharedPreference.SharedPreferencesUtils;

public class MomProfileActivity extends BaseActivity {

    private Button back_button, change_profile_image_button;
    private EditText editName,editSurname,editPatronymic,editEmail,editPassword;

    private ImageView profile_image;
    final int SELECT_IMAGE_CODE = 1;

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

        change_profile_image_button = findViewById(R.id.change_profile_image_button);
        profile_image = findViewById(R.id.profile_image);
        change_profile_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Добавить фотографию"), SELECT_IMAGE_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                ImageView profile_image = findViewById(R.id.profile_image);
                profile_image.setImageURI(imageUri);
            }
        }
    }
}
