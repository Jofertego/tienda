package org.example.dao;

import org.example.model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO{

    private final Connection connection;

    public EmpleadoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (identificacion, nombre, edad) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.getIdentificacion());
            statement.setString(2, empleado.getNombre());
            statement.setInt(3, empleado.getEdad());

            statement.executeUpdate();
            System.out.println("Empleado guardado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Empleado empleado1 = new Empleado();
                empleado1.setIdEmpleado(resultSet.getInt("id_empleado"));
                empleado1.setIdentificacion(resultSet.getString("identificacion"));
                empleado1.setNombre(resultSet.getString("nombre"));
                empleado1.setEdad(resultSet.getInt("edad"));

                empleados.add(empleado1);
                System.out.println("Empleado listado correctamente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }

    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET identificacion = ?, nombre = ?, edad = ? WHERE id_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.getIdentificacion());
            statement.setString(2, empleado.getNombre());
            statement.setInt(3, empleado.getEdad());
            statement.setInt(4, empleado.getIdEmpleado());

            statement.executeUpdate();
            System.out.println("Empleado actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id_empleado) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_empleado);

            statement.executeUpdate();
            System.out.println("Empleado eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}