package com.maxcode.plutus.model;

public class Gasto {
    private int id;
    private String nombre;
    private double monto;
    private String categoria;
    private String fecha;

    public Gasto() {}

    public Gasto(String nombre, double monto, String categoria, String fecha) {
        this.nombre = nombre;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}