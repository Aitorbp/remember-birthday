package com.example.rememberdate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
/***
 * En esta interfaz hacemos la petición a la raspberry
 * - Hacemos un get a un string
 */

public interface LedAPI {

    @GET("today.php")
    Call<String> getLed();
}
