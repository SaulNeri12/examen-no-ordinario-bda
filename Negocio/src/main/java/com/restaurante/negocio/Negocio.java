/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.restaurante.negocio;

import com.restaurante.negocio.bo.implementaciones.FabricaClientesBO;
import com.restaurante.negocio.bo.interfaces.IClientesBO;
import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.entidades.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author neri
 */
public class Negocio {

    public static void main(String[] args) {
        IClientesBO clientes = FabricaClientesBO.obtenerClientesDAO();

        /*
            List<ClienteDTO> lista = new ArrayList<>();
            
            ClienteDTO c = new ClienteDTO();
            
            c.setNombreCompleto("Miguel Pedro Martinez Quiroz");
            c.setTelefono("453-902-343");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("María Fernanda Lopez Hernández");
            c.setTelefono("291-342-042");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Luis Eduardo Gomez Sanchez");
            c.setTelefono("383-710-952");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Ana Sofía Ramirez Lopez");
            c.setTelefono("512-401-884");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Carlos Juan Perez Hernández");
            c.setTelefono("202-118-763");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Laura Beatriz Sanchez Garcia");
            c.setTelefono("607-345-028");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Alejandro Luis Martinez Ramirez");
            c.setTelefono("814-509-647");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Sofia Camila Gomez Figueroa");
            c.setTelefono("701-230-111");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Miguel Fernando Quiroz Perez");
            c.setTelefono("930-457-983");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Lucia Valeria Sanchez Lopez");
            c.setTelefono("319-602-456");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Oscar Alejandro Garcia Ramirez");
            c.setTelefono("485-802-311");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Isabel Andrea Martinez Gomez");
            c.setTelefono("712-394-521");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Carlos Juan Figueroa Gomez");
            c.setTelefono("514-906-214");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Paula Elena Lopez Sanchez");
            c.setTelefono("825-402-768");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Juan Eduardo Quiroz Perez");
            c.setTelefono("649-528-903");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Gabriela Daniela Ramirez Hernández");
            c.setTelefono("935-284-112");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Miguel Alejandro Martinez Gomez");
            c.setTelefono("208-601-339");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Emilia Regina Sanchez Figueroa");
            c.setTelefono("401-731-908");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Oscar Eduardo Garcia Lopez");
            c.setTelefono("602-894-765");
            lista.add(c);
            
            c = new ClienteDTO();
            c.setNombreCompleto("Julieta Miranda Ramirez Hernandez");
            c.setTelefono("807-315-542");
            lista.add(c);
            
            try {
            clientes.insercionMasivaClientes(lista);
            System.out.println("[!] Se agregaron los clientes correctamente...");
            } catch (BOException ex) {
            System.out.println("[x] ERROR: %s.".formatted(ex.getMessage()));
            }*/
        try {
            System.out.println(clientes.obtenerClientePorTelefono("935-284-112"));
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
