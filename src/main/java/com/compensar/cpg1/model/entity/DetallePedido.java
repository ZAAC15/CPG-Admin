package com.compensar.cpg1.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orden_items")
public class DetallePedido {

    @Id
    @Column(name = "id")
    private String idDetalle;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Pedido pedido;

    @Column(name = "nombre")
    private String nombreProducto;

    @Column(name = "precio")
    private BigDecimal precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "imagen_res")
    private String imagenUrl;

    public String getIdDetalle() { return idDetalle; }
    public void setIdDetalle(String idDetalle) { this.idDetalle = idDetalle; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Producto getProducto() { return null; }
    public void setProducto(Producto p) {}
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal p) { this.precioUnitario = p; }
    public BigDecimal getSubtotal() {
        if (precioUnitario != null && cantidad != null)
            return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        return BigDecimal.ZERO;
    }
    public void setSubtotal(BigDecimal s) {}
}