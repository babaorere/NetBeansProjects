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
public class ListaUsuarios {
    
    private ArrayList <Usuario> listaUsuarios;
    
    public ListaUsuarios(){
        this.listaUsuarios = new ArrayList ();
    }
    /* 
     * @param newUsuario // usuario
     * Metodo que añade un usuario a la lista de usuarios
     */
    public void agregarUsuario(Usuario newUsuario){
        this.listaUsuarios.add(newUsuario);
    }
    /* 
     * @return entero
     * Metodo que devuelve la cantidad de usuarios o tamaño de la lista
     */
    public int tamanioLista(){
        return(this.listaUsuarios.size());
    }
    /* 
     * @param username // nombre de un usuario
     * @param pass // contraseña del usuario
     * @return usuario
     * Metodo que a partir de un username y password entrega todos los datos del usuario
     */
    public Usuario datosUsuario(String username, String pass){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0 ; j<i; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(username) && user.getContrasena().equals(pass)){
                return(user);
            } 
        }
        return (null); 
    }
    /* 
     * @param Nombre // nombre de un usuario
     * @return usuario
     * Metodo que a partir de un username entrega todos los datos del usuario
     */
    public Usuario datosUsuario(String username){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0 ; j<i; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(username)){
                return(user);
            } 
        }
        return (null); 
    }
    /* 
     * @param Nombre // nombre de un usuario
     * @param Contrasena // contraseña del usuario
     * @return numero entero, 1 si el usuario existe, 0 si no existe
     * Metodo que comprueba la existencia de un usuario con su contraseña para el login
     */
    public int existeUsuarioLogin(String Nombre, String Contrasena){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0 ; j<i; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(Nombre) && user.getContrasena().equals(Contrasena)){
                return(1);
            } 
        }
        return (0);
    }
    /* 
     * @param Nombre // nombre de un usuario
     * @return numero entero, 1 si el usuario existe, 0 si no existe
     * Metodo que comprueba la existencia de un usuario
     */
    public int existeUsuario(String Nombre){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0; j<i ; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(Nombre)){
                return (1);
            }
        }
        return (0);           
    }
    /*
     * @param Dirigidos// lista de usuario 
     * @param post // publicacion 
     * Metodo que agrega una publicacion a una lista de usuarios
     */
    public void anadirPublicacionesLista(List Dirigidos,Publicaciones Post){
       int i = this.listaUsuarios.size();
       int j = Dirigidos.size();
       int k,l;
       Usuario user;
       for(k=0;k<j;k++){
           for(l=0;l<i;l++){
               user = this.listaUsuarios.get(l);
               if(Dirigidos.get(k).equals(user.getNombre())){
                   user.getPublicaciones().agregarPublicacion(Post);
               }
           }
       }
    }
    /*
     * @param Dirigidos// lista de usuario 
     * @param post // publicacion 
     * Metodo que agrega una publicacion a una lista de usuarios
     */
    public void anadirPublicacionesListaShare(List Dirigidos,Publicaciones Post){
       int i = this.listaUsuarios.size();
       int j = Dirigidos.size();
       int k,l;
       Usuario user;
       for(k=0;k<j;k++){
           for(l=0;l<i;l++){
               user = this.listaUsuarios.get(l);
               if(Dirigidos.get(k).equals(user.getNombre())){
                   user.getPCcompartidas().agregarPublicacion(Post);   
               }
           }
       }
    }
    /*
     * @param name //nombre del usuario
     * @param post // publicacion 
     * Metodo que agrega una publicacion a un usuario
     */
    public void anadirPublicacionAutor(Publicaciones post,String name){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0; j<i ; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(name)){
                user.getPublicaciones().agregarPublicacion(post);
            }
        }
    }
    /*
     * @param usuarioSeguir //nombre del usuario
     * @param UsuarioA // usuario donde se aplicara el follow
     * Metodo que agrega un usuario a la lista de seguidores
     */
    public void seguir(String UsuarioSeguir,Usuario UsuarioA){
        Usuario user;
        int i = this.listaUsuarios.size();
        int j;
        for(j=0; j<i ; j++){
            user = this.listaUsuarios.get(j);
            if(user.getNombre().equals(UsuarioSeguir)){
                user.getSeguidores().agregarUsuario(UsuarioA); 
            }
        }
    }
    /*
     * @return String con los datos de la lista de usuarios
     * Metodo que convierte los datos de una lista de usuarios a string
     */
    public String setListaUsuariosString(){
        Usuario User;
        String listaString = "\n--------------------------------------- Usuarios Registrados ----------------------------------- \n\n";
        int i = this.listaUsuarios.size();
        for(int k = 0; k < i; k++){
            User = this.listaUsuarios.get(k);
            listaString = listaString + User.setUsuarioString();
        }
        return (listaString);
    }
    /*
     * @return String con los datos de la lista de seguidores
     * Metodo que convierte los nombres de una lista de seguidores a string
     */
    public String setListaUsuariosStringSeguidores(){
        Usuario User;
        String listaString = " SEGUIDORES: ";
        int i = this.listaUsuarios.size();
        for(int k = 0; k < i; k++){
            User = this.listaUsuarios.get(k);
            listaString = listaString + User.getNombre() + " ";
        }
        return (listaString);
    }
    /*
     * @return String con los datos de la lista de compartidos
     * Metodo que convierte los nombres de una lista de follow a string
     */
    public String setListaUsuariosStringCompartido(){
        Usuario User;
        String listaString = " ";
        int i = this.listaUsuarios.size();
        for(int k = 0; k < i; k++){
            User = this.listaUsuarios.get(k);
            listaString = listaString + User.getNombre() + " ";
        }
        return (listaString);
    }
    
}
