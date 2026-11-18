package org.example.dao;

import org.example.model.Categoria;
import org.example.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{

    private final Connection connection;

    public ProductoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, cantidad, id_categoria) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getCategoria().getIdCategoria());

            statement.executeUpdate();
            System.out.println("Producto guardado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Producto producto1 = new Producto();
                producto1.setIdProducto(resultSet.getInt("id_producto"));
                producto1.setNombre(resultSet.getString("nombre"));
                producto1.setPrecio(resultSet.getDouble("precio"));
                producto1.setCantidad(resultSet.getInt("cantidad"));

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(resultSet.getInt("id_categoria"));
                producto1.setCategoria(categoria);

                productos.add(producto1);
                System.out.println("Producto listado correctamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, cantidad = ?, id_categoria = ? WHERE id_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getCategoria().getIdCategoria());
            statement.setInt(5, producto.getIdProducto());

            statement.executeUpdate();
            System.out.println("Producto actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id_producto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_producto);

            statement.executeUpdate();
            System.out.println("Producto eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}