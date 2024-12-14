/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos.convertidores;

import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.persistencia.entidades.TipoMesa;

/**
 * Convertidor para transformar objetos TipoMesa DTO a Entidad o viceversa.
 * @author Saul Neri
 */
public class TipoMesaConvertidor {

    /**
     * Convierte una entidad TipoMesa a su correspondiente DTO TipoMesaDTO.
     * 
     * @param entity la entidad TipoMesa a convertir
     * @return el TipoMesaDTO correspondiente
     */
    public static TipoMesaDTO convertirADTO(TipoMesa entity) {
        TipoMesaDTO dto = new TipoMesaDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setMaximoPersonas(entity.getMaximoPersonas());
        dto.setMinimoPersonas(entity.getMinimoPersonas());
        dto.setPrecio(entity.getPrecio());
        
        return dto;
    }

    /**
     * Convierte un DTO TipoMesaDTO a su correspondiente entidad TipoMesa.
     * 
     * @param dto el TipoMesaDTO a convertir
     * @return la entidad TipoMesa correspondiente
     */
    public static TipoMesa convertirAEntidad(TipoMesaDTO dto) {
        TipoMesa entity = new TipoMesa();

        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setMaximoPersonas(dto.getMaximoPersonas());
        entity.setMinimoPersonas(dto.getMinimoPersonas());
        entity.setPrecio(dto.getPrecio());

        return entity;
    }
}

