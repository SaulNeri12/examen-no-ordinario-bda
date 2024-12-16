/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.restaurante.persistencia;

import com.restaurante.persistencia.dao.implementaciones.FabricaRestaurantesDAO;
import com.restaurante.persistencia.dao.interfaces.IRestaurantesDAO;
import com.restaurante.persistencia.entidades.Restaurante;
import com.restaurante.persistencia.excepciones.DAOException;
import java.time.LocalTime;

/**
 *
 * @author neri
 */
public class Persistencia {

    public static void main(String[] args) {

        IRestaurantesDAO restaurantes = FabricaRestaurantesDAO.obtenerRestaurantesDAO();

        Restaurante r = new Restaurante();

        /*
        r.setNombre("Restaurante Prueba");
        r.setDireccion("Calle Z #9002, Colonia Valle Verde");
        r.setHoraApertura(LocalTime.of(9, 0));
        r.setHoraCierre(LocalTime.of(20, 30));
        r.setTelefono("769-111-222");

        try {
            restaurantes.agregarRestaurante(r);
            System.out.println("Se agrego el restaurante.");
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }*/

        try {
            r = restaurantes.obtenerRestaurantePorID(8l);
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            /*
            r.setNombre("Restaurante Prueba Modificado");
            r.setDireccion("Calle Shesh #1300, Colonia Municipio Checheno.");
            r.setHoraApertura(LocalTime.of(9, 0));
            r.setHoraCierre(LocalTime.of(20, 30));
            r.setTelefono("769-031-000");
            
            try {
            restaurantes.actualizarRestaurante(r);
            System.out.println("Se actualizo la informacion del restaurante...");
            } catch (DAOException ex) {
            System.out.println(ex.getMessage());
            }*/
            
            restaurantes.eliminarRestaurante(8l);
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
