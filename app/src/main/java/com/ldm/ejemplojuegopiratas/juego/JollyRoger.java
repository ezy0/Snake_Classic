package com.ldm.ejemplojuegopiratas.juego;

import java.util.ArrayList;
import java.util.List;

public class JollyRoger {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;
    public List<Cuerpo> partes = new ArrayList<Cuerpo>();
    public int direccion;

    public JollyRoger() {
        //direccion = ARRIBA;
        partes.add(new Cuerpo(5, 6,0));
        partes.add(new Cuerpo(5, 7,0));
        partes.add(new Cuerpo(5, 8,0));
        this.direccion = JollyRoger.ARRIBA;
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

    public void abordaje() {//añadir un nuevo cuerpo al serpiente
        Cuerpo end = partes.get(partes.size()-1);
        partes.add(new Cuerpo(end.x, end.y,end.getDireccion()));
    }

    public void avance() {
        Cuerpo barco = partes.get(0);
        //lastHeadX = barco.x;
        //lastHeadY = barco.y;

        int len = partes.size() - 1;
        for(int i = len; i > 0; i--) {
            Cuerpo antes = partes.get(i-1);
            Cuerpo parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            barco.y -= 1;
        if(direccion == IZQUIERDA)
            barco.x -= 1;
        if(direccion == ABAJO)
            barco.y += 1;
        if(direccion == DERECHA)
            barco.x += 1;

        if(barco.x < 0)
            barco.x = 9;
        if(barco.x > 9)
            barco.x = 0;
        if(barco.y < 0)
            barco.y = 12;
        if(barco.y > 12)
            barco.y = 0;
        //updateBodyDirection();
    }

    /*private void updateBodyDirection() {
        int len = partes.size() - 1;
        for (int i = len; i > 0; i--) {
            Cuerpo parte = partes.get(i);
            if (parte.x == lastHeadX && parte.y == lastHeadY) {
                // El segmento del cuerpo ha alcanzado la posición anterior de la cabeza
                // Actualiza la dirección del segmento del cuerpo según la nueva dirección de la cabeza
                parte.direccion = direccion;
            }
        }
    }*/
    public boolean comprobarChoque() {
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
