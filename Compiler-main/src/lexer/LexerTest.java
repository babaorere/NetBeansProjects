package lexer;

import compilerTools.Token;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 */
public class LexerTest {

    public static void main(String[] args) throws IOException {
        // Crear un objeto Lexer
        Lexer lexer = new Lexer(new FileReader("inputTest.txt"));

        // Realizar el análisis léxico
        Token token;
        while ((token = lexer.yylex()) != null) {
            // Procesar o imprimir el token obtenido
            System.out.println(token);
        }

        // Cerrar el archivo de entrada
        lexer.yyclose();
    }

}
