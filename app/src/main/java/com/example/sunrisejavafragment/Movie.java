package com.example.sunrisejavafragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Movie
{
    private String name;
    private String rating;
    private String votes;

    public Movie(String pName, String pRating, String pVotes) {
        name = pName;
        rating = pRating;
        votes = pVotes;
    }

    public String getName() {
        return name;
    }


    public String getRating() {
        return rating;
    }


    public String getVotes() {
        return votes;
    }



}

