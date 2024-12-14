/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.restaurante.negocio.bo.interfaces;

import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.util.List;


/**
 * Define las operaciones necesarias para manejar clientes en el sistema
 * @author neri
 */
public interface IClientesBO {
    
    /**
     * Inserta la lista predefinida de 
     * @param clientesDTO Lista de clientes a insertar
     * @throws BOException Si ocurre un error en la insercion
     */
    public void insercionMasivaClientes(List<ClienteDTO> clientesDTO) throws BOException;

    /**
     * Regresa una lista con todos los clientes en el sistema.
     * @return Lista de clientes
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<ClienteDTO> obtenerClientesTodos() throws BOException;
    
    /**
     * Obtiene el cliente por su numero de telefono registrado en el sistema
     * @param numeroTelefono Numero de telefono del cliente
     * @return Objeto Cliente
     * @throws BOException Si ocurre un error en la consulta
     */
    public ClienteDTO obtenerClientePorTelefono(String numeroTelefono) throws BOException;
}