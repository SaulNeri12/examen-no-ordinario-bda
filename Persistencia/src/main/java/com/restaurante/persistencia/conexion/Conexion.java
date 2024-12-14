/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.persistencia.conexion;

import com.restaurante.persistencia.conexion.interfaces.IConexion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Implementacion de la clase de obtencion de manejador para interactuar con la
 * base de datos.
 * @author Saul Neri
 */
public class Conexion implements IConexion {
    private static Conexion instancia;
    private EntityManagerFactory emf;
            
    private Conexion() {
        this.emf = Persistence.createEntityManagerFactory("restaurantes_PU");
    }
    
    /**
     * Obtiene la instancia unica para acceder a las instancias de entity manager.
     * @return 
     */
    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        
        return instancia;
    }

    @Override
    public EntityManager crearConexion() {
        return emf.createEntityManager();
    }
}
