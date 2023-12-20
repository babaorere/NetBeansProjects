

package proyectohotel;

/**
 *
 * @author Esteban Quesada
 */
public class Habitacion {
    
    String codigoHabitacion;
    String tipoHabitacion;
    String costo;
    String estado;

    public Habitacion(String codigoHabitacion, String tipoHabitacion, String costo, String estado) {
        this.codigoHabitacion = codigoHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.costo = costo;
        this.estado = estado;
    }
    
    public Habitacion(){
        this.codigoHabitacion = "";
        this.tipoHabitacion = "";
        this.costo = "";
        this.estado = "";
    }

    public String getCodigoHabitacion() {
        return codigoHabitacion;
    }

    public void setCodigoHabitacion(String codigoHabitacion) {
        this.codigoHabitacion = codigoHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
