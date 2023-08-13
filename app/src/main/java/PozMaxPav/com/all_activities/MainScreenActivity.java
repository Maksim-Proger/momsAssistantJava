package PozMaxPav.com.all_activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import PozMaxPav.com.R;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.mainmenu.Category;
import PozMaxPav.com.view.Controller;

public class MainScreenActivity extends AppCompatActivity {

    private Button sleep_button, diary_button, assistant_button, button_show_popup_menu;
    private TextView fieldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);


        // Получение и вывод имени пользователя и вывод в поле fieldName
        fieldName = findViewById(R.id.fieldName);
        String name = SharedPreferencesUtils.getKeyName(this);
        if (name != null) {
            fieldName.setText(name);
        }


        addListenerOnButton();
    }

    private void addListenerOnButton() {
        Controller controller = new Controller();
        sleep_button = (Button)findViewById(R.id.sleep_button);
        diary_button = (Button)findViewById(R.id.diary_button);
        assistant_button = (Button)findViewById(R.id.assistant_button);
        button_show_popup_menu = (Button)findViewById(R.id.button_show_popup_menu);

        sleep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainScreenActivity.this, SleepActivity.class
                );
                startActivity(intent);
            }
        });

        diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainScreenActivity.this, DiaryActivity.class
                );
                startActivity(intent);
            }
        });

        assistant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, Assistant.class);
                startActivity(intent);
            }
        });

        button_show_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Category> categories = new ArrayList<>();
                categories.add(new Category(1,"Добавить вашего малыша", ChildrenProfileActivity.class));
                categories.add(new Category(2,"Профиль малыша", ChildrenProfileActivity.class));
                categories.add(new Category(3,"Давай поиграем", GamesActivity.class));
                categories.add(new Category(4,"Сон", SleepActivity.class));
                categories.add(new Category(5,"Оставь тут свои заметки", NotesActivity.class));
//                categories.add(new Category(6,"Первый"));
//                categories.add(new Category(7,"Первый"));
//                categories.add(new Category(8,"Первый"));

                controller.menu(MainScreenActivity.this,view,categories);
            }
        });


    }

}