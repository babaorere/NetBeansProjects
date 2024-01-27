package com.webapp.lab_nro3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int numero;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String actividad;

    public Cliente() {
        this(0, null, null, null, null, null);
    }

    public Cliente(int numero, String nombre, String apellido, String direccion, String telefono, String actividad) {
        this.numero = numero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.actividad = actividad;
    }

    public static Connection getConnection() {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab_nro3", "usuario", "lGxo[FowC)0cu@RG");
        } catch (Exception e) {
            System.out.println(e);
            con = null;
        }
        return con;
    }

    public static int save(Cliente cliente) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO cliente(nombre, apellido, direccion, telefono, actividad) values(?,?,?,?,?)");
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getActividad());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getActividad());
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }
        return status;
    }

    public static int update(Cliente cliente) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cliente SET nombre=?,apellido=?,direccion=?,telefono=?,actividad=? WHERE numero=?");
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getActividad());
            ps.setInt(6, cliente.getNumero());
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }
        return status;
    }

    public static int delete(int numero) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM cliente WHERE numero=?");
            ps.setInt(1, numero);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }

        return status;
    }

    public static List<Cliente> getList() {
        List<Cliente> list = new ArrayList<>();

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("numero"), rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("direccion"), rs.getString("telefono"), rs.getString("actividad"));
                list.add(cliente);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static Cliente get(int inNumero) {
        Cliente cliente = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE numero=?");
            ps.setInt(1, inNumero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("numero"), rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("direccion"), rs.getString("telefono"), rs.getString("actividad"));
            }
        } catch (Exception e) {
            System.out.println(e);
            cliente = null;
        }

        return cliente;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the actividad
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
