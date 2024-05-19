/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *La clase Administrador representa un usuario con privilegios de administrador.
 * Hereda de la clase {Usuario} y se inicializa con un ID fijo de 0 y el nombre "admin".
 * 
 * @author adriana
 */
public class Administrador extends Usuario {
    /**
     * Crea una nueva instancia de Administrador con ID 0 y nombre "admin".
     */
    public Administrador() {
        super(0, "admin");
    }

}
