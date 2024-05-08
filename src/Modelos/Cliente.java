package Modelos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adriana
 */
public class Cliente {
    private int id;
    private String nombre;
    private int edad;

    public Cliente(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }
    
    
    
    public String clienteToCsv(){
        return id + "," + nombre + "," + edad;
    }
}
