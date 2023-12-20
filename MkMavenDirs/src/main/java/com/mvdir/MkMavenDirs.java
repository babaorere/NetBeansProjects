package com.mvdir;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Este programa es utilizado para generar un arbol de directorios, tipicos de una aplicacion MAVEN web
 *
 * @author manager
 */
public final class MkMavenDirs {

    private static final Logger logger = LogManager.getLogger(MkMavenDirs.class);

    // This is the directory from which we generate the directory tree
    // It should be noted that we must be at the root of the project     
    private final static String WORKING_DIR = System.getProperty("user.dir");

    private static String[] getMainPackageName() {
        String className = getMainClassName();
        if (className != null) {
            int lastIndex = className.lastIndexOf('.');
            if (lastIndex != -1) {
                return className.substring(0, lastIndex).split("\\.");
            }
        }

        return null;
    }

    private static String getMainClassName() {
        String mainClass = System.getProperty("sun.java.command");
        if (mainClass != null) {
            int spaceIndex = mainClass.indexOf(' ');
            if (spaceIndex != -1) {
                return mainClass.substring(0, spaceIndex);
            }
            return mainClass;
        }
        return null;
    }

    public static void main(String[] args) {

        if (validateMavenProject(WORKING_DIR)) {
            System.out.println("\nThis directory contains a valid Maven project.");
        } else {
            System.out.println("\nThis directory does not contain a valid Maven project.");
        }

        int li = WORKING_DIR.lastIndexOf("/");
        // Creamos la estructura de directorios
        Directory myApplication = new Directory(WORKING_DIR.substring(li + 1), "This is the root directory of the project.");

        Directory src = new Directory("src", "This directory contains the source code of the project.");
        myApplication.add(src);
        Directory main = new Directory("main", "This directory contains the main source code of the application.");
        src.add(main);
        Directory javaDir = new Directory("java", "Java source code files go here.");
        main.add(javaDir);

        // Compañía y paquete 
        String[] pkgList = getMainPackageName();
        Directory beginVPackage = new Directory(pkgList[0], null);
        javaDir.add(beginVPackage);

        Directory endPakage = beginVPackage;

        for (int i = 1; i < pkgList.length; i++) {
            Directory nextDir = new Directory(pkgList[i], null);
            endPakage.add(nextDir);
            endPakage = nextDir;
        }

        // Paquetes
        Directory controller = new Directory("controller", "Controllers for handling HTTP requests.");
        Directory dto = new Directory("dto", "Data Transfer Objects for API communication.");
        Directory entity = new Directory("entity", "JPA Entities for database mapping.");
        Directory repository = new Directory("repository", "Repository Interfaces for data access.");
        Directory service = new Directory("service", "Services for business logic.");
        Directory security = new Directory("security", "Security configuration.");
        endPakage.add(controller);
        endPakage.add(dto);
        endPakage.add(entity);
        endPakage.add(repository);
        endPakage.add(service);
        endPakage.add(security);

        Directory impl = new Directory("impl", "Implementation classes for service interfaces.");
        service.add(impl);

        Directory config = new Directory("config", "Security configuration classes.");
        Directory model = new Directory("model", "Security-related model classes.");
        Directory serviceSecurity = new Directory("service", "Security service classes.");
        security.add(config);
        security.add(model);
        security.add(serviceSecurity);

        // Recursos
        Directory resources = new Directory("resources", "Resource files for the application.");
        javaDir.add(resources);

        Directory staticResources = new Directory("static", "Static resources.");
        resources.add(staticResources);

        Directory css = new Directory("css", "CSS Stylesheets.");
        Directory js = new Directory("js", "JavaScript Files.");
        Directory img = new Directory("img", "Image Files.");
        staticResources.add(css);
        staticResources.add(js);
        staticResources.add(img);

        Directory templates = new Directory("templates", "Template files (e.g., Thymeleaf templates).");
        Directory metaInf = new Directory("META-INF", "META-INF files.");
        String propStr = """
                        # File example application.properties
                        database.url=jdbc:mysql://localhost:3306/mydb
                        database.user=root
                        database.password=secretpassword
                        max_connections=100
                        """;
        FileHandlerImpl applicationProperties = new FileHandlerImpl("application.properties", propStr);
        resources.add(applicationProperties);

        resources.add(templates);
        resources.add(metaInf);
        String persXML = """
                        <!-- JPA/Hibernate Configuration. -->
                        <?xml version="1.0" encoding="UTF-8"?>
                        <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
                                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                     xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                                     http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
                                     version="2.2">
                            <persistence-unit name="my-persistence-unit" transaction-type="RESOURCE_LOCAL">
                                <!-- Proveedor de JPA (aquí se utiliza Hibernate como ejemplo) -->
                                <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
                        
                                <!-- Clases de entidad -->
                                <class>com.mycompany.model.User</class>
                                <class>com.mycompany.model.Product</class>
                        
                                <!-- Configuración de la fuente de datos (DataSource) -->
                                <properties>
                                    <!-- Configura la URL de la base de datos -->
                                    <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/mydb"/>
                        
                                    <!-- Configura el nombre de usuario y contraseña -->
                                    <property name="javax.persistence.jdbc.user" value="root"/>
                                    <property name="javax.persistence.jdbc.password" value="secretpassword"/>
                        
                                    <!-- Configura el dialecto de la base de datos (para Hibernate) -->
                                    <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
                        
                                    <!-- Otros ajustes de Hibernate (opcional) -->
                                    <property name="hibernate.show_sql" value="true"/>
                                    <property name="hibernate.hbm2ddl.auto" value="update"/>
                                </properties>
                            </persistence-unit>
                        </persistence>                        
                        """;
        FileHandlerImpl persistenceXml = new FileHandlerImpl("persistence.xml", persXML);
        metaInf.add(persistenceXml);

        // Directorio webapp
        Directory webapp = new Directory("webapp", "Web application files.");
        src.add(webapp);

        Directory webInf = new Directory("WEB-INF", "Web application configuration.");
        webapp.add(webInf);

        Directory jsp = new Directory("jsp", "JSP Views.");
        Directory lib = new Directory("lib", "Third-party JAR Libraries.");
        Directory views = new Directory("views", "HTML Views.");
        Directory webXml = new Directory("web.xml", "Web Configuration File.");
        webInf.add(jsp);
        webInf.add(lib);
        webInf.add(views);
        webInf.add(webXml);

        Directory target = new Directory("target", "This directory contains the results of the compilation and construction of the project are stored.");
        myApplication.add(target);

        // Directorio de pruebas
        Directory test = new Directory("test", "Test files and resources.");
        src.add(test);
        Directory testJava = new Directory("java", "Test Java source code files.");
        Directory testResources = new Directory("resources", "Test resource files.");
        test.add(testJava);
        test.add(testResources);

        myApplication.write(WORKING_DIR.substring(0, li));

        System.out.println("Directory structure created successfully.");
    }

