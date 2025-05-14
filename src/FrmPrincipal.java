import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java_cup.runtime.Symbol;

public class FrmPrincipal extends JFrame {
    private JTextArea txtEntrada;
    private JTextArea txtResultado;
    private JButton btnAnalizar;

    public FrmPrincipal() {
        setTitle("Analizador de estructuras IF - JFlex y CUP");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(230, 240, 255)); // Azul pastel claro

        // entrada del area 
        JLabel lblEntrada = new JLabel("Código de entrada:");
        lblEntrada.setFont(fuente);
        txtEntrada = new JTextArea(10, 50);
        txtEntrada.setFont(fuente);
        txtEntrada.setLineWrap(true);
        txtEntrada.setWrapStyleWord(true);
        txtEntrada.setBackground(new Color(255, 255, 230)); // Fondo amarillo claro
        txtEntrada.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 200), 1));
        JScrollPane scrollEntrada = new JScrollPane(txtEntrada);

        // area para mostrar los resultados
        JLabel lblResultado = new JLabel("Resultado del análisis:");
        lblResultado.setFont(fuente);
        txtResultado = new JTextArea(10, 50);
        txtResultado.setFont(fuente);
        txtResultado.setEditable(false);
        txtResultado.setBackground(new Color(235, 255, 235)); // Verde claro
        txtResultado.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 1));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(230, 240, 255));
        JButton btnCargar = new JButton("Cargar archivo");
        btnCargar.setFont(fuente);
        btnCargar.setBackground(new Color(100, 150, 255));
        btnCargar.setForeground(Color.WHITE);

        btnAnalizar = new JButton("Analizar");
        btnAnalizar.setFont(fuente);
        btnAnalizar.setBackground(new Color(0, 180, 100));
        btnAnalizar.setForeground(Color.WHITE);

        panelBotones.add(btnCargar);
        panelBotones.add(btnAnalizar);

        // Acciones
        btnCargar.addActionListener(e -> cargarArchivo());
        btnAnalizar.addActionListener(e -> analizar());

        // Agregar componentes al panel principal
        mainPanel.add(lblEntrada);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(scrollEntrada);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(panelBotones);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblResultado);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(scrollResultado);

        add(mainPanel);
    }

    private void cargarArchivo() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                txtEntrada.read(br, null);
            } catch (IOException ex) {
                txtResultado.setText("Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    private void analizar() {
        String entrada = txtEntrada.getText();
        LexerCup lexer = new LexerCup(new StringReader(entrada));
        Sintax sintactico = new Sintax(lexer);
        try {
            sintactico.parse();
            txtResultado.setText("Análisis correcto. Sentencias válidas.");
        } catch (Exception ex) {
            txtResultado.setText("Error de análisis: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
}