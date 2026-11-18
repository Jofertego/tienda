package org.example.model;

import java.time.Duration;
import java.time.LocalTime;

public class Empleado {

    private int idEmpleado;
    private String identificacion;
    private String nombre;
    private int edad;
    private LocalTime horaLlegada;
    private LocalTime horaSalida;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String identificacion, String nombre, int edad) {
        this.idEmpleado = idEmpleado;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String calcularTiempoTrabajado() {
        if (horaLlegada == null || horaSalida == null) {
            return "Falta registrar hora de llegada o salida.";
        }
        Duration duracion = Duration.between(horaLlegada, horaSalida);
        if (duracion.isNegative()) {
            duracion = duracion.plusHours(24); // Por si el turno cruza la medianoche
        }
        long horas = duracion.toHours();
        long minutos = duracion.toMinutesPart();
        return horas + " horas y " + minutos + " minutos";
    }
}