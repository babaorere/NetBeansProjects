package com.app;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ModCatch {

    public static void main(String[] args) {
        String directorioRaiz = ".";
        modificarArchivosJava(directorioRaiz);
    }

    public static void modificarArchivosJava(String directorio) {
        try {
            Files.walk(Paths.get(directorio)).filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")).forEach(path -> insertarLoggerErrorEnCatch(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertarLoggerErrorEnCatch(Path archivo) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(archivo);
            new CatchClauseVisitor().visit(cu, null);
            Files.write(archivo, cu.toString().getBytes());
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private static class CatchClauseVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(TryStmt n, Void arg) {
            super.visit(n, arg);
            List<CatchClause> catchClauses = n.getCatchClauses();
            for (CatchClause catchClause : catchClauses) {
                BlockStmt catchBlock = catchClause.getBody();
                String varError = catchClause.getParameter().getNameAsString();
                // Crear una expresión para logger.error(varError)
                ExpressionStmt loggerStmt = (ExpressionStmt) StaticJavaParser.parseStatement("logger.error(" + varError + ");");
                // Agregar logger.error(varError) al bloque catch
                catchBlock.addStatement(loggerStmt);
            }
        }
    }
}
