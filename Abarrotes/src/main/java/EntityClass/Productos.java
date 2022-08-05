/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntityClass;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Propietario
 */
@Entity
@Table(name = "productos")
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
    @NamedQuery(name = "Productos.findByNumerodeidentificaciondelproducto", query = "SELECT p FROM Productos p WHERE p.numerodeidentificaciondelproducto = :numerodeidentificaciondelproducto"),
    @NamedQuery(name = "Productos.findByPrecio", query = "SELECT p FROM Productos p WHERE p.precio = :precio"),
    @NamedQuery(name = "Productos.findByUserPass", query = "SELECT count(p) FROM Productos p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Productos.deleteUser", query = "DELETE FROM Productos p WHERE p.nombre = :nombre")})
public class Productos implements Serializable {

    @Lob
    @Size(max = 65535)
    @Column(name = "CodigodeBarras")
    private String codigodeBarras;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Numero_de_identificacion_del_producto")
    private Integer numerodeidentificaciondelproducto;
    @Lob
    @Column(name = "Nombre")
    private String nombre;
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Lob
    @Column(name = "Marca")
    private String marca;
    @Lob
    @Column(name = "Contenido_del_producto")
    private String contenidodelproducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Float precio;

    public Productos() {
    }

    public Productos(Integer numerodeidentificaciondelproducto) {
        this.numerodeidentificaciondelproducto = numerodeidentificaciondelproducto;
    }

    public Integer getNumerodeidentificaciondelproducto() {
        return numerodeidentificaciondelproducto;
    }

    public void setNumerodeidentificaciondelproducto(Integer numerodeidentificaciondelproducto) {
        this.numerodeidentificaciondelproducto = numerodeidentificaciondelproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getContenidodelproducto() {
        return contenidodelproducto;
    }

    public void setContenidodelproducto(String contenidodelproducto) {
        this.contenidodelproducto = contenidodelproducto;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerodeidentificaciondelproducto != null ? numerodeidentificaciondelproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.numerodeidentificaciondelproducto == null && other.numerodeidentificaciondelproducto != null) || (this.numerodeidentificaciondelproducto != null && !this.numerodeidentificaciondelproducto.equals(other.numerodeidentificaciondelproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClass.Productos[ numerodeidentificaciondelproducto=" + numerodeidentificaciondelproducto + " ]";
    }

    public String getCodigodeBarras() {
        return codigodeBarras;
    }

    public void setCodigodeBarras(String codigodeBarras) {
        this.codigodeBarras = codigodeBarras;
    }

}
