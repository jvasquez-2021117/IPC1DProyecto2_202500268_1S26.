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

public class NodoMatriz {
    
    private Carta dato;
    private NodoMatriz arriba;
    private NodoMatriz abajo;
    private NodoMatriz izquierda;
    private NodoMatriz derecha;
    private int fila;
    private int columna;

    public NodoMatriz(Carta dato, int fila, int columna) {
        this.dato = dato;
        this.fila = fila;
        this.columna = columna;
    }

    public Carta getDato() {
        return dato;
    }

    public void setDato(Carta dato) {
        this.dato = dato;
    }

    public NodoMatriz getArriba() {
        return arriba;
    }

    public void setArriba(NodoMatriz arriba) {
        this.arriba = arriba;
    }

    public NodoMatriz getAbajo() {
        return abajo;
    }

    public void setAbajo(NodoMatriz abajo) {
        this.abajo = abajo;
    }

    public NodoMatriz getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoMatriz izquierda) {
        this.izquierda = izquierda;
    }

    public NodoMatriz getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoMatriz derecha) {
        this.derecha = derecha;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean estaVacia() {
        return dato == null;
    }
    
}
