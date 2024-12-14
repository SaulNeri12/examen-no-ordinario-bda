/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.dao.interfaces.IMesasDAO;

/**
 * Fábrica para obtener el DAO de Mesas.
 * Esta clase proporciona un método para acceder a la instancia única del DAO de Mesas.
 * 
 * @author Saul Neri
 */
public class FabricaMesasDAO {

    /**
     * Devuelve la instancia única del DAO de Mesas.
     * Este método utiliza el patrón Singleton para garantizar que solo exista una instancia de IMesasDAO.
     * 
     * @return Instancia de IMesasDAO.
     */
    public static IMesasDAO obtenerMesasDAO() {
        return MesasDAO.getInstance();
    }
}
