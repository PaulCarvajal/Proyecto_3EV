
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
    private int duracion;
    private ArrayList<Horario> horario = new ArrayList<>();

    public Clase(int id, String nombre, int duracion) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
//        this.inscritos = new ArrayList<>();
        //si no inicializo el array me da una excepcion nullpointer al intentar añadir clientes
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
            System.out.println(it.horarioToCsv());
        }
    }
    
    public void seleccionarHorario(int idHorario){        
        for (Horario it : horario) {
            if(idHorario==it.getId()){
                it.anadirCliente(entrada);
            }
        }
    }
    
    public boolean buscarMiHorario(Cliente entrada) {
        boolean flag = false;
        for (Horario it : horario) {
            if (it.comprobarClienteInscrito(entrada)) {
                System.out.println(nombre);
                flag = true;
                System.out.println(it.horarioToCsv());
            }
        }
       return flag;
    }
}
