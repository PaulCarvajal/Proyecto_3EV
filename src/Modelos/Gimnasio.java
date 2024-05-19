package Modelos;

import Operativa.OperativaAdministrador;
import Operativa.OperativaComun;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Esta clase representa un gimnasio en un sistema de gestión. Contiene métodos
 * para gestionar salas, clases, horarios y clientes.
 *
 * @author adriana
 */
public class Gimnasio {

    private int id;
    private String nombre;
    private ArrayList<Sala> salas = new ArrayList<>();
    private final int[] horasApertura = {9, 10, 11, 12, 13};
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private final Administrador admin = new Administrador();

    private static Scanner sc = new Scanner(System.in);

    /**
     * Constructor de la clase Gimnasio.
     *
     * @param id El ID del gimnasio.
     * @param nombre El nombre del gimnasio.
     */
    public Gimnasio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del gimnasio.
     *
     * @return El ID del gimnasio.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del gimnasio.
     *
     * @return El nombre del gimnasio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene las salas disponibles en el gimnasio.
     *
     * @return La lista de salas del gimnasio.
     */
    public ArrayList<Sala> getSalas() {
        return salas;
    }

    /**
     * Obtiene las horas de apertura del gimnasio.
     *
     * @return Las horas de apertura del gimnasio.
     */
    public int[] getHorasApertura() {
        return horasApertura;
    }

    /**
     * Obtiene la lista de clientes del gimnasio.
     *
     * @return La lista de clientes del gimnasio.
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Establece el id del gimnasio.
     *
     * @param id El nuevo id del gimnasio.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre del gimnasio.
     *
     * @param nombre El nuevo nombre del gimnasio.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la lista de salas del gimnasio.
     *
     * @param salas La nueva lista de salas del gimnasio.
     */
    public void setSalas(ArrayList<Sala> salas) {
        this.salas = salas;
    }

    /**
     * Establece la lista de clientes del gimnasio.
     *
     * @param clientes La nueva lista de clientes del gimnasio.
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * Convierte los datos del gimnasio a formato CSV.
     *
     * @return Una cadena que representa el gimnasio en formato CSV.
     */
    public String gimnasioToCsv() {
        return id + "," + nombre + "," + salas;
    }

    /**
     * Muestra las salas disponibles en el gimnasio.
     */
    public void mostrarSalas() {
        System.out.println("Mostrando salas...");
        for (Sala sala : salas) {
            System.out.println(sala.salaToCsv());
        }
    }

    /**
     * Muestra las clases disponibles en el gimnasio.
     */
    public void mostrarClases() {//ordenar con sort
        ArrayList<Clase> clases = recorrerClases();
        if (clases.isEmpty()) {
            System.out.println("No hay clases disponibles");
        } else {
            System.out.println("Mostrando clases...");
            for (Clase clase : clases) {
                System.out.println(clase.claseToString());

            }
        }

    }

