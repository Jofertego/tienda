package org.example.dao;

import org.example.model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO{

    private final Connection connection;

    public EmpleadoDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void guardar(Empleado empleado){
        String sql = "INSERT INTO empleados (identificacion, nombre, edad) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, empleado.getIdentificacion());
            statement.setString(2, empleado.getNombre());
            statement.setInt(3, empleado.getEdad());
            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Empleado> listar(){
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT id_empleado, identificacion, nombre, edad FROM empleados";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                empleados.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return empleados;
    }
}