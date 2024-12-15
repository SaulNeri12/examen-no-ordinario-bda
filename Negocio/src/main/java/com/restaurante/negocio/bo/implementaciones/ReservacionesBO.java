
package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IReservacionesBO;
import com.restaurante.negocio.dtos.ReservacionDTO;
import com.restaurante.negocio.dtos.convertidores.ReservacionConvertidor;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaReservacionesDAO;
import com.restaurante.persistencia.dao.interfaces.IReservacionesDAO;
import com.restaurante.persistencia.excepciones.DAOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementa los metodos definidos por la interfaz IReservacionesBO para 
 * interactuar con la tabla Reservaciones en la base de datos.
 * @author Saul Neri
 */
public class ReservacionesBO implements IReservacionesBO {
    
    private static ReservacionesBO instancia;
    
    /**
     * DAO de restaurantes necesaria para el funcionamiento del BO.
     */
    private final IReservacionesDAO reservaciones;

    /**
     * Constructor privado necesario para la implementacion del singleon.
     */
    private ReservacionesBO() {
        this.reservaciones = FabricaReservacionesDAO.obtenerReservacionesDAO();
    }

    /**
     * Obtiene una instancia anonima del BO de reservaciones.
     * @return Intancia unica
     */
    public static ReservacionesBO getInstance() {
        if (instancia == null) {
            instancia = new ReservacionesBO();
        }
        return instancia;
    }

    @Override
    public List<ReservacionDTO> obtenerReservacionesDeMesa(Long idRestaurante, String codigoMesa) throws BOException {
        try {
            List<ReservacionDTO> listaReservaciones = this.reservaciones.obtenerReservacionesDeMesa(idRestaurante, codigoMesa)
                    .stream()
                    .map(ReservacionConvertidor::convertirADTO)
                    .toList();
            
            return listaReservaciones;
                    
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void cancelarReservacion(Long idReservacion) throws BOException {
        try {
            this.reservaciones.cancelarReservacion(idReservacion);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<ReservacionDTO> obtenerReservacionesTodos(Long idRestaurante) throws BOException {
        try {
            List<ReservacionDTO> lista = this.reservaciones.obtenerReservacionesTodos(idRestaurante)
                    .stream()
                    .map(ReservacionConvertidor::convertirADTO)
                    .toList();
            
            return lista;
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<ReservacionDTO> obtenerReservacionesPorPeriodo(Long idRestaurante, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws BOException {
        
        if (fechaInicio == null) {
            throw new BOException("No se especifico la fecha de inicio en la busqueda.");
        }
        
        if (fechaFin == null) {
            throw new BOException("No se especifico la fecha de fin en la busqueda.");
        }
        
        try {
            List<ReservacionDTO> lista = this.reservaciones.obtenerReservacionesPorPeriodo(idRestaurante, fechaInicio, fechaFin)
                    .stream()
                    .map(ReservacionConvertidor::convertirADTO)
                    .toList();
            
            return lista;
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<ReservacionDTO> obtenerReservacionesCliente(Long idRestaurante, String telefono) throws BOException {
        if (telefono == null) {
            throw new BOException("No se especifico el numero de telefono en la busqueda.");
        }
        
        try {
            List<ReservacionDTO> lista = this.reservaciones.obtenerReservacionesCliente(idRestaurante, telefono)
                    .stream()
                    .map(ReservacionConvertidor::convertirADTO)
                    .toList();
            
            return lista;
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public ReservacionDTO obtenerReservacionPorID(Long id) throws BOException {
        try {
            return ReservacionConvertidor.convertirADTO(this.reservaciones.obtenerReservacionPorID(id));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void agregarReservacion(ReservacionDTO reservacion) throws BOException {
        try {
            this.reservaciones.agregarReservacion(ReservacionConvertidor.convertirAEntidad(reservacion));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void actualizarReservacion(ReservacionDTO reservacion) throws BOException {
        try {
            this.reservaciones.actualizarReservacion(ReservacionConvertidor.convertirAEntidad(reservacion));
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void eliminarReservacion(Long id) throws BOException {
        try {
            this.reservaciones.eliminarReservacion(id);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
}
