package com.android.buscaminasgame;

public class Casilla {

    public int contenido=0;
    public boolean destapado=false;
    public int f;
    public int c;

    public Casilla() {
    }

    public Casilla(int contenido, boolean destapado) {
        this.contenido = contenido;
        this.destapado = destapado;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public void setDestapado(boolean destapado) {
        this.destapado = destapado;
    }

    public int getContenido() {
        return contenido;
    }

    public boolean isDestapado() {
        return destapado;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
