package Modelos;

import Operativa.OperativaComun;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
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

    public Gimnasio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public int[] getHorasApertura() {
        return horasApertura;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalas(ArrayList<Sala> salas) {
        this.salas = salas;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String gimnasioToCsv() {
        return id + "," + nombre + "," + salas;
    }

    public void mostrarSalas() {
        System.out.println("Mostrando salas...");
        for (Sala sala : salas) {
            System.out.println(sala.salaToCsv());
        }
    }

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

    public void mostrarClientes() {
        System.out.println("Mostrando clientes registrados...");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.clienteToString());
        }
    }

    public ArrayList<Horario> horariosReservaClase() {
        ArrayList<Horario> salida = new ArrayList<>();
        Clase clase_a_reservar = seleccionarClaseSinSaberSala();
        salida = clase_a_reservar.horarioDisponible();
        return salida;
    }

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

    public boolean existeSala(int idSala) {
        for (Sala sala : salas) {
            if (idSala == sala.getId()) {
                return true;
            }
        }
        return false;
    }

    public Sala seleccionarSala() {//hay que ver como controlar que no me meta una sala inexistente y si no me mete un numero
        int idSala;
        do {
            System.out.println("Introduce el id de la sala: ");
            try {
                idSala = Integer.valueOf(sc.nextLine());
                for (Sala sala : salas) {
                    if (sala.getId() == idSala) {
                        return sala;
                    }
                }
                System.out.println("ID invalido...");
                System.out.println("La sala indicada no existe");
            } catch (NumberFormatException e) {
                System.out.println("ID invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true);//bucle infinito, no parara hasta que llegue al return, es decir, hasta que se introduzca un id valido
    }

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

    public Sala identificarSala(Clase entrada) {
        for (Sala sala : salas) {
            if (sala.existeClase(entrada.getId())) {
                return sala;
            }
        }
        return null;//habria que controlar si esto devuelve null porque será que la clase no existe
    }

    public Clase seleccionarClaseSinSaberSala() {
        int idClase;
        do {
            try {
                System.out.println("Introduce el id de la clase: ");
                idClase = Integer.valueOf(sc.nextLine());
                for (Sala sala : salas) {
                    for (Clase clase : sala.getClases()) {
                        if (clase.getId() == idClase) {
                            return clase;
                        }
                    }
                }
                System.out.println("ID invalido...");
            } catch (NumberFormatException e) {
                System.out.println("ID invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }

        } while (true);
    }

    public Clase seleccionarClaseSinSaberSala(ArrayList<Clase> entrada) {
        int idClase;
        do {
            try {
                System.out.println("Introduce el id de la clase: ");
                idClase = Integer.valueOf(sc.nextLine());
                for (Sala sala : salas) {
                    for (Clase clase : entrada) {
                        if (clase.getId() == idClase) {
                            return clase;
                        }
                    }
                }
                System.out.println("ID invalido...");
            } catch (NumberFormatException e) {
                System.out.println("ID invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }

        } while (true);
    }

    public int asignarIdClase() {
        int idMayor = -1;
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).ultimoId() > idMayor) {
                idMayor = salas.get(i).ultimoId();
            }
        }
        return idMayor + 1;
    }

    public void reasignarIdClase() {
        int id = 0;
        for (Sala sala : salas) {
            for (int i = 0; i < sala.getClases().size(); i++) {
                sala.getClases().get(i).setId(id);
                id++;
            }
        }
    }

    public int asignarIdCliente() {
        return clientes.size() + 1;
    }

    public void reasignarIdCliente(int id_borrado) throws SQLException {
        int id = id_borrado; //recupero el id del cliente que se borrara

        if (!clientes.isEmpty()) {
            OperativaComun.actualizarIdCliente(clientes.get(clientes.size() - 1), id);
            clientes.get(clientes.size() - 1).setId(id);//getlast()
        }

    }

    public Cliente seleccionarCliente() {
        int idCliente;
        do {
            try {
                System.out.println("Introduce el id del cliente: ");
                idCliente = Integer.valueOf(sc.nextLine());
                for (Cliente cliente : clientes) {
                    if (cliente.getId() == idCliente) {
                        return cliente;
                    }
                }
                System.out.println("ID invalido...");
            } catch (NumberFormatException e) {
                System.out.println("ID invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }

        } while (true);

    }

}
