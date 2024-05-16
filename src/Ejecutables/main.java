package Ejecutables;

import Modelos.Sala;
import Modelos.Cliente;
import Modelos.Gimnasio;
import Operativa.OperativaComun;
import java.io.IOException;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author adriana
 */
public class main {
    public static void main(String[] args) throws IOException, SQLException {

        //CLIENTES
        /*
        Cliente c1 = new Cliente(1, "Adriana", 23);
        Cliente c2 = new Cliente(2, "Paul", 18);*/
        
        Sala s1 = new Sala(0);
        Sala s2 = new Sala(1);

        Gimnasio g1 = new Gimnasio(0, "AtlasGym");
        g1.getSalas().add(s1);
        g1.getSalas().add(s2);
        
        for (Cliente cliente : g1.getClientes()) {
            System.out.println(cliente.clienteToCsv());
        }
        OperativaComun.iniciarPrograma(g1);
        

    }

}
