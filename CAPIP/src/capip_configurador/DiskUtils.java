/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capip_configurador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

public class DiskUtils {

    private DiskUtils() {
        super();
    }

    public static String getSerialNumber(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                + "Set colDrives = objFSO.Drives\n"
                + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (final Exception _ex) {
            JOptionPane.showMessageDialog(null, _ex);
        }
        return result.trim();
    }

    public static void main(String[] args) {

        java.util.BitSet z;

        z = new java.util.BitSet();

        int a = 1;

        int b = ~a;

//        if (JOptionPane.showConfirmDialog(null, "¿Desea actualizar la licencia ?",
//            "Confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
//            final String sn = DiskUtils.getSerialNumber("C");
//
//            final byte[] pass;
//            try {
//                pass = MessageDigest.getInstance("MD5").digest(sn.getBytes());
//            } catch (NoSuchAlgorithmException _ex) {
//                JOptionPane.showMessageDialog(null, _ex);
//                return;
//            }
//
//            javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, sn, "Serial Number of C:",
//                javax.swing.JOptionPane.DEFAULT_OPTION);
//            try {
//                final ResultSet rs = Conn.executeQuery("SELECT * FROM global WHERE nombre= 'licencia' LOCK IN SHARE MODE");
//                if (rs.next()) {
//                    final PreparedStatement pst = Conn.getConnection().prepareStatement("UPDATE global set valor= ?, dato= ?, iduser= ? WHERE nombre= ?");
//                    pst.setString(1, new String(pass, "UTF-8"));
//                    pst.setBytes(2, pass);
//                    pst.setLong(3, UserPassIn.getIdUser());
//                    pst.setString(4, "licencia");
//
//                    if (pst.executeUpdate() != 1) {
//                        JOptionPane.showMessageDialog(null, "Problemas al actualizar la licencia");
//                    }
//                } else {
//                    final PreparedStatement pst = Conn.getConnection().prepareStatement("INSERT INTO globales(nombre,valor,iduser) VALUES (?,?,?)");
//                    pst.setString(1, "licencia");
//                    pst.setString(2, new String(pass, "UTF-8"));
//                    pst.setLong(3, UserPassIn.getIdUser());
//
//                    if (pst.executeUpdate() != 1) {
//                        JOptionPane.showMessageDialog(null, "Problemas al actualizar la licencia");
//                    }
//                }
//            } catch (final Exception _ex) {
//                JOptionPane.showMessageDialog(null, _ex);
//            }
//        }
    }

//    private static final Connection cn = Conn.getConnection();
}
