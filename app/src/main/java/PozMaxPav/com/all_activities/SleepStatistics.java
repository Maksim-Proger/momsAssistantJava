package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import PozMaxPav.com.R;
import PozMaxPav.com.model.database.AppDatabase;
import PozMaxPav.com.model.database.MyApp;
import PozMaxPav.com.model.database.User;
import PozMaxPav.com.model.database.UserDao;

public class SleepStatistics extends AppCompatActivity {

    private Button back_button_statistics;
    private TextView statisticsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleepstatistics);

        statisticsView = findViewById(R.id.statisticsView);

        // Инициализируем экземпляр базы данных
        AppDatabase appDatabase = ((MyApp) getApplication()).getAppDatabase();

        // Получаем список пользователей из базы данных асинхронно
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<User>> future = executor.submit(new GetUsersCallable(appDatabase));
        try {
            List<User> users = future.get();
            showStatistics(users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        addListenerOnButton();
    }

    private static class GetUsersCallable implements Callable<List<User>> {
        private final AppDatabase appDatabase;

        public GetUsersCallable(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        public List<User> call() throws Exception {
            // Получаем DAO для работы с таблицей пользователей
            UserDao userDao = appDatabase.getUserDao();

            // Получаем список всех пользователей из базы данных
            return userDao.getAllUsers();
        }
    }

    private void addListenerOnButton() {

        back_button_statistics = findViewById(R.id.back_button_statistics);

        back_button_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showStatistics(List<User> users) {
        StringBuilder stringBuilder = new StringBuilder();
        String currentGroupDate = null; // Дата текущей группы

        // Сортируем список по sleep1
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getSleep1().compareTo(o2.getSleep1());
            }
        });

        for (User item: users) {
            String groupDate = item.getDate(); // Получаем дату создания группы из записи

            // Если дата создания группы изменилась, создаем новую группу
            if (!TextUtils.equals(currentGroupDate, groupDate)) {
                currentGroupDate = groupDate;
                stringBuilder.append("\nДата ").append(groupDate).append("\n");
            }

            stringBuilder.append(item.getSleep1()).append(" - ")
                    .append(item.getSleep2()).append(", Итого: ")
                    .append(item.getSleep3()).append(" минут").append("\n");
        }
        statisticsView.setText(stringBuilder);
    }
}
