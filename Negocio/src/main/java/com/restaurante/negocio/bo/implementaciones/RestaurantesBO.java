
package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.dtos.convertidores.RestauranteConvertidor;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaRestaurantesDAO;
import com.restaurante.persistencia.dao.interfaces.IRestaurantesDAO;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;

/**
 * Implementa los metodos definidos por la interfaz IRestaurantesBO, la cual
 * proporciona funcionalidad para interactuar con la tabla de restaurantes en la
 * base de datos.
 *
 * @author Saul Neri
 */
class RestaurantesBO implements IRestaurantesBO {
    
    private static IRestaurantesBO instancia;

    /**
     * DAO de restaurantes necesaria para el funcionamiento del BO.
     */
    private final IRestaurantesDAO restaurantes;

    /**
     * Constructor privado necesario para la implementacion del singleon.
     */
    private RestaurantesBO() {
        this.restaurantes = FabricaRestaurantesDAO.obtenerRestaurantesDAO();
    }

    /**
     * Obtiene una instancia anonima del BO de restaurantes.
     *
     * @return Intancia unica de
     */
    public static IRestaurantesBO getInstance() {
        if (instancia == null) {
            instancia = new RestaurantesBO();
        }
        return instancia;
    }
    
    @Override
    public List<RestauranteDTO> obtenerRestaurantesTodos() throws BOException {
        try {
            return this.restaurantes.obtenerRestaurantesTodos()
                    .stream()
                    .map(RestauranteConvertidor::convertirADTO)
                    .toList();
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
    
    @Override
    public RestauranteDTO obtenerRestaurantePorID(Long id) throws BOException {
        try {
            return RestauranteConvertidor.convertirADTO(this.restaurantes.obtenerRestaurantePorID(id));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
    
    @Override
    public RestauranteDTO obtenerRestaurantePorNumeroTelefono(String numeroTelefono) throws BOException {
        try {
            return RestauranteConvertidor.convertirADTO(this.restaurantes.obtenerRestaurantePorNumeroTelefono(numeroTelefono));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
    
    @Override
    public void agregarRestaurante(RestauranteDTO restaurante) throws BOException {
        try {
            this.restaurantes.agregarRestaurante(RestauranteConvertidor.convertirAEntidad(restaurante));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
    
    @Override
    public void actualizarRestaurante(RestauranteDTO restaurante) throws BOException {
        try {
            this.restaurantes.actualizarRestaurante(RestauranteConvertidor.convertirAEntidad(restaurante));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
    
    @Override
    public void eliminarRestaurante(Long idRestaurante) throws BOException {
        try {
            this.restaurantes.eliminarRestaurante(idRestaurante);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
}
