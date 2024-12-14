/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.dao.interfaces.IClientesDAO;

/**
 * Fábrica para obtener el DAO de Clientes.
 * Esta clase proporciona un método para acceder a la instancia única del DAO de Clientes.
 * 
 * @author Saul Neri
 */
public class FabricaClientesDAO {

    /**
     * Devuelve la instancia única del DAO de Clientes.
     * Este método utiliza el patrón Singleton para garantizar que solo exista una instancia de IClientesDAO.
     * 
     * @return Instancia de IClientesDAO.
     */
    public static IClientesDAO obtenerClientesDAO() {
        return ClientesDAO.getInstance();
    }
}
