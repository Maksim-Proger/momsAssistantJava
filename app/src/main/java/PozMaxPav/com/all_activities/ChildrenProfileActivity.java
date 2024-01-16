package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.sharedPreference.SharedPreferencesUtils;

public class ChildrenProfileActivity extends AppCompatActivity {

    private EditText edit_name, edit_age, edit_weight, edit_height, edit_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_childrenprofile);

        edit_name = findViewById(R.id.edit_name);
        edit_age = findViewById(R.id.edit_age);
        edit_weight = findViewById(R.id.edit_weight);
        edit_height = findViewById(R.id.edit_height);
        edit_gender = findViewById(R.id.edit_gender);

        addListenerOnButton();
        showItemProfile();
    }

    private void addListenerOnButton() {
        Button back_button = findViewById(R.id.back_button);
        Button save_button = findViewById(R.id.save_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(edit_name.getText());
                String age = String.valueOf(edit_age.getText());
                String weight = String.valueOf(edit_weight.getText());
                String height = String.valueOf(edit_height.getText());
                String gender = String.valueOf(edit_gender.getText());
                SharedPreferencesUtils.saveChildrenProfile(
                        ChildrenProfileActivity.this,name,age,weight,height,gender);
            }
        });
    }

    private void showItemProfile() {
        String name = SharedPreferencesUtils.getKeyChildrenName(ChildrenProfileActivity.this);
        String age = SharedPreferencesUtils.getKeyChildrenAge(ChildrenProfileActivity.this);
        String weight = SharedPreferencesUtils.getKeyChildrenWeight(ChildrenProfileActivity.this);
        String height = SharedPreferencesUtils.getKeyChildrenHeight(ChildrenProfileActivity.this);
        String gender = SharedPreferencesUtils.getKeyChildrenGender(ChildrenProfileActivity.this);

        if (name != null) {
            edit_name.setText(name);
        }

        if (age != null) {
            edit_age.setText(age);
        }

        if (weight != null) {
            edit_weight.setText(weight);
        }

        if (height != null) {
            edit_height.setText(height);
        }

        if (gender != null) {
            edit_gender.setText(gender);
        }
    }
}
