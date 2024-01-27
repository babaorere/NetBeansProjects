package com.principal.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 */
public class SimpleTest {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static String OSArch = System.getProperty("os.arch").toLowerCase();

    private static String OSVersion = System.getProperty("os.version").toLowerCase();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print("Sistema operativo: ");
        System.out.println(OS);
        if (isWindows()) {
            System.out.println("Es un Windows");
        } else if (isMac()) {
            System.out.println("Es un Mac");
        } else if (isUnix()) {
            System.out.println("Es un Unix/Linux");
        } else if (isSolaris()) {
            System.out.println("Es Solaris");
        } else {
            System.out.println("Sistema operativo no reconocido!!");
        }
        System.out.println("Version: " + OSVersion);
        System.out.println("Aquitectura: " + OSArch);
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    private static final Logger logger = LogManager.getLogger(SimpleTest.class);
}
