/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.restaurante.negocio.bo.implementaciones;

import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.negocio.dtos.convertidores.MesaConvertidor;
import com.restaurante.negocio.dtos.convertidores.TipoMesaConvertidor;
import com.restaurante.negocio.excepciones.BOException;
import com.restaurante.persistencia.dao.implementaciones.FabricaMesasDAO;
import com.restaurante.persistencia.dao.interfaces.IMesasDAO;
import com.restaurante.persistencia.entidades.UbicacionMesa;
import com.restaurante.persistencia.excepciones.DAOException;

import java.util.List;

/**
 * Implementa los metodos definidos en la interfaz IMesasBO.
 * @author Saul Neri
 */
class MesasBO implements IMesasBO {

    /**
     * Instancia unica para el sigleton.
     */
    private static MesasBO instancia;
    
    /**
     * DAO necesario para interacturar con la tabla de mesas.
     */
    private IMesasDAO mesasDAO;

    /**
     * Constructor privado para usar el singleton
     */
    private MesasBO() {
        this.mesasDAO = FabricaMesasDAO.obtenerMesasDAO();
    }

    /**
     * Obtiene la instancia única de MesasBO.
     *
     * @return Instancia de MesasBO.
     */
    public static MesasBO getInstance() {
        if (instancia == null) {
            instancia = new MesasBO();
        }
        return instancia;
    }
    
    @Override
    public List<MesaDTO> obtenerMesasTodas(Long idRestaurante) throws BOException {
        try {
            List<MesaDTO> mesasDTO = this.mesasDAO.obtenerMesasTodas(idRestaurante)
                    .stream()
                    .map(MesaConvertidor::convertirADTO)
                    .toList();
            
            return mesasDTO;
            
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<MesaDTO> obtenerMesasDisponibles(Long idRestaurante) throws BOException {
        try {
            List<MesaDTO> mesasDTO = this.mesasDAO.obtenerMesasDisponibles(idRestaurante)
                    .stream()
                    .map(MesaConvertidor::convertirADTO)
                    .toList();
            
            return mesasDTO;
            
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public List<MesaDTO> obtenerMesasPorTipo(Long idRestaurante, TipoMesaDTO tipo) throws BOException {
        
        if (tipo == null) {
            throw new BOException("No se especifico el tipo de mesa a obtener");
        }
        
        try {
            List<MesaDTO> mesasDTO = this.mesasDAO.obtenerMesasPorTipo(
                        idRestaurante, 
                        TipoMesaConvertidor.convertirAEntidad(tipo)
                    )
                    .stream()
                    .map(MesaConvertidor::convertirADTO)
                    .toList();
            
            return mesasDTO;
            
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void insertarMesas(Long idRestautante, TipoMesaDTO tipo, UbicacionMesaDTO ubicacion, int cantidad) throws BOException {
        
        if (tipo == null) {
            throw new BOException("No se especifico el tipo de mesa de mesa a insertar");
        }
        
        if (ubicacion == null) {
            throw new BOException("No se especifico la ubicacion de las mesas a insertar");
        }
        
        if (cantidad <= 0) {
            throw new BOException("La cantidad de mesas no puede ser igual o menor a cero.");
        }
        
        try {
            this.mesasDAO.insertarMesas(
                    idRestautante, 
                    TipoMesaConvertidor.convertirAEntidad(tipo), 
                    UbicacionMesa.valueOf(ubicacion.toString()), 
                    cantidad
            );
            
            
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public void eliminarMesa(Long idRestaurante, String codigo) throws BOException {
        
        if (idRestaurante == null) {
            throw new BOException("No se especifico el restaurante.");
        }
        
        if (codigo == null) {
            throw new BOException("No se especifico el codigo de la mesa.");
        }
        
        try {
            this.mesasDAO.eliminarMesa(idRestaurante, codigo);
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }

    @Override
    public MesaDTO obtenerMesaPorCodigo(Long idRestaurante, String codigo) throws BOException {
        try {
            MesaDTO mesa = MesaConvertidor.convertirADTO(this.mesasDAO.obtenerMesaPorCodigo(idRestaurante, codigo));
            if (mesa == null) {
                throw new DAOException("No se encontró la mesa a buscar por código.");
            }
            return mesa;
        } catch (DAOException ex) {
            throw new BOException(ex.getMessage());
        }
    }
}
