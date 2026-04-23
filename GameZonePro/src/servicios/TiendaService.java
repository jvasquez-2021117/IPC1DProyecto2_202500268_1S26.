/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelo.ItemCarrito;
import modelo.Juego;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.Compra;
import modelo.DetalleCompra;

public class TiendaService {
    
    private ListaSimple carrito;
    private ListaSimple historialCompras;

    public TiendaService() {
        carrito = new ListaSimple();
        historialCompras = new ListaSimple();
    }

    public ListaSimple getCarrito() {
        return carrito;
    }
    
    public ListaSimple getHistorialCompras() {
        return historialCompras;
    }

    public void agregarAlCarrito(Juego juego) {
        NodoSimple actual = carrito.getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            if (item.getJuego().getCodigo().equals(juego.getCodigo())) {
                item.setCantidad(item.getCantidad() + 1);
                return;
            }

            actual = actual.getSiguiente();
        }

        ItemCarrito nuevoItem = new ItemCarrito(juego, 1);
        carrito.insertarAlFinal(nuevoItem);
    }

    public void mostrarCarrito() {
        NodoSimple actual = carrito.getCabeza();

        if (carrito.estaVacia()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();
            System.out.println(item);
            actual = actual.getSiguiente();
        }
    }
    
    public double calcularTotalCarrito() {
        double total = 0.0;
        NodoSimple actual = carrito.getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();
            total += item.getSubtotal();
            actual = actual.getSiguiente();
        }

        return total;
    }
    
    public void vaciarCarrito() {
        carrito = new ListaSimple();
    }
    
    public boolean eliminarDelCarrito(String codigoJuego) {
        if (carrito.estaVacia()) {
            return false;
        }

        NodoSimple actual = carrito.getCabeza();
        NodoSimple anterior = null;

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            if (item.getJuego().getCodigo().equals(codigoJuego)) {

                if (anterior == null) {
                    carrito.setCabeza(actual.getSiguiente());
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }
    
        public void registrarCompra() {
        if (carrito.estaVacia()) {
            return;
        }

        ListaSimple detallesCompra = new ListaSimple();
        NodoSimple actual = carrito.getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            DetalleCompra detalle = new DetalleCompra(
                    item.getJuego().getCodigo(),
                    item.getJuego().getNombre(),
                    item.getCantidad(),
                    item.getJuego().getPrecio()
            );

            detallesCompra.insertarAlFinal(detalle);
            actual = actual.getSiguiente();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);

        Compra compra = new Compra(fechaHora, detallesCompra, calcularTotalCarrito());

        historialCompras.insertarAlInicio(compra);
    }
        
    public int editarCantidadItem(String codigoJuego, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            return -1;
        }

        NodoSimple actual = carrito.getCabeza();

        while (actual != null) {
            ItemCarrito item = (ItemCarrito) actual.getDato();

            if (item.getJuego().getCodigo().equals(codigoJuego)) {

                if (nuevaCantidad > item.getJuego().getStock()) {
                    return 0;
                }

                item.setCantidad(nuevaCantidad);
                return 1;
            }

            actual = actual.getSiguiente();
        }

        return 2;
    }
        
    public void setHistorialCompras(ListaSimple historialCompras) {
        this.historialCompras = historialCompras;
    }
    
}
