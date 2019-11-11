package com.example.sunrisejavafragment;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunrisejavafragment.CSVReader;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.interrupted;

public class PerformWorkout extends AppCompatActivity {
    String WorkoutName;

    public Integer exerciseIndex = 0;

    List<String> exercises = new ArrayList<>();
    private CSVReader file_reader;

    LoadThread newThread = new LoadThread();
    boolean bool = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.perform_layout);



        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions, 1);



        Intent intent = getIntent();
        WorkoutName = intent.getStringExtra("WorkoutName");

        TextView name = (TextView)findViewById(R.id.workoutnameText);
        name.setText(WorkoutName);






        exercises = getFileData();

        final Integer size;
        size = exercises.size();


        TextView exercise = (TextView)findViewById(R.id.exercisenameText);


        exercise.setText(exercises.get(0));

        ImageView Cancel = (ImageView) findViewById(R.id.cancelBtn);
        Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                returnToMain();
            }
        });

        TextView CancelTxt = (TextView) findViewById(R.id.cancelTxt);
        CancelTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                returnToMain();
            }
        });


        ImageView Done = (ImageView) findViewById(R.id.nextBtn);
        Done.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {



                if(exerciseIndex < size-1) {
                    exerciseIndex++;
                    nextExercise(exerciseIndex);
                }else if(exerciseIndex == size-1){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "This is a Workout is now complete",
                            Toast.LENGTH_SHORT);

                    toast.show();

                    ((ProgressBar) findViewById(R.id.progressBar)).setProgress(size);
                    exerciseIndex = size;
                    TextView progressText = (TextView) findViewById(R.id.exerciseprogressText);
                    progressText.setText(size + "/" + exercises.size());
                    TextView exerciseText = (TextView) findViewById(R.id.exercisenameText);
                    exerciseText.setText("Complete!");

                }


            }
        });

        TextView DoneTxt = (TextView) findViewById(R.id.nextTxt);
        DoneTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(exerciseIndex < size-1) {
                    exerciseIndex++;
                    nextExercise(exerciseIndex);
                }else if(exerciseIndex == size-1){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "This is a Workout is now complete",
                            Toast.LENGTH_SHORT);

                    toast.show();

                    ((ProgressBar) findViewById(R.id.progressBar)).setProgress(size);
                    exerciseIndex = size;
                    TextView progressText = (TextView) findViewById(R.id.exerciseprogressText);
                    progressText.setText(size + "/" + exercises.size());
                    TextView exerciseText = (TextView) findViewById(R.id.exercisenameText);
                    exerciseText.setText("Complete!");

                }
            }
        });

        ImageView Previous = (ImageView) findViewById(R.id.previousBtn);
        Previous.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(exerciseIndex > 0) {
                    exerciseIndex--;
                    previousExercise(exerciseIndex);
                }
            }
        });

        TextView PreviousTxt = (TextView) findViewById(R.id.previousTxt);
        PreviousTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(exerciseIndex > 0) {
                    exerciseIndex--;
                    previousExercise(exerciseIndex);
                }
            }
        });
        ((ProgressBar) findViewById(R.id.progressBar)).setMax(exercises.size());
        newThread.execute();
    }

    private void returnToMain(){
        Intent myIntent = new Intent(PerformWorkout.this, MainActivity.class);
        startActivity(myIntent);

        bool = true;
    }

    private Integer previousExercise(Integer exerciseIndex){





            TextView exercise = (TextView)findViewById(R.id.exercisenameText);

            exercise.setText(exercises.get(exerciseIndex));


        return exerciseIndex;
    }

    private Integer nextExercise(Integer exerciseIndex){




            TextView exercise = (TextView) findViewById(R.id.exercisenameText);

            exercise.setText(exercises.get(exerciseIndex));
            return exerciseIndex;

    }

    private List<String> getFileData()
    {
        List<String> return_list = null;
        CSVReader reader = new CSVReader();

        try
        {
            return_list = reader.getWorkouts(this.getAssets().open("text_files/workouts.txt"), this, WorkoutName);
        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }



        return return_list;
    }


    public class LoadThread extends AsyncTask
    {


        @Override
        protected Object doInBackground(Object[] objects) {


            while (bool == false) {
                if (interrupted()) {
                    return null;
                }
                publishProgress(exerciseIndex);

            }
            return null;
        }

        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            int progressInt = (Integer) (values[0]);


            ((ProgressBar) findViewById(R.id.progressBar)).setProgress(progressInt);
            TextView progressText = (TextView) findViewById(R.id.exerciseprogressText);
            progressText.setText(progressInt + "/" + exercises.size());



        }

    }


}

