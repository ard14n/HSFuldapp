package com.abmedia.hsfuldapp.frag;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abmedia.hsfuldapp.Grades;
import com.abmedia.hsfuldapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

import java.io.IOException;
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
        View v = inflater.inflate(R.layout.fragment_grades, container, false);

        Button btn = (Button) v.findViewById(R.id.button);
        btn.setOnClickListener(this);
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

        if ( uservalue.equals("") && passvalue.equals("")) {

            text.setText("Bitte geben Sie die korrekten Daten ein");

        } else {
            new LoginGrades().execute(uservalue, passvalue);
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



        @Override
        protected Void doInBackground(String... params) {


            String uservalue = params[0];
            String passvalue = params[1];

            try{

                //TODO Fehlermanagement vervollständigen
                //TODO Bedingung für nicht erfolgreiche Anmeldung hinzufügen
                //TODO Bei erfolgreicher Anmeldung zur nächsten Activity wechseln
                //TODO Cookiemanagement zur Erhaltung der Session hinzufügen (JSESSIONID)

                //DOM-Objekt erzeugen nach Anfrage in Qispos mit Credentials
                Document docs = Jsoup.connect(login_url)
                        .data("asdf", uservalue,"fdsa",passvalue,"submit", "Anmelden")
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

            Context context = getActivity();
            text =  (TextView) getView().findViewById(R.id.text);
            text.setText(title);

        }
    }
}
