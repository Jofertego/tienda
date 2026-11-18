package org.example.dao;
import org.example.model.Empleado;
import java.util.List;

public interface EmpleadoDAO{
    void guardar(Empleado empleado);
    List<Empleado> listar();
    void actualizar(Empleado empleado);
    void eliminar(int id);
}