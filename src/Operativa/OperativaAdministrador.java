/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import IO.IO_CSV_Gimnasio;
import IO.IO_Clientes;
import Modelos.Clase;
import Modelos.Cliente;
import Modelos.DiaSemana;
import Modelos.Gimnasio;
import Modelos.Horario;
import Modelos.Sala;
import static Operativa.OperativaCliente.sc;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author adriana
 */
public class OperativaAdministrador {

    private static File f_clientes = new File("./Registro/clientes.csv");

    private static ArrayList<Cliente> clientes_actualizados = new ArrayList<>();

    public static void menuAdministrador(Gimnasio g1) throws IOException, SQLException {

        clientes_actualizados = IO_Clientes.leer(f_clientes);
        g1.setClientes(clientes_actualizados);

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
                    crearClase(g1);
                    break;
                case "1":
                    eliminarClase(g1);
                    break;
                case "2":
                    anadirHorario(g1);
                    break;
                case "3":
                    crearCliente(g1);
                    break;
                case "4":
                    eliminarCliente(g1);
                    break;
                case "5":
                    IO_Clientes.escribir(f_clientes, g1.getClientes());
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("5")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    public static void crearClase(Gimnasio g1) {
        System.out.println("Clases existentes: ");
        g1.mostrarClases();

        System.out.println("LISTADO DE SALAS: ");
        g1.mostrarSalas();

        Sala salaClase = g1.seleccionarSala(); //devuelve la sala donde se va a crear la nueva clase
        Clase nueva = g1.crearClase();
        salaClase.getClases().add(nueva);

        //horariosClase
        System.out.println("Debe crear al menos un horario asociado a esta clase: ");
        String continuar = "s";
        externo: //etiqueta para salir de ese bucle do-while
        do {//en cada buucle creo una clase, y tengo que meterlo en el array todo porque si no no actualiza las horas libres
            crearHorario(nueva, g1); //le paso la sala para ver en que horarios esta ocupada por otras clases
            System.out.println("Desea añadir más horarios? (s/n)");
            continuar = sc.nextLine();
            while (true) {
                if (continuar.equalsIgnoreCase("n")) {
                    break externo;
                } else if (continuar.equalsIgnoreCase("s")) {
                    break;
                } else {
                    System.out.println("Introduzca una opcion valida: (s/n)");
                    continuar = sc.nextLine();
                }
            }

        } while (continuar.equalsIgnoreCase("s"));

    }

    public static void crearHorario(Clase clase, Gimnasio g1) {
        System.out.println("Que día se impartirá la clase: ");
        //enum para los dias?
        DiaSemana dia = asignarDia();

        ArrayList<Integer> horasLibres = g1.horasLibresSalaPorDia(g1.identificarSala(clase), dia);//controlar que pasa si identificarSala devuelve null

        System.out.println("Las horas libres en la sala ese día son: ");
        for (Integer hora : horasLibres) {
            System.out.println(hora);
        }
        boolean horaValida = false;
        int hora = -88;
        do {
            try {

                System.out.println("Indica la hora a la que se impartirá la clase: ");
                hora = Integer.valueOf(sc.nextLine());
                for (Integer horasLibre : horasLibres) {
                    if (horasLibre == hora) {//si la hora está entre las disponibles
                        horaValida = true;
                    }
                }
                if (!horaValida) {
                    System.out.println("Hora no valida..");
                    System.out.println("Selecciona una de las indicadas");
                }
            } catch (NumberFormatException e) {
                System.out.println("La hora debe ser un valor numerico...");
            }
        } while (!horaValida);
        Horario nuevoHorario = new Horario(clase.asignarIdHorario(), dia, hora);
        clase.getHorario().add(nuevoHorario);
    }

    public static DiaSemana asignarDia() {
        String diaNombre;
        do {
            System.out.println("Introduzca el día de la semana: ");
            diaNombre = sc.nextLine().toUpperCase();
            if (!comprobarDia(diaNombre)) {
                System.out.println("Dia no valido");
            }
        } while (!comprobarDia(diaNombre));
        DiaSemana dia = DiaSemana.valueOf(diaNombre);
        return dia;
    }

    public static boolean comprobarDia(String entrada) {
        for (DiaSemana it : DiaSemana.values()) {
            if (entrada.equals(it.name())) {//equalsIgnoreCase no hace falta xq lo he controlado antes de pasar el string
                return true;
            }
        }
        return false;
    }

    public static void anadirHorario(Gimnasio g1) {
        g1.mostrarClases();
        System.out.println("Para la clase a la que quieres añadirle un horario: ");
        Clase modificar = g1.seleccionarClaseSinSaberSala();
        Sala salaClase = g1.identificarSala(modificar);
        crearHorario(modificar, g1);
    }

    public static void eliminarClase(Gimnasio g1) {
        g1.mostrarClases();
        System.out.println("Para la clase a eliminar: ");
        Clase eliminar = g1.seleccionarClaseSinSaberSala();
        Sala salaClase = g1.identificarSala(eliminar);
        salaClase.getClases().remove(eliminar);/*
        for (int i = 0; i < salaClase.getClases().size(); i++) {
            salaClase.getClases().remove(salaClase.getClases().get(i));//si puedo borrar de la sala la clase

        }*/
        System.out.println("Clase eliminada");
        g1.reasignarIdClase();

    }

    public static void crearCliente(Gimnasio g1) throws SQLException {
        Cliente nuevo = Cliente.crearClienteTeclado(g1.asignarIdCliente());//le paso el id llamando a un metodo no static desde fuera porque desde dentrto daba problemas
        System.out.println("nuevo.");
        g1.getClientes().add(nuevo);
        OperativaComun.insertarCliente(nuevo);
        System.out.println("Cliente creado exitosamente");
    }

    public static void eliminarCliente(Gimnasio g1) throws SQLException {
        g1.mostrarClientes();
        
        Cliente cliente_a_borrar = g1.seleccionarCliente();
        
        OperativaCliente.eliminarTodasLasReservas(cliente_a_borrar, g1); //elimino las reservas que tenía para que no se queden guardadas y se actualice en el csv
        
        int idClienteBorrar = cliente_a_borrar.getId();//lo capturo antes de eliminarlo
        
        OperativaComun.eliminarCliente(idClienteBorrar);
        
        if (g1.getClientes().remove(cliente_a_borrar)) {
            System.out.println("Cliente eliminado exitosamente");
        }
        g1.reasignarIdCliente(idClienteBorrar);
    }

}
