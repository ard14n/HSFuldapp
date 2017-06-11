package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abmedia.hsfuldapp.R;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
        String category, name, description, price, photoUri;

        public Food(String category, String name, String description, String price, String photoUri){
            this.category = category;
            this.name = name;
            this.description = description;
            this.photoUri = photoUri;
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

            foodCategory.setText(currentFood.category);
            foodName.setText(currentFood.name);
            foodDescription.setText(currentFood.description);
            foodPhoto.setImageURI(Uri.parse(currentFood.photoUri));
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
            System.out.println(inboxJson);

            // test items
            //TODO Bild wird nicht angezeigt wenn es von einer URI kommt
            Food item = new Food("Hauptspeise", "Rahmgeschnetzeltes vom Schwein mit Champignons", "mit Beilage nach Wahl", "3,20€", "http://www.maxmanager.de/daten-extern/sw-giessen/html/fotos/s275837e1_10.jpg");
            Food item2 = new Food("Hauptspeise", "Rahmgeschnetzeltes vom Schwein mit Champignons", "mit Beilage nach Wahl", "3,20€", "http://www.maxmanager.de/daten-extern/sw-giessen/html/fotos/s275837e1_10.jpg");
            Food item3 = new Food("Hauptspeise", "Rahmgeschnetzeltes vom Schwein mit Champignons", "mit Beilage nach Wahl", "3,20€", "http://www.maxmanager.de/daten-extern/sw-giessen/html/fotos/s275837e1_10.jpg");
            menu.add(item);
            menu.add(item2);
            menu.add(item3);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
