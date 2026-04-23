/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class DetalleCompra {
 
    private String codigoJuego;
    private String nombreJuego;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleCompra(String codigoJuego, String nombreJuego, int cantidad, double precioUnitario) {
        this.codigoJuego = codigoJuego;
        this.nombreJuego = nombreJuego;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public String getCodigoJuego() {
        return codigoJuego;
    }

    public void setCodigoJuego(String codigoJuego) {
        this.codigoJuego = codigoJuego;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return nombreJuego + " | Cantidad: " + cantidad + " | Subtotal: Q" + subtotal;
    }
    
}
