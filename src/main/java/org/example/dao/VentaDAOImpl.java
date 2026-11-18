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

    public VentaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Venta venta) {
        String sql = "INSERT INTO ventas (fecha, cantidad, total, id_empleado, id_cliente, id_producto) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(venta.getFecha()));
            statement.setInt(2, venta.getCantidad());
            statement.setDouble(3, venta.getTotal());
            statement.setInt(4, venta.getEmpleado().getIdEmpleado());
            statement.setInt(5, venta.getCliente().getIdCliente());
            statement.setInt(6, venta.getProducto().getIdProducto());

            statement.executeUpdate();
            System.out.println("Venta guardada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Venta> listar() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Venta venta1 = new Venta();
                venta1.setIdVenta(resultSet.getInt("id_venta"));
                venta1.setFecha(resultSet.getDate("fecha").toLocalDate());
                venta1.setCantidad(resultSet.getInt("cantidad"));
                venta1.setTotal(resultSet.getDouble("total"));

                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(resultSet.getInt("id_empleado"));
                venta1.setEmpleado(empleado);

                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                venta1.setCliente(cliente);

                Producto producto = new Producto();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                venta1.setProducto(producto);

                ventas.add(venta1);
                System.out.println("Venta listada correctamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventas;
    }

    @Override
    public void actualizar(Venta venta) {
        String sql = "UPDATE ventas SET fecha = ?, cantidad = ?, total = ?, id_empleado = ?, id_cliente = ?, id_producto = ? WHERE id_venta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(venta.getFecha()));
            statement.setInt(2, venta.getCantidad());
            statement.setDouble(3, venta.getTotal());
            statement.setInt(4, venta.getEmpleado().getIdEmpleado());
            statement.setInt(5, venta.getCliente().getIdCliente());
            statement.setInt(6, venta.getProducto().getIdProducto());
            statement.setInt(7, venta.getIdVenta());

            statement.executeUpdate();
            System.out.println("Venta actualizada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id_venta) {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_venta);

            statement.executeUpdate();
            System.out.println("Venta eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}