package com.abmedia.hsfuldapp.frag;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.abmedia.hsfuldapp.Grade;
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
    private PopupWindow myPopupWindow;
    private LinearLayout myLayout;


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




        grades = GradesFragment.gradeslist;
        adapter = new MyArrayAdapter(getActivity(), R.layout.grades_item, grades);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.grades_item, container, false);
        final Context c = getActivity();
        myListView = new ListView(c);

        myListView.setAdapter(adapter);
        myLayout = (LinearLayout) getActivity().findViewById(R.id.linearmain);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(c.LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.grade_popup,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                myPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    myPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        myPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout

                myPopupWindow.showAtLocation(myLayout, Gravity.CENTER,0,0);

            }

        });
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
