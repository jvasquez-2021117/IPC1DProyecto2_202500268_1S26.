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
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import estructuras.ListaSimple;
import estructuras.NodoSimple;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import servicios.ArchivoService;
import servicios.TiendaService;
import modelo.Juego;
import modelo.ItemCarrito;
import modelo.Compra;
import modelo.DetalleCompra;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JComboBox;
import java.awt.Cursor;
import servicios.GamificacionService;

public class PanelTienda extends JPanel {
    
    private JButton btnVolver;
    private JPanel panelCatalogo;
    private JPanel panelCarrito;
    private JTextArea areaCarrito;
    private TiendaService tiendaService;
    private ListaSimple catalogo;
    private JButton btnVaciarCarrito;
    private JButton btnConfirmarCompra;
    private JTextArea areaHistorial;
    private ArchivoService archivoService;
    private JButton btnEliminarItem;
    private JButton btnEditarCantidad;
    private JTextField txtBuscar;
    private JComboBox<String> comboGenero;
    private JComboBox<String> comboPlataforma;
    private JTextArea areaDetallesJuego;
    private JButton btnAgregarDetalle;
    private Juego juegoSeleccionado;
    private GamificacionService gamificacionService;
    
    public PanelTienda() {
        this(new GamificacionService("Jugador Actual"));
    }

    public PanelTienda(GamificacionService gamificacionService) {
        this.tiendaService = new TiendaService();
        this.archivoService = new ArchivoService();
        this.gamificacionService = gamificacionService;

        inicializarComponentes();
        cargarCatalogo();
        cargarHistorial();
        mostrarCatalogo();
        actualizarHistorial();
        configurarEventos();
    }
    
    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("MÓDULO TIENDA", JLabel.CENTER);

        // PANEL CATÁLOGO
        JPanel contenedorCatalogo = new JPanel(new BorderLayout());
        contenedorCatalogo.setBorder(BorderFactory.createTitledBorder("Catálogo de Juegos"));

        JPanel panelBusqueda = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel lblBuscar = new JLabel("Buscar por nombre o código:");
        txtBuscar = new JTextField();

        JLabel lblGenero = new JLabel("Filtrar por género:");
        comboGenero = new JComboBox<>();
        comboGenero.addItem("Todos");
        comboGenero.addItem("Accion");
        comboGenero.addItem("RPG");
        comboGenero.addItem("Estrategia");
        comboGenero.addItem("Deportes");
        comboGenero.addItem("Terror");
        comboGenero.addItem("Aventura");

        JLabel lblPlataforma = new JLabel("Filtrar por plataforma:");
        comboPlataforma = new JComboBox<>();
        comboPlataforma.addItem("Todas");
        comboPlataforma.addItem("PC");
        comboPlataforma.addItem("PlayStation");
        comboPlataforma.addItem("Xbox");
        comboPlataforma.addItem("Nintendo Switch");

        JPanel panelFiltros = new JPanel(new GridLayout(1, 4, 10, 10));
        panelFiltros.add(lblGenero);
        panelFiltros.add(comboGenero);
        panelFiltros.add(lblPlataforma);
        panelFiltros.add(comboPlataforma);

        JPanel contenedorBusqueda = new JPanel(new BorderLayout(5, 5));
        contenedorBusqueda.add(lblBuscar, BorderLayout.WEST);
        contenedorBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JPanel panelSuperiorCatalogo = new JPanel(new BorderLayout(5, 5));
        panelSuperiorCatalogo.add(contenedorBusqueda, BorderLayout.NORTH);
        panelSuperiorCatalogo.add(panelFiltros, BorderLayout.SOUTH);

        panelCatalogo = new JPanel();
        panelCatalogo.setBackground(Color.LIGHT_GRAY);
        panelCatalogo.setLayout(new BoxLayout(panelCatalogo, BoxLayout.Y_AXIS));

        JScrollPane scrollCatalogo = new JScrollPane(panelCatalogo);

        areaDetallesJuego = new JTextArea();
        areaDetallesJuego.setEditable(false);
        areaDetallesJuego.setText("Seleccione un juego para ver sus detalles.");

        JScrollPane scrollDetalles = new JScrollPane(areaDetallesJuego);

