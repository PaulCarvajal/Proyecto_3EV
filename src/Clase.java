
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adriana
 */
class Clase {

    private int id;
    private String nombre;
    private int capacidad;
    private int duracion;
    private ArrayList<Horario> horario = new ArrayList<>();

    public Clase(int id, int capacidad, String nombre, int duracion) {
        this.id = id;        
        this.capacidad = capacidad;
        this.nombre = nombre;
        this.duracion = duracion;
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

    public int getDuracion() {
        return duracion;
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

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setHorario(ArrayList<Horario> horario) {
        this.horario = horario;
    }
    
    public String claseToCsv() {
        return id + "," + nombre + "," + duracion;
    }

    public void mostrarHorario(){        
        for (Horario it : horario) {
            System.out.println(it.horarioToString());
        }
    }

    
    public ArrayList<Horario> buscarMiHorario(Cliente entrada) {
        ArrayList <Horario> misHorarios = new ArrayList<>();
        for (Horario it : horario) {
            if (it.comprobarClienteInscrito(entrada)) {
                misHorarios.add(it);
            }
        }
       return misHorarios;
    }
    
    public ArrayList<Horario> horarioDisponible(){
        ArrayList<Horario> salida = new ArrayList<>();
        for (Horario it : horario) {
            if(it.hueco(capacidad)){
                salida.add(it);
            }
        }
        return salida;
    }
    
    public String claseToString(){
        return nombre + "(ID: " + id + ")" ;
    }

}
