package Modelos;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Esta clase representa un horario para una clase en el gimnasio. Contiene
 * información sobre el día, la hora y los clientes inscritos en ese horario.
 *
 * @author adriana
 */
public class Horario {

    private int id;
    private ArrayList<Cliente> inscritos = new ArrayList<>();
    private DiaSemana dia;
    private int hora;

    /**
     * Crea un nuevo horario con el ID, día y hora especificados.
     *
     * @param id El ID del horario.
     * @param dia El día del horario.
     * @param hora La hora del horario.
     */
    public Horario(int id, DiaSemana dia, int hora) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
    }

    /**
     * Obtiene la lista de clientes inscritos en este horario.
     *
     * @return La lista de clientes inscritos.
     */
    public ArrayList<Cliente> getInscritos() {
        return inscritos;
    }

    /**
     * Obtiene el ID de este horario.
     *
     * @return El ID del horario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el día de la semana de este horario.
     *
     * @return El día de la semana.
     */
    public DiaSemana getDia() {
        return dia;
    }

    /**
     * Obtiene la hora de este horario.
     *
     * @return La hora del horario.
     */
    public int getHora() {
        return hora;
    }

    /**
     * Establece el ID de este horario.
     *
     * @param id El nuevo ID del horario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la lista de clientes inscritos en este horario.
     *
     * @param inscritos La nueva lista de clientes inscritos.
     */
    public void setInscritos(ArrayList<Cliente> inscritos) {
        this.inscritos = inscritos;
    }

    /**
     * Establece el día de la semana de este horario.
     *
     * @param dia El nuevo día de la semana.
     */
    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    /**
     * Establece la hora de este horario.
     *
     * @param hora La nueva hora del horario.
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * Añade un cliente a la lista de inscritos en este horario.
     *
     * @param entrada El cliente a añadir.
     */
    public void anadirCliente(Cliente entrada) {
        if (!comprobarClienteInscrito(entrada)) {
            inscritos.add(entrada);
        } else {
            System.out.println("Ya estás inscrito en esta clase.");
        }
    }

    /**
     * Elimina un cliente de la lista de inscritos en este horario.
     *
     * @param entrada El cliente a eliminar.
     */
    public void eliminarCliente(Cliente entrada) {
        inscritos.remove(entrada);
        System.out.println("Reserva cancelada");
    }

    /**
     * Comprueba si un cliente está inscrito en este horario.
     *
     * @param entrada El cliente a comprobar.
     * @return true si el cliente está inscrito, false en caso contrario.
     */
    public boolean comprobarClienteInscrito(Cliente entrada) {
        for (Cliente it : inscritos) {
            if (it.getId() == entrada.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Imprime los clientes inscritos en este horario.
     */
    public void imprimirInscritos() {
        System.out.println(dia + " " + hora + ":00");
        for (Cliente it : inscritos) {
            System.out.println(it.clienteToCsv());
        }
    }

    /**
     * Genera una representación CSV de este horario.
     *
     * @return La representación CSV del horario.
     */
    public String horarioToCsv() {
        return id + "," + dia + "," + hora;
    }

    /**
     * Genera una representación en formato de cadena de caracteres de este
     * horario.
     *
     * @return La representación en formato de cadena de caracteres del horario.
     */
    public String horarioToString() {
        return "ID: " + id + "  FECHA: " + dia + " " + hora + ":00";
    }

    /**
     * Comprueba si hay espacio disponible en este horario para un cierto número
     * de inscritos.
     *
     * @param capacidad La capacidad deseada.
     * @return true si hay espacio disponible, false en caso contrario.
     */
    public boolean hueco(int capacidad) {
        if (inscritos.size() < capacidad) {
            return true;
        }
        return false;
    }
    
    /**
     * Crea un nuevo objeto de tipo Horario a partir de una cadena de datos.
     * 
     * @param datos La cadena de datos que contiene la información del horario.
     * @return Un objeto Horario creado a partir de los datos.
     */
    public static Horario crearHorarioLeido(String datos) {
        String[] aux = datos.split(",");
        int id = Integer.valueOf(aux[0]);
        DiaSemana dia = DiaSemana.valueOf(aux[1]);
        int hora = Integer.valueOf(aux[2]);
        Horario salida = new Horario(id, dia, hora);
        return salida;
    }

}
