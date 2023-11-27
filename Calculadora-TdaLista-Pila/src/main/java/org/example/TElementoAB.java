package org.example;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class TElementoAB<T> implements IElementoAB<T> {

    private Comparable etiqueta;
    private TElementoAB<T> hijoIzq;
    private TElementoAB<T> hijoDer;
    private T datos;

    private Lista<Comparable> ListaNodos = new Lista<>();

    /**
     * @param unaEtiqueta
     * @param unosDatos
     */
    @SuppressWarnings("unchecked")
    public TElementoAB(Comparable unaEtiqueta, T unosDatos) {
        etiqueta = unaEtiqueta;
        datos = unosDatos;
        ListaNodos.insertar(new Nodo<>(this.getEtiqueta(),this.getEtiqueta()));
    }


    public TElementoAB<T> getHijoIzq() {
        return hijoIzq;
    }

    public TElementoAB<T> getHijoDer() {
        return hijoDer;
    }

    /**
     * @param unElemento
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean insertar(TElementoAB<T> unElemento) {
        if (unElemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().insertar(unElemento);
            } else {
                hijoIzq = unElemento;
                // Insertar en la lista de nodos
                return true;
            }
        } else if (unElemento.getEtiqueta().compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                return getHijoDer().insertar(unElemento);
            } else {
                hijoDer = unElemento;
                // Insertar en la lista de nodos
                return true;
            }
        } else {
            // ya existe un elemento con la misma etiqueta.-
            return false;
        }
    }

    /**
     * @param unaEtiqueta
     * @return
     */
    @Override
    public TElementoAB<T> buscar(Comparable unaEtiqueta) {

        if (unaEtiqueta.equals(etiqueta)) {
            return this;
        } else if (unaEtiqueta.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().buscar(unaEtiqueta);
            } else {
                return null;
            }
        } else if (hijoDer != null) {
            return getHijoDer().buscar(unaEtiqueta);
        } else {
            return null;
        }
    }

    public boolean esHoja(){
        return this.getHijoDer() == null && this.getHijoIzq() == null;
    }

    /**
     * @return recorrida en inorden del subArbol que cuelga del elemento actual
     */

    public String preOrden() {
        String resultado = "";
        resultado = resultado + this.getEtiqueta();
        if (this.getHijoIzq() != null) {
            resultado = resultado + this.getHijoIzq().inOrden();
        }
        if (this.getHijoDer() != null) {
            resultado = resultado + this.getHijoDer().inOrden();
        }

        return resultado;
    }

    @Override
    public String inOrden() {
        String resultado = "";
        if (this.getHijoIzq() != null) {
            resultado = resultado + this.getHijoIzq().inOrden();
        }
        resultado = resultado + this.getEtiqueta();
        if (this.getHijoDer() != null) {
            resultado = resultado + this.getHijoDer().inOrden();
        }

        return resultado;
    }

    public String postOrden() {
        StringBuilder resultado = new StringBuilder();
        if (this.getHijoIzq() != null) {
            resultado = new StringBuilder(resultado + this.getHijoIzq().inOrden());
        }
        if (this.getHijoDer() != null) {
            resultado = new StringBuilder(resultado + this.getHijoDer().inOrden());
        }

        resultado.append(this.getEtiqueta());
        resultado.append("-");

        return resultado.toString();
    }

    public void agregarNodosInOrden(TElementoAB<T> nodo) {
        if (this != null) {
            if (this.getHijoIzq() != null) {
                this.getHijoIzq().agregarNodosInOrden(this);
                ListaNodos.insertar(new Nodo<>(this.getEtiqueta(),this.getEtiqueta()));
            }

            ListaNodos.insertar(new Nodo<>(this.getEtiqueta(),this.getEtiqueta()));

            if (this.getHijoDer() != null) {

                this.getHijoDer().agregarNodosInOrden(this);
                ListaNodos.insertar(new Nodo<>(this.getEtiqueta(),this.getEtiqueta()));
            }
        }
    }


    @Override
    public Comparable getEtiqueta() {
        return etiqueta;
    }

    /**
     * @return
     */
    public String imprimir() {
        return (etiqueta.toString());
    }

    @Override
    public T getDatos() {
        return datos;
    }

    @Override
    public void setHijoIzq(TElementoAB<T> elemento) {
        this.hijoIzq = elemento;

    }

    @Override
    public void setHijoDer(TElementoAB<T> elemento) {
        this.hijoDer = elemento;
    }

    public Lista<Comparable> getListaNodos(){
        return ListaNodos;
    }

    @Override
    public int obtenerAltura() {
        if (esHoja()){return 0;}
        int hIzq = -1;
        int hDer = -1;

        if (this.getHijoIzq() != null) {
            hIzq = this.getHijoIzq().obtenerAltura();
        }
        if (this.getHijoDer() != null) {
            hIzq = this.getHijoDer().obtenerAltura();
        }

        return max(hDer, hIzq) + 1;
    }

    @Override
    public int obtenerTamanio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int obtenerNivel(Comparable unaEtiqueta) {
        return this.obtenerAltura() - this.buscar(unaEtiqueta).obtenerAltura();
    }

    @Override
    public int obtenerCantidadHojas(){
        int hojasIzquierda = 0;
        int hojasDerecha = 0;
        if (this == null) {
            return 0;
        }

        // Si es un nodo hoja, retornamos 1; de lo contrario, sumamos las hojas de los subárboles izquierdo y derecho
        if (this.esHoja()) {
            return 1;
        } else {
            if (this.getHijoDer() != null)
            {hojasDerecha = this.getHijoDer().obtenerCantidadHojas();}
            if (this.getHijoIzq() != null){
                hojasIzquierda = this.getHijoIzq().obtenerCantidadHojas();}
            return hojasIzquierda + hojasDerecha;
        }
    }
    
     @Override
    public TElementoAB eliminar(Comparable unaEtiqueta) {
         if (unaEtiqueta.compareTo(this.getEtiqueta())<0){
             if (this.getHijoIzq()!=null){
                 this.setHijoIzq(this.getHijoIzq().eliminar(unaEtiqueta));
             }
             return this;
         }
         if (unaEtiqueta.compareTo(this.getEtiqueta())>0){
             if (this.getHijoDer()!=null){
                 this.setHijoDer(this.getHijoDer().eliminar(unaEtiqueta));
             }
             return this;
         }

         return this.quitaElNodo();
    }

  
    
     private TElementoAB quitaElNodo() {
         if (this.getHijoIzq() == null){
             return this.getHijoDer();
         }
         if (this.getHijoDer() == null){
             return this.getHijoIzq();
         }

         TElementoAB elHijo = this.getHijoIzq();
         TElementoAB elPadre = this;
         while (elHijo.getHijoDer() != null) {
             elPadre = elHijo;
             elHijo = elHijo.getHijoDer();
         }
         if (elPadre != this){
             elPadre.setHijoDer(elHijo.getHijoIzq());
             elHijo.setHijoDer(this.getHijoIzq());
         }
         elHijo.setHijoDer(this.getHijoDer());
         return elHijo;
        }

    /*
    * Obtengo el balance para todos los casos de un Nodo.
    * En el caso que tenga dos hijos o en el caso de que tenga solo uno
    * */
    public int obtenerBalance(){
        if (this.hijoIzq !=null && this.hijoDer!=null) {
            return this.getHijoIzq().obtenerAltura() - this.getHijoDer().obtenerAltura();
        }
        if (this.hijoDer != null && this.hijoIzq==null){
            return getHijoDer().obtenerAltura();
        }
        else{return hijoIzq.obtenerAltura();}
    }

    public boolean estaBalanceado(){
        if (abs(this.obtenerBalance())>1){
            return false;
        } else {
            return true;
        }
    }

    /*
     * ------MenorClave------:
     * Devuelvo la menor clave recorriendo hacia la izquierda lo mas posible
     * */
    public TElementoAB<T> menorClave(){
        TElementoAB NodoAuxMenor = this;
        while (this.getHijoIzq() != null && this.getHijoIzq().getEtiqueta().compareTo(NodoAuxMenor.getEtiqueta()) < 0){
            NodoAuxMenor = this.getHijoIzq().menorClave();
        }

        return NodoAuxMenor;
    }

    /*
    * ------MayorClave------:
    * Devuelvo la mayor clave recorriendo hacia la derecha lo mas posible
    * */
    public TElementoAB<T> mayorClave(){
        TElementoAB NodoAuxMayor = this;
        while (this.getHijoDer() != null && this.getHijoDer().getEtiqueta().compareTo(NodoAuxMayor.getEtiqueta()) > 0){
            NodoAuxMayor = this.getHijoDer().mayorClave();
        }
        return NodoAuxMayor;
    }

    /*
    * -----ObtenerPadre---------:
    * Metodo donde voy recorriendo los nodos y verificando si alguno de los hijo del actual
    * tienen la misma etiqueta que la que busco y si es Así lo devuelvo como padre
    * */
    public TElementoAB<T> obtenerPadre(Comparable etiqueta) {
        if ((hijoIzq != null && hijoIzq.getEtiqueta().equals(etiqueta)) || (hijoDer != null && hijoDer.getEtiqueta().equals(etiqueta))) {
            return this; // Este nodo es el padre del nodo buscado.
        } else if (etiqueta.compareTo(this.getEtiqueta()) < 0 && hijoIzq != null) {
            return hijoIzq.obtenerPadre(etiqueta); // Buscar en el subárbol izquierdo.
        } else if (etiqueta.compareTo(this.getEtiqueta()) > 0 && hijoDer != null) {
            return hijoDer.obtenerPadre(etiqueta); // Buscar en el subárbol derecho.
        }
        return null; // El nodo no se encuentra en el árbol.
    }

    private TElementoAB<T> rotacionLL(TElementoAB k2){
        TElementoAB k1 = k2.getHijoIzq();
        k2.setHijoIzq(k1.getHijoDer());
        k1.setHijoDer(k2);
        return k1;
    }

    private TElementoAB<T> rotacionRR (TElementoAB k1){
        TElementoAB k2 = k1.getHijoDer();
        k1.setHijoDer(k2.getHijoIzq());
        k2.setHijoIzq(k1);
        return k2;
    }

    private TElementoAB<T> rotacionLR (TElementoAB k3){
        k3.setHijoIzq(rotacionRR(k3.getHijoIzq()));
        return rotacionLL(k3);
    }
    private TElementoAB<T> rotacionRL(TElementoAB k1){
        k1.setHijoDer(this.rotacionLL(k1.getHijoDer()));
        return this.rotacionRR(k1);
    }

    // Método para realizar el balanceo de un nodo
    public TElementoAB<T> balancear(TElementoAB<T> nodo) {
        int factorBalance = nodo.obtenerBalance();

        // Caso LL: Desbalance hacia la izquierda en el subárbol izquierdo del subárbol izquierdo
        if (factorBalance > 1 && nodo.getEtiqueta().compareTo(nodo.getHijoIzq().getEtiqueta()) < 0) {
            return rotacionLL(nodo);
        }

        // Caso RR: Desbalance hacia la derecha en el subárbol derecho del subárbol derecho
        if (factorBalance < -1 && nodo.getEtiqueta().compareTo(nodo.getHijoDer().getEtiqueta()) > 0) {
            return rotacionRR(nodo);
        }

        // Caso LR: Desbalance hacia la izquierda en el subárbol derecho del subárbol izquierdo
        if (factorBalance > 1 && nodo.getEtiqueta().compareTo(nodo.getHijoIzq().getEtiqueta()) > 0) {
            nodo.setHijoIzq(rotacionRR(nodo.getHijoIzq()));
            return rotacionLL(nodo);
        }

        // Caso RL: Desbalance hacia la derecha en el subárbol izquierdo del subárbol derecho
        if (factorBalance < -1 && nodo.getEtiqueta().compareTo(nodo.getHijoDer().getEtiqueta()) < 0) {
            nodo.setHijoDer(rotacionLL(nodo.getHijoDer()));
            return rotacionRR(nodo);
        }


        return nodo;
    }}
