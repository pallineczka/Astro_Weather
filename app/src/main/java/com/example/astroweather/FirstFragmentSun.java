package com.example.astroweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.TimeZone;

public class FirstFragmentSun extends Fragment {

    private static final String TAG = "Equation";
    Double dl1;
    Double sz1;
    String info = "";
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    int currentDay = localCalendar.get(Calendar.DATE);
    int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    int currentYear = localCalendar.get(Calendar.YEAR);
    int currentHour = localCalendar.get(Calendar.HOUR);
    int currentMinutes = localCalendar.get(Calendar.MINUTE);
    int currentSeconds = localCalendar.get(Calendar.SECOND);
    int timezone;
    boolean dayLightSaving;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment_sun, container, false);

        if (getArguments() != null) {
            setDlugosc(getArguments().getDouble("dl1"));
            setSzerokosc(getArguments().getDouble("sz1"));
        }
        else {
            Astro activity = (Astro) getActivity();
            setSzerokosc(activity.getSzerokosc());
            setDlugosc(activity.getDlugosc());
        }
        TextView text3 = view.findViewById(R.id.text3);


        if(sz1 != null && dl1 != null){

            timezone = (dl1.intValue()/15);
            Log.d(TAG, "\n\ntimezone: " + timezone + "\n\n");
            Log.d(TAG, "\n\nmonth: " + currentMonth + "\n\n");

            if(currentMonth > 3 && currentMonth < 11){
                dayLightSaving = true;
            } else dayLightSaving = false;

            AstroDateTime astroDateTime = new AstroDateTime(currentYear, currentMonth, currentDay, currentHour, currentMinutes, currentSeconds, timezone, dayLightSaving);
            AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, new AstroCalculator.Location(sz1, dl1));
            String sunrise = astroCalculator.getSunInfo().getSunrise().getHour() + ":" + astroCalculator.getSunInfo().getSunrise().getMinute() + ":" + astroCalculator.getSunInfo().getSunrise().getSecond() + " AM";
            String azimuthrise = String.format("%.2f°", astroCalculator.getSunInfo().getAzimuthRise());
            String sunset = astroCalculator.getSunInfo().getSunset().getHour() + ":" + astroCalculator.getSunInfo().getSunset().getMinute() + ":" + astroCalculator.getSunInfo().getSunset().getSecond() + " PM";
            String azimuthset = String.format("%.2f°", astroCalculator.getSunInfo().getAzimuthSet());
            String twilightevening = astroCalculator.getSunInfo().getTwilightEvening().getHour() + ":" + astroCalculator.getSunInfo().getTwilightEvening().getMinute() + ":" + astroCalculator.getSunInfo().getTwilightEvening().getSecond() + " PM";
            String twilightmorning = astroCalculator.getSunInfo().getTwilightMorning().getHour() + ":" + astroCalculator.getSunInfo().getTwilightMorning().getMinute() + ":" + astroCalculator.getSunInfo().getTwilightMorning().getSecond() + " AM";

            info = "";
            info = " Sunrise: " + sunrise + "\n\n" + " Azimuth rise: " + azimuthrise
                    + "\n\n" + " Sunset: " + sunset + "\n\n" + " Azimuth set: " + azimuthset
                    + "\n\n" + " Twilight morning: " + twilightmorning + "\n\n" + " Twilight evening: " + twilightevening;

            text3.setText("");
            if(text3.getText().equals("")){
                text3.setText(info);
            }
        }

        return view;
    }

    public void setSzerokosc(Double sz1) {
        this.sz1 = sz1;
    }

    public void setDlugosc(Double dl1) {
        this.dl1 = dl1;
    }
}
