package com.example.socketclient;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by DPavliuk on 11.09.2017.
 */

public class User {
    private int id;
    private String name;
    private int color;

    public User(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public User(int id, String name) {

        this.id = id;
        this.name = name;
        Random rnd = new Random();
        this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public String toJson() {
        String result = null;
        JSONObject jo = new JSONObject();
        try {
            jo.put("id", id);
            jo.put("name", name);
            result = jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    ;
}
