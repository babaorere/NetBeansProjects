/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3_carlapolanco;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carla
 */
public class ListaPublicaciones {
    private ArrayList <Publicaciones> listaPublicaciones;
    
    public ListaPublicaciones(){
        this.listaPublicaciones = new ArrayList();
    }
    public int tamanioLista(){
        return(this.listaPublicaciones.size());
    }
    /* 
     * @param newPublicacion // publicacion
     * Metodo que añade una publicacion a la lista de Publicaciones
     */
    public void agregarPublicacion(Publicaciones newPublicacion){
        this.listaPublicaciones.add(newPublicacion);
    }
    /* 
     * @param id // identidicador de una publicacion
     * @return publicacion
     * Metodo que a partir de un identificador retorna todos los datos de esta publicacion
     */
    public Publicaciones datosPublicacion(int id){
        Publicaciones post;
        int i = this.listaPublicaciones.size();
        int j;
        for(j=0 ; j<i; j++){
            post = this.listaPublicaciones.get(j);
            if(post.getIDP() == id){
                return(post);
            } 
        }
        return (null); 
    }
    /* 
     * @param user // usuario
     * @param postE // publicacion en donde se añade
     * Metodo que añade el nombre de un usuario a la lista de compartido
     */
    public void editarPublicacionesCompartidas(Usuario user, Publicaciones postE){
        Publicaciones post;
        int i = this.listaPublicaciones.size();
        int j;
        for(j=0 ; j<i; j++){
            post = this.listaPublicaciones.get(j);
            if(post.getIDP() == postE.getIDP()){
                post.getUsuarioCompartidoP().agregarUsuario(user);
            } 
        }
    }
    /*
     * @return String con los datos de la lista de publicaciones
     * Metodo que convierte las publicaciones de un usuario a string
     */
    public String setListaPublicacionesString(){
        Publicaciones Publicacion;
        String listaString = " PUBLICACIONES DEL USUARIO: ";
        int i = this.listaPublicaciones.size();
        for(int k = 0; k < i; k++){
            Publicacion = this.listaPublicaciones.get(k);
            listaString = listaString + Publicacion.PublicacionestoString();
        }
        return (listaString);
    }
    /*
     * @return String con los datos de la lista de publicaciones
     * Metodo que convierte las publicaciones  a string
     */
    public String setListaPublicacionesStringRedSocial(){
        Publicaciones Publicacion;
        String listaString = "------------------------------- PUBLICACIONES ------------------------------------ \n";
        int i = this.listaPublicaciones.size();
        for(int k = 0; k < i; k++){
            Publicacion = this.listaPublicaciones.get(k);
            listaString = listaString + Publicacion.PublicacionestoString();
        }
        return (listaString);
    }
    
    /*
     * @return String con los datos de la lista de publicacionesCompartidas
     * Metodo que convierte las publicacionesCompartidas de un usuario a string
     */
    public String setListaPublicacionesStringCompartidas(){
        Publicaciones Publicacion;
        String listaString = " PUBLICACIONES COMPARTIDAS CONMIGO: ";
        int i = this.listaPublicaciones.size();
        for(int k = 0; k < i; k++){
            Publicacion = this.listaPublicaciones.get(k);
            listaString = listaString + Publicacion.PublicacionestoString();
        }
        return (listaString);
    }
    
}
