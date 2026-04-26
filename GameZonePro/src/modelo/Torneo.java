/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class Torneo {
    
    private String idTorneo;
    private String nombre;
    private String juego;
    private String fecha;
    private String hora;
    private double precioTicket;
    private int ticketsDisponibles;

    public Torneo(String idTorneo, String nombre, String juego, String fecha,
                  String hora, double precioTicket, int ticketsDisponibles) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.juego = juego;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTicket = precioTicket;
        this.ticketsDisponibles = ticketsDisponibles;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getPrecioTicket() {
        return precioTicket;
    }

    public void setPrecioTicket(double precioTicket) {
        this.precioTicket = precioTicket;
    }

    public int getTicketsDisponibles() {
        return ticketsDisponibles;
    }

    public void setTicketsDisponibles(int ticketsDisponibles) {
        this.ticketsDisponibles = ticketsDisponibles;
    }

    public boolean hayTickets() {
        return ticketsDisponibles > 0;
    }

    public void restarTicket() {
        if (ticketsDisponibles > 0) {
            ticketsDisponibles--;
        }
    }

    @Override
    public String toString() {
        return nombre + " | " + juego + " | " + fecha + " " + hora
                + " | Q" + precioTicket + " | Tickets: " + ticketsDisponibles;
    }
    
}
