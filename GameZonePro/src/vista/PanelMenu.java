/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vqzjo
 */

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

public class PanelMenu extends JPanel {
    
    private JButton btnTienda;
    private JButton btnAlbum;
    private JButton btnTorneos;
    private JButton btnGamificacion;
    private JButton btnReportes;
    private JButton btnSalir;
    
    public PanelMenu() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(7, 1, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100)); // margenes en px, arriba, izquierda, abajo, derech

        JLabel titulo = new JLabel("GAMEZONE PRO", JLabel.CENTER);

        btnTienda = new JButton("Tienda de Videojuegos");
        btnAlbum = new JButton("Álbum de Cartas");
        btnTorneos = new JButton("Eventos Especiales");
        btnGamificacion = new JButton("Gamificación");
        btnReportes = new JButton("Reportes");
        btnSalir = new JButton("Salir");

        panelCentral.add(titulo);
        panelCentral.add(btnTienda);
        panelCentral.add(btnAlbum);
        panelCentral.add(btnTorneos);
        panelCentral.add(btnGamificacion);
        panelCentral.add(btnReportes);
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);
    }

    public JButton getBtnTienda() {
        return btnTienda;
    }

    public JButton getBtnAlbum() {
        return btnAlbum;
    }

    public JButton getBtnTorneos() {
        return btnTorneos;
    }

    public JButton getBtnGamificacion() {
        return btnGamificacion;
    }

    public JButton getBtnReportes() {
        return btnReportes;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

}
