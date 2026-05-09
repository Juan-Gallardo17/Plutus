package com.maxcode.plutus.model;

public class Meta {
    private int id;
    private String nombre;
    private double montoObjetivo;
    private double montoActual;
    private String fechaLimite;

    public Meta() {}

    public Meta(String nombre, double montoObjetivo, double montoActual, String fechaLimite) {
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
        this.montoActual = montoActual;
        this.fechaLimite = fechaLimite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMontoObjetivo() { return montoObjetivo; }
    public void setMontoObjetivo(double montoObjetivo) { this.montoObjetivo = montoObjetivo; }

    public double getMontoActual() { return montoActual; }
    public void setMontoActual(double montoActual) { this.montoActual = montoActual; }

    public String getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }

    public int getPorcentaje() {
        if (montoObjetivo == 0) return 0;
        return (int) ((montoActual / montoObjetivo) * 100);
    }
}