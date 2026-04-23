/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author vqzjo
 */

import javax.swing.SwingUtilities;
import modelo.Juego;
import servicios.TiendaService;
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
