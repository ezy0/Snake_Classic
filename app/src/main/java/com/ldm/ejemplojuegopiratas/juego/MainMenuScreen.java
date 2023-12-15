package com.ldm.ejemplojuegopiratas.juego;
import java.util.List;
import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Input.TouchEvent;
import com.ldm.ejemplojuegopiratas.Pantalla;
import com.ldm.ejemplojuegopiratas.androidimpl.AndroidJuego;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if (!Configuraciones.sonidoHabilitado)
            AndroidJuego.musica.stop();
        else
            AndroidJuego.musica.play();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                //if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                if(inBounds(event, 20, 747, 80, 80)) {
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

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.logo, (g.getWidth()/2)-87, 100);
        g.drawPixmap(Assets.menuprincipal, 64, 350);
        if(Configuraciones.sonidoHabilitado)//primero dos valores es para ver donde colocar y los ultimos 4 son para ver donde cortar
            g.drawPixmap(Assets.sonido, 20,747,0,0,80,80);//colocar el boton de sonido
            //g.drawPixmap(Assets.botones, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.silenciar, 20,747,0,0,80,80);
            //g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);
    }

    @Override
    public void pause() {
        //Configuraciones.save(juego.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

