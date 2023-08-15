package PozMaxPav.com.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PozMaxPav.com.all_activities.LoginActivity;
import PozMaxPav.com.all_activities.MainScreenActivity;
import PozMaxPav.com.all_activities.RegistrationActivity;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.mainmenu.Category;

public class Model {

    protected ArrayList<String> answerOptions = new ArrayList<>();
    protected ArrayList<String> questionOptions = new ArrayList<>();
    protected ArrayList<String> answerOptions2 = new ArrayList<>();
    protected ArrayList<String> answerOptions3 = new ArrayList<>();
    protected ArrayList<String> answerOptions4 = new ArrayList<>();

    public void showPopupMenu(Context context, View view, ArrayList<Category> categories){

        PopupMenu popupMenu = new PopupMenu(context, view);

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            popupMenu.getMenu().add(Menu.NONE, category.getId(), i, category.getTitle());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                for (Category category : categories) {
                    if (itemId == category.getId()) {
                        Intent intent = new Intent(context, category.getActivityClass());
                        context.startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });

        popupMenu.show();
    }

    public String fixTime() {
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dateNow);
    }

    public void fillingArrayListAnswer() {
        answerOptions.add("Привет");
        answerOptions.add("Рад вас видеть");
        answerOptions.add("И вам привет");
        answerOptions.add("Здравствуйте");

    }

    public void fillingArrayListQuestion() {
        questionOptions.add("Привет");
        questionOptions.add("Привед");
        questionOptions.add("привет");
        questionOptions.add("привед");
        questionOptions.add("Привет.");
        questionOptions.add("Привет ");
        questionOptions.add("привет ");
    }

    public String assistantMethod(String question) {

        fillingArrayListAnswer();
        fillingArrayListQuestion();
        Random random = new Random();
        String name = "MaksBot";

        if (questionOptions.contains(question)) {
            int num = random.nextInt(answerOptions.size());
            return answerOptions.get(num);
        } if (question.equals("Как тебя зовут?") || question.equals("как тебя зовут?")) {
            return String.format("%s %s", "Меня зовут", name);
        }
        return "Пока я не могу ответить вам на этот вопрос. Попробуйте его переформулировать.";
    }

    public String inputValidation(Context context, String email, String password) {
        String emailSharedPreferences = SharedPreferencesUtils.getKeyEmail(context);
        String passwordSharedPreferencesUtils = SharedPreferencesUtils.getKeyPassword(context);

        if (emailSharedPreferences != null && passwordSharedPreferencesUtils != null)
            if (emailSharedPreferences.equals(email) && passwordSharedPreferencesUtils.equals(password)) {
                return "Вход выполнен";
            } else {
                return "Неверный логин или пароль";
            }
        else {
            return "Пользователь не зарегистрирован";
        }
    }

    public String checkValidation(String name, String surname, String patronymic, String email, String password) {

        boolean isValid = isValidEmail(email);

        if (name.equals("")) {
            return "Введите имя пользователя";
        } else if (surname.equals("")) {
            return "Введите фамилию пользователя";
        } else if (patronymic.equals("")) {
            return "Введите отчество пользователя";
        } else if (email.equals("")) {
            return "Введите email";
        } else if (!isValid) {
            return "Введен некоректный email";
        } else if (password.equals("")) {
            return "Введите пароль";
        }
        return "Пользователь зарегистрирован!";
    }

    public static boolean isValidEmail(String email) {
        // Шаблон для проверки email
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}


























