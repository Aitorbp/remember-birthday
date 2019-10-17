package com.example.rememberdate;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Adapter para crear la galería/list de cumpleaños
 * */

public class AdaptarListBirthDay extends ArrayAdapter {
    Context context;
    int item_Layaut;
    ArrayList<Birthday> data;

    public AdaptarListBirthDay(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.item_Layaut = resource;
        this.data = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(item_Layaut, parent, false);
        }

        String image = data.get(position).getImage();
        String name = data.get(position).getName();
        Long date = data.get(position).getDate();


        ImageView elementImage = convertView.findViewById(R.id.image);
//        elementImage.setImageResource(Integer.parseInt(image));
        try {
            Picasso.get().load(image).into(elementImage);
        }catch (Exception e){

        }


        TextView elementName = convertView.findViewById(R.id.name);
        elementName.setText(name);

        TextView elementDate = convertView.findViewById(R.id.date);

        String dateString = String.valueOf(setDateFormat(date));
        elementDate.setText(dateString);



        return convertView;
    }
/**
 * Método para formatear la fecha
 * @param date de tipo long
 * @return fecha formateada en string
 * */
    //Método formtear fecha
    public String setDateFormat(long date){
        Date result = new Date(date);
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy");
        String dateFomate = simple.format(result);

        return dateFomate;
    }
}