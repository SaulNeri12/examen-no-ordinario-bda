/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IClientesBO;


/**
 * Fábrica para obtener el BO de Clientes.
 * Esta clase proporciona un método para acceder a la instancia única del BO de Clientes.
 * @author Saul Neri
 */
public class FabricaClientesBO {

    /**
     * Devuelve la instancia única del BO de Clientes.
     * Este método utiliza el patrón Singleton para garantizar que solo exista una instancia de IClientesBO.
     * 
     * @return Instancia de IClientesBO.
     */
    public static IClientesBO obtenerClientesDAO() {
        return ClientesBO.getInstance();
    }
}
