/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import Modelos.Cliente;
import Modelos.Horario;
import Modelos.Clase;
import Modelos.Gimnasio;
import Modelos.Sala;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author adriana
 */
public class OperativaCliente {
    public static Scanner sc = new Scanner(System.in);
        
    public static void menuCliente(Cliente entrada, Gimnasio g1) throws IOException {
        String opcion = "";

        do {
            System.out.println("Elija una opción:");
            System.out.println("0.- Mostrar clases disponibles");
            System.out.println("1.- Reservar clase");
            System.out.println("2.- Eliminar reserva");
            System.out.println("3.- Mis reservas");
            System.out.println("4.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    mostrarHorariosDisponibles(g1);
                    break;
                case "1":
                    reserva(entrada,g1);
                    break;
                case "2":
                    eliminarReserva(entrada,g1);
                    break;
                case "3":
                    misReservas(entrada,g1);
                    break;
                case "4":

                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void mostrarHorariosDisponibles(Gimnasio g1) {
        g1.mostrarClases(); //Muestra todas las clases existentes
        ArrayList<Clase> clases = g1.recorrerClases(); //guarda en un array las clases del gimnasio(TODAS)
        System.out.println("Selecciona el id de la clase deseada: ");
        int idClase = Integer.valueOf(sc.nextLine());
        for (Clase clase : clases) { //recorro el array de las clases del gimnasio
            if (idClase == clase.getId()) { //busco la clase indicada
                if (clase.horarioDisponible().isEmpty()) {//clase.horario disponible devuelve el array de horario de esa clase en la que hay hueco
                    System.out.println("No quedan huecos en la clase de " + clase.getNombre());//si entra aqui es que de la clase indicada no quedan horarios disponibles
                } else {//si hay algun horario disponible los muestro por pantalla
                    System.out.println("Horario disponible de " + clase.getNombre() + ":");
                    for (Horario horario : clase.horarioDisponible()) {
                        System.out.println(horario.horarioToString());
                    }
                }
            }
        }
    }

    public static void reserva(Cliente entrada,Gimnasio g1) {
        boolean existeHorario = false;
        ArrayList<Horario> seleccionado;
        int idHorario;
        g1.mostrarClases();//muestro todas las clases 
        seleccionado = g1.reservaClase();//Guardo en un array los horarios disponibles de la clase indicada(se pide en el metodo de gimnasio)
        
        if (!seleccionado.isEmpty()) {//seleccionado.size() != 0 ; si hay horarios disponibles...
            
            //muestro los horarios disponibles para reservar
            for (Horario it : seleccionado) { 
                System.out.println(it.horarioToString());
            }
            
            //indico el id del horario que reservare
            System.out.println("Selecciona el id del horario a reservar: ");
            idHorario = Integer.valueOf(sc.nextLine());
            for (Horario it : seleccionado) {
                if (it.getId() == idHorario) { 
                    existeHorario = true;//flag por si el horario no  existe o no está disponible
                    System.out.println("Registrando reserva..."); //solo indico que se ha regisrado una clase si encuentro el id del horario
                    it.anadirCliente(entrada);
                }
            }
            
            if (!existeHorario) {//si el horario no  existe o no está disponible
                System.out.println("El horario indicado no es válido");
            }

        } else {
            System.out.println("La clase seleccionadda no existe");
        }

    }

    public static void eliminarReserva(Cliente entrada,Gimnasio g1) {
        ArrayList<Clase> misClases = misReservas(entrada,g1); //mis reservas devuelve un AL con las clases en las que hay al menos 1 reserva
        ArrayList<Horario> misHorarios = new ArrayList<>(); //creo un AL para guardar luego los horarios en los que estoy inscrito de la clase que indico
        if (!misClases.isEmpty()) {//solo realizo esto si estoy matriculado en ALGUNA clase=>si no no me puede desmatricular de nada
            int idClase = -77;
            int idHorario = -88;
            boolean horarioValido = false;//variable para controlar si el horario indicado existe/estoy inscrito
            do {
                System.out.println("Indica el id de la clase a cancelar: ");
                idClase = Integer.valueOf(sc.nextLine());
                for (Clase clase : misClases) {
                    if (clase.getId() == idClase) {
                        misHorarios = clase.buscarMiHorario(entrada);
                        do {
                            System.out.println("Indica el id de la hora a cancelar: ");
                            idHorario = Integer.valueOf(sc.nextLine());

                            for (int i = 0; i < misHorarios.size(); i++) {
                                if (misHorarios.get(i).getId() == idHorario) {
                                    horarioValido = true;
                                    misHorarios.get(i).getInscritos().remove(entrada);
                                    System.out.println("Reserva cancelada: " + clase.getNombre());
                                    i--;
                                    break;
                                }
                            }
                            if (!horarioValido) {
                                System.out.println("Horario no válido...");
                            }
                        } while (!horarioValido);
                    }
                }
                if (idHorario == -88) {
                    System.out.println("ID no válido...");
                }
            } while (idHorario == -88);
        }
    }

    public static ArrayList<Clase> misReservas(Cliente entrada,Gimnasio g1) {
        ArrayList<Clase> misClases = new ArrayList<>();
        for (Clase clase : g1.recorrerClases()) { //AL de todas las clases existentes en el gimnasio
            if (!clase.buscarMiHorario(entrada).isEmpty()) {
                misClases.add(clase);
                System.out.println(clase.claseToString());
                for (Horario horario : clase.buscarMiHorario(entrada)) {
                    System.out.println(horario.horarioToString());
                }
            }
        }
        if (misClases.isEmpty()) {
            System.out.println("No estas matriculado en ninguna clase");
        }
        return misClases;
    }

}
