package com.mvdir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Directory extends FileHandlerImpl {

    // Constructor
    public Directory(String inNameDir, String inContent) {
        super(inNameDir, inContent);
        setList(new ArrayList<>());
    }

    @Override
    public boolean write(String inBasePath) {
        if (inBasePath == null || inBasePath.isEmpty() || this.getContent() == null) {
            return false;
        }

        Path auxPath = Paths.get(inBasePath, this.getName());

        File file = new File(auxPath.toString());
        if (!file.exists()) {
            try {
                Files.createDirectory(auxPath);
            } catch (Exception e) {
                getLogger().error("An exception occurred:", e);
            }
        }

        try {
            if (!getContent().isEmpty()) {
                // Crea el archivo _readme.md en el directorio especificado
                File readmeFile = new File(auxPath.toString(), "_readme.md");
                try (FileWriter writer = new FileWriter(readmeFile)) {
                    writer.write(getContent());
                }
            }
        } catch (Exception e) {
            getLogger().error("An exception occurred:", e);
        }

        boolean[] success = {true}; // We use an array to avoid effective finality

        this.getList().forEach(auxFile -> {
            if (!auxFile.write(auxPath.toString())) {
                success[0] = false;
            }
        });

        return success[0];
    }

    @Override
    public boolean add(FileHandler inFile) {
        return getList().add(inFile);
    }

}
