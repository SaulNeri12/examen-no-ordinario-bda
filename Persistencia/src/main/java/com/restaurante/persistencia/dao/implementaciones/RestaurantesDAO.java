package com.restaurante.persistencia.dao.implementaciones;

import com.restaurante.persistencia.conexion.Conexion;
import com.restaurante.persistencia.dao.interfaces.IRestaurantesDAO;
import com.restaurante.persistencia.entidades.Restaurante;
import com.restaurante.persistencia.excepciones.DAOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 * Implementa los metodos definidos por IRestaurantesDAO
 *
 * @author Saul Neri
 */
class RestaurantesDAO implements IRestaurantesDAO {

    private static RestaurantesDAO instancia;

    private RestaurantesDAO() {
    }

    /**
     * Obtiene la instancia unica del DAO de restaurantes.
     *
     * @return Instancia unica (singleton)
     */
    public static RestaurantesDAO getInstance() {
        if (instancia == null) {
            instancia = new RestaurantesDAO();
        }
        return instancia;
    }

    @Override
    public List<Restaurante> obtenerRestaurantesTodos() throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        try {
            TypedQuery<Restaurante> query = entityManager.createQuery("SELECT r FROM Restaurante r", Restaurante.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DAOException("Error al obtener todos los restaurantes");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Restaurante obtenerRestaurantePorID(Long id) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        try {
            return entityManager.find(Restaurante.class, id);
        } catch (NoResultException e) {
            throw new DAOException("No se encontro al restaurante con el ID dado [telefono: %d]".formatted(id));
        } catch (Exception e) {
            throw new DAOException("Error al obtener el restaurante por ID");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Restaurante obtenerRestaurantePorNumeroTelefono(String numeroTelefono) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        try {
            TypedQuery<Restaurante> query = entityManager.createQuery(
                    "SELECT r FROM Restaurante r WHERE r.telefono = :telefono", Restaurante.class);
            query.setParameter("telefono", numeroTelefono);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new DAOException("No se encontro al restaurante con el numero de telefono dado [telefono: %s]".formatted(numeroTelefono));
        } catch (Exception e) {
            throw new DAOException("Error al obtener el restaurante por número de teléfono");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void agregarRestaurante(Restaurante restaurante) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            boolean telefonoEnUso = entityManager.createQuery(
                    "SELECT COUNT(r) FROM Restaurante r WHERE r.telefono = :telefono", Long.class)
                    .setParameter("telefono", restaurante.getTelefono())
                    .getSingleResult() > 0;

            if (telefonoEnUso) {
                throw new DAOException("El número de teléfono ya está en uso por otro restaurante.");
            }

            boolean direccionEnUso = entityManager.createQuery(
                    "SELECT COUNT(r) FROM Restaurante r WHERE r.direccion = :direccion", Long.class)
                    .setParameter("direccion", restaurante.getDireccion())
                    .getSingleResult() > 0;

            if (direccionEnUso) {
                throw new DAOException("La dirección ya está en uso por otro restaurante.");
            }

            transaction.begin();
            entityManager.persist(restaurante);
            entityManager.flush();
            transaction.commit();
        } catch (PersistenceException ex) {
            //System.out.println("### error: %s".formatted(ex.getClass().getSimpleName()));
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new DAOException("Error al intentar guardar un nuevo restaurante, porfavor intente mas tarde.");
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }

    }

    @Override
    public void actualizarRestaurante(Restaurante restaurante) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            boolean noExiste = entityManager.createQuery("SELECT COUNT(r) FROM Restaurante r WHERE r.id = :idRestaurante", Long.class)
                    .setParameter("idRestaurante", restaurante.getId())
                    .getSingleResult() == 0;

            if (noExiste) {
                throw new DAOException("El restaurante no existe.");
            }

            boolean telefonoEnUso = entityManager.createQuery(
                    "SELECT COUNT(r) FROM Restaurante r WHERE r.telefono = :telefono AND r.id != :idRestaurante", Long.class)
                    .setParameter("telefono", restaurante.getTelefono())
                    .setParameter("idRestaurante", restaurante.getId())
                    .getSingleResult() > 0;

            if (telefonoEnUso) {
                throw new DAOException("El número de teléfono ya está en uso por otro restaurante.");
            }

            boolean direccionEnUso = entityManager.createQuery(
                    "SELECT COUNT(r) FROM Restaurante r WHERE r.direccion = :direccion AND r.id != :idRestaurante", Long.class)
                    .setParameter("direccion", restaurante.getDireccion())
                    .setParameter("idRestaurante", restaurante.getId())
                    .getSingleResult() > 0;

            if (direccionEnUso) {
                throw new DAOException("La dirección ya está en uso por otro restaurante.");
            }

            transaction.begin();
            entityManager.merge(restaurante);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new DAOException("No se pudo actualizar la información debido a un error, porfavor intente mas tarde.");
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void eliminarRestaurante(Long idRestaurante) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Restaurante encontrado = entityManager.createQuery("SELECT r FROM Restaurante r WHERE r.id = :idRestaurante", Restaurante.class)
                    .setParameter("idRestaurante", idRestaurante)
                    .getSingleResult();

            if (encontrado == null) {
                throw new DAOException("El restaurante que se intenta eliminar no existe.");
            }

            transaction.begin();
            entityManager.remove(encontrado);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new DAOException("No se pudo eliminar el restaurante debido a un error, porfavor intente mas tarde.");
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
