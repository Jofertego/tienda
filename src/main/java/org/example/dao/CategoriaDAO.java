package org.example.dao;
import org.example.model.Categoria;
import java.util.List;

public interface CategoriaDAO{
    void guardar(Categoria categoria);
    List<Categoria> listar();
    Categoria buscarPorId(int id);
    void actualizar(Categoria categoria);
    void eliminar(int id);
}