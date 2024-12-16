/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos;

import java.io.Serializable;


/**
 * Este objeto se utiliza para transferir la informacion del cliente
 * de manera simplificada entre capas de la aplicacion.
 * 
 * @author Saul Neri
 */
public class ClienteDTO implements Serializable {

    /**
     * Identificador único del cliente en el sistema.
     */
    private Long id;
    
    /**
     * Nombre completo del cliente.
     */
    private String nombreCompleto;
    
    /**
     * Número de teléfono del cliente.
     */
    private String telefono;

    /**
     * Constructor vacío, que inicializa un nuevo objeto ClienteDTO.
     */
    public ClienteDTO() {
    }

    /**
     * Constructor que inicializa un nuevo objeto ClienteDTO con los parámetros especificados.
     * 
     * @param id el identificador único del cliente
     * @param nombreCompleto el nombre completo del cliente
     * @param telefono el número de teléfono del cliente
     */
    public ClienteDTO(Long id, String nombreCompleto, String telefono) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador único del cliente.
     * 
     * @return el identificador del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del cliente.
     * 
     * @param id el identificador del cliente a establecer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del cliente.
     * 
     * @return el nombre completo del cliente
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del cliente.
     * 
     * @param nombreCompleto el nombre completo del cliente a establecer
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     * 
     * @return el número de teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     * 
     * @param telefono el número de teléfono del cliente a establecer
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        ClienteDTO other = (ClienteDTO) obj;
        return (id != null && id.equals(other.id));
    }
    
    public String toString() {
        return String.format("%s, %s", this.nombreCompleto, this.telefono);
    }       
}