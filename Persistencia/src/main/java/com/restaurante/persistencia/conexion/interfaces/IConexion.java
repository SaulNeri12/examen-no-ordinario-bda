/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.restaurante.persistencia.conexion.interfaces;

import javax.persistence.EntityManager;


/**
 * Interfaz para crear una conexión a la base de datos.
 * Define el método para obtener un EntityManager.
 * 
 * @author neri
 */
public interface IConexion {
   
    /**
     * Crea y devuelve una conexión a la base de datos.
     * 
     * @return EntityManager para realizar operaciones en la base de datos.
     */
    public EntityManager crearConexion();
}