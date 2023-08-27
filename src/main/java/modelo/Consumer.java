/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.JLabel;

/**
 *
 * @author Cheto Sandoval
 */
public class Consumer extends Thread {

    private Buffer buffer;
    private int locationX = 40;
    private JLabel consumidores;
    private int tam;
    public boolean running = true;
    Producer[] producers;
    JLabel[] comida;

    public Consumer(Buffer buffer, JLabel consumidores, int tam, Producer[] producers, JLabel[] comida) {
        this.buffer = buffer;
        this.consumidores = consumidores;
        this.tam = tam;
        this.producers = producers;
        this.comida = comida;
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
        while (running && buffer.isRunning1) {
            try {
                System.out.println("");
                if (buffer.salir1 >= buffer.consumers.length) {
                    buffer.estadoActual1 = -1;
                }

                if (buffer.progreso > 0) {
                    moverImg();
                }

                if (buffer.isRunning1) {
                    int item = buffer.consume();
                }

                Thread.sleep(8500);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public boolean moverImg() throws InterruptedException {
        if (tam == 0 && buffer.isRunning1 == true && (buffer.estadoActual1 <= tam || buffer.estadoActual1 == -1)) {
            for (int i = 0; i < 3; i++) {
                consumidores.setLocation((600) - i * locationX, 20);
                Thread.sleep((int) (Math.random() * 1000));
            }
            consumidores.setLocation(600, 20);
            buffer.salir1++;
        }
        if (!buffer.isRunning1) {
            buffer.estadoActual1 = 0;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 1 && buffer.isRunning1 == true && (buffer.estadoActual1 <= tam || buffer.estadoActual1 == -1)) {
            for (int i = 0; i < 3; i++) {
                consumidores.setLocation((600) - i * locationX, 100);
                Thread.sleep((int) (Math.random() * 1000));
            }
            consumidores.setLocation(600, 100);
            buffer.salir1++;
        }
        if (!buffer.isRunning1) {
            buffer.estadoActual1 = 1;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 2 && buffer.isRunning1 == true && (buffer.estadoActual1 <= tam || buffer.estadoActual1 == -1)) {
            for (int i = 0; i < 3; i++) {
                consumidores.setLocation((600) - i * locationX, 180);
                Thread.sleep((int) (Math.random() * 1000));
            }
            consumidores.setLocation(600, 180);
            buffer.salir1++;
        }
        if (!buffer.isRunning1) {
            buffer.estadoActual1 = 2;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 3 && buffer.isRunning1 == true && (buffer.estadoActual1 <= tam || buffer.estadoActual1 == -1)) {
            for (int i = 0; i < 3; i++) {
                consumidores.setLocation((600) - i * locationX, 260);
                Thread.sleep((int) (Math.random() * 1000));
            }
            consumidores.setLocation(600, 260);
            buffer.salir1++;
        }
        if (!buffer.isRunning1) {
            buffer.estadoActual1 = 3;
            return true;
        }
        Thread.sleep(3500);

        if (tam == 4 && buffer.isRunning1 == true && (buffer.estadoActual1 <= tam || buffer.estadoActual1 == -1)) {
            for (int i = 0; i < 3; i++) {
                consumidores.setLocation((600) - i * locationX, 340);
                Thread.sleep((int) (Math.random() * 1000));
            }
            consumidores.setLocation(600, 340);
            buffer.salir1++;
        }

        if (!buffer.isRunning1) {
            buffer.estadoActual1 = 4;
            return true;
        }
        Thread.sleep(3500);

        buffer.progreso--;
        System.out.println(buffer.progreso);

        if (buffer.progreso < (buffer.bufferSize / 2) && buffer.estadoActual1 == -1) {
            System.out.println("Ingresa");

            for (int i = 0; i < comida.length; i++) {
                if (comida[i].getIcon() != null) {
                    comida[i].setIcon(null);
                }
            }
            buffer.entrar();
        }

        return true;
    }

}
