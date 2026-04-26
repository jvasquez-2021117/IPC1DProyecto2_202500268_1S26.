/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */


import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelo.Logro;
import modelo.UsuarioGamificacion;

public class GamificacionService {
    
    private UsuarioGamificacion usuarioActual;
    private ListaSimple logros;

    private int comprasRealizadas;
    private int cartasAgregadas;
    private int filasCompletadas;
    private int torneosInscritos;
    private int cartasLegendarias;
    private double totalGastado;

    private UsuarioGamificacion[] leaderboard;
    private int cantidadLeaderboard;

    public GamificacionService() {
        this("Jugador Actual");
    }

    public GamificacionService(String nombreUsuario) {
        usuarioActual = new UsuarioGamificacion(nombreUsuario, 0);
        logros = new ListaSimple();

        comprasRealizadas = 0;
        cartasAgregadas = 0;
        filasCompletadas = 0;
        torneosInscritos = 0;
        cartasLegendarias = 0;
        totalGastado = 0;

        leaderboard = new UsuarioGamificacion[100];
        cantidadLeaderboard = 0;

        inicializarLogros();
    }

    public UsuarioGamificacion getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(UsuarioGamificacion usuarioActual) {
        if (usuarioActual != null) {
            this.usuarioActual = usuarioActual;
        }
    }

    public ListaSimple getLogros() {
        return logros;
    }

    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public int getCartasAgregadas() {
        return cartasAgregadas;
    }

    public int getFilasCompletadas() {
        return filasCompletadas;
    }

    public int getTorneosInscritos() {
        return torneosInscritos;
    }

    public int getCartasLegendarias() {
        return cartasLegendarias;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public int getCantidadLeaderboard() {
        return cantidadLeaderboard;
    }

    public void registrarInicioSesion() {
        usuarioActual.sumarXp(10);
        revisarLogros();
    }

    public String registrarCompraJuego(int cantidadJuegos, double montoGastado) {
        if (cantidadJuegos <= 0) {
            return "La cantidad de juegos debe ser mayor que 0.";
        }

        int nivelAnterior = usuarioActual.getNivel();

        comprasRealizadas += cantidadJuegos;
        totalGastado += montoGastado;
        usuarioActual.sumarXp(cantidadJuegos * 50);

        return construirResumenAccion("Compra registrada correctamente.", nivelAnterior);
    }

    public String registrarCartaAgregada(boolean esLegendaria) {
        int nivelAnterior = usuarioActual.getNivel();

        cartasAgregadas++;

        if (esLegendaria) {
            cartasLegendarias++;
            usuarioActual.sumarXp(200);
        }

        return construirResumenAccion("Carta agregada correctamente.", nivelAnterior);
    }

    public String registrarFilaCompletaAlbum() {
        int nivelAnterior = usuarioActual.getNivel();

        filasCompletadas++;
        usuarioActual.sumarXp(100);

        return construirResumenAccion("Fila completada correctamente.", nivelAnterior);
    }

    public String registrarInscripcionTorneo() {
        int nivelAnterior = usuarioActual.getNivel();

        torneosInscritos++;
        usuarioActual.sumarXp(150);

        return construirResumenAccion("Inscripción a torneo registrada.", nivelAnterior);
    }

    public int getNivelActual() {
        return usuarioActual.getNivel();
    }

    public String getRangoActual() {
        return usuarioActual.getRango();
    }

    public int getXpActual() {
        return usuarioActual.getXp();
    }

    public int getValorProgreso() {
        if (usuarioActual.esNivelMaximo()) {
            return usuarioActual.getXp();
        }

        return usuarioActual.getXpActualDelNivel();
    }

    public int getMaximoProgreso() {
        if (usuarioActual.esNivelMaximo()) {
            return usuarioActual.getXp();
        }

        return usuarioActual.getXpNecesariaNivelActual();
    }

    public String getResumenUsuario() {
        StringBuilder sb = new StringBuilder();

        sb.append("Usuario: ").append(usuarioActual.getNombreUsuario()).append("\n");
        sb.append("XP: ").append(usuarioActual.getXp()).append("\n");
        sb.append("Nivel: ").append(usuarioActual.getNivel()).append("\n");
        sb.append("Rango: ").append(usuarioActual.getRango()).append("\n");

        if (!usuarioActual.esNivelMaximo()) {
            sb.append("XP para siguiente nivel: ")
              .append(usuarioActual.getXpParaSiguienteNivel());
        } else {
            sb.append("Nivel máximo alcanzado.");
        }

        return sb.toString();
    }

    public String getTextoLogros() {
        if (logros.estaVacia()) {
            return "No hay logros registrados.";
        }

        StringBuilder sb = new StringBuilder();
        NodoSimple actual = logros.getCabeza();

        while (actual != null) {
            Logro logro = (Logro) actual.getDato();

            if (logro.isDesbloqueado()) {
                sb.append("[DESBLOQUEADO] ");
            } else {
                sb.append("[CANDADO] ");
            }

            sb.append(logro.getNombre())
              .append(" - ")
              .append(logro.getDescripcion())
              .append("\n");

            actual = actual.getSiguiente();
        }

        return sb.toString();
    }

    public int contarLogrosDesbloqueados() {
        int contador = 0;
        NodoSimple actual = logros.getCabeza();

        while (actual != null) {
            Logro logro = (Logro) actual.getDato();

            if (logro.isDesbloqueado()) {
                contador++;
            }

            actual = actual.getSiguiente();
        }

        return contador;
    }

    public void setLeaderboard(UsuarioGamificacion[] leaderboard, int cantidadLeaderboard) {
        if (leaderboard == null) {
            this.leaderboard = new UsuarioGamificacion[100];
            this.cantidadLeaderboard = 0;
            return;
        }

        this.leaderboard = leaderboard;
        this.cantidadLeaderboard = cantidadLeaderboard;
    }

    public UsuarioGamificacion[] getLeaderboardOrdenado() {
        UsuarioGamificacion[] copia = copiarLeaderboard();
        ordenarLeaderboardDescendente(copia, cantidadLeaderboard);
        return copia;
    }

    public UsuarioGamificacion[] getTop10Leaderboard() {
        UsuarioGamificacion[] ordenado = getLeaderboardOrdenado();

        int limite = cantidadLeaderboard;
        if (limite > 10) {
            limite = 10;
        }

        UsuarioGamificacion[] top10 = new UsuarioGamificacion[limite];

        for (int i = 0; i < limite; i++) {
            top10[i] = ordenado[i];
        }

        return top10;
    }

    public void actualizarUsuarioActualEnLeaderboard() {
        int indice = buscarIndiceUsuarioEnLeaderboard(usuarioActual.getNombreUsuario());

        if (indice >= 0) {
            leaderboard[indice].setXp(usuarioActual.getXp());
        } else {
            if (cantidadLeaderboard < leaderboard.length) {
                leaderboard[cantidadLeaderboard] = new UsuarioGamificacion(
                        usuarioActual.getNombreUsuario(),
                        usuarioActual.getXp()
                );
                cantidadLeaderboard++;
            }
        }
    }

    public String getTextoLeaderboardOrdenado() {
        if (cantidadLeaderboard == 0) {
            return "No hay datos en el leaderboard.";
        }

        UsuarioGamificacion[] ordenado = getLeaderboardOrdenado();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cantidadLeaderboard; i++) {
            sb.append(i + 1)
              .append(". ")
              .append(ordenado[i].toString())
              .append("\n");
        }

        return sb.toString();
    }

