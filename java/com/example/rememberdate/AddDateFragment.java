package com.example.rememberdate;


import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
/**
 * Fragment para crear el cumpleaños
 * */

public class AddDateFragment extends Fragment {


    ImageView imageView,imageView1, imageView2, imageView3, imageView4;
    EditText nameEditText;
    Button goToCalendarButton;
    Button sentDataButton;
    long calendarDate;
    MainActivity mainActivity;
    String link;
    String link1;
    String link2;
    String link3;
    String link4;
    String currentImage;
    Drawable shape;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_add_date, container, false);
        imageView4 = (ImageView) RootView.findViewById(R.id.imagenview4);
        imageView3 = (ImageView) RootView.findViewById(R.id.imagenview3);
        imageView2= (ImageView) RootView.findViewById(R.id.imagenview2);
        imageView1= (ImageView) RootView.findViewById(R.id.imagenview1);
        imageView = (ImageView) RootView.findViewById(R.id.imagenview);
        nameEditText = (EditText) RootView.findViewById(R.id.nameEditText);
        goToCalendarButton = (Button) RootView.findViewById(R.id.goToCalendarButton);
        sentDataButton = (Button) RootView.findViewById(R.id.sentDataButton);
        mainActivity = (MainActivity) getActivity();
        shape = getResources().getDrawable(R.drawable.shape);


        Log.d("oncrea", "eee");

        loadImage();
        loadImage1();
        loadImage2();
        loadImage3();
        loadImage4();


        //Boton para ir al calendario
        goToCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });


        // Boton para enviar los datos
        sentDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();

        //Hacemos las validaciones
                if(name.isEmpty()){
                    Toast.makeText(getContext(), "Escribe un dato", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(currentImage == null){
                    Toast.makeText(getContext(), "Debes poner una imagen", Toast.LENGTH_SHORT).show();
                    return;
                }

        // Metemos los datos en el array creado
                mainActivity.bithdayPOST( name, currentImage, sendDatePicker(calendarDate)); //POST PARA ENVIAR LOS DATOS AL SERVIDOR
                mainActivity.birthdayGET(); //Metemos el método Get para que actualize los datos que tengamos.
                mainActivity.changeScreen(1);


            }
        });

        return RootView;
    }

    // Cargar imagen 1
    public void loadImage(){
        link = "http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe";
        Picasso.get().load(link).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(imageView!=null){
                    imageView.setBackground(shape);
                    imageView1.setBackground(null);
                    imageView2.setBackground(null);
                    imageView4.setBackground(null);
                    imageView3.setBackground(null);
                    currentImage= link;
                    Log.d("image1", currentImage);
                }
            }
        });
    }

    // Cargar imagen 2
    public void loadImage1(){
        link1 = "http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi";
        Picasso.get().load(link1).into(imageView1);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(imageView!=null){

                    imageView1.setBackground(shape);
                    imageView.setBackground(null);
                    imageView2.setBackground(null);
                    imageView3.setBackground(null);
                    imageView4.setBackground(null);
                    currentImage= link1;
                    Log.d("image1", currentImage);
                }
            }
        });
    }
    // Cargar imagen 3
    public void loadImage2(){
        link2 = "http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX";
        Picasso.get().load(link2).into(imageView2);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(imageView!=null){

                    imageView2.setBackground(shape);
                    imageView1.setBackground(null);
                    imageView.setBackground(null);
                    imageView3.setBackground(null);
                    imageView4.setBackground(null);
                    currentImage= link2;
                    Log.d("image1", currentImage);
                }
            }
        });

    }

    // Cargar imagen 3
    public void loadImage3(){
        link3 = "http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn";
        Picasso.get().load(link3).into(imageView3);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(imageView!=null){
                    imageView3.setBackground(shape);
                    imageView2.setBackground(null);
                    imageView1.setBackground(null);
                    imageView.setBackground(null);
                    imageView4.setBackground(null);
                    currentImage= link3;
                    Log.d("image1", currentImage);
                }
            }
        });

    }
    // Cargar imagen 3
    public void loadImage4(){
        link4 = "http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE";
        Picasso.get().load(link4).into(imageView4);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(imageView!=null){
                    imageView4.setBackground(shape);
                    imageView3.setBackground(null);
                    imageView2.setBackground(null);
                    imageView1.setBackground(null);
                    imageView.setBackground(null);
                    currentImage= link4;
                    Log.d("image1", currentImage);
                }
            }
        });

    }

//Método para enviar los datos de la fecha
    public Long sendDatePicker(long calendar) {
        calendarDate =calendar;
        Log.d("eeeeeeeeeeeee", String.valueOf(calendarDate));
        return calendarDate;

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new CalendarFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
