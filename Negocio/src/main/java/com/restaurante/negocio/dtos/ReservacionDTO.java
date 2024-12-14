/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;



/**
 * Representa una reservación en un restaurante.
 * 
 * @author Saul Neri
 */
public class ReservacionDTO implements Serializable {

    /**
     * Identificador único de la reservación.
     */
    private Long id;

    /**
     * Fecha y hora de la reservación.
     */
    private LocalDateTime fechaHora;

    /**
     * Número de personas para la reservación.
     */
    private Integer numeroPersonas;

    /**
     * Estado de la reservación.
     */
    private String estado; // Estado como String, se podría convertir a Enum si se requiere

    /**
     * Identificador de la mesa asociada a la reservación.
     */
    private Long mesaId;

    /**
     * Identificador del cliente asociado a la reservación.
     */
    private Long clienteId;

    /**
     * Monto total de la reservación.
     */
    private Float montoTotal;

    /**
     * Fecha y hora exacta de cuando se registró la reservación en el sistema.
     */
    private LocalDateTime fechaHoraRegistro;

    /**
     * Constructor vacío, inicializa un nuevo objeto ReservacionDTO.
     */
    public ReservacionDTO() {
    }

    /**
     * Constructor que inicializa un nuevo objeto ReservacionDTO con los parámetros especificados.
     * 
     * @param id el identificador único de la reservación
     * @param fechaHora la fecha y hora de la reservación
     * @param numeroPersonas el número de personas para la reservación
     * @param estado el estado de la reservación
     * @param mesaId el identificador de la mesa asociada
     * @param clienteId el identificador del cliente que realizó la reservación
     * @param montoTotal el monto total a pagar por la reservación
     * @param fechaHoraRegistro la fecha y hora de registro de la reservación
     */
    public ReservacionDTO(Long id, LocalDateTime fechaHora, Integer numeroPersonas, String estado, Long mesaId, Long clienteId, Float montoTotal, LocalDateTime fechaHoraRegistro) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.numeroPersonas = numeroPersonas;
        this.estado = estado;
        this.mesaId = mesaId;
        this.clienteId = clienteId;
        this.montoTotal = montoTotal;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    /**
     * Obtiene el identificador único de la reservación.
     * 
     * @return el identificador único de la reservación
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la reservación.
     * 
     * @param id el identificador único de la reservación a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha y hora de la reservación.
     * 
     * @return la fecha y hora de la reservación
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la reservación.
     * 
     * @param fechaHora la fecha y hora de la reservación a establecer
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el número de personas para la reservación.
     * 
     * @return el número de personas de la reservación
     */
    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    /**
     * Establece el número de personas para la reservación.
     * 
     * @param numeroPersonas el número de personas a establecer
     */
    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    /**
     * Obtiene el estado de la reservación.
     * 
     * @return el estado de la reservación
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la reservación.
     * 
     * @param estado el estado de la reservación a establecer
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la mesa asociada a la reservación.
     * 
     * @return el identificador de la mesa asociada a la reservación
     */
    public Long getMesaId() {
        return mesaId;
    }

    /**
     * Establece el identificador de la mesa asociada a la reservación.
     * 
     * @param mesaId el identificador de la mesa
     */
    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }

    /**
     * Obtiene el identificador del cliente asociado a la reservación.
     * 
     * @return el identificador del cliente
     */
    public Long getClienteId() {
        return clienteId;
    }

    /**
     * Establece el identificador del cliente asociado a la reservación.
     * 
     * @param clienteId el identificador del cliente
     */
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Obtiene el monto total de la reservación.
     * 
     * @return el monto total a pagar por la reservación
     */
    public Float getMontoTotal() {
        return montoTotal;
    }

    /**
     * Establece el monto total de la reservación.
     * 
     * @param montoTotal el monto total de la reservación
     */
    public void setMontoTotal(Float montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * Obtiene la fecha y hora exacta de cuando se registró la reservación.
     * 
     * @return la fecha y hora de registro de la reservación
     */
    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    /**
     * Establece la fecha y hora exacta de cuando se registró la reservación.
     * 
     * @param fechaHoraRegistro la fecha y hora de registro
     */
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReservacionDTO other = (ReservacionDTO) obj;
        return (id != null && id.equals(other.id));
    }

    @Override
    public String toString() {
        return "ReservacionDTO{id=" + id + ", fechaHora=" + fechaHora + ", numeroPersonas=" + numeroPersonas + ", estado='" + estado + "'}";
    }
}