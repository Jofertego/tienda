package org.example.dao;

import org.example.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO{

    private final Connection connection;

    public CategoriaDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void guardar(Categoria categoria){
        String sql = "INSERT INTO categoria (nombre_categoria) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, categoria.getNombreCategoria());
            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Categoria> listar(){
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre_categoria FROM categoria";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                categorias.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return categorias;
    }
}