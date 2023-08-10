package PozMaxPav.com.view;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import PozMaxPav.com.model.mainmenu.Category;
import PozMaxPav.com.presenter.Presenter;

public class Controller {

    Presenter presenter = new Presenter();

    public void menu(Context context, View view, ArrayList<Category> categories) {
        presenter.popupMenu(context,view,categories);
    }

    public String fixTime(){
        return presenter.fixTime();
    }

    public String assistantMethod(String question){
        return presenter.assistantMethod(question);
    }

}
