/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.conexion.Conexion;
import com.restaurante.persistencia.dao.interfaces.IClientesDAO;
import com.restaurante.persistencia.entidades.Cliente;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

/**
 * Implementa los metodos de la interfaz IClientesDAO.
 *
 * @author Saul Neri
 */
class ClientesDAO implements IClientesDAO {

    private static ClientesDAO instancia;

    private ClientesDAO() {
    }

    /**
     * Obtiene la instancia unica del DAO de clientes.
     *
     * @return Instancia unica (singleton)
     */
    public static ClientesDAO getInstance() {
        if (instancia == null) {
            instancia = new ClientesDAO();
        }
        return instancia;
    }

    @Override
    public void insercionMasivaClientes(List<Cliente> clientes) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for (Cliente cliente : clientes) {
                entityManager.persist(cliente);
            }
            entityManager.flush();

            transaction.commit();

        } catch (Exception e) {
            throw new DAOException("Error al insertar clientes de manera masiva.");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Cliente> obtenerClientesTodos() throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        } catch (Exception ex) {
            throw new DAOException("Error al intentar obtener los clientes registrados en el sistema, porfavor intente mas tarde");
        }
    }

    @Override
    public Cliente obtenerClientePorTelefono(String numeroTelefono) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.telefono = :telefono", Cliente.class)
                    .setParameter("telefono", numeroTelefono)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new DAOException("No se encontro al cliente con el numero de telefono especificado.");
        } catch (Exception ex) {
            throw new DAOException("Error al intentar obtener los clientes registrados en el sistema, porfavor intente mas tarde.");
        }
    }
}