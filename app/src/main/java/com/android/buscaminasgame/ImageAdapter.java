package com.android.buscaminasgame;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImageAdapter extends BaseAdapter {

    private static final String TAG = "CustomAdapter";

    private Casilla[] casillas1D;
    private Casilla[][] casillas;

    private GameActivity mContext;
    private LayoutInflater mInflater;

    Button ivCasilla = null;
    int dimension = 8;

    static class ViewHolder {
        public Button ivCasilla;

        public ViewHolder() {

        }
    }

    public ImageAdapter(GameActivity context, Casilla[][] casillas, int dimension) {
        mContext = context;
        this.casillas = casillas;
        this.dimension = dimension;

        casillas1D = new Casilla[dimension*dimension];

        int i = 0;
        for (int f = 0; f < dimension; f++) {
            for (int c = 0; c < dimension; c++) {
                casillas1D[i] = casillas[f][c];
                casillas1D[i].setF(f);
                casillas1D[i].setC(c);
                i++;
            }
        }

        mInflater = LayoutInflater.from(context.getApplicationContext());
    }



    @Override
    public int getCount() {
        return casillas1D.length;
    }

    @Override
    public Object getItem(int pos) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {  // if it's not recycled, initialize some attributes

            rowView = mInflater.inflate(R.layout.custom_layout, null);


            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivCasilla = rowView.findViewById(R.id.image);

            if (casillas1D[position].destapado == false)
                viewHolder.ivCasilla.setBackgroundColor(mContext.getResources().getColor(R.color.colorClaro));
            else
                viewHolder.ivCasilla.setBackgroundColor(mContext.getResources().getColor(R.color.colorOscuro));


            if (casillas1D[position].contenido >= 1
                    && casillas1D[position].contenido <= 8
                    && casillas1D[position].destapado)

                viewHolder.ivCasilla.setText(casillas1D[position].contenido+"");



            if (casillas1D[position].contenido == 80
                    && casillas1D[position].destapado) {
                //Bomba
                viewHolder.ivCasilla.setBackgroundResource(R.drawable.ic_stat_name);
            }


            viewHolder.ivCasilla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "asdsa", Toast.LENGTH_SHORT).show();

                    if (GameActivity.activo) {
                        casillas1D[position].destapado = true;

                        if (casillas1D[position].contenido == 80) {
                            GameActivity.numeroPerdidas = GameActivity.numeroPerdidas + 1;

                            GameActivity.activo = false;

                            new SweetAlertDialog(mContext, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                    .setTitleText("Booooooooommmmmmmmmmmm")
                                    .setConfirmText("Resultado")
                                    .setCustomImage(R.drawable.bm)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance

                                            Intent intent = new Intent(mContext, ResultsActivity.class);
                                            intent.putExtra("resultat", "Alias: "+mContext.alias +" " +
                                                    "Casillas: "+(mContext.porCiento*100)+"% ");
                                            mContext.startActivity(intent);
                                            mContext.finish();

                                        }
                                    })
                                    .show();

                        } else if (casillas1D[position].contenido == 0)

                            recorrer(casillas1D[position].getF(), casillas1D[position].getC());


                    }

                    if (gano() && GameActivity.activo) {
                        GameActivity.numeroVictorias = GameActivity.numeroVictorias + 1;


                        GameActivity.activo = false;

                        new SweetAlertDialog(mContext, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setTitleText("Ganastes")
                                .setConfirmText("Resultado")
                                .setCustomImage(R.drawable.bm)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance
                                        Intent intent = new Intent(mContext, ResultsActivity.class);
                                        intent.putExtra("resultat", "Alias: "+mContext.alias +" " +
                                                "Casillas: "+(mContext.porCiento*100)+"% ");
                                        mContext.startActivity(intent);
                                        mContext.finish();



                                    }
                                })
                                .show();

                    }


                   mContext.refreshAdapter();


                }
            });

            rowView.setTag(viewHolder);
        }

        return rowView;

    }

    public boolean gano() {
        int cant = 0;

        for (int f = 0; f < dimension; f++)
            for (int c = 0; c < dimension; c++)
                if (casillas[f][c].destapado)
                    cant++;
        if (cant == (dimension*dimension)-mContext.cantidadBombas) {
            return true;
        } else {
            return false;
        }

    }

    public void recorrer(int fil, int col) {
        if (fil >= 0 && fil < dimension && col >= 0 && col < dimension) {
            if (casillas[fil][col].contenido == 0) {
                casillas[fil][col].destapado = true;
                casillas[fil][col].contenido = 50;
                recorrer(fil, col + 1);
                recorrer(fil, col - 1);
                recorrer(fil + 1, col);
                recorrer(fil - 1, col);
                recorrer(fil - 1, col - 1);
                recorrer(fil - 1, col + 1);
                recorrer(fil + 1, col + 1);
                recorrer(fil + 1, col - 1);
            } else if (casillas[fil][col].contenido >= 1
                    && casillas[fil][col].contenido <= 8) {
                casillas[fil][col].destapado = true;
            }
        }
    }
}