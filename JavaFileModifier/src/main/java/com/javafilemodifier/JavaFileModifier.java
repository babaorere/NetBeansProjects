package com.javafilemodifier;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaFileModifier {

    public static void main(String[] args) {
        System.out.print("Starting .");
        String directorioRaiz = ".";
        modificarArchivosJava(directorioRaiz);
        System.out.println("\n***   End   ***\n");
    }

    public static void modificarArchivosJava(String directorio) {
        try (Stream<Path> paths = Files.walk(Paths.get(directorio))) {
            paths.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")).forEach(path -> insertarCodigoAlFinalDeLaClase(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void insertarCodigoAlFinalDeLaClase(Path archivo) {
        System.out.print(".");
        try {
            CompilationUnit cu = StaticJavaParser.parse(archivo);
            cu.getClassByName(cu.getPrimaryTypeName().orElse("")).ifPresent(classDeclaration -> {
                // Verificar si el atributo "logger" ya existe en la clase
                boolean loggerExist = classDeclaration.getFields().stream().anyMatch(fieldDeclaration -> fieldDeclaration.getVariables().stream().anyMatch(variableDeclarator -> "logger".equals(variableDeclarator.getNameAsString())));
                if (!loggerExist) {
                    // Agregar las importaciones de Log4j
                    cu.addImport(new ImportDeclaration("org.apache.logging.log4j.LogManager", false, false));
                    cu.addImport(new ImportDeclaration("org.apache.logging.log4j.Logger", false, false));
                    // Agregar el campo logger
                    FieldDeclaration field = new FieldDeclaration();
                    VariableDeclarator variable = new VariableDeclarator(new ClassOrInterfaceType("Logger"), "logger");
                    field.getVariables().add(variable);
                    field.addModifier(com.github.javaparser.ast.Modifier.Keyword.PRIVATE, com.github.javaparser.ast.Modifier.Keyword.STATIC, com.github.javaparser.ast.Modifier.Keyword.FINAL);
                    variable.setInitializer("LogManager.getLogger(" + classDeclaration.getNameAsString() + ".class)");
                    classDeclaration.getMembers().addLast(field);
                    try {
                        Files.write(archivo, cu.toString().getBytes());
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Error: " + e);
            logger.error(e.toString());
        }
    }

    private static final Logger logger = LogManager.getLogger(JavaFileModifier.class);
}
