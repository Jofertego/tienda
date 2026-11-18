package org.example.dao;

import org.example.model.Cliente;
import org.example.model.Empleado;
import org.example.model.Producto;
import org.example.model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO{

    private final Connection connection;

    public VentaDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void guardar(Venta venta){
        String sql = "INSERT INTO ventas (fecha, cantidad, total, id_empleado, id_cliente, id_producto) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, Date.valueOf(venta.getFecha()));
            statement.setInt(2, venta.getCantidad());
            statement.setDouble(3, venta.getTotal());
            statement.setInt(4, venta.getEmpleado().getIdEmpleado());
            statement.setInt(5, venta.getCliente().getIdCliente());
            statement.setInt(6, venta.getProducto().getIdProducto());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Venta> listar(){
        List<Venta> ventas = new ArrayList<>();
        String sql = """
                SELECT 
                    v.id_venta AS v_id,
                    v.fecha AS v_fecha,
                    v.cantidad AS v_cantidad,
                    v.total AS v_total,
                    
                    e.id_empleado AS e_id,
                    e.identificacion AS e_identificacion,
                    e.nombre AS e_nombre,
                    e.edad AS e_edad,
                    
                    c.id_cliente AS c_id,
                    c.nombre AS c_nombre,
                    c.telefono AS c_telefono,
                    
                    p.id_producto AS p_id,
                    p.nombre AS p_nombre,
                    p.precio AS p_precio,
                    p.cantidad AS p_stock
                FROM ventas v
                INNER JOIN empleados e ON v.id_empleado = e.id_empleado
                INNER JOIN clientes c ON v.id_cliente = c.id_cliente
                INNER JOIN productos p ON v.id_producto = p.id_producto
                """;

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                Empleado empleado = new Empleado(
                        rs.getInt("e_id"),
                        rs.getString("e_identificacion"),
                        rs.getString("e_nombre"),
                        rs.getInt("e_edad")
                );

                Cliente cliente = new Cliente(
                        rs.getInt("c_id"),
                        rs.getString("c_nombre"),
                        rs.getString("c_telefono")
                );

                Producto producto = new Producto(
                        rs.getInt("p_id"),
                        rs.getString("p_nombre"),
                        rs.getDouble("p_precio"),
                        rs.getInt("p_stock"),
                        null // Opcional: puedes mapear la categoría completa si la requieres también aquí con un JOIN extra
                );

                Venta venta = new Venta(
                        rs.getInt("v_id"),
                        rs.getDate("v_fecha").toLocalDate(),
                        rs.getInt("v_cantidad"),
                        rs.getDouble("v_total"),
                        empleado,
                        cliente,
                        producto
                );

                ventas.add(venta);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return ventas;
    }
}