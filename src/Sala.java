
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
//    private int capacidad;
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
        ArrayList<Horario> horario_aux = null;
        for (Clase it : clases) {
            if (it.getId() == idClase) {
                horario_aux = it.getHorario();
            }
        }
        return horario_aux;
    }

    public void mostrarClases() {
        for (Clase it : clases) {
            System.out.println(it.claseToCsv());
        }
    }

    public void eliminarReserva(Cliente entrada) {
        boolean reserva = false;//controlar si estoy inscrito en ALGUNA clase
        boolean existe = false;
        for (Clase it : clases) {
            if (it.comprobarClienteInscrito(entrada)) {//indica las clases en las que esta inscrito
                System.out.println(it.claseToCsv());
                reserva = true;
            }
        }
        if (reserva) {
            System.out.println("Indica el id de la reserva a cancelar: ");
            int aux = Integer.valueOf(sc.nextLine());
            for (int i = 0; i < clases.size(); i++) {
                if (clases.get(i).getId() == aux) {
                    clases.get(i).getInscritos().remove(entrada);
                    System.out.println("Reserva cancelada: " + clases.get(i).getNombre());
                    i--;
                    break;
                }
            }
        } else {
            System.out.println("No estas matriculado en ninguna clase");
        }

    }

    public boolean buscarMiClase(Cliente entrada) {
        boolean flag = false;
        for (Clase clase : clases) {
            if (clase.buscarMiHorario(entrada)) {
                flag = true;
            }
        }
        return flag;
    }
    
    public static void asdafd(){
        System.out.println("dos");
    }

    
}
