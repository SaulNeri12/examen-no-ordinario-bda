/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.restaurante.negocio.bo.interfaces;

import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.util.List;

/**
 * Define las operaciones necesarias para las mesas en el restaurante
 * @author neri
 */
public interface IMesasBO {
    
    /**
     * Devuelve todas las mesas registradas en el sistema
     * @param idRestaurante ID del restaurante
     * @return
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<MesaDTO> obtenerMesasTodas(Long idRestaurante) throws BOException;
    
    /**
     * Devuelve una lista con las mesas disponibles para su reservacion
     * @param idRestaurante ID del restaurante
     * @return Mesas disponibles
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<MesaDTO> obtenerMesasDisponibles(Long idRestaurante) throws BOException;
    
    /**
     * Devuelve todas las mesas del tipo especificado
     * @param idRestaurante ID del restaurante en cuestion
     * @param tipo Tipo de mesa
     * @return
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<MesaDTO> obtenerMesasPorTipo(Long idRestaurante, TipoMesaDTO tipo) throws BOException;
    
    /**
     * Inserta de manera "masiva" el numero dado de mesas con el tipo y
     * ubicacion especifica.
     * @param idRestautante ID del restaurante donde se agregaran las mesas
     * @param tipo Tipo de mesas a insertar
     * @param ubicacion Ubicacion de las mesas a insertar
     * @param cantidad Cantidad de las mesas a insertar
     * @throws BOException Si ocurre un error al insertar las mesas.
     */
    public void insertarMesas(Long idRestautante, TipoMesaDTO tipo, UbicacionMesaDTO ubicacion, int cantidad) throws BOException;
    
    /**
     * Elimina una mesa en el sistema por su codigo especificado
     * @param idRestaurante ID del restaurante en donde se eliminara la mesa
     * @param codigo Codigo de la mesa a eliminar
     * @throws BOException Si ocurre un error en la eliminacion
     */
    public void eliminarMesa(Long idRestaurante, String codigo) throws BOException;
}