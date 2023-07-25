package PozMaxPav.com.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import PozMaxPav.com.R;
import PozMaxPav.com.model.mainmenu.Category;

public class Model {

    public void showPopupMenu(Context context, View view, ArrayList<Category> categories){

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.AppTheme);
        PopupMenu popupMenu = new PopupMenu(contextThemeWrapper, view);

//        PopupMenu popupMenu = new PopupMenu(context, view);

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
