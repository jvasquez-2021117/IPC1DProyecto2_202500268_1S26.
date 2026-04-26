/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */

import estructuras.Cola;
import estructuras.ListaSimple;
import estructuras.NodoCola;
import estructuras.NodoSimple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.SolicitudTicket;
import modelo.TicketVendido;
import modelo.Torneo;

public class TorneoService {
    
    private static final String RUTA_TORNEOS = "src/recursos/torneos.txt";

    private ListaSimple torneos;
    private Cola colaEspera;
    private ListaSimple ticketsVendidos;
    private Torneo torneoSeleccionado;

    public TorneoService() {
        torneos = new ListaSimple();
        colaEspera = new Cola();
        ticketsVendidos = new ListaSimple();
        torneoSeleccionado = null;

        cargarTorneos();
    }

    public ListaSimple getTorneos() {
        return torneos;
    }

    public Cola getColaEspera() {
        return colaEspera;
    }

    public ListaSimple getTicketsVendidos() {
        return ticketsVendidos;
    }

    public Torneo getTorneoSeleccionado() {
        return torneoSeleccionado;
    }

    public void setTorneoSeleccionado(Torneo torneoSeleccionado) {
        this.torneoSeleccionado = torneoSeleccionado;
    }

    public void cargarTorneos() {
        torneos = new ListaSimple();

        File archivo = new File(RUTA_TORNEOS);

        if (!archivo.exists()) {
            return;
        }

        BufferedReader lector = null;

        try {
            lector = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|");

                if (partes.length < 7) {
                    continue;
                }

                String idTorneo = partes[0];
                String nombre = partes[1];
                String juego = partes[2];
                String fecha = partes[3];
                String hora = partes[4];
                double precioTicket = Double.parseDouble(partes[5]);
                int ticketsDisponibles = Integer.parseInt(partes[6]);

                Torneo torneo = new Torneo(
                        idTorneo,
                        nombre,
                        juego,
                        fecha,
                        hora,
                        precioTicket,
                        ticketsDisponibles
                );

                insertarTorneo(torneo);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar torneos: " + e.getMessage());
        } finally {
            try {
                if (lector != null) {
                    lector.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public Torneo buscarTorneoPorId(String idTorneo) {
        if (idTorneo == null || idTorneo.trim().isEmpty()) {
            return null;
        }

        NodoSimple actual = torneos.getCabeza();

        while (actual != null) {
            Torneo torneo = (Torneo) actual.getDato();

            if (torneo.getIdTorneo().equalsIgnoreCase(idTorneo.trim())) {
                return torneo;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public String seleccionarTorneo(String idTorneo) {
        Torneo torneo = buscarTorneoPorId(idTorneo);

        if (torneo == null) {
            return "No se encontró un torneo con ese ID.";
        }

        torneoSeleccionado = torneo;
        return null;
    }

    public String inscribirUsuario(String nombreUsuario) {
        if (torneoSeleccionado == null) {
            return "Debe seleccionar un torneo primero.";
        }

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return "Debe ingresar un nombre válido.";
        }

        if (!torneoSeleccionado.hayTickets()) {
            return "Ese torneo ya no tiene tickets disponibles.";
        }

        SolicitudTicket solicitud = new SolicitudTicket(
                nombreUsuario.trim(),
                torneoSeleccionado.getIdTorneo()
        );

        colaEspera.encolar(solicitud);
        return null;
    }

    public boolean haySolicitudesPendientes() {
        return !colaEspera.estaVacia();
    }

    public String obtenerTextoCola() {
        if (colaEspera.estaVacia()) {
            return "Cola vacía";
        }

        StringBuilder sb = new StringBuilder();
        NodoCola actual = colaEspera.getFrente();

        while (actual != null) {
            SolicitudTicket solicitud = (SolicitudTicket) actual.getDato();
            sb.append(solicitud.getNombreUsuario())
              .append(" -> ")
              .append(solicitud.getIdTorneo());

            if (actual.getSiguiente() != null) {
                sb.append("\n");
            }

            actual = actual.getSiguiente();
        }

        return sb.toString();
    }

    public String obtenerResumenTorneoSeleccionado() {
        if (torneoSeleccionado == null) {
            return "No hay torneo seleccionado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(torneoSeleccionado.getIdTorneo()).append("\n");
        sb.append("Nombre: ").append(torneoSeleccionado.getNombre()).append("\n");
        sb.append("Juego: ").append(torneoSeleccionado.getJuego()).append("\n");
        sb.append("Fecha: ").append(torneoSeleccionado.getFecha()).append("\n");
        sb.append("Hora: ").append(torneoSeleccionado.getHora()).append("\n");
        sb.append("Precio ticket: Q").append(torneoSeleccionado.getPrecioTicket()).append("\n");
        sb.append("Tickets disponibles: ").append(torneoSeleccionado.getTicketsDisponibles());

        return sb.toString();
    }

    public synchronized TicketVendido tomarTicketParaProcesamiento(String nombreTaquilla) {
        if (colaEspera.estaVacia()) {
            return null;
        }

        SolicitudTicket solicitud = (SolicitudTicket) colaEspera.desencolar();

        if (solicitud == null) {
            return null;
        }

        Torneo torneo = buscarTorneoPorId(solicitud.getIdTorneo());

        if (torneo == null) {
            return null;
        }

        if (!torneo.hayTickets()) {
            return null;
        }

        torneo.restarTicket();

        TicketVendido ticket = new TicketVendido(
                solicitud.getNombreUsuario(),
                torneo.getIdTorneo(),
                torneo.getNombre(),
                torneo.getJuego(),
                torneo.getPrecioTicket(),
                nombreTaquilla,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );

        insertarTicketVendido(ticket);

        return ticket;
    }

    public String obtenerTextoTicketsVendidos() {
        if (ticketsVendidos.estaVacia()) {
            return "No hay tickets vendidos todavía.";
        }

        StringBuilder sb = new StringBuilder();
        NodoSimple actual = ticketsVendidos.getCabeza();

        while (actual != null) {
            TicketVendido ticket = (TicketVendido) actual.getDato();
            sb.append(ticket.toString()).append("\n");
            actual = actual.getSiguiente();
        }

        return sb.toString();
    }

    public int contarTorneos() {
        int contador = 0;
        NodoSimple actual = torneos.getCabeza();

        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }

        return contador;
    }

    private void insertarTorneo(Torneo torneo) {
        /*
         * Si en tu ListaSimple el método no se llama insertarAlFinal,
         * aquí solo cambias este nombre por el que tú ya tengas.
         */
        torneos.insertarAlFinal(torneo);
    }

    private void insertarTicketVendido(TicketVendido ticket) {
        ticketsVendidos.insertarAlInicio(ticket);
    }
    
    public boolean torneoSeleccionadoTieneTickets() {
        return torneoSeleccionado != null && torneoSeleccionado.hayTickets();
    }
    
    public int getTicketsDisponiblesTorneoSeleccionado() {
        if (torneoSeleccionado == null) {
            return 0;
        }

        return torneoSeleccionado.getTicketsDisponibles();
    }
    
    public int getTamanioCola() {
        return colaEspera.tamanio();
    }
    
    public void setTicketsVendidos(ListaSimple ticketsVendidos) {
        if (ticketsVendidos != null) {
            this.ticketsVendidos = ticketsVendidos;
        }
    }
    
}
