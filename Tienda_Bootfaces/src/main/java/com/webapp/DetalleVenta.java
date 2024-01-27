/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapp;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author manager
 */
@Entity
@Table(name = "detalle_venta")
@NamedQueries({
    @NamedQuery(name = "DetalleVenta.findAll", query = "SELECT d FROM DetalleVenta d"),
    @NamedQuery(name = "DetalleVenta.findByIdDetalleVenta", query = "SELECT d FROM DetalleVenta d WHERE d.idDetalleVenta = :idDetalleVenta"),
    @NamedQuery(name = "DetalleVenta.findByCantidad", query = "SELECT d FROM DetalleVenta d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleVenta.findByPrecioUnitario", query = "SELECT d FROM DetalleVenta d WHERE d.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "DetalleVenta.findByTotal", query = "SELECT d FROM DetalleVenta d WHERE d.total = :total")})
public class DetalleVenta implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private BigDecimal total;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_venta")
    private Integer idDetalleVenta;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta")
    @ManyToOne(optional = false)
    private Venta idVenta;

    public DetalleVenta() {
    }

    public DetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public DetalleVenta(Integer idDetalleVenta, int cantidad, BigDecimal precioUnitario, BigDecimal total) {
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleVenta != null ? idDetalleVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVenta)) {
            return false;
        }
        DetalleVenta other = (DetalleVenta) object;
        if ((this.idDetalleVenta == null && other.idDetalleVenta != null) || (this.idDetalleVenta != null && !this.idDetalleVenta.equals(other.idDetalleVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapp.DetalleVenta[ idDetalleVenta=" + idDetalleVenta + " ]";
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
