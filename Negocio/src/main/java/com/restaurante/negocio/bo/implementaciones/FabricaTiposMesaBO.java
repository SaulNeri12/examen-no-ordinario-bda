
package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;

/**
 * Fábrica que proporciona una instancia de ITiposMesaBO.
 * @author Saul Neri
 */
public class FabricaTiposMesaBO {

    /**
     * Método que devuelve la instancia única de ITiposMesaBO.
     * Si aún no se ha creado, crea una nueva instancia de TiposMesaBO.
     * @return Una instancia de TiposMesaBO que implementa ITiposMesaBO.
     */
    public static ITiposMesaBO obtenerTiposMesaBO() {
        return TiposMesaBO.getInstance();
    }
}
