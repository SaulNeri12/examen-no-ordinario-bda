/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.restaurante.presentacion.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.restaurante.negocio.bo.implementaciones.FabricaClientesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaMesasBO;
import com.restaurante.negocio.bo.implementaciones.FabricaRestaurantesBO;
import com.restaurante.negocio.bo.interfaces.IClientesBO;
import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.awt.Color;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author neri
 */
public class MenuPrincipalFrm extends javax.swing.JFrame {

    private RestauranteDTO restaurante = InformacionRestaurante.getInstance().getRestaurante();
    private IRestaurantesBO restaurantesBO = FabricaRestaurantesBO.obtenerRestaurantesBO();
    private IClientesBO clientesBO = FabricaClientesBO.obtenerClientesDAO();
    private IMesasBO mesasBO = FabricaMesasBO.obtenerMesasBO();

    private Timer timerActualizarInfoRestaurante;

    /**
     * Creates new form MenuPrincipalFrm
     */
    public MenuPrincipalFrm() {
        this.getContentPane().setBackground(Color.WHITE);
        FlatLightLaf.setup();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Menu Principal");
        //Estilo.prepararEstilo();
        this.cargarInfoRestaurante();
        this.cargarMesasDisponibles();
        
        this.horaAperturaTimePicker.addTimeChangeListener(t -> {
            if (horaCierreTimePicker.getTime().isBefore(t.getNewTime())) {
                horaAperturaTimePicker.setTime(t.getOldTime());
                JOptionPane.showMessageDialog(
                        null,
                        "La hora de cierre es anterior a la hora de apertura.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        this.horaCierreTimePicker.addTimeChangeListener(t -> {
            if (horaAperturaTimePicker.getTime().isAfter(t.getNewTime())) {
                horaCierreTimePicker.setTime(t.getOldTime());
                JOptionPane.showMessageDialog(
                        null,
                        "La hora de cierre es anterior a la hora de apertura.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
    }

    private void cargarInfoRestaurante() {

        this.direccionRestauranteTextField.setText(restaurante.getDireccion());
        this.horaAperturaTimePicker.setTime(restaurante.getHoraApertura());
        this.horaCierreTimePicker.setTime(restaurante.getHoraCierre());
        this.telefonoRestauranteTextField.setText(restaurante.getTelefono());
    }

    private void cargarMesasDisponibles() {
        try {
            List<MesaDTO> mesasDisponibles = this.mesasBO.obtenerMesasDisponibles(restaurante.getId());

            List<MesaDTO> mesasPeqDisponibles = mesasDisponibles
                    .stream()
                    .filter(m -> m.getTipoMesa()
                    .getNombre()
                    .equalsIgnoreCase("pequeña")
                    )
                    .toList();

            List<MesaDTO> mesasMedDisponibles = mesasDisponibles
                    .stream()
                    .filter(m -> m.getTipoMesa()
                    .getNombre()
                    .equalsIgnoreCase("mediana")
                    )
                    .toList();

            List<MesaDTO> mesasGraDisponibles = mesasDisponibles
                    .stream()
                    .filter(m -> m.getTipoMesa()
                    .getNombre()
                    .equalsIgnoreCase("grande")
                    )
                    .toList();

            this.mesasPequenasDispLbl.setText(Integer.toString(mesasPeqDisponibles.size()));
            this.mesasMedianasDispLbl.setText(Integer.toString(mesasMedDisponibles.size()));
            this.mesasGrandesDispLbl.setText(Integer.toString(mesasGraDisponibles.size()));

            // seccion de Mesas pequenas
            List<MesaDTO> mesasPeqGeneral = mesasPeqDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.GENERAL))
                    .toList();

            List<MesaDTO> mesasPeqTerraza = mesasPeqDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.TERRAZA))
                    .toList();

            List<MesaDTO> mesasPeqVentana = mesasPeqDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.VENTANA))
                    .toList();

            this.mesasPeqGeneralLbl.setText(Integer.toString(mesasPeqGeneral.size()));
            this.mesasPeqTerrazaLbl.setText(Integer.toString(mesasPeqTerraza.size()));
            this.mesasPeqVentanaLbl.setText(Integer.toString(mesasPeqVentana.size()));

            // seccion de Medas medianas
            List<MesaDTO> mesasMedGeneral = mesasMedDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.GENERAL))
                    .toList();

