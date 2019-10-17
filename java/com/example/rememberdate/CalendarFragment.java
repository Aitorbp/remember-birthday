package com.example.rememberdate;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Fragment del popup del calendario. Se activa al pulsar en el boton del calendario en la pantalla
 * AddDateFragment
 */

public class CalendarFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    MainActivity mainActivity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
/**
 * Método para coger la fecha y pasarla a milisecond
 * */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Toast.makeText(getActivity(), dayOfMonth + " / " + (month+1) + " / " + year, Toast.LENGTH_SHORT).show();
                    Calendar calendar = new GregorianCalendar(year, (month), dayOfMonth);
                    Date dateCalendar = calendar.getTime();
                    long millisecond = dateCalendar.getTime();
                    Log.d("1111111111111", String.valueOf(millisecond));


                    mainActivity.sendDatePicker(millisecond);

    }
}




