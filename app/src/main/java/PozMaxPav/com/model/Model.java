package PozMaxPav.com.model;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import PozMaxPav.com.model.helperClasses.readBasePackage.ReadBase;
import PozMaxPav.com.model.helperClasses.sharedPreference.SharedPreferencesUtils;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;

public class Model {

    protected ArrayList<String> answerOptions = new ArrayList<>();

    // region методы для работы со временем и датой

    // выводим текущую дату с днем недели (класс Calendar)
    public String sourceDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE\ndd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        // Преобразование первой буквы дня недели в заглавную
        return currentDate.substring(0, 1).toUpperCase() + currentDate.substring(1);
    }

    // метод для фиксации времени
    public String fixTime() {
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault()); // добавил тут Locale.getDefault()
        return sdf.format(dateNow);
    }

    // разбираемся с фоновой работой уведомлений
//    public boolean checkTimeLastSleep(String wakingTime) {
//        int hours = 0;
//        int minutes = 0;
//
//        if (wakingTime != null && !wakingTime.isEmpty()) {
//            LocalTime localTime = LocalTime.parse(wakingTime);
//            hours = localTime.getHour();
//            minutes = localTime.getMinute();
//        }
//
//        if (minutes > 1) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    // метод правильного склонения минут
    public String correctWordMinutes(long minutes) {

        if (minutes % 10 == 1 && minutes % 100 != 11) {
            return " минуту";
        } else if ((minutes % 10 > 2 && minutes % 10 <= 4)
                && (minutes % 100 < 10 || minutes % 100 >= 20)) {
            return " минуты";
        } else {
            return " минут";
        }
    }
    // метод правильного склонения часов
    public String correctWordHours(long hours) {

        if (hours == 1) {
            return " час";
        } else if (hours > 1 && hours < 5) {
            return " часа";
        } else {
            return " часов";
        }
    }


    // метод расчета времени бодрствования
    public String timeSinceLastSleep(String time, Context context) {
        String stringDifferenceTimeView = "";
        if (time != null) {

            // region блок отвечающий за переход через полночь
            String timeWithDate = LocalDate.now() + "T" + time;
            LocalDateTime awokeTime = LocalDateTime.parse(timeWithDate);
            LocalDateTime currentTime = LocalDateTime.now();

            if (currentTime.isBefore(awokeTime)) {
                // Перешли через полночь, обновляем дату бодрствования
                awokeTime = awokeTime.minusDays(1);
            }
            // endregion

            Duration durationDifferenceTime = Duration.between(awokeTime, currentTime);
            long hours = durationDifferenceTime.toHours();
            long minutes = durationDifferenceTime.toMinutes() - (hours * 60); // вычитаем минуты, учтенные как часы
            String stringDifferenceTime = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);

            SharedPreferencesUtils.saveDifferenceTime(context, stringDifferenceTime); // записываем в SharedPreference

            if (hours != 0) {
                stringDifferenceTimeView =
                        hours + correctWordHours(hours) + " " + minutes + correctWordMinutes(minutes);
            } else {
                stringDifferenceTimeView = minutes + correctWordMinutes(minutes);
            }
        }
        return stringDifferenceTimeView;
    }

    // метод вычисляет разницу между временем для дальнейшей записи в базу данных
    public String result(String first, String second){

        LocalTime time1 = LocalTime.parse(first);
        LocalTime time2 = LocalTime.parse(second);

        long differenceInMinutes = ChronoUnit.MINUTES.between(time1, time2);

        // (Переход через полночь) Если разница отрицательная, добавляем 24 часа
        if (differenceInMinutes < 0) {
            differenceInMinutes += 24 * 60; // 24 часа * 60 минут
        }

        return String.valueOf(differenceInMinutes);
    }

    // метод для определения времени суток
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
        return "Что-то не работает!";
    }

    // метод определения даты
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // для проверки сортировки поменял dd-MM-yyyy на yyyy-MM-dd
        return sdf.format(new Date());
    }

    // endregion

    // region методы для ассистента
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
        } else if (containsAnyNormalized(tokens,
                "что такое лактостаз", "про лактостаз", "о лактостазе")) {
            return readBase.read(context, "lactostasis.txt");
        } else if (containsAnyNormalized(tokens,
                "справиться с лактостазом", "у тебя лактостаз", "при лактостазе")) {
            return readBase.read(context, "lactostasisTreatment.txt");
        }

        return "Пока я не могу ответить вам на этот вопрос. Попробуйте его переформулировать.";
    }

    // Проверка, содержит ли массив нормализованных строк хотя бы одну из заданных нормализованных строк
    private boolean containsAnyNormalized(String[] array, String... normalizedValues) {
        // Преобразование массива строк array в одну строку с пробелами между элементами
        // Сведение к нижнему регистру для регистронезависимого поиска
        String combined = String.join(" ", array).toLowerCase();

        for (String normalizedValue : normalizedValues) {
            // Проверка, содержится ли normalizedValue (приведенное к нижнему регистру) в combined
            if (combined.contains(normalizedValue.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    // endregion

    // region методы регистрации
    // метод для проверки наличия профиля пользователя
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

    // валидация правильности введения данных регистрации
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
            return "Введен некорректный email";
        } else if (password.equals("")) {
            return "Введите пароль";
        }
        return "Пользователь зарегистрирован!";
    }

    // метод для проверки email
    public static boolean isValidEmail(String email) {
        // Шаблон для проверки email
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    // endregion


    /**
     * Считаем кол-во снов
     */
    int count = 0;
    public int counterSleeps(Button button) {

        if (button != null) {
            count++;
        }

        LocalTime nightTime = LocalTime.of(23,59);
        LocalTime localTime = LocalTime.now();

        if (localTime.isAfter(nightTime)) {
            count = 0;
        }

        return count;
    }


}


























