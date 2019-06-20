package com.example.astroweather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Astro extends AppCompatActivity {

    private static final String TAG = "Equation";

    Double dl1;
    Double sz1;
    Double timer;
    ViewPager viewPager;
    SimpleDateFormat sdf;
    TextView text1;
    String dlsz = "";
    TextView text2;
    String WE = "";
    String NS = "";
    String dlstring;
    String szstring;
    Thread updateTime;
    Thread refreshData;

    @Override
    public void onStop() {
        super.onStop();
        if(updateTime != null)
            updateTime.interrupt();
        if(refreshData != null)
            refreshData.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(updateTime != null)
            updateTime.interrupt();
        if(refreshData != null)
            refreshData.interrupt();
    }

    @Override
    public void onStart() {
        super.onStart();

        updateTime = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("THREAD", "REFRESH TIME FOR SECOND");
                                text1.setText(sdf.format(System.currentTimeMillis()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        updateTime.start();

        refreshData = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(timer.intValue() * 1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("THREAD", "REFRESH DATA");
                                viewPager = findViewById(R.id.viewPager);
                                if (viewPager != null) {
                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), dl1, sz1);
                                    viewPager.setAdapter(adapter);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        refreshData.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dl1 = getIntent().getDoubleExtra("dl1", 0);
        sz1 = getIntent().getDoubleExtra("sz1", 0);
        timer = getIntent().getDoubleExtra("timer", 1);
        Log.d(TAG, "\n\ndlugosc: " + dl1 + "\n\n");

        if (dl1 < 0) {
            WE = " W";
            dl1 = dl1 * (-1);
        } else WE = " E";
        if (sz1 < 0) {
            NS = " S";
            sz1 = sz1 * (-1);
        } else NS = " N";

        setContentView(R.layout.astro_layout);

        text1 = (TextView) findViewById(R.id.text1);
        sdf = new SimpleDateFormat("hh:mm:ss a");

        dlstring = dl1.toString();

        while (dlstring.contains(".") || dlstring.charAt(dlstring.length() - 1) == '0' || dlstring.charAt(dlstring.length() - 1) == ',' || dlstring.charAt(dlstring.length() - 1) == '.') {
            if (dlstring.charAt(dlstring.length() - 1) == '.' || dlstring.charAt(dlstring.length() - 1) == ',') {
                dlstring = dlstring.substring(0, dlstring.length() - 1);
                break;
            } else dlstring = dlstring.substring(0, dlstring.length() - 1);
        }

        szstring = sz1.toString();

        while (szstring.contains(".") || szstring.charAt(szstring.length() - 1) == '0' || szstring.charAt(szstring.length() - 1) == ',' || szstring.charAt(szstring.length() - 1) == '.') {
            if (szstring.charAt(szstring.length() - 1) == '.' || szstring.charAt(szstring.length() - 1) == ',') {
                szstring = szstring.substring(0, szstring.length() - 1);
                break;
            } else szstring = szstring.substring(0, szstring.length() - 1);
        }

        dlsz = dlsz.concat("dlugosc: ").concat(dlstring).concat(WE).concat(" szerokosc: ").concat(szstring).concat(NS);
        text2 = (TextView) findViewById(R.id.text2);
        text2.setText("");
        if (text2.getText().equals("")) {
            text2.setText(dlsz);
        }

        setLayout();

    }

    void setLayout(){

        int screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = getResources().getConfiguration().orientation;

        if((orientation == Configuration.ORIENTATION_PORTRAIT && screensize == Configuration.SCREENLAYOUT_SIZE_NORMAL)){

            viewPager = findViewById(R.id.viewPager);
            if (viewPager != null) {
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), dl1, sz1);
                viewPager.setAdapter(adapter);
            }
        }
    }

    public Double getSzerokosc() {
        return sz1;
    }

    public Double getDlugosc() {
        return dl1;
    }

}
