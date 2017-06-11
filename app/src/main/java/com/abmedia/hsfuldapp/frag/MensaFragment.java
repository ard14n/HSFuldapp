package com.abmedia.hsfuldapp.frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abmedia.hsfuldapp.R;

import org.jsoup.Jsoup;

import java.io.IOException;

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
        String name, description, price;

        public Food(String name, String description, String price){
            this.name = name;
            this.description = description;
            this.price = price;
        }
    }
    private static class ViewHolder{
        TextView foodName;
        TextView foodDescription;
        TextView foodPrice;
    }

    private Food[] menu = {
            new Food("Nudeln", "mit Tomatensauce", "4,20€")
    };



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



        new GetDataJSON().execute();
    }

    private class GetDataJSON extends AsyncTask<Void, Void, Void>{

        @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(Void... arg0){

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
                return null;
            }

            protected void onPostExecute(String stream){



            }
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.mensa_item, container, false);
        Context c = getActivity();
        ListView cheeseList = new ListView(c);

        ArrayAdapter<Food> cheeseAdapter = new ArrayAdapter<Food>(c, 0, menu){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                Food currentFood = menu[position];
                if(convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.mensa_item, null, false);
                }
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.foodName =
                        (TextView)convertView.findViewById(R.id.food_name);
                viewHolder.foodDescription =
                        (TextView)convertView.findViewById(R.id.food_description);
                viewHolder.foodPrice =
                        (TextView)convertView.findViewById(R.id.food_price);

                convertView.setTag(viewHolder);

                TextView foodName =
                        ((ViewHolder)convertView.getTag()).foodName;
                TextView foodDescription =
                        ((ViewHolder)convertView.getTag()).foodDescription;
                TextView foodPrice =
                        ((ViewHolder)convertView.getTag()).foodPrice;

                foodName.setText(currentFood.name);
                foodDescription.setText(currentFood.description);
                foodPrice.setText(currentFood.price);
                return convertView;
            }

        };

        cheeseList.setAdapter(cheeseAdapter);
        v.addView(cheeseList);
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