    private void inicializarLogros() {
        insertarLogro(new Logro(
                "Primera Compra",
                "Realiza tu primera compra en la tienda.",
                false
        ));

        insertarLogro(new Logro(
                "Coleccionista Novato",
                "Añade 10 cartas a tu álbum.",
                false
        ));

        insertarLogro(new Logro(
                "Coleccionista Experto",
                "Completa una fila completa del álbum.",
                false
        ));

        insertarLogro(new Logro(
                "Taquillero",
                "Compra tickets para 3 torneos distintos.",
                false
        ));

        insertarLogro(new Logro(
                "Alta Rareza",
                "Obtén una carta de rareza Legendaria.",
                false
        ));

        insertarLogro(new Logro(
                "Gamer Dedicado",
                "Acumula 1,000 XP.",
                false
        ));

        insertarLogro(new Logro(
                "Leyenda Viviente",
                "Alcanza el Nivel 5.",
                false
        ));

        insertarLogro(new Logro(
                "Gran Gastador",
                "Gasta más de Q2,000 en la tienda.",
                false
        ));
    }

    private String construirResumenAccion(String mensajeBase, int nivelAnterior) {
        StringBuilder sb = new StringBuilder();
        sb.append(mensajeBase).append("\n");
        sb.append("XP actual: ").append(usuarioActual.getXp()).append("\n");

        String nuevosLogros = revisarLogros();

        if (usuarioActual.getNivel() > nivelAnterior) {
            sb.append("¡Subiste al nivel ")
              .append(usuarioActual.getNivel())
              .append(" - ")
              .append(usuarioActual.getRango())
              .append("!\n");
        }

        if (!nuevosLogros.isEmpty()) {
            sb.append(nuevosLogros);
        }

        actualizarUsuarioActualEnLeaderboard();

        return sb.toString().trim();
    }

