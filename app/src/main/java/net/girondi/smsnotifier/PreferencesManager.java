package net.girondi.smsnotifier;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    Context ctx;
    public PreferencesManager(Context ctx)
    {
        this.ctx = ctx;
    }

    public String get(String key)
    {
        SharedPreferences sharedPref = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }
    public void set(String key, String s)
    {
        SharedPreferences sharedPref = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, s);
        editor.commit();
    }



}
