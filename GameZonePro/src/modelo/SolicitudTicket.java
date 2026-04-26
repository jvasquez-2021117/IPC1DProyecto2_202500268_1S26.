/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class SolicitudTicket {
    
    private String nombreUsuario;
    private String idTorneo;

    public SolicitudTicket(String nombreUsuario, String idTorneo) {
        this.nombreUsuario = nombreUsuario;
        this.idTorneo = idTorneo;
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

    @Override
    public String toString() {
        return nombreUsuario + " -> " + idTorneo;
    }
    
}