        btnAgregarDetalle = new JButton("Agregar juego seleccionado al carrito");
        btnAgregarDetalle.setEnabled(false);

        JPanel panelDetalles = new JPanel(new BorderLayout(5, 5));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Juego"));
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        panelDetalles.add(btnAgregarDetalle, BorderLayout.SOUTH);

        JSplitPane splitCatalogo = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollCatalogo,
                panelDetalles
        );

        splitCatalogo.setResizeWeight(0.7);
        splitCatalogo.setDividerLocation(350);
        splitCatalogo.setBorder(null);

        contenedorCatalogo.add(panelSuperiorCatalogo, BorderLayout.NORTH);
        contenedorCatalogo.add(splitCatalogo, BorderLayout.CENTER);

        // PANEL DERECHO: CARRITO + HISTORIAL
        panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBorder(BorderFactory.createTitledBorder("Carrito / Historial"));
        panelCarrito.setBackground(Color.WHITE);

        areaCarrito = new JTextArea();
        areaCarrito.setEditable(false);

        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);

        JScrollPane scrollCarrito = new JScrollPane(areaCarrito);
        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);

        JSplitPane splitDerecho = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollCarrito,
                scrollHistorial
        );

        splitDerecho.setResizeWeight(0.5);
        splitDerecho.setDividerLocation(220);
        splitDerecho.setBorder(null);

        // BOTONES DEL CARRITO
        btnVaciarCarrito = new JButton("Vaciar carrito");
        btnConfirmarCompra = new JButton("Confirmar compra");
        btnEliminarItem = new JButton("Eliminar item");
        btnEditarCantidad = new JButton("Editar cantidad");

        JPanel panelBotonesCarrito = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotonesCarrito.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        panelBotonesCarrito.add(btnVaciarCarrito);
        panelBotonesCarrito.add(btnConfirmarCompra);
        panelBotonesCarrito.add(btnEditarCantidad);
        panelBotonesCarrito.add(btnEliminarItem);

        panelCarrito.add(splitDerecho, BorderLayout.CENTER);
        panelCarrito.add(panelBotonesCarrito, BorderLayout.SOUTH);

        // SPLIT PRINCIPAL 70/30
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            contenedorCatalogo,
            panelCarrito
        );

        splitPane.setResizeWeight(0.7);
        splitPane.setDividerLocation(700);
        splitPane.setBorder(null);

        // BOTÓN VOLVER
        btnVolver = new JButton("Volver al menú");

        add(titulo, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }
    
    private int contarCantidadJuegosEnCarrito() {
        int cantidadTotal = 0;
        NodoSimple actual = tiendaService.getCarrito().getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();
            cantidadTotal += item.getCantidad();
            actual = actual.getSiguiente();
        }

        return cantidadTotal;
    }
    
    private void guardarGamificacion() {
        archivoService.guardarLeaderboard(
                gamificacionService.getLeaderboardOrdenadoParaGuardar(),
                gamificacionService.getCantidadLeaderboard()
        );
    }
    
    private void cargarCatalogo() {
        ArchivoService archivoService = new ArchivoService();
        catalogo = archivoService.cargarCatalogoDesdeArchivo();
    }
    
    private void mostrarCatalogo(String filtroTexto, String filtroGenero, String filtroPlataforma) {
        panelCatalogo.removeAll();

        NodoSimple actual = catalogo.getCabeza();

        while (actual != null) {
            Juego juego = (Juego) actual.getDato();

            boolean coincideTexto = filtroTexto == null || filtroTexto.isEmpty()
                    || juego.getNombre().toLowerCase().contains(filtroTexto.toLowerCase())
                    || juego.getCodigo().toLowerCase().contains(filtroTexto.toLowerCase());

            boolean coincideGenero = filtroGenero == null
                    || filtroGenero.equals("Todos")
                    || juego.getGenero().equalsIgnoreCase(filtroGenero);

            boolean coincidePlataforma = filtroPlataforma == null
                    || filtroPlataforma.equals("Todas")
                    || juego.getPlataforma().equalsIgnoreCase(filtroPlataforma);

            if (coincideTexto && coincideGenero && coincidePlataforma) {
                JPanel tarjeta = new JPanel();
                tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
                tarjeta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                tarjeta.setBackground(Color.WHITE);

                JLabel lblNombre = new JLabel("Nombre: " + juego.getNombre());
                JLabel lblGenero = new JLabel("Género: " + juego.getGenero());
                JLabel lblPlataforma = new JLabel("Plataforma: " + juego.getPlataforma());
                JLabel lblPrecio = new JLabel("Precio: Q" + juego.getPrecio());
                JLabel lblStock = new JLabel("Stock: " + juego.getStock());

                tarjeta.add(lblNombre);
                tarjeta.add(lblGenero);
                tarjeta.add(lblPlataforma);
                tarjeta.add(lblPrecio);
                tarjeta.add(lblStock);

                panelCatalogo.add(tarjeta);
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                        mostrarDetallesJuego(juego);
                    }
                });
            }

            actual = actual.getSiguiente();
        }

        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }
    
    private void mostrarCatalogo() {
        mostrarCatalogo("", "Todos", "Todas");
    }
    
    private void actualizarAreaCarrito() {
        areaCarrito.setText("");

        NodoSimple actual = tiendaService.getCarrito().getCabeza();

        if (actual == null) {
            areaCarrito.setText("El carrito está vacío.\n");
            return;
        }

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            areaCarrito.append("Código: " + item.getJuego().getCodigo() + "\n");
            areaCarrito.append("Nombre: " + item.getJuego().getNombre() + "\n");
            areaCarrito.append("Cantidad: " + item.getCantidad() + "\n");
            areaCarrito.append("Subtotal: Q" + item.getSubtotal() + "\n");
            areaCarrito.append("-----------------------------\n");

            actual = actual.getSiguiente();
        }

        areaCarrito.append("TOTAL: Q" + tiendaService.calcularTotalCarrito());
    }
    
    private void configurarEventos() {
        btnVaciarCarrito.addActionListener(e -> {
            tiendaService.vaciarCarrito();
            actualizarAreaCarrito();
        });

        btnConfirmarCompra.addActionListener(e -> {
            confirmarCompra();
        });
        
        btnEliminarItem.addActionListener(e -> {
            eliminarItemDelCarrito();
        });
        
        btnEditarCantidad.addActionListener(e -> {
            editarCantidadItemCarrito();
        });
        
         txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarCatalogo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarCatalogo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarCatalogo();
            }
        });
         
        comboGenero.addActionListener(e -> {
            filtrarCatalogo();
        });

        comboPlataforma.addActionListener(e -> {
            filtrarCatalogo();
        });
        
        btnAgregarDetalle.addActionListener(e -> {
            if (juegoSeleccionado != null) {
                tiendaService.agregarAlCarrito(juegoSeleccionado);
                actualizarAreaCarrito();
                JOptionPane.showMessageDialog(this,
                        "Juego agregado al carrito: " + juegoSeleccionado.getNombre());
            }
        });
    }
    
    private void filtrarCatalogo() {
        String texto = txtBuscar.getText().trim();
        String genero = comboGenero.getSelectedItem().toString();
        String plataforma = comboPlataforma.getSelectedItem().toString();

        mostrarCatalogo(texto, genero, plataforma);
    }
    
    private void confirmarCompra() {
        
        if (tiendaService.getCarrito().estaVacia()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        NodoSimple actual = tiendaService.getCarrito().getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            if (item.getCantidad() > item.getJuego().getStock()) {
                JOptionPane.showMessageDialog(this,
                        "Stock insuficiente para: " + item.getJuego().getNombre());
                return;
            }

            actual = actual.getSiguiente();
        }

        actual = tiendaService.getCarrito().getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();
            Juego juego = item.getJuego();

            juego.setStock(juego.getStock() - item.getCantidad());

            actual = actual.getSiguiente();
        }

        double totalCompra = tiendaService.calcularTotalCarrito();
        int cantidadJuegosComprados = contarCantidadJuegosEnCarrito();

        String resumenGamificacion = gamificacionService.registrarCompraJuego(
                cantidadJuegosComprados,
                totalCompra
        );
        guardarGamificacion();

        tiendaService.registrarCompra();
        archivoService.guardarHistorialCompras(tiendaService.getHistorialCompras());
        archivoService.guardarCatalogo(catalogo);

        JOptionPane.showMessageDialog(
                this,
                "Compra realizada con éxito. Total: Q" + totalCompra + "\n\n" + resumenGamificacion
        );

        actualizarHistorial();
        tiendaService.vaciarCarrito();
        actualizarAreaCarrito();
        refrescarCatalogo();
    }
    
    private void refrescarCatalogo() {
        filtrarCatalogo();
        juegoSeleccionado = null;
        areaDetallesJuego.setText("Seleccione un juego para ver sus detalles.");
        btnAgregarDetalle.setEnabled(false);
        panelCatalogo.revalidate();
        panelCatalogo.repaint();
    }
    
    private void actualizarHistorial() {
        areaHistorial.setText("");

        NodoSimple actual = tiendaService.getHistorialCompras().getCabeza();

        while (actual != null) {
            Compra compra = (Compra) actual.getDato();
            areaHistorial.append(compra.toString() + "\n");

            NodoSimple detalleActual = compra.getDetalles().getCabeza();
            while (detalleActual != null) {
                DetalleCompra detalle = (DetalleCompra) detalleActual.getDato();
                areaHistorial.append("   - " + detalle.toString() + "\n");
                detalleActual = detalleActual.getSiguiente();
            }

            areaHistorial.append("\n");
            actual = actual.getSiguiente();
        }
    }
    
    private void eliminarItemDelCarrito() {
        if (tiendaService.getCarrito().estaVacia()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        String codigo = JOptionPane.showInputDialog(this,
                "Ingrese el código del juego a eliminar:");

        if (codigo == null) {
            return;
        }

        codigo = codigo.trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código válido.");
            return;
        }

        boolean eliminado = tiendaService.eliminarDelCarrito(codigo);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Item eliminado del carrito.");
            actualizarAreaCarrito();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un item con ese código en el carrito.");
        }
    }
    
    private void cargarHistorial() {
        ListaSimple historial = archivoService.cargarHistorialCompras();
        tiendaService.setHistorialCompras(historial);
    }
    
    private void editarCantidadItemCarrito() {
        if (tiendaService.getCarrito().estaVacia()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        String codigo = JOptionPane.showInputDialog(this,
                "Ingrese el código del juego:");

        if (codigo == null) {
            return;
        }

        codigo = codigo.trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código válido.");
            return;
        }

        String cantidadTexto = JOptionPane.showInputDialog(this,
                "Ingrese la nueva cantidad:");

        if (cantidadTexto == null) {
            return;
        }

        cantidadTexto = cantidadTexto.trim();

        if (cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad válida.");
            return;
        }

        try {
            int nuevaCantidad = Integer.parseInt(cantidadTexto);

            int resultado = tiendaService.editarCantidadItem(codigo, nuevaCantidad);

            switch (resultado) {
                case -1:
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.");
                    break;
                case 0:
                    JOptionPane.showMessageDialog(this, "La cantidad excede el stock disponible.");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(this, "Cantidad actualizada correctamente.");
                    actualizarAreaCarrito();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(this, "No se encontró un item con ese código.");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.");
                    break;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.");
        }
    }
    
    private void mostrarDetallesJuego(Juego juego) {
        juegoSeleccionado = juego;

        areaDetallesJuego.setText("");
        areaDetallesJuego.append("Código: " + juego.getCodigo() + "\n");
        areaDetallesJuego.append("Nombre: " + juego.getNombre() + "\n");
        areaDetallesJuego.append("Género: " + juego.getGenero() + "\n");
        areaDetallesJuego.append("Plataforma: " + juego.getPlataforma() + "\n");
        areaDetallesJuego.append("Precio: Q" + juego.getPrecio() + "\n");
        areaDetallesJuego.append("Stock: " + juego.getStock() + "\n");
        areaDetallesJuego.append("Descripción: " + juego.getDescripcion() + "\n");

        btnAgregarDetalle.setEnabled(true);
    }
    
    

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
    public JPanel getPanelCatalogo() {
        return panelCatalogo;
    }

    public JTextArea getAreaCarrito() {
        return areaCarrito;
    }
    
}
