package PozMaxPav.com.all_activities.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM statistic")
    List<User> getAllUsers();

    @Query("SELECT * FROM statistic WHERE id = :userId")
    User getUserById(int userId);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

}
