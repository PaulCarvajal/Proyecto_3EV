
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

    private static Scanner sc = new Scanner(System.in);

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
        Clase zumba = new Clase(0, "Zumba", 1);
        zumba.setHorario(horariosZumba);

        Clase gap = new Clase(1, "GAP", 1);
        gap.setHorario(horariosGAP);

        Clase yoga = new Clase(2, "Yoga", 1);
        yoga.setHorario(horariosYoga);

        Clase hipopresivos = new Clase(3, "Hipopresivos", 1);
        hipopresivos.setHorario(horariosHipopresivos);

        Clase ciclo = new Clase(4, "Ciclo", 1);
        ciclo.setHorario(horariosCiclo);

        Clase hiit = new Clase(5, "HIIT", 1);
        hiit.setHorario(horariosHIIT);

        //ASIGNACION DE CLASES A SALAS         
        clases.add(zumba);
        clases.add(gap);
        clases.add(yoga);
        clases.add(hipopresivos);

        clases2.add(ciclo);
        clases2.add(hiit);

        Sala s2 = new Sala(1, clases2);
        salas.add(s1);
        salas.add(s2);

        menuCliente(c1);

        for (Clase it : clases) {
            System.out.println(it.claseToCsv());
            for (Horario hit : it.getHorario()) {
                hit.imprimirInscritos();
            }
        }

    }

    public static void menuCliente(Cliente entrada) throws IOException {
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
                    g1.mostrarClasesDisponibles();
                    break;
                case "1":
//                    g1.reservaClase(entrada);
                    reserva(entrada);
                    //seleccionar horario
                    //añadir al array de horario
                    break;
                case "2":
                    s1.eliminarReserva(entrada);
                    break;
                case "3":
                    g1.misReservas(entrada);
                    break;
                case "4":

                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void reserva(Cliente entrada) {
        boolean existeHorario = false;
        ArrayList<Horario> seleccionado;
        int aux_idHorario;
        g1.mostrarClasesDisponibles();
        try {
            seleccionado = g1.reservaClase();
            for (Horario it : seleccionado) {
                System.out.println(it.horarioToString());
            }
            System.out.println("Selecciona el id del horario a reservar: ");
            aux_idHorario = Integer.valueOf(sc.nextLine());            
            for (Horario it : seleccionado) {
                if(it.getId() == aux_idHorario){
                    existeHorario = true;
                    System.out.println("Registrando reserva...");
                    it.anadirCliente(entrada);
                }
            }
            if(!existeHorario){
                System.out.println("El horario indicado no es válido");
            }
            
        } catch (NullPointerException ex) {
            System.out.println("No existen clases para el id indicado...");
        }        
    }
    
    public static void misReservas (Cliente entrada){
        ArrayList<Clase>aux;
        aux=g1.recorrerClases();
        comprobarClienteInscrito(entrada);
    }

}
