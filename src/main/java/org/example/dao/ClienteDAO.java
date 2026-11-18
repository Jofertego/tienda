package org.example.dao;
import org.example.model.Cliente;
import java.util.List;

public interface ClienteDAO{
    void guardar(Cliente cliente);
    List<Cliente> listar();
    void actualizar(Cliente cliente);
    void eliminar(int id);
}