/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaRestaurantesDAO;
import com.restaurante.persistencia.dao.interfaces.IRestaurantesDAO;
import java.util.List;

/**
 * Implementa los metodos definidos por la interfaz IRestaurantesBO, la cual
 * proporciona funcionalidad para interactuar con la tabla de restaurantes
 * en la base de datos.
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RestauranteDTO obtenerRestaurantePorID(Long id) throws BOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RestauranteDTO obtenerRestaurantePorNumeroTelefono(String numeroTelefono) throws BOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarRestaurante(RestauranteDTO restaurante) throws BOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarRestaurante(RestauranteDTO restaurante) throws BOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarRestaurante(Long idRestaurante) throws BOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
