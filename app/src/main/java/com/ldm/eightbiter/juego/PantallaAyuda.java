package com.ldm.eightbiter.juego;
import java.util.List;
import com.ldm.eightbiter.Juego;
import com.ldm.eightbiter.Graficos;
import com.ldm.eightbiter.Input.TouchEvent;
import com.ldm.eightbiter.Pantalla;

public class PantallaAyuda extends Pantalla {
    public PantallaAyuda(Juego juego) {
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
                if(event.x > 300 && event.y > 700 ) {
                    juego.setScreen(new PantallaAyuda2(juego));
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
        g.drawPixmap(Assets.ayuda1, 64, 100);
        g.drawPixmap(Assets.flecha, 380, 747, 0, 0, 80, 80);
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