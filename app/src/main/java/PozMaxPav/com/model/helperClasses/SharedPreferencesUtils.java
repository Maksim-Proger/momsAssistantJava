package PozMaxPav.com.model.helperClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    // переменные для активности регистрации
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PATRONYMIC = "patronymic";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    // переменные для активности Сон
    private static final String KEY_ASLEEP = "Asleep";
    private static final String KEY_AWOKE = "Awoke";

    // переменные для активности детского профиля
    private static final String KEY_CHILDREN_NAME = "Children_name";
    private static final String KEY_CHILDREN_AGE = "Children_age";
    private static final String KEY_CHILDREN_WEIGHT = "Children_weight";
    private static final String KEY_CHILDREN_HEIGHT = "Children_height";
    private static final String KEY_CHILDREN_GENDER = "Children_gender";

    // переменная для отсчета времения бодрствования
    private static final String KEY_WAKING_TIME = "waking_time";
    private static final String KEY_DIFFERENCE_TIME = "difference_Time";


    public static String getKeyName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public static String getKeySurname(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.getString(KEY_SURNAME, null);
    }

    public static String getKeyPatronymic(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.getString(KEY_PATRONYMIC, null);
    }

    public static String getKeyEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public static String getKeyPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public static void saveCredentials(
            Context context, String name, String surname,
            String patronymic, String email, String password) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_PATRONYMIC, patronymic);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }



    // Методы проверки наличия логина и пароля в SharedPreferences
    public boolean hasLoginAndPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, null);
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        return savedEmail != null && savedPassword != null;
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
        editor.apply();
    }


    // Активность Сон

    public static String getKeyAsleep(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ASLEEP, null);
    }

    public static String getKeyAwoke(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AWOKE, null);
    }

    public static void saveKeyAsleep(Context context, String asleep) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ASLEEP, asleep);
        editor.apply();
    }

    public static void saveKeyAwoke(Context context, String awoke) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AWOKE, awoke);
        editor.apply();
    }

    public static void removeAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ASLEEP);
        editor.remove(KEY_AWOKE);
        editor.apply();
    }

    // Активность деткого профиля

    public static String getKeyChildrenName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILDREN_NAME, null);
    }

    public static String getKeyChildrenAge(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILDREN_AGE, null);
    }

    public static String getKeyChildrenWeight(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILDREN_WEIGHT, null);
    }

    public static String getKeyChildrenHeight(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILDREN_HEIGHT, null);
    }

    public static String getKeyChildrenGender(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILDREN_GENDER, null);
    }

    public static void saveChildrenProfile(
            Context context, String name, String age,
            String weight, String height, String gender) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHILDREN_NAME, name);
        editor.putString(KEY_CHILDREN_AGE, age);
        editor.putString(KEY_CHILDREN_WEIGHT, weight);
        editor.putString(KEY_CHILDREN_HEIGHT, height);
        editor.putString(KEY_CHILDREN_GENDER, gender);
        editor.apply();
    }

    // для отсчета времения бодрствования
    public static String getKeyWakingTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WAKING_TIME, null);
    }

    public static void saveWakingTime(Context context, String wakingTime) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_WAKING_TIME, wakingTime);
        editor.apply();
    }

    public static void removeWakingTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_WAKING_TIME);
        editor.apply();
    }

    public static String getKeyDifferenceTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DIFFERENCE_TIME, null);
    }

    public static void saveDifferenceTime(Context context, String differenceTime) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DIFFERENCE_TIME, differenceTime);
        editor.apply();
    }
    public static void removeDifferenceTime(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_DIFFERENCE_TIME);
        editor.apply();
    }

}
