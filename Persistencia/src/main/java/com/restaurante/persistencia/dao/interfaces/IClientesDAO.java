/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.restaurante.persistencia.dao.interfaces;

import com.restaurante.persistencia.entidades.Cliente;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;


/**
 * Define las operaciones necesarias para manejar clientes en el sistema
 * @author neri
 */
public interface IClientesDAO {
    /**
     * Inserta la lista predefinida de 
     * @param clientes Lista de clientes a insertar
     * @throws DAOException Si ocurre un error en la insercion
     */
    public void insercionMasivaClientes(List<Cliente> clientes) throws DAOException;

    /**
     * Regresa una lista con todos los clientes en el sistema.
     * @return Lista de clientes
     * @throws DAOException Si ocurre un error en la consulta
     */
    public List<Cliente> obtenerClientesTodos() throws DAOException;
    
    /**
     * Obtiene el cliente por su numero de telefono registrado en el sistema
     * @param numeroTelefono Numero de telefono del cliente
     * @return Objeto Cliente
     * @throws DAOException Si ocurre un error en la consulta
     */
    public Cliente obtenerClientePorTelefono(String numeroTelefono) throws DAOException;
}