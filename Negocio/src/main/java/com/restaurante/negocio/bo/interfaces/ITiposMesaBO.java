
package com.restaurante.negocio.bo.interfaces;

import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.util.List;


/**
 * Define las operaciones necesarias para manejar los tipos de mesa en el sistema
 * @author neri
 */
public interface ITiposMesaBO {
    
    /**
     * Devuelve todos los tipos de mesa en el sistema
     * @return Lista de los distintos tipos de mesa
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<TipoMesaDTO> obtenerTiposMesaTodos() throws BOException;
    
    /**
     * Agrega un tipo de mesa en el sistema
     * @param tipoMesa Tipo de mesa a registrar
     * @throws BOException Si ocurre un error en la agregacion
     */
    public void agregarTipoMesa(TipoMesaDTO tipoMesa) throws BOException;
    
    /**
     * Elimina un tipo de mesa en el sistema
     * @param id ID del tipo de mesa a eliminar
     * @throws BOException Si ocurre un error en la eliminacion del tipo
     */
    public void eliminarTipoMesa(Long id) throws BOException;
}