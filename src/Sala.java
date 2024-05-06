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
class Sala {

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
        return id + "," + clases;
    }

    public ArrayList<Horario> seleccionarClase(int idClase) {
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

    
}
