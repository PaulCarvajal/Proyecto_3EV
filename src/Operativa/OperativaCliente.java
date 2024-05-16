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

    private static File f_gimnasio = new File("./Registro/clases.csv");

    private static ArrayList<Sala> salas = new ArrayList<>();

    public static void menuCliente(Cliente entrada, Gimnasio g1) throws IOException {
        //salas = IO_CSV_Gimnasio.leer(f_gimnasio, g1);

        g1.getSalas().addAll(salas);

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
                    reserva(entrada, g1);
                    break;
                case "2":
                    eliminarReserva(entrada, g1);
                    break;
                case "3":
                    misReservas(entrada, g1);
                    break;
                case "4":
                    //IO_CSV_Gimnasio.escribir(f_gimnasio, g1.getSalas());
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

        if (!clases.isEmpty()) {
            Clase clase_seleccionada = g1.seleccionarClaseSinSaberSala();

            if (clase_seleccionada.horarioDisponible().isEmpty()) {//clase.horario disponible devuelve el array de horario de esa clase en la que hay hueco
                System.out.println("No quedan huecos en la clase de " + clase_seleccionada.getNombre());//si entra aqui es que de la clase indicada no quedan horarios disponibles
            } else {//si hay algun horario disponible los muestro por pantalla
                System.out.println("Horario disponible de " + clase_seleccionada.getNombre() + ":");
                for (Horario horario : clase_seleccionada.horarioDisponible()) {
                    System.out.println(horario.horarioToString());
                }
            }
        }

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
            horario_a_borrar = clase_a_borrar.seleccionarHorario(misHorarios); //le paso el AL de horarios en los que estoy de esa clase para que seleccione el que hay que borrar
            horario_a_borrar.getInscritos().remove(entrada); // borro al cliente del AL de inscritos del horario seleccionado

            System.out.println("Reserva cancelada: " + clase_a_borrar.getNombre());

            /*
            do {
                try { // lo podria hacer modificando el metodo seleccionarClaseSinSaberSala pero tendria que pasarle parametros y lo uso en varios lugares, que es mejor??? //de momento lo he sobrecargado
                    System.out.println("Indica el id de la clase a cancelar: ");
                    idClase = Integer.valueOf(sc.nextLine());
                    for (Clase clase : misClases) {
                        if (clase.getId() == idClase) {
                            misHorarios = clase.buscarMiHorario(entrada);
                            System.out.println("Para el horario a cancelar: ");
                            horario_a_borrar = clase.seleccionarHorario(misHorarios); //le paso el AL de horarios en los que estoy de esa clase para que seleccione el que hay que borrar
                            horario_a_borrar.getInscritos().remove(entrada); // borro al cliente del AL de inscritos del horario seleccionado
                            System.out.println("Reserva cancelada: " + clase.getNombre());

                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID invalido...");
                    System.out.println("El valor inrtoducido no es numérico");
                }
            } while (idHorario == -88);*/
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

    public static void eliminarTodasLasReservas(Cliente entrada, Gimnasio g1) {
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
    }

}
