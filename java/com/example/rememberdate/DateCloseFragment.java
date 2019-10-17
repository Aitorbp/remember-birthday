package com.example.rememberdate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Fragment que muestra la fecha más cercana a nuestro día actual
 */
public class DateCloseFragment extends Fragment {
TextView textView;
ImageView imageClose;
String image;
String name;
Long dateInLong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_date_closet, container, false);

        textView =(TextView) RootView.findViewById(R.id.textView);
        imageClose = (ImageView) RootView.findViewById(R.id.imageClose);



        if(image != null && name != null){

            textView.setText(name + " nació el " + setDateFormat(dateInLong));
            Picasso.get().load(image).into(imageClose);

        }

        Picasso.get().load(image).into(imageClose);

        // Inflate the layout for this fragment
        return RootView;
    }

    public void sendLastBirthday(long dateSaved, String imageSaved, String nameSaved) {
        if(image != null && name != null){
             // Cambio de fecha de formato milisecond a normal
            textView.setText(name + " nació el " + setDateFormat(dateSaved));
            Picasso.get().load(image).into(imageClose); //utilizamos el picasso para cargar la imagen de Internet
        }
        dateInLong = dateSaved;
        image= imageSaved;
        name = nameSaved;
    }

    /**
    Método para formatear la fecha y ponerla de una forma más visual
     */
    public String setDateFormat(long date){
        Date dateFormat =new Date(date); // Cambio de fecha de formato milisecond a normal
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy");
        String dateFomate = simple.format(dateFormat);
        return dateFomate;
    }
}
