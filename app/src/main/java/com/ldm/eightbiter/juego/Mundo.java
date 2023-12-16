package com.ldm.eightbiter.juego;

import java.util.LinkedList;
import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 14; //10 default
    static final int MUNDO_ALTO = 27; // 13 default
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.25f;
    static final float TICK_DECREMENTO = 0.05f;

    public Snake snake;
    public Manzana manzana;
    public Obstaculo obstaculo;
    public LinkedList<Obstaculo> obstaculos = new LinkedList<>();

    public boolean finalJuego = false;
    public int puntuacion = 0;
    boolean[][] campos = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick;
    static float tick;

    int ocupadaX, ocupadaY;


    public Mundo() {
        snake = new Snake();
        colocarManzana();
        tiempoTick = 0;
        tick = TICK_INICIAL;
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

        /*int manzanaX = random.nextInt(MUNDO_ANCHO);
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
        }*/
        int manzanaX, manzanaY;

        do {
            manzanaX = random.nextInt(MUNDO_ANCHO - 2) + 1;
            manzanaY = random.nextInt(MUNDO_ALTO - 3) + 2;
        } while (campos[manzanaX][manzanaY] || ocupadoObstaculo(manzanaX, manzanaY));

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

        /*int obstaculoX = random.nextInt(MUNDO_ANCHO);
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
        }*/
        int obstaculoX, obstaculoY;

        do {
            obstaculoX = random.nextInt(MUNDO_ANCHO - 2) + 1;
            obstaculoY = random.nextInt(MUNDO_ALTO - 3) + 2;
        } while (campos[obstaculoX][obstaculoY] || (obstaculoX == ocupadaX && obstaculoY == ocupadaY) || ocupadoObstaculo(obstaculoX, obstaculoY));

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
}

