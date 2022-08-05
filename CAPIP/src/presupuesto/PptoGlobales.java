/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package presupuesto;

import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public class PptoGlobales {

    public static final BigDecimal CIEN = new BigDecimal(100.00);
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

    public static final NumberFormat intFormat;

    public static final String TBL_GLOBALES = "globales";

//    public static final String TBL_PPTO_EGRESO = "pptoegreso";
//    public static final String TBL_PPTO_INGRESO = "pptoingreso";
    public static final String TBL_PPTO_ADICIONAL = "tmppptoadicional";

    public static final String TBL_CREDITO_ADICIONAL = "creditoadicional";

    public static final String TBL_CREDITO_ADICIONAL_DET = "creditoadicional_det";

    public static final String TBL_ORDEN_COMPRA = "ordencompra";

    public static final String TBL_ORDEN_SERVICIO = "ordenservicio";

    public static final String TBL_ORDEN_OTROS = "ordenotros";

    public static final String TBL_ORDEN_ITEMS = "ordenitems";

    public static final String TBL_ID_ITEMS = "ordeniditems";

    public static final String TBL_BENEFICIARIOS = "beneficiarios";

    public static final String TBL_TRASP_PARTIDAS = "trasppartidas";

    public static final String TBL_CAUSADO = "causado";

    public static MaskFormatter getDateMaskFormatter() {
        try {
            MaskFormatter formatter = new MaskFormatter(strDateMask);
            formatter.setValueClass(String.class);
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setValueContainsLiteralCharacters(true);
            return formatter;
        } catch (final Exception _ex) {
        }

        return null;
    }

    public static DefaultFormatterFactory getDateFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getDateMaskFormatter());
        } catch (final Exception _ex) {
        }
        return null;
    }

    public static DefaultFormatter getIntFormatter() {
        try {
            DefaultFormatter formatter = new NumberFormatter(new DecimalFormat(strIntFormat));
            formatter.setValueClass(Integer.class);
            formatter.setAllowsInvalid(false);
            return formatter;
        } catch (final Exception _ex) {
        }
        return null;
    }

    public static DefaultFormatter getCurFormatter() {
        try {
            DefaultFormatter formatter = new NumberFormatter(new DecimalFormat(strCurMask));
            formatter.setValueClass(BigDecimal.class);
            formatter.setAllowsInvalid(false);
            return formatter;
        } catch (final Exception _ex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getIntFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getIntFormatter());
        } catch (final Exception _ex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCurFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCurFormatter());
        } catch (final Exception _ex) {
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
        } catch (ParseException _ex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCodEgresoFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCodEgresoMaskFormatter());
        } catch (final Exception _ex) {
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
        } catch (ParseException _ex) {
        }
        return null;
    }

    public static DefaultFormatterFactory getCodIngresoFormatterFactory() {
        try {
            return new DefaultFormatterFactory(getCodIngresoMaskFormatter());
        } catch (final Exception _ex) {
        }

        return null;
    }

    public static DefaultTableCellRenderer getIntCellRenderer() {
        DefaultTableCellRenderer rendererInt = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object value) {
                try {
                    setText(PptoGlobales.intFormat.format((value == null) ? new Integer(0) : value));
                } catch (final Exception _ex) {
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
                    setText(PptoGlobales.dateFormat.format((value == null) ? new Date() : value));
                } catch (final Exception _ex) {
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
                    setText(PptoGlobales.curFormat.valueToString((value == null) ? BigDecimal.ZERO : value));
                } catch (final Exception _ex) {
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
        } catch (final Exception _ex) {
            auxInt = null;
        }

        intFormat = auxInt;

        SimpleDateFormat auxDate;
        try {
            auxDate = new SimpleDateFormat("dd/MM/yyyy");
        } catch (final Exception _ex) {
            auxDate = null;
        }

        dateFormat = auxDate;

        DefaultFormatter aux;
        try {
            aux = getCurFormatter();
        } catch (final Exception _ex) {
            aux = null;
        }

        curFormat = aux;
    }
}
