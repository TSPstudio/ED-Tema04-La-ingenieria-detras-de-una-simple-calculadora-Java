import java.util.Scanner;

public class Arboles_analisis {
    static int pos;
    static String expr;

    public static void main(String[] args) {
        Nodo tres = new Nodo("3");
        Nodo cinco = new Nodo("5");
        Nodo dos = new Nodo("2");

        Nodo suma = new Nodo("+");
        suma.izquierda = tres;
        suma.derecha = cinco;

        Nodo raiz = new Nodo("*");
        raiz.izquierda = suma;
        raiz.derecha = dos;

        double resultado = evaluar(raiz);
        System.out.println("Resultado de (3+5)*2 = " + resultado);

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingresa una expresion: ");
        if (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (!entrada.isBlank()) {
                Nodo arbol = construirArbol(entrada);
                System.out.println("Resultado = " + evaluar(arbol));
            }
        }
    }

    public static double evaluar(Nodo nodo) {
        if (nodo.izquierda == null && nodo.derecha == null) {
            return Double.parseDouble(nodo.valor);
        }
        double izquierda = evaluar(nodo.izquierda);
        double derecha = evaluar(nodo.derecha);
        switch (nodo.valor) {
            case "+":
                return izquierda + derecha;
            case "-":
                return izquierda - derecha;
            case "*":
                return izquierda * derecha;
            case "/":
                if (derecha == 0) {
                    throw new ArithmeticException("No se puede dividir entre cero");
                }
                return izquierda / derecha;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + nodo.valor);
        }
    }

    public static Nodo construirArbol(String texto) {
        expr = texto.replace(" ", "");
        pos = 0;
        return parseExpresion();
    }

    static Nodo parseExpresion() {
        Nodo nodo = parseTermino();
        while (pos < expr.length() && (expr.charAt(pos) == '+' || expr.charAt(pos) == '-')) {
            String op = String.valueOf(expr.charAt(pos++));
            Nodo padre = new Nodo(op);
            padre.izquierda = nodo;
            padre.derecha = parseTermino();
            nodo = padre;
        }
        return nodo;
    }

    static Nodo parseTermino() {
        Nodo nodo = parseFactor();
        while (pos < expr.length() && (expr.charAt(pos) == '*' || expr.charAt(pos) == '/')) {
            String op = String.valueOf(expr.charAt(pos++));
            Nodo padre = new Nodo(op);
            padre.izquierda = nodo;
            padre.derecha = parseFactor();
            nodo = padre;
        }
        return nodo;
    }

    static Nodo parseFactor() {
        if (expr.charAt(pos) == '(') {
            pos++;
            Nodo nodo = parseExpresion();
            pos++;
            return nodo;
        }
        int inicio = pos;
        if (expr.charAt(pos) == '-') {
            pos++;
        }
        while (pos < expr.length() && (Character.isDigit(expr.charAt(pos)) || expr.charAt(pos) == '.')) {
            pos++;
        }
        return new Nodo(expr.substring(inicio, pos));
    }
}
