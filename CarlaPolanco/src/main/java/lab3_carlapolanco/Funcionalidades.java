/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3_carlapolanco;

import java.util.List;

/**
 *
 * @author carla
 */
public class Funcionalidades {
    private redSocial socialRed;
    
    public Funcionalidades(){
        this.socialRed = new redSocial();
    }
    
    public redSocial getredSocial(){
        return(this.socialRed);
    }
    
    /*
     * @param usermane\\pertenece al nombre del usuario
     * @param pass \\pertenece la contrasena del usuario 
     * Metodo que permite a un usuario registrarse 
     */
    public void register(String username,String pass){
        Usuario userN;
        int tamanio;
        tamanio = socialRed.getTamanioUsuarios();
        userN = new Usuario(tamanio+1,username,pass);
        socialRed.registar(userN);
    }
    /*
     * @param usermane \\pertenece al nombre del usuario
     * @param pass \\pertenece la contrasena del usuario 
     * Metodo que permite a un usuario iniciar sesion
     */
    public void login(String username, String pass){
        if(socialRed.getListaUsuarioActivo()==null){
            socialRed.iniciarsesion(username, pass);
        }
    }
    /*
    * @param nombre \\pertenece al nombre del usuario
    * @param contra \\pertenece la contrasena del usuario 
    * Metodo que permite cerrar sesion 
    */
    public void logout(String nombre, String Contra){
        if(socialRed.getListaUsuarioActivo()!= null){
            if(socialRed.getListaUsuarioActivo().getContrasena().equals(Contra) && socialRed.getListaUsuarioActivo().getNombre().equals(nombre)){
                socialRed.exit();
                System.out.println("----------------------------------");
                System.out.println("  Sesion Cerrada Correctamente");
                System.out.println("----------------------------------");
            }
            else{
                System.out.println("--------------------------------------------");
                System.out.println("  Datos ingresados erroneos para la salida");
                System.out.println("--------------------------------------------");
            }
        }
        else{
            System.out.println("--------------------------------------------");
            System.out.println("  No existe usuario con sesion iniciada");
            System.out.println("--------------------------------------------");
        }
    }
    /*
     * @param tipoP \\pertenece al tipo de publicacion
     * @param contenido \\ contenido de la publicaicon
     * Metodo que permite realizar una publicacion
     */
    public void post(String tipoP, String Contenido){
        if(socialRed.getListaUsuarioActivo()!= null){
            Publicaciones post;
            int tamanio = socialRed.getListaPublicaciones().tamanioLista();
            post = new Publicaciones(tamanio+1,socialRed.getListaUsuarioActivo().getNombre(),Contenido,tipoP);
            socialRed.getListaPublicaciones().agregarPublicacion(post);
            socialRed.getListaUsuarios().anadirPublicacionAutor(post, socialRed.getListaUsuarioActivo().getNombre());
            
        }
        else{
            System.out.println("--------------------------------------------");
            System.out.println("  No existe usuario con sesion iniciada");
            System.out.println("--------------------------------------------");
        }
    }
    
    /*
     * @param tipoP \\pertenece al tipo de publicacion
     * @param contenido \\ contenido de la publicaicon
     * @param Dirigido \\ Lista con los usuarios que va dirigida la publicacion
     * Metodo que permite realizar una publicacion citando a algunos usuarios
     */
    public void post(String tipoP, String Contenido, List Dirigidos){
        if(socialRed.getListaUsuarioActivo()!= null){
            Publicaciones post;
            int tamanio = socialRed.getListaPublicaciones().tamanioLista();
            post = new Publicaciones(tamanio+1,socialRed.getListaUsuarioActivo().getNombre(),Contenido,tipoP);
            socialRed.getListaPublicaciones().agregarPublicacion(post);
            socialRed.getListaUsuarios().anadirPublicacionesLista(Dirigidos, post);
        }
        else{
            System.out.println("--------------------------------------------");
            System.out.println("  No existe usuario con sesion iniciada");
            System.out.println("--------------------------------------------");
        }
    }
    /*
     * @param UsuarioSeguir // pertenece al nombre del usaurio para seguir
     * Metodo que permite realizar follow a un usuario
     */
    public void follow(String UsuarioSeguir){
        Usuario userA;
        if(socialRed.getListaUsuarioActivo()!= null){
            userA = socialRed.getListaUsuarios().datosUsuario(socialRed.getListaUsuarioActivo().getNombre(), socialRed.getListaUsuarioActivo().getContrasena());
            if(!socialRed.getListaUsuarioActivo().getNombre().equals(UsuarioSeguir)){
                socialRed.getListaUsuarios().seguir(UsuarioSeguir,userA);
                System.out.println("-------------------------------");
                System.out.println("  Usuario seguido con exito");
                System.out.println("--------------------------------");
            }
            else{
                System.out.println("--------------------------------------------");
                System.out.println("  No se puede seguir a usted mismo");
                System.out.println("--------------------------------------------");
            }
        }
        else{
            System.out.println("--------------------------------------------");
            System.out.println("  No existe usuario con sesion iniciada");
            System.out.println("--------------------------------------------"); 
        }
    }
    /*
     * @param idP // pertenece al id de una publicacion
     * @param UsuariosC // pertenece al nombre de los usaurios para seguir
     * Metodo que permite compartir una publicacion
     */
    public void share(int idP,List UsuariosC){
        if(socialRed.getListaUsuarioActivo()!= null){
            Publicaciones post;
            Usuario user;
            post = socialRed.getListaPublicaciones().datosPublicacion(idP);
            socialRed.getListaUsuarios().anadirPublicacionesListaShare(UsuariosC, post);
            
            int i=0,j=UsuariosC.size();
            
            for(i=0;i<j;i++){
                user = socialRed.getListaUsuarios().datosUsuario((String) UsuariosC.get(i));
                post.getUsuarioCompartidoP().agregarUsuario(user);
            }
            
        }
        else{
            System.out.println("--------------------------------------------");
            System.out.println("  No existe usuario con sesion iniciada");
            System.out.println("--------------------------------------------");
        }
        
    }
}
