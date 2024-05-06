
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
        Clase zumba = new Clase(0, 10, "Zumba", 1);
        zumba.setHorario(horariosZumba);

        Clase gap = new Clase(1, 1, "GAP", 1);
        gap.setHorario(horariosGAP);

        Clase yoga = new Clase(2, 1, "Yoga", 1);
        yoga.setHorario(horariosYoga);

        Clase hipopresivos = new Clase(3, 1, "Hipopresivos", 1);
        hipopresivos.setHorario(horariosHipopresivos);

        Clase ciclo = new Clase(4, 1, "Ciclo", 1);
        ciclo.setHorario(horariosCiclo);

        Clase hiit = new Clase(5, 1, "HIIT", 1);
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
                    mostrarHorariosDisponibles();
                    break;
                case "1":
                    reserva(entrada);
                    break;
                case "2":
                    eliminarReserva(entrada);
                    break;
                case "3":
                    misReservas(entrada);
                    break;
                case "4":

                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("4")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void mostrarHorariosDisponibles() {
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

    public static void reserva(Cliente entrada) {
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

    public static void eliminarReserva(Cliente entrada) {
        ArrayList<Clase> misClases = misReservas(entrada); //mis reservas devuelve un AL con las clases en las que hay al menos 1 reserva
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

    public static ArrayList<Clase> misReservas(Cliente entrada) {
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
