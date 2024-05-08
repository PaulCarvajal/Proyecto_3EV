package Ejecutables;

import Modelos.Sala;
import Modelos.Cliente;
import Modelos.Horario;
import Modelos.Clase;
import Modelos.Gimnasio;
import Operativa.OperativaCliente;
import static Operativa.OperativaCliente.eliminarReserva;
import static Operativa.OperativaCliente.menuCliente;
import static Operativa.OperativaCliente.misReservas;
import static Operativa.OperativaCliente.mostrarHorariosDisponibles;
import static Operativa.OperativaCliente.reserva;
import static Operativa.OperativaCliente.sc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author adriana
 */
public class main {

    private static ArrayList<Cliente> clientes = new ArrayList<>();

    private static ArrayList<Horario> horariosYoga = new ArrayList<>();

    private static ArrayList<Horario> horariosZumba = new ArrayList<>();

    private static ArrayList<Horario> horariosGAP = new ArrayList<>();

    private static ArrayList<Horario> horariosHipopresivos = new ArrayList<>();

    private static ArrayList<Horario> horariosCiclo = new ArrayList<>();

    private static ArrayList<Horario> horariosHIIT = new ArrayList<>();

    private static ArrayList<Clase> clases = new ArrayList<>();

    private static ArrayList<Clase> clases2 = new ArrayList<>();

    private static ArrayList<Sala> salas = new ArrayList<>();

    private static Gimnasio g1 = new Gimnasio(0, "AtlasGym", salas);

    private static Sala s1 = new Sala(0, clases);

    private static Sala s2 = new Sala(1, clases2);

    public static void main(String[] args) throws IOException {
        //CLIENTES
        Cliente c1 = new Cliente(0, "Adriana", 23);
        Cliente c2 = new Cliente(1, "Paul", 18);

        //HORARIOS
        Horario yoga9 = new Horario(0, "Lunes", 9);
        Horario yoga10 = new Horario(1, "Lunes", 10);
        Horario yoga11 = new Horario(2, "Martes", 11);
        horariosYoga.add(yoga9);
        horariosYoga.add(yoga10);
        horariosYoga.add(yoga11);

        Horario zumba0 = new Horario(0, "Martes", 9);
        Horario zumba1 = new Horario(1, "Martes", 10);
        Horario zumba2 = new Horario(2, "Miercoles", 11);
        horariosZumba.add(zumba0);
        horariosZumba.add(zumba1);
        horariosZumba.add(zumba2);

        Horario gap0 = new Horario(0, "Miercoles", 9);
        Horario gap1 = new Horario(1, "Miercoles", 10);
        Horario gap2 = new Horario(2, "Jueves", 11);
        horariosGAP.add(gap0);
        horariosGAP.add(gap1);
        horariosGAP.add(gap2);

        Horario hipopresivos0 = new Horario(0, "Jueves", 9);
        Horario hipopresivos1 = new Horario(1, "Jueves", 10);
        horariosHipopresivos.add(hipopresivos0);
        horariosHipopresivos.add(hipopresivos1);

        Horario ciclo0 = new Horario(0, "Jueves", 11);
        Horario ciclo1 = new Horario(1, "Jueves", 12);
        horariosCiclo.add(ciclo0);
        horariosCiclo.add(ciclo1);

        Horario hiit0 = new Horario(0, "Lunes", 12);
        Horario hiit1 = new Horario(1, "Miercoles", 12);
        horariosHIIT.add(hiit0);
        horariosHIIT.add(hiit1);

        //CLASES + ASIGNACION HORARIOS
        Clase zumba = new Clase(0, 10, "Zumba");
        zumba.setHorario(horariosZumba);

        Clase gap = new Clase(1, 1, "GAP");
        gap.setHorario(horariosGAP);

        Clase yoga = new Clase(2, 1, "Yoga");
        yoga.setHorario(horariosYoga);

        Clase hipopresivos = new Clase(3, 1, "Hipopresivos");
        hipopresivos.setHorario(horariosHipopresivos);

        Clase ciclo = new Clase(4, 1, "Ciclo");
        ciclo.setHorario(horariosCiclo);

        Clase hiit = new Clase(5, 1, "HIIT");
        hiit.setHorario(horariosHIIT);

        //ASIGNACION DE CLASES A SALAS         
        clases.add(zumba);
        clases.add(gap);
        clases.add(yoga);
        clases.add(hipopresivos);

        clases2.add(ciclo);
        clases2.add(hiit);

        salas.add(s1);
        salas.add(s2);

        menuAdministrador(g1);

        menuCliente(c1, g1);

        for (Clase it : clases) {
            System.out.println(it.claseToCsv());
            for (Horario hit : it.getHorario()) {
                hit.imprimirInscritos();
            }
        }

    }

    public static void menuAdministrador(Gimnasio g1) throws IOException {
        String opcion = "";

        do {
            System.out.println("Elija una opción:");
            System.out.println("0.- Crear clase");
            System.out.println("1.- Eliminar clase");
            System.out.println("2.- Añadir horario");
            System.out.println("3.- Crear cliente");
            System.out.println("4.- Eliminar cliente");
            System.out.println("5.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    crearClase();
                    break;
                case "1":

                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("5")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void crearClase() {
        boolean salaValida = false;
        System.out.println("Clases existentes: ");
        g1.mostrarClases();
        System.out.println("LISTADO DE SALAS: ");
        g1.mostrarSalas();

        Sala salaClase = g1.seleccionarSala();

        Clase nueva = g1.crearClase(salaClase.getId());
        
        salaClase.getClases().add(nueva);
        
        //horariosClase
        System.out.println("Debe crear al menos un horario asociado a esta clase: ");
        String continuar = "s";
        do{//en cada buucle creo una clase, y tengo que meterlo en el array todo pporque si no no actualiza las horas libres
            crearHorario(salaClase,nueva); //le paso la sala para ver en que horarios esta ocupada por otras clases
            System.out.println("Desea añadir más horarios? (s/n)");
            continuar = sc.nextLine();
            if(continuar.equalsIgnoreCase("n")){
                break;
            }
        }while(true);
        
        
        
    }
    
    public static void crearHorario(Sala salaClase, Clase clase){        //si no le paso la sala podria encontrarla recooriendo las dos salas y seleccionando en la que este la clase
        System.out.println("Que día se impartirá la clase: ");
        //enum para los dias?
        String dia = sc.nextLine();

        ArrayList<Integer> horasLibres = g1.horasLibresSalaPorDia(salaClase, dia);

        System.out.println("Las horas libres en la sala ese día son: ");
        for (Integer hora : horasLibres) {
            System.out.println(hora);
        }
        boolean horaValida = false;
        int hora;
        do {
            System.out.println("Indica la hora a la que se impartirá la clase: ");
            hora = Integer.valueOf(sc.nextLine());
            for (Integer horasLibre : horasLibres) {
                if(horasLibre == hora){//si la hora está entre las disponibles
                    horaValida=true;
                }
            }
            if(!horaValida){
                System.out.println("Hora no valida..");
            }
        } while (!horaValida);   
        Horario nuevoHorario = new Horario(clase.asignarId(), dia, hora);
        clase.getHorario().add(nuevoHorario);
    }

}
