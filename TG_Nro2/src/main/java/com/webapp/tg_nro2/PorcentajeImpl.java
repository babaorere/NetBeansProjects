package com.webapp.tg_nro2;

/**
 *
 */
public class PorcentajeImpl extends PorcentajeAbstract {

    @Override
    public String obtenerNivel(double valor) {

        String nivel;

        if (valor >= 0.90) {
            nivel = "Superior";
        } else if (valor >= 0.75) {
            nivel = "Medio";
        } else if (valor >= 0.50) {
            nivel = "Regular";
        } else {
            nivel = "Fuera de nivel";
        }

        return nivel;
    }

}
