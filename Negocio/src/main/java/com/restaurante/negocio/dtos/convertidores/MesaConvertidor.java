/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos.convertidores;

import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.persistencia.entidades.Mesa;
import com.restaurante.persistencia.entidades.UbicacionMesa;

/**
 * Convertidor para transformar objetos Mesa DTO a Entidad o viceversa.
 * @author Saul Neri
 */
public class MesaConvertidor {

    /**
     * Convierte una entidad Mesa a su correspondiente DTO MesaDTO.
     * 
     * @param entity la entidad Mesa a convertir
     * @return el MesaDTO correspondiente
     */
    public static MesaDTO convertirADTO(Mesa entity) {
        MesaDTO dto = new MesaDTO();

        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        
        // Convertir el tipo de mesa a TipoMesaDTO si es necesario
        if (entity.getTipoMesa() != null) {
            dto.setTipoMesa(TipoMesaConvertidor.convertirADTO(entity.getTipoMesa()));
        }

        // Convertir la ubicación a UbicacionMesaDTO si es necesario
        if (entity.getUbicacion() != null) {
            dto.setUbicacion(UbicacionMesaDTO.valueOf(entity.getUbicacion().toString()));
        }

        // Convertir el restaurante a RestauranteDTO si es necesario
        if (entity.getRestaurante() != null) {
            dto.setRestaurante(RestauranteConvertidor.convertirADTO(entity.getRestaurante()));
        }

        return dto;
    }

    /**
     * Convierte un DTO MesaDTO a su correspondiente entidad Mesa.
     * 
     * @param dto el MesaDTO a convertir
     * @return la entidad Mesa correspondiente
     */
    public static Mesa convertirAEntidad(MesaDTO dto) {
        Mesa entity = new Mesa();

        entity.setId(dto.getId());
        entity.setCodigo(dto.getCodigo());
        
        // Convertir TipoMesaDTO a entidad TipoMesa si es necesario
        if (dto.getTipoMesa() != null) {
            entity.setTipoMesa(TipoMesaConvertidor.convertirAEntidad(dto.getTipoMesa()));
        }

        // Convertir UbicacionMesaDTO a entidad UbicacionMesa si es necesario
        if (dto.getUbicacion() != null) {
            entity.setUbicacion(UbicacionMesa.valueOf(dto.getUbicacion().toString()));
        }

        // Convertir RestauranteDTO a entidad Restaurante si es necesario
        if (dto.getRestaurante() != null) {
            entity.setRestaurante(RestauranteConvertidor.convertirAEntidad(dto.getRestaurante()));
        }

        return entity;
    }
}