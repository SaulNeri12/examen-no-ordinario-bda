
package com.restaurante.negocio.bo.interfaces;

import com.restaurante.negocio.dtos.ReservacionDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Define las operaciones para el BO de reservaciones.
 * @author neri
 */
public interface IReservacionesBO {
       
    /**
     * Obtiene la lista de reservaciones registradas de una mesa a partir de su codigo
     * @param idRestaurante ID del restaurante en donde se quiere consultar las reservas
     * @param codigoMesa Codigo de la mesa a buscar
     * @return Reservaciones de dicha mesa
     * @throws BOException Si ocurre un error en la consulta
     */
    public List<ReservacionDTO> obtenerReservacionesDeMesa(Long idRestaurante, String codigoMesa) throws BOException;
    
    /**
     * Cancela la reservacion con el ID dado.
     * @param idReservacion ID de la reservacion en cuestion
     * @throws BOException Si ocurre un error en la cancelacion
     */
    public void cancelarReservacion(Long idReservacion) throws BOException;
    
    /**
     * Obtiene una lista de todas las reservaciones almacenadas.
     *
     * @param idRestaurante ID del restaurante en cuestion
     * @return Una lista de objetos `Reservacion`.
     * @throws BOException Si ocurre un error durante la obtención de los
     * datos.
     */
    public List<ReservacionDTO> obtenerReservacionesTodos(Long idRestaurante) throws BOException;

    /**
     * Obtiene una lista de reservaciones dentro de un período de tiempo
     * específico.
     *
     * @param idRestaurante ID del restaurante en cuestion
     * @param fechaInicio La fecha y hora de inicio del período.
     * @param fechaFin La fecha y hora de fin del período.
     * @return Una lista de objetos `Reservacion` que se encuentran en el rango
     * especificado.
     * @throws BOException Si ocurre un error durante la obtención de los
     * datos.
     */
    public List<ReservacionDTO> obtenerReservacionesPorPeriodo(Long idRestaurante, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws BOException;

    /**
     * Obtiene una lista de reservaciones asociadas a un cliente específico
     * basado en su número de teléfono.
     *
     * @param idRestaurante ID del restaurante en cuestion
     * @param telefono El número de teléfono del cliente.
     * @return Una lista de objetos `Reservacion` pertenecientes al cliente.
     * @throws BOException Si ocurre un error durante la obtención de los
     * datos.
     */
    public List<ReservacionDTO> obtenerReservacionesCliente(Long idRestaurante, String telefono) throws BOException;

    /**
     * Obtiene una reservación específica basada en su identificador único.
     *
     * @param id El identificador de la reservación.
     * @return Un objeto `Reservacion` correspondiente al identificador
     * proporcionado.
     * @throws BOException Si ocurre un error durante la obtención de los
     * datos.
     */
    public ReservacionDTO obtenerReservacionPorID(Long id) throws BOException;

    /**
     * Agrega una nueva reservación al sistema.
     *
     * @param reservacion El objeto `Reservacion` que se desea agregar.
     * @throws BOException Si ocurre un error durante la inserción de los
     * datos.
     */
    public void agregarReservacion(ReservacionDTO reservacion) throws BOException;

    /**
     * Actualiza los detalles de una reservación existente en el sistema.
     *
     * @param reservacion El objeto `Reservacion` que contiene los datos
     * actualizados.
     * @throws BOException Si ocurre un error durante la actualización de los
     * datos.
     */
    public void actualizarReservacion(ReservacionDTO reservacion) throws BOException;

    /**
     * Elimina una reservación del sistema basada en su identificador único.
     *
     * @param id El identificador de la reservación que se desea eliminar.
     * @throws BOException Si ocurre un error durante la eliminación de los
     * datos.
     */
    public void eliminarReservacion(Long id) throws BOException;
}

