/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vqzjo
 */
public class ListaSimple {
    
    private NodoSimple cabeza;

    public ListaSimple() {
        this.cabeza = null;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void insertarAlInicio(Object dato) {
        NodoSimple nuevo = new NodoSimple(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    public void insertarAlFinal(Object dato) {
        NodoSimple nuevo = new NodoSimple(dato);

        if (estaVacia()) {
            cabeza = nuevo;
            return;
        }

        NodoSimple actual = cabeza;

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        actual.setSiguiente(nuevo);
    }

    public int tamanio() {
        int contador = 0;
        NodoSimple actual = cabeza;

        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }

        return contador;
    }

    public void imprimirLista() {
        NodoSimple actual = cabeza;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }

    public NodoSimple getCabeza() {
        return cabeza;
    }
    
    public void setCabeza(NodoSimple cabeza) {
        this.cabeza = cabeza;
    }
    
}
