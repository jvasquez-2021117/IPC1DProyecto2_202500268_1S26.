/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class Carta {
    
    private String codigo;
    private String nombre;
    private String tipo;
    private String rareza;
    private int ataque;
    private int defensa;
    private int ps;
    private String imagen;

    public Carta(String codigo, String nombre, String tipo, String rareza,
                 int ataque, int defensa, int ps, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ps = ps;
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean esLegendaria() {
        return rareza != null && rareza.equalsIgnoreCase("Legendaria");
    }

    public boolean tieneImagen() {
        return imagen != null && !imagen.trim().isEmpty();
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + tipo + " | " + rareza;
    }
    
}
