package com.abmedia.hsfuldapp;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
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

    private String login_url = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=1&category=auth.login&startpage=portal.vm&breadCrumbSource=portal";
    private String referer = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=0";

    private String useragent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";


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



    public String login(){

        return "Execute";

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

    private ArrayList<String> saveGrades(ArrayList<String> grades){

        ArrayList<String> grades_intern = new ArrayList<String>();

        return grades;
    }

    private ArrayList<String> orderGrades(ArrayList<String> grades){

        return grades;
    }

    private boolean checkGradesOnChange(){

        return false;
    }





}
