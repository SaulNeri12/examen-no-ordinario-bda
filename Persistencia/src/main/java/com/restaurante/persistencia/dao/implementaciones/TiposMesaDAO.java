
package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.conexion.Conexion;
import com.restaurante.persistencia.dao.interfaces.ITiposMesaDAO;
import com.restaurante.persistencia.entidades.TipoMesa;
import com.restaurante.persistencia.excepciones.DAOException;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;


/**
 * Implementa los metodos de la interfaz ITiposMesaDAO, la cual nos proporciona
 * metodos para interactuar con la tabla de tipos de mesa en la base de datos.
 * @author Saul Neri
 */
class TiposMesaDAO implements ITiposMesaDAO {

    /**
     * Intancia unica necesaria para implementar el singleton.
     */
    private static TiposMesaDAO instancia;

    /**
     * Constructor privado para implementacion del singleton.
     */
    private TiposMesaDAO() {
        
    }

    /**
     * Obtiene la instancia unica del tipos de mesa.
     * @return Tipos de mesa.
     */
    public static TiposMesaDAO getInstance() {
        if (instancia == null) {
            instancia = new TiposMesaDAO();
        }
        return instancia;
    }
    
    @Override
    public List<TipoMesa> obtenerTiposMesaTodos() throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();

        try {
            TypedQuery<TipoMesa> query = entityManager.createQuery("SELECT t FROM TipoMesa t", TipoMesa.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DAOException("No se pudo obtener los tipos de mesa en el sistema, porfavor intentelo mas tarde.");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void agregarTipoMesa(TipoMesa tipoMesa) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            boolean existe = entityManager.createQuery("SELECT COUNT(t) FROM TipoMesa t WHERE t.nombre LIKE :nombreTipo", Long.class)
                .setParameter("nombreTipo", tipoMesa.getNombre())
                .getSingleResult() > 0;
            
            if (existe) {
                throw new DAOException("El tipo de mesa a agregar ya fue registrado previamente.");
            }
            
            transaction.begin();
            entityManager.persist(tipoMesa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            
            throw new DAOException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void eliminarTipoMesa(Long id) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();
        
        try {
            
            boolean noExiste = entityManager.createQuery("SELECT COUNT(t) FROM TipoMesa t WHERE t.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() == 0;
            if (noExiste) {
                throw new DAOException("El tipo de mesa con el ID dado no existe");
            }
            
            transaction.begin();

            TipoMesa tipoMesa = entityManager.find(TipoMesa.class, id);
            if (tipoMesa != null) {
                entityManager.remove(tipoMesa);
            }

            transaction.commit();
        } catch (Exception e) {
            
            if (transaction.isActive()) {
                transaction.rollback();
            }
            
            throw new DAOException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}