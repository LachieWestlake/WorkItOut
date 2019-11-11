package com.example.sunrisejavafragment.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sunrisejavafragment.CSVReader;
import com.example.sunrisejavafragment.CreateWorkout;
import com.example.sunrisejavafragment.PerformWorkout;
import com.example.sunrisejavafragment.FragmentViewModel;
import com.example.sunrisejavafragment.MainActivity;
import com.example.sunrisejavafragment.R;
import com.example.sunrisejavafragment.calc.GeoLocation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentViewModel model;
    public List<TextView>allEDs = new ArrayList<TextView>();
    private OnFragmentInteractionListener mListener;

    private TableLayout TableLayout1;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(GeoLocation param1) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(FragmentViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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

    private List<String> getFileData()
    {
        List<String> return_list = null;
        CSVReader reader = new CSVReader();

        try
        {
            return_list = reader.getWorkouts(getActivity().getAssets().open("text_files/workouts.txt"), getContext(), "workout");
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }



        return return_list;
    }

    private void createRow(String workoutName){
        TableLayout1 = (TableLayout)getView().findViewById(R.id.TableLayout01);


        TableRow.LayoutParams tableRow = new TableRow.LayoutParams();
        tableRow.width = TableRow.LayoutParams.MATCH_PARENT;
        tableRow.height = TableRow.LayoutParams.WRAP_CONTENT;

        TableRow.LayoutParams editParams = new TableRow.LayoutParams();
        editParams.height = 68;
        editParams.width = 68;

        TableRow.LayoutParams goParams = new TableRow.LayoutParams();
        goParams.height = 68;
        goParams.width = 68;

        TableRow row = new TableRow(getContext());
        row.setBackgroundColor(Color.parseColor("#cfd8de"));
        TableLayout1.addView(row);


        ImageView editButton = new ImageView(getContext());
        editButton.setImageResource(R.drawable.create);

        editButton.setLayoutParams(editParams);

        ImageView goButton = new ImageView(getContext());
        goButton.setImageResource(R.drawable.next);
        goButton.setLayoutParams(goParams);


        final TextView rowTextView = new TextView(getContext());

        rowTextView.setGravity(Gravity.LEFT);
        rowTextView.setPadding(4,8,4,8);
        rowTextView.setTextColor(getResources().getColor(android.R.color.black));
        rowTextView.setTextSize(24);
        rowTextView.setText(workoutName);

        row.addView(editButton);
        row.addView(rowTextView);
        row.addView(goButton);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String textvalue = rowTextView.getText().toString();
                Intent myIntent = new Intent(getActivity(), PerformWorkout.class);
                myIntent.putExtra("WorkoutName", textvalue);
                startActivity(myIntent);
            }
        });


        row.setLayoutParams(tableRow);

        TableRow greyRow = new TableRow(getContext());
        greyRow.setBackgroundColor(getResources().getColor(android.R.color.white));
        greyRow.setPadding(0,20,0,0);
        TableLayout1.addView(greyRow);
    }

    private void gatherIntent(){

    }

    private void addWorkout(){
        Intent myIntent = new Intent(getActivity(), CreateWorkout.class);
        startActivity(myIntent);
    }

    private void initializeUI(View current_view) {



        List<String> workouts = new ArrayList<>();

        workouts = getFileData();


        for(int i=0;i< workouts.size();i++){
            createRow(workouts.get(i));
            System.out.println(workouts.get(i));

        }



        TableLayout1 = (TableLayout)getView().findViewById(R.id.TableLayout01);


        TableRow.LayoutParams tableRow = new TableRow.LayoutParams();
        tableRow.width = TableRow.LayoutParams.MATCH_PARENT;
        tableRow.height = TableRow.LayoutParams.WRAP_CONTENT;


        TableRow.LayoutParams editParams = new TableRow.LayoutParams();
        editParams.height = 68;
        editParams.width = 68;

        TableRow.LayoutParams goParams = new TableRow.LayoutParams();
        goParams.height = 68;
        goParams.width = 68;


        TableRow greyRow = new TableRow(getContext());
        greyRow.setBackgroundColor(getResources().getColor(android.R.color.white));
        greyRow.setPadding(0,20,0,0);
        TableLayout1.addView(greyRow);


        TableRow row = new TableRow(getContext());
        row.setBackgroundColor(getResources().getColor(android.R.color.black));

        TableLayout1.addView(row);


        ImageView editButton = new ImageView(getContext());
        editButton.setImageResource(R.drawable.add);
        editButton.setColorFilter(getResources().getColor(android.R.color.white));
        editButton.setLayoutParams(editParams);


        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addWorkout();
            }
        });


        TextView rowTextView = new TextView(getContext());

        rowTextView.setGravity(Gravity.CENTER);
        rowTextView.setPadding(4,4,4,4);
        rowTextView.setTextColor(getResources().getColor(android.R.color.white));

        rowTextView.setTextSize(24);
        rowTextView.setText("Add new Workout");

        row.addView(editButton);
        row.addView(rowTextView);



        row.setLayoutParams(tableRow);

        allEDs.add(rowTextView);

    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI(getView());

    }


    @Override
    public void onResume()
    {
        super.onResume();
    }


}
