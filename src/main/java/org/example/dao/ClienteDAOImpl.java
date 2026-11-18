package org.example.dao;

import org.example.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{

    private final Connection connection;

    public ClienteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, telefono) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());

            statement.executeUpdate();
            System.out.println("Cliente guardado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Cliente cliente1 = new Cliente();
                cliente1.setIdCliente(resultSet.getInt("id_cliente"));
                cliente1.setNombre(resultSet.getString("nombre"));
                cliente1.setTelefono(resultSet.getString("telefono"));

                clientes.add(cliente1);
                System.out.println("Cliente listado correctamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ? WHERE id_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setInt(3, cliente.getIdCliente());

            statement.executeUpdate();
            System.out.println("Cliente actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id_cliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_cliente);

            statement.executeUpdate();
            System.out.println("Cliente eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}