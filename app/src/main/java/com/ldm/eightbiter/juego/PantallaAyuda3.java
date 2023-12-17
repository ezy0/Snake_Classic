package com.ldm.eightbiter.juego;

import java.util.List;

import com.ldm.eightbiter.Juego;
import com.ldm.eightbiter.Graficos;
import com.ldm.eightbiter.Input.TouchEvent;
import com.ldm.eightbiter.Pantalla;

public class PantallaAyuda3 extends Pantalla {
    public PantallaAyuda3(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 380 && event.y >= 747 && event.x<=460 && event.y<=827) {
                    juego.setScreen(new MainMenuScreen(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.ayuda3, 64, 100);
        g.drawPixmap(Assets.cruz, 380, 747, 0, 0, 80, 80);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}