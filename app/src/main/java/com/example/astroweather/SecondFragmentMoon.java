package com.example.astroweather;

import android.os.Bundle;
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

public class SecondFragmentMoon extends Fragment {

    private static final String TAG = "Equation";
    Double dl1;
    Double sz1;
    String info = "";
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    int currentDay = localCalendar.get(Calendar.DATE);
    int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    int currentYear = localCalendar.get(Calendar.YEAR);
    int currentHour = localCalendar.get(Calendar.HOUR) - 1;
    int currentMinutes = localCalendar.get(Calendar.MINUTE);
    int currentSeconds = localCalendar.get(Calendar.SECOND);
    int timezone;
    boolean dayLightSaving;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_fragment_moon, container, false);

        if (getArguments() != null) {
            setDlugosc(getArguments().getDouble("dl1"));
            setSzerokosc(getArguments().getDouble("sz1"));
            Log.d(TAG, "\n\ndlugosc: " + dl1 + "\n\n");
        }
        else {
            Astro activity = (Astro) getActivity();
            setSzerokosc(activity.getSzerokosc());
            setDlugosc(activity.getDlugosc());

        }

        TextView text3 = view.findViewById(R.id.text3);
        Log.d(TAG, "\n\ndlugosc: " + dl1 + ""+ sz1);

        if(sz1 != null && dl1 != null){

            timezone = (dl1.intValue()/15);
            Log.d(TAG, "\n\ntimezone: " + timezone + "\n\n");
            Log.d(TAG, "\n\nmonth: " + currentMonth + "\n\n");

            if(currentMonth > 3 && currentMonth < 11){
                dayLightSaving = true;
            } else dayLightSaving = false;

            AstroDateTime astroDateTime = new AstroDateTime(currentYear, currentMonth, currentDay, currentHour, currentMinutes, currentSeconds, timezone, dayLightSaving);
            AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, new AstroCalculator.Location(sz1, dl1));
            String moonrise = astroCalculator.getMoonInfo().getMoonrise().getHour() + ":" + astroCalculator.getMoonInfo().getMoonrise().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonrise().getSecond();
            String moonset = astroCalculator.getMoonInfo().getMoonset().getHour() + ":" + astroCalculator.getMoonInfo().getMoonset().getMinute() + ":" + astroCalculator.getMoonInfo().getMoonset().getSecond();
            String moon = astroCalculator.getMoonInfo().getNextNewMoon().getDay() + "." + astroCalculator.getMoonInfo().getNextNewMoon().getMonth() + "." + astroCalculator.getMoonInfo().getNextNewMoon().getYear();
            String fullmoon = astroCalculator.getMoonInfo().getNextFullMoon().getDay() + "." + astroCalculator.getMoonInfo().getNextFullMoon().getMonth() + "." + astroCalculator.getMoonInfo().getNextFullMoon().getYear();
            String illumination = String.format("%.2f", astroCalculator.getMoonInfo().getIllumination() * 100);
            String age = String.format("%.0f", astroCalculator.getMoonInfo().getAge() *(-1));

            info = " Moonrise: " + moonrise + "\n\n" + " Moonset: " + moonset + "\n\n"
                    + " Next new moon: " + moon + "\n\n" + " Next full moon: " + fullmoon + "\n\n"
                    + " Phase: " + illumination + "%" + "\n\n" + " Age synod month: " + age + " days";

            text3.setText(info);
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
