/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app.pc.mvc1;

import controlador.Controller;
import vista.View;

/**
 *
 * @author Cheto Sandoval
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        View vista = new View(); // Capacidad del buffer
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        Controller controller = new Controller(vista);
    }

}
