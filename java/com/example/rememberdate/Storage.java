package com.example.rememberdate;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Storage {
    private SharedPreferences preferences;
    private Gson gson;

    Storage(Context context) {
        gson = new Gson();
        preferences = context.getSharedPreferences("Enemigos", Context.MODE_PRIVATE);
    }

    public void save(Dates data) {
        String json = toJson(data);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data", json);
        editor.commit();
    }
    public Dates load() {
        String json = preferences.getString("data", "");
        return fromJson(json);
    }

    public String toJson(Dates dates) {
        return gson.toJson(dates);
    }

    public Dates fromJson(String json) {
        return gson.fromJson(json, Dates.class);
    }

}
