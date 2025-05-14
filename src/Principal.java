import java.io.File;

public class Principal {
    public static void main(String[] args) {
        try {
            String rutaLexer = "src/LexerCup.flex";
            String rutaCup[] = {"-parser", "Sintax", "src/Sintax.cup"};
            //jflex.Main.generate(new File(rutaLexer));
            jflex.Main.main(new String[]{rutaLexer});
            java_cup.Main.main(rutaCup);
            System.out.println("Análisis generado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}