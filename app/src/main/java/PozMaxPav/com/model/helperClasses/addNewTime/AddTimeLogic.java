package PozMaxPav.com.model.helperClasses.addNewTime;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Locale;

public class AddTimeLogic {
    private Context context;
    private final ListenerInterface listener;

    public interface ListenerInterface {
        void listenerMethod(String selectedTime);
    }

    public AddTimeLogic(Context context, ListenerInterface listener) {
        this.context = context;
        this.listener = listener;
    }

    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                if (listener != null) {
                    listener.listenerMethod(selectedTime);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 0,0,true);
        timePickerDialog.show();
    }
}
