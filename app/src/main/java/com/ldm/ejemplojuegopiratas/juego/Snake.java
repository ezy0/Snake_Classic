package com.ldm.ejemplojuegopiratas.juego;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;
    public List<Cuerpo> partes = new ArrayList<Cuerpo>();
    public int direccion;

    public boolean choqueBordes = false;

    public Snake() {
        //direccion = ARRIBA;
        partes.add(new Cuerpo(5, 6,0));
        partes.add(new Cuerpo(5, 7,0));
        partes.add(new Cuerpo(5, 8,0));
        this.direccion = Snake.ARRIBA;
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void crecimiento() {
        Cuerpo end = partes.get(partes.size()-1);
        Cuerpo cuerpo = new Cuerpo(end.x, end.y,end.getDireccion());
        cuerpo.nextDirection = end.nextDirection;
        partes.add(cuerpo);
    }

    public void avance() {
        Cuerpo cuerpo = partes.get(0);

        int len = partes.size() - 1;
        for(int i = len; i > 0; i--) {
            Cuerpo antes = partes.get(i-1);
            Cuerpo parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            cuerpo.y -= 1;
        if(direccion == IZQUIERDA)
            cuerpo.x -= 1;
        if(direccion == ABAJO)
            cuerpo.y += 1;
        if(direccion == DERECHA)
            cuerpo.x += 1;

        if (cuerpo.x < 1 || cuerpo.x > 13 || cuerpo.y < 2 || cuerpo.y > 25) {
            choqueBordes = true;
        }
        /*if(cuerpo.x < 1) PARA HACER QUE DE UN BORDE PASE A OTRO
            cuerpo.x = 13;
        if(cuerpo.x > 13)
            cuerpo.x = 1;
        if(cuerpo.y < 2)
            cuerpo.y = 25;
        if(cuerpo.y > 25)
            cuerpo.y = 2;*/

        for(int i = len; i > 0; i--) {
            Cuerpo siguiente = partes.get(i-1);
            Cuerpo actual = partes.get(i);

            if (actual.x < siguiente.x && actual.x != 1) {
                actual.nextDirection = Snake.DERECHA;
            } else if (actual.x > siguiente.x && actual.x != 13) {
                actual.nextDirection = Snake.IZQUIERDA;
            } else if (actual.y < siguiente.y) {
                actual.nextDirection = Snake.ABAJO;
            } else if (actual.y > siguiente.y) {
                actual.nextDirection = Snake.ARRIBA;
            }
        }
    }

    public boolean comprobarChoque() {
        if (choqueBordes)
            return true;
        int len = partes.size();
        Cuerpo barco = partes.get(0);
        for(int i = 1; i < len; i++) {
            Cuerpo parte = partes.get(i);
            if(parte.x == barco.x && parte.y == barco.y)
                return true;
        }
        return false;
    }
}

