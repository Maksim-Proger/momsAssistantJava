package PozMaxPav.com.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM statistic ORDER BY date, id DESC")
    List<User> getAllUsers(); // Запрос для получения записей с учетом даты создания группы

    @Query("SELECT * FROM statistic WHERE id = :userId")
    User getUserById(int userId);

    @Update
    void updateUser(User user);

}
