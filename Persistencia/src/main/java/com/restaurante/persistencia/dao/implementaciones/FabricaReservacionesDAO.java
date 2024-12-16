

package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.dao.interfaces.IReservacionesDAO;

/**
 * Fábrica que proporciona una instancia de IReservacionesDAO.
 * @author Saul Neri
 */
public class FabricaReservacionesDAO {

    /**
     * Método estático que devuelve la instancia única de IReservacionesDAO.
     * Si aún no se ha creado, crea una nueva instancia de ReservacionesDAO.
     * @return Una instancia de ReservacionesDAO que implementa IReservacionesDAO.
     */
    public static IReservacionesDAO obtenerReservacionesDAO() {
        return ReservacionesDAO.getInstance();
    }
}