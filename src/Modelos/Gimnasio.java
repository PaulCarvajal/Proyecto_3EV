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
public class Gimnasio {

    private int id;
    private String nombre;
    private ArrayList<Sala> salas;
    private final int[] horasApertura = {9, 10, 11, 12, 13};

    private Scanner sc = new Scanner(System.in);

    public Gimnasio(int id, String nombre, ArrayList<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.salas = salas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public int[] getHorasApertura() {
        return horasApertura;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalas(ArrayList<Sala> salas) {
        this.salas = salas;
    }

    public String gimnasioToCsv() {
        return id + "," + nombre + "," + salas;
    }

    public void mostrarSalas() {
        System.out.println("Mostrando salas...");
        for (Sala sala : salas) {
            System.out.println(sala.salaToCsv());
        }
    }

    public void mostrarClases() {
        System.out.println("Mostrando clases...");
        for (Sala sala : salas) {
            sala.mostrarClases();
        }
    }

    public ArrayList<Horario> reservaClase() {
        ArrayList<Horario> salida = new ArrayList<>();//esttaria bien inicializarlo a null??
        System.out.println("Selecciona el id de la clase a reservar");
        int idClase = Integer.valueOf(sc.nextLine());
        for (Sala sala : salas) {//busco en que sala esta la clase indicada
            salida = sala.horarioDisponibleClase(idClase);
            if (!salida.isEmpty()) { //salida.size()!=0
                return salida;
            }
        }
        return salida;
    }

    public ArrayList<Clase> recorrerClases() {
        ArrayList<Clase> aux = new ArrayList<>();
        ArrayList<Clase> clases = new ArrayList<>();
        for (Sala sala : salas) {
            aux = sala.getClases();
            for (Clase clase : aux) {
                clases.add(clase);
            }
        }
        return clases;
    }

    public Clase crearClase(int idSala) {
        int idClase = -88;
        for (Sala sala : salas) {
            if (sala.getId() == idSala) {
                idClase = sala.asignarId();
            }
        }
        System.out.println("Indica el nombre de la clase: ");
        String nombreClase = sc.nextLine();
        System.out.println("Indica el numero máximo de alumnos de la clase: ");
        int capacidadClase = Integer.valueOf(sc.nextLine());

        return new Clase(idClase, capacidadClase, nombreClase);

    }

    public boolean existeSala(int idSala) {
        for (Sala sala : salas) {
            if (idSala == sala.getId()) {
                return true;
            }
        }
        return false;
    }

    public Sala seleccionarSala() {//hay que ver como controlar que no me met una sala inexistente
        int idSala;
        do {
            System.out.println("Introduce el id de la sala: ");
            idSala=Integer.valueOf(sc.nextLine());
            for (Sala sala : salas) {
                if (sala.getId() == idSala) {
                    return sala;
                }
            }
            System.out.println("ID invalido...");
        } while (!existeSala(idSala));
        return null;//qque devuelvo si no?
    }
    
    public ArrayList<Integer> horasLibresSalaPorDia(Sala sala, String dia){
        ArrayList<Horario> ocupados = sala.horariosOcupadosSalaPorDia(dia);
        
        ArrayList<Integer> libres = new ArrayList<>();
        for (int i : horasApertura) {
            libres.add(i);
        }
        for (Horario horario : ocupados) {
            for (int i=0; i<libres.size(); i++) {
                if(libres.get(i) == horario.getHora()){
                    libres.remove(libres.get(i));
                    i--;
                }
            }
        }
        return libres;
    }

}