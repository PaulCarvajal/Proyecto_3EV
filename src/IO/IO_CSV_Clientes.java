/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import Modelos.Cliente;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class IO_CSV_Clientes {

    public static boolean escribir(File f, ArrayList<Cliente> clientes) throws IOException {
        boolean flag = true;
        FileWriter fw = null;//FileWriter es una clase para escribir
        try {
            /*añadiendo true al append del constructor, escribimos sin sobreescribir lo anterior*/
 /*Quito el append para que me sobreescriba cada vez que abro el programa ya que 
            primero leo el contenido que ya tenía el fichero e introduzco en el array  
            los alumnos registrados. Si no sobreescribo, en cada ejecución volvería a escribir 
            todo el contenido recuperado a continuación repitiendo la información cada vez que ejecute*/
            fw = new FileWriter(f);

            /*recorro el arrayList y voy escribiendo los datos de cada objeto alumno (it)
            llamando a su metodo AlumnoToCsv que estructura la información del alumno de 
            la manera deseada*/
            for (Cliente cliente : clientes) {
                fw.write(cliente.clienteToCsv());//Escribo en formato csv los datos de cada sala
                fw.write("\n");// "\n" escribe un salto de linea tras cada alumno

            }
        } catch (IOException ex) {
            /*se maneja cualquier excepción de E/S (entrada/salida) que pueda 
            ocurrir durante la apertura del archivo.*/
            System.out.println("Error al abrir el fichero");
            flag = false;
            System.out.println("Error al guardar el fichero");
            return flag;
        } finally {
            if (fw != null) { //Controlo que no sea null ya que si no ejecuta el try puede ser null
                /*Controlo que se cierre el fw correctamente después de haber 
                terminado de escribir en el archivo, para liberar recursos y 
                evitar posibles pérdidas de datos.*/
                try { // lo hago en un try porque puede crear excepciones al cerrar el fichero
                    fw.close();
                    //cerrar el fichero
                } catch (IOException ex) {
                    System.out.println("Error al cerrar el fichero");
                    //Si da error al cerrar, se pierde lo que se ha escrito?
                }
            }
        }
        System.out.println("Registro de clientes guardado");
        return flag;
    }//revisar optimizacion del flag

    public static ArrayList<Cliente> leer(File f) {
        ArrayList<Cliente> clientes_salida = new ArrayList<>();
        int cont_lineas = 0;
        /*separo la declaración del scanner fuera del try y como se queja de que 
        puede que al llegar al final no se haya inicializado lo inicializo a null*/
        Scanner sc_file = null;
        try {
            //Scanner sc_file = new Scanner(f);//System.in-> lee el teclado // f -> lee el contenido del fichero
            sc_file = new Scanner(f); //lee el contenido del archivo 
            //sc_file = new Scanner(f).useDelimiter(","); // indico yo la separación. Se usa con el sc_file.next(); que por defecto separa por espacios y por saltos 

            //Si llego aquí es que he abierto el fichero
            String linea;
            while (sc_file.hasNextLine()) { //mientras halla lineas a continuacion(devuelva true) sigue leyendo.
                linea = sc_file.nextLine(); //nextLine() me separa por salto de linea -> next() me separa x saltos de linea y x espacios  
                clientes_salida.add(Cliente.crearClienteLeido(linea));

            }
            System.out.println(cont_lineas + " registros leidos"); //cada salto de linea es un registro(me interesa distingui un registro de otro)

        } catch (FileNotFoundException ex) { //También puedo capturar en lugar un IOException
            System.out.println("Error al abrir el fichero");

        } finally {//Hay que cerrar el scanner
            if (sc_file != null) {
                sc_file.close(); //Se queja si he creado el sc_file dentro del try y eso no siempre se ejecuta -> divido l declaración y la instancia       
            }
        }
        return clientes_salida;
    }
}
