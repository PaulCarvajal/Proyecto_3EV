package Modelos;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adriana
 */
public class Clase {

    private int id;
    private String nombre;
    private int capacidad;
    private ArrayList<Horario> horario = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    public Clase(int id, int capacidad, String nombre) {
        this.id = id;
        this.capacidad = capacidad;
        this.nombre = nombre;
//        this.inscritos = new ArrayList<>();
        //si no inicializo el array me da una excepcion nullpointer al intentar añadir clientes
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Horario> getHorario() {
        return horario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHorario(ArrayList<Horario> horario) {
        this.horario = horario;
    }

    public String claseToCsv() {
        return id + "," + capacidad + "," + nombre;
    }

    //sobre cargo o que hago?
    
    public void mostrarHorario() {//muestra los disponibles
        for (Horario it : horarioDisponible()) {
            System.out.println(it.horarioToString());
        }
    }
    
    public void mostrarHorario(ArrayList<Horario> entrada) {//muestra los que esten en el AL pasado
        for (Horario it : entrada) {
            System.out.println(it.horarioToString());
        }
    }

    public ArrayList<Horario> buscarMiHorario(Cliente entrada) {
        ArrayList<Horario> misHorarios = new ArrayList<>();
        for (Horario it : horario) {
            if (it.comprobarClienteInscrito(entrada)) {
                misHorarios.add(it);
            }
        }
        return misHorarios;
    }

    public ArrayList<Horario> horarioDisponible() {
        ArrayList<Horario> salida = new ArrayList<>();
        for (Horario it : horario) {
            if (it.hueco(capacidad)) {
                salida.add(it);
            }
        }
        return salida;
    }

    public String claseToString() {
        return nombre + "(ID: " + id + ")";
    }

    public Horario seleccionarHorario(ArrayList<Horario> entrada) {//le paso AL para poder usarlo con los metodos de borrar reserva y de eliminar 
        mostrarHorario(entrada);//muestra los de entrada
        int idHorario;

        do {
            try {
                System.out.println("Introduce el id del horario: ");
                idHorario = Integer.valueOf(sc.nextLine());
                for (Horario horario : entrada) {//recorree los horarios pasados por parametro
                    if (horario.getId() == idHorario) {
                        return horario;
                    }
                }
                System.out.println("ID invalido...");
            } catch (NumberFormatException e) {
                System.out.println("ID invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true);

    }

    public boolean existeHorario(int idHorario) {
        for (Horario horario : horario) {
            if (idHorario == horario.getId()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Horario> horariosPorDia(DiaSemana dia) {
        ArrayList<Horario> salida = new ArrayList<>();
        for (Horario horario : horario) {
            if (horario.getDia().name().equalsIgnoreCase(dia.name())) {
                salida.add(horario);
            }
        }
        return salida;
    }

    public int asignarIdHorario() {
        if (horario.isEmpty()) {//si no existen horarios el primer id será 0
            return 0;
        }
        return horario.get(horario.size() - 1).getId() + 1;//devuelve el id del horario en ultima posición +1
    }

    public static Clase crearClaseLeida(String datos) {
        String[] aux = datos.split(",");
        int id = Integer.valueOf(aux[0]);
        int capacidad = Integer.valueOf(aux[1]);
        String nombre = aux[2];
        Clase salida = new Clase(id, capacidad, nombre);
        return salida;
    }

}
