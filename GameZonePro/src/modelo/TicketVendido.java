/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class TicketVendido {
    
    private String nombreUsuario;
    private String idTorneo;
    private String nombreTorneo;
    private String juego;
    private double precio;
    private String taquilla;
    private String fechaHoraVenta;

    public TicketVendido(String nombreUsuario, String idTorneo, String nombreTorneo,
                         String juego, double precio, String taquilla, String fechaHoraVenta) {
        this.nombreUsuario = nombreUsuario;
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.juego = juego;
        this.precio = precio;
        this.taquilla = taquilla;
        this.fechaHoraVenta = fechaHoraVenta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(String taquilla) {
        this.taquilla = taquilla;
    }

    public String getFechaHoraVenta() {
        return fechaHoraVenta;
    }

    public void setFechaHoraVenta(String fechaHoraVenta) {
        this.fechaHoraVenta = fechaHoraVenta;
    }

    @Override
    public String toString() {
        return fechaHoraVenta + " | " + taquilla + " | " + nombreUsuario
                + " | " + nombreTorneo + " | Q" + precio;
    }
    
}
