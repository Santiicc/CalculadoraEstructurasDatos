package org.example;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//------------------------------------------------------------------------------------------------------

/*
*
* La idea principal de este proyecto es hacer una calculadora que tenga el funcionamiento de una
* calculadora normal,pero que internamente. Use una pila para guardar los operadores y ademas use el
* TDALista para poder almacenar los resultados anteriores y mostrarlos por consola. Luego que tenga la funcionalidad
* de mostrar un arbol con la ultima operacion y ordenar por HeapSort los ultimos resultados.
*
* */

//-------------------------------------------------------------------------------------------------------


public class Calculadora {



    public static int evaluarExpresion(String expresion) {
        Stack<Integer> operandosPila = new Stack<>();
        Stack<Character> operadoresPila = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            if (Character.isDigit(caracter)) {
                StringBuilder buffer = new StringBuilder();
                buffer.append(caracter);
                while (i + 1 < expresion.length() && Character.isDigit(expresion.charAt(i + 1))) {
                    buffer.append(expresion.charAt(++i));
                }
                operandosPila.push(Integer.parseInt(buffer.toString()));
            } else if (caracter == '(') {
                operadoresPila.push(caracter);
            } else if (caracter == ')') {
                while (operadoresPila.peek() != '(') {
                    operandosPila.push(realizarOperacion(operadoresPila.pop(), operandosPila.pop(), operandosPila.pop()));
                }
                operadoresPila.pop();
            } else if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/') {
                while (!operadoresPila.isEmpty() && obtenerordenPrecedencia(operadoresPila.peek()) >= obtenerordenPrecedencia(caracter)) {
                    operandosPila.push(realizarOperacion(operadoresPila.pop(), operandosPila.pop(), operandosPila.pop()));
                }
                operadoresPila.push(caracter);
            }
        }

        while (!operadoresPila.isEmpty()) {
            operandosPila.push(realizarOperacion(operadoresPila.pop(), operandosPila.pop(), operandosPila.pop()));
        }

        return operandosPila.pop();
    }

    public static TArbolBB arbolBBaux = new TArbolBB<>();
    public static int resultadoanterior;

    //--------------------------------------------------------------------------------------------------


    /*
    * OBtengo el orden de precedencia para evaluar que operacion debe hacerse primero
    * */
    private static int obtenerordenPrecedencia(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        }
        return 0;
    }

    private static void intercambiar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //O(N * Log n) -----------------------------------------------------------------------------------------------------
    private static void armaHeap(int[] datosParaClasificar, int primero, int ultimo) {
        if (primero < ultimo) {
            int actual = primero;
            while (actual <= ultimo / 2) {
                if (ultimo == 2 * actual) {
                    if (datosParaClasificar[actual] < (datosParaClasificar[actual * 2])) {
                        intercambiar(datosParaClasificar, actual, 2 * actual);
                    }
                    actual = ultimo;
                } else {
                    int posicionIntercambio = 0;
                    if (datosParaClasificar[2 * actual] < datosParaClasificar[2 * actual + 1]) {
                        posicionIntercambio = 2 * actual + 1;
                    } else {
                        posicionIntercambio = 2 * actual;
                    }
                    if (datosParaClasificar[actual] < (datosParaClasificar[posicionIntercambio])) {
                        intercambiar(datosParaClasificar, actual, posicionIntercambio);
                        actual = posicionIntercambio;
                    } else {
                        actual = ultimo;
                    }
                }
            }
        }
    }

    public static int[] ordenarPorHeapSort(int[] datosParaClasificar) {
        for (int i = (datosParaClasificar.length - 1) / 2; i >= 0; i--) {
            armaHeap(datosParaClasificar, i, datosParaClasificar.length - 1);
        }
        for (int i = datosParaClasificar.length - 1; i > 0; i--) {
            intercambiar(datosParaClasificar, 0, i);
            armaHeap(datosParaClasificar, 0, i - 1);
        }
        return datosParaClasificar;
    }
    //----------------------------------------------------------------------------------------------------

    //Realizo operaciones de suma,resta,division y multiplicación
    private static int realizarOperacion(char operador, int b, int a) {
        resultadoanterior = 0;
        arbolBBaux.vaciar();
        switch (operador) {
            case '+':
                arbolBBaux.insertar(new TElementoAB<>(operador,operador));
                arbolBBaux.getRaiz().setHijoIzq(new TElementoAB<>(a,a));
                arbolBBaux.getRaiz().setHijoDer(new TElementoAB<>(b,b));
                resultadoanterior=a+b;
                return a + b;
            case '-':
                arbolBBaux.insertar(new TElementoAB<>(operador,operador));
                arbolBBaux.getRaiz().setHijoIzq(new TElementoAB<>(a,a));
                arbolBBaux.getRaiz().setHijoDer(new TElementoAB<>(b,b));
                resultadoanterior=a-b;
                return a - b;
            case '*':
                arbolBBaux.insertar(new TElementoAB<>(operador,operador));
                arbolBBaux.getRaiz().setHijoIzq(new TElementoAB<>(a,a));
                arbolBBaux.getRaiz().setHijoDer(new TElementoAB<>(b,b));
                resultadoanterior=a*b;
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("No se puede dividir por cero");
                }
                arbolBBaux.insertar(new TElementoAB<>(operador,operador));
                arbolBBaux.getRaiz().setHijoIzq(new TElementoAB<>(a,a));
                arbolBBaux.getRaiz().setHijoDer(new TElementoAB<>(b,b));
                resultadoanterior=a/b;
                return a / b;
        }
        return 0;
    }


    //----------------------------------------------------------------------------------------------------
    public static class ArbolAnterior extends JFrame{
        private char raiz;  // Caracter en la raíz
        private String hijoIzquierdo;  // Caracter en el hijo izquierdo
        private String hijoDerecho;  // Caracter en el hijo derecho
        public ArbolAnterior(final char raiz, String hijoizq, String hijoder){
            super("ARBOLOPERACIONANTERIOR");

            this.raiz = raiz;
            this.hijoIzquierdo = hijoizq;
            this.hijoDerecho = hijoder;


        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Dibuja un grafo simple (puedes personalizar esto según tus necesidades)
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int circleRadius = 30;

                // Dibuja la raíz (círculo en el centro)
                g.drawOval(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius, 2 * circleRadius);
                g.drawString(String.valueOf(raiz), centerX - 5, centerY + 5);

                // Dibuja el hijo izquierdo
                int leftX = centerX - 100;
                int leftY = centerY + 100;
                g.drawOval(leftX - circleRadius, leftY - circleRadius, 2 * circleRadius, 2 * circleRadius);
                g.drawString(String.valueOf(hijoIzquierdo), leftX - 5, leftY + 5);

                // Calcular el punto de inicio de la línea en el borde del círculo de la raíz
                int startX = centerX - (int) (circleRadius * Math.cos(Math.PI / 4));  // Ángulo de 45 grados
                int startY = centerY + (int) (circleRadius * Math.sin(Math.PI / 4));
                g.drawLine(startX, startY, leftX + circleRadius, leftY - circleRadius);

                // Dibuja el hijo derecho
                int rightX = centerX + 100;
                int rightY = centerY + 100;
                g.drawOval(rightX - circleRadius, rightY - circleRadius, 2 * circleRadius, 2 * circleRadius);
                g.drawString(String.valueOf(hijoDerecho), rightX - 5, rightY + 5);

                // Calcular el punto de inicio de la línea en el borde del círculo de la raíz
                int endX = centerX + (int) (circleRadius * Math.cos(Math.PI / 4));  // Ángulo de 45 grados
                int endY = centerY + (int) (circleRadius * Math.sin(Math.PI / 4));
                g.drawLine(endX, endY, rightX - circleRadius, rightY - circleRadius);

                Font fuente = new Font("Comic Sans MS", Font.BOLD, 1);
                g.drawString("ARBOL Y RESULTADO OPERACION ANTERIOR = " + (resultadoanterior) , 60, 20);
                g.setFont(fuente);



            }

        };

        add(panel);
        setVisible(true);
    }}
    public static class CalculadoraGUI extends JFrame implements ActionListener {

        private Lista resultadosAnterioresTdaLista = new Lista();

        private JTextField campoTexto;
        private JButton[] botones;
        private String[] etiquetas = {
                "9", "8", "7", "6",
                "5", "4", "3", "2",
                "1", "0", "+", "-",
                "*", "(", ")", "/",
                "=","Borrar","Arbol"
        };
        public void MostrarArbolAnterior() {
            new ArbolAnterior((char)arbolBBaux.getRaiz().getEtiqueta(),String.valueOf(arbolBBaux.getRaiz().getHijoIzq().getEtiqueta()),String.valueOf(arbolBBaux.getRaiz().getHijoDer().getEtiqueta()));
        }


        //Diseño de la calculadora
        public CalculadoraGUI() {


            campoTexto = new JTextField();
            campoTexto.setFont(new Font("Times New Roman", Font.PLAIN, 60));
            campoTexto.setHorizontalAlignment(JTextField.CENTER);
            campoTexto.setEditable(false);


            botones = new JButton[etiquetas.length];
            for (int i = 0; i < etiquetas.length; i++) {
                botones[i] = new JButton(etiquetas[i]);
                botones[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
                botones[i].setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto en el botón
                botones[i].setBackground(Color.gray); // Establece el color de fondo del botón
                botones[i].setForeground(Color.black);
                botones[i].setBorder(new BevelBorder(1));
                botones[i].addActionListener(this);
            }

            JPanel panelBotones = new JPanel();
            panelBotones.setLayout(new GridLayout(5, 4, 3, 3));
            for (JButton boton : botones) {
                panelBotones.add(boton);
            }

            setLayout(new BorderLayout());
            add(campoTexto, BorderLayout.NORTH);
            add(panelBotones, BorderLayout.CENTER);

            setTitle("CalculadoraAlgoritmos");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setSize(1920, 1080);
            setLocationRelativeTo(null);
            setVisible(true);


        }

        //-----------------------------------------------------------------------------------------------


        //Funcionamiento de la calculadora y muestra de resultados anteriores por consola.

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
            String textoBoton = boton.getText();

            if (textoBoton.equals("Borrar")) {
                campoTexto.setText("");
                   
            } else if (textoBoton.equals("Arbol")) {
                MostrarArbolAnterior();
            } else if (textoBoton.equals("=")) {
                String expresion = campoTexto.getText();
                try {
                    int resultado = Calculadora.evaluarExpresion(expresion);
                    resultadosAnterioresTdaLista.insertar(new Nodo<>(resultado,resultado));
                    campoTexto.setText(String.valueOf(resultado));
                    while (resultadosAnterioresTdaLista.cantElementos() > 5) {
                        resultadosAnterioresTdaLista.eliminar(0);
                    }
                    // Muestra los resultados anteriores en la consola
                    System.out.println("Resultados anteriores:");
                    resultadosAnterioresTdaLista.imprimir();
                    System.out.println("Resultados anteriores ordenados:");
                    int[] resordenados = ordenarPorHeapSort(resultadosAnterioresTdaLista.obtenerArregloDeElementos());
                    for (int i:
                         resordenados) {
                        System.out.println(i);
                    }




                } catch (Exception ex) {
                    campoTexto.setText("Error");
                }
            } else {
                campoTexto.setText(campoTexto.getText() + textoBoton);
            }
        }




    }

    //------------------------------------------------------------------------------------------------


    //Ejecuto la interfaz de la calculadora
    public static void main(String[] args) {
        new CalculadoraGUI();
    }
}
