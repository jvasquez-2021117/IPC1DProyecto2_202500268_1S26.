/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vqzjo
 */

import estructuras.NodoMatriz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Carta;
import servicios.AlbumService;
import servicios.ArchivoService;
import estructuras.Album;
import servicios.GamificacionService;

public class PanelAlbum extends JPanel {

    private JButton btnVolver;
    private JButton btnAgregarCarta;
    private JButton btnIntercambiarCartas;
    private JButton btnLimpiarBusqueda;

    private JPanel panelCuadricula;
    private JTextArea areaDetallesCarta;
    private JTextField txtBuscar;
    private JLabel lblResumenAlbum;
    private JLabel lblEstadoIntercambio;

    private AlbumService albumService;

    private NodoMatriz nodoSeleccionado;
    private NodoMatriz primeraSeleccionIntercambio;
    private ArchivoService archivoService;
    private GamificacionService gamificacionService;

    public PanelAlbum() {
        this(new GamificacionService("Jugador Actual"));
    }

    public PanelAlbum(GamificacionService gamificacionService) {
        this.albumService = new AlbumService();
        this.archivoService = new ArchivoService();
        this.gamificacionService = gamificacionService;

        inicializarComponentes();
        cargarDatos();
        refrescarVista();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("MÓDULO ÁLBUM DE CARTAS", JLabel.CENTER);

        // PANEL IZQUIERDO: BÚSQUEDA + CUADRÍCULA
        JPanel contenedorAlbum = new JPanel(new BorderLayout(10, 10));
        contenedorAlbum.setBorder(BorderFactory.createTitledBorder("Álbum"));

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lblBuscar = new JLabel("Buscar por nombre, tipo o rareza:");
        txtBuscar = new JTextField();

        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        lblResumenAlbum = new JLabel("Cartas: 0 / 0");
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        panelSuperior.add(lblResumenAlbum, BorderLayout.SOUTH);

        panelCuadricula = new JPanel();
        panelCuadricula.setLayout(new GridLayout(
                albumService.getAlbum().getFilas(),
                albumService.getAlbum().getColumnas(),
                10,
                10
        ));
        panelCuadricula.setBackground(Color.WHITE);

        JScrollPane scrollAlbum = new JScrollPane(panelCuadricula);
        scrollAlbum.setBorder(null);

        contenedorAlbum.add(panelSuperior, BorderLayout.NORTH);
        contenedorAlbum.add(scrollAlbum, BorderLayout.CENTER);

        // PANEL DERECHO: DETALLES + CONTROLES
        JPanel panelDerecho = new JPanel(new BorderLayout(10, 10));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Detalles y Gestión"));

        areaDetallesCarta = new JTextArea();
        areaDetallesCarta.setEditable(false);
        areaDetallesCarta.setLineWrap(true);
        areaDetallesCarta.setWrapStyleWord(true);
        areaDetallesCarta.setText("Seleccione una celda del álbum para ver sus detalles.");

        JScrollPane scrollDetalles = new JScrollPane(areaDetallesCarta);

        btnAgregarCarta = new JButton("Agregar carta");
        btnIntercambiarCartas = new JButton("Intercambiar cartas");
        btnLimpiarBusqueda = new JButton("Limpiar búsqueda");

        lblEstadoIntercambio = new JLabel("Intercambio: sin iniciar");

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.add(btnAgregarCarta);
        panelBotones.add(btnIntercambiarCartas);
        panelBotones.add(btnLimpiarBusqueda);
        panelBotones.add(lblEstadoIntercambio);

        panelDerecho.add(scrollDetalles, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        JSplitPane splitPrincipal = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                contenedorAlbum,
                panelDerecho
        );
        splitPrincipal.setResizeWeight(0.7);
        splitPrincipal.setDividerLocation(700);
        splitPrincipal.setBorder(null);

        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(splitPrincipal, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }
    
    private void guardarGamificacion() {
        archivoService.guardarLeaderboard(
                gamificacionService.getLeaderboardOrdenadoParaGuardar(),
                gamificacionService.getCantidadLeaderboard()
        );
    }
    
    public void guardarDatos() {
        archivoService.guardarAlbum(albumService.getAlbum());
    }

    private void cargarDatos() {
        Album albumCargado = archivoService.cargarAlbumDesdeArchivo();
        albumService.setAlbum(albumCargado);
    }

    private void configurarEventos() {
        btnAgregarCarta.addActionListener(e -> {
            agregarCartaDesdeFormulario();
        });

        btnIntercambiarCartas.addActionListener(e -> {
            intercambiarCartasSeleccionadas();
        });

        btnLimpiarBusqueda.addActionListener(e -> {
            txtBuscar.setText("");
            refrescarVista();
        });

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refrescarVista();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refrescarVista();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refrescarVista();
            }
        });
    }

    private void refrescarVista() {
        panelCuadricula.removeAll();

        NodoMatriz filaActual = albumService.getAlbum().getCabeza();

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                JPanel panelCelda = crearPanelCelda(columnaActual);
                panelCuadricula.add(panelCelda);
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        int totalCeldas = albumService.getAlbum().getFilas() * albumService.getAlbum().getColumnas();
        lblResumenAlbum.setText("Cartas: " + albumService.contarCartas() + " / " + totalCeldas);

        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    }

    private JPanel crearPanelCelda(NodoMatriz nodo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(130, 120));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Carta carta = nodo.getDato();

        if (nodo == nodoSeleccionado) {
            panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        } else if (nodo == primeraSeleccionIntercambio) {
            panel.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker(), 3));
        } else if (carta != null && albumService.cartaCoincideConBusqueda(carta, txtBuscar.getText().trim())) {
            panel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        } else {
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }

        if (carta == null) {
            panel.setBackground(new Color(230, 230, 230));

            JLabel lblPosicion = new JLabel("[" + nodo.getFila() + "," + nodo.getColumna() + "]", JLabel.CENTER);
            JLabel lblVacia = new JLabel("Vacía", JLabel.CENTER);

            panel.add(lblPosicion, BorderLayout.NORTH);
            panel.add(lblVacia, BorderLayout.CENTER);
        } else {
            panel.setBackground(obtenerColorPorTipo(carta.getTipo()));

            JLabel lblNombre = new JLabel(carta.getNombre(), JLabel.CENTER);
            JLabel lblTipo = new JLabel(carta.getTipo(), JLabel.CENTER);
            JLabel lblRareza = new JLabel(carta.getRareza(), JLabel.CENTER);

            lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblRareza.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel contenido = new JPanel();
            contenido.setOpaque(false);
            contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

            contenido.add(new JLabel("[" + nodo.getFila() + "," + nodo.getColumna() + "]", JLabel.CENTER));
            contenido.add(lblNombre);
            contenido.add(lblTipo);
            contenido.add(lblRareza);

            panel.add(contenido, BorderLayout.CENTER);
        }

        agregarEventoSeleccion(panel, nodo);

        return panel;
    }

    private void agregarEventoSeleccion(JComponent componente, NodoMatriz nodo) {
        MouseAdapter evento = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nodoSeleccionado = nodo;
                mostrarDetallesNodo(nodo);
                refrescarVista();
            }
        };

        componente.addMouseListener(evento);

        for (Component hijo : componente.getComponents()) {
            if (hijo instanceof JComponent) {
                agregarEventoSeleccion((JComponent) hijo, nodo);
            }
        }
    }

    private void mostrarDetallesNodo(NodoMatriz nodo) {
        if (nodo == null) {
            areaDetallesCarta.setText("No hay celda seleccionada.");
            return;
        }

        Carta carta = nodo.getDato();

        areaDetallesCarta.setText("");
        areaDetallesCarta.append("Posición: [" + nodo.getFila() + "," + nodo.getColumna() + "]\n\n");

        if (carta == null) {
            areaDetallesCarta.append("Estado: Celda vacía\n");
            return;
        }

        areaDetallesCarta.append("Código: " + carta.getCodigo() + "\n");
        areaDetallesCarta.append("Nombre: " + carta.getNombre() + "\n");
        areaDetallesCarta.append("Tipo: " + carta.getTipo() + "\n");
        areaDetallesCarta.append("Rareza: " + carta.getRareza() + "\n");
        areaDetallesCarta.append("Ataque: " + carta.getAtaque() + "\n");
        areaDetallesCarta.append("Defensa: " + carta.getDefensa() + "\n");
        areaDetallesCarta.append("PS: " + carta.getPs() + "\n");
        areaDetallesCarta.append("Imagen: " + carta.getImagen() + "\n");
    }

    private void agregarCartaDesdeFormulario() {
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>();
        JComboBox<String> comboRareza = new JComboBox<>();
        JTextField txtAtaque = new JTextField();
        JTextField txtDefensa = new JTextField();
        JTextField txtPs = new JTextField();
        JTextField txtImagen = new JTextField();

        comboTipo.addItem("Fuego");
        comboTipo.addItem("Agua");
        comboTipo.addItem("Planta");
        comboTipo.addItem("Eléctrico");
        comboTipo.addItem("Psíquico");
        comboTipo.addItem("Normal");
        comboTipo.addItem("Oscuro");
        comboTipo.addItem("Acero");

        comboRareza.addItem("Común");
        comboRareza.addItem("Poco Común");
        comboRareza.addItem("Rara");
        comboRareza.addItem("Ultra Rara");
        comboRareza.addItem("Legendaria");

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 8, 8));
        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(comboTipo);

        panelFormulario.add(new JLabel("Rareza:"));
        panelFormulario.add(comboRareza);

        panelFormulario.add(new JLabel("Ataque:"));
        panelFormulario.add(txtAtaque);

        panelFormulario.add(new JLabel("Defensa:"));
        panelFormulario.add(txtDefensa);

        panelFormulario.add(new JLabel("PS:"));
        panelFormulario.add(txtPs);

        panelFormulario.add(new JLabel("Imagen (ruta):"));
        panelFormulario.add(txtImagen);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panelFormulario,
                "Agregar carta",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Carta carta = new Carta(
                    txtCodigo.getText().trim(),
                    txtNombre.getText().trim(),
                    comboTipo.getSelectedItem().toString(),
                    comboRareza.getSelectedItem().toString(),
                    Integer.parseInt(txtAtaque.getText().trim()),
                    Integer.parseInt(txtDefensa.getText().trim()),
                    Integer.parseInt(txtPs.getText().trim()),
                    txtImagen.getText().trim()
            );

            String resultado = albumService.agregarCarta(carta);

            if (resultado == null) {
                StringBuilder resumenGamificacion = new StringBuilder();

                String resumenCarta = gamificacionService.registrarCartaAgregada(carta.esLegendaria());
                resumenGamificacion.append(resumenCarta);

                NodoMatriz nodoInsertado = albumService.buscarNodoPorCodigo(carta.getCodigo());

                if (nodoInsertado != null && albumService.filaEstaCompleta(nodoInsertado.getFila())) {
                    String resumenFila = gamificacionService.registrarFilaCompletaAlbum();
                    resumenGamificacion.append("\n\n").append(resumenFila);
                }

                archivoService.guardarAlbum(albumService.getAlbum());
                guardarGamificacion();
                refrescarVista();

                JOptionPane.showMessageDialog(
                        this,
                        "Carta agregada correctamente.\n\n" + resumenGamificacion.toString()
                );
            } else {
                JOptionPane.showMessageDialog(this, resultado);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ataque, defensa y PS deben ser números enteros válidos.");
        }
    }

    private void intercambiarCartasSeleccionadas() {
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero seleccione una celda del álbum.");
            return;
        }

        if (primeraSeleccionIntercambio == null) {
            primeraSeleccionIntercambio = nodoSeleccionado;
            lblEstadoIntercambio.setText(
                    "Intercambio: origen [" 
                    + primeraSeleccionIntercambio.getFila() 
                    + "," 
                    + primeraSeleccionIntercambio.getColumna() 
                    + "]"
            );
            refrescarVista();
            JOptionPane.showMessageDialog(this,
                    "Primera celda marcada. Ahora seleccione otra celda y presione de nuevo el botón.");
            return;
        }

        if (primeraSeleccionIntercambio == nodoSeleccionado) {
            JOptionPane.showMessageDialog(this, "Seleccione una segunda celda diferente.");
            return;
        }

        String resultado = albumService.intercambiarCartas(
                primeraSeleccionIntercambio.getFila(),
                primeraSeleccionIntercambio.getColumna(),
                nodoSeleccionado.getFila(),
                nodoSeleccionado.getColumna()
        );

        if (resultado == null) {
            archivoService.guardarAlbum(albumService.getAlbum());
            JOptionPane.showMessageDialog(this, "Cartas intercambiadas correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, resultado);
        }

        primeraSeleccionIntercambio = null;
        lblEstadoIntercambio.setText("Intercambio: sin iniciar");
        mostrarDetallesNodo(nodoSeleccionado);
        refrescarVista();
    }

    private Color obtenerColorPorTipo(String tipo) {
        if (tipo == null) {
            return Color.WHITE;
        }

        if (tipo.equalsIgnoreCase("Fuego")) {
            return new Color(255, 204, 153);
        }

        if (tipo.equalsIgnoreCase("Agua")) {
            return new Color(173, 216, 230);
        }

        if (tipo.equalsIgnoreCase("Planta")) {
            return new Color(181, 230, 162);
        }

        if (tipo.equalsIgnoreCase("Eléctrico")) {
            return new Color(255, 255, 153);
        }

        if (tipo.equalsIgnoreCase("Psíquico")) {
            return new Color(230, 190, 255);
        }

        if (tipo.equalsIgnoreCase("Normal")) {
            return new Color(224, 224, 224);
        }

        if (tipo.equalsIgnoreCase("Oscuro")) {
            return new Color(180, 180, 180);
        }

        if (tipo.equalsIgnoreCase("Acero")) {
            return new Color(200, 200, 220);
        }

        return Color.WHITE;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
}