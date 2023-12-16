package com.ldm.eightbiter.juego;

import com.ldm.eightbiter.Juego;
import com.ldm.eightbiter.Graficos;
import com.ldm.eightbiter.Pantalla;
import com.ldm.eightbiter.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.cabezaArriba = g.newPixmap("cabezaArriba.png", PixmapFormat.ARGB4444);
        Assets.cabezaIzquierda = g.newPixmap("cabezaIzquierda.png", PixmapFormat.ARGB4444);
        Assets.cabezaAbajo = g.newPixmap("cabezaAbajo.png", PixmapFormat.ARGB4444);
        Assets.cabezaDerecha = g.newPixmap("cabezaDerecha.png", PixmapFormat.ARGB4444);
        Assets.cuerpoDerecha = g.newPixmap("cuerpoDerecha.png", PixmapFormat.ARGB4444);
        Assets.cuerpoAbajo = g.newPixmap("cuerpoAbajo.png", PixmapFormat.ARGB4444);
        Assets.cuerpoIzquierda = g.newPixmap("cuerpoIzquierda.png", PixmapFormat.ARGB4444);
        Assets.manzana = g.newPixmap("manzana.png", PixmapFormat.ARGB4444);
        Assets.obstaculo = g.newPixmap("obstaculo.png", PixmapFormat.ARGB4444);
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.comer = juego.getAudio().nuevoSonido("apple.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        Assets.cruz = g.newPixmap("cruz.png",PixmapFormat.ARGB4444);
        Assets.silenciar=g.newPixmap("silenciar.png", PixmapFormat.ARGB4444);
        Assets.sonido=g.newPixmap("sonido.png",PixmapFormat.ARGB4444);
        Assets.pausa=g.newPixmap("pausa.png",PixmapFormat.ARGB4444);
        Assets.flecha=g.newPixmap("flecha.png",PixmapFormat.ARGB4444);
        Assets.puntuaciones=g.newPixmap("puntuaciones.png",PixmapFormat.ARGB4444);
        Assets.flechaAyuda=g.newPixmap("ayuda2flecha.png",PixmapFormat.ARGB4444);
        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));

    }

    @Override
    public void present(float deltaTime) {

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