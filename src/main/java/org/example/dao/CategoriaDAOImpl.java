package org.example.dao;

import org.example.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO{
    private final Connection connection;

    public CategoriaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre_categoria) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoria.getNombreCategoria());

            statement.executeUpdate();
            System.out.println("Categoria guardada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Categoria categoria1 = new Categoria();
                categoria1.setIdCategoria(resultSet.getInt("id_categoria"));
                categoria1.setNombreCategoria(resultSet.getString("nombre_categoria"));

                categorias.add(categoria1);
                System.out.println("Categoria listada correctamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre_categoria = ? WHERE id_categoria = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoria.getNombreCategoria());
            statement.setInt(2, categoria.getIdCategoria());

            statement.executeUpdate();
            System.out.println("Categoria actualizada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id_categoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_categoria);

            statement.executeUpdate();
            System.out.println("Categoria eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}