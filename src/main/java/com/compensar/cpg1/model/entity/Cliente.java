package com.compensar.cpg1.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "\"Usuarios\"")
public class Cliente {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private String idCliente;

    @Column(name = "usuario")
    private String nombre;

    @Column(name = "nombre")
    private String apellido;

    @Column(name = "correo", nullable = false, unique = true)
    private String email;

    @Column(name = "apellidos")
    private String passwordHash;

    @Column(name = "celular")
    private String telefono;

    @Column(name = "rol")
    private String rol;

    @Column(name = "foto_url")
    private String estado;

    @Column(name = "created_at")
    private OffsetDateTime fechaIngreso;

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaIngreso() { return fechaIngreso != null ? fechaIngreso.toLocalDate() : null; }
    public void setFechaIngreso(LocalDate f) {}
}