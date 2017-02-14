package com.timagreat.stonetetris;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.FileIOInternal;


public class Settings {

    public static int highscore = 0;
    public static int highscore2 = 0;
    public static int highscore3 = 0;
    public static int highscore4 = 0;
    public static int highscore5 = 0;
    static ArrayList<Integer> forHighscore = new ArrayList<Integer>(5);
    static boolean compareOneTime = false;

    static Context context;
    static com.timagreat.stonetetris.DB db;
    static Cursor cursor;

    public Settings (Context ctx){
        context = ctx;
        db = new com.timagreat.stonetetris.DB(context);
        db.open();
    }

    public static void load(){

        cursor = db.getAllData();
        if(cursor!=null&&cursor. moveToFirst()){
            do{

                int score = cursor.getInt(cursor.getColumnIndexOrThrow(com.timagreat.stonetetris.DB.COLUMN_SCORE));

                forHighscore.add(score);

            }while(cursor.moveToNext());
        }

        Collections.sort(forHighscore);

        for (int i = 0; i < forHighscore.size(); i++){
            if (i==0){
                highscore5 = forHighscore.get(i);
            }else if(i==1){
                highscore4 = forHighscore.get(i);
            }else if(i==2){
                highscore3 = forHighscore.get(i);
            }else if(i==3){
                highscore2 = forHighscore.get(i);
            }else if(i==4){
                highscore = forHighscore.get(i);
            }
        }
    }

    public static void addScore(int score){

        for(int i = forHighscore.size()-1; i >= 0 ; i--){

            int highscore = forHighscore.get(i);
            if(compareOneTime == false) {
                if (score > highscore) {
                    forHighscore.remove(0);
                    forHighscore.add(score);
                    compareOneTime = true;
                }
            }
        }

        Collections.sort(forHighscore);
        compareOneTime = false;
    }

    public static void saveInBase(){
        db.delAll();
        for(int i = 0; i < forHighscore.size(); i++){
            db.addRec(forHighscore.get(i));
        }
        for (int i = 0; i < forHighscore.size(); i++){
            if (i==0){
                highscore5 = forHighscore.get(i);
            }else if(i==1){
                highscore4 = forHighscore.get(i);
            }else if(i==2){
                highscore3 = forHighscore.get(i);
            }else if(i==3){
                highscore2 = forHighscore.get(i);
            }else if(i==4){
                highscore = forHighscore.get(i);
            }
        }
    }

}
