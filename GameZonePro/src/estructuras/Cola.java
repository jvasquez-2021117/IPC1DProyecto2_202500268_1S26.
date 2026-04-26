/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vqzjo
 */
public class Cola {
    
    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public void encolar(Object dato) {
        NodoCola nuevo = new NodoCola(dato);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }

        tamanio++;
    }

    public synchronized Object desencolar() {
        if (estaVacia()) {
            return null;
        }

        Object dato = frente.getDato();
        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return tamanio;
    }

    public Object peek() {
        if (estaVacia()) {
            return null;
        }

        return frente.getDato();
    }

    public NodoCola getFrente() {
        return frente;
    }

    public NodoCola getFin() {
        return fin;
    }

    public void vaciar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    @Override
    public String toString() {
        if (estaVacia()) {
            return "Cola vacía";
        }

        StringBuilder sb = new StringBuilder();
        NodoCola actual = frente;

        while (actual != null) {
            sb.append(actual.getDato());

            if (actual.getSiguiente() != null) {
                sb.append(" -> ");
            }

            actual = actual.getSiguiente();
        }

        return sb.toString();
    }
    
}
