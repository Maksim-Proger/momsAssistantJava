package PozMaxPav.com.model.helperClasses.addNewTime;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import java.util.Locale;

public class AddTimeLogic {
    private final Context context;
    private final ListenerInterface listener; // Интерфейс для добавления времени вручную
    private final ChangeTimeFellAsleep changeTimeInterface; // Интерфейс для изменения времени FellAsleep
    private final ChangeTimeWokeUp changeTimeWokeUp; // Интерфейс для изменения времени WokeUp

    public interface ListenerInterface {
        void listenerMethod(String selectedTime);
    }

    public AddTimeLogic(
            Context context, ListenerInterface listener,
            ChangeTimeFellAsleep changeTimeInterface, ChangeTimeWokeUp changeTimeWokeUp
    ) {
        this.context = context;
        this.listener = listener;
        this.changeTimeInterface = changeTimeInterface;
        this.changeTimeWokeUp = changeTimeWokeUp;
    }

    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                // Условие для добавления времени вручную
                if (listener != null) {
                    listener.listenerMethod(selectedTime);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 0,0,true);
        timePickerDialog.show();
    }

    public void showTimePickerDialogFellAsleep() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                // Условие для изменения времени FellAsleep
                if (changeTimeInterface != null) {
                    changeTimeInterface.changedTimeMethod(selectedTime);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 0,0,true);
        timePickerDialog.show();
    }

    public void showTimePickerDialogWokeUp() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                // Условие для изменения времени WokeUp
                if (changeTimeWokeUp != null) {
                    changeTimeWokeUp.changedTimeWokeUp(selectedTime);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 0,0,true);
        timePickerDialog.show();
    }
}
