package com.abmedia.hsfuldapp.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

     /*
    * Methode zum Finden von RegexMatches
    *
    * @param regex Ist der Regex String nach dem gesucht werden soll
    * @param lookAt Ist der String in dem gesucht werden soll
    * @param index Entspricht dem Index der ausgegeben werden soll
    *
    * @return Gefundener String
    *
    * Autor: Dini
    *
    * */

    public String regexReq(String regex, String toLookAt, int index){

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(toLookAt);

        String result;

        if (m.find()) {

            result = m.group(index);

            return result;

        } else {
            return "";
        }



    }




}
