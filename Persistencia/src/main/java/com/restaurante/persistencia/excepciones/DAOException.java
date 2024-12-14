/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.persistencia.excepciones;

/**
 * Excepcion usada para manejar errores de los DAOs de la persistencia.
 * @author Saul Neri
 */
public class DAOException extends Exception {
    public DAOException(String msg) {
        super(msg);
    }
}
