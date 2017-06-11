package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abmedia.hsfuldapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MensaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MensaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MensaFragment extends Fragment {

    private ProgressDialog pDialog;
    private String inboxJson;
    private String myJSON;
    private static String url;
    private OnFragmentInteractionListener mListener;

    private static class Food {
        String category, name, description, price;
        Bitmap photo;

        public Food(String category, String name, String description, String price, Bitmap photo){
            this.category = category;
            this.name = name;
            this.description = description;
            this.photo = photo;
            this.price = price;
        }
    }
    private static class ViewHolder{
        TextView foodCategory;
        TextView foodName;
        TextView foodDescription;
        ImageView foodPhoto;
        TextView foodPrice;
    }

    public Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    private class MyArrayAdapter extends ArrayAdapter{

        public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Food currentFood = menu.get(position);
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.mensa_item, null, false);
            }
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.foodCategory =
                    (TextView)convertView.findViewById(R.id.food_category);
            viewHolder.foodName =
                    (TextView)convertView.findViewById(R.id.food_name);
            viewHolder.foodDescription =
                    (TextView)convertView.findViewById(R.id.food_description);
            viewHolder.foodPhoto =
                    (ImageView)convertView.findViewById(R.id.food_photo);
            viewHolder.foodPrice =
                    (TextView)convertView.findViewById(R.id.food_price);

            convertView.setTag(viewHolder);

            TextView foodCategory =
                    ((ViewHolder)convertView.getTag()).foodCategory;
            TextView foodName =
                    ((ViewHolder)convertView.getTag()).foodName;
            TextView foodDescription =
                    ((ViewHolder)convertView.getTag()).foodDescription;
            ImageView foodPhoto =
                    ((ViewHolder)convertView.getTag()).foodPhoto;
            TextView foodPrice =
                    ((ViewHolder)convertView.getTag()).foodPrice;



           //TODO Für die Gerichte einer einzelnen Kategorie nur eine Überschrift festlegen



            foodName.setText(currentFood.name);
            foodDescription.setText(currentFood.description);
            foodPhoto.setImageBitmap(currentFood.photo);
            foodPrice.setText(currentFood.price);
            return convertView;
        }
    }


    private ArrayList<Food> menu;
    private ListView myListView;
    private MyArrayAdapter adapter;



    public MensaFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MensaFragment.
     */
    public static MensaFragment newInstance() {
        MensaFragment fragment = new MensaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myListView = new ListView(getActivity());

        menu = new ArrayList<>();
        adapter = new MyArrayAdapter(getActivity(), R.layout.mensa_item, menu);
        new GetDataJSON().execute();
    }

    private class GetDataJSON extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Void... arg0) {
            try {
                inboxJson = Jsoup.connect("http://91.205.173.172/android/mensa/mensa.php")
                        .timeout(1000000)
                        .header("Accept", "text/javascript")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0")
                        .get()
                        .body()
                        .text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONArray array = new JSONArray(inboxJson);

                for (int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);

                    JSONObject obj2 = obj.getJSONObject("gericht");

                    String name = obj2.getString("gerichtsname");
                    String datum = obj2.getString("datum");
                    String beschreibung = obj2.getString("gerichtsbeschreibung");
                    String bild = obj2.getString("bild");
                    String kategorie = obj2.getString("kategorie");
                    String preis = obj2.getString("preis");



                    Bitmap photo = getbmpfromURL(bild);

                    menu.add(new Food(kategorie, name, beschreibung, preis, photo));

                }



            } catch (JSONException e) {
                Log.e(TAG, "" + e);
            }


            return null;
        }


        protected void onPostExecute(String result){

            adapter.notifyDataSetChanged();


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.mensa_item, container, false);
        Context c = getActivity();
        myListView = new ListView(c);

        myListView.setAdapter(adapter);
        v.addView(myListView);
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
}
