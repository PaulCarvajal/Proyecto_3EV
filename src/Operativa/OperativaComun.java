/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import IO.IO_CSV_Clientes;
import IO.IO_CSV_Gimnasio;
import Modelos.Clase;
import Modelos.Cliente;
import Modelos.Gimnasio;
import Modelos.Horario;
import Modelos.Sala;
import static Operativa.OperativaAdministrador.menuAdministrador;
import static Operativa.OperativaCliente.menuCliente;
import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author adriana
 */
public class OperativaComun {


    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
     */
    private static Scanner sc = new Scanner(System.in);
    private static Connection con = null;

    private static File f = new File("./Registro/clases.csv");
    private static File f_clientes = new File("./Registro/clientes.csv");

    private static ArrayList<Sala> salas = new ArrayList<>();

    private static Gimnasio g1;
    private static Sala s1;
    private static Sala s2;
    private static ArrayList<Cliente> clientes_actualizados = new ArrayList<>();

    public static void iniciarGimnasio() {
        g1 = new Gimnasio(0, "AtlasGym");

        s1 = new Sala(0);
        s2 = new Sala(1);

        g1.getSalas().add(s1);
        g1.getSalas().add(s2);
    }

    public static void iniciarPrograma() throws IOException {
        try {
            // Establecer conexión JDBC
            String cadena_conexion = "jdbc:mysql://localhost:3306/";
            String nombre_BBDD = "proyectoprogramacion";
            String nickname = "admin";
            String contrasena = "0000";
            con = DriverManager.getConnection(cadena_conexion + nombre_BBDD, nickname, contrasena);

            iniciarGimnasio();
            salas = IO_CSV_Gimnasio.leer(f, g1);
            g1.getSalas().addAll(salas);

            clientes_actualizados = IO_CSV_Clientes.leer(f_clientes);
            g1.setClientes(clientes_actualizados);

            login(con, g1);

        } catch (SQLException e) {
            System.out.println("Muy mal >:>" + e.toString());
            e.printStackTrace();//muestra toda la info de la excepcion en rojo
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
                System.out.println(ex.toString());
            }
        }
    }

    public static void login(Connection con, Gimnasio g1) throws SQLException, IOException {
        String nickname;
        String contrasena;

        do {
            IO_CSV_Clientes.escribir(f_clientes, g1.getClientes());
            IO_CSV_Gimnasio.escribir(f, g1.getSalas());
            System.out.println("Introduce el nombre de usuario");
            nickname = sc.nextLine();
            System.out.println("Introduce la contraseña: ");
            contrasena = sc.nextLine();

            if (loginAdmin(con, nickname, contrasena)) {
                menuAdministrador(g1, con, f);
            } else if (loginCliente(con, nickname, contrasena)) {
                Cliente cliente_logueado = identificarCliente(g1, con, nickname, contrasena);
                menuCliente(cliente_logueado, g1);
            } else {
                System.out.println("Credenciales no válidas...");
            }
        } while (true);

    }

    public static boolean loginAdmin(Connection con, String nickname_entrada, String contrasena_entrada) throws SQLException { //y donde llamas a esos metodos?

        String myQueryPrepared = "SELECT * FROM `usuarios` WHERE type='admin' AND nickname = ? AND password = ? ";

        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setString(1, nickname_entrada);
        pstm.setString(2, contrasena_entrada);
        ResultSet rs = pstm.executeQuery(); //que saca esto si el usuario no existe?
        /*if (rs.isBeforeFirst()) { //porque funciona aqui??? //rs.next() tb funciona
            return true;
        }
        return false;*/
        return rs.next();
    }

    public static boolean loginCliente(Connection con, String nickname_entrada, String contrasena_entrada) throws SQLException {
        String myQueryPrepared = "SELECT * FROM usuarios WHERE nickname = ? AND password = ?";

        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setString(1, nickname_entrada);
        pstm.setString(2, contrasena_entrada);
        ResultSet rs = pstm.executeQuery(); //que saca esto si el usuario no existe?
        if (rs.next() && !(rs.getString(4).equalsIgnoreCase("admin"))) { //isBeforeFirst() dice si rs está vacío NO FUNCIONA
            return true;
        }
        return false;
    }

    public static Cliente identificarCliente(Gimnasio g1, Connection con, String nickname_entrada, String contrasena_entrada) throws SQLException {
        String myQueryPrepared = "SELECT * FROM usuarios WHERE nickname = ? AND password = ?";

        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setString(1, nickname_entrada);
        pstm.setString(2, contrasena_entrada);
        ResultSet rs = pstm.executeQuery(); //que saca esto si el usuario no existe?
        int idCliente = -88;
        if (rs.next()) {
            idCliente = rs.getInt(1);
        }

        for (Cliente cliente : g1.getClientes()) {
            if (cliente.getId() == idCliente) {
                return cliente;
            }
        }
        return null;//que edvuelvo si no quiero que sea null??

    }

    /*
    public static void insertarCliente(Cliente entrada) throws SQLException {
        // Preparar la consulta SQL
        System.out.println("Introduce un nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.println("Introduce una contraseña: ");
        String contrasena = sc.nextLine();

        String myQueryPrepared = "INSERT INTO `usuarios` (`id`,`nickname`,`password`, `type`) VALUES (?, ?, ?,'')";

        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, entrada.getId());
        pstm.setString(2, usuario);
        pstm.setString(3, contrasena);

        pstm.executeUpdate();
    }

    public static void actualizarIdCliente(Cliente entrada, int id) throws SQLException {
        String myQueryPrepared = "UPDATE `usuarios` SET `id` = ? WHERE `id` = ?";
        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, id);
        pstm.setInt(2, entrada.getId());

        pstm.executeUpdate();
    }

    public static void eliminarCliente(int idClienteBorrar) throws SQLException {
        String myQueryPrepared = "DELETE FROM usuarios WHERE `id` = ?";
        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, idClienteBorrar);

        pstm.executeUpdate();
    }
     */
    public static void mostrarHorariosDisponibles(Gimnasio g1) {
        g1.mostrarClases(); //Muestra todas las clases existentes

        ArrayList<Clase> clases = g1.recorrerClases(); //guarda en un array las clases del gimnasio(TODAS)
        String continuar = "s";
        externo:
        do {
            if (!clases.isEmpty()) {
                Clase clase_seleccionada = g1.seleccionarClaseSinSaberSala();

                if (clase_seleccionada.horarioDisponible().isEmpty()) {//clase.horario disponible devuelve el array de horario de esa clase en la que hay hueco
                    System.out.println("No quedan huecos en la clase de " + clase_seleccionada.getNombre());//si entra aqui es que de la clase indicada no quedan horarios disponibles
                } else {//si hay algun horario disponible los muestro por pantalla
                    System.out.println("Horario disponible de " + clase_seleccionada.getNombre() + ":");
                    for (Horario horario : clase_seleccionada.horarioDisponible()) {
                        System.out.println(horario.horarioToString());
                    }
                }
                System.out.println("Desea consultar el horario de otra clase? (s/n)");
                continuar = sc.nextLine();
                while (true) {
                    if (continuar.equalsIgnoreCase("n")) {
                        break externo;
                    } else if (continuar.equalsIgnoreCase("s")) {
                        break;
                    } else {
                        System.out.println("Introduzca una opcion valida: (s/n)");
                        continuar = sc.nextLine();
                    }
                }

            }

        } while (continuar.equalsIgnoreCase("s"));
    }

    public static int asignarEntero() {
        int salida;
        do {
            try {
                salida = Integer.valueOf(sc.nextLine());
                return salida;
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true);
    }
}
