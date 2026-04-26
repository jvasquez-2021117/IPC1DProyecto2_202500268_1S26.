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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class VentanaPrincipal extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel contenedor;

    private PanelMenu panelMenu;
    private PanelTienda panelTienda;
    private PanelAlbum panelAlbum;
    private PanelTorneos panelTorneos;
    
    public VentanaPrincipal() {
        configurarVentana();
        inicializarContenedor();
        configurarEventos();
        configurarCierre();
    }
    
    private void configurarCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (panelAlbum != null) {
                    panelAlbum.guardarDatos();
                }

                if (panelTorneos != null) {
                    panelTorneos.guardarDatos();
                }
            }
        });
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
        panelAlbum = new PanelAlbum();
        panelTorneos = new PanelTorneos();

        contenedor.add(panelMenu, "MENU");
        contenedor.add(panelTienda, "TIENDA");
        contenedor.add(panelAlbum, "ALBUM");
        contenedor.add(panelTorneos, "TORNEOS");

        add(contenedor);

        cardLayout.show(contenedor, "MENU");
    }
    
    private void configurarEventos() {
        panelMenu.getBtnTienda().addActionListener(e -> {
            cardLayout.show(contenedor, "TIENDA");
        });
        
        panelMenu.getBtnAlbum().addActionListener(e -> {
            cardLayout.show(contenedor, "ALBUM");
        });

        panelTienda.getBtnVolver().addActionListener(e -> {
            cardLayout.show(contenedor, "MENU");
        });

        
        panelAlbum.getBtnVolver().addActionListener(e -> {
            cardLayout.show(contenedor, "MENU");
        });
        
        panelMenu.getBtnSalir().addActionListener(e -> {
            dispose();
        });
        
        panelMenu.getBtnTorneos().addActionListener(e -> {
            cardLayout.show(contenedor, "TORNEOS");
        });

        panelTorneos.getBtnVolver().addActionListener(e -> {
            cardLayout.show(contenedor, "MENU");
        });
    }
    

}


