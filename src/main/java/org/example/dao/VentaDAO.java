package org.example.dao;
import org.example.model.Venta;
import java.util.List;

public interface VentaDAO{

    void guardar(Venta venta);

    List<Venta> listar();

}