/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos.convertidores;

import com.restaurante.negocio.dtos.EstadoReservacionDTO;
import com.restaurante.negocio.dtos.ReservacionDTO;
import com.restaurante.persistencia.entidades.EstadoReservacion;
import com.restaurante.persistencia.entidades.Reservacion;

/**
 *
 * @author Saul Neri
 */
public class ReservacionConvertidor {

    /**
     * Convierte una entidad Reservacion a su correspondiente DTO ReservacionDTO.
     * 
     * @param entity la entidad Reservacion a convertir
     * @return el ReservacionDTO correspondiente
     */
    public static ReservacionDTO convertirADTO(Reservacion entity) {
        ReservacionDTO dto = new ReservacionDTO();

        dto.setId(entity.getId());
        dto.setFechaHora(entity.getFechaHora());
        dto.setNumeroPersonas(entity.getNumeroPersonas());
        dto.setEstado(EstadoReservacionDTO.valueOf(entity.getEstado().toString()));
        dto.setMesa(MesaConvertidor.convertirADTO(entity.getMesa()));
        dto.setCliente(ClienteConvertidor.convertirADTO(entity.getCliente()));
        dto.setMontoTotal(entity.getMontoTotal());
        dto.setFechaHoraRegistro(entity.getFechaHoraRegistro());

        return dto;
    }

    /**
     * Convierte un DTO ReservacionDTO a su correspondiente entidad Reservacion.
     * 
     * @param dto el ReservacionDTO a convertir
     * @return la entidad Reservacion correspondiente
     */
    public static Reservacion convertirAEntidad(ReservacionDTO dto) {
        Reservacion entity = new Reservacion();

        entity.setId(dto.getId());
        entity.setFechaHora(dto.getFechaHora());
        entity.setNumeroPersonas(dto.getNumeroPersonas());
        entity.setEstado(EstadoReservacion.valueOf(dto.getEstado().toString()));
        entity.setMesa(MesaConvertidor.convertirAEntidad(dto.getMesa()));
        entity.setCliente(ClienteConvertidor.convertirAEntidad(dto.getCliente()));
        entity.setMontoTotal(dto.getMontoTotal());
        entity.setFechaHoraRegistro(dto.getFechaHoraRegistro());

        return entity;
    }
}
