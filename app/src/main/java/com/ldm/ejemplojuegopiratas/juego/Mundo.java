package com.ldm.ejemplojuegopiratas.juego;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ldm.ejemplojuegopiratas.androidimpl.AndroidFileIO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class Mundo {
    static final int MUNDO_ANCHO = 14; //10 default
    static final int MUNDO_ALTO = 27; // 13 default
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.25f;
    static final float TICK_DECREMENTO = 0.05f;
    private AndroidFileIO fileIO;

    public Snake snake;
    public Manzana manzana;
    public Obstaculo obstaculo;
    public LinkedList<Obstaculo> obstaculos = new LinkedList<>();

    public boolean finalJuego = false;
    public int puntuacion = 0;
    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    int ocupadaX, ocupadaY;

    public Mundo(AndroidFileIO fileIO) {
        snake = new Snake();
        colocarManzana();
        this.fileIO=fileIO;//tendria que ser algo asi, pero  no esta saliendo bien
    }

    public Mundo() {
        snake = new Snake();
        colocarManzana();
    }


    private void colocarManzana() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = snake.partes.size();
        for (int i = 0; i < len; i++) {
            Cuerpo parte = snake.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int manzanaX = random.nextInt(MUNDO_ANCHO);
        int manzanaY = random.nextInt(MUNDO_ALTO);
        if (manzanaX > 12)
            manzanaX = 12;
        if (manzanaX < 1)
            manzanaX = 1;
        if (manzanaY > 25)
            manzanaY = 25;
        if (manzanaY < 2)
            manzanaY = 2;
        while (campos[manzanaX][manzanaY] || ocupadoObstaculo(manzanaX, manzanaY)) {
            manzanaX = random.nextInt(MUNDO_ANCHO);
            manzanaY = random.nextInt(MUNDO_ALTO);
            if (manzanaX > 12)
                manzanaX = 12;
            if (manzanaX < 1)
                manzanaX = 1;
            if (manzanaY > 25)
                manzanaY = 25;
            if (manzanaY < 2)
                manzanaY = 2;
        }
        manzana = new Manzana(manzanaX, manzanaY);
        ocupadaX = manzanaX;
        ocupadaY = manzanaY;
    }

    private boolean ocupadoObstaculo(int x, int y) {
        for (Obstaculo obs : obstaculos) {
            if (obs.x == x && obs.y == y)
                return true;
        }
        return false;
    }

    private void colocarObstaculo() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = snake.partes.size();
        for (int i = 0; i < len; i++) {
            Cuerpo parte = snake.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int obstaculoX = random.nextInt(MUNDO_ANCHO);
        int obstaculoY = random.nextInt(MUNDO_ALTO);
        if (obstaculoX > 12)
            obstaculoX = 12;
        if (obstaculoX < 1)
            obstaculoX = 1;
        if (obstaculoY > 25)
            obstaculoY = 25;
        if (obstaculoY < 2)
            obstaculoY = 2;
        while (campos[obstaculoX][obstaculoY] || (obstaculoX == ocupadaX && obstaculoY == ocupadaY)) {
            obstaculoX = random.nextInt(MUNDO_ANCHO);
            obstaculoY = random.nextInt(MUNDO_ALTO);
            if (obstaculoX > 12)
                obstaculoX = 12;
            if (obstaculoX < 1)
                obstaculoX = 1;
            if (obstaculoY > 25)
                obstaculoY = 25;
            if (obstaculoY < 2)
                obstaculoY = 2;
            Log.i("OBSTACULO", "OBSTACULO");
        }
        obstaculo = new Obstaculo(obstaculoX, obstaculoY);
        obstaculos.add(obstaculo);
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            snake.avance();
            Cuerpo head = snake.partes.get(0);

            if (snake.comprobarChoque()) {
                finalJuego = true;
                //savePoints(puntuacion);
                return;
            }

            for (Obstaculo obs : obstaculos){
                if (head.x == obs.x && head.y == obs.y) {
                    finalJuego = true;
                    return;
                }
            }

            if (head.x == manzana.x && head.y == manzana.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                snake.crecimiento();
                if (snake.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarManzana();
                    colocarObstaculo();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
    public void savePoints(int p) {
        ArrayList<String> puntuaciones = new ArrayList<>();
        puntuaciones.add(String.valueOf(p));

        try (ObjectOutputStream outputStream = new ObjectOutputStream(fileIO.escribirArchivo("puntuaciones.txt"))) {
            outputStream.writeObject(puntuaciones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

