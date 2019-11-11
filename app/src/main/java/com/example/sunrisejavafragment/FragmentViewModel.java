package com.example.sunrisejavafragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.sunrisejavafragment.calc.GeoLocation;

import java.util.TimeZone;

public class FragmentViewModel extends ViewModel
{
    TimeZone tz = TimeZone.getDefault();
    private GeoLocation location =  new GeoLocation("Melbourne", -37.50, 145.01, tz);

    public GeoLocation getLocation()
    {
        return location;
    }

    public void setLocation(GeoLocation location)
    {
        this.location = location;
        System.out.println(location.getLocationName());
    }
}
