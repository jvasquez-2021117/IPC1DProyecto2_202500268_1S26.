/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vqzjo
 */
public class UsuarioGamificacion {
    
    private String nombreUsuario;
    private int xp;

    public UsuarioGamificacion(String nombreUsuario, int xp) {
        this.nombreUsuario = nombreUsuario;
        this.xp = xp;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        if (xp < 0) {
            this.xp = 0;
        } else {
            this.xp = xp;
        }
    }

    public void sumarXp(int cantidad) {
        if (cantidad > 0) {
            this.xp += cantidad;
        }
    }

    public int getNivel() {
        if (xp >= 7000) {
            return 5;
        } else if (xp >= 3500) {
            return 4;
        } else if (xp >= 1500) {
            return 3;
        } else if (xp >= 500) {
            return 2;
        } else {
            return 1;
        }
    }

    public String getRango() {
        switch (getNivel()) {
            case 1:
                return "Aprendiz";
            case 2:
                return "Jugador";
            case 3:
                return "Veterano";
            case 4:
                return "Maestro";
            case 5:
                return "Leyenda";
            default:
                return "Desconocido";
        }
    }

    public int getXpActualDelNivel() {
        switch (getNivel()) {
            case 1:
                return xp;
            case 2:
                return xp - 500;
            case 3:
                return xp - 1500;
            case 4:
                return xp - 3500;
            case 5:
                return xp - 7000;
            default:
                return 0;
        }
    }

    public int getXpNecesariaNivelActual() {
        switch (getNivel()) {
            case 1:
                return 500;
            case 2:
                return 1000;
            case 3:
                return 2000;
            case 4:
                return 3500;
            case 5:
                return 0;
            default:
                return 0;
        }
    }

    public int getXpParaSiguienteNivel() {
        switch (getNivel()) {
            case 1:
                return 500 - xp;
            case 2:
                return 1500 - xp;
            case 3:
                return 3500 - xp;
            case 4:
                return 7000 - xp;
            case 5:
                return 0;
            default:
                return 0;
        }
    }

    public boolean esNivelMaximo() {
        return getNivel() == 5;
    }

    @Override
    public String toString() {
        return nombreUsuario + " | XP: " + xp + " | Nivel " + getNivel() + " - " + getRango();
    }
    
    
}
