/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.dtos.convertidores;

import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.persistencia.entidades.Cliente;

/**
 * Convertidor para transformar objetos Cliente DTO a Entidad o viceversa.
 * @author Saul Neri
 */
public class ClienteConvertidor {
    
    /**
     * Convierte una entidad Cliente a su correspondiente DTO ClienteDTO.
     * 
     * @param entity la entidad Cliente a convertir
     * @return el ClienteDTO correspondiente
     */
    public static ClienteDTO convertirADTO(Cliente entity) {
        ClienteDTO dto = new ClienteDTO();
        
        dto.setId(entity.getId());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setTelefono(entity.getTelefono());
        
        return dto;
    }
    
    /**
     * Convierte un DTO ClienteDTO a su correspondiente entidad Cliente.
     * 
     * @param dto el ClienteDTO a convertir
     * @return la entidad Cliente correspondiente
     */
    public static Cliente convertirAEntidad(ClienteDTO dto) {
        Cliente entity = new Cliente();
        
        entity.setId(dto.getId());
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setTelefono(dto.getTelefono());
        
        return entity;
    }
}