    private static void createReadmeFile(File inDir, String content) {

        if ((inDir == null) || (content == null) || content.isEmpty()) {
            return;
        }

        if (inDir.isDirectory()) {
            // Crea el archivo _readme.md en el directorio especificado
            File readmeFile = new File(inDir, "_readme.md");
            try (FileWriter writer = new FileWriter(readmeFile)) {
                writer.write(content);
            } catch (IOException e) {
                logger.error("An exception occurred:", e);
            }
        }
    }

    private static void createFile(File inFile, String inContent) {
        if ((inFile == null) || (inContent == null) || inContent.isEmpty()) {
            return;
        }

        if (inFile.isFile()) {
            // This is a file, not a directory.
            try (FileWriter writer = new FileWriter(inFile)) {
                writer.write(inContent);
            } catch (IOException e) {
                logger.error("An exception occurred:", e);
            }
        }
    }

    private static boolean validateMavenProject(String directoryPath) {
        File pomFile = new File(directoryPath, "pom.xml");

        if (!pomFile.exists() || !pomFile.isFile()) {
            // The pom.xml file does not exist or is not a regular file.
            return false;
        }

        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model;

            try (FileReader fileReader = new FileReader(pomFile)) {
                model = reader.read(fileReader);
            }

            // If the model is successfully parsed, it's a Maven project.
            return model != null;
        } catch (IOException | XmlPullParserException e) {
            logger.error("An exception occurred:", e);
            return false;
        }
    }
}
