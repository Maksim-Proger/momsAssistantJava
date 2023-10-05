package PozMaxPav.com.model.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistic")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date; // дата создания группы
    private String sleep1;
    private String sleep2;
    private String sleep3;


    public int getId() {
        return id;
    }

    public String getSleep1() {
        return sleep1;
    }

    public String getSleep2() {
        return sleep2;
    }

    public String getSleep3() {
        return sleep3;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSleep1(String sleep1) {
        this.sleep1 = sleep1;
    }

    public void setSleep2(String sleep2) {
        this.sleep2 = sleep2;
    }

    public void setSleep3(String sleep3) {
        this.sleep3 = sleep3;
    }
}
