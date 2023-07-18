package PozMaxPav.com.presenter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.mainmenu.Category;

public class Presenter {

    Model model = new Model();

    public void popupMenu(Context context, View view, ArrayList<Category> categories) {
        model.showPopupMenu(context,view,categories);
    }

    public String fixTime(){
        return model.fixTime();
    }


}
