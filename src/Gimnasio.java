
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
            salida = sala.seleccionarClase(idClase);
            if(!salida.isEmpty()){ //salida.size()!=0
                return salida;
            }
        }
        return salida;
    }
    
    public ArrayList<Clase> recorrerClases(){
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

}
