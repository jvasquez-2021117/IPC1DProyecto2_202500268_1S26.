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
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.UsuarioGamificacion;
import servicios.ArchivoService;
import servicios.GamificacionService;

public class PanelGamificacion extends JPanel {
    
    private JButton btnVolver;
    private JLabel lblUsuario;
    private JLabel lblXp;
    private JLabel lblNivel;
    private JLabel lblRango;
    private JLabel lblXpSiguiente;

    private JProgressBar barraProgreso;

    private JTextArea areaLogros;

    private JLabel lblPodio1;
    private JLabel lblPodio2;
    private JLabel lblPodio3;

    private JTable tablaLeaderboard;
    private DefaultTableModel modeloTablaLeaderboard;

    private GamificacionService gamificacionService;
    private ArchivoService archivoService;

    public PanelGamificacion(GamificacionService gamificacionService) {
        this.gamificacionService = gamificacionService;
        this.archivoService = new ArchivoService();

        inicializarComponentes();
        cargarDatos();
        configurarEventos();
    }
    
    public PanelGamificacion() {
        this(new GamificacionService("Jugador Actual"));
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("MÓDULO GAMIFICACIÓN", JLabel.CENTER);

        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));

        JPanel panelResumen = new JPanel(new GridLayout(6, 1, 5, 5));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen del usuario"));

        lblUsuario = new JLabel("Usuario: ");
        lblXp = new JLabel("XP: ");
        lblNivel = new JLabel("Nivel: ");
        lblRango = new JLabel("Rango: ");
        lblXpSiguiente = new JLabel("XP para siguiente nivel: ");

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);

        panelResumen.add(lblUsuario);
        panelResumen.add(lblXp);
        panelResumen.add(lblNivel);
        panelResumen.add(lblRango);
        panelResumen.add(lblXpSiguiente);
        panelResumen.add(barraProgreso);

        areaLogros = new JTextArea();
        areaLogros.setEditable(false);
        areaLogros.setLineWrap(true);
        areaLogros.setWrapStyleWord(true);

        JScrollPane scrollLogros = new JScrollPane(areaLogros);
        scrollLogros.setBorder(BorderFactory.createTitledBorder("Logros"));

        panelIzquierdo.add(panelResumen, BorderLayout.NORTH);
        panelIzquierdo.add(scrollLogros, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));

        JPanel panelPodio = new JPanel(new GridLayout(1, 3, 10, 10));
        panelPodio.setBorder(BorderFactory.createTitledBorder("Podio"));

        lblPodio1 = crearLabelPodio("1° Lugar\nVacío", new Color(255, 215, 0));
        lblPodio2 = crearLabelPodio("2° Lugar\nVacío", new Color(220, 220, 220));
        lblPodio3 = crearLabelPodio("3° Lugar\nVacío", new Color(205, 127, 50));

        panelPodio.add(lblPodio2);
        panelPodio.add(lblPodio1);
        panelPodio.add(lblPodio3);

        modeloTablaLeaderboard = new DefaultTableModel(
                new Object[]{"Pos.", "Usuario", "XP", "Nivel", "Rango"},
                0
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaLeaderboard = new JTable(modeloTablaLeaderboard);
        tablaLeaderboard.setDefaultRenderer(Object.class, new RenderizadorLeaderboard());

        JScrollPane scrollLeaderboard = new JScrollPane(tablaLeaderboard);
        scrollLeaderboard.setBorder(BorderFactory.createTitledBorder("Leaderboard"));

        panelDerecho.add(panelPodio, BorderLayout.NORTH);
        panelDerecho.add(scrollLeaderboard, BorderLayout.CENTER);

        JSplitPane splitPrincipal = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                panelIzquierdo,
                panelDerecho
        );
        splitPrincipal.setResizeWeight(0.42);
        splitPrincipal.setDividerLocation(420);
        splitPrincipal.setBorder(null);

        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(splitPrincipal, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        UsuarioGamificacion[] leaderboardCargado = archivoService.cargarLeaderboard();
        int cantidad = archivoService.obtenerCantidadLeaderboard(leaderboardCargado);

        gamificacionService.cargarLeaderboardDesdeArchivo(leaderboardCargado, cantidad);
        refrescarVista();
    }

    private void configurarEventos() {
        
    }

    private void refrescarVista() {
        refrescarResumenUsuario();
        refrescarLogros();
        refrescarPodio();
        refrescarTablaLeaderboard();
    }

    private void refrescarResumenUsuario() {
        UsuarioGamificacion usuario = gamificacionService.getUsuarioActual();

        lblUsuario.setText("Usuario: " + usuario.getNombreUsuario());
        lblXp.setText("XP: " + usuario.getXp());
        lblNivel.setText("Nivel: " + usuario.getNivel());
        lblRango.setText("Rango: " + usuario.getRango());

        if (usuario.esNivelMaximo()) {
            lblXpSiguiente.setText("XP para siguiente nivel: Nivel máximo alcanzado");
            barraProgreso.setMinimum(0);
            barraProgreso.setMaximum(usuario.getXp() == 0 ? 1 : usuario.getXp());
            barraProgreso.setValue(usuario.getXp());
            barraProgreso.setString("Nivel máximo");
        } else {
            lblXpSiguiente.setText("XP para siguiente nivel: " + usuario.getXpParaSiguienteNivel());
            barraProgreso.setMinimum(0);
            barraProgreso.setMaximum(gamificacionService.getMaximoProgreso());
            barraProgreso.setValue(gamificacionService.getValorProgreso());
            barraProgreso.setString(
                    gamificacionService.getValorProgreso() + " / " + gamificacionService.getMaximoProgreso()
            );
        }
    }

    private void refrescarLogros() {
        areaLogros.setText(gamificacionService.getTextoLogros());
    }

    private void refrescarPodio() {
        UsuarioGamificacion[] top10 = gamificacionService.getTop10Leaderboard();

        lblPodio1.setText(formatearTextoPodio("1° Lugar", top10.length > 0 ? top10[0] : null));
        lblPodio2.setText(formatearTextoPodio("2° Lugar", top10.length > 1 ? top10[1] : null));
        lblPodio3.setText(formatearTextoPodio("3° Lugar", top10.length > 2 ? top10[2] : null));
    }

    private void refrescarTablaLeaderboard() {
        modeloTablaLeaderboard.setRowCount(0);

        UsuarioGamificacion[] ordenado = gamificacionService.getLeaderboardOrdenado();
        int cantidad = gamificacionService.getCantidadLeaderboard();

        int limite = Math.min(cantidad, 10);
        String nombreActual = gamificacionService.getUsuarioActual().getNombreUsuario();
        boolean usuarioActualEnTop10 = false;
        int posicionRealUsuario = -1;
        UsuarioGamificacion usuarioActual = null;

        for (int i = 0; i < cantidad; i++) {
            if (ordenado[i] != null
                    && ordenado[i].getNombreUsuario().equalsIgnoreCase(nombreActual)) {
                posicionRealUsuario = i + 1;
                usuarioActual = ordenado[i];

                if (i < 10) {
                    usuarioActualEnTop10 = true;
                }
                break;
            }
        }

        for (int i = 0; i < limite; i++) {
            UsuarioGamificacion usuario = ordenado[i];

            if (usuario != null) {
                modeloTablaLeaderboard.addRow(new Object[]{
                    i + 1,
                    usuario.getNombreUsuario(),
                    usuario.getXp(),
                    usuario.getNivel(),
                    usuario.getRango()
                });
            }
        }

        if (!usuarioActualEnTop10 && usuarioActual != null) {
            modeloTablaLeaderboard.addRow(new Object[]{
                "...",
                "...",
                "...",
                "...",
                "..."
            });

            modeloTablaLeaderboard.addRow(new Object[]{
                posicionRealUsuario,
                usuarioActual.getNombreUsuario(),
                usuarioActual.getXp(),
                usuarioActual.getNivel(),
                usuarioActual.getRango()
            });
        }
    }

    private JLabel crearLabelPodio(String texto, Color colorFondo) {
        JLabel label = new JLabel("<html><center>" + texto.replace("\n", "<br>") + "</center></html>", JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(colorFondo);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return label;
    }

    private String formatearTextoPodio(String titulo, UsuarioGamificacion usuario) {
        if (usuario == null) {
            return "<html><center>" + titulo + "<br>Vacío</center></html>";
        }

        return "<html><center>"
                + titulo
                + "<br>"
                + usuario.getNombreUsuario()
                + "<br>XP: "
                + usuario.getXp()
                + "</center></html>";
    }

    public void guardarDatos() {
        gamificacionService.actualizarUsuarioActualEnLeaderboard();
        archivoService.guardarLeaderboard(
                gamificacionService.getLeaderboardOrdenadoParaGuardar(),
                gamificacionService.getCantidadLeaderboard()
        );
    }

    public GamificacionService getGamificacionService() {
        return gamificacionService;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    private class RenderizadorLeaderboard extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable tabla,
                Object valor,
                boolean seleccionado,
                boolean enfocado,
                int fila,
                int columna
        ) {
            Component componente = super.getTableCellRendererComponent(
                    tabla, valor, seleccionado, enfocado, fila, columna
            );

            String nombreUsuarioFila = tabla.getValueAt(fila, 1).toString();
            String nombreActual = gamificacionService.getUsuarioActual().getNombreUsuario();

            if (nombreUsuarioFila.equalsIgnoreCase(nombreActual)) {
                componente.setBackground(new Color(198, 239, 206));
            } else {
                componente.setBackground(Color.WHITE);
            }

            return componente;
        }
    }
    
    public void refrescarDatosExternos() {
        refrescarVista();
    }
    
}
