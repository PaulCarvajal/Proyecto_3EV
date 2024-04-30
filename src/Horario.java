
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adriana
 */
public class Horario {

    private int id;
    private ArrayList<Cliente> inscritos = new ArrayList<>();
    private String dia;
    private int hora;

    public Horario(int id, String dia, int hora) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
    }

    public ArrayList<Cliente> getInscritos() {
        return inscritos;
    }

    public int getId() {
        return id;
    }
   
    public String getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public void setInscritos(ArrayList<Cliente> inscritos) {
        this.inscritos = inscritos;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void anadirCliente(Cliente entrada) {
        if (!comprobarClienteInscrito(entrada)) {
            inscritos.add(entrada);
        } else {
            System.out.println("Ya estás inscrito en esta clase.");
        }
    }

    public boolean comprobarClienteInscrito(Cliente entrada) {
        for (Cliente it : inscritos) {
            if (it.getId() == entrada.getId()) {
                return true;
            }
        }
        return false;
    }

    public void imprimirInscritos() {
        System.out.println(dia + hora +":00");
        for (Cliente it : inscritos) {
            System.out.println(it.clienteToCsv());
        }
    }

    public String horarioToCsv() {
        return id + "," + dia + "," + hora;
    }
    
    public String horarioToString() {
        return "ID: " + id + "  FECHA: " + dia + " " + hora + ":00";
    }
    
   
}
