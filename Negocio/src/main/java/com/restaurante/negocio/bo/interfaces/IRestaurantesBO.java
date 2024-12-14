
package com.restaurante.negocio.bo.interfaces;

import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.util.List;

/**
 * 
 * @author neri
 */
public interface IRestaurantesBO {
    
    /**
     * Obtiene todos las sucursales del restaurante en el sistema
     * @return Lista sucursales restaurante
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<RestauranteDTO> obtenerRestaurantesTodos() throws BOException;
    
    /**
     * Obtiene la sucursal del restaurante con el ID dado
     * @param id ID de la sucursal del restaurante a buscar
     * @return Restaurante sucursal
     * @throws BOException Si ocurre un error en la consulta
     */
    public RestauranteDTO obtenerRestaurantePorID(Long id) throws BOException;
    
    /**
     * Obtiene la sucursal del restaurante con el numero telefonico dado
     * @param numeroTelefono Numero de telefono de la sucursal
     * @return Restaurante
     * @throws BOException Si ocurre un error en la consulta 
     */
    public RestauranteDTO obtenerRestaurantePorNumeroTelefono(String numeroTelefono) throws BOException;
    
    /**
     * Agrega una nueva sucursal del restaurante al sistema
     * @param restaurante Nueva sucursal del restaurante
     * @throws BOException Si ocurre un error en la insercion
     */
    public void agregarRestaurante(RestauranteDTO restaurante) throws BOException;
    
    /**
     * Actualiza la informacion de la sucursal del restaurante dado
     * @param restaurante Sucursal del restaurante
     * @throws BOException Si ocurre un error en la modificacion
     */
    public void actualizarRestaurante(RestauranteDTO restaurante) throws BOException;
    
    /**
     * Elimina una sucursal en el sistema
     * @param idRestaurante ID de la sucursal a eliminar
     * @throws BOException Si ocurre un error en la eliminacion
     */
    public void eliminarRestaurante(Long idRestaurante) throws BOException;
}
