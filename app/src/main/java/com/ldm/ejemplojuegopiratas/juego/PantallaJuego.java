package com.ldm.ejemplojuegopiratas.juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Input.TouchEvent;
import com.ldm.ejemplojuegopiratas.Pixmap;
import com.ldm.ejemplojuegopiratas.Pantalla;
import com.ldm.ejemplojuegopiratas.androidimpl.AndroidGraficos;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";


    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            estado = EstadoJuego.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            /*if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo.snake.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.snake.girarDerecha();
                }
            }*/
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if (event.y >= 450 && event.y < 650) {
                    if (mundo.snake.direccion == Snake.ARRIBA || mundo.snake.direccion == Snake.ABAJO) {
                        if (event.x < 240) {
                            mundo.snake.izquierda();
                        } else {
                            mundo.snake.derecha();
                        }
                    } else if (mundo.snake.direccion == Snake.IZQUIERDA || mundo.snake.direccion == Snake.DERECHA) {
                        mundo.snake.arriba();
                    }
                } else if (event.y >= 650){
                    if (mundo.snake.direccion == Snake.IZQUIERDA || mundo.snake.direccion == Snake.DERECHA) {
                        mundo.snake.abajo();
                    } else if (mundo.snake.direccion == Snake.ARRIBA || mundo.snake.direccion == Snake.ABAJO) {
                        if (event.x < 240) {
                            mundo.snake.izquierda();
                        } else {
                            mundo.snake.derecha();
                        }
                    }
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            if(Configuraciones.sonidoHabilitado)
                Assets.derrota.play(1);
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.comer.play(6);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 400) {
                    if(event.y >= 347 && event.y <= 460) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if(event.y > 460 && event.y <= 561) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        juego.setScreen(new MainMenuScreen(juego));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 220 && event.x <= 300 &&
                        event.y >= 520 && event.y <= 600) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }


    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        drawWorld(mundo);
        if(estado == EstadoJuego.Preparado)
            drawReadyUI();
        if(estado == EstadoJuego.Ejecutandose)
            drawRunningUI();
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();
        if(estado == EstadoJuego.FinJuego)
            drawGameOverUI();

        drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2, 15);
    }

    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        Snake snake = mundo.snake;
        Cuerpo head = snake.partes.get(0);
        Manzana manzana = mundo.manzana;

        Pixmap stainPixmap = Assets.manzana;
        int x = manzana.x * 32;
        int y = manzana.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        int len = snake.partes.size();

        for(int i = 1; i < len; i++) {
            Cuerpo part = snake.partes.get(i);
            x = part.x * 32;
            y = part.y * 32;

            switch (part.nextDirection) {
                case Snake.ARRIBA:
                case Snake.ABAJO:
                    g.drawPixmap(Assets.cuerpoAbajo, x, y);
                    break;
                case Snake.IZQUIERDA:
                    g.drawPixmap(Assets.cuerpoIzquierda, x, y);
                    break;
                case Snake.DERECHA:
                    g.drawPixmap(Assets.cuerpoDerecha, x, y);
                    break;
            }
        }


        Pixmap headPixmap = null;
        if(snake.direccion == Snake.ARRIBA)
            headPixmap = Assets.cabezaArriba;
        if(snake.direccion == Snake.IZQUIERDA)
            headPixmap = Assets.cabezaIzquierda;
        if(snake.direccion == Snake.ABAJO)
            headPixmap = Assets.cabezaAbajo;
        if(snake.direccion == Snake.DERECHA)
            headPixmap = Assets.cabezaDerecha;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        assert headPixmap != null;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 90, 450);
        //g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.pausa, 0, 0, 0, 0, 73, 74);//este es de parar
        //g.drawLine(0, 416, 480, 416, Color.BLACK);
        //g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        //g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.menupausa, (g.getWidth()/2)-150, (g.getHeight()/2)-78);
        //g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.finjuego, (g.getWidth()/2)-150, (g.getHeight()/2)-47);
        g.drawPixmap(Assets.cruz, (g.getWidth()/2)-40, 500, 0, 0, 80, 80);//para cerrar
        //g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        if(mundo.finalJuego) {
            Configuraciones.addScore(mundo.puntuacion);
            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}