package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.util.conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner usuario = new Scanner(System.in);
        Connection conn = null;
        try {
            conn = conexion.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CategoriaDAO categoriaDAO = new CategoriaDAOImpl(conn);
        ClienteDAO clienteDAO = new ClienteDAOImpl(conn);
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(conn);
        ProductoDAO productoDAO = new ProductoDAOImpl(conn);
        VentaDAO ventaDAO = new VentaDAOImpl(conn);

        int opcion;
        int programa=1;
        while (programa==1){
            System.out.println(""" 
            === MENU PRINCIPAL TIENDA ===
            1. Crear Categoría
            2. Crear Cliente
            3. Crear Empleado y calcular turno (Llegada/Salida)
            4. Crear Producto (con ID Categoría)
            5. Crear Venta (con ID Empleado, Cliente y Producto)
            6. Listar todo");
            7. Editar o Eliminar cualquier objeto
            0. Salir
            """);
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(usuario.nextLine());

            switch (opcion) {
                case 1: {
                    System.out.print("Nombre categoría: ");
                    categoriaDAO.guardar(new Categoria(usuario.nextLine()));
                }
                case 2: {
                    System.out.print("Nombre cliente: ");
                    String nom = usuario.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = usuario.nextLine();
                    clienteDAO.guardar(new Cliente(nom, tel));
                }
                case 3: {
                    System.out.print("Identificación: ");
                    String cc = usuario.nextLine();
                    System.out.print("Nombre: ");
                    String nom = usuario.nextLine();
                    System.out.print("Edad: ");
                    int edad = Integer.parseInt(usuario.nextLine());

                    Empleado emp = new Empleado(0, cc, nom, edad);

                    System.out.print("Hora llegada: ");
                    emp.setHoraLlegada(LocalTime.parse(usuario.nextLine()));
                    System.out.print("Hora salida: ");
                    emp.setHoraSalida(LocalTime.parse(usuario.nextLine()));

                    empleadoDAO.guardar(emp);
                    System.out.println("Tiempo trabajado por " + emp.getNombre() + ": " + emp.calcularTiempoTrabajado());
                }
                case 4: {
                    categoriaDAO.listar().forEach(c -> System.out.println(c.getIdCategoria() + " - " + c.getNombreCategoria()));
                    System.out.print("ID Categoría: ");
                    Categoria cat = new Categoria();
                    cat.setIdCategoria(Integer.parseInt(usuario.nextLine()));

                    System.out.print("Nombre producto: ");
                    String nom = usuario.nextLine();
                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(usuario.nextLine());
                    System.out.print("Cantidad: ");
                    int cant = Integer.parseInt(usuario.nextLine());

                    productoDAO.guardar(new Producto(0, nom, precio, cant, cat));
                }
                case 5: {
                    empleadoDAO.listar().forEach(e -> System.out.println("Empleado [" + e.getIdEmpleado() + "] " + e.getNombre()));
                    System.out.print("ID Empleado: ");
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(Integer.parseInt(usuario.nextLine()));

                    clienteDAO.listar().forEach(c -> System.out.println("Cliente [" + c.getIdCliente() + "] " + c.getNombre()));
                    System.out.print("ID Cliente: ");
                    Cliente cli = new Cliente();
                    cli.setIdCliente(Integer.parseInt(usuario.nextLine()));

                    productoDAO.listar().forEach(p -> System.out.println("Producto [" + p.getIdProducto() + "] " + p.getNombre() + " ($" + p.getPrecio() + ")"));
                    System.out.print("ID Producto: ");
                    int idProd = Integer.parseInt(usuario.nextLine());

                    // Buscamos el precio del producto elegido en la lista
                    double precioUnitario = 0;
                    for (Producto p : productoDAO.listar()) {
                        if (p.getIdProducto() == idProd) {
                            precioUnitario = p.getPrecio();
                            break;
                        }
                    }

                    Producto prod = new Producto();
                    prod.setIdProducto(idProd);

                    System.out.print("Cantidad vendida: ");
                    int cant = Integer.parseInt(usuario.nextLine());
                    double total = precioUnitario * cant;

                    ventaDAO.guardar(new Venta(0, LocalDate.now(), cant, total, emp, cli, prod));
                    System.out.println("Total calculado: $" + total);
                }
                case 6: {
                    System.out.println("\n--- CATEGORÍAS ---");
                    categoriaDAO.listar().forEach(c -> System.out.println(c.getIdCategoria() + ": " + c.getNombreCategoria()));

                    System.out.println("\n--- CLIENTES ---");
                    clienteDAO.listar().forEach(c -> System.out.println(c.getIdCliente() + ": " + c.getNombre() + " (" + c.getTelefono() + ")"));

                    System.out.println("\n--- EMPLEADOS ---");
                    empleadoDAO.listar().forEach(e -> System.out.println(e.getIdEmpleado() + ": " + e.getNombre() + " - CC: " + e.getIdentificacion()));

                    System.out.println("\n--- PRODUCTOS ---");
                    productoDAO.listar().forEach(p -> System.out.println(p.getIdProducto() + ": " + p.getNombre() + " | $" + p.getPrecio() + " | Stock: " + p.getCantidad() + " | ID Cat: " + p.getCategoria().getIdCategoria()));

                    System.out.println("\n--- VENTAS ---");
                    ventaDAO.listar().forEach(v -> System.out.println(v.getIdVenta() + ": Fecha " + v.getFecha() + " | Cant: " + v.getCantidad() + " | Total: $" + v.getTotal() + " | ID Emp: " + v.getEmpleado().getIdEmpleado() + " | ID Cli: " + v.getCliente().getIdCliente() + " | ID Prod: " + v.getProducto().getIdProducto()));
                }
                case 7: {
                    System.out.print("¿Que deseas modificar? (1.Categoría, 2.Cliente, 3.Empleado, 4.Producto, 5.Venta): ");
                    int tipo = Integer.parseInt(usuario.nextLine());
                    System.out.print("¿Que accion desea hacer? (1.Actualizar / 2.Eliminar): ");
                    int accion = Integer.parseInt(usuario.nextLine());
                    System.out.print("Digite el ID del registro: ");
                    int id = Integer.parseInt(usuario.nextLine());

                    if (accion == 2) {
                        if (tipo == 1) categoriaDAO.eliminar(id);
                        else if (tipo == 2) clienteDAO.eliminar(id);
                        else if (tipo == 3) empleadoDAO.eliminar(id);
                        else if (tipo == 4) productoDAO.eliminar(id);
                        else if (tipo == 5) ventaDAO.eliminar(id);
                    } else if (accion == 1) {
                        if (tipo == 1) {
                            System.out.print("Nuevo nombre categoría: ");
                            categoriaDAO.actualizar(new Categoria(id, usuario.nextLine()));
                        } else if (tipo == 2) {
                            System.out.print("Nuevo nombre: ");
                            String nom = usuario.nextLine();
                            System.out.print("Nuevo teléfono: ");
                            clienteDAO.actualizar(new Cliente(id, nom, usuario.nextLine()));
                        } else if (tipo == 3) {
                            System.out.print("Nueva identificación: ");
                            String cc = usuario.nextLine();
                            System.out.print("Nuevo nombre: ");
                            String nom = usuario.nextLine();
                            System.out.print("Nueva edad: ");
                            empleadoDAO.actualizar(new Empleado(id, cc, nom, Integer.parseInt(usuario.nextLine())));
                        } else if (tipo == 4) {
                            System.out.print("Nuevo nombre: ");
                            String nom = usuario.nextLine();
                            System.out.print("Nuevo precio: ");
                            double pre = Double.parseDouble(usuario.nextLine());
                            System.out.print("Nueva cantidad: ");
                            int cant = Integer.parseInt(usuario.nextLine());
                            System.out.print("Nuevo ID Categoría: ");
                            Categoria cat = new Categoria();
                            cat.setIdCategoria(Integer.parseInt(usuario.nextLine()));
                            productoDAO.actualizar(new Producto(id, nom, pre, cant, cat));
                        } else if (tipo == 5) {
                            System.out.print("Nuevo ID Empleado: ");
                            Empleado emp = new Empleado();
                            emp.setIdEmpleado(Integer.parseInt(usuario.nextLine()));
                            System.out.print("Nuevo ID Cliente: ");
                            Cliente cli = new Cliente();
                            cli.setIdCliente(Integer.parseInt(usuario.nextLine()));
                            System.out.print("Nuevo ID Producto: ");
                            Producto prod = new Producto();
                            prod.setIdProducto(Integer.parseInt(usuario.nextLine()));
                            System.out.print("Nueva cantidad: ");
                            int cant = Integer.parseInt(usuario.nextLine());
                            System.out.print("Nuevo total: ");
                            double tot = Double.parseDouble(usuario.nextLine());
                            ventaDAO.actualizar(new Venta(id, LocalDate.now(), cant, tot, emp, cli, prod));
                        }
                    }
                }
                case 0: {
                        System.out.println("Saliendo...");
                programa+=1;
                }
            }
        }
    }
}