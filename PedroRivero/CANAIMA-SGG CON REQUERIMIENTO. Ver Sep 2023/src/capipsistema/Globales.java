/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import connection.ConnCapip;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static java.lang.System.exit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import utils.Format;
import utils.OrdPagIdTipo;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class Globales {

    public static final OrdPagIdTipo ORD_PAG = OrdPagIdTipo.valueOf(CapipPropiedades.CAPIP_NUM_PAGO);
//    public static final OrdPagIdTipo ORD_PAG_ID_TIPO = OrdPagIdTipo.RELATIVO;

    public static final String CAPIP_CONTACTE_MSJ = "Contacte a su proveedor de CAPIP SISTEMAS: capipsistemas@gmail.com, o Llame al: 0412-993-7696";

    public static final BigDecimal CIEN = new BigDecimal(100.00d);
    public static final String strCodEgresoZero = "00.00.00.00.000.00.00.00";
    public static final String strCodEgresoMask = "##.##.##.##.###.##.##.##";

    public static final String strCodIngresoZero = "0.00.00.00.00";
    public static final String strCodIngresoMask = "#.##.##.##.##";

    public static final String strDateFormat = "dd/MM/yyyy";
    public static final String strDateMask = "##/##/####";
    public static final String strFchZERO = "__/__/____";

    public static final String strCurMask = "#,##0.00";

    public static final String strIntFormat = "#,##0";

    public static final SimpleDateFormat dateFormat;

    public static final DefaultFormatter curFormat;

    public static final DefaultFormatter bigDecFormat;

    public static final NumberFormat intFormat;

    public static final String TBL_GLOBALES = "globales";

    public static final String TBL_PPTO_EGRESO = "presupe";

    public static final String TBL_PPTO_INGRESO = "presupi";

    public static final String TBL_PPTO_ADICIONAL = "tmppptoadicional";

    public static final String TBL_CREDITO_ADICIONAL = "creditoadicional";

    public static final String TBL_ORDEN_COMPRA = "ordencompra";

    public static final String TBL_ORDEN_SERVICIO = "ordenservicio";

    public static final String TBL_ORDEN_OTROS = "ordenotros";

    public static final String TBL_ORDEN_ITEMS = "ordenitems";

    public static final String TBL_ID_ITEMS = "ordeniditems";

    public static final String TBL_BENEFICIARIOS = "beneficiarios";

    public static final String TBL_TRASP_PARTIDAS = "trasppartidas";

    public static final String TBL_GASTO_CAUSADO = "gastocausado";

    // Rio Chico
    //public static final String PART_IVA_COD = "01.05.00.51.403.18.01.00";
    //
    // Diego Ibarra
    public static final String PART_IVA_COD = "01.03.00.51.403.18.01.00";
    //
    // Higuerote
    // public static final String PART_IVA_COD = "403.18.01.00";
    //
    public static final String PART_IVA_DESC = "IMPUESTO AL VALOR AGREGADO";

    public static MaskFormatter getDateMaskFormatter() {
        try {
            MaskFormatter formatter = new MaskFormatter(strDateMask);
            formatter.setValueClass(String.class);
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setValueContainsLiteralCharacters(true);
            return formatter;
        } catch (final Exception inex) {
        }

        return null;
    }

    public static DefaultFormatterFactory getDateFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getDateMaskFormatter());
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatter getIntFormatter() {
        try {
            DefaultFormatter formatter = new NumberFormatter(new DecimalFormat(strIntFormat));
            formatter.setValueClass(Integer.class);
            formatter.setAllowsInvalid(false);
            return formatter;
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatter getCurFormatter() {
        try {
            DefaultFormatter formatter = new NumberFormatter(new DecimalFormat(strCurMask));
            formatter.setValueClass(Double.class); //Double.class);
            formatter.setAllowsInvalid(false);
            return formatter;
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatter getBigDecFormatter() {
        try {
            DefaultFormatter formatter = new NumberFormatter(new DecimalFormat(strCurMask));
            formatter.setValueClass(BigDecimal.class);
            formatter.setAllowsInvalid(false);
            return formatter;
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getIntFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getIntFormatter());
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCurFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCurFormatter());
        } catch (final Exception inex) {
        }
        return null;
    }

    public static MaskFormatter getCodEgresoMaskFormatter() {
        try {
            MaskFormatter formatter = new MaskFormatter(strCodEgresoMask);
            formatter.setValueClass(String.class);
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setValueContainsLiteralCharacters(true);
            return formatter;
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCodEgresoFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCodEgresoMaskFormatter());
        } catch (final Exception inex) {
        }

        return null;
    }

    public static MaskFormatter getCodIngresoMaskFormatter() {
        try {
            MaskFormatter formatter = new MaskFormatter(strCodIngresoMask);
            formatter.setValueClass(String.class);
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setValueContainsLiteralCharacters(true);
            return formatter;
        } catch (final Exception inex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCodIngresoFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCodIngresoMaskFormatter());
        } catch (final Exception inex) {
        }

        return null;
    }

    public static DefaultTableCellRenderer getIntCellRenderer() {
        DefaultTableCellRenderer rendererInt = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object value) {
                try {
                    setText(Globales.intFormat.format((value == null) ? String.valueOf(0) : value));
                } catch (final Exception inex) {
                    setText("error");
                }
            }
        };

        rendererInt.setHorizontalAlignment(SwingConstants.RIGHT);
        return rendererInt;
    }

    public static DefaultTableCellRenderer getDateCellRenderer() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object value) {
                try {
                    setText(Globales.dateFormat.format((value == null) ? new Date() : value));
                } catch (final Exception inex) {
                    setText("error");
                }
            }
        };

        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        return renderer;
    }

    public static Comparator<Date> getDateComparator() {
        return new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        };
    }

    public static DefaultTableCellRenderer getCurCellRenderer() {
        DefaultTableCellRenderer rendererCur = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object value) {
                try {
                    setText(Globales.curFormat.valueToString((value == null) ? BigDecimal.ZERO : value));
                } catch (final Exception inex) {
                    setText("error");
                }
            }
        };

        rendererCur.setHorizontalAlignment(SwingConstants.RIGHT);

        return rendererCur;
    }

    public static Comparator<BigDecimal> getCurComparator() {
        return new Comparator<BigDecimal>() {
            @Override
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.compareTo(o2);
            }
        };
    }

    static {

        // Para hacer el select() en todos los JTextComponent, inclusive los JFormattedTextFiels
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener("permanentFocusOwner", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(final PropertyChangeEvent e) {

                        if (e.getOldValue() instanceof JTextComponent) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JTextComponent oldTextField = (JTextComponent) e.getOldValue();
                                    oldTextField.setSelectionStart(0);
                                    oldTextField.setSelectionEnd(0);
                                }
                            });

                        }

                        if (e.getNewValue() instanceof JTextComponent) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JTextComponent textField = (JTextComponent) e.getNewValue();
                                    textField.selectAll();
                                }
                            });

                        }
                    }
                });

        NumberFormat auxInt;
        try {
            auxInt = new DecimalFormat(strIntFormat);
        } catch (final Exception inex) {
            auxInt = null;
        }

        intFormat = auxInt;

        SimpleDateFormat auxDate;
        try {
            auxDate = new SimpleDateFormat("dd/MM/yyyy");
        } catch (final Exception inex) {
            auxDate = null;
        }

        dateFormat = auxDate;

        DefaultFormatter aux;
        try {
            aux = getCurFormatter();
        } catch (final Exception inex) {
            aux = null;
        }

        curFormat = aux;

        try {
            aux = getBigDecFormatter();
        } catch (final Exception inex) {
            aux = null;
        }

        bigDecFormat = aux;
    }

    /**
     * Rev 24/09/2016
     *
     * @return
     */
    public static BigDecimal getIvaPorcValue(long inid_iva_aplicado) {
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM iva_aplicado WHERE id_iva_aplicado= " + inid_iva_aplicado);
            if (rs.next()) {
                return rs.getBigDecimal("valor_porc").setScale(2, RoundingMode.HALF_UP);
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar el IVA" + System.getProperty("line.separator") + inex);
        }

        JOptionPane.showMessageDialog(null, "Error al recuperar el IVA");
        exit(1);
        return null;
    }

    /**
     * Rev 10/10/2016
     *
     * @return
     */
    public static BigDecimal getIvaDecValue(long inid_iva_aplicado) {
        return getIvaPorcValue( inid_iva_aplicado).movePointLeft(2);
    }

    /**
     * Hay que considerar que la aplicacion no se ejecutara por las siguientes razones: .- Que el año del Ejercicio Fiscal sea diferente al año del O/S del Servidor. .- Que el año del Ejercicio Fiscal sea diferente al año del O/S del Sistema local.
     *
     * Rev 08/01/2017
     *
     * @return
     */
    public static java.sql.Date getEjefis() {
        final long ejefis;
        try {
            final ResultSet rs = ConnCapip.getInstance().executeQuery("SELECT * FROM global WHERE nombre= 'ejercicio_fiscal'");
            if (rs.next()) {
                ejefis = rs.getLong("valor");
            } else {
                JOptionPane.showMessageDialog(null, "Error al recuperar el Ejercicio Fiscal");
                exit(1);
                return null;
            }
        } catch (final Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al recuperar el Ejercicio Fiscal: " + System.getProperty("line.separator") + inex);
            exit(1);
            return null;
        }

        final Calendar fechaServidor = Calendar.getInstance();
        fechaServidor.setTimeInMillis(Globales.getServerTimeStamp().getTime());
        if (fechaServidor.get(Calendar.YEAR) != ejefis) {
            JOptionPane.showMessageDialog(null, "Error. El Ejercicio Fiscal " + ejefis + System.getProperty("line.separator") + "difiere del año del O/S del Servidor: " + Format.toStr(fechaServidor));
        }

        final Calendar fechaLocal = Calendar.getInstance();
        if (fechaLocal.get(Calendar.YEAR) != ejefis) {
            JOptionPane.showMessageDialog(null, "Error. El Ejercicio Fiscal " + ejefis + System.getProperty("line.separator") + "difiere del año del O/S del Sistema local: " + Format.toStr(fechaLocal));
        }

        final Calendar fechaAux = fechaServidor;
        fechaAux.set(Calendar.YEAR, (int) ejefis);

        return new java.sql.Date(fechaAux.getTimeInMillis());
    }

    /**
     *
     * Rev 08/01/2017
     *
     * @return
     */
    public static long getEjeFisYear() {

        final Calendar fechaAux = Calendar.getInstance();
        fechaAux.setTimeInMillis(getEjefis().getTime());

        return fechaAux.get(Calendar.YEAR);
    }

    /**
     * Rev 08/01/2017
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

    /**
     * Rev 07/01/2017
     *
     * @return
     */
    public static java.sql.Date getServerSqlDate() {

        return new java.sql.Date(getServerTimeStamp().getTime());
    }

    /**
     * Rev 08/01/2017
     *
     * @return
     */
    public static long getServerYear() {

        final Calendar taux = Calendar.getInstance();
        taux.setTimeInMillis(getServerTimeStamp().getTime());

        return (long) taux.get(Calendar.YEAR);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

}
