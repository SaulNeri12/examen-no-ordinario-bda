/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.restaurante.presentacion.gui;

import com.restaurante.negocio.bo.implementaciones.FabricaClientesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaMesasBO;
import com.restaurante.negocio.bo.implementaciones.FabricaReservacionesBO;
import com.restaurante.negocio.bo.interfaces.IClientesBO;
import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.bo.interfaces.IReservacionesBO;
import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.dtos.EstadoReservacionDTO;
import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.ReservacionDTO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neri
 */
public class CancelarReservacionFrm extends javax.swing.JFrame {

    private RestauranteDTO restaurante = InformacionRestaurante.getInstance().getRestaurante();
    private IMesasBO mesasBO = FabricaMesasBO.obtenerMesasBO();
    private IClientesBO clientesBO = FabricaClientesBO.obtenerClientesDAO();
    private IReservacionesBO reservacionesBO = FabricaReservacionesBO.obtenerReservacionesDAO();

    private Long idReservacion = null;

    /**
     * Creates new form CrearReservacionFrm
     */
    public CancelarReservacionFrm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.cargarInformacionCampos();

        this.fechaHoraInicioDateTimePicker.addDateTimeChangeListener(t -> {
            if (fechaHoraFinDateTimePicker.getDateTimePermissive() != null) {
                if (!t.getNewDateTimePermissive().isBefore(fechaHoraFinDateTimePicker.getDateTimePermissive())) {
                    JOptionPane.showMessageDialog(
                            this,
                            //"No se pudo obtener la informacion de las mesas en la base de datos.",
                            "La fecha de inicio no debe ser despues de la fecha de fin.",
                            "Error - Fecha erronea",
                            JOptionPane.ERROR_MESSAGE
                    );
                    fechaHoraInicioDateTimePicker.setDateTimePermissive(LocalDateTime.now().minusDays(2));
                }
            }
        });

        this.fechaHoraFinDateTimePicker.addDateTimeChangeListener(t -> {
            if (fechaHoraInicioDateTimePicker.getDateTimePermissive() != null) {
                if (!t.getNewDateTimePermissive().isAfter(fechaHoraInicioDateTimePicker.getDateTimePermissive())) {
                    JOptionPane.showMessageDialog(
                            this,
                            //"No se pudo obtener la informacion de las mesas en la base de datos.",
                            "La fecha de fin no debe ser anterior a la fecha de inicio.",
                            "Error - Fecha erronea",
                            JOptionPane.ERROR_MESSAGE
                    );
                    fechaHoraFinDateTimePicker.setDateTimePermissive(LocalDateTime.now());
                }
            }
        });

        this.tablaReservaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaReservaciones.getSelectedRow();

