/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapp.persistences;

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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author manager
 */
@Entity
@Table(name = "users", catalog = "webdb", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre_completo"}),
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"nickname"})})
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByIdUsers", query = "SELECT u FROM Users u WHERE u.idUsers = :idUsers"),
    @NamedQuery(name = "Users.findByNombreCompleto", query = "SELECT u FROM Users u WHERE u.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByNickname", query = "SELECT u FROM Users u WHERE u.nickname = :nickname")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_users", nullable = false)
    private Integer idUsers;
    @Basic(optional = false)
    @Column(name = "nombre_completo", nullable = false, length = 256)
    private String nombreCompleto;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Basic(optional = false)
    @Column(name = "nickname", nullable = false, length = 32)
    private String nickname;
    @Basic(optional = false)
    @Lob
    @Column(name = "password", nullable = false)
    private byte[] password;

    public Users() {
    }

    public Users(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public Users(Integer idUsers, String nombreCompleto, String email, String nickname, byte[] password) {
        this.idUsers = idUsers;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsers != null ? idUsers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.idUsers == null && other.idUsers != null) || (this.idUsers != null && !this.idUsers.equals(other.idUsers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapp.persistences.Users[ idUsers=" + idUsers + " ]";
    }

}
