package com.abmedia.hsfuldapp.frag;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.abmedia.hsfuldapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MensaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MensaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MensaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private String[] food = {
            "Nudeln",
            "Dönerteller",
            "Pizza",
            "Schnitzel",
            "Salat",
            "Nudeln",
            "Dönerteller",
            "Pizza",
            "Schnitzel",
            "Salat",
            "Nudeln",
            "Dönerteller",
            "Pizza",
            "Schnitzel",
            "Salat",
            "Nudeln",
            "Dönerteller",
            "Pizza",
            "Schnitzel",
            "Salat",
            "Nudeln",
            "Dönerteller",
            "Pizza",
            "Schnitzel",
            "Salat"
    };
    private ArrayAdapter<String> cheeseAdapter;

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.mensa_item, container, false);
        Context c = getActivity();
        ListView cheeseList = new ListView(c);
        cheeseAdapter =
                new ArrayAdapter<String>(c, R.layout.mensa_item, R.id.food_name, food);

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
