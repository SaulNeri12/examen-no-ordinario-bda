/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.restaurante.presentacion;

import com.restaurante.negocio.bo.implementaciones.FabricaRestaurantesBO;
import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.presentacion.gui.InformacionRestaurante;
import com.restaurante.presentacion.gui.MenuPrincipalFrm;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author neri
 */
public class Presentacion {

    public static void main(String[] args) {
        
        /* NOTE: PRUEBA DE USO DE LA APLICACION
            
        SE INICIALIZARA LA APLICACION CON EL RESTAURANTE PREVIAMENTE ESPECIFICADO Y 
        CONFIGURADO.
        */
        
        IRestaurantesBO restaurantes = FabricaRestaurantesBO.obtenerRestaurantesBO();
        
        RestauranteDTO restaurante = new RestauranteDTO();
        
        restaurante.setNombre("Factory's Restaurant");
        restaurante.setDireccion("Calle Z #9002, Colonia Valle Verde");
        restaurante.setHoraApertura(LocalTime.of(9, 0));
        restaurante.setHoraCierre(LocalTime.of(20, 30));
        restaurante.setTelefono("100-111-222");
        
        MenuPrincipalFrm menuPrincipal = null;
        
        try {
            restaurantes.agregarRestaurante(restaurante);
            InformacionRestaurante.getInstance().setRestaurante(restaurante);
            
            System.out.println("[*] Se creo un nuevo restaurante...");
            menuPrincipal = new MenuPrincipalFrm();
            menuPrincipal.setVisible(true);
           
        } catch (BOException ex) {
            System.out.println("[!] El restaurante ya existe");
            
            try {
                restaurante = restaurantes.obtenerRestaurantePorNumeroTelefono(restaurante.getTelefono());
                
                if (restaurante == null) {
                    throw new BOException("");
                }
                
                InformacionRestaurante.getInstance().setRestaurante(restaurante);
                
                menuPrincipal = new MenuPrincipalFrm();
                menuPrincipal.setVisible(true);
                
            } catch (BOException ex1) {
                JOptionPane.showMessageDialog(
                        null, 
                        "No se pudo iniciar la aplicacion debido a que no se encontró o el restaurante", 
                        "Error - Admin. Restaurantes", 
                        JOptionPane.ERROR_MESSAGE
                );
            }
            
        }
    }
}
