/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.excepciones;

/**
 * Excepcion utilizada para el manejo de errores en los BOs.
 * @author Saul Neri
 */
public class BOException extends Exception {
    public BOException(String msg) {
        super(msg);
    }
}
