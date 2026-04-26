/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author vqzjo
 */

import modelo.TicketVendido;

public interface EscuchadorTaquilla {
    
    void actualizarEstadoTaquilla(String nombreTaquilla, String estado);

    void actualizarColaVisual();

    void registrarTicketVendido(TicketVendido ticket);
        
}
