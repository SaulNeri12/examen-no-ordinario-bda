/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IClientesBO;
import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.dtos.convertidores.ClienteConvertidor;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaClientesDAO;
import com.restaurante.persistencia.dao.interfaces.IClientesDAO;
import com.restaurante.persistencia.entidades.Cliente;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;

/**
 * Implementa los metodos de la interfaz IClientesBO, la cual nos proporciona
 * metodos para acceder a la tabla clientes en la base de datos.
 * @author Saul Neri
 */
class ClientesBO implements IClientesBO {

    /**
     * Instancia unica necesaria para el singleton.
     */
    private static ClientesBO instancia;
    
    /**
     * DAO necesario para interactuar con la tabla de clientes.
     */
    private final IClientesDAO clientesDAO;

    /**
     * Constructor privado necesario para la implementacion del singleton.
     */
    private ClientesBO() {
        this.clientesDAO = FabricaClientesDAO.obtenerClientesDAO();
    }

    /**
     * Obtiene la instancia unica del BO de clientes.
     * @return Instancia unica (singleton)
     */
    public static ClientesBO getInstance() {
        if (instancia == null) {
            instancia = new ClientesBO();
        }
        return instancia;
    }
    
    @Override
    public void insercionMasivaClientes(List<ClienteDTO> clientesDTO) throws BOException {
        
        if (clientesDTO == null) {
            throw new BOException("No se pudo realizar la insercion masiva ya que no se proporciono ningun cliente.");
        }
        
        List<Cliente> clientes = clientesDTO.stream().map(ClienteConvertidor::convertirAEntidad).toList();
        
        try {
            this.clientesDAO.insercionMasivaClientes(clientes);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<ClienteDTO> obtenerClientesTodos() throws BOException {
        try {
            return this.clientesDAO.obtenerClientesTodos()
                    .stream()
                    .map(ClienteConvertidor::convertirADTO)
                    .toList();
            
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public ClienteDTO obtenerClientePorTelefono(String numeroTelefono) throws BOException {
        try {
            return ClienteConvertidor.convertirADTO(this.clientesDAO.obtenerClientePorTelefono(numeroTelefono));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
}
