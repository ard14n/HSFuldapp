package com.abmedia.hsfuldapp;

import java.util.HashMap;

/**
 * Created by Dini on 13.06.2017.
 */

public class Grade {


    public String datum, pruefung, status, versuch, credits, note;
    public HashMap<String, String> averageGrades;

    public Grade(String datum, String pruefung, String status, String versuch, String credits, String note){
        this.datum = datum;
        this.pruefung = pruefung;
        this.status = status;
        this.versuch = versuch;
        this.credits = credits;
        this.note = note;
    }


    public Grade(String datum, String pruefung, String status, String versuch, String credits, String note, HashMap<String, String> averageGrades){
        this.datum = datum;
        this.pruefung = pruefung;
        this.status = status;
        this.versuch = versuch;
        this.credits = credits;
        this.note = note;
        this.averageGrades = averageGrades;
    }





}
