/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vqzjo
 */


import estructuras.NodoSimple;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.TicketVendido;
import modelo.Torneo;
import servicios.ArchivoService;
import servicios.TorneoService;
import util.EscuchadorTaquilla;
import util.TaquillaThread;
import estructuras.ListaSimple;
import servicios.GamificacionService;


public class PanelTorneos extends JPanel implements EscuchadorTaquilla {
    
    private JButton btnVolver;
    private JButton btnInscribirse;
    private JButton btnIniciarVenta;

    private JList<String> listaTorneos;
    private DefaultListModel<String> modeloListaTorneos;

    private JTextArea areaDetallesTorneo;
    private JTextArea areaCola;
    private JTextArea areaTicketsVendidos;

    private JTextField txtNombreUsuario;

    private JLabel lblEstadoTaquilla1;
    private JLabel lblEstadoTaquilla2;

    private TorneoService torneoService;

    private TaquillaThread taquilla1;
    private TaquillaThread taquilla2;

    private boolean ventaEnProceso;
    
    private ArchivoService archivoService;
    private GamificacionService gamificacionService;
    private PanelReportes panelReportes;

    public PanelTorneos() {
        this(new GamificacionService("Jugador Actual"));
    }

    public PanelTorneos(GamificacionService gamificacionService) {
        this.torneoService = new TorneoService();
        this.archivoService = new ArchivoService();
        this.gamificacionService = gamificacionService;
        this.ventaEnProceso = false;

        inicializarComponentes();
        cargarDatos();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("MÓDULO EVENTOS ESPECIALES / TORNEOS", JLabel.CENTER);

        // PANEL IZQUIERDO: LISTA DE TORNEOS
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Torneos disponibles"));

        modeloListaTorneos = new DefaultListModel<>();
        listaTorneos = new JList<>(modeloListaTorneos);

        JScrollPane scrollListaTorneos = new JScrollPane(listaTorneos);

        panelIzquierdo.add(scrollListaTorneos, BorderLayout.CENTER);

        // PANEL DERECHO: DETALLES + COLA + LOG
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));

        JPanel panelSuperiorDerecho = new JPanel(new BorderLayout(10, 10));

        areaDetallesTorneo = new JTextArea();
        areaDetallesTorneo.setEditable(false);
        areaDetallesTorneo.setLineWrap(true);
        areaDetallesTorneo.setWrapStyleWord(true);
        areaDetallesTorneo.setText("Seleccione un torneo para ver sus detalles.");

        JScrollPane scrollDetalles = new JScrollPane(areaDetallesTorneo);
        scrollDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del torneo"));

        JPanel panelInscripcion = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInscripcion.setBorder(BorderFactory.createTitledBorder("Inscripción"));

        panelInscripcion.add(new JLabel("Nombre del usuario:"));
        txtNombreUsuario = new JTextField();
        panelInscripcion.add(txtNombreUsuario);

        btnInscribirse = new JButton("Inscribirse / Comprar Ticket");
        btnIniciarVenta = new JButton("Iniciar venta");
        panelInscripcion.add(btnInscribirse);
        panelInscripcion.add(btnIniciarVenta);

        panelSuperiorDerecho.add(scrollDetalles, BorderLayout.CENTER);
        panelSuperiorDerecho.add(panelInscripcion, BorderLayout.SOUTH);

        areaCola = new JTextArea();
        areaCola.setEditable(false);
        areaCola.setLineWrap(true);
        areaCola.setWrapStyleWord(true);

        areaTicketsVendidos = new JTextArea();
        areaTicketsVendidos.setEditable(false);
        areaTicketsVendidos.setLineWrap(true);
        areaTicketsVendidos.setWrapStyleWord(true);

        JScrollPane scrollCola = new JScrollPane(areaCola);
        scrollCola.setBorder(BorderFactory.createTitledBorder("Cola restante"));

        JScrollPane scrollTickets = new JScrollPane(areaTicketsVendidos);
        scrollTickets.setBorder(BorderFactory.createTitledBorder("Tickets vendidos"));

        JSplitPane splitInferior = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollCola,
                scrollTickets
        );
        splitInferior.setResizeWeight(0.4);
        splitInferior.setDividerLocation(180);
        splitInferior.setBorder(null);

        lblEstadoTaquilla1 = new JLabel("Taquilla 1: Libre");
        lblEstadoTaquilla2 = new JLabel("Taquilla 2: Libre");

        JPanel panelEstados = new JPanel(new GridLayout(1, 2, 10, 10));
        panelEstados.setBorder(BorderFactory.createTitledBorder("Estado de taquillas"));
        panelEstados.add(lblEstadoTaquilla1);
        panelEstados.add(lblEstadoTaquilla2);

        JPanel contenedorCentralDerecho = new JPanel(new BorderLayout(10, 10));
        contenedorCentralDerecho.add(splitInferior, BorderLayout.CENTER);
        contenedorCentralDerecho.add(panelEstados, BorderLayout.SOUTH);

        panelDerecho.add(panelSuperiorDerecho, BorderLayout.NORTH);
        panelDerecho.add(contenedorCentralDerecho, BorderLayout.CENTER);

        JSplitPane splitPrincipal = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                panelIzquierdo,
                panelDerecho
        );
        splitPrincipal.setResizeWeight(0.3);
        splitPrincipal.setDividerLocation(280);
        splitPrincipal.setBorder(null);

        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(splitPrincipal, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }
    
    public void guardarDatos() {
        archivoService.guardarTorneos(torneoService.getTorneos());
        archivoService.guardarTicketsVendidos(torneoService.getTicketsVendidos());
    }

    private void cargarDatos() {
        ListaSimple ticketsCargados = archivoService.cargarTicketsVendidos();
        torneoService.setTicketsVendidos(ticketsCargados);

        refrescarListaTorneos();
        refrescarCola();
        refrescarTicketsVendidos();
    }

    private void configurarEventos() {
        listaTorneos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                manejarSeleccionTorneo();
            }
        });

        btnInscribirse.addActionListener(e -> {
            inscribirUsuario();
        });

        btnIniciarVenta.addActionListener(e -> {
            iniciarVenta();
        });
    }

    private void refrescarListaTorneos() {
        modeloListaTorneos.clear();

        NodoSimple actual = torneoService.getTorneos().getCabeza();

        while (actual != null) {
            Torneo torneo = (Torneo) actual.getDato();

            modeloListaTorneos.addElement(
                    torneo.getIdTorneo() + " | "
                    + torneo.getNombre() + " | "
                    + torneo.getJuego() + " | Tickets: "
                    + torneo.getTicketsDisponibles()
            );

            actual = actual.getSiguiente();
        }
    }

    private void manejarSeleccionTorneo() {
        if (ventaEnProceso || torneoService.getTamanioCola() > 0) {
            return;
        }

        int indice = listaTorneos.getSelectedIndex();

        if (indice < 0) {
            return;
        }

        Torneo torneo = obtenerTorneoPorIndice(indice);

        if (torneo == null) {
            return;
        }

        torneoService.setTorneoSeleccionado(torneo);
        areaDetallesTorneo.setText(torneoService.obtenerResumenTorneoSeleccionado());
    }

    private Torneo obtenerTorneoPorIndice(int indice) {
        int contador = 0;
        NodoSimple actual = torneoService.getTorneos().getCabeza();

        while (actual != null) {
            if (contador == indice) {
                return (Torneo) actual.getDato();
            }

            contador++;
            actual = actual.getSiguiente();
        }

        return null;
    }
    
    private void guardarGamificacion() {
        archivoService.guardarLeaderboard(
                gamificacionService.getLeaderboardOrdenadoParaGuardar(),
                gamificacionService.getCantidadLeaderboard()
        );
    }

    private void inscribirUsuario() {
        if (ventaEnProceso) {
            JOptionPane.showMessageDialog(this, "La venta ya está en proceso.");
            return;
        }

        if (torneoService.getTorneoSeleccionado() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un torneo primero.");
            return;
        }

        String nombreUsuario = txtNombreUsuario.getText().trim();

        String resultado = torneoService.inscribirUsuario(nombreUsuario);

        if (resultado == null) {
            String resumenGamificacion = gamificacionService.registrarInscripcionTorneo();
            guardarGamificacion();

            txtNombreUsuario.setText("");
            refrescarCola();

            if (torneoService.getTamanioCola() > 0) {
                listaTorneos.setEnabled(false);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario agregado a la cola.\n\n" + resumenGamificacion
            );
        } else {
            JOptionPane.showMessageDialog(this, resultado);
        }
    }

    private void iniciarVenta() {
        if (ventaEnProceso) {
            JOptionPane.showMessageDialog(this, "La venta ya se está ejecutando.");
            return;
        }

        if (torneoService.getTorneoSeleccionado() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un torneo primero.");
            return;
        }

        if (!torneoService.haySolicitudesPendientes()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios en la cola.");
            return;
        }

        if (!torneoService.torneoSeleccionadoTieneTickets()) {
            JOptionPane.showMessageDialog(this, "El torneo seleccionado ya no tiene tickets.");
            return;
        }

        ventaEnProceso = true;
        btnIniciarVenta.setEnabled(false);

        taquilla1 = new TaquillaThread("Taquilla 1", torneoService, this);
        taquilla2 = new TaquillaThread("Taquilla 2", torneoService, this);

        taquilla1.start();
        taquilla2.start();
    }

    private void refrescarCola() {
        areaCola.setText(torneoService.obtenerTextoCola());
    }

    private void refrescarTicketsVendidos() {
        areaTicketsVendidos.setText(torneoService.obtenerTextoTicketsVendidos());
    }

    private void verificarFinVenta() {
        boolean taquilla1Libre = lblEstadoTaquilla1.getText().contains("Libre");
        boolean taquilla2Libre = lblEstadoTaquilla2.getText().contains("Libre");

        if (ventaEnProceso && torneoService.getTamanioCola() == 0 && taquilla1Libre && taquilla2Libre) {
            ventaEnProceso = false;
            btnIniciarVenta.setEnabled(true);
            listaTorneos.setEnabled(true);
            guardarDatos();
            refrescarListaTorneos();

            if (torneoService.getTorneoSeleccionado() != null) {
                areaDetallesTorneo.setText(torneoService.obtenerResumenTorneoSeleccionado());
            }

            JOptionPane.showMessageDialog(this, "Proceso de venta finalizado.");
        }
    }

    @Override
    public void actualizarEstadoTaquilla(String nombreTaquilla, String estado) {
        if (nombreTaquilla.equalsIgnoreCase("Taquilla 1")) {
            lblEstadoTaquilla1.setText(nombreTaquilla + ": " + estado);
        } else if (nombreTaquilla.equalsIgnoreCase("Taquilla 2")) {
            lblEstadoTaquilla2.setText(nombreTaquilla + ": " + estado);
        }

        refrescarListaTorneos();

        if (torneoService.getTorneoSeleccionado() != null) {
            areaDetallesTorneo.setText(torneoService.obtenerResumenTorneoSeleccionado());
        }

        verificarFinVenta();
    }

    @Override
    public void actualizarColaVisual() {
        refrescarCola();
        refrescarListaTorneos();

        if (torneoService.getTorneoSeleccionado() != null) {
            areaDetallesTorneo.setText(torneoService.obtenerResumenTorneoSeleccionado());
        }

        if (!ventaEnProceso && torneoService.getTamanioCola() == 0) {
            listaTorneos.setEnabled(true);
        }
    }

    @Override
    public void registrarTicketVendido(TicketVendido ticket) {
        guardarDatos();
        refrescarTicketsVendidos();
        refrescarListaTorneos();

        if (torneoService.getTorneoSeleccionado() != null) {
            areaDetallesTorneo.setText(torneoService.obtenerResumenTorneoSeleccionado());
        }
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
    public ListaSimple getTorneosActuales() {
        return torneoService.getTorneos();
    }

    public ListaSimple getTicketsVendidosActuales() {
        return torneoService.getTicketsVendidos();
    }
    
}
