package PozMaxPav.com.model.helperClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PATRONYMIC = "patronymic";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    // переменные из активности Сон
    private static final String KEY_ASLEEP = "Asleep";
    private static final String KEY_AWOKE = "Awoke";



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

}
