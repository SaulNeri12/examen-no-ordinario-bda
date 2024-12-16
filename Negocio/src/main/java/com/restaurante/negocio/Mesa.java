/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio;

import com.restaurante.negocio.bo.implementaciones.FabricaMesasBO;
import com.restaurante.negocio.bo.implementaciones.FabricaTiposMesaBO;
import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.negocio.excepciones.BOException;

/**
 *
 * @author Saul Neri
 */
public class Mesa {
    public static void main(String[] args) {
        IMesasBO mesas = FabricaMesasBO.obtenerMesasBO();
        ITiposMesaBO tipos = FabricaTiposMesaBO.obtenerTiposMesaBO();
        
        TipoMesaDTO tipo = null;
        
        try {
            tipo = tipos.obtenerTiposMesaTodos()
                    .stream()
                    .filter(t -> t.getNombre().equalsIgnoreCase("pequeña"))
                    .findFirst()
                    .orElse(null);
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            mesas.insertarMesas(1l, tipo, UbicacionMesaDTO.VENTANA, -92);
            System.out.println("se guardaron las mesas...");
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
