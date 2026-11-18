package org.example.util;

import java.sql.*;

public class conexion {

    private static final String URL = "jdbc:mysql://localhost:3302/tienda";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}