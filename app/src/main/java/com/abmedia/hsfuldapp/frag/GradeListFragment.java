package com.abmedia.hsfuldapp.frag;

import android.content.Context;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GradeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GradeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradeListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static class Grade {
        private String datum, pruefung, status, versuch, credits, note;

        public Grade(String datum, String pruefung, String status, String versuch, String credits, String note){
            this.datum = datum;
            this.pruefung = pruefung;
            this.status = status;
            this.versuch = versuch;
            this.credits = credits;
            this.note = note;
        }
    }

    private static class ViewHolder{
        TextView gradeDatum;
        TextView gradePruef;
        TextView gradeStatus;
        TextView gradeVersuch;
        TextView gradeCredits;
        TextView gradeNote;
    }

    private ArrayList<Grade> grades;
    private ListView myListView;
    private MyArrayAdapter adapter;


    private class MyArrayAdapter extends ArrayAdapter {

        public MyArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Grade currentFood = grades.get(position);
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.grades_item, null, false);
            }
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.gradeDatum =
                    (TextView)convertView.findViewById(R.id.grades_datum);
            viewHolder.gradePruef =
                    (TextView)convertView.findViewById(R.id.grades_pruefung);
            viewHolder.gradeStatus =
                    (TextView)convertView.findViewById(R.id.grades_status);
            viewHolder.gradeVersuch =
                    (TextView)convertView.findViewById(R.id.grades_versuch);
            viewHolder.gradeCredits =
                    (TextView) convertView.findViewById(R.id.grades_credits);
            viewHolder.gradeNote =
                    (TextView)convertView.findViewById(R.id.grades_note);

            convertView.setTag(viewHolder);

            TextView gradeDatum =
                    ((ViewHolder)convertView.getTag()).gradeDatum;
            TextView gradePruef =
                    ((ViewHolder)convertView.getTag()).gradePruef;
            TextView gradeStatus =
                    ((ViewHolder)convertView.getTag()).gradeStatus;
            TextView gradeVersuch =
                    ((ViewHolder)convertView.getTag()).gradeVersuch;
            TextView gradeCredits =
                    ((ViewHolder)convertView.getTag()).gradeCredits;
            TextView gradeNote =
                    ((ViewHolder)convertView.getTag()).gradeNote;

            gradeDatum.setText(currentFood.datum);
            gradePruef.setText(currentFood.pruefung);
            gradeStatus.setText(currentFood.status);
            gradeVersuch.setText(currentFood.versuch);
            gradeCredits.setText(currentFood.credits);
            gradeNote.setText(currentFood.note);
            return convertView;
        }
    }
    public GradeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GradeListFragment.
     */
    public static GradeListFragment newInstance() {
        GradeListFragment fragment = new GradeListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myListView = new ListView(getActivity());

        grades = new ArrayList<>();
        adapter = new MyArrayAdapter(getActivity(), R.layout.grades_item, grades);
        //new GetDataJSON().execute(); //TODO JSON Klasse erstellen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.grades_item, container, false);
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
