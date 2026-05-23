package com.compensar.cpg1.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ordenes")
public class Pedido {

    @Id
    @Column(name = "id")
    private String idPedido;

    @Column(name = "user_id", nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "created_at")
    private OffsetDateTime fechaPedido;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "descuento")
    private BigDecimal descuento;

    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public String getCodigo() { return idPedido; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDateTime getFechaPedido() { return fechaPedido != null ? fechaPedido.toLocalDateTime() : null; }
    public void setFechaPedido(LocalDateTime f) {}
    public String getEstado() { return "COMPLETADO"; }
    public void setEstado(String e) {}
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public Integer getCantidadItems() { return 0; }
    public void setCantidadItems(Integer i) {}
}