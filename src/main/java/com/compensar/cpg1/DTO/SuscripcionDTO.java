package com.compensar.cpg1.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SuscripcionDTO {

    private String idSuscripcion;
    private String codigo;
    private String idCliente;
    private String nombreCliente;
    private String idPlan;
    private String nombrePlan;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaRenueva;
    private BigDecimal monto;

    public String getIdSuscripcion() { return idSuscripcion; }
    public void setIdSuscripcion(String idSuscripcion) { this.idSuscripcion = idSuscripcion; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getIdPlan() { return idPlan; }
    public void setIdPlan(String idPlan) { this.idPlan = idPlan; }
    public String getNombrePlan() { return nombrePlan; }
    public void setNombrePlan(String nombrePlan) { this.nombrePlan = nombrePlan; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaRenueva() { return fechaRenueva; }
    public void setFechaRenueva(LocalDate fechaRenueva) { this.fechaRenueva = fechaRenueva; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}