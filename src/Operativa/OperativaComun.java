/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import IO.IO_CSV_Gimnasio;
import Modelos.Cliente;
import Modelos.Gimnasio;
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

 /*
Este código en Java es un ejemplo de cómo conectarse a una base de datos MySQL, 
realizar consultas y luego actualizar registros. 
     */
 /*Una transaccion sql es que las secuencias que ejecuto contra la bbdd se hagan en bloque, o se hacen toddas o ninguna. Si alguna falla no se hace ninguna. Ejemplo de banco*/
 /*
try{
conexion.setAutoCommit(false)
s.executeUpdate("Insert into...");
...
conexion.commit();
}catch (SQLException e){
//hago rollback
}
     */
//Investigar como gurdar las contraseñas(no texto plano) en bbdd
    private static Scanner sc = new Scanner(System.in);
    private static Connection con = null;

    private static File f = new File("./Registro/clases.csv");

    private static ArrayList<Sala> salas = new ArrayList<>();

    public static void iniciarPrograma(Gimnasio g1) throws IOException {
        try {
            // Establecer conexión JDBC
            String cadena_conexion = "jdbc:mysql://localhost:3306/";
            String nombre_BBDD = "proyectoprogramacion";
            String nickname = "admin";
            String contrasena = "0000";
            con = DriverManager.getConnection(cadena_conexion + nombre_BBDD, nickname, contrasena);

            salas = IO_CSV_Gimnasio.leer(f, g1);
            g1.getSalas().addAll(salas);
            
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
            
            IO_CSV_Gimnasio.escribir(f, g1.getSalas());
            System.out.println("Introduce el nombre de usuario");
            nickname = sc.nextLine();
            System.out.println("Introduce la contraseña: ");
            contrasena = sc.nextLine();

            if (loginAdmin(con, nickname, contrasena)) {
                menuAdministrador(g1);
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

}

