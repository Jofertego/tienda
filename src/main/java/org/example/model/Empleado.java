package org.example.model;

public class Empleado{

    private int idEmpleado;
    private String identificacion;
    private String nombre;
    private int edad;

    public Empleado(){
    }

    public Empleado(int idEmpleado, String identificacion, String nombre, int edad){
        this.idEmpleado = idEmpleado;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getIdEmpleado(){
        return idEmpleado;
    }
    public void setIdEmpleado(int idEmpleado){
        this.idEmpleado = idEmpleado;
    }

    public String getIdentificacion(){
        return identificacion;
    }
    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        this.edad = edad;
    }
}