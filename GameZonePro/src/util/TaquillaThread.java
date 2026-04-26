/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author vqzjo
 */

import java.util.Random;
import javax.swing.SwingUtilities;
import modelo.TicketVendido;
import servicios.TorneoService;

public class TaquillaThread extends Thread {
    
    private String nombreTaquilla;
    private TorneoService torneoService;
    private EscuchadorTaquilla escuchador;
    private boolean enEjecucion;
    private Random random;

    public TaquillaThread(String nombreTaquilla, TorneoService torneoService, EscuchadorTaquilla escuchador) {
        this.nombreTaquilla = nombreTaquilla;
        this.torneoService = torneoService;
        this.escuchador = escuchador;
        this.enEjecucion = true;
        this.random = new Random();
    }

    @Override
    public void run() {
        notificarEstado("Libre");

        while (enEjecucion) {

            if (!torneoService.torneoSeleccionadoTieneTickets()) {
                break;
            }

            TicketVendido ticket = torneoService.tomarTicketParaProcesamiento(nombreTaquilla);

            if (ticket == null) {
                if (!torneoService.haySolicitudesPendientes()) {
                    break;
                }

                dormir(300);
                continue;
            }

            notificarEstado("Procesando a: " + ticket.getNombreUsuario());

            int tiempoEspera = 800 + random.nextInt(1201);
            dormir(tiempoEspera);

            notificarTicketVendido(ticket);
            notificarActualizacionCola();
        }

        notificarEstado("Libre");
    }

    public void detenerTaquilla() {
        enEjecucion = false;
        interrupt();
    }

    private void dormir(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void notificarEstado(String estado) {
        if (escuchador != null) {
            SwingUtilities.invokeLater(() -> {
                escuchador.actualizarEstadoTaquilla(nombreTaquilla, estado);
            });
        }
    }

    private void notificarActualizacionCola() {
        if (escuchador != null) {
            SwingUtilities.invokeLater(() -> {
                escuchador.actualizarColaVisual();
            });
        }
    }

    private void notificarTicketVendido(TicketVendido ticket) {
        if (escuchador != null && ticket != null) {
            SwingUtilities.invokeLater(() -> {
                escuchador.registrarTicketVendido(ticket);
            });
        }
    }
    
}
