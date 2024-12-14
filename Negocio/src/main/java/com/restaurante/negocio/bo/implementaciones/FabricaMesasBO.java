
package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IMesasBO;

/**
 * Fábrica que proporciona una instancia de IMesasBO.
 * @author Saul Neri
 */
public class FabricaMesasBO {

    /**
     * Método estático que devuelve la instancia única de IMesasBO.
     * Si aún no se ha creado, crea una nueva instancia de MesasBO.
     * @return Una instancia de MesasBO que implementa IMesasBO.
     */
    public static IMesasBO obtenerMesasBO() {
        return MesasBO.getInstance();
    }
}

