package Modelos;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Representa un cliente del gimnasio.
 * Hereda de la clase {Usuario}.
 * Contiene información adicional específica de un cliente como su edad.
 * 
 * @see Usuario
 * @see Administrador
 * @see Gimnasio
 * @see Clase
 * @see Horario
 * @see Sala
 * @see Operativa.OperativaCliente
 *
 * @author adriana
 */
public class Cliente extends Usuario {

    private int edad;

    private static Scanner sc = new Scanner(System.in);

    /**
     * Constructor para crear una nueva instancia de Cliente.
     *
     * @param id El identificador único del cliente.
     * @param nombre El nombre del cliente.
     * @param edad La edad del cliente.
     */
    public Cliente(int id, String nombre, int edad) {
        super(id, nombre);
        this.edad = edad;
    }

    /**
     * Obtiene la edad del cliente.
     *
     * @return La edad del cliente.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene el ID del cliente.
     *
     * @return El ID del cliente.
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Establece el ID del cliente.
     *
     * @param id El nuevo ID del cliente.
     */
    public void setId(int id) {
        super.setId(id);
    }

    /**
     * Convierte los atributos del cliente a una cadena en formato CSV.
     *
     * @return Una cadena en formato CSV que representa el cliente.
     */
    public String clienteToCsv() {
        return super.getId() + "," + super.getNombre() + "," + edad;
    }

    /**
     * Crea una instancia de Cliente a partir de una cadena en formato CSV.
     *
     * @param datos Una cadena en formato CSV que representa un cliente.
     * @return Una nueva instancia de Cliente.
     */
    public static Cliente crearClienteLeido(String datos) {
        String[] aux = datos.split(",");
        int id = Integer.valueOf(aux[0]);
        String nombre = aux[1];
        int edad = Integer.valueOf(aux[2]);
        Cliente salida = new Cliente(id, nombre, edad);
        return salida;
    }

    /**
     * Crea una instancia de Cliente a partir de datos introducidos por teclado.
     *
     * @param idCliente El ID del nuevo cliente.
     * @return Una nueva instancia de Cliente.
     */
    public static Cliente crearClienteTeclado(int idCliente) {
        System.out.println("Introduce nombre:");
        String nombre = sc.nextLine();

        System.out.println("Introduce edad: ");
        int edad = Integer.valueOf(sc.nextLine());

        return new Cliente(idCliente, nombre, edad);
    }

    /**
     * Convierte la información del cliente a una cadena legible.
     *
     * @return Una cadena legible que representa el cliente.
     */
    public String clienteToString() {
        return "ID: " + super.getId() + "   Nombre: " + super.getNombre();
    }
}
