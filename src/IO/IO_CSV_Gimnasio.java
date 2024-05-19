/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO;

import Modelos.Clase;
import Modelos.Cliente;
import Modelos.Gimnasio;
import Modelos.Horario;
import Modelos.Sala;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 ** La clase IO_CSV_Gimnasio proporciona métodos para leer y escribir datos de
 * un gimnasio, incluyendo salas, clases, horarios y clientes, en formato CSV.
 * 
 * @author Usuario
 */
public class IO_CSV_Gimnasio {
    /**
     * Escribe la lista de salas, junto con sus clases, horarios y clientes, en
     * un archivo CSV.
     * 
     * @param f     El archivo en el que se guardarán los datos del gimnasio.
     * @param salas La lista de salas a escribir en el archivo.
     * @return      true si la operación fue exitosa, false en caso de error.
     * @throws IOException Si ocurre un error de E/S durante la escritura.
     */
    public static boolean escribir(File f, ArrayList<Sala> salas) throws IOException {
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
            for (Sala sala : salas) {
                fw.write("#S" + "\n");// "#S" escribe un #S tras cada clase  
                fw.write(sala.salaToCsv());//Escribo en formato csv los datos de cada sala
                fw.write("\n");// "\n" escribe un salto de linea tras cada alumno
                for (Clase clase : sala.getClases()) {
                    fw.write("#C" + "\n");// "#C" escribe un #C tras cada clase  
                    fw.write(clase.claseToCsv());//Escribo en formato csv los datos de cada alumno
                    fw.write("\n");// "\n" escribe un salto de linea tras cada alumno
                    for (Horario horario : clase.getHorario()) {
                        fw.write("#H" + "\n");// "#H" escribe un #H tras cada clase 
                        fw.write(horario.horarioToCsv());//Escribo en formato csv los datos de cada horario
                        fw.write("\n");// "\n" escribe un salto de linea tras cada horario
                        for (Cliente inscrito : horario.getInscritos()) {
                            fw.write("#I" + "\n");// "#H" escribe un #H tras cada clase 
                            fw.write(inscrito.clienteToCsv());//Escribo en formato csv los datos de cada horario
                            fw.write("\n");// "\n" escribe un salto de linea tras cada horario                        
                        }

                    }
                }
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
        System.out.println("Registro de clases guardado");
        return flag;
    }//revisar optimizacion del flag

    /**
     * Lee los datos del gimnasio desde un archivo CSV y los carga en el objeto
     * Gimnasio proporcionado.
     *
     * @param f El archivo desde el cual se leerán los datos del gimnasio.
     * @param g1 El objeto Gimnasio en el que se cargarán los datos leídos.
     * @return Una lista de salas leídas del archivo.
     */
    public static ArrayList<Sala> leer(File f, Gimnasio g1) {
        ArrayList<Sala> salas_salida = new ArrayList<>();
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
            Sala sala_aux = null;//y si no la quiero inicializar a null que??????
            Clase clase_aux = null;//y si no la quiero inicializar a null que??????
            Horario horario_aux = null;//y si no la quiero inicializar a null que??????

            while (sc_file.hasNextLine()) { //mientras halla lineas a continuacion(devuelva true) sigue leyendo.
                linea = sc_file.nextLine(); //nextLine() me separa por salto de linea -> next() me separa x saltos de linea y x espacios  
                if (linea.equals("#S")) {//clase
                    linea = sc_file.nextLine();
                    for (Sala sala : g1.getSalas()) {//recorro las salas de mi gimnasio para encontrar a la que hace referencia el registro
                        if (Integer.valueOf(linea) == sala.getId()) {
                            sala_aux = sala; //indico en que sala ya existente y fija se guardan las clases
                        }
                    }
                }
                if (linea.equals("#C")) {//clase
                    cont_lineas++;
                    linea = sc_file.nextLine();
                    clase_aux = Clase.crearClaseLeida(linea);
                    sala_aux.getClases().add(clase_aux);
                }
                if (linea.equals("#H")) {//horarios
                    linea = sc_file.nextLine();
                    horario_aux = Horario.crearHorarioLeido(linea);
                    clase_aux.getHorario().add(horario_aux);
                }
                if (linea.equals("#I")) {//clientes inscritos
                    linea = sc_file.nextLine();
                    horario_aux.getInscritos().add(Cliente.crearClienteLeido(linea));
                }
            }
            System.out.println(cont_lineas + " registros leidos"); //cada salto de linea es un registro(me interesa distingui un registro de otro)

        } catch (FileNotFoundException ex) { //También puedo capturar en lugar un IOException
            System.out.println("Error al abrir el fichero");

        } finally {//Hay que cerrar el scanner
            if (sc_file != null) {
                sc_file.close(); //Se queja si he creado el sc_file dentro del try y eso no siempre se ejecuta -> divido l declaración y la instancia       
            }
        }
        return salas_salida;
    }
}
