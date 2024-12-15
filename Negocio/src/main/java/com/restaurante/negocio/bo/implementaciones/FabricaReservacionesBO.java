/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IReservacionesBO;

/**
 * Fábrica que proporciona una instancia de IReservacionesBO.
 * @author Saul Neri
 */
public class FabricaReservacionesBO {

    /**
     * Método estático que devuelve la instancia única de IReservacionesBO.
     * Si aún no se ha creado, crea una nueva instancia de ReservacionesBO.
     * @return Una instancia de ReservacionesBO que implementa IReservacionesBO.
     */
    public static IReservacionesBO obtenerReservacionesDAO() {
        return ReservacionesBO.getInstance();
    }
}