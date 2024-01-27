package com.compilador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CompiladorBasicoJava {

    static int reservadas = 0, id = 0, pizq = 0, pder = 0, llaIz = 0, llaDe = 0, igualdad = 0, operArit = 0, operRel = 0;

    static ArrayList<String> resList;

    static int contLinea = 0;

    static {

        String[] aux = {"abstract",
            "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
            "native", "new", "null", "package", "private", "protected", "public", "return", "short",
            "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient",
            "true", "try", "void", "volatile", "while"};

        resList = new ArrayList<>(Arrays.asList(aux));
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileWriter fileWriter;
        BufferedWriter writer = null;

        String fileName;
        if (args.length != 1) {
            System.out.println("Puede proporcionar el nombre de archivo como un parametro.");
            fileName = "src/main/java/calculadora/CALCULADORA.java";
        } else {
            fileName = args[0];
        }

        try {
            fileWriter = new FileWriter(new File("nombrearchivo_errores.txt"));
            writer = new BufferedWriter(fileWriter);

            ArrayList<Token> list;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                list = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    list.addAll(separadorToken(line));
                }
            }

            for (Token t : list) {
                System.out.println(t);
            }

            int idx = fileName.lastIndexOf("/");

            // quitar el path
            String nombrePaquete;
            if (idx < 0) {
                nombrePaquete = fileName;
            } else {
                nombrePaquete = fileName.substring(idx + 1);
            }

            // quitar la extension
            idx = nombrePaquete.lastIndexOf(".");
            if (idx >= 0) {
                nombrePaquete = nombrePaquete.substring(0, idx);
            }

            nombrePaquete = nombrePaquete.toLowerCase();

            // Verificar el paquete
            int ntoken = 0;

            // Nos saltamos las lineas en blanco
            while (list.get(ntoken).getType() == TokenType.SALTOLINEA) {
                ntoken++;
            }

            if (list.get(ntoken).getValue().equals("package")) {
                // pasamos al siguiente token
                ntoken++;
                if (list.get(ntoken).getValue().equals(nombrePaquete)) {
                    // saltamos el ;
                    ntoken++;

                    if (list.get(ntoken).getType() != TokenType.PUNTOCOMA) {
                        String err1 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                        System.out.println(err1);
                        writer.write(err1);
                    }

                } else {
                    String err1 = "\nlinea " + list.get(ntoken).getNlinea() + "Error. No corresponde el nombre del paquete";
                    System.out.println(err1);
                    writer.write(err1);

                    // seguimos hasta salto de linea
                    while (list.get(ntoken).getType() == TokenType.SALTOLINEA
                            || list.get(ntoken).getType() == TokenType.PUNTOCOMA) {

                        if (list.get(ntoken).getType() == TokenType.SALTOLINEA
                                && list.get(ntoken - 1).getType() != TokenType.PUNTOCOMA
                                && list.get(ntoken - 1).getType() != TokenType.SALTOLINEA) {
                            String err2 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                            System.out.println(err2);
                            writer.write(err2);
                        }

                        ntoken++;
                    }

                }
            }

            // seguimos hasta salto de linea
            while (list.get(ntoken).getType() == TokenType.SALTOLINEA
                    || list.get(ntoken).getType() == TokenType.PUNTOCOMA) {
                if (list.get(ntoken).getType() == TokenType.SALTOLINEA
                        && list.get(ntoken - 1).getType() != TokenType.PUNTOCOMA
                        && list.get(ntoken - 1).getType() != TokenType.SALTOLINEA) {
                    String err2 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                    System.out.println(err2);
                    writer.write(err2);
                }
                ntoken++;
            }

            System.out.println(list.get(ntoken).getValue().trim());

            boolean continuar = true;

            while (continuar) {

                if (list.get(ntoken).getValue().trim().equals("import")) {

                    // seguimos hasta salto de linea
                    while (list.get(ntoken).getType() != TokenType.SALTOLINEA
                            && list.get(ntoken).getType() != TokenType.PUNTOCOMA) {
                        if (list.get(ntoken).getType() == TokenType.SALTOLINEA
                                && list.get(ntoken - 1).getType() != TokenType.PUNTOCOMA
                                && list.get(ntoken - 1).getType() != TokenType.SALTOLINEA) {
                            String err2 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                            System.out.println(err2);
                            writer.write(err2);
                        }
                        ntoken++;
                    }
                } else {
                    continuar = false;
                }
            }

            // seguimos hasta salto de linea
            while (list.get(ntoken).getType() == TokenType.SALTOLINEA
                    || list.get(ntoken).getType() == TokenType.PUNTOCOMA) {
                if (list.get(ntoken).getType() == TokenType.SALTOLINEA
                        && list.get(ntoken - 1).getType() != TokenType.PUNTOCOMA
                        && list.get(ntoken - 1).getType() != TokenType.SALTOLINEA) {
                    String err2 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                    System.out.println(err2);
                    writer.write(err2);
                }
                ntoken++;
            }

            String aux2 = list.get(ntoken).getValue();
            ntoken++;

            aux2 += " " + list.get(ntoken).getValue();
            ntoken++;

            aux2 += " " + list.get(ntoken).getValue();
            ntoken++;

            System.out.println(aux2);

            String aux3 = "public class " + nombrePaquete;

            String err;
            if (!aux2.equalsIgnoreCase(aux3)) {
                err = "\nlinea " + list.get(ntoken).getNlinea() + "Error se esperaba " + aux3;
                System.out.println(err);
                writer.write(err);
            }

            // seguimos hasta salto de linea
            while (list.get(ntoken).getType() == TokenType.SALTOLINEA
                    || list.get(ntoken).getType() == TokenType.PUNTOCOMA) {
                if (list.get(ntoken).getType() == TokenType.SALTOLINEA
                        && list.get(ntoken - 1).getType() != TokenType.PUNTOCOMA
                        && list.get(ntoken - 1).getType() != TokenType.SALTOLINEA) {
                    String err2 = "\nlinea " + list.get(ntoken).getNlinea() + ". Se esperaba ;";
                    System.out.println(err2);
                    writer.write(err2);
                }
                ntoken++;
            }

            aux2 = list.get(ntoken).getValue();
            ntoken++;

            for (int i = 0; i < 9; i++) {
                aux2 += " " + list.get(ntoken).getValue();
                ntoken++;
            }

            System.out.println(aux2);

            aux3 = "public static void main ( String [ ] args )";
            if (!aux2.equalsIgnoreCase(aux3)) {
                err = "\nlinea " + list.get(ntoken).getNlinea() + "Error se esperaba " + aux3;
                System.out.println(err);
                writer.write(err);
            }

            System.out.println(list.get(ntoken).getValue().trim());

        } catch (IOException e) {
            String err = "Error al tratar de abrir el archivo: " + fileName;
            System.out.println(err);
            if (writer != null) {
                try {
                    writer.write(err);
                } catch (IOException ex) {
                }
                writer = null;
            }
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ex) {
            }
        }

        System.out.println("Estadisticas finales");
        System.out.println("palabras reservadas   " + reservadas);
        System.out.println("identificadores       " + id);
        System.out.println("paréntesis izquierdos " + pizq);
        System.out.println("paréntesis derechos   " + pder);
        System.out.println("llaves izquierdas     " + llaIz);
        System.out.println("llaves derechas       " + llaDe);
        System.out.println("signo de igualdad     " + igualdad);
        System.out.println("operadores aritméticos  " + operArit);
        System.out.println("operadores relacionales " + operRel);

    }

    private static boolean comentario = false;

    /**
     *
     */
    private static ArrayList<Token> separadorToken(String line) {

        // Recortar la linea a un maximo de 80
        line = line.substring(0, (int) min(line.length(), 80));

        ArrayList<Token> auxList = new ArrayList<>();
        ArrayList<Token> tokenList = new ArrayList<>();

        // Este patron se consiguio probando y probando, es mejorable
        String pattern = "\\r|;|,|:|\\b(\\w+)\\b|\"|[+\\-*/%]|\\(|\\)|\\[|\\]|\\{|\\}|=|==|<=|>=|!=|&&";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        while (m.find()) {
            auxList.add(determineTokenType(m.group(0)));
        }

        // Agregamos un salto de linea
        auxList.add(new Token(TokenType.SALTOLINEA, "\n", ++contLinea));

        // Eliminar comentarios
        if (auxList.size() < 2) {
            return auxList;
        }

        for (int i = 0; i < auxList.size() - 2;) {

            String aux = auxList.get(i).getValue() + auxList.get(i + 1).getValue();
            if (!comentario && aux.equals("/*")) {
                comentario = true;
                i += 2;
                continue;
            } else if (comentario && aux.equals("*/")) {
                comentario = false;
                i += 2;
                continue;
            } else if (!comentario && aux.equals("//")) {
                comentario = false;
                i += 2;
                break;
            }

            if (!comentario) {
                tokenList.add(auxList.get(i));
            }

            i++;
        }

        return tokenList;
    }

    private static Token determineTokenType(String text) {

        switch (text) {

            case "(":
                pizq++;
                break;
            case ")":
                pder++;
                break;
            case "{":
                llaIz++;
                break;
            case "}":
                llaDe++;
                break;
            case "==":
                igualdad++;
                break;

            case "+":
            case "-":
            case "*":
            case "/":
                operArit++;
                break;

            case "&&":
            case "||":
            case "<":
            case "<=":
            case ">=":
            case ">":
                operRel++;
                break;

            default:
                if (resList.contains(text)) {
                    reservadas++;
                } else {
                    id++;
                }
        }

        Token t;

        switch (text) {
            case ";":
                t = new Token(TokenType.PUNTOCOMA, text, contLinea);
                break;

            default:
                t = new Token(TokenType.UNKNOWN, text, contLinea);
        }

        return t;
    }

}
