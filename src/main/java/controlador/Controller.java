/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import modelo.Buffer;
import modelo.Consumer;
import modelo.Producer;
import vista.View;

/**
 *
 * @author Cheto Sandoval
 */
public class Controller implements ActionListener {

    private Buffer buffer;
    private View vista;
    public JLabel[] productores;
    public JLabel[] comida;
    public JLabel[] consumidores;

    Producer[] producers;
    Consumer[] consumers;

    int tam;

    public Controller(View vista) {
        this.vista = vista;
        this.vista.jButton1.addActionListener(this);
        this.vista.jButton2.addActionListener(this);
        this.vista.jButton3.addActionListener(this);

        ImageIcon icon = new ImageIcon("D:\\Descargas\\_11cf3956-d748-4268-99bd-431516f808e7.jpg");
        JLabel backgroundLabel = new JLabel(icon);
        backgroundLabel.setBounds(0, 0, vista.getWidth(), vista.getHeight());
        vista.jLayeredPane1.add(backgroundLabel, Integer.valueOf(0));
    }

    public void startSimulation(int numProducers, int numConsumers) throws InterruptedException {

        for (int i = 0; i < numProducers; i++) {
            producers[i] = new Producer(buffer, productores[i], i, vista);
            producers[i].start();
        }

        Thread.sleep(5000);

        for (int i = 0; i < numConsumers; i++) {
            consumers[i] = new Consumer(buffer, consumidores[i], i, producers, comida);
            consumers[i].start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton1) {
            try {
                pedirDatos();
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.jButton2) {
            try {
                controlButton2();
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.jButton3) {
            controlButton3();
        }
    }

    public void pedirDatos() throws InterruptedException {
        int nProductores = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de productores que desea: "));
        int nConsumidores = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de consumidores que desea: "));
        productores = new JLabel[nProductores];
        tam = nProductores;
        consumidores = new JLabel[nConsumidores];
        comida = new JLabel[nConsumidores * 2];
        int salto = 100;
        int x = 1;

        for (int i = 0; i < consumidores.length; i++) {
            ImageIcon icon = new ImageIcon("D:\\Descargas\\healthy.png");
            consumidores[i] = new JLabel(icon);
            consumidores[i].setSize(50, 50);
            consumidores[i].setLocation(600, (i * 80) + 20);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Color y grosor del borde
            consumidores[i].setBorder(border);

            vista.jLayeredPane1.add(consumidores[i], Integer.valueOf(1));
        }

        for (int i = 0; i < productores.length; i++) {
            ImageIcon icon = new ImageIcon("D:\\Descargas\\chef.png");
            productores[i] = new JLabel(icon);
            productores[i].setSize(50, 50);
            productores[i].setLocation(100, (i * 80) + 20);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Color y grosor del borde
            productores[i].setBorder(border);

            vista.jLayeredPane1.add(productores[i], Integer.valueOf(1));
        }

        for (int i = 0; i < comida.length; i++) {
            comida[i] = new JLabel();
            comida[i].setSize(50, 50);
            comida[i].setLocation((x * 100) + 160, salto);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Color y grosor del borde
            comida[i].setBorder(border);

            vista.jLayeredPane1.add(comida[i], Integer.valueOf(1));
            x++;

            if (x % 4 == 0) {
                salto += 80;
                x = 1;
            }

        }

        vista.repaint();

        producers = new Producer[nProductores];
        consumers = new Consumer[nConsumidores];

        buffer = new Buffer(nConsumidores * 2, comida, vista, producers, consumers);
        vista.jProgressBar1.setMaximum(nConsumidores * 2);

        startSimulation(nProductores, nConsumidores);
    }

    public void controlButton2() throws InterruptedException {

        if (buffer.isRunning == true) {
            buffer.isRunning = false;
        } else {
            buffer.isRunning = true;
            for (int i = 0; i < producers.length; i++) {
                producers[i].startThread();
            }
        }

    }

    public void controlButton3() {

        if (buffer.isRunning1 == true) {
            buffer.isRunning1 = false;
        } else {
            buffer.isRunning1 = true;

            for (int i = 0; i < consumers.length; i++) {
                consumers[i].startThread();

            }
        }
    }
}