            List<MesaDTO> mesasMedTerraza = mesasMedDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.TERRAZA))
                    .toList();

            List<MesaDTO> mesasMedVentana = mesasMedDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.VENTANA))
                    .toList();

            this.mesasMedGeneralLbl.setText(Integer.toString(mesasMedGeneral.size()));
            this.mesasMedTerrazaLbl.setText(Integer.toString(mesasMedTerraza.size()));
            this.mesasMedVentanaLbl.setText(Integer.toString(mesasMedVentana.size()));

            // seccion de Medas grandes
            List<MesaDTO> mesasGraGeneral = mesasGraDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.GENERAL))
                    .toList();

            List<MesaDTO> mesasGraTerraza = mesasGraDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.TERRAZA))
                    .toList();

            List<MesaDTO> mesasGraVentana = mesasGraDisponibles
                    .stream()
                    .filter(m -> m.getUbicacion().equals(UbicacionMesaDTO.VENTANA))
                    .toList();

            this.mesasGraGeneralLbl.setText(Integer.toString(mesasGraGeneral.size()));
            this.mesasGraTerrazaLbl.setText(Integer.toString(mesasGraTerraza.size()));
            this.mesasGraVentanaLbl.setText(Integer.toString(mesasGraVentana.size()));

        } catch (BOException ex) {
            System.out.println("### " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo obtener la informacion de las mesas en la base de datos.",
                    ex.getMessage(),
                    "Error - Carga de mesas disponibles",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        direccionRestauranteTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        telefonoRestauranteTextField = new javax.swing.JTextField();
        actualizarInformacionRestauranteBtn = new javax.swing.JButton();
        horaAperturaTimePicker = new com.github.lgooddatepicker.components.TimePicker();
        horaCierreTimePicker = new com.github.lgooddatepicker.components.TimePicker();
        jPanel2 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        mesasPeqGeneralLbl = new javax.swing.JLabel();
        mesasPeqTerrazaLbl = new javax.swing.JLabel();
        mesasPequenasDispLbl = new javax.swing.JLabel();
        mesasPeqVentanaLbl = new javax.swing.JLabel();
        mesasMedianasDispLbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        mesasMedGeneralLbl = new javax.swing.JLabel();
        mesasMedTerrazaLbl = new javax.swing.JLabel();
        mesasMedVentanaLbl = new javax.swing.JLabel();
        mesasGrandesDispLbl = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        mesasGraGeneralLbl = new javax.swing.JLabel();
        mesasGraTerrazaLbl = new javax.swing.JLabel();
        mesasGraVentanaLbl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        actualizarMesasDisponibles = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuMesasAdministrar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuReservacionesHistorial = new javax.swing.JMenuItem();
        menuReservacionCancelarReservacion = new javax.swing.JMenuItem();
        menuReservacionesAgregarReservacion = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuClientesInsercionMasiva = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("jMenu3");

        jMenu4.setText("jMenu4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Dirección:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Horario");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("Apertura:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Cierre:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Teléfono:");

        actualizarInformacionRestauranteBtn.setText("Actualizar Información");
        actualizarInformacionRestauranteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarInformacionRestauranteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(direccionRestauranteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(telefonoRestauranteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horaAperturaTimePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(horaCierreTimePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(actualizarInformacionRestauranteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccionRestauranteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horaAperturaTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horaCierreTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telefonoRestauranteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(actualizarInformacionRestauranteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jLabel7.setText("Pequeñas:");

        jLabel9.setText("Medianas:");

        jLabel10.setText("Grandes:");

        jLabel11.setText("General:");

        jLabel12.setText("Terraza:");

        jLabel13.setText("Ventana:");

        mesasPeqGeneralLbl.setText("0");

        mesasPeqTerrazaLbl.setText("0");

        mesasPequenasDispLbl.setText("0");

        mesasPeqVentanaLbl.setText("0");

        mesasMedianasDispLbl.setText("0");

        jLabel14.setText("General:");

        jLabel15.setText("Terraza:");

        jLabel16.setText("Ventana:");

        mesasMedGeneralLbl.setText("0");

        mesasMedTerrazaLbl.setText("0");

        mesasMedVentanaLbl.setText("0");

        mesasGrandesDispLbl.setText("0");

        jLabel17.setText("General:");

        jLabel18.setText("Terraza:");

        jLabel19.setText("Ventana:");

        mesasGraGeneralLbl.setText("0");

        mesasGraTerrazaLbl.setText("0");

        mesasGraVentanaLbl.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mesasPequenasDispLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mesasMedianasDispLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(mesasPeqTerrazaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(mesasPeqGeneralLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mesasPeqVentanaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mesasMedTerrazaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mesasMedGeneralLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(mesasGrandesDispLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(mesasMedVentanaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mesasGraGeneralLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mesasGraTerrazaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mesasGraVentanaLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(mesasPequenasDispLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(mesasPeqGeneralLbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(mesasPeqTerrazaLbl))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mesasPeqVentanaLbl)
                            .addComponent(jLabel16))))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(mesasMedianasDispLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(mesasMedGeneralLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(mesasMedTerrazaLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(mesasMedVentanaLbl))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(mesasGrandesDispLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(mesasGraGeneralLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(mesasGraTerrazaLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(mesasGraVentanaLbl))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Mesas Disponibles");

        actualizarMesasDisponibles.setText("Actualizar Disponibles");
        actualizarMesasDisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarMesasDisponiblesActionPerformed(evt);
            }
        });

        jMenu1.setText("Mesas");

        menuMesasAdministrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuMesasAdministrar.setText("Administrar");
        menuMesasAdministrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMesasAdministrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuMesasAdministrar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reservaciones");

        menuReservacionesHistorial.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuReservacionesHistorial.setText("Historial");
        menuReservacionesHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReservacionesHistorialActionPerformed(evt);
            }
        });
        jMenu2.add(menuReservacionesHistorial);

        menuReservacionCancelarReservacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuReservacionCancelarReservacion.setText("Cancelar Reservación");
        menuReservacionCancelarReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReservacionCancelarReservacionActionPerformed(evt);
            }
        });
        jMenu2.add(menuReservacionCancelarReservacion);

        menuReservacionesAgregarReservacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuReservacionesAgregarReservacion.setText("Agregar Reservación");
        menuReservacionesAgregarReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReservacionesAgregarReservacionActionPerformed(evt);
            }
        });
        jMenu2.add(menuReservacionesAgregarReservacion);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Clientes");
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        menuClientesInsercionMasiva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuClientesInsercionMasiva.setText("Inserción Masiva");
        menuClientesInsercionMasiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClientesInsercionMasivaActionPerformed(evt);
            }
        });
        jMenu5.add(menuClientesInsercionMasiva);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Acerca De");
        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(actualizarMesasDisponibles)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(actualizarMesasDisponibles)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuMesasAdministrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMesasAdministrarActionPerformed

        AdminMesasFrm adminMesas = new AdminMesasFrm();
        adminMesas.setVisible(true);
        
    }//GEN-LAST:event_menuMesasAdministrarActionPerformed

    private void menuReservacionCancelarReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReservacionCancelarReservacionActionPerformed
        CancelarReservacionFrm frm = new CancelarReservacionFrm();
        frm.setVisible(true);
    }//GEN-LAST:event_menuReservacionCancelarReservacionActionPerformed

    private void menuReservacionesHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReservacionesHistorialActionPerformed
        HistorialReservacionesFrm frm = new HistorialReservacionesFrm();
        frm.setVisible(true);
    }//GEN-LAST:event_menuReservacionesHistorialActionPerformed

    private void menuReservacionesAgregarReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuReservacionesAgregarReservacionActionPerformed
        CrearReservacionFrm crearReservacionFrm = new CrearReservacionFrm();
        crearReservacionFrm.setVisible(true);
    }//GEN-LAST:event_menuReservacionesAgregarReservacionActionPerformed

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5ActionPerformed

    private void menuClientesInsercionMasivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClientesInsercionMasivaActionPerformed

        boolean clientesRegistrados = false;
        try {
            clientesRegistrados = this.clientesBO.obtenerClientesTodos().size() > 0;
        } catch (BOException ex) {
            System.out.println("### " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo conectar con la base de datos debido a un error, porfavor intentelo de nuevo más tarde.",
                    ex.getMessage(),
                    "Error - Insercion Masiva",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (clientesRegistrados) {
            return;
        }

        List<ClienteDTO> lista = new ArrayList<>();

        ClienteDTO c = new ClienteDTO();

        c.setNombreCompleto("Miguel Pedro Martinez Quiroz");
        c.setTelefono("453-902-343");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("María Fernanda Lopez Hernández");
        c.setTelefono("291-342-042");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Luis Eduardo Gomez Sanchez");
        c.setTelefono("383-710-952");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Ana Sofía Ramirez Lopez");
        c.setTelefono("512-401-884");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Carlos Juan Perez Hernández");
        c.setTelefono("202-118-763");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Laura Beatriz Sanchez Garcia");
        c.setTelefono("607-345-028");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Alejandro Luis Martinez Ramirez");
        c.setTelefono("814-509-647");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Sofia Camila Gomez Figueroa");
        c.setTelefono("701-230-111");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Miguel Fernando Quiroz Perez");
        c.setTelefono("930-457-983");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Lucia Valeria Sanchez Lopez");
        c.setTelefono("319-602-456");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Oscar Alejandro Garcia Ramirez");
        c.setTelefono("485-802-311");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Isabel Andrea Martinez Gomez");
        c.setTelefono("712-394-521");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Carlos Juan Figueroa Gomez");
        c.setTelefono("514-906-214");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Paula Elena Lopez Sanchez");
        c.setTelefono("825-402-768");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Juan Eduardo Quiroz Perez");
        c.setTelefono("649-528-903");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Gabriela Daniela Ramirez Hernández");
        c.setTelefono("935-284-112");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Miguel Alejandro Martinez Gomez");
        c.setTelefono("208-601-339");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Emilia Regina Sanchez Figueroa");
        c.setTelefono("401-731-908");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Oscar Eduardo Garcia Lopez");
        c.setTelefono("602-894-765");
        lista.add(c);

        c = new ClienteDTO();
        c.setNombreCompleto("Julieta Miranda Ramirez Hernandez");
        c.setTelefono("807-315-542");
        lista.add(c);

        try {
            this.clientesBO.insercionMasivaClientes(lista);
            //System.out.println("[!] Se agregaron los clientes correctamente...");
            JOptionPane.showMessageDialog(
                    this,
                    "Se agregaron los clientes correctamente.",
                    "Insercion Masiva",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (BOException ex) {
            System.out.println("### " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar con la base de datos debido a un error, porfavor intentelo de nuevo más tarde.",
                    "Error - Insercion Masiva",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_menuClientesInsercionMasivaActionPerformed

    private void actualizarInformacionRestauranteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarInformacionRestauranteBtnActionPerformed
        // Deshabilitar el botón
        this.actualizarInformacionRestauranteBtn.setEnabled(false);

        // Acción que se ejecuta cuando el botón es presionado
        System.out.println("Botón presionado");

        this.actualizarInformacionRestaurante();

        try {
            this.restaurantesBO.actualizarRestaurante(restaurante);
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear un temporizador que habilita el botón después de un tiempo
        this.timerActualizarInfoRestaurante = new Timer();
        this.timerActualizarInfoRestaurante.schedule(new TimerTask() {
            @Override
            public void run() {
                // Habilitar el botón después de 2 segundos
                actualizarInformacionRestauranteBtn.setEnabled(true);
            }
        }, 5000);  // Esperar 2000 milisegundos (2 segundos)
    }//GEN-LAST:event_actualizarInformacionRestauranteBtnActionPerformed

    private void actualizarMesasDisponiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarMesasDisponiblesActionPerformed
        this.cargarMesasDisponibles();
    }//GEN-LAST:event_actualizarMesasDisponiblesActionPerformed

    private void actualizarInformacionRestaurante() {
        LocalTime horaApertura = this.horaAperturaTimePicker.getTime();
        LocalTime horaCierre = this.horaCierreTimePicker.getTime();
        String direccion = this.direccionRestauranteTextField.getText();
        String telefono = this.telefonoRestauranteTextField.getText();

        try {
            if (horaApertura == null) {
                throw new IllegalArgumentException("No se especificó la hora de apertura.");
            }

            if (horaCierre == null) {
                throw new IllegalArgumentException("No se especificó la hora de cierre.");
            }

            if (horaApertura.isAfter(horaCierre)) {
                throw new IllegalArgumentException("La hora de apertura no puede ser posterior a la hora de cierre.");
            }

            if (direccion == null || direccion.trim().isEmpty()) {
                throw new IllegalArgumentException("La dirección del restaurante no puede estar vacía.");
            }

            if (telefono == null || telefono.trim().isEmpty()) {
                throw new IllegalArgumentException("El teléfono del restaurante no puede estar vacío.");
            }
            
            if (!this.validarTelefono(telefono)) {
                this.telefonoRestauranteTextField.setText("");
                throw new IllegalArgumentException("El número de telefono especificado es incorrecto");
            }

            if (horaCierre.isBefore(horaApertura)) {
                this.horaAperturaTimePicker.setTime(restaurante.getHoraApertura());
                this.horaCierreTimePicker.setTime(restaurante.getHoraCierre());
                throw new IllegalArgumentException("La hora de cierre es anterior a la fecha de apertura");
            }
            
            restaurante.setDireccion(direccion);
            restaurante.setTelefono(telefono);
            restaurante.setHoraCierre(horaCierre);
            restaurante.setHoraApertura(horaApertura);
            
            this.restaurantesBO.actualizarRestaurante(restaurante);

            JOptionPane.showMessageDialog(
                    null, 
                    "La información del restaurante fué actualizada.", 
                    "Actualizar Info", 
                    JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarTelefono(String telefono) {
        String regex = "^\\d{8,15}(-\\d+)*$";
        return telefono != null && telefono.matches(regex);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarInformacionRestauranteBtn;
    private javax.swing.JButton actualizarMesasDisponibles;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField direccionRestauranteTextField;
    private javax.swing.Box.Filler filler1;
    private com.github.lgooddatepicker.components.TimePicker horaAperturaTimePicker;
    private com.github.lgooddatepicker.components.TimePicker horaCierreTimePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem menuClientesInsercionMasiva;
    private javax.swing.JMenuItem menuMesasAdministrar;
    private javax.swing.JMenuItem menuReservacionCancelarReservacion;
    private javax.swing.JMenuItem menuReservacionesAgregarReservacion;
    private javax.swing.JMenuItem menuReservacionesHistorial;
    private javax.swing.JLabel mesasGraGeneralLbl;
    private javax.swing.JLabel mesasGraTerrazaLbl;
    private javax.swing.JLabel mesasGraVentanaLbl;
    private javax.swing.JLabel mesasGrandesDispLbl;
    private javax.swing.JLabel mesasMedGeneralLbl;
    private javax.swing.JLabel mesasMedTerrazaLbl;
    private javax.swing.JLabel mesasMedVentanaLbl;
    private javax.swing.JLabel mesasMedianasDispLbl;
    private javax.swing.JLabel mesasPeqGeneralLbl;
    private javax.swing.JLabel mesasPeqTerrazaLbl;
    private javax.swing.JLabel mesasPeqVentanaLbl;
    private javax.swing.JLabel mesasPequenasDispLbl;
    private javax.swing.JTextField telefonoRestauranteTextField;
    // End of variables declaration//GEN-END:variables
}
