/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.restaurante.presentacion;

import com.restaurante.negocio.bo.implementaciones.FabricaRestaurantesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaTiposMesaBO;
import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.presentacion.gui.InformacionRestaurante;
import com.restaurante.presentacion.gui.MenuPrincipalFrm;
import java.time.LocalTime;
import java.util.List;
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
        IRestaurantesBO restaurantesBO = FabricaRestaurantesBO.obtenerRestaurantesBO();
        ITiposMesaBO tiposMesaBO = FabricaTiposMesaBO.obtenerTiposMesaBO();

        List<TipoMesaDTO> tiposMesa;
        try {
            tiposMesa = tiposMesaBO.obtenerTiposMesaTodos();

            if (tiposMesa.isEmpty()) {
                throw new BOException("No hay tipos de mesa");
            }
        } catch (BOException ex) {
            TipoMesaDTO t = new TipoMesaDTO();
            t.setNombre("Pequeña");
            t.setPrecio(300.f);
            t.setMaximoPersonas(2);
            t.setMinimoPersonas(1);

            try {
                tiposMesaBO.agregarTipoMesa(t);
                System.out.println("%s agregada!".formatted(t.getNombre()));
            } catch (BOException ex1) {
                System.out.println(ex.getMessage());
            }

            t = new TipoMesaDTO();
            t.setNombre("Mediana");
            t.setPrecio(500.f);
            t.setMaximoPersonas(4);
            t.setMinimoPersonas(3);

            try {
                tiposMesaBO.agregarTipoMesa(t);
                System.out.println("%s agregada!".formatted(t.getNombre()));
            } catch (BOException ex2) {
                System.out.println(ex.getMessage());
            }

            t = new TipoMesaDTO();
            t.setNombre("Grande");
            t.setPrecio(700.f);
            t.setMaximoPersonas(8);
            t.setMinimoPersonas(5);

            try {
                tiposMesaBO.agregarTipoMesa(t);
                System.out.println("%s agregada!".formatted(t.getNombre()));
            } catch (BOException ex3) {
                System.out.println(ex.getMessage());
            }
        }

        RestauranteDTO restaurante;

        try {
            // Intentar obtener el restaurante por algún criterio, por ejemplo, su teléfono
            List<RestauranteDTO> restaurantes = restaurantesBO.obtenerRestaurantesTodos();

            if (!restaurantes.isEmpty()) {
                restaurante = restaurantes.getFirst();
                // Restaurante encontrado, configurar la información y abrir el menú principal
                InformacionRestaurante.getInstance().setRestaurante(restaurante);
                System.out.println("[*] Restaurante encontrado: " + restaurante.getNombre());

                MenuPrincipalFrm menuPrincipal = new MenuPrincipalFrm();
                menuPrincipal.setVisible(true);
            } else {
                // Si no se encuentra el restaurante, se crea uno nuevo
                throw new BOException("No se encontró el restaurante.");
            }
        } catch (BOException ex) {
            System.out.println("[!] El restaurante no existe. Creando uno nuevo...");

            // Crear y configurar un restaurante nuevo
            restaurante = new RestauranteDTO();
            restaurante.setNombre("Factory's Restaurant");
            restaurante.setDireccion("Calle Z #9002, Colonia Valle Verde");
            restaurante.setHoraApertura(LocalTime.of(9, 0));
            restaurante.setHoraCierre(LocalTime.of(20, 30));
            restaurante.setTelefono("6420104377");

            try {
                // Insertar el nuevo restaurante en el sistema
                restaurantesBO.agregarRestaurante(restaurante);

                InformacionRestaurante.getInstance().setRestaurante(restaurante);
                System.out.println("[*] Nuevo restaurante creado: " + restaurante.getNombre());

                MenuPrincipalFrm menuPrincipal = new MenuPrincipalFrm();
                menuPrincipal.setVisible(true);
            } catch (BOException ex1) {
                // Manejo de errores si no se puede agregar el restaurante
                JOptionPane.showMessageDialog(
                        null,
                        "No se pudo crear el restaurante. Verifique la configuración.",
                        "Error - Admin. Restaurantes",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
