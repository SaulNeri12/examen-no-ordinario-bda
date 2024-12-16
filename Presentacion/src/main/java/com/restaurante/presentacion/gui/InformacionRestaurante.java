/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.presentacion.gui;

import com.restaurante.negocio.dtos.RestauranteDTO;

/**
 * Almacena la informacion del contexto del restaurante actual, el cual el programa
 * esta modificando su informacion interna. 
 * 
 * Usa  el patron Singleton para acceder a la informacion desde diferentes partes del
 * programa sin la necesidad de usar inyeccion de dependencias.
 * 
 * @author Saul Neri
 */
public class InformacionRestaurante {
    
    /**
     * Instancia unica necesaria para la implementacion del Singleton.
     */
    private static InformacionRestaurante instance;
    
    /**
     * Informacion del restaurante que se esta modificando.
     */
    private RestauranteDTO restaurante;
    
    /**
     * Constructor privado necesario para la implementacion del Singleton.
     */
    private InformacionRestaurante() {
        
    } 
    
    /**
     * Devuelve la informacion del restaurante el cual esta siendo manejado
     * desde la aplicacion.
     * @return InformacionRestaurante.
     */
    public static InformacionRestaurante getInstance() {
        if (instance == null)  {
            instance = new InformacionRestaurante();
        }
        
        return instance;
    }
    
    /**
     * Asigna el objeto restaurante que sera accedido desde diferentes partes del programa.
     * @param restaurante Objeto restauranteDTO.
     */
    public void setRestaurante(RestauranteDTO restaurante) {
        this.restaurante = restaurante;
    }
    
    /**
     * Obtiene el objeto restaurante que sera accedido desde diferentes partes del programa.
     * @return RestauranteDTO.
     */
    public RestauranteDTO getRestaurante() {
        return this.restaurante;
    }
}
