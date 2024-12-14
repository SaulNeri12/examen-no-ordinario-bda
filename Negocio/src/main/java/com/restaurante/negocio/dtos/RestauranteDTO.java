/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

/**
 * Este objeto se utiliza para transferir la informacion básica de un restaurante
 * de manera simplificada entre capas de la aplicacion.
 * 
 * @author Saul Neri
 */
public class RestauranteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del restaurante.
     */
    private Long id;

    /**
     * Nombre del restaurante.
     */
    private String nombre;

    /**
     * Dirección del restaurante.
     */
    private String direccion;

    /**
     * Teléfono del restaurante.
     */
    private String telefono;

    /**
     * Hora de apertura del restaurante.
     */
    private LocalTime horaApertura;

    /**
     * Hora de cierre del restaurante.
     */
    private LocalTime horaCierre;

    /**
     * Lista de mesas del restaurante. Solo se incluirán los datos esenciales para las transferencias.
     */
    private List<MesaDTO> mesas;  // Podrías incluir un DTO para mesas si fuera necesario

    /**
     * Constructor vacío, inicializa un nuevo objeto RestauranteDTO.
     */
    public RestauranteDTO() {
    }
    
    /**
     * Crea una instancia nueva de restaurante DTO para ser utilizada en una 
     * busqueda por ID.
     * @param id ID del restaurante.
     */
    public RestauranteDTO(Long id) {
       this.id = id;
    }

    /**
     * Obtiene el identificador único del restaurante.
     * 
     * @return el ID del restaurante
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del restaurante.
     * 
     * @param id el ID del restaurante
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del restaurante.
     * 
     * @return el nombre del restaurante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del restaurante.
     * 
     * @param nombre el nombre del restaurante
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del restaurante.
     * 
     * @return la dirección del restaurante
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del restaurante.
     * 
     * @param direccion la dirección del restaurante
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el número de teléfono del restaurante.
     * 
     * @return el número de teléfono del restaurante
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del restaurante.
     * 
     * @param telefono el número de teléfono del restaurante
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la hora de apertura del restaurante.
     * 
     * @return la hora de apertura del restaurante
     */
    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    /**
     * Establece la hora de apertura del restaurante.
     * 
     * @param horaApertura la hora de apertura del restaurante
     */
    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    /**
     * Obtiene la hora de cierre del restaurante.
     * 
     * @return la hora de cierre del restaurante
     */
    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    /**
     * Establece la hora de cierre del restaurante.
     * 
     * @param horaCierre la hora de cierre del restaurante
     */
    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    /**
     * Obtiene las mesas disponibles en el restaurante.
     * 
     * @return una lista de las mesas en el restaurante
     */
    public List<MesaDTO> getMesas() {
        return mesas;
    }

    /**
     * Establece las mesas disponibles en el restaurante.
     * 
     * @param mesas las mesas a asignar al restaurante
     */
    public void setMesas(List<MesaDTO> mesas) {
        this.mesas = mesas;
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
        RestauranteDTO other = (RestauranteDTO) obj;
        return (id != null && id.equals(other.id));
    }

    @Override
    public String toString() {
        return "RestauranteDTO{id=" + id + ", nombre='" + nombre + "', direccion='" + direccion + "', telefono='" + telefono + "'}";
    }
}
