package org.example.dao;

import org.example.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO{

    private final Connection connection;

    public ClienteDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void guardar(Cliente cliente){
        String sql = "INSERT INTO clientes (nombre, telefono) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar(){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, telefono FROM clientes";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                clientes.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("telefono")
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return clientes;
    }
}