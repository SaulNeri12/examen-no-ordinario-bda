

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.dtos.convertidores.TipoMesaConvertidor;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaTiposMesaDAO;
import com.restaurante.persistencia.dao.interfaces.ITiposMesaDAO;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;

/**
 * Implementa los metodos que define la interfaz ITiposMesaBO, la cual nos 
 * permite interactuar con la tabla de tipos de mesa en la base de datos.
 * @author Saul Neri
 */
class TiposMesaBO implements ITiposMesaBO {

    /**
     * Instancia unica necesaria para el singleton.
     */
    private static TiposMesaBO instancia;
    
    /**
     * DAO necesario para interactuar con la tabla de tipos de mesa.
     */
    private final ITiposMesaDAO tiposMesaDAO;

    /**
     * Constructor privado necesario para la implementacion del singleton.
     */
    private TiposMesaBO() {
        this.tiposMesaDAO = FabricaTiposMesaDAO.obtenerTiposMesaDAO();
    }

    /**
     * Obtiene la instancia unica del BO de tipos de mesa.
     * @return Instancia unica (singleton)
     */
    public static TiposMesaBO getInstance() {
        if (instancia == null) {
            instancia = new TiposMesaBO();
        }
        return instancia;
    }
    
    @Override
    public List<TipoMesaDTO> obtenerTiposMesaTodos() throws BOException {
        try {
            List<TipoMesaDTO> tiposMesaDTO = this.tiposMesaDAO.obtenerTiposMesaTodos()
                    .stream()
                    .map(TipoMesaConvertidor::convertirADTO)
                    .toList();
            
            return tiposMesaDTO;
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void agregarTipoMesa(TipoMesaDTO tipoMesa) throws BOException {
        try {
            this.tiposMesaDAO.agregarTipoMesa(TipoMesaConvertidor.convertirAEntidad(tipoMesa));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void eliminarTipoMesa(Long id) throws BOException {
        try {
            this.tiposMesaDAO.eliminarTipoMesa(id);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
}
