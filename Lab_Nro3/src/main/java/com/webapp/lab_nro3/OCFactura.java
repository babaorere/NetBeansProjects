package com.webapp.lab_nro3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OCFactura {

    private Integer id;
    private Integer clNumero;
    private Integer faNumero;

    public OCFactura() {
        this(null, null, null);
    }

    public OCFactura(Integer id, Integer clNumero, Integer faNumero) {
        this.id = id;
        this.clNumero = clNumero;
        this.faNumero = faNumero;
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

    public static int save(OCFactura reg) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO oc_factura(cl_numero, fa_numero) values(?,?)");
            ps.setInt(1, reg.getClNumero());
            ps.setInt(2, reg.getFaNumero());
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }
        return status;
    }

    public static int update(OCFactura reg) {
        int status;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE oc_factura SET cl_numero=?,fa_numero=? WHERE id=?");
            ps.setInt(1, reg.getClNumero());
            ps.setInt(2, reg.getFaNumero());
            ps.setInt(3, reg.getId());
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
            PreparedStatement ps = con.prepareStatement("DELETE FROM oc_factura WHERE numero=?");
            ps.setInt(1, numero);
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            status = 0;
        }

        return status;
    }

    public static List<OCFactura> getList() {
        List<OCFactura> list = new ArrayList<>();

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OCFactura reg = new OCFactura(rs.getInt("id"), rs.getInt("cl_numero"), rs.getInt("fa_numero"));
                list.add(reg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static OCFactura get(int inNumero) {
        OCFactura reg = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE numero=?");
            ps.setInt(1, inNumero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg = new OCFactura(rs.getInt("id"), rs.getInt("cl_numero"), rs.getInt("fa_numero"));
            }
        } catch (Exception e) {
            System.out.println(e);
            reg = null;
        }

        return reg;
    }

    /**
     * @return the clNumero
     */
    public Integer getClNumero() {
        return clNumero;
    }

    /**
     * @param clNumero the clNumero to set
     */
    public void setClNumero(Integer clNumero) {
        this.clNumero = clNumero;
    }

    /**
     * @return the faNumero
     */
    public Integer getFaNumero() {
        return faNumero;
    }

    /**
     * @param faNumero the faNumero to set
     */
    public void setFaNumero(Integer faNumero) {
        this.faNumero = faNumero;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

}
