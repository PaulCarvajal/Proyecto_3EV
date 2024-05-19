/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operativa;

import IO.IO_CSV_Clientes;
import IO.IO_CSV_Gimnasio;
import Modelos.Clase;
import Modelos.Cliente;
import Modelos.DiaSemana;
import Modelos.Gimnasio;
import Modelos.Horario;
import Modelos.Sala;
import java.sql.Connection;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author adriana
 */
public class OperativaAdministrador {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Muestra el menú de opciones para el administrador del gimnasio y permite
     * realizar diferentes operaciones como mostrar clases, crear clases,
     * modificar clases, eliminar clases, añadir horarios, listar clientes,
     * crear clientes, eliminar clientes, etc.
     *
     * @param g1 El gimnasio sobre el cual se van a realizar las operaciones.
     * @param con La conexión a la base de datos.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws SQLException Si ocurre un error de SQL al interactuar con la base
     * de datos.
     */
    public static void menuAdministrador(Gimnasio g1, Connection con) throws IOException, SQLException {

        String opcion = "";
        do {
            System.out.println("Elija una opción:");
            System.out.println("0.- Mostrar clases");
            System.out.println("1.- Mostrar horarios disponibles de las clases");
            System.out.println("2.- Crear clase");
            System.out.println("3.- Modificar clase");
            System.out.println("4.- Eliminar clase");
            System.out.println("5.- Añadir horario");
            System.out.println("6.- Listar clientes");
            System.out.println("7.- Crear cliente");
            System.out.println("8.- Eliminar cliente");
            System.out.println("9.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    g1.mostrarClases();
                    break;
                case "1":
                    OperativaComun.mostrarHorariosDisponibles(g1);
                    break;
                case "2":
                    crearClase(g1);
                    break;
                case "3":
                    modificarClase(g1);
                    break;
                case "4":
                    eliminarClase(g1);
                    break;
                case "5":
                    anadirHorario(g1);
                    break;
                case "6":
                    g1.mostrarClientes();
                    break;
                case "7":
                    crearCliente(g1, con);
                    break;
                case "8":
                    eliminarCliente(g1, con);
                    break;
                case "9":
                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("9")); //Mientras seleccione un numero distinto de 4 seguir el bucle
    }

    /**
     * Crea una nueva clase en el gimnasio, solicitando al usuario los detalles
     * necesarios, como el nombre, la capacidad, la sala, y los horarios.
     *
     * @param g1 El gimnasio donde se creará la clase.
     */
    public static void crearClase(Gimnasio g1) {
        g1.mostrarClases();

        g1.mostrarSalas();

        Sala salaClase = g1.seleccionarSala(); //devuelve la sala donde se va a crear la nueva clase
        Clase nueva = g1.crearClase();
        salaClase.getClases().add(nueva);
        System.out.println("Debe crear al menos un horario asociado a esta clase: ");
        crearHorario(nueva, g1);
    }

    /**
     * Permite crear horarios para una clase existente en el gimnasio. Se
     * realiza hasta que el adminisrador indica lo contrario.
     *
     * @param clase La clase a la que se le añadirá el horario.
     * @param g1 El gimnasio donde se realiza la operación.
     */
    public static void crearHorario(Clase clase, Gimnasio g1) {
        String continuar = "s";
        externo: //etiqueta para salir de ese bucle do-while
        do {//en cada buucle creo una clase, y tengo que meterlo en el array todo porque si no no actualiza las horas libres
            crearUnHorario(clase, g1); //le paso la sala para ver en que horarios esta ocupada por otras clases
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

    /**
     * Crea un único horario para una clase específica en el gimnasio.
     *
     * @param clase La clase a la que se añadirá el horario.
     * @param g1 El gimnasio donde se realiza la operación.
     */
    public static void crearUnHorario(Clase clase, Gimnasio g1) {
        System.out.println("Que día se impartirá la clase: ");
        //enum para los dias?
        Sala sala = g1.identificarSala(clase);
        DiaSemana dia = asignarDia(g1, sala);

        ArrayList<Integer> horasLibres = g1.horasLibresSalaPorDia(sala, dia);//controlar que pasa si identificarSala devuelve null

        System.out.println("Las horas libres en la sala ese día son: ");
        for (Integer hora : horasLibres) {
            System.out.println(hora);
        }
        boolean horaValida = false;
        int hora = -88;
        do {

            System.out.println("Indica la hora a la que se impartirá la clase: ");
            hora = OperativaComun.asignarEntero();
            for (Integer horasLibre : horasLibres) {
                if (horasLibre == hora) {//si la hora está entre las disponibles
                    horaValida = true;
                }
            }
            if (!horaValida) {
                System.out.println("Hora no valida..");
                System.out.println("Selecciona una de las indicadas");
            }
        } while (!horaValida);
        Horario nuevoHorario = new Horario(clase.asignarIdHorario(), dia, hora);
        clase.getHorario().add(nuevoHorario);
    }

    /**
     * Asigna un día de la semana para una clase, solicitando la entrada al
     * administrador y verificando si el día es válido y está disponible para la
     * sala seleccionada.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param sala La sala en la cual se impartirá la clase.
     * @return El día de la semana asignado para la clase.
     */
    public static DiaSemana asignarDia(Gimnasio g1, Sala sala) {
        mostrarDiasDisponibles(g1, sala);
        ArrayList<DiaSemana> disponibles = diasDisponibles(g1, sala);
        String diaNombre;
        do {
            System.out.println("Introduzca el día de la semana: ");
            diaNombre = sc.nextLine().toUpperCase();
            if (!comprobarDia(diaNombre, disponibles)) {
                System.out.println("Dia no valido");
            }
        } while (!comprobarDia(diaNombre, disponibles));
        DiaSemana dia = DiaSemana.valueOf(diaNombre);
        return dia;
    }

    /**
     * Verifica si el día introducido es válido y está disponible para la sala
     * seleccionada.
     *
     * @param entrada El día introducido por el administrador.
     * @param disponibles Los días disponibles para la sala seleccionada.
     * @return true si el día es válido y disponible, false en caso contrario.
     */
    public static boolean comprobarDia(String entrada, ArrayList<DiaSemana> disponibles) {
        for (DiaSemana it : disponibles) {
            if (entrada.equals(it.name())) {//equalsIgnoreCase no hace falta xq lo he controlado antes de pasar el string
                return true;
            }
        }
        return false;
    }

    /**
     * Muestra los días de la semana disponibles para asignar una clase en la
     * sala seleccionada.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param sala La sala en la cual se impartirá la clase.
     */
    public static void mostrarDiasDisponibles(Gimnasio g1, Sala sala) {
        System.out.println("Dias disponibles:");
        for (DiaSemana dia : diasDisponibles(g1, sala)) {
            System.out.println(dia.name());
        }
    }

    /**
     * Obtiene los días de la semana disponibles para asignar a una clase en la
     * sala seleccionada.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param sala La sala en la cual se impartirá la clase.
     * @return Una lista de los días de la semana disponibles.
     */
    public static ArrayList<DiaSemana> diasDisponibles(Gimnasio g1, Sala sala) {
        ArrayList<DiaSemana> disponibles = new ArrayList<>();
        for (DiaSemana dia : DiaSemana.values()) {
            if (!g1.horasLibresSalaPorDia(sala, dia).isEmpty()) {
                disponibles.add(dia);
            }
        }
        return disponibles;
    }

    /**
     * Permite al administrador añadir un horario a una clase existente en el
     * gimnasio.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     */
    public static void anadirHorario(Gimnasio g1) {
        g1.mostrarClases();
        System.out.println("Para la clase a la que quieres añadirle un horario: ");
        Clase modificar = g1.seleccionarClaseSinSaberSala();
        Sala salaClase = g1.identificarSala(modificar);
        crearHorario(modificar, g1);
    }

    /**
     * Permite al administrador modificar una clase existente en el gimnasio,
     * ofreciendo opciones para cambiar su nombre, capacidad o sala de
     * impartición.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     */
    public static void modificarClase(Gimnasio g1) {
        g1.mostrarClases();
        System.out.println("¿Que clase desea modificar?");
        Clase clase = g1.seleccionarClaseSinSaberSala();
        String opcion = "";
        String aux = "";
        do {
            System.out.println("Elija una opción:");
            System.out.println("0.- Nombre");
            System.out.println("1.- Capacidad");
            System.out.println("2.- Sala");
            System.out.println("3.- Salir");

            opcion = sc.nextLine();
            switch (opcion) {
                case "0":
                    modificarNombreClase(g1, clase);
                    break;
                case "1":
                    modificarCapacidadClase(g1, clase);
                    break;
                case "2":
                    modificarSalaClase(g1, clase);
                    break;
                case "3":

                    break;
                default:
                    System.out.println("¡Opción incorrecta!");
                    ;

            }
        } while (!opcion.equals("3")); //Mientras seleccione un numero distinto de 4 seguir el bucle

    }

    /**
     * Permite al administrador modificar el nombre de una clase existente.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param clase La clase que se desea modificar.
     */
    public static void modificarNombreClase(Gimnasio g1, Clase clase) {
        System.out.println("Nombre actual: " + clase.getNombre());
        System.out.println("Indica el nuevo nombre: ");
        clase.setNombre(sc.nextLine());
        System.out.println("Nombre actualizado");
    }

    /**
     * Permite al administrador modificar la capacidad de una clase existente.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param clase La clase que se desea modificar.
     */
    public static void modificarCapacidadClase(Gimnasio g1, Clase clase) {
        System.out.println("Capacidad actual: " + clase.getCapacidad());
        System.out.println("Indica la nueva capacidad: ");
        clase.setCapacidad(OperativaComun.asignarEntero());
        System.out.println("Capacidad actualizada");
    }

    /**
     * Permite al administrador modificar la sala de impartición de una clase
     * existente.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     * @param clase La clase que se desea modificar.
     */
    public static void modificarSalaClase(Gimnasio g1, Clase clase) {
        System.out.println("Sala actual: " + g1.identificarSala(clase).getId());
        System.out.println("Para la nueva sala en la que se impartirá: ");
        Sala nueva = g1.seleccionarSala();
        Sala antigua = g1.identificarSala(clase);
        antigua.getClases().remove(clase);
        nueva.getClases().add(clase);
        clase.setHorario(new ArrayList<Horario>());
        crearHorario(clase, g1);
        g1.reasignarIdClase();
        System.out.println("Sala actualizada");
    }

    /**
     * Permite al administrador eliminar una clase existente en el gimnasio.
     *
     * @param g1 El gimnasio en el cual se encuentra la clase.
     */
    public static void eliminarClase(Gimnasio g1) {
        g1.mostrarClases();
        System.out.println("Para la clase a eliminar: ");
        Clase eliminar = g1.seleccionarClaseSinSaberSala();
        Sala salaClase = g1.identificarSala(eliminar);
        salaClase.getClases().remove(eliminar);
        System.out.println("Clase eliminada");
        g1.reasignarIdClase();

    }

    /**
     * Permite al administrador crear un nuevo cliente y guardarlo en la base de
     * datos.
     *
     * @param g1 El gimnasio al que se asociará el nuevo cliente.
     * @param con La conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la operación con la base
     * de datos.
     */
    public static void crearCliente(Gimnasio g1, Connection con) throws SQLException {
        Cliente nuevo = Cliente.crearClienteTeclado(g1.asignarIdCliente());//le paso el id llamando a un metodo no static desde fuera porque desde dentrto daba problemas
        g1.getClientes().add(nuevo);
        insertarClienteBBDD(nuevo, con);
        System.out.println("Cliente creado exitosamente");
    }

    /**
     * Permite al administrador eliminar un cliente existente, incluyendo sus
     * reservas asociadas, tanto del gimnasio como de la base de datos.
     *
     * @param g1 El gimnasio del cual se eliminará el cliente y sus reservas.
     * @param con La conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la operación con la base
     * de datos.
     */
    public static void eliminarCliente(Gimnasio g1, Connection con) throws SQLException {
        g1.mostrarClientes();

        Cliente cliente_a_borrar = g1.seleccionarCliente();

        g1.borrarClienteClases(cliente_a_borrar); //elimino las reservas que tenía para que no se queden guardadas y se actualice en el csv

        int idClienteBorrar = cliente_a_borrar.getId();//lo capturo antes de eliminarlo

        eliminarClienteBBDD(idClienteBorrar, con);

        if (g1.getClientes().remove(cliente_a_borrar)) {
            System.out.println("Cliente eliminado exitosamente");
        }
        g1.reasignarIdCliente(idClienteBorrar, con);

    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param entrada El cliente que se va a insertar.
     * @param con La conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la operación con la base
     * de datos.
     */
    public static void insertarClienteBBDD(Cliente entrada, Connection con) throws SQLException {
        // Preparar la consulta SQL
        System.out.println("Introduce un nombre de usuario: ");
        String usuario = sc.nextLine();
        System.out.println("Introduce una contraseña: ");
        String contrasena = sc.nextLine();

        String myQueryPrepared = "INSERT INTO `usuarios` (`id`,`nickname`,`password`, `type`) VALUES (?, ?, ?,'')";

        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, entrada.getId());
        pstm.setString(2, usuario);
        pstm.setString(3, contrasena);

        pstm.executeUpdate();
    }

    /**
     * Actualiza el ID de un cliente en la base de datos.
     *
     * @param entrada El cliente cuyo ID se va a actualizar.
     * @param id El nuevo ID que se asignará al cliente.
     * @param con La conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la operación con la base
     * de datos.
     */
    public static void actualizarIdClienteBBDD(Cliente entrada, int id, Connection con) throws SQLException {
        String myQueryPrepared = "UPDATE `usuarios` SET `id` = ? WHERE `id` = ?";
        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, id);
        pstm.setInt(2, entrada.getId());

        pstm.executeUpdate();
    }

    /**
     * Elimina un cliente de la base de datos.
     *
     * @param idClienteBorrar El ID del cliente que se va a eliminar.
     * @param con La conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la operación con la base
     * de datos.
     */
    public static void eliminarClienteBBDD(int idClienteBorrar, Connection con) throws SQLException {
        String myQueryPrepared = "DELETE FROM usuarios WHERE `id` = ?";
        PreparedStatement pstm = con.prepareStatement(myQueryPrepared);
        pstm.setInt(1, idClienteBorrar);

        pstm.executeUpdate();
    }

}
