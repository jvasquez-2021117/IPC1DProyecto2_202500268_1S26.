/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vqzjo
 */

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class VentanaPrincipal extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel contenedor;

    private PanelMenu panelMenu;
    private PanelTienda panelTienda;
    
    public VentanaPrincipal() {
        configurarVentana();
        inicializarContenedor();
        configurarEventos();
    }
    

    private void configurarVentana() {
        setTitle("GameZone Pro");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarContenedor() {
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelMenu = new PanelMenu();
        panelTienda = new PanelTienda();

        contenedor.add(panelMenu, "MENU");
        contenedor.add(panelTienda, "TIENDA");

        add(contenedor);

        cardLayout.show(contenedor, "MENU");
    }
    
    private void configurarEventos() {
        panelMenu.getBtnTienda().addActionListener(e -> {
            cardLayout.show(contenedor, "TIENDA");
        });

        panelTienda.getBtnVolver().addActionListener(e -> {
            cardLayout.show(contenedor, "MENU");
        });

        panelMenu.getBtnSalir().addActionListener(e -> {
            dispose();
        });
    }
    

}


