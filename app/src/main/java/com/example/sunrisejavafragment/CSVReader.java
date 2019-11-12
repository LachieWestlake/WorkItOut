package com.example.sunrisejavafragment;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    List<String> getWorkouts = new ArrayList<String>();

    public List<String> getWorkouts(InputStream filepath, Context context, String ref) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions, 1);




        File locations_file = new File(Environment.getExternalStorageDirectory().getPath() + "/sunrise_app", "workouts.txt");

        String workoutName = new String();

        if(ref == "workout") {
            read_file(context, "workouts.txt");
        }
        else{
            read_file(context, ref + ".txt");
        }


        return getWorkouts;
    }

    private void requestPermissions(String[] permissions, int i) {
    }

    public boolean FileExists(String file_location)
    {
        File tmpDir = new File(file_location);
        return tmpDir.exists();
    }

    private void read_file(Context context, String filename) {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String sb = new String();
            String text;

            while ((text = br.readLine()) != null) {
                sb = text;
                getWorkouts.add(sb.toString());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
