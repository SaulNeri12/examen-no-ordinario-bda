
package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.dao.interfaces.IRestaurantesDAO;

/**
 * Fábrica para obtener el DAO de Restaurantes.
 * @author Saul Neri
 */
public class FabricaRestaurantesDAO {
    
    /**
     * Devuelve la instancia única del DAO de Restaurantes.
     * @return Instancia de IRestaurantesDAO.
     */
    public static IRestaurantesDAO obtenerRestaurantesDAO() {
        return RestaurantesDAO.getInstance();
    }
}