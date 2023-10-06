package PozMaxPav.com.model;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import PozMaxPav.com.model.helperClasses.ReadBase;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;
import PozMaxPav.com.model.mainmenu.Category;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;

public class Model {

    protected ArrayList<String> answerOptions = new ArrayList<>();

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



    // разбираемся с фоновой работой уведомлений
    public boolean checkTimeLastSleep(String wakingTime) {
        int hours = 0;
        int minutes = 0;

        if (wakingTime != null && !wakingTime.isEmpty()) {
            LocalTime localTime = LocalTime.parse(wakingTime);
            hours = localTime.getHour();
            minutes = localTime.getMinute();
        }

        if (minutes > 1) {
            return true;
        } else {
            return false;
        }
    }





    public void fillingArrayListAnswer() {
        answerOptions.add("Привет!");
        answerOptions.add("Рад вас видеть!");
        answerOptions.add("И вам привет!");
        answerOptions.add("Здравствуйте!");
    }

    public String assistantMethod(Context context, String question) {
        ReadBase readBase = new ReadBase();
        fillingArrayListAnswer();
        Random random = new Random();
        String name = "MaksBot";

        // Создание токенизатора и стеммера
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        PorterStemmer stemmer = new PorterStemmer();

        // Преобразование запроса в нормализованные токены
        String[] tokens = tokenizer.tokenize(question);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = stemmer.stem(tokens[i]);
        }

        // Теперь можно сравнивать нормализованные токены с нормализованными ключевыми словами
        if (containsAnyNormalized(tokens, "привет", "здравствуй", "добрый", "день")) {
            int num = random.nextInt(answerOptions.size());
            return answerOptions.get(num);
        } else if (containsAnyNormalized(
                tokens, "имя","зовут","представься")) {
            return String.format("%s %s", "Меня зовут", name);
        } else if (containsAnyNormalized(
                tokens, "питание","питания","питании","диета","диеты")) {
            return readBase.read(context, "powerSupply.txt");
        } else if (containsAnyNormalized(
                tokens, "алкоголь","алкоголя","спиртное", "спиртных")) {
            return readBase.read(context, "alcohol.txt");
        } else if (containsAnyNormalized(
                tokens, "молоко","молоком","количество","увеличить")) {
            return readBase.read(context, "milkProblem.txt");
        } else if (containsAnyNormalized(
                tokens, "позы", "кормление","кормить")) {
            return readBase.read(context, "feedingPoses.txt");
        } else if (containsAnyNormalized(
                tokens, "режим","режимы")) {
            return readBase.read(context, "feedingMode.txt");
        } else if (containsAnyNormalized(tokens, "умеешь", "можешь")) {
            return "Я могу дать советы помогающие мамам справиться со сложностями в первые годы " +
                    "жизни. Например, я могу дать советы по вопросу грудного вскармливания. " +
                    "Рассказать про режимы и позы кормления или " +
                    "рассказать про необходимость диеты для мамы. Могу дать ответы на часто " +
                    "возникающие вопросы, например, можно ли маме выпивать алкоголь в небольшом " +
                    "количестве, естественно. И это далеко не все мои возможности.";
        }
        return "Пока я не могу ответить вам на этот вопрос. Попробуйте его переформулировать.";
    }

    // Проверка, содержит ли массив нормализованных строк хотя бы одну из заданных нормализованных строк
    private boolean containsAnyNormalized(String[] array, String... normalizedValues) {
        for (String normalizedValue: normalizedValues) {
            for (String token: array) {
                if (token.equalsIgnoreCase(normalizedValue)) {
                    return true;
                }
            }
        }
        return false;
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

    public String checkTime(String timeNow){

        LocalTime morningTime = LocalTime.of(5, 59);
        LocalTime dayTime = LocalTime.of(11,59);
        LocalTime eveningTime = LocalTime.of(16,59);
        LocalTime nightTime = LocalTime.of(23,59);

        LocalTime time = LocalTime.parse(timeNow);
        if (time.isAfter(morningTime) && time.isBefore(dayTime)) {
            return "Утренний сон!";
        } if (time.isAfter(dayTime) && time.isBefore(eveningTime)) {
            return "Дневной сон!";
        } if (time.isAfter(eveningTime) && time.isBefore(nightTime)) {
            return "Вечерний сон!";
        } if (time.isAfter(nightTime) || time.isBefore(morningTime)) {
            return "Ночной сон!";
        }
        return "Что-то не работет!";
    }
}


























