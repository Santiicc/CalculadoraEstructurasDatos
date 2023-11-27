package org.example;

public class TArbolBB<T> implements IArbolBB<T> {

    private TElementoAB<T> raiz;

    /**
     * Separador utilizado entre elemento y elemento al imprimir la lista
     */
    public static final String SEPARADOR_ELEMENTOS_IMPRESOS = "-";

    public TArbolBB() {
        raiz = null;
    }

    public int Contadorelementos;

    /**
     * @param unElemento
     * @return
     */
    public boolean insertar(TElementoAB<T> unElemento) {
        if (esVacio()) {
            raiz = unElemento;
            return true;
        } else {
            Contadorelementos+=1;
            return raiz.insertar(unElemento);
        }
    }

    public TElementoAB getRaiz (){
        return raiz;
    }

    public Lista getNodosEnLista(){
        Lista listaNodosaux = raiz.getListaNodos();
        return listaNodosaux;
    }


    /**
     * @param unaEtiqueta
     * @return
     */
    @SuppressWarnings("unchecked")
    public TElementoAB<T> buscar(Comparable unaEtiqueta) {
        if (esVacio()) {
            return null;
        } else {
            return raiz.buscar(unaEtiqueta);
        }
    }

    public Comparable menorClave(){
        if (esVacio()){
            return null;
        }
        else {
            return raiz.menorClave().getEtiqueta();
        }
    }

    public Comparable mayorClave(){
        if (esVacio()){
            return null;
        }
        else {
            return raiz.mayorClave().getEtiqueta();
        }
    }

    public Comparable getPadre(Comparable etiqueta){
        if (raiz != null){
            return raiz.obtenerPadre(etiqueta).getEtiqueta();
        }
        {return null;}
    }

    public boolean EsBalanceado(){
        return raiz.estaBalanceado();
    }

    public int AlturaArbol(){
        return raiz.obtenerAltura();
    }


    /**
     * @return recorrida en inorden del arbol, null en caso de ser vacío
     */
    public String inOrden() {
        if (esVacio()) {
            return null;
        } else {
            return raiz.inOrden();
        }
    }

    public String preOrden() {
        if (raiz != null){
            return raiz.preOrden();
        } else {
            return null;
        }
    }


    public String postOrden() {
        if (raiz!=null){
            return raiz.postOrden();
        } else {
            return null;
        }
    }

    /**
     * @return recorrida en preOrden del arbol, null en caso de ser vacío
     */
    /**
     * @return
     */
    public boolean esVacio() {
        return (raiz == null);
    }

    /**
     * @return True si habían elementos en el árbol, false en caso contrario
     */
    public boolean vaciar() {
        if (!esVacio()) {
            raiz = null;
            return true;
        }
        return false;
    }


    @Override
    public int obtenerAltura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int obtenerTamanio() {
       return Contadorelementos+1;
    }

    @Override
    public int obtenerNivel(Comparable unaEtiqueta) {
        return raiz.obtenerNivel(unaEtiqueta);
    }

    @Override
    public int obtenerCantidadHojas() {
        return raiz.obtenerCantidadHojas();
    }

  @Override
    public void eliminar(Comparable unaEtiqueta) {
        if (!esVacio()) {
            Contadorelementos -=1;
            this.raiz = this.raiz.eliminar(unaEtiqueta);
        }
        else{
            System.out.println("No se puede eliminar, no hay elementos");
      }
    }

    public void ImprimirARBOL(TElementoAB nodo){
        if(nodo!=null){
            ImprimirARBOL(nodo.getHijoDer());
            System.out.println(nodo.getEtiqueta());
            ImprimirARBOL(nodo.getHijoIzq());
        }
    }
   

}
