package PozMaxPav.com.all_activities.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1) // тут указываем все наши классы сущностей
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao(); // абстрактный метод для каждой сущности
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"my_database")
                    .fallbackToDestructiveMigration() // Используем fallbackToDestructiveMigration() для простой миграции
                    .build();
        }
        return INSTANCE;
    }
}
