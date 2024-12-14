/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos.convertidores;

import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.persistencia.entidades.Restaurante;

/**
 * Convertidor para transformar objetos Restaurante DTO a Entidad o viceversa.
 * @author Saul Neri
 */
public class RestauranteConvertidor {
    
    /**
     * Convierte un restaurante entidad a DTO.
     * @param entity Objeto Restaurante entidad.
     * @return Objeto RestauranteDTO.
     */
    public static RestauranteDTO convertirADTO(Restaurante entity) {
        RestauranteDTO dto = new RestauranteDTO();
        
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setHoraApertura(entity.getHoraApertura());
        dto.setHoraCierre(entity.getHoraCierre());
        
        return dto;
    }
    
    
    /**
     * Convierte un restaurante DTO a Entidad.
     * @param dto Objeto RestauranteDTO.
     * @return Objeto Restaurante entidad.
     */
    public static Restaurante convertirAEntidad(RestauranteDTO dto) {
        Restaurante entity = new Restaurante();
        
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setHoraApertura(dto.getHoraApertura());
        entity.setHoraCierre(dto.getHoraCierre());
        
        return entity;
    }
}
