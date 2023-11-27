package org.example;

import java.util.ArrayList;

public class Lista<T> implements ILista<T> {

    private Nodo<T> primero;

    public int ContadorElementos;

    public Lista() {
        primero = null;
    }

    ArrayList<Nodo> arrayListaux;

    //Insertar recorre hasta el final la lista y agrega un elemento al final
    @Override
    public void insertar(Nodo<T> unNodo){

        Nodo aux = primero;
        if (esVacia()){
            primero = unNodo;
        }
        else{
            while (aux.getSiguiente() != null){
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(unNodo);
        }
        ContadorElementos+=1;
    }

    /*
    insertarOrdenado va verificando con el CompareTo si el siguiente es mayor o no  y Así evaluá donde
    agrega el nuevo nodo. En caso de ser mas pequeño que el primero al principio, en caso de ser mas grande
    que todos, al final de la lista, y sino hasta que encuentre el punto donde debe ir
    */

    public void insertarOrdenado(Nodo<T> unNodo){

        if (esVacia() || unNodo.getEtiqueta().compareTo(primero.getEtiqueta()) <= 0){
            unNodo.setSiguiente(primero);
            primero = unNodo;
        } else {
            Nodo<T> actual = primero;
            while(actual.getSiguiente() != null && unNodo.getEtiqueta().compareTo(actual.getSiguiente().getEtiqueta()) > 0){
                actual = actual.getSiguiente();
            }
            unNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(unNodo);
        }
        ContadorElementos+=1;
    }


    @Override
    public Nodo<T> buscar(Comparable clave) {
        if (esVacia()) {
            return null;
        } else {
            Nodo<T> aux = primero;
            while (aux != null) {
                if (aux.getEtiqueta().equals(clave)) {
                    return aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(Comparable clave) {
        if (esVacia()) {
            return false;
        }
        if (primero.getSiguiente() == null) {
            if (primero.getEtiqueta().equals(clave)) {
                primero = null;
                return true;
            }
        }
        Nodo<T> aux = primero;
        if (aux.getEtiqueta().compareTo(clave) == 0) {
            //Eliminamos el primer elemento
            Nodo<T> temp1 = aux;
            Nodo<T> temp = aux.getSiguiente();
            primero = temp;
            return true;
        }
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getEtiqueta().equals(clave)) {
                Nodo<T> temp = aux.getSiguiente();
                aux.setSiguiente(temp.getSiguiente());
                return true;
            }
            aux = aux.getSiguiente();
        }
        ContadorElementos -=1;
        return false;

    }

    @Override
    public String imprimir() {
        String aux = "";
        if (!esVacia()) {
            Nodo<T> temp = primero;
            while (temp != null) {
                temp.imprimirEtiqueta();
                temp = temp.getSiguiente();
            }
        }
        return aux;
    }

    public String imprimir(String separador) {
        StringBuilder aux = new StringBuilder();
        if (esVacia()) {
            return "";
        } else {
            Nodo<T> temp = primero;
            aux.append(temp.getEtiqueta());

            while (temp.getSiguiente() != null) {
                aux.append(separador).append(temp.getSiguiente().getEtiqueta());
                temp = temp.getSiguiente();
            }

        }
        return aux.toString();

    }

    @Override
    public int cantElementos() {
        if (this.esVacia()){
            return 0;
        }
        return ContadorElementos;
    }

    @Override
    public boolean esVacia() {
        return primero == null;
    }

    public Nodo<T> getPrimero() {
        return primero;
    }

    @Override
    public void setPrimero(Nodo<T> unNodo) {
        this.primero = unNodo;
    }

    public int[] obtenerArregloDeElementos() {
        if (esVacia()) {
            return new int[0]; // Retorna un arreglo vacío si la lista está vacía
        } else {
            int[] arreglo = new int[ContadorElementos];
            Nodo<T> temp = primero;
            int indice = 0;

            while (temp != null) {
                // Suponiendo que las etiquetas son enteros, convierte la etiqueta a int y guárdala en el arreglo
                arreglo[indice] = Integer.parseInt(temp.getEtiqueta().toString());
                temp = temp.getSiguiente();
                indice++;
            }

            return arreglo;
        }}



}
