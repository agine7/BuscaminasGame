package com.android.buscaminasgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GameActivity extends AppCompatActivity {

    private Casilla[][] casillas;
    public static boolean activo = true;

    public static int numeroVictorias = 0;
    public static int numeroPerdidas = 0;
    public static int cantidadBombas = 8;

    private GridView grid;
    private int dimension = 7;

    public static ImageAdapter adaptador =null;

    int segundos;
    CountDownTimer downTimer;

    TextView tvTemps;

    String alias ;
    String graella ;
    boolean temp ;
    double porCiento;

    public GameActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        //No permite que la pantalla gire
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activo = true;

        SharedPreferences prefs = this.getSharedPreferences(
                "com.android.buscaminasgame", Context.MODE_PRIVATE);

         alias = prefs.getString("alias", "Username");
         graella = prefs.getString("graella", "4");
         temp = prefs.getBoolean("temps", false);
        porCiento= Double.parseDouble(prefs.getString("porCiento", "0.25"));

        dimension=Integer.parseInt(graella);

        tvTemps= findViewById(R.id.temps);

         /*et1 = (EditText) findViewById(R.id.et1);
        tw1 = (TextView) findViewById(R.id.temps);
        start = (Button) findViewById(R.id.start);
        tw2 = (TextView) findViewById(R.id.tw2);

        layout = (LinearLayout) findViewById(R.id.layout1);*/

        //Grid
        grid = findViewById(R.id.grid);

        casillas = new Casilla[dimension][dimension];
        for (int f = 0; f < dimension; f++) {
            for (int c = 0; c < dimension; c++) {
                casillas[f][c] = new Casilla();
            }
        }
        this.disponerBombas();
        this.contarBombasPerimetro();

        adaptador =
                new ImageAdapter(this, casillas, dimension);



        grid.setColumnWidth(GridView.AUTO_FIT);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setNumColumns(dimension);
        grid.setAdapter(adaptador);

        //getSupportActionBar().hide();

        segundos=60;
        downTimer=new CountDownTimer(segundos*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTemps.setText(getText(R.string.temps)+ " 0:"+formatSegundos(segundos));
                segundos--;
            }
            public void onFinish() {
                new SweetAlertDialog(GameActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Tiempo Agotado")
                        .setConfirmText("Resultado")
                        .setCustomImage(R.drawable.bm)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                Intent intent = new Intent(GameActivity.this, ResultsActivity.class);
                               intent.putExtra("resultat", "Alias: "+alias +" " +
                                       "Casillas: "+(porCiento*100)+"% ");
                                startActivity(intent);
                                finish();

                            }
                        })
                        .show();
            }
        };

        if(temp){
            downTimer.start();
        }else{
            tvTemps.setText(getText(R.string.temps)+ "--");
        }
    }

    public String formatSegundos(int num) {
        return num <= 9 ? "0" + num : String.valueOf(num);
    }

    //OnClick - boton reiniciar
    public void reiniciar(View v) {

        casillas = new Casilla[dimension][dimension];
        for (int f = 0; f < dimension; f++) {
            for (int c = 0; c < dimension; c++) {
                casillas[f][c] = new Casilla();
            }
        }

        this.disponerBombas();
        this.contarBombasPerimetro();
        activo = true;

        adaptador =
                new ImageAdapter(this, casillas, dimension);

        grid.setAdapter(adaptador);

    }

    private void disponerBombas() {
       cantidadBombas=(int) (dimension * dimension* porCiento);

        do {
            int fila = (int) (Math.random() * dimension);
            int columna = (int) (Math.random() * dimension);
            if (casillas[fila][columna].contenido == 0) {
                casillas[fila][columna].contenido = 80;
                cantidadBombas--;
            }
        } while (cantidadBombas != 0);
    }



    private void contarBombasPerimetro() {
        for (int f = 0; f < dimension; f++) {
            for (int c = 0; c < dimension; c++) {
                if (casillas[f][c].contenido == 0) {
                    int cant = contarCoordenada(f, c);
                    casillas[f][c].contenido = cant;
                }
            }
        }
    }

    int contarCoordenada(int fila, int columna) {
        int total = 0;
        if (fila - 1 >= 0 && columna - 1 >= 0) {
            if (casillas[fila - 1][columna - 1].contenido == 80)
                total++;
        }
        if (fila - 1 >= 0) {
            if (casillas[fila - 1][columna].contenido == 80)
                total++;
        }
        if (fila - 1 >= 0 && columna + 1 < dimension) {
            if (casillas[fila - 1][columna + 1].contenido == 80)
                total++;
        }

        if (columna + 1 < dimension) {
            if (casillas[fila][columna + 1].contenido == 80)
                total++;
        }
        if (fila + 1 < dimension && columna + 1 < dimension) {
            if (casillas[fila + 1][columna + 1].contenido == 80)
                total++;
        }

        if (fila + 1 < dimension) {
            if (casillas[fila + 1][columna].contenido == 80)
                total++;
        }
        if (fila + 1 < dimension && columna - 1 >= 0) {
            if (casillas[fila + 1][columna - 1].contenido == 80)
                total++;
        }
        if (columna - 1 >= 0) {
            if (casillas[fila][columna - 1].contenido == 80)
                total++;
        }
        return total;
    }

    void refreshAdapter() {
        adaptador =
                new ImageAdapter(this, casillas, dimension);

        grid.setAdapter(adaptador);
    }





}
