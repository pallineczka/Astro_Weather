package com.example.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Equation";
    String[] dlugosc = {"E", "W"};
    String[] szerokosc = {"N", "S"};
    String[] timer = {"15", "30", "45"};
    String dl = "";
    Double dl1;
    String sz = "";
    Double sz1;
    String spidl = "";
    String spisz = "";
    String spitimer = "";
    Double stimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText1 = (EditText) findViewById(R.id.editText1);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);

        final Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(this);

        final Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        spin3.setOnItemSelectedListener(this);

        ArrayAdapter array1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dlugosc);
        array1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(array1);

        ArrayAdapter array2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,szerokosc);
        array2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(array2);

        ArrayAdapter array3 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,timer);
        array3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(array3);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl = editText1.getText().toString();
                sz = editText2.getText().toString();

                if(!dl.equals("") && !sz.equals("")) {

                    spidl = spin.getSelectedItem().toString();
                    spisz = spin2.getSelectedItem().toString();
                    spitimer = spin3.getSelectedItem().toString();

                    if (spidl == "W") {
                        dl = String.valueOf(Double.parseDouble(dl) * (-1));
                    }
                    if (spisz == "S") {
                        sz = String.valueOf(Double.parseDouble(sz) * (-1));
                    }

                    dl1 = Double.parseDouble(dl);
                    sz1 = Double.parseDouble(sz);
                    stimer = Double.parseDouble(spitimer);

                    Log.d(TAG, "dlugosc: " + dl + "\t szerokosc: " + sz + "dlugosc: " + dl1 + "\t szerokosc: " + sz1);
                    if(dl1 > -180 && dl1 < 180 && sz1 > -90 && sz1 < 90) {
                        Intent i = new Intent(MainActivity.this, Astro.class);
                        i.putExtra("dl1", dl1);
                        i.putExtra("sz1", sz1);
                        i.putExtra("timer", stimer);
                        startActivity(i);
                    } else{
                        Toast message = Toast.makeText(getBaseContext(), "Błędne dane!", Toast.LENGTH_LONG);
                        message.show();
                    }
                } else{
                    Toast message = Toast.makeText(getBaseContext(), "Uzupełnij pola", Toast.LENGTH_LONG);
                    message.show();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}