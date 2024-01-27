package com.principal.test;

import com.principal.capipsistema.FrmPrincipal;
import com.principal.capipsistema.Propiedades;
import com.principal.compromisos.Compromiso;
import com.principal.connection.ConnCapip;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author
 */
public class testJaspert {

    private static final Logger logger = LogManager.getLogger(Compromiso.class);

    public static void main(String[] args) {

        Map<String, Object> param = new HashMap<>(101);

        testJaspert test = new testJaspert();

        try {
            test.genReport(1, param, false);
        } catch (Exception inex) {
            System.out.println("Error: " + inex);
        }

    }

    // Default constructor
    public testJaspert() {
    }

    public void genReport(final long inid, final Map<String, Object> inparam, final boolean inexport) throws Exception {

        final Map<String, Object> param = new HashMap<>(101);
        param.put("benef_domicilio", "1");
        param.put("benef_razonsocial", "1");
        param.put("benef_rif_ci", "1");
        param.put("benef_telefonos", "1");
        param.put("date_session", "01/01/2023");
        param.put("ejefis", "2023");
        param.put("fechacompr", "01/01/2023");
        param.put("fechafact", "01/01/2023");
        param.put("id_user", "1");
        param.put("id_session", "1");
        param.put("numfact", "1");
        final String NumLiteral = "1";
        final String NumLiteral_1;
        final String NumLiteral_2;
        if (NumLiteral.length() < 65) {
            NumLiteral_1 = NumLiteral;
            NumLiteral_2 = "";
        } else {
            int pos = NumLiteral.lastIndexOf(" CON ");
            final String s1 = NumLiteral.substring(0, pos);
            if (s1.length() > 65) {
                pos = s1.lastIndexOf(" ") + 1;
            }
            NumLiteral_1 = NumLiteral.substring(0, pos).trim();
            NumLiteral_2 = NumLiteral.substring(pos).trim();
        }
        param.put("NumLiteral_1", "1");
        param.put("NumLiteral_2", "1");
        param.put("Numorden", "1");
        param.put("observacion", "1");
        param.put("tipo_compr", "1");
        param.put("titulo", "1");
        param.put("total", "1");
        param.put("user", "1");
        param.put("rpt_fecha_hora", "1");
        param.put("anulado", "ANULADO");
        param.put("aux_1", "CAPIP_AUX_1");
        param.put("aux_2", "CAPIP_AUX_2");
        param.put("linea_1", "CAPIP_LINEA_1");
        param.put("linea_2", "CAPIP_LINEA_2");
        param.put("linea_3", "CAPIP_LINEA_3");
        param.put("linea_4", "CAPIP_LINEA_4");
        param.put("desc_1", "CAPIP_DESC_1");
        param.put("desc_2", "CAPIP_DESC_2");
        param.put("desc_3", "CAPIP_DESC_3");
        param.put("desc_4", "CAPIP_DESC_4");
        param.put("desc_5", "CAPIP_DESC_5");
        param.put("desc_6", "CAPIP_DESC_6");
        param.put("desc_7", "CAPIP_DESC_7");
        param.put("desc_8", "CAPIP_DESC_8");
        param.put("func_1", "CAPIP_FUNC_1");
        param.put("func_2", "CAPIP_FUNC_2");
        param.put("func_3", "CAPIP_FUNC_3");
        param.put("func_4", "CAPIP_FUNC_4");
        param.put("func_5", "CAPIP_FUNC_5");
        param.put("func_6", "CAPIP_FUNC_6");
        param.put("func_7", "CAPIP_FUNC_7");
        param.put("func_8", "CAPIP_FUNC_8");
        final Class aClass = FrmPrincipal.getInstance().getClass();
        param.put("logo_iz", aClass.getResourceAsStream("/imagenes/logo_iz.png"));
        param.put("logo_der", aClass.getResourceAsStream("/imagenes/logo_der.png"));

        InputStream pathRpt = getClass().getResourceAsStream("/reportes/blank.jasper");
        if (pathRpt == null) {
            JOptionPane.showMessageDialog(null, "Reporte de orden de compra no encontrado");
        } else {

            try {

                File file = new File("src/reportes/compromiso.jrxml");

                System.out.println("JasperReport-> " + JasperCompileManager.compileReportToFile(file.getAbsolutePath()));

                String aux = file.getAbsolutePath().replace("compromiso.jrxml", "Compromiso.jasper");
                File file2 = new File(aux);

                System.out.println("Aux -> " + aux);

                final InputStream pathRpt2 = getClass().getResourceAsStream("/reportes/Compromiso.jasper");

                final JasperPrint jasperPrint = JasperFillManager.fillReport(pathRpt2, new HashMap<>(param), ConnCapip.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint, false);
                if (inexport) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, Propiedades.CAPIP_DIR_LOCAL_COMPROMISO + "/" + inid + ".pdf");
                }

            } catch (final Exception inex) {
                logger.error(inex);
                JOptionPane.showMessageDialog(null, inex);
            }
        }
    }

}
