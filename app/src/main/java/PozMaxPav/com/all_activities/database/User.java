package PozMaxPav.com.all_activities.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistic")
public class User {
    @PrimaryKey
    private int id;

    private String sleep1;
    private String sleep2;
//    private String sleep3;

    // Создаем гетеры и сетеры


    public int getId() {
        return id;
    }

    public String getSleep1() {
        return sleep1;
    }

    public String getSleep2() {
        return sleep2;
    }

//    public String getSleep3() {
//        return sleep3;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSleep1(String sleep1) {
        this.sleep1 = sleep1;
    }

    public void setSleep2(String sleep2) {
        this.sleep2 = sleep2;
    }

//    public void setSleep3(String sleep3) {
//        this.sleep3 = sleep3;
//    }
}
