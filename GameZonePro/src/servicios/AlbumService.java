/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */

import estructuras.Album;
import estructuras.NodoMatriz;
import modelo.Carta;

public class AlbumService {
    
    private Album album;

    public AlbumService() {
        album = new Album(4, 6);
    }

    public AlbumService(int filas, int columnas) {
        album = new Album(filas, columnas);
    }

    public Album getAlbum() {
        return album;
    }

    public void crearNuevoAlbum(int filas, int columnas) {
        album = new Album(filas, columnas);
    }

    public String agregarCarta(Carta carta) {
        String validacion = validarCarta(carta);

        if (validacion != null) {
            return validacion;
        }

        if (album.estaLleno()) {
            return "El álbum ya está lleno.";
        }

        if (album.existeCarta(carta.getCodigo())) {
            return "Ya existe una carta con ese código en el álbum.";
        }

        boolean agregada = album.agregarCarta(carta);

        if (!agregada) {
            return "No se pudo agregar la carta al álbum.";
        }

        return null;
    }

    public String validarCarta(Carta carta) {
        if (carta == null) {
            return "La carta no puede ser nula.";
        }

        if (estaVacio(carta.getCodigo())) {
            return "El código de la carta es obligatorio.";
        }

        if (estaVacio(carta.getNombre())) {
            return "El nombre de la carta es obligatorio.";
        }

        if (estaVacio(carta.getTipo())) {
            return "El tipo de la carta es obligatorio.";
        }

        if (estaVacio(carta.getRareza())) {
            return "La rareza de la carta es obligatoria.";
        }

        if (!esTipoValido(carta.getTipo())) {
            return "El tipo de la carta no es válido.";
        }

        if (!esRarezaValida(carta.getRareza())) {
            return "La rareza de la carta no es válida.";
        }

        if (carta.getAtaque() <= 0) {
            return "El ataque debe ser mayor que 0.";
        }

        if (carta.getDefensa() <= 0) {
            return "La defensa debe ser mayor que 0.";
        }

        if (carta.getPs() <= 0) {
            return "Los PS deben ser mayores que 0.";
        }

        return null;
    }

    public NodoMatriz buscarNodoPorCodigo(String codigo) {
        if (estaVacio(codigo)) {
            return null;
        }

        NodoMatriz filaActual = album.getCabeza();

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                Carta carta = columnaActual.getDato();

                if (carta != null && carta.getCodigo().equalsIgnoreCase(codigo.trim())) {
                    return columnaActual;
                }

                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return null;
    }

    public NodoMatriz obtenerNodo(int fila, int columna) {
        return album.obtenerNodo(fila, columna);
    }

    public String intercambiarCartas(int fila1, int columna1, int fila2, int columna2) {
        if (!esPosicionValida(fila1, columna1)) {
            return "La primera posición no es válida.";
        }

        if (!esPosicionValida(fila2, columna2)) {
            return "La segunda posición no es válida.";
        }

        if (fila1 == fila2 && columna1 == columna2) {
            return "Debe seleccionar dos posiciones diferentes.";
        }

        boolean intercambiadas = album.intercambiarCartas(fila1, columna1, fila2, columna2);

        if (!intercambiadas) {
            return "No se pudieron intercambiar las cartas.";
        }

        return null;
    }

    public boolean cartaCoincideConBusqueda(Carta carta, String criterio) {
        if (carta == null || estaVacio(criterio)) {
            return false;
        }

        String texto = normalizarTexto(criterio);

        return normalizarTexto(carta.getNombre()).contains(texto)
                || normalizarTexto(carta.getTipo()).contains(texto)
                || normalizarTexto(carta.getRareza()).contains(texto);
    }

    public boolean hayCoincidencias(String criterio) {
        if (estaVacio(criterio)) {
            return false;
        }

        NodoMatriz filaActual = album.getCabeza();

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                if (cartaCoincideConBusqueda(columnaActual.getDato(), criterio)) {
                    return true;
                }

                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return false;
    }

    public int contarCartas() {
        return album.contarCartas();
    }

    public boolean albumEstaLleno() {
        return album.estaLleno();
    }

    public void vaciarAlbum() {
        album.vaciarAlbum();
    }

    public boolean filaEstaCompleta(int fila) {
        if (fila < 0 || fila >= album.getFilas()) {
            return false;
        }

        NodoMatriz inicioFila = album.obtenerNodo(fila, 0);

        while (inicioFila != null) {
            if (inicioFila.estaVacia()) {
                return false;
            }

            inicioFila = inicioFila.getDerecha();
        }

        return true;
    }

    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < album.getFilas()
                && columna >= 0 && columna < album.getColumnas();
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.trim().toLowerCase();
    }

    private boolean esTipoValido(String tipo) {
        String valor = normalizarTexto(tipo);

        return valor.equals("fuego")
                || valor.equals("agua")
                || valor.equals("planta")
                || valor.equals("eléctrico")
                || valor.equals("electrico")
                || valor.equals("psíquico")
                || valor.equals("psiquico")
                || valor.equals("normal")
                || valor.equals("oscuro")
                || valor.equals("acero");
    }

    private boolean esRarezaValida(String rareza) {
        String valor = normalizarTexto(rareza);

        return valor.equals("común")
                || valor.equals("comun")
                || valor.equals("poco común")
                || valor.equals("poco comun")
                || valor.equals("rara")
                || valor.equals("ultra rara")
                || valor.equals("legendaria");
    }
    
    public void setAlbum(Album album) {
        if (album != null) {
            this.album = album;
        }
    }
    
}
