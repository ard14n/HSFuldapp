package com.abmedia.hsfuldapp;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/*
* Objekt zur Abfrage und Verarbeitung der Noten.
*
*
*
* */

public class Grades {

    private String asi;
    private String username;
    private String password;

    private String useragent = "";


    public Grades(){

    }


    public Grades(String password, String username){

        this.password = password;
        this.username = username;

    }

    public Grades(String asi, String username, String password){

        this.asi = asi;
        this.username = username;
        this.password = password;

    }

    private void login(){

        String postfields;

        try {
            Document doc = Jsoup.connect("http://test.com")
                    .data("query", "Java")
                    .userAgent(this.useragent)
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        


    }

    private void logout(){

    }

    private void changeAsi(String asi){

        this.asi = asi;
    }

    private ArrayList<String> getGrades(){

        ArrayList<String> arrayStrings = new ArrayList<String>();
        return arrayStrings;
    }

    private ArrayList<String> orderGrades(ArrayList<String> grades){

        return grades;
    }

    private boolean checkGradesOnChange(){

        return false;
    }





}
