/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import IO.IO_CSV_Gimnasio;
import Modelos.Cliente;
import Modelos.Horario;
import Modelos.Clase;
import Modelos.Gimnasio;
import Modelos.Sala;
import java.io.File;
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
                    OperativaComun.mostrarHorariosDisponibles(g1);
                    break;
                case "1":
                    reserva(entrada, g1);
                    break;
                case "2":
                    eliminarReserva(entrada, g1);
                    break;
                case "3":
                    misReservas(entrada, g1);
                    break;
                case "4":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }



    public static void reserva(Cliente entrada, Gimnasio g1) {
        g1.mostrarClases(); //Muestra todas las clases existentes

        if (!g1.recorrerClases().isEmpty()) {//solo lo ejecuta si existe alguna clase
            Clase clase_a_reservar = g1.seleccionarClaseSinSaberSala();

            Horario horario_a_reservar = clase_a_reservar.seleccionarHorario(clase_a_reservar.horarioDisponible()); //selecciono el horario de entre los disponibles

            horario_a_reservar.anadirCliente(entrada);//indico si se ha inscrito al cliente o si ya estaba inscrito
        }
        //controlo si el cliente está insccrito al añadirlo o al guardar el horario??            
    }

    public static void eliminarReserva(Cliente entrada, Gimnasio g1) {
        ArrayList<Clase> misClases = misReservas(entrada, g1); //mis reservas devuelve un AL con las clases en las que hay al menos 1 reserva
        ArrayList<Horario> misHorarios = new ArrayList<>(); //creo un AL para guardar luego los horarios en los que estoy inscrito de la clase que indico
        Horario horario_a_borrar;
        
        if (!misClases.isEmpty()) {//solo realizo esto si estoy matriculado en ALGUNA clase=>si no no me puede desmatricular de nada
            
            Clase clase_a_borrar = g1.seleccionarClaseSinSaberSala(misClases); //selecciono una de las clases en la que tengo reserva
            misHorarios = clase_a_borrar.buscarMiHorario(entrada); //guardo los horarios de esa clase en los que estoy inscrito

            System.out.println("Para el horario a cancelar: ");
           /* System.out.println("Indica el id del horario");
            int idHorario = OperativaComun.asignarEntero();
            for (Horario horario : misHorarios) {
                if(idHorario==horario.getId()){
                    horario.eliminarCliente(entrada);
                    horario.imprimirInscritos();
                }
            }*/
            horario_a_borrar = clase_a_borrar.seleccionarHorario(misHorarios); //le paso el AL de horarios en los que estoy de esa clase para que seleccione el que hay que borrar
            
            horario_a_borrar.imprimirInscritos();
            horario_a_borrar.eliminarCliente(entrada);
            horario_a_borrar.imprimirInscritos();
            
            System.out.println("Reserva cancelada: " + clase_a_borrar.getNombre());
        }
    }

    public static ArrayList<Clase> misReservas(Cliente entrada, Gimnasio g1) {
        ArrayList<Clase> misClases = new ArrayList<>();
        for (Clase clase : g1.recorrerClases()) { //AL de todas las clases existentes en el gimnasio
            if (!clase.buscarMiHorario(entrada).isEmpty()) { //si estoy inscrito en algun horario de esa clase... 
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

    /*public static void eliminarTodasLasReservas(Cliente entrada, Gimnasio g1) {
        ArrayList<Clase> misClases = misReservas(entrada, g1); //mis reservas devuelve un AL con las clases en las que hay al menos 1 reserva
        ArrayList<Horario> misHorarios = new ArrayList<>(); //creo un AL para guardar luego los horarios en los que estoy inscrito de la clase que indico
        if (!misClases.isEmpty()) {//solo realizo esto si estoy matriculado en ALGUNA clase=>si no no me puede desmatricular de nada
            for (Clase clase_a_borrar : misClases) {
                misHorarios = clase_a_borrar.buscarMiHorario(entrada);//guardo los horarios de esa clase en los que estoy inscrito
                for (Horario horario_a_borrar : misHorarios) {
                    horario_a_borrar.getInscritos().remove(entrada); // borro al cliente del AL de inscritos del horario seleccionado
                }
            }
            System.out.println("Reservas canceladas ");
        }
    }*/

}
