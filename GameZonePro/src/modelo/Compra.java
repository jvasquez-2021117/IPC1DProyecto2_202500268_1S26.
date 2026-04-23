/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */

import estructuras.ListaSimple;

public class Compra {
 
    private String fechaHora;
    private ListaSimple detalles;
    private double total;

    public Compra(String fechaHora, ListaSimple detalles, double total) {
        this.fechaHora = fechaHora;
        this.detalles = detalles;
        this.total = total;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public ListaSimple getDetalles() {
        return detalles;
    }

    public void setDetalles(ListaSimple detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Fecha: " + fechaHora + " | Total: Q" + total;
    }
    
}
