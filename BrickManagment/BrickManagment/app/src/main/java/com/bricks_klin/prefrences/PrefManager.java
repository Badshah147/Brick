package com.bricks_klin.prefrences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "brick_manag";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String USERNAME = "uname";
    private static final String USERCELL = "ucell";
    private static final String LOGVAL = "logval";
    private static final String RANCODE = "rancode";
    private static final String USERCELLREG = "usercellreg";
    private static final String BHATTA = "bhatta";
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void clearlogout(Context c){
         pref = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().clear().commit();
    }

    public void setusername(String username){
        editor.putString(USERNAME,username);
        editor.commit();
    }
    public void setbhatta(String bhatta){
        editor.putString(BHATTA,bhatta);
        editor.commit();
    }
    public void setrandcode(String rancode){
        editor.putString(RANCODE,rancode);
        editor.commit();
    }
    public void setcell(String cell){
        editor.putString(USERCELL,cell);
        editor.commit();
    }
    public void setcellreg(String cell){
        editor.putString(USERCELLREG,cell);
        editor.commit();
    }

    public void setlogin(String logval){
        editor.putString(LOGVAL,logval);
        editor.commit();
    }
    public String getLogval(){return  pref.getString(LOGVAL,"");}
    public String getBhatta(){return  pref.getString(BHATTA,"");}

    public String getcell(){return  pref.getString(USERCELL,"");}

    public String getcellreg(){return  pref.getString(USERCELLREG,"");}
    public String getusername(){
        return pref.getString(USERNAME,"");
    }
    public String getRancode(){
        return pref.getString(RANCODE,"");
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
