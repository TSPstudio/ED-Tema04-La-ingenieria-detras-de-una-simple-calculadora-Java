import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraGUI {
    static Nodo raizActual;

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Calculadora - Arbol Binario");
        ventana.setSize(760, 480);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.add(crearContenido());
        ventana.setVisible(true);
    }

    static JPanel crearContenido() {
        JPanel contenedor = new JPanel(new BorderLayout());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel panelArbol = crearPanelArbol();

        JTextField cajaTexto = new JTextField(22);
        JButton boton = new JButton("Calcular");
        JLabel etiquetaResultado = new JLabel("Resultado: ");
        etiquetaResultado.setFont(new Font("SansSerif", Font.BOLD, 14));

        footer.add(new JLabel("Expresion:"));
        footer.add(cajaTexto);
        footer.add(boton);
        footer.add(etiquetaResultado);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                raizActual = Arboles_analisis.construirArbol(cajaTexto.getText());
                double resultado = Arboles_analisis.evaluar(raizActual);
                etiquetaResultado.setText("Resultado: " + resultado);
                panelArbol.repaint();
            }
        });

        contenedor.add(panelArbol, BorderLayout.CENTER);
        contenedor.add(footer, BorderLayout.SOUTH);
        return contenedor;
    }

    static JPanel crearPanelArbol() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (raizActual != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    dibujarArbol(g2, raizActual, getWidth() / 2, 36, getWidth() / 4);
                }
            }
        };
        panel.setBackground(new Color(248, 250, 252));
        return panel;
    }

    static void dibujarArbol(Graphics2D g, Nodo nodo, int x, int y, int dx) {
        int nextY = y + 58;
        int nextDx = Math.max(dx / 2, 24);
        if (nodo.izquierda != null) {
            g.setColor(new Color(71, 85, 105));
            g.drawLine(x, y, x - dx, nextY);
            dibujarArbol(g, nodo.izquierda, x - dx, nextY, nextDx);
        }
        if (nodo.derecha != null) {
            g.setColor(new Color(71, 85, 105));
            g.drawLine(x, y, x + dx, nextY);
            dibujarArbol(g, nodo.derecha, x + dx, nextY, nextDx);
        }
        dibujarNodo(g, nodo, x, y);
    }

    static void dibujarNodo(Graphics2D g, Nodo nodo, int x, int y) {
        int r = 18;
        boolean esHoja = nodo.izquierda == null && nodo.derecha == null;
        g.setColor(esHoja ? new Color(108, 170, 242) : new Color(219, 234, 254));
        g.fillOval(x - r, y - r, r * 2, r * 2);
        g.setColor(esHoja ? new Color(29, 78, 216) : new Color(59, 130, 246));
        g.drawOval(x - r, y - r, r * 2, r * 2);
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(nodo.valor, x - fm.stringWidth(nodo.valor) / 2, y + fm.getAscent() / 2 - 2);
    }
}
