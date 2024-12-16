/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.dao.interfaces.ITiposMesaDAO;

/**
 * Fábrica que proporciona una instancia de ITiposMesaDAO.
 * @author Saul Neri
 */
public class FabricaTiposMesaDAO {

    /**
     * Método estático que devuelve la instancia única de ITiposMesaDAO.
     * Si aún no se ha creado, crea una nueva instancia de TiposMesaDAO.
     * @return Una instancia de TiposMesaDAO que implementa ITiposMesaDAO.
     */
    public static ITiposMesaDAO obtenerTiposMesaDAO() {
        return TiposMesaDAO.getInstance();
    }
}
