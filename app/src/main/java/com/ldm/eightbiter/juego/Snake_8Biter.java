package com.ldm.eightbiter.juego;

import com.ldm.eightbiter.Pantalla;
import com.ldm.eightbiter.androidimpl.AndroidJuego;

public class Snake_8Biter extends AndroidJuego {

    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
