package com.maxcode.plutus.model;

public class Deuda {
    private int id;
    private String nombre;
    private double monto;
    private String fechaLimite;
    private String estado;

    public Deuda() {}

    public Deuda(String nombre, double monto, String fechaLimite, String estado) {
        this.nombre = nombre;
        this.monto = monto;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}