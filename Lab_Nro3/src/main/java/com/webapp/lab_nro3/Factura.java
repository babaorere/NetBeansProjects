package com.webapp.lab_nro3;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import com.webapp.lab_nro3.OCFactura;

public class Factura {

    private Integer numero;
    private String proveedor;
    private LocalDate fecha;
    private String articulo;
    private Integer cantidad;
    private String unidad;
    private Double itbm;
    private Double total;

    public Factura() {
        this(null, null, null, null, null, null, null, null);
    }

    public Factura(Integer numero, String proveedor, LocalDate fecha, String articulo, Integer cantidad, String unidad, Double itbm, Double total) {
        this.numero = numero;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.itbm = itbm;
        this.total = total;
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

    public static int save(Factura factura) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO factura(proveedor, fecha, articulo, cantidad, unidad, itbm, total) values(?,?,?,?,?,?,?)");
            ps.setString(1, factura.getProveedor());
            ps.setDate(2, java.sql.Date.valueOf(factura.getFecha()));
            ps.setString(3, factura.getArticulo());
            ps.setInt(4, factura.getCantidad());
            ps.setString(5, factura.getUnidad());
            ps.setDouble(6, factura.getItbm());
            ps.setDouble(7, factura.getTotal());

            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }
        return status;
    }

    public static int update(Factura factura) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE factura SET proveedor=?,fecha=?,articulo=?,cantidad=?,unidad=?,itbm=?,total=? WHERE numero=?");
            ps.setString(1, factura.getProveedor());
            ps.setDate(2, java.sql.Date.valueOf(factura.getFecha()));
            ps.setString(3, factura.getArticulo());
            ps.setInt(4, factura.getCantidad());
            ps.setString(5, factura.getUnidad());
            ps.setDouble(6, factura.getItbm());
            ps.setDouble(7, factura.getTotal());
            ps.setInt(8, factura.getNumero());
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
            PreparedStatement ps = con.prepareStatement("DELETE FROM factura WHERE numero=?");
            ps.setInt(1, numero);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }

        return status;
    }

    public static List<Factura> getList() {
        List<Factura> list = new ArrayList<>();

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM factura");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Factura factura = new Factura(rs.getInt("numero"), rs.getString("proveedor"),
                        rs.getDate("fecha").toLocalDate(), rs.getString("articulo"), rs.getInt("cantidad"),
                        rs.getString("unidad"), rs.getDouble("itbm"), rs.getDouble("total"));
                list.add(factura);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<Factura> getListCliente(int numero) {
        List<OCFactura> listOC = new ArrayList<>();
        List<Factura> list = new ArrayList<>();

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM oc_factura WHERE cl_numero=?");
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OCFactura ocFactura = new OCFactura(rs.getInt("id"), rs.getInt("cl_numero"), rs.getInt("fa_numero"));
                listOC.add(ocFactura);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            for (OCFactura reg : listOC) {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM factura WHERE numero=?");
                ps.setInt(1, reg.getFaNumero());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Factura factura = new Factura(rs.getInt("numero"), rs.getString("proveedor"),
                            rs.getDate("fecha").toLocalDate(), rs.getString("articulo"), rs.getInt("cantidad"),
                            rs.getString("unidad"), rs.getDouble("itbm"), rs.getDouble("total"));
                    list.add(factura);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }

    public static Factura get(int inNumero) {
        Factura cliente = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM factura WHERE numero=?");
            ps.setInt(1, inNumero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Factura(rs.getInt("numero"), rs.getString("proveedor"),
                        rs.getDate("fecha").toLocalDate(), rs.getString("articulo"), rs.getInt("cantidad"),
                        rs.getString("unidad"), rs.getDouble("itbm"), rs.getDouble("total"));
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
     * @return the proveedor
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the articulo
     */
    public String getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the itbm
     */
    public Double getItbm() {
        return itbm;
    }

    /**
     * @param itbm the itbm to set
     */
    public void setItbm(Double itbm) {
        this.itbm = itbm;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

}
