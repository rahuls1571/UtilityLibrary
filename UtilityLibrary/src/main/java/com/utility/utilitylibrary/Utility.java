package com.utility.utilitylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

public class Utility {

//------------------------------------------------------- SharedPreferences ----------------------------------------------------------------------------------------------------------------

    public static void saveData(Context ctx, String TAG, String data,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TAG, data);
        editor.apply();
    }

    public static void saveData(Context ctx, String TAG, boolean value,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(TAG, value);
        editor.apply();
    }

    public static String getSavedData(Context ctx, String TAG,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        return prefs.getString(TAG, "");
    }

    public static boolean getBooleanSavedData(Context ctx, String TAG,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        return prefs.getBoolean(TAG, false);
    }

    public static void clearData(Context ctx,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static void setPermissionDenied(Context ctx, String keyPermission, boolean isDenied,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(keyPermission, isDenied);
        editor.apply();
    }

    public static boolean isPermissionDenied(Context ctx, String keyPermission,String PREF_KEY) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_KEY, 0);
        return prefs.getBoolean(keyPermission, false);
    }

//------------------------------------------------------- isEmptyString ----------------------------------------------------------------------------------------------------------------
    public static boolean isEmptyString(String str) {
        if (str == null) {
            return true;
        } else if (str.isEmpty()) {
            return true;
        } else return str.equalsIgnoreCase("null");
    }

    public static String getStringValue(String value){
        return isEmptyString(value) ? "" : value;
    }

    public static String getEditTextValue(EditText editText){
        return isEmptyString(editText.getText().toString()) ? "" : editText.getText().toString();
    }

//------------------------------------------------------- Toast ----------------------------------------------------------------------------------------------------------------

    public static void showToast(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
