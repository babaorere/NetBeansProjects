package com.app;

/**
 *
 */
public class Telefono {

    // Para generar automaticamente el id del telefono
    private static int contId;

    private final int id; // identificador interno del sistema
    private String idFab; // identificador de la fabrica
    private Fabricante fabricante;
    private Modelo modelo;
    private String imei;

    static {
        contId = 0;
    }

    public Telefono() {
        this(null, null, null, null);
    }

    public Telefono(String idFab, Fabricante fabricante, Modelo modelo, String imei) {
        this.id = ++contId; // aqui se genera automaticamente el id del telefono
        this.idFab = idFab;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.imei = imei;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the idFab
     */
    public String getIdFab() {
        return idFab;
    }

    /**
     * @param idFab the idFab to set
     */
    public void setIdFab(String idFab) {
        this.idFab = idFab;
    }

    /**
     * @return the fabricante
     */
    public Fabricante getFabricante() {
        return fabricante;
    }

    /**
     * @param fabricante the fabricante to set
     */
    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

}
