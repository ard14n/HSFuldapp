package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abmedia.hsfuldapp.R;
import com.abmedia.hsfuldapp.helper.RegexHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static org.jsoup.helper.HttpConnection.connect;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sys2TeachFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sys2TeachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sys2TeachFragment extends Fragment {

    public TextView meldung;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Sys2TeachFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sys2Teach.
     */
    // TODO: Rename and change types and number of parameters
    public static Sys2TeachFragment newInstance(String param1, String param2) {
        Sys2TeachFragment fragment = new Sys2TeachFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sys2_teach, container, false);

        meldung = (TextView) v.findViewById(R.id.info_text);
        new Sys2TeachNews().execute();


        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class Sys2TeachNews extends AsyncTask<Void, String, String> {

        private String username, password, cookie, test, titel, datum, kategorie, autor;

        private String url = "https://www.system2teach.de/hfg/logon.jsp";
        private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0";
        private String referer = "https://www.system2teach.de/hfg/login.jsp";

        RegexHelper regex = new RegexHelper();

        private ProgressDialog progressDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Anmelden");
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {


            try {

                username = "";
                password = "";

                Connection.Response res = Jsoup.connect(url)

                        .followRedirects(true)
                        .userAgent(userAgent)
                        .referrer(referer)
                        .data("ACTION","login", "j_username", username, "j_password", password, "LOGIN", "Anmelden")
                        .method(Connection.Method.POST)
                        .execute();



                cookie = res.cookie("JSESSIONID");

                Document doc = res.parse();

                //debugging
                System.out.println("Is URL going to redirect : " + res.hasHeader("location"));
                System.out.println("Target : " + res.header("location"));
                System.out.println("BODY " + res.body());

                String body = res.body();

                Elements input = doc.getElementsByTag("input");

                String username_encrypted = input.get(0).attr("value");
                String password_encrypted = input.get(1).attr("value");

                //debugging
                System.out.println("*************************");
                System.out.println(username_encrypted);
                System.out.println(password_encrypted);



                res = Jsoup.connect("https://www.system2teach.de/hfg/protected/j_security_check")
                        .cookie("JSESSIONID", cookie)
                        .followRedirects(true)
                        .header("Connection", "keep-alive")
                        .header("Upgrade-Insecure-Requests", "1")
                        .data("j_username", username_encrypted, "j_password", password_encrypted)
                        .method(Connection.Method.POST)
                        .execute();

                res = Jsoup.connect("https://www.system2teach.de/hfg/ea/show_all_notices.jsp")
                        .cookie("JSESSIONID", cookie)
                        .followRedirects(true)
                        .method(Connection.Method.POST)
                        .execute();


                Document doc2 = res.parse();

                /*Elements table = doc2.select("table[border=0]");

                Elements dls = table.select("dl");

                String kategorie = "";

                for (int i = 0; i < 3; i++) {

                    Element current_dl = dls.get(i);

                    kategorie = current_dl.select("span").get(0).text();

                }*/

                Element table = doc2.select("table[width=100%]").get(1);

                Elements trs = table.getElementsByTag("tr");

                for (int i = 1; i < trs.size(); i++) {

                    Element current_tr = trs.get(i);

                    Elements tds = current_tr.getElementsByTag("td");



                    titel = tds.get(3).text();
                    datum = tds.get(1).text();
                    kategorie = tds.get(2).text();
                    autor = tds.get(4).text();

                    //debugging
                    System.out.println("------------------------------------------------");
                    System.out.println("Kategorie " + kategorie);
                    System.out.println("Datum " + datum);
                    System.out.println("Titel " + titel);
                    System.out.println("Autor " + autor);
                }




                //System.out.println(table.html());




            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        protected void onPostExecute(String result){

           progressDialog.dismiss();

            meldung.setText(test);


        }
    }
}
