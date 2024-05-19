/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 * La clase Usuario representa un usuario genérico del sistema. Cada usuario
 * tiene un identificador único y un nombre asociado.
 *
 * @author adriana
 */
public class Usuario {
    
    private int id;
    private String nombre;

    /**
     * Constructor de la clase Usuario.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre del usuario.
     */
    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El identificador único del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param id El identificador único del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
