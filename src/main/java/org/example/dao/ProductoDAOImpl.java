package org.example.dao;

import org.example.model.Categoria;
import org.example.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{

    private final Connection connection;

    public ProductoDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void guardar(Producto producto){
        String sql = "INSERT INTO productos (nombre, precio, cantidad, id_categoria) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCantidad());
            statement.setInt(4, producto.getCategoria().getIdCategoria());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> listar(){
        List<Producto> productos = new ArrayList<>();
        String sql = """
                SELECT 
                    p.id_producto AS prod_id,
                    p.nombre AS prod_nombre,
                    p.precio AS prod_precio,
                    p.cantidad AS prod_cantidad,
                    c.id_categoria AS cat_id,
                    c.nombre_categoria AS cat_nombre
                FROM productos p
                INNER JOIN categoria c ON p.id_categoria = c.id_categoria
                """;

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                Categoria categoria = new Categoria(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nombre")
                );

                productos.add(new Producto(
                        rs.getInt("prod_id"),
                        rs.getString("prod_nombre"),
                        rs.getDouble("prod_precio"),
                        rs.getInt("prod_cantidad"),
                        categoria
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return productos;
    }
}