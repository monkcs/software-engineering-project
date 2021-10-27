package com.example.covid_tracker;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;

import java.util.Locale;

public class ChangeLanguage {
    static String swedish = "sv";
    static String english= "en";
    Boolean is_swedish;
    String languageStr= getLanguage();

    public String getLanguage(){
        //get if any language is selected from server
        //if not get native language from the phone
        String language;
        language = downloadSelectedLanguage();

        if (language.isEmpty() ||language == swedish){
            language = swedish;
            is_swedish=true;
        }

        else {
            language = english;
            is_swedish=false;
            //Log.i("local language: ", getNativeLanguage());
        }

    return  language;
    }

    public void setLanguage(Context context, String language){
        //change language to the selected
        //then upload selected language to server

        //change language code here
        if (language == swedish){
            setAppLocate(context, swedish);
            is_swedish=true;
        }
        else {
            setAppLocate(context, english);
            is_swedish=false;
        }

        //------------------------
        uploadSelectedLanguage(languageStr);
    }
    private void uploadSelectedLanguage(String strLanguage){
        //code for uploading to server
    }

    private String downloadSelectedLanguage(){
        String selectedLanguage = "";
        //getting info from database
        return selectedLanguage;
    }

    private String getNativeLanguage(){
        String nativeLanguage = Locale.getDefault().getLanguage();
        return nativeLanguage;
    }

    public void setAppLocate(Context context, String language){


        Resources re = context.getResources();
        DisplayMetrics dm = re.getDisplayMetrics();
        Configuration config = re.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(language.toLowerCase()));
        }
        else{

            config.locale = new Locale(language.toLowerCase());

        }


        re.updateConfiguration(config, dm);

    }

}
