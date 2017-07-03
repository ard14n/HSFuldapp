package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abmedia.hsfuldapp.R;
import com.abmedia.hsfuldapp.helper.RegexHelper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<Sys2TeachFragment.News> news_list;
    private ListView myListView;
    private MyArrayAdapter adapter;

    private static class News {
        String category, date, author, title, text;


        public News(String category, String title, String date, String author, String text){
            this.category = category;
            this.title = title;
            this.date = date;
            this.author = author;
            this.text = text;

        }
    }

    private static class ViewHolder{
        TextView newsCategory;
        TextView newsDate;
        TextView newsTitle;
        TextView newsAuthor;
        TextView newsText;
    }

    private class MyArrayAdapter extends ArrayAdapter {

        public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            News current_news = news_list.get(position);
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.sys2teach_item, null, false);
            }
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.newsCategory =
                    (TextView)convertView.findViewById(R.id.news_category);
            viewHolder.newsTitle =
                    (TextView)convertView.findViewById(R.id.news_title);
            viewHolder.newsText =
                    (TextView)convertView.findViewById(R.id.news_text);
            viewHolder.newsDate =
                    (TextView)convertView.findViewById(R.id.news_date);
            viewHolder.newsAuthor =
                    (TextView)convertView.findViewById(R.id.news_author);

            convertView.setTag(viewHolder);

            TextView newsCategory =
                    ((Sys2TeachFragment.ViewHolder)convertView.getTag()).newsCategory;
            TextView newsTitle =
                    ((Sys2TeachFragment.ViewHolder)convertView.getTag()).newsTitle;
            TextView newsText =
                    ((Sys2TeachFragment.ViewHolder)convertView.getTag()).newsText;
            TextView newsDate =
                    ((Sys2TeachFragment.ViewHolder)convertView.getTag()).newsDate;
            TextView newsAuthor =
                    ((Sys2TeachFragment.ViewHolder)convertView.getTag()).newsAuthor;


            newsCategory.setText(current_news.category);
            newsDate.setText(current_news.date);
            newsAuthor.setText(current_news.author);
            newsTitle.setText(current_news.title);
            newsText.setText(current_news.text);

            return convertView;

        }
    }

    private OnFragmentInteractionListener mListener;

    public Sys2TeachFragment() {
        // Required empty public constructor

    }

    public static Sys2TeachFragment newInstance() {
        Sys2TeachFragment fragment = new Sys2TeachFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myListView = new ListView(getActivity());

        news_list = new ArrayList<>();
        adapter = new MyArrayAdapter(getActivity(), R.layout.sys2teach_item, news_list);
        new Sys2TeachNews().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.sys2teach_item, container, false);
        Context c = getActivity();
        myListView = new ListView(c);

        myListView.setAdapter(adapter);
        v.addView(myListView);

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

                //Funktioniert. Passwort und Benutzername eingeben zum Testen ansonsten crasht App
                username = "";
                password = "";

                Connection.Response res = Jsoup.connect(url)
                        .timeout(1000)
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
                        .timeout(1000)
                        .cookie("JSESSIONID", cookie)
                        .followRedirects(true)
                        .header("Connection", "keep-alive")
                        .header("Upgrade-Insecure-Requests", "1")
                        .data("j_username", username_encrypted, "j_password", password_encrypted)
                        .method(Connection.Method.POST)
                        .execute();

                System.out.println(res.body());

                res = Jsoup.connect("https://www.system2teach.de/hfg/ea/show_all_notices.jsp")
                        .timeout(1000)
                        .cookie("JSESSIONID", cookie)
                        .followRedirects(true)
                        .method(Connection.Method.POST)
                        .execute();


                Document doc2 = res.parse();

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

                    news_list.add(new News(kategorie, titel, datum, autor, ""));
                }


                //System.out.println(table.html());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(String result){

           progressDialog.dismiss();
            adapter.notifyDataSetChanged();


        }
    }
}
