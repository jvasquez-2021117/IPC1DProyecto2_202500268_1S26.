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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import servicios.ReporteService;


public class PanelReportes extends JPanel {
    
    private JButton btnVolver;
    private JButton btnReporteInventario;
    private JButton btnReporteVentas;
    private JButton btnReporteAlbum;
    private JButton btnReporteTorneos;

    private JTextArea areaDescripcion;

    private ReporteService reporteService;

    private PanelTienda panelTienda;
    private PanelAlbum panelAlbum;
    private PanelTorneos panelTorneos;

    public PanelReportes(PanelTienda panelTienda, PanelAlbum panelAlbum, PanelTorneos panelTorneos) {
        this.reporteService = new ReporteService();
        this.panelTienda = panelTienda;
        this.panelAlbum = panelAlbum;
        this.panelTorneos = panelTorneos;

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("MÓDULO REPORTES HTML", JLabel.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Generar reportes"));

        btnReporteInventario = new JButton("Generar reporte de Inventario");
        btnReporteVentas = new JButton("Generar reporte de Ventas");
        btnReporteAlbum = new JButton("Generar reporte del Álbum");
        btnReporteTorneos = new JButton("Generar reporte de Torneos");

        panelBotones.add(btnReporteInventario);
        panelBotones.add(btnReporteVentas);
        panelBotones.add(btnReporteAlbum);
        panelBotones.add(btnReporteTorneos);

        areaDescripcion = new JTextArea();
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setText(
                "Desde este módulo puede generar los 4 reportes HTML del sistema.\n\n"
                + "1. Inventario de Tienda\n"
                + "2. Ventas\n"
                + "3. Álbum\n"
                + "4. Torneos\n\n"
                + "Cada reporte se guarda con timestamp y se abre automáticamente en el navegador."
        );

        JScrollPane scrollDescripcion = new JScrollPane(areaDescripcion);
        scrollDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));

        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.WEST);
        add(scrollDescripcion, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        btnReporteInventario.addActionListener(e -> {
            generarReporteInventario();
        });

        btnReporteVentas.addActionListener(e -> {
            generarReporteVentas();
        });

        btnReporteAlbum.addActionListener(e -> {
            generarReporteAlbum();
        });

        btnReporteTorneos.addActionListener(e -> {
            generarReporteTorneos();
        });
    }

    private void generarReporteInventario() {
        boolean generado = reporteService.generarReporteInventario(
                panelTienda.getCatalogoActual()
        );

        mostrarResultado(generado, "Reporte de Inventario");
    }

    private void generarReporteVentas() {
        boolean generado = reporteService.generarReporteVentas(
                panelTienda.getHistorialComprasActual()
        );

        mostrarResultado(generado, "Reporte de Ventas");
    }

    private void generarReporteAlbum() {
        boolean generado = reporteService.generarReporteAlbum(
                panelAlbum.getAlbumActual()
        );

        mostrarResultado(generado, "Reporte del Álbum");
    }

    private void generarReporteTorneos() {
        boolean generado = reporteService.generarReporteTorneos(
                panelTorneos.getTorneosActuales(),
                panelTorneos.getTicketsVendidosActuales()
        );

        mostrarResultado(generado, "Reporte de Torneos");
    }

    private void mostrarResultado(boolean generado, String nombreReporte) {
        if (generado) {
            JOptionPane.showMessageDialog(this, nombreReporte + " generado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo generar " + nombreReporte + ".");
        }
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
}
