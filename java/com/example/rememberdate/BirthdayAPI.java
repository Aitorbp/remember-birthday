package com.example.rememberdate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
/***
 * En esta interfaz hacemos las peticiones
 * - Hacemos un get de todos los cumpleaños de la lista
 * -Hacemos un post con los parametros name, image, date
 */
public interface BirthdayAPI {
    @GET("bdays/")
    Call<ArrayList<Birthday>> getBirthdays();

    @FormUrlEncoded
    @POST("bday")
    Call<String> postBirthdays(@Field("name") String name, @Field("image") String image, @Field("date") Long date);
}
