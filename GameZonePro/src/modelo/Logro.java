/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class Logro {
    
    private String nombre;
    private String descripcion;
    private boolean desbloqueado;

    public Logro(String nombre, String descripcion, boolean desbloqueado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.desbloqueado = desbloqueado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public String getEstadoTexto() {
        if (desbloqueado) {
            return "Desbloqueado";
        }
        return "Bloqueado";
    }

    @Override
    public String toString() {
        return nombre + " | " + descripcion + " | " + getEstadoTexto();
    }
    
}
