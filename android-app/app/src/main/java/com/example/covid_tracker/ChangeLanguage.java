package com.example.covid_tracker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.io.Serializable;
import java.util.Locale;

public class ChangeLanguage implements Serializable {
    static String swedish = "sv";
    static String english= "en";
    static int english_flag = R.drawable.flaggb_foreground;
    static int swedish_flag = R.drawable.flagswe_foreground;
    int current_flag;
    Boolean is_swedish;
    String current_language = getLanguage();

    public String getLanguage(){
        //get if any language is selected from server
        //if not get native language from the phone
        String language;
        language = current_language;//downloadSelectedLanguage();

        if (language == null ||language.isEmpty() ||language == swedish){
            language = swedish;
            is_swedish=true;
            current_flag= swedish_flag;
        }

        else {
            language = english;
            is_swedish=false;
            current_flag= english_flag;
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
            current_language = language;
            current_flag = swedish_flag;
        }
        else {
            setAppLocate(context, english);
            is_swedish=false;
            current_language =language;
            current_flag= english_flag;
        }

        //------------------------
        uploadSelectedLanguage(current_language);
    }
    private void uploadSelectedLanguage(String strLanguage){
        //code for uploading to server
    }

    private String downloadSelectedLanguage(){
        String selectedLanguage = "";
        //getting info from database
        return selectedLanguage;
    }

    public int getFlagIcon(){
        return current_flag;
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
