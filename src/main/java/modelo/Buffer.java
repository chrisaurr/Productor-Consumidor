/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.util.concurrent.Semaphore;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import vista.View;

/**
 *
 * @author Cheto Sandoval
 */
public class Buffer {

    private int[] buffer;
    public int bufferSize;
    public int in;
    private int out;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;
    public int estadoActual = -1;
    public int estadoActual1 = -1;

    public boolean isRunning = true;
    public boolean isRunning1 = true;

    public int vueltas = 0;

    public boolean detener = false;

    private JLabel[] comida;
    public Producer[] producers;
    public Consumer[] consumers;
    public int progreso = 0;
    View vista;
    public int salir = 1;
    public int salir1 = 1;

    ImageIcon startIcon = new ImageIcon("D:\\Descargas\\start-button.png");
    ImageIcon stopIcon = new ImageIcon("D:\\Descargas\\stop.png");
    JLabel[] estados = new JLabel[2];

    public Buffer(int size, JLabel[] comida, View vista, Producer[] producers, Consumer[] consumers) {
        bufferSize = size;
        buffer = new int[bufferSize];
        in = 0;
        out = 0;
        empty = new Semaphore(bufferSize);
        full = new Semaphore(0, true);
        mutex = new Semaphore(1, true);
        this.comida = comida;
        this.vista = vista;

        this.producers = producers;
        this.consumers = consumers;

        estados[0] = new JLabel(startIcon);
        estados[0].setSize(65, 65);
        estados[0].setLocation(20, 20);
        Border border1 = BorderFactory.createLineBorder(Color.BLACK, 2); // Color y grosor del borde
        estados[0].setBorder(border1);

        estados[1] = new JLabel(stopIcon);
        estados[1].setSize(65, 65);
        estados[1].setLocation(670, 20);
        Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2); // Color y grosor del borde
        estados[1].setBorder(border2);

        vista.jLayeredPane1.add(estados[0], Integer.valueOf(1));
        vista.jLayeredPane1.add(estados[1], Integer.valueOf(1));

    }

    public void produce(int item) throws InterruptedException {

        empty.acquire();
        mutex.acquire();

        in = (in + 1) % bufferSize;
        mutex.release();
        full.release();
        ImageIcon icon = new ImageIcon("D:\\Descargas\\fast-food.png");
        if ((in - 1) != -1) {
            comida[in - 1].setIcon(icon);
            // System.out.println(in-1);
            vista.jProgressBar1.setValue(in - 1);
        } else {
            comida[in + 1].setIcon(icon);
            comida[0].setIcon(icon);
            comida[bufferSize - 1].setIcon(icon);
            //System.out.println(in);
            vista.jProgressBar1.setValue(bufferSize);
        }

        if (estadoActual != -1) {
            for (int i = 0; i < estadoActual; i++) {
                System.out.println("Falta: " + i);
                comida[i].setIcon(icon);
            }
        }

        progreso++;

        if (vista.jProgressBar1.getValue() == bufferSize) {

            for (int i = 0; i < producers.length; i++) {
                if (producers[i].running) {
                    producers[i].stopThread();
                    //System.out.println("Durmiendo productor: "+i);
                }

            }
            progreso = bufferSize;

            Thread.sleep(5000);

            for (int i = 0; i < consumers.length; i++) {
                if (!consumers[i].running) {
                    consumers[i].startThread();
                }

            }

            estados[0].setIcon(stopIcon);
            estados[1].setIcon(startIcon);
            vista.jButton2.setEnabled(false);
            vista.jButton3.setEnabled(true);
            buffer[in] = item;

        }

    }

    public int consume() throws InterruptedException {

        full.acquire();
        mutex.acquire();
        int item = buffer[out];
        out = (out + 1) % bufferSize;
        mutex.release();
        empty.release();
        comida[out].setIcon(null);

        vista.jProgressBar1.setValue(progreso);

        if (progreso == 0) {
            comida[out + 1].setIcon(null);

            for (int i = 0; i < consumers.length; i++) {

                if (consumers[i].running) {
                    consumers[i].stopThread();
                }
            }

            Thread.sleep(3000);

            for (int i = 0; i < producers.length; i++) {

                if (!producers[i].running) {
                    producers[i].startThread();
                }

            }

            estados[0].setIcon(startIcon);
            estados[1].setIcon(stopIcon);

            vista.jButton2.setEnabled(true);
            vista.jButton3.setEnabled(false);

        }
        ImageIcon icon = new ImageIcon("D:\\Descargas\\fast-food.png");

        if (estadoActual1 != -1) {
            for (int i = 0; i < estadoActual1; i++) {
                System.out.println("Falta: " + i);
                comida[i].setIcon(icon);
            }
        }

        return item;
    }

    public boolean isFull() {
        return (in + 1) % bufferSize == out;
    }

    public void entrar() throws InterruptedException {
        comida[out + 1].setIcon(null);

        for (int i = 0; i < consumers.length; i++) {
            if (consumers[i].running) {
                consumers[i].stopThread();
            }
        }

        Thread.sleep(3000);

        for (int i = 0; i < producers.length; i++) {

            if (!producers[i].running) {
                producers[i].startThread();
            }

        }
        vista.jProgressBar1.setValue(0);
        estados[0].setIcon(startIcon);
        estados[1].setIcon(stopIcon);

        vista.jButton2.setEnabled(true);
        vista.jButton3.setEnabled(false);
        salir = 1;
    }

    public void pausar() {
        for (int i = 0; i < producers.length; i++) {
            producers[i].stopThread();
        }

        for (int i = 0; i < consumers.length; i++) {
            consumers[i].stopThread();
        }
    }

}
