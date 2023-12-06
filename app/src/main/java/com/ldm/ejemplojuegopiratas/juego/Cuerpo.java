package com.ldm.ejemplojuegopiratas.juego;

public class Cuerpo {
    public int x, y;
    public int direccion;

    public int nextDirection;


    public Cuerpo(int x, int y,int direccion) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDireccion() {
        return direccion;
    }

}

