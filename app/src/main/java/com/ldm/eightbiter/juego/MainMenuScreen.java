package com.ldm.eightbiter.juego;
import java.util.List;
import com.ldm.eightbiter.Juego;
import com.ldm.eightbiter.Graficos;
import com.ldm.eightbiter.Input.TouchEvent;
import com.ldm.eightbiter.Pantalla;
import com.ldm.eightbiter.androidimpl.AndroidJuego;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if (!Configuraciones.sonidoHabilitado)
            AndroidJuego.musica.stop();
        else
            AndroidJuego.musica.play();

        if (!AndroidJuego.musica.isPlaying() && Configuraciones.sonidoHabilitado)
            AndroidJuego.musica.play();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event)) {
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                }
                if(event.x > 150 && event.x < 300 && event.y > 350 & event.y < 400) {
                    juego.setScreen(new PantallaJuego(juego));
                    if(Configuraciones.sonidoHabilitado) {
                        Assets.pulsar.play(1);
                    }
                    return;
                }
                if(event.x > 50 && event.x < 400 && event.y > 415 & event.y < 455) {
                    juego.setScreen(new PantallaMaximasPuntuaciones(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
                if(event.x > 150 && event.x < 300 && event.y > 470 & event.y < 510) {
                    juego.setScreen(new PantallaAyuda(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event) {
        return event.x > 20 && event.x < 20 + 80 - 1 &&
                event.y > 747 && event.y < 747 + 80 - 1;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.logo, (g.getWidth()/2)-87, 100);
        g.drawPixmap(Assets.menuprincipal, 64, 350);
        if(Configuraciones.sonidoHabilitado)
            g.drawPixmap(Assets.sonido, 20,747,0,0,80,80);
        else
            g.drawPixmap(Assets.silenciar, 20,747,0,0,80,80);
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

