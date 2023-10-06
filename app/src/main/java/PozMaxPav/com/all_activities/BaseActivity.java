package PozMaxPav.com.all_activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.helperClasses.ForegroundService;
import PozMaxPav.com.model.helperClasses.GeneralNotificationClass;
import PozMaxPav.com.model.helperClasses.SharedPreferencesUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startForegroundService();
    }

    private void startForegroundService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        ContextCompat.startForegroundService(this,serviceIntent);
    }
}