    /**
     * Muestra los clientes registrados en el gimnasio.
     */
    public void mostrarClientes() {
        System.out.println("Mostrando clientes registrados...");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.clienteToString());
        }
    }


    /*public ArrayList<Horario> horariosReservaClase() {
        ArrayList<Horario> salida = new ArrayList<>();
        Clase clase_a_reservar = seleccionarClaseSinSaberSala();
        salida = clase_a_reservar.horarioDisponible();
        return salida;
    }*/
    /**
     * Recorre todas las clases existentes en las salas del gimnasio.
     *
     * @return Una lista de todas las clases existentes.
     */
    public ArrayList<Clase> recorrerClases() {
        ArrayList<Clase> aux = new ArrayList<>();
        ArrayList<Clase> clases = new ArrayList<>();
        for (Sala sala : salas) {
            aux = sala.getClases();
            for (Clase clase : aux) {
                clases.add(clase);
            }
            //clases.addAll(aux);
        }
        return clases;
    }

    /**
     * Crea una nueva clase para el gimnasio.
     *
     * @return La clase creada.
     */
    public Clase crearClase() {

        int idClase = asignarIdClase();
        int capacidadClase;
        System.out.println("Indica el nombre de la clase: ");
        String nombreClase = sc.nextLine().toUpperCase();
        do {
            try {
                System.out.println("Indica el numero máximo de alumnos de la clase: ");
                capacidadClase = Integer.valueOf(sc.nextLine());
                //break; //para que llegue al return
                return new Clase(idClase, capacidadClase, nombreClase);//o lo devuelvo aqui

            } catch (NumberFormatException e) {
                System.out.println("Numero maximo de alumnos debe ser un valor numerico...");
            }
        } while (true);

        //return new Clase(idClase, capacidadClase, nombreClase);
    }

    /**
     * Selecciona una sala del gimnasio.
     *
     * @return La sala seleccionada.
     */
    public Sala seleccionarSala() {
        int idSala;
        do {
            System.out.println("Introduce el id de la sala: ");
            idSala = OperativaComun.asignarEntero();
            for (Sala sala : salas) {
                if (sala.getId() == idSala) {
                    return sala;
                }
            }
            System.out.println("ID invalido...");
            System.out.println("La sala indicada no existe");
        } while (true);//bucle infinito, no parara hasta que llegue al return, es decir, hasta que se introduzca un id valido
    }

    /**
     * Obtiene las horas libres de una sala para un día específico.
     *
     * @param sala La sala para la cual se desea comprobar las horas libres.
     * @param dia El día de la semana para el cual se desea comprobar las horas
     * libres.
     * @return Una lista de horas libres para la sala y el día especificados.
     */
    public ArrayList<Integer> horasLibresSalaPorDia(Sala sala, DiaSemana dia) {
        ArrayList<Horario> ocupados = sala.horariosOcupadosSalaPorDia(dia);

        ArrayList<Integer> libres = new ArrayList<>();
        for (int i : horasApertura) {
            libres.add(i);
        }
        for (Horario horario : ocupados) {
            for (int i = 0; i < libres.size(); i++) {
                if (libres.get(i) == horario.getHora()) {
                    libres.remove(libres.get(i));
                    i--;
                }
            }
        }
        return libres;
    }

    /**
     * Identifica la sala a la que pertenece una clase específica.
     *
     * @param entrada La clase de la cual se desea identificar la sala.
     * @return La sala a la que pertenece la clase, o null si no se encuentra.
     */
    public Sala identificarSala(Clase entrada) {
        for (Sala sala : salas) {
            if (sala.existeClase(entrada.getId())) {
                return sala;
            }
        }
        return null;//habria que controlar si esto devuelve null porque será que la clase no existe
    }

    /**
     * Permite al usuario seleccionar una clase sin especificar la sala.
     *
     * @return La clase seleccionada por el usuario.
     */
    public Clase seleccionarClaseSinSaberSala() {
        int idClase;
        do {
            System.out.println("Introduce el id de la clase: ");
            idClase = OperativaComun.asignarEntero();
            for (Sala sala : salas) {
                for (Clase clase : sala.getClases()) {
                    if (clase.getId() == idClase) {
                        return clase;
                    }
                }
            }
            System.out.println("ID invalido...");
        } while (true);
    }

    /**
     * Permite al usuario seleccionar una clase sin especificar la sala, a
     * partir de una lista de clases.
     *
     * @param entrada La lista de clases de la cual se puede seleccionar.
     * @return La clase seleccionada por el usuario.
     */
    public Clase seleccionarClaseSinSaberSala(ArrayList<Clase> entrada) {
        int idClase;
        do {
            System.out.println("Introduce el id de la clase: ");
            idClase = OperativaComun.asignarEntero();
            for (Sala sala : salas) {
                for (Clase clase : entrada) {
                    if (clase.getId() == idClase) {
                        return clase;
                    }
                }
            }
            System.out.println("ID invalido...");

        } while (true);
    }

    /**
     * Asigna un nuevo ID para una clase basado en el ID más alto existente en
     * las salas.
     *
     * @return El nuevo ID asignado para la clase.
     */
    public int asignarIdClase() {
        int idMayor = -1;
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).ultimoId() > idMayor) {
                idMayor = salas.get(i).ultimoId();
            }
        }
        return idMayor + 1;
    }

    /**
     * Reasigna IDs a todas las clases en el gimnasio, basado en su orden en la
     * lista de clases.
     */
    public void reasignarIdClase() {
        for (int i = 0; i < recorrerClases().size(); i++) {
            recorrerClases().get(i).setId(i);
        }
    }

    /**
     * Asigna un nuevo ID para un cliente basado en la cantidad actual de
     * clientes.
     *
     * @return El nuevo ID asignado para el cliente.
     */
    public int asignarIdCliente() {
        return clientes.size() + 1;
    }

    /**
     * Reasigna un ID de cliente después de eliminar otro cliente.
     *
     * @param id_borrado El ID del cliente que fue eliminado.
     * @param con La conexión a la base de datos para actualizar el ID en caso
     * necesario.
     * @throws SQLException Si ocurre un error al actualizar el ID en la base de
     * datos.
     */
    public void reasignarIdCliente(int id_borrado, Connection con) throws SQLException {
        int id = id_borrado; //recupero el id del cliente que se borrara

        if (!clientes.isEmpty()) {
            OperativaAdministrador.actualizarIdClienteBBDD(clientes.get(clientes.size() - 1), id, con);
            clientes.get(clientes.size() - 1).setId(id);//getlast()
        }

    }

    /**
     * Permite al usuario seleccionar un cliente a partir de su ID.
     *
     * @return El cliente seleccionado por el usuario.
     */
    public Cliente seleccionarCliente() {
        int idCliente;
        do {
            System.out.println("Introduce el id del cliente: ");
            idCliente = OperativaComun.asignarEntero();
            for (Cliente cliente : clientes) {
                if (cliente.getId() == idCliente) {
                    return cliente;
                }
            }
            System.out.println("ID invalido...");

        } while (true);

    }

    /**
     * Borra a un cliente de todas las clases a las que está inscrito.
     *
     * @param entrada El cliente que se desea borrar.
     */
    public void borrarClienteClases(Cliente entrada) {
        for (Sala sala : salas) {
            for (Clase clase : sala.getClases()) {
                for (Horario horario : clase.buscarMiHorario(entrada)) {
                    horario.getInscritos().remove(entrada);
                }
            }
        }
    }
}
