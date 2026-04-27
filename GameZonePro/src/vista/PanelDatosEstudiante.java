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
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelDatosEstudiante extends JPanel{
    
    private JButton btnVolver;

    public PanelDatosEstudiante() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("DATOS DEL ESTUDIANTE", JLabel.CENTER);

        JPanel panelDatos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Información del Estudiante"));

        panelDatos.add(new JLabel("Nombre completo:"));
        panelDatos.add(new JLabel("José Marcos Vásquez Román"));

        panelDatos.add(new JLabel("Número de carné:"));
        panelDatos.add(new JLabel("202500268"));

        panelDatos.add(new JLabel("Correo universitario:"));
        panelDatos.add(new JLabel("3033113040108@ingenieria.usac.edu.gt"));

        panelDatos.add(new JLabel("Sección del curso:"));
        panelDatos.add(new JLabel("D"));

        panelDatos.add(new JLabel("Semestre y año:"));
        panelDatos.add(new JLabel("Primer Semestre 2026"));

        JTextArea areaDescripcion = new JTextArea();
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setBorder(BorderFactory.createTitledBorder("Acerca de GameZone Pro"));
        areaDescripcion.setText(
                "GameZone Pro es una aplicación de escritorio desarrollada en Java Swing "
                + "que centraliza diferentes experiencias gamer dentro de una sola plataforma. "
                + "El sistema incluye una tienda de videojuegos, un álbum de cartas coleccionables, "
                + "un módulo de eventos especiales con venta de tickets usando hilos, un sistema "
                + "de gamificación con niveles, logros y leaderboard, y un módulo de reportes HTML. "
                + "El proyecto fue desarrollado aplicando estructuras de datos implementadas desde cero, "
                + "persistencia en archivos de texto y navegación por paneles usando CardLayout."
        );

        JScrollPane scrollDescripcion = new JScrollPane(areaDescripcion);

        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(panelDatos, BorderLayout.CENTER);
        add(scrollDescripcion, BorderLayout.SOUTH);
        add(btnVolver, BorderLayout.PAGE_END);
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
}
