package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abmedia.hsfuldapp.Grade;
import com.abmedia.hsfuldapp.MainActivity;
import com.abmedia.hsfuldapp.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GradesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradesFragment extends Fragment implements View.OnClickListener {

    EditText username_edit;
    EditText password_edit;

    TextView text;

    String username;
    String password;

    ProgressDialog progressDialog;

    public static ArrayList<Grade> gradeslist;

    GradeListFragment glf;

    private OnFragmentInteractionListener mListener;


    public GradesFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * @return A new instance of fragment GradesFragment.
     */
    public static GradesFragment newInstance() {
        GradesFragment fragment = new GradesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = (View) inflater.inflate(R.layout.fragment_grades, container, false);

        Button btn = (Button) v.findViewById(R.id.button);
        btn.setOnClickListener(this);

        gradeslist = new ArrayList<Grade>();
        glf  = new GradeListFragment();



        return v;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        text = (TextView) getView().findViewById(R.id.text);

        username_edit = (EditText) getView().findViewById(R.id.username_field);
        password_edit = (EditText) getView().findViewById(R.id.password_field);

        String uservalue = username_edit.getText().toString().trim();
        String passvalue = password_edit.getText().toString().trim();

        Boolean isLoggedIn = false;

        if ( uservalue.equals("") && passvalue.equals("")) {

            text.setText("Bitte geben Sie die korrekten Daten ein");

        } else if ( isLoggedIn == false ) {

            new LoginGrades().execute(uservalue, passvalue);
            isLoggedIn = true;

        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private class LoginGrades extends AsyncTask<String, Void, Void> {

        String title,asi_href, asi;
        String login_url = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=1&category=auth.login&startpage=portal.vm&breadCrumbSource=portal";
        String referer = "https://qispos.hs-fulda.de/qisserver/rds?state=user&type=0";

        String noten_url = "https://qispos.hs-fulda.de/qisserver/rds?state=notenspiegelStudent&next=list.vm&nextdir=qispos/notenspiegel/student&createInfos=Y&struct=auswahlBaum&nodeID=auswahlBaum%7Cabschluss%3Aabschl%3D84%2Cstgnr%3D1&expand=0&asi=" + asi + "#auswahlBaum%7Cabschluss%3Aabschl%3D84%2Cstgnr%3D1";
        String referer_noten = "https://qispos .hs-fulda.de/qisserver/rds?state=notenspiegelStudent&next=tree.vm&nextdir=qispos/notenspiegel/student&navigationPosition=functions,notenspiegelStudent&breadcrumb=notenspiegel&topitem=functions&subitem=notenspiegelStudent&asi=" + asi;
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        Map<String, String> cookie;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Anmelden");
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {


            String uservalue = params[0];
            String passvalue = params[1];

            try{
                Connection.Response ck = Jsoup.connect(URLDecoder.decode(login_url, "utf-8"))
                        .method(Connection.Method.GET)
                        .execute();




                //TODO Fehlermanagement vervollständigen
                //TODO Bedingung für nicht erfolgreiche Anmeldung hinzufügen
                //TODO Bei erfolgreicher Anmeldung zur nächsten Activity wechseln
                //TODO Cookiemanagement zur Erhaltung der Session hinzufügen (JSESSIONID)

                //DOM-Objekt erzeugen nach Anfrage in Qispos mit Credentials
                Connection.Response res = Jsoup.connect(URLDecoder.decode(login_url, "utf-8"))
                        .data("asdf", uservalue,"fdsa",passvalue,"submit", "Anmelden")
                        .referrer(referer)
                        .userAgent(userAgent)
                        .method(Connection.Method.POST)
                        .execute();


                String cookie = res.cookie("JSESSIONID");
                Document docs = res.parse();


                //QISPOS DOM-Traversing und Regex-Extrahierung für asi-code
                //TODO Regex für ASI noch weiter spezifiern um mögliche Fehler zu verhindern
                Element e = docs.select("a[class=auflistung]").get(3);
                asi_href = e.attr("href");

                Pattern p = Pattern.compile("(?<=asi=).*");
                Matcher m = p.matcher(asi_href);

                if (m.find()) {
                    asi = m.group(0);
                }


                 res = Jsoup.connect(URLDecoder.decode("https://qispos.hs-fulda.de/qisserver/rds?state=notenspiegelStudent&next=list.vm&nextdir=qispos/notenspiegel/student&createInfos=Y&struct=auswahlBaum&nodeID=auswahlBaum%7Cabschluss%3Aabschl%3D84%2Cstgnr%3D1&expand=0&asi=" + asi + "#auswahlBaum%7Cabschluss%3Aabschl%3D84%2Cstgnr%3D1", "utf-8"))
                         .referrer(referer_noten)
                         .cookie("JSESSIONID", cookie)
                         .userAgent(userAgent)
                         .header("Host", "qispos.hs-fulda.de")
                         .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                         .header("Accept-Language", "de,en-US;q=0.7,en;q=0.3")
                         .header("Accept-Encoding", "*/*")
                         .header("Connection", "keep-alive")
                         .header("Upgrade-Insecure-Requests", "1")
                         .method(Connection.Method.GET)
                         .execute();


                Document d = res.parse();

                Element notentable = d.select("table[border=0]").get(1);

                Elements notenrow = notentable.select("tr");

                for (int i = 3; i < notenrow.size()-1; i++){

                    Element eintrag = notenrow.get(i);

                    Elements spalten = eintrag.select("td");

                    //title += spalten.text() + "\n";

                    String pruefungsnummer = spalten.get(0).text();
                    String pruefungsname = spalten.get(1).text();
                    String note = spalten.get(2).text();
                    String status = spalten.get(3).text();
                    String credits = spalten.get(4).text();
                    String versuch = spalten.get(5).text();
                    String datum = spalten.get(6).text();


                    gradeslist.add(new Grade(datum, pruefungsname, status, versuch, credits, note));

                }



            }catch(IOException e){
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Context context = getActivity();
            text =  (TextView) getView().findViewById(R.id.text);
            text.setText("Hallo");
            progressDialog.dismiss();


            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, glf);
            ft.commit();

        }
    }
}
