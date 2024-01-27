/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import connection.ConnCapip;
import java.io.File;
import java.io.FileInputStream;
import static java.lang.System.exit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import modelos.IvaAplicModel;
import modelos.PptoModel;

/**
 *
 * @author Capip Sistemas C.A.
 */
public final class CapipPropiedades {

    // Para determinar el sistema operativo, donde se ejecuta
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static final String CAPIP_PATH;
    public static final String CAPIP_PROPERTIES;

    static {
        if (isWindows()) {
            CAPIP_PATH = "C:/CAPIP_SISTEMAS";
            CAPIP_PROPERTIES = CAPIP_PATH + "/CAPIPSISTEMAS.PROPERTIES";
        } else if (isUnix()) {
            CAPIP_PATH = "/opt/CAPIP_SISTEMAS";
            CAPIP_PROPERTIES = CAPIP_PATH + "/CapipSistemas.properties";
        } else {
            CAPIP_PATH = "";
            CAPIP_PROPERTIES = CAPIP_PATH + "";
        }
    }

    public static final String CAPIP_SISTEMAS = "CANAIMA-SGG";
    public static final String CAPIP_CLIENTE_RAZONSOCIAL;
    public static final String CAPIP_CLIENTE_RIF_CI;
    public static final String CAPIP_CLIENTE_DOMICILIO_FISCAL;
    public static final String CAPIP_FECHA_LICENCIA;
    public static final String CAPIP_SERVIDOR;
    public static final String CAPIP_DIR_SERVIDOR;
    public static final String CAPIP_DIR_LOCAL;
    public static final String CAPIP_BASE_DATOS;
    public static final String CAPIP_USUARIO_BD;
    public static final String CAPIP_CLAVE_BD;

    public static final String CAPIP_NUM_PAGO;
    public static final String CAPIP_PATH_MYSQLDUMP;
    public static final Map<Long, PptoModel> CAPIP_IVA_PARTIDA;
    public static final Map<Long, IvaAplicModel> CAPIP_IVA_APLICADO;

    public static final String CAPIP_LINEA_1;
    public static final String CAPIP_LINEA_2;
    public static final String CAPIP_LINEA_3;
    public static final String CAPIP_LINEA_4;

    public static final String CAPIP_DESC_1;
    public static final String CAPIP_DESC_2;
    public static final String CAPIP_DESC_3;
    public static final String CAPIP_DESC_4;
    public static final String CAPIP_DESC_5;
    public static final String CAPIP_DESC_6;
    public static final String CAPIP_DESC_7;
    public static final String CAPIP_DESC_8;

    public static final String CAPIP_FUNC_1;
    public static final String CAPIP_FUNC_2;
    public static final String CAPIP_FUNC_3;
    public static final String CAPIP_FUNC_4;
    public static final String CAPIP_FUNC_5;
    public static final String CAPIP_FUNC_6;
    public static final String CAPIP_FUNC_7;
    public static final String CAPIP_FUNC_8;

    public static final String CAPIP_AUX_1;
    public static final String CAPIP_AUX_2;

    public static final String CAPIP_DIR_LOCAL_COMPROMISO;
    public static final String CAPIP_DIR_LOCAL_CAUSADO;
    public static final String CAPIP_DIR_LOCAL_IVA;
    public static final String CAPIP_DIR_LOCAL_ISLR;
    public static final String CAPIP_DIR_LOCAL_ORDENPAGO;

    public static final String CAPIP_DIR_SERV_COMPROMISO;
    public static final String CAPIP_DIR_SERV_CAUSADO;
    public static final String CAPIP_DIR_SERV_IVA;
    public static final String CAPIP_DIR_SERV_ISLR;
    public static final String CAPIP_DIR_SERV_ORDENPAGO;

    public static final SimpleDateFormat dateFormat;

