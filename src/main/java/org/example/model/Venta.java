package org.example.model;

import java.time.LocalDate;

public class Venta{

    private int idVenta;
    private LocalDate fecha;
    private int cantidad;
    private double total;
    private Empleado empleado;
    private Cliente cliente;
    private Producto producto;

    public Venta(){
    }

    public Venta(int idVenta, LocalDate fecha, int cantidad, double total, Empleado empleado, Cliente cliente, Producto producto){
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.total = total;
        this.empleado = empleado;
        this.cliente = cliente;
        this.producto = producto;
    }

    public int getIdVenta(){
        return idVenta;
    }
    public void setIdVenta(int idVenta){
        this.idVenta = idVenta;
    }

    public LocalDate getFecha(){
        return fecha;
    }
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public int getCantidad(){
        return cantidad;
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public double getTotal(){
        return total;
    }
    public void setTotal(double total){
        this.total = total;
    }

    public Empleado getEmpleado(){
        return empleado;
    }
    public void setEmpleado(Empleado empleado){
        this.empleado = empleado;
    }

    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Producto getProducto(){
        return producto;
    }
    public void setProducto(Producto producto){
        this.producto = producto;
    }
}