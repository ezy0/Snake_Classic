package com.ldm.ejemplojuegopiratas.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 14; //10 default
    static final int MUNDO_ALTO = 27; // 13 default
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.25f;
    static final float TICK_DECREMENTO = 0.05f;

    public Snake snake;
    public Manzana manzana;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

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
        while (campos[manzanaX][manzanaY]) {
            manzanaX += 1;
            if (manzanaX > 12)
                manzanaX = 12;
            if (manzanaX <= 2)
                manzanaX = 3;
            if (manzanaY >= 25)
                manzanaY = 24;
            if (manzanaY <= 2)
                manzanaY = 1;
        }
        manzana = new Manzana(manzanaX, manzanaY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            snake.avance();
            if (snake.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Cuerpo head = snake.partes.get(0);
            if (head.x == manzana.x && head.y == manzana.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                snake.crecimiento();
                if (snake.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarManzana();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}