    static {
        SimpleDateFormat auxDate;
        try {
            auxDate = new SimpleDateFormat("dd/MM/yyyy");
        } catch (final Exception inex) {
            auxDate = null;
        }

        dateFormat = auxDate;

        // verificar el archivo de propiedades
        File file_properties = null;
        try {

            // El archivo de propiedades, debe estar ubicado en el directorio por defecto
            file_properties = new File(CAPIP_PROPERTIES);

            if (!file_properties.exists()) {
                JOptionPane.showMessageDialog(null, "Error. Archivo de propiedades no encontrado"
                        + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ);
                exit(1);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de ubicar el archivo de Propiedades"
                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ
                    + System.getProperty("line.separator") + inex);
            exit(1);
        }

        // leer el archivo de propiedades
        Properties prop = new Properties();
        try (final FileInputStream is = new FileInputStream(file_properties)) {
            prop.loadFromXML(is);
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de leer el archivo de propiedades"
                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ
                    + System.getProperty("line.separator") + inex);
            exit(1);
        }

        try {
//            CpuInfo cpuinfo = new CpuInfo();
//            JOptionPane.showMessageDialog(null, "cpuinfo" + System.getProperty("line.separator") + Arrays.toString(cpuinfo.getCpuInfo().toString().getBytes()));
//            JOptionPane.showMessageDialog(null, new String(MessageDigest.getInstance("MD5").digest(new CpuInfo().getCpuInfo().toString().getBytes()), "UTF-8") + System.getProperty("line.separator")
//                + decrypt(prop.getProperty("CAPIP_LICENCIA")));

//            pw.println("Prop. Licencia " + prop.getProperty("CAPIP_LICENCIA"));
//            pw.println("Prop. Decrypt  " + decrypt(prop.getProperty("CAPIP_LICENCIA")));
//
//            pw.println("      Licencia " + new String(MessageDigest.getInstance("MD5").digest(new CpuInfo().getCpuInfo().toString().getBytes("UTF-8")), "UTF-8"));
            // verificar la licencia
//            if (!(new String(MessageDigest.getInstance("MD5").digest(new CpuInfo().getCpuInfo().toString().getBytes("UTF-8")), "UTF-8").equals(decrypt(prop.getProperty("CAPIP_LICENCIA"))))) {
//                JOptionPane.showMessageDialog(null, "Error. Archivo de Licencia no configurado"
//                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ);
//                pw.close();
//                exit(1);
//            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de comprobar la Licencia"
                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ
                    + System.getProperty("line.separator") + inex);
            exit(1);
        }

        // establecer las variables de configuracion globales
        CAPIP_CLIENTE_RAZONSOCIAL = decrypt(prop.getProperty("CAPIP_CLIENTE_RAZONSOCIAL"));
        CAPIP_CLIENTE_RIF_CI = decrypt(prop.getProperty("CAPIP_CLIENTE_RIF_CI"));

        CAPIP_CLIENTE_DOMICILIO_FISCAL = decrypt(prop.getProperty("CAPIP_CLIENTE_DOMICILIO_FISCAL"));
        CAPIP_FECHA_LICENCIA = decrypt(prop.getProperty("CAPIP_FECHA_LICENCIA"));
        CAPIP_SERVIDOR = decrypt(prop.getProperty("CAPIP_SERVIDOR"));
        CAPIP_DIR_SERVIDOR = decrypt(prop.getProperty("CAPIP_DIR_SERVIDOR"));
        CAPIP_DIR_LOCAL = decrypt(prop.getProperty("CAPIP_DIR_LOCAL"));
        CAPIP_BASE_DATOS = decrypt(prop.getProperty("CAPIP_BASE_DATOS"));
        CAPIP_USUARIO_BD = decrypt(prop.getProperty("CAPIP_USUARIO_BD"));
        CAPIP_CLAVE_BD = decrypt(prop.getProperty("CAPIP_CLAVE_BD"));

        CAPIP_NUM_PAGO = decrypt(prop.getProperty("CAPIP_NUM_PAGO", ""));
        CAPIP_PATH_MYSQLDUMP = decrypt(prop.getProperty("CAPIP_PATH_MYSQLDUMP", ""));

        CAPIP_IVA_APLICADO = new HashMap<>(101);
        CAPIP_IVA_PARTIDA = new HashMap<>(101);

        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM iva_aplicado ORDER BY id_iva_aplicado");
            while (rs.next()) {
                final IvaAplicModel reg = new IvaAplicModel(rs);
                final PptoModel regPpto = PptoModel.getReg_x_Id("presupe", reg.getId_part_ppto());
                CAPIP_IVA_APLICADO.put(reg.getId_iva_aplicado(), reg);
                CAPIP_IVA_PARTIDA.put(reg.getId_iva_aplicado(), regPpto);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, inex);
        }

        CAPIP_LINEA_1 = decrypt(prop.getProperty("CAPIP_LINEA_1", ""));
        CAPIP_LINEA_2 = decrypt(prop.getProperty("CAPIP_LINEA_2", ""));
        CAPIP_LINEA_3 = decrypt(prop.getProperty("CAPIP_LINEA_3", ""));
        CAPIP_LINEA_4 = decrypt(prop.getProperty("CAPIP_LINEA_4", ""));

        String func_1 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_1'")) {
            if (rs.next()) {
                func_1 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_1 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_2 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_2'")) {
            if (rs.next()) {
                func_2 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_2 no encontrado");
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_3 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_3'")) {
            if (rs.next()) {
                func_3 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_3 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_4 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_4'")) {
            if (rs.next()) {
                func_4 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_4 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_5 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_5'")) {
            if (rs.next()) {
                func_5 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_5 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_6 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_6'")) {
            if (rs.next()) {
                func_6 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_6 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_7 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_7'")) {
            if (rs.next()) {
                func_7 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_7 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        String func_8 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'FUNC_8'")) {
            if (rs.next()) {
                func_8 = rs.getString("valor");
            } else {
                throw new Exception("FUNC_8 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar " + System.getProperty("line.separator") + inex);
        }

        CAPIP_FUNC_1 = func_1;
        CAPIP_FUNC_2 = func_2;
        CAPIP_FUNC_3 = func_3;
        CAPIP_FUNC_4 = func_4;
        CAPIP_FUNC_5 = func_5;
        CAPIP_FUNC_6 = func_6;
        CAPIP_FUNC_7 = func_7;
        CAPIP_FUNC_8 = func_8;

        String desc_1 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_1'")) {
            if (rs.next()) {
                desc_1 = rs.getString("valor");
            } else {
                throw new Exception("DESC_1 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al caargar la DESC_1" + System.getProperty("line.separator") + inex);
        }

        String desc_2 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_2'")) {
            if (rs.next()) {
                desc_2 = rs.getString("valor");
            } else {
                throw new Exception("DESC_2 no encontrado");
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al caargar la DESC_2" + System.getProperty("line.separator") + inex);
        }

        String desc_3 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_3'")) {
            if (rs.next()) {
                desc_3 = rs.getString("valor");
            } else {
                throw new Exception("DESC_3 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al caargar la DESC_3" + System.getProperty("line.separator") + inex);
        }

        String desc_4 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_4'")) {
            if (rs.next()) {
                desc_4 = rs.getString("valor");
            } else {
                throw new Exception("DESC_4 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la DESC_4" + System.getProperty("line.separator") + inex);
        }

        String desc_5 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_5'")) {
            if (rs.next()) {
                desc_5 = rs.getString("valor");
            } else {
                throw new Exception("DESC_5 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la DESC_5" + System.getProperty("line.separator") + inex);
        }

        String desc_6 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_6'")) {
            if (rs.next()) {
                desc_6 = rs.getString("valor");
            } else {
                throw new Exception("DESC_6 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la DESC_6" + System.getProperty("line.separator") + inex);
        }

        String desc_7 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_7'")) {
            if (rs.next()) {
                desc_7 = rs.getString("valor");
            } else {
                throw new Exception("DESC_7 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la DESC_7" + System.getProperty("line.separator") + inex);
        }

        String desc_8 = "";
        try (ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'DESC_8'")) {
            if (rs.next()) {
                desc_8 = rs.getString("valor");
            } else {
                throw new Exception("DESC_8 no encontrado");
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al cargar la DESC_8" + System.getProperty("line.separator") + inex);
        }

        CAPIP_DESC_1 = desc_1;
        CAPIP_DESC_2 = desc_2;
        CAPIP_DESC_3 = desc_3;
        CAPIP_DESC_4 = desc_4;
        CAPIP_DESC_5 = desc_5;
        CAPIP_DESC_6 = desc_6;
        CAPIP_DESC_7 = desc_7;
        CAPIP_DESC_8 = desc_8;

        CAPIP_AUX_1 = decrypt(prop.getProperty("CAPIP_AUX_1", ""));
        CAPIP_AUX_2 = decrypt(prop.getProperty("CAPIP_AUX_2", ""));

        CAPIP_DIR_LOCAL_COMPROMISO = CAPIP_DIR_LOCAL + "/COMPROMISO";
        CAPIP_DIR_LOCAL_CAUSADO = CAPIP_DIR_LOCAL + "/CAUSADO";
        CAPIP_DIR_LOCAL_IVA = CAPIP_DIR_LOCAL + "/IVA";
        CAPIP_DIR_LOCAL_ISLR = CAPIP_DIR_LOCAL + "/ISLR";
        CAPIP_DIR_LOCAL_ORDENPAGO = CAPIP_DIR_LOCAL + "/ORDENPAGO";

        CAPIP_DIR_SERV_COMPROMISO = CAPIP_DIR_SERVIDOR + "/COMPROMISO";
        CAPIP_DIR_SERV_CAUSADO = CAPIP_DIR_SERVIDOR + "/CAUSADO";
        CAPIP_DIR_SERV_IVA = CAPIP_DIR_SERVIDOR + "/IVA";
        CAPIP_DIR_SERV_ISLR = CAPIP_DIR_SERVIDOR + "/ISLR";
        CAPIP_DIR_SERV_ORDENPAGO = CAPIP_DIR_SERVIDOR + "/ORDENPAGO";

        // Verificar la fecha de validez de la licencia
        try {

            if (getServerTimeStamp().after(dateFormat.parse(CAPIP_FECHA_LICENCIA))) {
                JOptionPane.showMessageDialog(null, "Error. Ha vencido el período de validez de la Licencia"
                        + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ);
                exit(1);
            }

        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error. No se ha podido comprobar la Licencia"
                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ
                    + System.getProperty("line.separator") + inex);
            exit(1);
        }

        chkDirs();

    }

    /**
     * Verifica la existencia de los directorios que son necesarios, para el funcionamiento de CAPIP Rev 22/11/2016
     */
    public static void chkDirs() {

        final File f0 = new File(CAPIP_DIR_LOCAL);
        if (!f0.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f0.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a finalizar el programa");
            System.exit(1);
        }

        final File f1 = new File(CAPIP_DIR_LOCAL_COMPROMISO);
        if (!f1.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f1.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a crearla");
            if (!f1.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f1.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f2 = new File(CAPIP_DIR_LOCAL_CAUSADO);
        if (!f2.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f2.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a crearla");
            if (!f2.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f2.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f3 = new File(CAPIP_DIR_LOCAL_IVA);
        if (!f3.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f3.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a crearla");
            if (!f3.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f3.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f4 = new File(CAPIP_DIR_LOCAL_ISLR);
        if (!f4.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f4.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a crearla");
            if (!f4.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f4.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }

        final File f5 = new File(CAPIP_DIR_LOCAL_ORDENPAGO);
        if (!f5.exists()) {
            JOptionPane.showMessageDialog(null, "La carpeta " + f5.getName() + "no existe" + System.getProperty("line.separator") + "Se procedera a crearla");
            if (!f5.mkdir()) {
                JOptionPane.showMessageDialog(null, "Error. No se pudo crear la Carpeta: " + f5.getName() + System.getProperty("line.separator") + "Debe crearla manualmente antes de continuar");
                System.exit(1);
            }
        }
    }

    /**
     *
     * @param instrToDecrypt
     * @return
     */
    private static String decrypt(final String instrToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf("45671357".getBytes(), 16), "AES"));
            return new String(cipher.doFinal(Base64.getDecoder().decode( instrToDecrypt)));
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de descifrar"
                    + System.getProperty("line.separator") + Globales.CAPIP_CONTACTE_MSJ
                    + System.getProperty("line.separator") + inex);
            exit(1);
        }

        return null;
    }

    /**
     * Rev 15/05/2017
     *
     * @return
     */
    public static java.sql.Timestamp getServerTimeStamp() {
        try {

            final ResultSet rs = ConnCapip.getInstance().getConnection().prepareStatement("SELECT NOW()").executeQuery();
            if (rs.next()) {
                return rs.getTimestamp(1);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar la fecha del Servidor: " + System.getProperty("line.separator") + inex);
            exit(1);
        }

        return null;
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

}
