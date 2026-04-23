/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class ItemCarrito {
    
    private Juego juego;
    private int cantidad;

    public ItemCarrito(Juego juego, int cantidad) {
        this.juego = juego;
        this.cantidad = cantidad;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return juego.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return juego.getNombre() + " | Cantidad: " + cantidad + " | Subtotal: Q" + getSubtotal();
    }
    
}
