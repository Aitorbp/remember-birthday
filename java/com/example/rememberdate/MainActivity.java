package com.example.rememberdate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity {

    ListView listview;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Dates dates;
    Storage storage;
    Retrofit retrofit;
    BirthdayAPI birthdayAPI;
    long calendar;
    long dateSaved;
    String imageSaved;
    String nameSaved;
    LedAPI ledAPI;
    FragmentManager manager;
    Fragment fragment;
    ArrayList<Birthday> birthdays;
    ArrayList<Birthday> birthdaysAll;
    long dayMoreThan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.listView);
        drawerLayout = findViewById(R.id.drawerLayout);

        //Utilizamos la librería retrofit para acceder al servidor
        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        birthdayAPI = retrofit.create(BirthdayAPI.class);


        //Código para poner boton de menú lateral
        drawerToggle= new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Con este código cargamos los datos
        storage = new Storage(this);
        dates = storage.load();
        if (dates == null) {
            dates = new Dates();
        }

        birthdayGET();
        ledGET();


        //Iniciar la aplicación con esta pantalla
        changeScreen(1);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeScreen(position);

            }
        });
    }

    //Código par aponer boton de menú lateral
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    //Código par aponer boton de menú lateral
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
/**
 * Método para cambiar de pantalla en el menú lateral a través un switch
 * */
    void changeScreen(int screen) {
        fragment = null;
        manager = getSupportFragmentManager();

        switch (screen) {
            case 0:
                fragment = new AddDateFragment();
                break;
            case 1:
                fragment = new DateCloseFragment();
                birthdayGET(); //métemos el get aquí para que cargue los datos desde el principio
                recieveLastBirthday(dateSaved, imageSaved, nameSaved, (DateCloseFragment) fragment);
                break;
            case 2:
                fragment = new ListDateFragment();
                recieveAllData(birthdaysAll, (ListDateFragment) fragment); //le enviamos toda la lista
                break;
            default:
                break;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

        drawerLayout.closeDrawer(Gravity.START);
    }


    //LLAMADAS AL SERVIDOR

//Método get para Led
    void ledGET(){
      Retrofit  retrofitLed = new Retrofit.Builder()
                .baseUrl("http://192.168.1.2/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ledAPI = retrofitLed.create(LedAPI.class);

        Call<String> call= ledAPI.getLed();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), "Led encendido", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
/**
 * Método para hacer un get con un método de respuesta y ort de fallo. Le pasamos en la llamada la interface con el método
 * getBirthdays. Igualamos los cumpleaños a responde.body()
 * */
    void birthdayGET(){
        Call<ArrayList<Birthday>> call = birthdayAPI.getBirthdays();
        call.enqueue(new Callback<ArrayList<Birthday>>() {
            @Override
            public void onResponse(Call<ArrayList<Birthday>> call, Response<ArrayList<Birthday>> response) {
                birthdays = response.body();
                sendAllData(birthdays); //Poenmos este método directamente despues de la respuesta para que meta los datos y no dé null
                closeBirthday();
            }
            @Override
            public void onFailure(Call<ArrayList<Birthday>> call, Throwable t) {
            }
        });
    }
/**
 *  Método para hacer un post. Le pasamos en la llamada la interface con el método
 *  postBirthdays con sus parámetros
 * */

    void bithdayPOST(String name, String image, Long date){
        Call<String> call = birthdayAPI.postBirthdays(name, image, date);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String state = response.body();
                Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                Log.d("********", state);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Enviar último cumpleaños al DateCloseFragment
    public void sendLastBirthday(long dateSavedSend, String imageSavedSend, String nameSavedSend){
            dateSaved= dateSavedSend;
            imageSaved= imageSavedSend;
            nameSaved = nameSavedSend;
    }
    //Enviar último cumpleaños al DateCloseFragment
    public void recieveLastBirthday(long dateSaved, String imageSaved, String nameSaved, DateCloseFragment fragment){
        fragment.sendLastBirthday(dateSaved, imageSaved, nameSaved);
    }

    /**
     *
     * Método para enviar todos los datos en un objeto. Este método lo utilizamos para el list
     */

    public void sendAllData(ArrayList<Birthday> birthdays){
      if(birthdays!= null){
          birthdaysAll = birthdays;
      }

    }
    public void recieveAllData(ArrayList<Birthday> birthdays, ListDateFragment fragment){

            fragment.sendAllData(birthdaysAll);
        }


    //Enviar los datos del calendario al activity
    public void sendDatePicker(long datePicker){

         calendar = datePicker;
        recievedDataCalendar(calendar, (AddDateFragment)fragment);
    }
    //Método para que el AddDateFragment reciba los datos del calendario.
    //
    public void recievedDataCalendar(long calendar, AddDateFragment fragment){
        fragment.sendDatePicker(calendar);
    }

/**
 * Método para sacar el día más cercano a hoy. Tenemos puesto mayor o igual por lo que nos sacará la fecha que pertenezca a hoy
 * Para ello sacamos la fecha de hoy en formato días del año. Recorremos el array de datos con un for y ponemos una condición
 * para coger la fecha más cercana.
 */
    public void closeBirthday(){
        sortByDayOfYear();
        //Calculamos la fecha en dias del año actual
        Calendar calendar = Calendar.getInstance();
        int dayCurrent = calendar.get(Calendar.DAY_OF_YEAR);


        //Hacemos el for para sacar el primer dato. Como la lista ya está ordenada nos sacará la última fecha.
        for (int i = 0; i < birthdays.size() ; i++) {

            dateSaved = birthdays.get(i).getDate();

            String nameAll =birthdays.get(i).getName();


            //Calculamos la fecha en dias del año
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dateSaved);
            int day = c.get(Calendar.DAY_OF_YEAR);


            if(day >= dayCurrent){

                dayMoreThan = dateSaved;
                imageSaved = birthdays.get(i).getImage();
                nameSaved = birthdays.get(i).getName();

                Log.d("MoreThan", String.valueOf(dayMoreThan));
                Log.d("day", String.valueOf(day));
                Log.d("name", String.valueOf(nameAll));
                Log.d("lastdate", String.valueOf(dayMoreThan));

                recieveLastBirthday(dayMoreThan, imageSaved, nameSaved, (DateCloseFragment) fragment);
                break;

            }



        }



    }
    /**
     * Método para comparar fechas por día del año
     *
     */
    public void sortByDayOfYear(){
        Collections.sort(birthdays, new Comparator<Birthday>() {
            @Override
            public int compare(Birthday o1, Birthday o2) {

                long date1 = o1.getDate();
                //Calculamos la fecha en dias del año
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(date1);
                int day1 = c.get(Calendar.DAY_OF_YEAR);

                long date2 = o2.getDate();
                //Calculamos la fecha en dias del año
                Calendar ca = Calendar.getInstance();
                ca.setTimeInMillis(date2);
                int day2 = ca.get(Calendar.DAY_OF_YEAR);

                if(day1 > day2){
                    return 1;
                }
                else if(day1 < day2){
                    return -1;
                }
                else return 0;
            }
        });
    }

    /***
     * Método para comparar fechas por edad. Este método lo utilizamos en el ListDateFragment
     */
    public void sortByAge(){
        Collections.sort(birthdays, new Comparator<Birthday>() {
            @Override
            public int compare(Birthday o1, Birthday o2) {

                long compare1 = o1.getDate();
                long compare2 = o2.getDate();

                if(compare1 > compare2){
                    return -1;
                }
                else if(compare1 < compare2){
                    return 1;
                }
                else return 0;
            }
        });
    }

    public void onPause(){
        super.onPause();
        storage.save(dates);
    }
}
