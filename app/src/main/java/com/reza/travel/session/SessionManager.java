package com.reza.travel.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.reza.travel.activity.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AzharPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";  // Added role key
    public static final String KEY_HP = "hp";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Method to create login session, now with role
    public void createLoginSession(String email, String role) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);  // Save role (user or admin)
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    // Method to get user details, including role
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));  // Get role (user or admin)
        user.put(KEY_HP, pref.getString(KEY_HP, null));
        return user;
    }

    // Logout user and clear session
    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
