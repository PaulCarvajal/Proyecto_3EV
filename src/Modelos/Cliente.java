package Modelos;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adriana
 */
public class Cliente extends Usuario {

    private int edad;

    private static Scanner sc = new Scanner(System.in);

    public Cliente(int id, String nombre, int edad) {
        super(id, nombre);
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }
    
    public int getId(){
        return super.getId();
    }
    
    public void setId(int id){
        super.setId(id);
    }

    public String clienteToCsv() {
        return super.getId() + "," + super.getNombre() + "," + edad;
    }

    public static Cliente crearClienteLeido(String datos) {
        String[] aux = datos.split(",");
        int id = Integer.valueOf(aux[0]);
        String nombre = aux[1];
        int edad = Integer.valueOf(aux[2]);
        Cliente salida = new Cliente(id, nombre, edad);
        return salida;
    }

    public static Cliente crearClienteTeclado(int idCliente) {       

        System.out.println("Introduce nombre:");
        String nombre = sc.nextLine();

        System.out.println("Introduce edad: ");
        int edad = Integer.valueOf(sc.nextLine());

        return new Cliente(idCliente, nombre, edad);

    }
    /*
    public int asignarIdCliente() {
        int idMayor = -1;
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).ultimoId() > idMayor) {
                idMayor = salas.get(i).ultimoId();
            }
        }
        return idMayor + 1;
    }

    public void reasignarIdCliente() {
        int id = 0;
        for (Sala sala : salas) {
            for (int i = 0; i < sala.getClases().size(); i++) {
                sala.getClases().get(i).setId(id);
                id++;
            }
        }
    }*/

    public String clienteToString() {
        return "ID: " + super.getId() + "   Nombre: " + super.getNombre() ;
    }
}
