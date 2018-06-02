package com.example.cdc.smartpan_task.activities.Pref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.cdc.smartpan_task.activities.LoginActivity;

public class Preference {

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "My_Pref";

    public Preference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(int userID, String userName){
        editor.putBoolean("IS_LOGIN", true);
        editor.putInt("ID", userID);
        editor.putString("userName", userName);
        editor.apply();
    }

    public int getUserID(){
        return sharedPreferences.getInt("ID", 0);
    }

    public String getUserName(){
        return sharedPreferences.getString("userName", "H");
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    private boolean isLoggedIn(){
        return sharedPreferences.getBoolean("IS_LOGIN", false);
    }
}