                if (filaSeleccionada != -1) {
                    idReservacion = (Long) tablaReservaciones.getValueAt(filaSeleccionada, 0);
                } else {
                    idReservacion = null;
                }
            }
        });

        this.cargarReservaciones();

    }

    /**
     *
     */
    private void cargarInformacionCampos() {
        try {
            List<ClienteDTO> clientes = this.clientesBO.obtenerClientesTodos();

            DefaultComboBoxModel model = new DefaultComboBoxModel();
            model.addAll(clientes);
            this.clientesComboBox.setModel(model);
            this.clientesComboBox.repaint();

        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo obtener la informacion de las mesas en la base de datos.",
                    ex.getMessage(),
                    "Error - Carga de clientes",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        this.fechaHoraInicioDateTimePicker.setDateTimePermissive(LocalDateTime.now().minusDays(2));
        this.fechaHoraFinDateTimePicker.setDateTimePermissive(LocalDateTime.now());
    }

    /**
     *
     */
    private void cargarReservaciones() {
        try {

            LocalDateTime fechaInicio = LocalDateTime.now().minusDays(2);
            LocalDateTime fechaFin = LocalDateTime.now();

            if (this.fechaHoraInicioDateTimePicker.getDateTimePermissive() != null) {
                fechaInicio = this.fechaHoraInicioDateTimePicker.getDateTimePermissive();
            }

            if (this.fechaHoraFinDateTimePicker.getDateTimePermissive() != null) {
                fechaFin = this.fechaHoraFinDateTimePicker.getDateTimePermissive();
            }

            List<ReservacionDTO> reservaciones = this.reservacionesBO.obtenerReservacionesPorPeriodo(
                    restaurante.getId(),
                    fechaInicio,
                    fechaFin
            );
            
            reservaciones = reservaciones.stream()
                    .filter(res -> res.getEstado().equals(EstadoReservacionDTO.PENDIENTE))
                    .toList();
            
            ClienteDTO cliente = (ClienteDTO) this.clientesComboBox.getSelectedItem();
            if (cliente != null) {
                reservaciones = reservaciones
                        .stream()
                        .filter(res -> res.getCliente().getId().equals(cliente.getId()))
                        .toList();
            }

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID");
            model.addColumn("Codigo Mesa");
            model.addColumn("Nombre Cliente");
            model.addColumn("Tel. Cliente");
            model.addColumn("Estado");
            model.addColumn("Ubicación Mesa");
            model.addColumn("Total");
            model.addColumn("Fecha Reserva");
            model.addColumn("Fecha Registro");

            for (ReservacionDTO r : reservaciones) {

                model.addRow(new Object[]{
                    r.getId(),
                    r.getMesa().getCodigo(),
                    r.getCliente().getNombreCompleto(),
                    r.getCliente().getTelefono(),
                    r.getEstado().toString(),
                    r.getMesa().getUbicacion().toString(),
                    r.getMontoTotal(),
                    r.getFechaHora(),
                    r.getFechaHoraRegistro()
                });
            }

            this.tablaReservaciones.setModel(model);
            this.tablaReservaciones.repaint();
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo obtener la informacion de las mesas en la base de datos.",
                    ex.getMessage(),
                    "Error - Carga de reservaciones",
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        clientesComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fechaHoraInicioDateTimePicker = new com.github.lgooddatepicker.components.DateTimePicker();
        fechaHoraFinDateTimePicker = new com.github.lgooddatepicker.components.DateTimePicker();
        buscarReservacionesBtn = new javax.swing.JButton();
        completarReservacionBtn = new javax.swing.JButton();
        tablaReservacionesScrollpane = new javax.swing.JScrollPane();
        tablaReservaciones = new javax.swing.JTable();
        volverBtn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cancelar Reservación");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel2.setText("Cliente");

        clientesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Fecha de inicio");

        jLabel6.setText("Fecha de fin");

        buscarReservacionesBtn.setText("Buscar");
        buscarReservacionesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarReservacionesBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(fechaHoraInicioDateTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaHoraFinDateTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(buscarReservacionesBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaHoraInicioDateTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaHoraFinDateTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buscarReservacionesBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        completarReservacionBtn.setText("Cancelar Reservación");
        completarReservacionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completarReservacionBtnActionPerformed(evt);
            }
        });

        tablaReservaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Codigo Mesa", "Nombre Cliente", "Tel. Cliente", "Ubicación Mesa", "Fecha Reserva", "Fecha Registro"
            }
        ));
        tablaReservacionesScrollpane.setViewportView(tablaReservaciones);

        volverBtn.setText("<-");
        volverBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        volverBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volverBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablaReservacionesScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(completarReservacionBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(tablaReservacionesScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(completarReservacionBtn)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void completarReservacionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completarReservacionBtnActionPerformed

        if (idReservacion == null || idReservacion == 0) {
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cancelar la reservacion?", "Cancelar Reservacion", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        try {
            this.reservacionesBO.cancelarReservacion(idReservacion);
            
            JOptionPane.showMessageDialog(
                    this,
                    "La reservación se canceló con éxito.",
                    "Error - Cancelar Reservacion",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            this.dispose();
            
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error - Cancelar Reservacion",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_completarReservacionBtnActionPerformed


    private void volverBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volverBtnMouseClicked
        dispose();
    }//GEN-LAST:event_volverBtnMouseClicked

    private void buscarReservacionesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarReservacionesBtnActionPerformed
        this.cargarReservaciones();
    }//GEN-LAST:event_buscarReservacionesBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarReservacionesBtn;
    private javax.swing.JComboBox<String> clientesComboBox;
    private javax.swing.JButton completarReservacionBtn;
    private com.github.lgooddatepicker.components.DateTimePicker fechaHoraFinDateTimePicker;
    private com.github.lgooddatepicker.components.DateTimePicker fechaHoraInicioDateTimePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable tablaReservaciones;
    private javax.swing.JScrollPane tablaReservacionesScrollpane;
    private javax.swing.JLabel volverBtn;
    // End of variables declaration//GEN-END:variables

}
