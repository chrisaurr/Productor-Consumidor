/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JLabel;
import vista.View;

/**
 *
 * @author Cheto Sandoval
 */
public class Producer extends Thread {

    private Buffer buffer;
    private JLabel productores;
    int tam;
    int locationX = 40;
    public boolean running = true;
    View vista;
    int tamanio;

    public Producer(Buffer buffer, JLabel productores, int tam, View vista) {
        this.buffer = buffer;
        this.productores = productores;
        this.tam = tam;
        this.vista = vista;
        tamanio = buffer.bufferSize;
        buffer.vueltas++;

    }

    public void startThread() {
        running = true;
        new Thread(this).start();
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        while (running && buffer.isRunning) {
            try {
                if (buffer.salir > buffer.bufferSize / 2) {
                    buffer.estadoActual = -1;
                }

                if (running) {
                    moverImg();
                }

                if (buffer.isRunning) {
                    int item = ThreadLocalRandom.current().nextInt(1, 100);
                    buffer.produce(item);
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public boolean moverImg() throws InterruptedException {

        if (tam == 0 && buffer.salir <= buffer.bufferSize && buffer.isRunning == true && (buffer.estadoActual <= tam || buffer.estadoActual == -1)) {
            for (int i = 0; i < 3; i++) {
                productores.setLocation((i * locationX) + 100, 20);
                Thread.sleep((int) (Math.random() * 1000));
            }
            productores.setLocation(100, 20);
            buffer.salir++;
        }
        if (!buffer.isRunning) {
            buffer.estadoActual = 0;
            return true;
        }

        Thread.sleep(3500);

        if (tam == 1 && buffer.salir <= buffer.bufferSize && buffer.isRunning == true && (buffer.estadoActual <= tam || buffer.estadoActual == -1)) {
            for (int i = 0; i < 3; i++) {
                productores.setLocation((i * locationX) + 100, 100);
                Thread.sleep((int) (Math.random() * 1000));
            }
            productores.setLocation(100, 100);
            buffer.salir++;
        }
        if (!buffer.isRunning) {
            buffer.estadoActual = 1;
            return true;
        }

        Thread.sleep(3500);

        if (tam == 2 && buffer.salir <= buffer.bufferSize && buffer.isRunning == true && (buffer.estadoActual <= tam || buffer.estadoActual == -1)) {
            for (int i = 0; i < 3; i++) {
                productores.setLocation((i * locationX) + 100, 180);
                Thread.sleep((int) (Math.random() * 1000));
            }
            productores.setLocation(100, 180);
            buffer.salir++;
        }
        if (!buffer.isRunning) {
            buffer.estadoActual = 2;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 3 && buffer.salir <= buffer.bufferSize && buffer.isRunning == true && (buffer.estadoActual <= tam || buffer.estadoActual == -1)) {
            for (int i = 0; i < 3; i++) {
                productores.setLocation((i * locationX) + 100, 260);
                Thread.sleep((int) (Math.random() * 1000));
            }
            productores.setLocation(100, 260);
            buffer.salir++;
        }
        if (!buffer.isRunning) {
            buffer.estadoActual = 3;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 4 && buffer.salir <= buffer.bufferSize && buffer.isRunning == true && (buffer.estadoActual <= tam || buffer.estadoActual == -1)) {
            for (int i = 0; i < 3; i++) {
                productores.setLocation((i * locationX) + 100, 340);
                Thread.sleep((int) (Math.random() * 1000));
            }
            productores.setLocation(100, 340);
            buffer.salir++;
        }
        if (!buffer.isRunning) {
            buffer.estadoActual = 4;
            return true;
        }

        Thread.sleep(3500);

        return true;

    }

}
