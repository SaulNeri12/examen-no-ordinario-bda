/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;

/**
 * Fábrica que proporciona una instancia de IRestaurantesBO.
 * @author Saul Neri
 */
public class FabricaRestaurantesBO {

    /**
     * Método estático que devuelve la instancia única de IRestaurantesBO.
     * Si aún no se ha creado, crea una nueva instancia de RestaurantesBO.
     * @return Una instancia de RestaurantesBO que implementa IRestaurantesBO.
     */
    public static IRestaurantesBO obtenerRestaurantesBO() {
        return RestaurantesBO.getInstance();
    }
}