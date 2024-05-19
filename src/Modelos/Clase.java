package Modelos;

import Operativa.OperativaComun;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * La clase Clase representa una clase en un gimnasio, incluyendo su ID, nombre,
 * capacidad y horarios. Proporciona métodos para gestionar los horarios y los
 * clientes inscritos.
 *
 * @author adriana
 */
public class Clase {

    private int id;
    private String nombre;
    private int capacidad;
    private ArrayList<Horario> horario = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    /**
     * Constructor para crear una nueva instancia de Clase.
     *
     * @param id El identificador único de la clase.
     * @param capacidad La capacidad máxima de la clase.
     * @param nombre El nombre de la clase.
     */
    public Clase(int id, int capacidad, String nombre) {
        this.id = id;
        this.capacidad = capacidad;
        this.nombre = nombre;
//        this.inscritos = new ArrayList<>();
        //si no inicializo el array me da una excepcion nullpointer al intentar añadir clientes
    }

    /**
     * Obtiene el ID de la clase.
     *
     * @return El ID de la clase.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la clase.
     *
     * @return El nombre de la clase.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la capacidad de la clase.
     *
     * @return La capacidad de la clase.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Obtiene la lista de horarios de la clase.
     *
     * @return Una lista de horarios de la clase.
     */
    public ArrayList<Horario> getHorario() {
        return horario;
    }

    /**
     * Establece el ID de la clase.
     *
     * @param id El nuevo ID de la clase.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre de la clase.
     *
     * @param nombre El nuevo nombre de la clase.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la capacidad de la clase.
     *
     * @param capacidad La nueva capacidad de la clase.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Establece la lista de horarios de la clase.
     *
     * @param horario La nueva lista de horarios de la clase.
     */
    public void setHorario(ArrayList<Horario> horario) {
        this.horario = horario;
    }

    /**
     * Convierte los atributos de la clase a una cadena en formato CSV.
     *
     * @return Una cadena en formato CSV que representa la clase.
     */
    public String claseToCsv() {
        return id + "," + capacidad + "," + nombre;
    }

    /**
     * Muestra los horarios disponibles de la clase.
     */
    public void mostrarHorario() {//muestra los disponibles
        for (Horario it : horarioDisponible()) {
            System.out.println(it.horarioToString());
        }
    }

    /**
     * Muestra los horarios especificados en la lista de entrada.
     *
     * @param entrada Una lista de horarios a mostrar.
     */
    public void mostrarHorario(ArrayList<Horario> entrada) {//muestra los que esten en el AL pasado
        for (Horario it : entrada) {
            System.out.println(it.horarioToString());
        }
    }

    /**
     * Busca los horarios en los que el cliente está inscrito.
     *
     * @param entrada El cliente para el que se buscan los horarios.
     * @return Una lista de horarios en los que el cliente está inscrito.
     */
    public ArrayList<Horario> buscarMiHorario(Cliente entrada) {
        ArrayList<Horario> misHorarios = new ArrayList<>();
        for (Horario it : horario) {
            if (it.comprobarClienteInscrito(entrada)) {
                misHorarios.add(it);
            }
        }
        return misHorarios;
    }

    /**
     * Obtiene una lista de horarios disponibles en la clase.
     *
     * @return Una lista de horarios disponibles.
     */
    public ArrayList<Horario> horarioDisponible() {
        ArrayList<Horario> salida = new ArrayList<>();
        for (Horario it : horario) {
            if (it.hueco(capacidad)) {
                salida.add(it);
            }
        }
        return salida;
    }

    /**
     * Convierte la información de la clase a una cadena legible.
     *
     * @return Una cadena legible que representa la clase.
     */
    public String claseToString() {
        return nombre + "(ID: " + id + ")";
    }

    /**
     * Selecciona un horario de la lista de entrada.
     *
     * @param entrada Una lista de horarios para seleccionar.
     * @return El horario seleccionado.
     */
    public Horario seleccionarHorario(ArrayList<Horario> entrada) {//le paso AL para poder usarlo con los metodos de borrar reserva y de eliminar 
        mostrarHorario(entrada);//muestra los de entrada
        int idHorario;

        do {
            System.out.println("Introduce el id del horario: ");
            idHorario = OperativaComun.asignarEntero();
            for (Horario horario : entrada) {//recorree los horarios pasados por parametro
                if (horario.getId() == idHorario) {
                    return horario;
                }
            }
            System.out.println("ID invalido...");
        } while (true);

    }

    /**
     * Obtiene una lista de horarios para un día específico de la semana.
     *
     * @param dia El día de la semana para el que se buscan los horarios.
     * @return Una lista de horarios para el día especificado.
     */
    public ArrayList<Horario> horariosPorDia(DiaSemana dia) {
        ArrayList<Horario> salida = new ArrayList<>();
        for (Horario horario : horario) {
            if (horario.getDia().name().equalsIgnoreCase(dia.name())) {
                salida.add(horario);
            }
        }
        return salida;
    }

    /**
     * Asigna un nuevo ID a un horario.
     *
     * @return El nuevo ID del horario.
     */
    public int asignarIdHorario() {
        if (horario.isEmpty()) {//si no existen horarios el primer id será 0
            return 0;
        }
        return horario.get(horario.size() - 1).getId() + 1;//devuelve el id del horario en ultima posición +1
    }

    /**
     * Crea una instancia de Clase a partir de una cadena en formato CSV.
     *
     * @param datos Una cadena en formato CSV que representa una clase.
     * @return Una nueva instancia de Clase.
     */
    public static Clase crearClaseLeida(String datos) {
        String[] aux = datos.split(",");
        int id = Integer.valueOf(aux[0]);
        int capacidad = Integer.valueOf(aux[1]);
        String nombre = aux[2];
        Clase salida = new Clase(id, capacidad, nombre);
        return salida;
    }

}
