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
public class Sala {

    private int id;
    private ArrayList<Clase> clases;
    Scanner sc = new Scanner(System.in);

    public Sala(int id, ArrayList<Clase> clases) {
        this.id = id;
        this.clases = clases;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Clase> getClases() {
        return clases;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClases(ArrayList<Clase> clases) {
        this.clases = clases;
    }

    public String salaToCsv() {
        return id+"";
    }

    public ArrayList<Horario> horarioDisponibleClase(int idClase) {
        ArrayList<Horario> horario_aux = new ArrayList<>();
        for (Clase it : clases) {
            if (it.getId() == idClase) {
                horario_aux = it.horarioDisponible();
            }
        }
        return horario_aux;
    }

    public void mostrarClases() {
        for (Clase it : clases) {
            System.out.println(it.claseToCsv());
        }
    }

    public int ultimoId() {
        if(clases.isEmpty()){//si no existen salas el primer id será 0
            return 0;
        }
        return clases.get(clases.size()-1).getId();//devuelve el id de la clase en ultima posición 
    }

        
   /* public ArrayList<int> horasDisponiblesSala (int idSala, String dia){
        ArrayList<Horario> horasOcupadas;
        for (Sala sala : salas) {
            if(sala.getId()==idSala){
                
            }
        }
    }*/
    
     public Clase seleccionarClase() {//hay que ver como controlar que no me met una sala inexistente
        int idClase;
        do {
            System.out.println("Introduce el id de la clase: ");
            idClase=Integer.valueOf(sc.nextLine());
            for (Clase clase : clases) {
                if (clase.getId() == idClase) {
                    return clase;
                }
            }
            System.out.println("ID invalido...");
        } while (!existeClase(idClase));//si pongo while true funciona igual no?
        return null;//qque devuelvo si no?
    }

    public boolean existeClase(int idClase) {
        for (Clase clase : clases) {
            if (idClase == clase.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public Clase identificarClase(int idClase) {
        for (Clase clase : clases) {
            if (idClase == clase.getId()) {
                return clase;
            }
        }
        return null;//habria que controlar si esto devuelve null porque será que la clase no existe
    }
    
    public ArrayList<Horario> horariosOcupadosSalaPorDia(DiaSemana dia){
        ArrayList<Horario> salida = new ArrayList<>();
        for (Clase clase : clases) {
            for (Horario horario : clase.horariosPorDia(dia)) {
                salida.add(horario);
            }            
        }
        return salida;
    }
    

    
}