    private String revisarLogros() {
        StringBuilder sb = new StringBuilder();

        desbloquearSiCumple("Primera Compra", comprasRealizadas >= 1, sb);
        desbloquearSiCumple("Coleccionista Novato", cartasAgregadas >= 10, sb);
        desbloquearSiCumple("Coleccionista Experto", filasCompletadas >= 1, sb);
        desbloquearSiCumple("Taquillero", torneosInscritos >= 3, sb);
        desbloquearSiCumple("Alta Rareza", cartasLegendarias >= 1, sb);
        desbloquearSiCumple("Gamer Dedicado", usuarioActual.getXp() >= 1000, sb);
        desbloquearSiCumple("Leyenda Viviente", usuarioActual.getNivel() >= 5, sb);
        desbloquearSiCumple("Gran Gastador", totalGastado > 2000, sb);

        return sb.toString();
    }

    private void desbloquearSiCumple(String nombreLogro, boolean condicion, StringBuilder sb) {
        if (!condicion) {
            return;
        }

        Logro logro = buscarLogroPorNombre(nombreLogro);

        if (logro != null && !logro.isDesbloqueado()) {
            logro.setDesbloqueado(true);
            sb.append("¡Logro desbloqueado!: ")
              .append(logro.getNombre())
              .append("\n");
        }
    }

    private Logro buscarLogroPorNombre(String nombre) {
        NodoSimple actual = logros.getCabeza();

        while (actual != null) {
            Logro logro = (Logro) actual.getDato();

            if (logro.getNombre().equalsIgnoreCase(nombre)) {
                return logro;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    private int buscarIndiceUsuarioEnLeaderboard(String nombreUsuario) {
        for (int i = 0; i < cantidadLeaderboard; i++) {
            if (leaderboard[i] != null
                    && leaderboard[i].getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return i;
            }
        }

        return -1;
    }

    private UsuarioGamificacion[] copiarLeaderboard() {
        UsuarioGamificacion[] copia = new UsuarioGamificacion[cantidadLeaderboard];

        for (int i = 0; i < cantidadLeaderboard; i++) {
            UsuarioGamificacion usuario = leaderboard[i];

            if (usuario != null) {
                copia[i] = new UsuarioGamificacion(
                        usuario.getNombreUsuario(),
                        usuario.getXp()
                );
            }
        }

        return copia;
    }

    private void ordenarLeaderboardDescendente(UsuarioGamificacion[] arreglo, int cantidad) {
        for (int i = 0; i < cantidad - 1; i++) {
            int indiceMayor = i;

            for (int j = i + 1; j < cantidad; j++) {
                if (arreglo[j] != null && arreglo[indiceMayor] != null) {
                    if (arreglo[j].getXp() > arreglo[indiceMayor].getXp()) {
                        indiceMayor = j;
                    }
                }
            }

            UsuarioGamificacion temporal = arreglo[i];
            arreglo[i] = arreglo[indiceMayor];
            arreglo[indiceMayor] = temporal;
        }
    }

    private void insertarLogro(Logro logro) {
        logros.insertarAlFinal(logro);
    }
    
    public boolean usuarioActualEstaEnTop10() {
        UsuarioGamificacion[] top10 = getTop10Leaderboard();

        for (int i = 0; i < top10.length; i++) {
            if (top10[i] != null
                    && top10[i].getNombreUsuario().equalsIgnoreCase(usuarioActual.getNombreUsuario())) {
                return true;
            }
        }

        return false;
    }
    
    public UsuarioGamificacion buscarUsuarioLeaderboardPorNombre(String nombreUsuario) {
        int indice = buscarIndiceUsuarioEnLeaderboard(nombreUsuario);

        if (indice >= 0) {
            return leaderboard[indice];
        }

        return null;
    }
    
    public UsuarioGamificacion[] getLeaderboardOrdenadoParaGuardar() {
        return getLeaderboardOrdenado();
    }
    
    private void revisarLogrosPorXpYNivel() {
        Logro gamerDedicado = buscarLogroPorNombre("Gamer Dedicado");
        if (gamerDedicado != null && usuarioActual.getXp() >= 1000) {
            gamerDedicado.setDesbloqueado(true);
        }

        Logro leyendaViviente = buscarLogroPorNombre("Leyenda Viviente");
        if (leyendaViviente != null && usuarioActual.getNivel() >= 5) {
            leyendaViviente.setDesbloqueado(true);
        }
    }
    
    public void sincronizarUsuarioActualConLeaderboard() {
        int indice = buscarIndiceUsuarioEnLeaderboard(usuarioActual.getNombreUsuario());

        if (indice >= 0) {
            usuarioActual.setXp(leaderboard[indice].getXp());
        } else {
            actualizarUsuarioActualEnLeaderboard();
        }

        revisarLogrosPorXpYNivel();
    }
    
    public void cargarLeaderboardDesdeArchivo(UsuarioGamificacion[] leaderboardCargado, int cantidadCargada) {
        setLeaderboard(leaderboardCargado, cantidadCargada);
        sincronizarUsuarioActualConLeaderboard();
    }
    
}
