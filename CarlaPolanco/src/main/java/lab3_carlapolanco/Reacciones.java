/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3_carlapolanco;

/**
 *
 * @author carla
 */
public class Reacciones {
    private int id;
    private String autor;
    private String fecha;
    private String contenido;
    private String tipo;
    
    public Reacciones(int Id, String Autor,String Fecha,String Contenido,String Tipo){
        this.id = Id;
        this.autor = Autor;
        this.fecha = Fecha;
        this.contenido = Contenido;
        this.tipo = Tipo;
    }
    
    public int getID(){
        return(this.id);
    }
    public String getAutorR(){
        return(this.autor);
    }
    public String getFechaR(){
        return(this.fecha);
    }
    public String getContenidoR(){
        return(this.contenido);
    }
    public String getTipoR(){
        return(this.tipo);
    }
}
