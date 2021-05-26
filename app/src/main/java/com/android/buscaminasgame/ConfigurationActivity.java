package com.android.buscaminasgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigurationActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    RadioButton radioButton15;
    RadioButton radioButton25;
    RadioButton radioButton35;

    EditText username;
    CheckBox temps;
    Button btnGame;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        radioGroup = findViewById(R.id.radioGroup);
        username = findViewById(R.id.username);
        temps = findViewById(R.id.checkBox);
        btnGame = findViewById(R.id.btnGame);
        radioButton1= findViewById(R.id.radioButton1);
        radioButton2= findViewById(R.id.radioButton2);
        radioButton3= findViewById(R.id.radioButton3);

        radioButton15= findViewById(R.id.radioButton15);
        radioButton25= findViewById(R.id.radioButton25);
        radioButton35= findViewById(R.id.radioButton35);

        SharedPreferences prefs = this.getSharedPreferences(
                "com.android.buscaminasgame", Context.MODE_PRIVATE);

        String alias = prefs.getString("alias", "Username");
        String graella = prefs.getString("graella", "4");
        boolean temp = prefs.getBoolean("temps", false);
        String porCiento = prefs.getString("porCiento", "0.25");

        if(!alias.equals("Username"))
            username.setText(alias);

        switch (Integer.parseInt(graella)){

            case 7:
                radioButton1.setChecked(true);
                break;
            case 6:
                radioButton2.setChecked(true);
                break;
            case 5:
                radioButton3.setChecked(true);
                break;
        }

        switch (porCiento){

            case "0.15":
                radioButton15.setChecked(true);
                break;
            case "0.25":
                radioButton25.setChecked(true);
                break;
            case "0.35":
                radioButton35.setChecked(true);
                break;
        }

        temps.setChecked(temp);







        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefs.edit().putString("alias", username.getText().toString()).apply();

                String graella;
                String porCiento;

                if(radioButton1.isChecked())
                    graella="7";
                else if(radioButton2.isChecked())
                    graella="6";
                else
                    graella="5";

                if(radioButton15.isChecked())
                    porCiento="0.15";
                else if(radioButton25.isChecked())
                    porCiento="0.25";
                else
                    porCiento="0.35";




                prefs.edit().putString("graella",graella).apply();
                prefs.edit().putBoolean("temps", temps.isChecked()).apply();
                prefs.edit().putString("porCiento",porCiento).apply();


                Intent in = new Intent(ConfigurationActivity.this, GameActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    public void checkButton(View v) {
        count = 1;
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Mida graella seleccionada: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}
