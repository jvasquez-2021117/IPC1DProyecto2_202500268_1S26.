/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vqzjo
 */


import modelo.Carta;

public class Album {
    
    private NodoMatriz cabeza;
    private int filas;
    private int columnas;

    public Album(int filas, int columnas) {
        if (filas < 4) {
            filas = 4;
        }

        if (columnas < 6) {
            columnas = 6;
        }

        this.filas = filas;
        this.columnas = columnas;

        construirMalla();
    }

    private void construirMalla() {
        NodoMatriz inicioFilaAnterior = null;

        for (int i = 0; i < filas; i++) {
            NodoMatriz inicioFilaActual = null;
            NodoMatriz anteriorEnFila = null;
            NodoMatriz arriba = inicioFilaAnterior;

            for (int j = 0; j < columnas; j++) {
                NodoMatriz nuevo = new NodoMatriz(null, i, j);

                if (inicioFilaActual == null) {
                    inicioFilaActual = nuevo;
                }

                if (anteriorEnFila != null) {
                    anteriorEnFila.setDerecha(nuevo);
                    nuevo.setIzquierda(anteriorEnFila);
                }

                if (arriba != null) {
                    nuevo.setArriba(arriba);
                    arriba.setAbajo(nuevo);
                    arriba = arriba.getDerecha();
                }

                anteriorEnFila = nuevo;
            }

            if (i == 0) {
                cabeza = inicioFilaActual;
            }

            inicioFilaAnterior = inicioFilaActual;
        }
    }

    public NodoMatriz getCabeza() {
        return cabeza;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public NodoMatriz obtenerNodo(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }

        NodoMatriz actual = cabeza;

        for (int i = 0; i < fila; i++) {
            if (actual != null) {
                actual = actual.getAbajo();
            }
        }

        for (int j = 0; j < columna; j++) {
            if (actual != null) {
                actual = actual.getDerecha();
            }
        }

        return actual;
    }

    public NodoMatriz obtenerPrimeraVacia() {
        NodoMatriz filaActual = cabeza;

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                if (columnaActual.estaVacia()) {
                    return columnaActual;
                }
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return null;
    }

    public boolean agregarCarta(Carta carta) {
        if (carta == null) {
            return false;
        }

        if (existeCarta(carta.getCodigo())) {
            return false;
        }

        NodoMatriz nodoVacio = obtenerPrimeraVacia();

        if (nodoVacio == null) {
            return false;
        }

        nodoVacio.setDato(carta);
        return true;
    }

    public boolean colocarCartaEn(int fila, int columna, Carta carta) {
        NodoMatriz nodo = obtenerNodo(fila, columna);

        if (nodo == null) {
            return false;
        }

        nodo.setDato(carta);
        return true;
    }

    public boolean intercambiarCartas(int fila1, int columna1, int fila2, int columna2) {
        NodoMatriz nodo1 = obtenerNodo(fila1, columna1);
        NodoMatriz nodo2 = obtenerNodo(fila2, columna2);

        if (nodo1 == null || nodo2 == null) {
            return false;
        }

        Carta temporal = nodo1.getDato();
        nodo1.setDato(nodo2.getDato());
        nodo2.setDato(temporal);

        return true;
    }

    public boolean existeCarta(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        NodoMatriz filaActual = cabeza;

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                Carta carta = columnaActual.getDato();

                if (carta != null && carta.getCodigo().equalsIgnoreCase(codigo.trim())) {
                    return true;
                }

                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return false;
    }

    public int contarCartas() {
        int contador = 0;
        NodoMatriz filaActual = cabeza;

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                if (!columnaActual.estaVacia()) {
                    contador++;
                }
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }

        return contador;
    }

    public boolean estaLleno() {
        return obtenerPrimeraVacia() == null;
    }

    public void vaciarAlbum() {
        NodoMatriz filaActual = cabeza;

        while (filaActual != null) {
            NodoMatriz columnaActual = filaActual;

            while (columnaActual != null) {
                columnaActual.setDato(null);
                columnaActual = columnaActual.getDerecha();
            }

            filaActual = filaActual.getAbajo();
        }
    }
    
}
