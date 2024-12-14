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
    private EstadoReservacionDTO estado; // Estado como String, se podría convertir a Enum si se requiere

    /**
     * Identificador de la mesa asociada a la reservación.
     */
    private MesaDTO mesaId;

    /**
     * Identificador del cliente asociado a la reservación.
     */
    private ClienteDTO cliente;

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
     * Crea una instancia de reservacion DTO usada para consultas por ID.
     * @param id ID de la reservacion.
     */
    public ReservacionDTO(Long id) {
        this.id = id;
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
    public EstadoReservacionDTO getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la reservación.
     * 
     * @param estado el estado de la reservación a establecer
     */
    public void setEstado(EstadoReservacionDTO estado) {
        this.estado = estado;
    }

     /**
     * Obtiene la mesa asociada a la reservación.
     * 
     * @return el objeto MesaDTO que representa la mesa asociada a la reservación
     */
    public MesaDTO getMesa() {
        return mesaId;
    }

    /**
     * Establece la mesa asociada a la reservación.
     * 
     * @param mesa el objeto MesaDTO que representa la mesa que se asignará a la reservación
     */
    public void setMesa(MesaDTO mesa) {
        this.mesaId = mesa;
    }

    /**
     * Obtiene el cliente asociado a la reservación.
     * 
     * @return el objeto ClienteDTO que representa el cliente asociado a la reservación
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado a la reservación.
     * 
     * @param cliente el objeto ClienteDTO que representa el cliente que se asignará a la reservación
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
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
        return "ReservacionDTO{id=" + id + ", fechaHora=" + fechaHora + ", numeroPersonas=" + numeroPersonas + ", estado='" + getEstado() + "'}";
    }
}