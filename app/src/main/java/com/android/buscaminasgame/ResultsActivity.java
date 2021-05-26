package com.android.buscaminasgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private String data;
    private String text;
    private EditText edday;
    private EditText edlog;
    private EditText edemail;
    private Button btnEmail;
    private Button btnNewGame;
    private Button btnExit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_results);

            btnEmail = findViewById(R.id.btnEmail);
            btnNewGame = findViewById(R.id.btnNewGame);
            btnExit2 = findViewById(R.id.btnExit2);
            edday = findViewById(R.id.edday);
            edlog = findViewById(R.id.edlog);
            edemail = findViewById(R.id.edemail);

            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dat = new Date();
            data = format1.format(dat);
            text = getIntent().getStringExtra("resultat");

            edday.setText(data);
            edlog.setText(text);

            btnEmail.setOnClickListener(this);
            btnNewGame.setOnClickListener(this);
            btnExit2.setOnClickListener(this);
        }

    @Override
    public void onClick(View src) {
            switch (src.getId()) {
                case R.id.btnEmail:
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { edemail.getText().toString() });
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Log - " + data);
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(intent, "Send Email"));
                    //finish();
                    break;
                case R.id.btnNewGame:
                    startActivity(new Intent(this, ConfigurationActivity.class));
                    finish();
                    break;
                case R.id.btnExit2:
                   finish();
        }

    }
}
