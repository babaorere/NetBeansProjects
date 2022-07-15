/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016
 *
 */
package modelos;

/**
 *
 * @author CAPIP Sistemas C.A.
 */
public final class ChequeModel {

    private final String montoPagar;
    private final String benef_razonsocial;
    private final String benef_rif_ci;
    private final String numLiteral_1;
    private final String numLiteral_2;
    private final String ciudad;
    private final java.util.Date fecha;
    private final String endosable;

    public ChequeModel(final String _montoPagar, final String _benef_razonsocial, final String _benef_rif_ci,
        final String _NumLiteral_1, final String _NumLiteral_2, final String _ciudad, final java.util.Date _fecha, final String _endosable) {

        this.montoPagar = _montoPagar;
        this.benef_razonsocial = _benef_razonsocial;
        this.benef_rif_ci = _benef_rif_ci;
        this.numLiteral_1 = _NumLiteral_1;
        this.numLiteral_2 = _NumLiteral_2;
        this.ciudad = _ciudad;
        this.fecha = _fecha;
        this.endosable = _endosable;
    }

    /**
     * @return the montoPagar
     */
    public String getMontoPagar() {
        return montoPagar;
    }

    /**
     * @return the benef_razonsocial
     */
    public String getBenef_razonsocial() {
        return benef_razonsocial;
    }

    /**
     * @return the benef_rif_ci
     */
    public String getBenef_rif_ci() {
        return benef_rif_ci;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @return the fecha
     */
    public java.util.Date getFecha() {
        return fecha;
    }

    /**
     * @return the endosable
     */
    public String getEndosable() {
        return endosable;
    }

    /**
     * @return the numLiteral_1
     */
    public String getNumLiteral_1() {
        return numLiteral_1;
    }

    /**
     * @return the numLiteral_2
     */
    public String getNumLiteral_2() {
        return numLiteral_2;
    }

}
