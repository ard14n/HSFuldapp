package com.abmedia.hsfuldapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = (TextView) findViewById(R.id.text);

        Grades testgrade = new Grades("test","test");

        String asiCode = testgrade.login();



        new LoginGrades().execute();


    }

    private class LoginGrades extends AsyncTask <String, Void, Void>  {

        String title,asi_href, asi;
        String login_url = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=1&category=auth.login&startpage=portal.vm&breadCrumbSource=portal";
        String referer = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=0";



        @Override
        protected Void doInBackground(String... params) {

            //Parameter der vorher erfolgten Benutzereingabe
            String username = "";
            String password = "";

            try{

                //TODO Fehlermanagement vervollständigen
                //TODO Bedingung für nicht erfolgreiche Anmeldung hinzufügen
                //TODO Bei erfolgreicher Anmeldung zur nächsten Activity wechseln
                //TODO Cookiemanagement zur Erhaltung der Session hinzufügen (JSESSIONID)

                //DOM-Objekt erzeugen nach Anfrage in Qispos mit Credentials
                Document docs = Jsoup.connect(login_url)
                        .data("asdf", username,"fdsa",password,"submit", "Anmelden")
                        .referrer(referer)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                        .post();


                Element f = docs.select("div[class=divloginstatus]").first();
                title = f.text();

                //QISPOS DOM-Traversing und Regex-Extrahierung für asi-code
                //TODO Regex für ASI noch weiter spezifiern um mögliche Fehler zu verhindern
                Element e = docs.select("a[class=auflistung]").get(3);
                asi_href = e.attr("href");

                Pattern p = Pattern.compile("(?<=asi=).*");
                Matcher m = p.matcher(asi_href);
                if (m.find()) {
                    asi = m.group(0);
                }


            }catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        //Setzt den greeting Text
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Context context = getApplicationContext();
            TextView test = (TextView) findViewById(R.id.text);
            test.setText(asi);

        }
    }

}


