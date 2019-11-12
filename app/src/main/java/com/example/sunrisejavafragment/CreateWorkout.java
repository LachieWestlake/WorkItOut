package com.example.sunrisejavafragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sunrisejavafragment.R;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.IndicPositionalCategory.LEFT;

public class CreateWorkout extends AppCompatActivity {

    private TableLayout TableLayout1;
    private TableRow AddRow;
    public Integer RowCount = 0;

    private CSVReader file_reader;
    public List<EditText> allQTs = new ArrayList<EditText>();
    public List<EditText> allEDs = new ArrayList<EditText>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_layout);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);



        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions, 1);

        TableRow AddRow = (TableRow) findViewById(R.id.AddRow);
        AddRow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              addRow();
            }
        });


        ImageView Cancel = (ImageView) findViewById(R.id.cancelBtn);
        Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                returnToMain();
            }
        });

        ImageView DeleteImage = (ImageView) findViewById(R.id.deleteImage);
        DeleteImage.setOnClickListener(new View.OnClickListener() {

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


        ImageView Done = (ImageView) findViewById(R.id.doneBtn);
        Done.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                saveWorkout();
            }
        });

        TextView DoneTxt = (TextView) findViewById(R.id.doneTxt);
        DoneTxt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                saveWorkout();
            }
        });


    }


    public void writeToFile(String input_string)
    {
        File locations_file = new File(getFilesDir(), "workouts.txt");


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(locations_file, true));
            writer.write(input_string + '\n');
            writer.flush();
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeExercises(String input_string, String exercise)
    {
        File locations_file = new File(getFilesDir(), input_string + ".txt");


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(locations_file, true));
            writer.write(exercise + '\n');
            writer.flush();
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

        public void writeToExerciseFile(String input_string,List<String> exerciseArray)
    {
        file_reader = new CSVReader();

        String path = this.getFilesDir().getAbsolutePath() + "/" + input_string;

        if (!file_reader.FileExists(path)){

            File directory = new File (getFilesDir().getAbsolutePath(), "/" + input_string + ".txt");
            try {
                FileWriter writer = new FileWriter(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        System.out.println(exerciseArray.size());
        for(int i = 0; i < exerciseArray.size(); i++) {

            writeExercises(input_string, exerciseArray.get(i).toString());
        }
        returnToMain();

    }


    private void saveWorkout(){
        EditText workoutNameText = (EditText) findViewById(R.id.workoutNameTxt);
        String WorkoutName = workoutNameText.getText().toString();

        System.out.println(WorkoutName.toString());

        writeToFile(WorkoutName);

        List<String> Exercises = new ArrayList<>();


        System.out.println("AllEdSZIE");
        System.out.println(allEDs.size());

        String[] ETResults = new String[allEDs.size()];
        String[] QTResults = new String[allQTs.size()];

        for(int i=0; i < allEDs.size(); i++){
            ETResults[i] = allEDs.get(i).getText().toString();
            QTResults[i] = allQTs.get(i).getText().toString();
        }

        for(int i = 0; i < allEDs.size(); i++){
            Exercises.add(ETResults[i] + " x" + QTResults[i]);

        }



        writeToExerciseFile(WorkoutName, Exercises);




    }

    private void PickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");

        String[] mimeTypes = {"image/jpeg", "image/png"};

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, 1000);




    }

    private void returnToMain(){
        Intent myIntent = new Intent(CreateWorkout.this, MainActivity.class);
        startActivity(myIntent);
    }


    private void addRow(){



        TableLayout1 = (TableLayout)this.findViewById(R.id.TableLayout02);


        TableRow.LayoutParams tableRow = new TableRow.LayoutParams();


        TableRow.LayoutParams editParams = new TableRow.LayoutParams();
        editParams.height = 100;
        editParams.width = 100;


        TableRow.LayoutParams goParams = new TableRow.LayoutParams();
        goParams.height = 100;
        goParams.width = 100;

        final TableRow row = new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#cfd8de"));
        TableLayout1.addView(row);


       final ImageView editButton = new ImageView(this);
        editButton.setImageResource(R.drawable.addphoto);
        editButton.setColorFilter(getResources().getColor(android.R.color.white));
        editButton.setLayoutParams(editParams);





        final ImageView goButton = new ImageView(this);
        goButton.setImageResource(R.drawable.clear);
        goButton.setLayoutParams(goParams);





        final EditText rowTextView = new EditText(this);
        rowTextView.setWidth(160);

        rowTextView.setId(RowCount);
        rowTextView.setTextColor(getResources().getColor(android.R.color.black));




        EditText quantityTextView = new EditText(this);
        quantityTextView.setTextColor(getResources().getColor(android.R.color.black));
        quantityTextView.setWidth(60);

        //row.addView(editButton);
        row.addView(editButton);
        row.addView(rowTextView);
        row.addView(quantityTextView);
        row.addView(goButton);

        allQTs.add(quantityTextView);
        allEDs.add(rowTextView);
        System.out.println("AllEdSZIE");
        System.out.println(allEDs.size());

        row.setLayoutParams(tableRow);

        final TableRow greyRow = new TableRow(this);
        greyRow.setBackgroundColor(getResources().getColor(android.R.color.white));
        greyRow.setPadding(0,20,0,0);
        TableLayout1.addView(greyRow);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                TableLayout1.removeView(row);
                TableLayout1.removeView(greyRow);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
               PickFromGallery();
            }
        });
    }

}
