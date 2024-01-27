package negocio;


/**
 *
 *
 */
public class Encomienda {

    private long en_id;
    private String en_destinatario;
    private String en_direccion;
    private String en_tipo;
    private int en_entregadomicilio;
    private String en_tamano;
    private String en_remitente;


    public Encomienda(long inEn_id, String inEn_destinatario, String inEn_direccion, String inEn_tipo, int inEn_entregadomicilio, String inEn_tamano, String inEn_remitente) {
        this.en_id = inEn_id;
        this.en_destinatario = inEn_destinatario;
        this.en_direccion = inEn_direccion;
        this.en_tipo = inEn_tipo;
        this.en_entregadomicilio = inEn_entregadomicilio;
        this.en_tamano = inEn_tamano;
        this.en_remitente = inEn_remitente;
    }


    @Override
    public String toString() {
        return en_id + " : " + en_destinatario + " <= " + en_remitente;
    }


    /**
     * @return the en_id
     */
    public long getEn_id() {
        return en_id;
    }


    /**
     * @param inEn_id
     */
    public void setEn_id(long inEn_id) {
        if (inEn_id > 0) {
            this.en_id = inEn_id;
        }
    }


    /**
     * @return the en_destinatario
     */
    public String getEn_destinatario() {
        return en_destinatario;
    }


    /**
     * @param inEn_destinatario
     */
    public void setEn_destinatario(String inEn_destinatario) {
        if (inEn_destinatario != null && inEn_destinatario.length() > 0) {
            this.en_destinatario = inEn_destinatario;
        }
    }


    /**
     * @return the en_direccion
     */
    public String getEn_direccion() {
        return en_direccion;
    }


    /**
     * @param inEn_direccion
     */
    public void setEn_direccion(String inEn_direccion) {
        if ((inEn_direccion != null) && (inEn_direccion.length() > 0)) {
            this.en_direccion = inEn_direccion;
        }
    }


    /**
     * @return the en_tipo
     */
    public String getEn_tipo() {
        return en_tipo;
    }


    /**
     * @param inEn_tipo
     */
    public void setEn_tipo(String inEn_tipo) {
        if ((inEn_tipo != null) && (inEn_tipo.length() > 0)) {
            this.en_tipo = inEn_tipo;
        }
    }


    /**
     * @return the en_entregadomicilio
     */
    public int getEn_entregadomicilio() {
        return en_entregadomicilio;
    }


    /**
     * @param inEn_entregadomicilio the en_entregadomicilio to set
     */
    public void setEn_entregadomicilio(int inEn_entregadomicilio) {
        if (inEn_entregadomicilio > 0) {
            this.en_entregadomicilio = inEn_entregadomicilio;
        }
    }


    /**
     * @return the en_tamano
     */
    public String getEn_tamano() {
        return en_tamano;
    }


    /**
     * @param inEn_tamano the en_tamano to set
     */
    public void setEn_tamano(String inEn_tamano) {
        if ((inEn_tamano != null) && (inEn_tamano.length() > 0)) {
            this.en_tamano = inEn_tamano;
        }
    }


    /**
     * @return the en_remitente
     */
    public String getEn_remitente() {
        return en_remitente;
    }


    /**
     * @param inEn_remitente the en_remitente to set
     */
    public void setEn_remitente(String inEn_remitente) {
        if ((inEn_remitente != null) && (inEn_remitente.length() > 0)) {
            this.en_remitente = inEn_remitente;
        }
    }

}
