package com.compensar.cpg1.DTO;

import java.math.BigDecimal;

public class DashboardDTO {

    private long usuariosTotales;
    private long suscripcionesActivas;
    private long productosTotales;
    private BigDecimal gananciaMensual;

    public DashboardDTO() {
    }

    public DashboardDTO(long usuariosTotales, long suscripcionesActivas,
                        long productosTotales, BigDecimal gananciaMensual) {
        this.usuariosTotales = usuariosTotales;
        this.suscripcionesActivas = suscripcionesActivas;
        this.productosTotales = productosTotales;
        this.gananciaMensual = gananciaMensual;
    }

    public long getUsuariosTotales() {
        return usuariosTotales;
    }

    public void setUsuariosTotales(long usuariosTotales) {
        this.usuariosTotales = usuariosTotales;
    }

    public long getSuscripcionesActivas() {
        return suscripcionesActivas;
    }

    public void setSuscripcionesActivas(long suscripcionesActivas) {
        this.suscripcionesActivas = suscripcionesActivas;
    }

    public long getProductosTotales() {
        return productosTotales;
    }

    public void setProductosTotales(long productosTotales) {
        this.productosTotales = productosTotales;
    }

    public BigDecimal getGananciaMensual() {
        return gananciaMensual;
    }

    public void setGananciaMensual(BigDecimal gananciaMensual) {
        this.gananciaMensual = gananciaMensual;
    }

}   