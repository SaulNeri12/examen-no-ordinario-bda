/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.restaurante.persistencia.dao.interfaces;

import com.restaurante.persistencia.entidades.Mesa;
import com.restaurante.persistencia.entidades.TipoMesa;
import com.restaurante.persistencia.entidades.UbicacionMesa;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;

/**
 * Define las operaciones necesarias para las mesas en el restaurante
 * @author neri
 */
public interface IMesasDAO {
    
    /**
     * Devuelve todas las mesas registradas en el sistema
     * @param idRestaurante ID del restaurante
     * @return
     * @throws DAOException Si ocurre un error en la consulta
     */
    public List<Mesa> obtenerMesasTodas(Long idRestaurante) throws DAOException;
    
    /**
     * Devuelve una lista con las mesas disponibles para su reservacion
     * @param idRestaurante ID del restaurante
     * @return Mesas disponibles
     * @throws DAOException Si ocurre un error en la consulta
     */
    public List<Mesa> obtenerMesasDisponibles(Long idRestaurante) throws DAOException;
    
    /**
     * Devuelve todas las mesas del tipo especificado
     * @param idRestaurante ID del restaurante en cuestion
     * @param tipo Tipo de mesa
     * @return
     * @throws DAOException Si ocurre un error en la consulta
     */
    public List<Mesa> obtenerMesasPorTipo(Long idRestaurante, TipoMesa tipo) throws DAOException;
    
    /**
     * Inserta de manera "masiva" el numero dado de mesas con el tipo y
     * ubicacion especifica.
     * @param idRestautante ID del restaurante donde se agregaran las mesas
     * @param tipo Tipo de mesas a insertar
     * @param ubicacion Ubicacion de las mesas a insertar
     * @param cantidad Cantidad de las mesas a insertar
     * @throws DAOException Si ocurre un error al insertar las mesas.
     */
    public void insertarMesas(Long idRestautante, TipoMesa tipo, UbicacionMesa ubicacion, int cantidad) throws DAOException;
    
    /**
     * Elimina una mesa en el sistema por su codigo especificado
     * @param idRestaurante ID del restaurante en donde se eliminara la mesa
     * @param codigo Codigo de la mesa a eliminar
     * @throws DAOException Si ocurre un error en la eliminacion
     */
    public void eliminarMesa(Long idRestaurante, String codigo) throws DAOException;
}