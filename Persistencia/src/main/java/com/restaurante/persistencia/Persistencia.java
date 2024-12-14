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
        
        r.setNombre("Restaurante X");
        r.setDireccion("Calle X #6009, Colonia Revolucion.");
        r.setHoraApertura(LocalTime.of(8, 0));
        r.setHoraCierre(LocalTime.of(21, 30));
        r.setTelefono("769-031-245");
        
        try {
            restaurantes.agregarRestaurante(r);
            System.out.println("Se agrego el restaurante.");
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
