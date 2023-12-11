package PozMaxPav.com.model.mainMenu;

public class Category {
    protected int id;
    protected String title;

    Class<?> activityClass;

    public Category(int id, String title, Class<?> activityClass) {
        this.id = id;
        this.title = title;
        this.activityClass = activityClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class<?> activityClass) {
        this.activityClass = activityClass;
    }
}
