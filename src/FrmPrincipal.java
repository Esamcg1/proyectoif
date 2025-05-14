    import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java_cup.runtime.Symbol;

public class FrmPrincipal extends JFrame {
    private JTextArea txtEntrada;
    private JTextArea txtResultado;
    private JButton btnAnalizar;

    public FrmPrincipal() {
        setTitle("Analizador if - JFlex y CUP");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtEntrada = new JTextArea(10, 50);
        txtResultado = new JTextArea(10, 50);
        txtResultado.setEditable(false);

        btnAnalizar = new JButton("Analizar");

        btnAnalizar.addActionListener(e -> analizar());

        JButton btnCargar = new JButton("Cargar archivo");
        btnCargar.addActionListener(e -> cargarArchivo());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCargar);
        panelBotones.add(btnAnalizar);

        add(new JScrollPane(txtEntrada), BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(new JScrollPane(txtResultado), BorderLayout.SOUTH);
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