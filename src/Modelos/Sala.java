package Modelos;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * La clase Sala representa una sala dentro de un gimnasio, donde se realizan
 * clases. Cada sala tiene un identificador único y puede contener varias
 * clases. * @author adriana
 */
public class Sala {

    private int id;
    private ArrayList<Clase> clases = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    /**
     * Constructor de la clase Sala.
     *
     * @param id El identificador único de la sala.
     */
    public Sala(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador único de la sala.
     *
     * @return El identificador único de la sala.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la lista de clases que se realizan en la sala.
     *
     * @return La lista de clases de la sala.
     */
    public ArrayList<Clase> getClases() {
        return clases;
    }

    /**
     * Establece el identificador único de la sala.
     *
     * @param id El identificador único de la sala.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la lista de clases que se realizan en la sala.
     *
     * @param clases La lista de clases de la sala.
     */
    public void setClases(ArrayList<Clase> clases) {
        this.clases = clases;
    }

    /**
     * Convierte la sala a formato CSV.
     *
     * @return Una cadena de texto que representa la sala en formato CSV.
     */
    public String salaToCsv() {
        return id + "";
    }

    /**
     * Muestra por consola la información de todas las clases que se realizan en
     * la sala.
     */
    public void mostrarClases() {
        for (Clase it : clases) {
            System.out.println(it.claseToCsv());
        }
    }

    /**
     * Obtiene el último identificador de clase asignado en la sala.
     *
     * @return El último identificador de clase asignado.
     */
    public int ultimoId() {
        if (clases.isEmpty()) {//si no existen salas el primer id será 0
            return 0;
        }
        return clases.get(clases.size() - 1).getId();//devuelve el id de la clase en ultima posición 
    }

    /**
     * Permite al usuario seleccionar una clase específica de la sala.
     *
     * @return La clase seleccionada por el usuario.
     */
    public Clase seleccionarClase() {//hay que ver como controlar que no me met una sala inexistente
        int idClase;
        do {
            System.out.println("Introduce el id de la clase: ");
            idClase = Integer.valueOf(sc.nextLine());
            for (Clase clase : clases) {
                if (clase.getId() == idClase) {
                    return clase;
                }
            }
            System.out.println("ID invalido...");
        } while (!existeClase(idClase));//si pongo while true funciona igual no?
        return null;//qque devuelvo si no?
    }

    /**
     * Verifica si una clase con un determinado identificador está presente en
     * la sala.
     *
     * @param idClase El identificador de la clase a verificar.
     * @return true si la clase existe en la sala, false en caso contrario.
     */
    public boolean existeClase(int idClase) {
        for (Clase clase : clases) {
            if (idClase == clase.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene todos los horarios ocupados de la sala para un día específico.
     *
     * @param dia El día de la semana para el que se desea obtener los horarios
     * ocupados.
     * @return Una lista de horarios ocupados para el día especificado.
     */
    public ArrayList<Horario> horariosOcupadosSalaPorDia(DiaSemana dia) {
        ArrayList<Horario> salida = new ArrayList<>();
        for (Clase clase : clases) {
            for (Horario horario : clase.horariosPorDia(dia)) {
                salida.add(horario);
            }
        }
        return salida;
    }

}
