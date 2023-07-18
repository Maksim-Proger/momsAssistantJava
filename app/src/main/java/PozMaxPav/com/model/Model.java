package PozMaxPav.com.model;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import PozMaxPav.com.model.mainmenu.Category;

public class Model {

    public void showPopupMenu(Context context, View view, ArrayList<Category> categories){

        PopupMenu popupMenu = new PopupMenu(context, view);

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            popupMenu.getMenu().add(Menu.NONE, category.getId(), i, category.getTitle());
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                for (Category category : categories) {
                    if (itemId == category.getId()) {
                        Intent intent = new Intent(context, category.getActivityClass());
                        context.startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });

        popupMenu.show();
    }

    public String fixTime() {
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dateNow);
    }
}
