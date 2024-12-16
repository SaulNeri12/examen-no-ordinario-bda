/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio;

import com.restaurante.negocio.bo.implementaciones.FabricaTiposMesaBO;
import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.excepciones.BOException;

/**
 * 
 * @author Saul Neri
 */
public class TiposMesa {
    public static void main(String[] args) {
        ITiposMesaBO tipos = FabricaTiposMesaBO.obtenerTiposMesaBO();
        
        TipoMesaDTO t = new TipoMesaDTO();
        t.setNombre("Pequeña");
        t.setPrecio(300.f);
        t.setMaximoPersonas(2);
        t.setMinimoPersonas(1);
        
        try {
            tipos.agregarTipoMesa(t);
            System.out.println("%s agregada!".formatted(t.getNombre()));
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
        
        t = new TipoMesaDTO();
        t.setNombre("Mediana");
        t.setPrecio(500.f);
        t.setMaximoPersonas(4);
        t.setMinimoPersonas(3);
        
        try {
            tipos.agregarTipoMesa(t);
            System.out.println("%s agregada!".formatted(t.getNombre()));
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
        
        t = new TipoMesaDTO();
        t.setNombre("Grande");
        t.setPrecio(700.f);
        t.setMaximoPersonas(8);
        t.setMinimoPersonas(5);
        
        try {
            tipos.agregarTipoMesa(t);
            System.out.println("%s agregada!".formatted(t.getNombre()));
        } catch (BOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
