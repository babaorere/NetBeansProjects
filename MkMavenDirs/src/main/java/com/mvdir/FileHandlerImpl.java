/*
 */
package com.mvdir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author manager
 */
public class FileHandlerImpl implements FileHandler {

    // Create a read-only view of the list
    private static final List<FileHandler> inmutableList = Collections.unmodifiableList(new ArrayList<>());

    // Create the tracking log
    private static final Logger logger = LogManager.getLogger(MkMavenDirs.class);

    private final String name;
    private final String content;
    private List<FileHandler> list;

    // Constructor
    public FileHandlerImpl(String inNameDir, String inContent) {

        if (inNameDir == null) {
            this.name = "";
        } else {
            this.name = inNameDir;
        }

        if (inContent == null) {
            this.content = "";
        } else {
            this.content = inContent;
        }

        this.list = inmutableList;
    }

    //
    // Getters y setters
    //
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<FileHandler> getList() {
        return list;
    }

    /**
     * @param inList
     */
    public void setList(List<FileHandler> inList) {
        if (inList == null) {
            this.list = inmutableList;
        } else {
            this.list = inList;
        }
    }

    @Override
    public boolean add(FileHandler inFile) {
        return false;
    }

    @Override
    public boolean write(String inBasePath) {
        if (inBasePath == null || inBasePath.isEmpty() || this.getContent() == null) {
            return false;
        }

        File file = new File(inBasePath + "/" + this.getName());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(this.getContent());
        } catch (IOException e) {
            getLogger().error("An exception occurred:", e);
            return false;
        }

        boolean[] success = {true}; // We use an array to avoid effective finality

        this.getList().forEach(auxFile -> {
            try {
                if (!auxFile.write(file.getCanonicalPath())) {
                    success[0] = false;
                }
            } catch (IOException e) {
                getLogger().error("An exception occurred:", e);
                success[0] = false; // Modificamos el valor del array
            }
        });

        return success[0];
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    @Override
    public String toString() {
        return getName();
    }

}
