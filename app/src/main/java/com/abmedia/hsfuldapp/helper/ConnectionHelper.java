package com.abmedia.hsfuldapp.helper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dini on 13.06.2017.
 *
 * Helfer Klasse für Get und Post Requests mit JSOUP
 *
 * Hinweis: Noch nicht benutzen!
 */

public class ConnectionHelper  {

    // TODO Cookieproblem lösen
    // TODO Header für die Verbindung implementieren

    public Connection.Response makeGetRequest(String url, String cookiename, String cookie, String referer, String useragent) {

        Connection.Response request = null;

        try {

            request = Jsoup.connect(URLDecoder.decode(url, "utf-8"))
                    .method(Connection.Method.GET)
                    .userAgent(useragent)
                    .referrer(referer)
                    .cookie(cookiename,cookie)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    public Connection.Response makePostRequest(String url, String referer, String useragent, HashMap<String, String> postfields) {


        Connection.Response request = null;
        try {

            request = Jsoup.connect(URLDecoder.decode(url, "utf-8"))
                    .method(Connection.Method.POST)
                    .userAgent(useragent)
                    .referrer(referer)
                    .data(postfields)
                    .execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;

    }

}
