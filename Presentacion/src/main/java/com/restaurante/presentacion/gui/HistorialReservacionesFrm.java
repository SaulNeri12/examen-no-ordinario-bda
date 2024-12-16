/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.restaurante.presentacion.gui;

import com.restaurante.negocio.bo.implementaciones.FabricaClientesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaMesasBO;
import com.restaurante.negocio.bo.implementaciones.FabricaReservacionesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaTiposMesaBO;
import com.restaurante.negocio.bo.interfaces.IClientesBO;
import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.bo.interfaces.IReservacionesBO;
import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.ClienteDTO;
import com.restaurante.negocio.dtos.EstadoReservacionDTO;
import com.restaurante.negocio.dtos.ReservacionDTO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neri
 */
public class HistorialReservacionesFrm extends javax.swing.JFrame {

    private RestauranteDTO restaurante = InformacionRestaurante.getInstance().getRestaurante();
    private IMesasBO mesasBO = FabricaMesasBO.obtenerMesasBO();
    private IClientesBO clientesBO = FabricaClientesBO.obtenerClientesDAO();
    private IReservacionesBO reservacionesBO = FabricaReservacionesBO.obtenerReservacionesDAO();
    private ITiposMesaBO tiposMesaBO = FabricaTiposMesaBO.obtenerTiposMesaBO();

    private Long idReservacion = null;

    /**
     * Creates new form CrearReservacionFrm
     */
    public HistorialReservacionesFrm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.cargarInformacionCampos();
        this.finalizarReservacionBtn.setEnabled(false);

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
                    finalizarReservacionBtn.setEnabled(true);
                } else {
                    idReservacion = null;
                    finalizarReservacionBtn.setEnabled(false);
                }
            }
        });

        this.cargarReservaciones();

    }

    /**
     *
     */
    private void cargarInformacionCampos() {

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        List<TipoMesaDTO> tiposMesa = null;
        try {

            tiposMesa = this.tiposMesaBO.obtenerTiposMesaTodos();

            if (tiposMesa.isEmpty()) {
                this.tiposMesaComboBox.setEnabled(false);
                throw new BOException("No se encontraron tipos de mesa en el sistema.");
            }

            model.addElement("N/A");
            model.addAll(tiposMesa);

            this.tiposMesaComboBox.setModel(model);
            this.tiposMesaComboBox.setSelectedIndex(0);
            this.tiposMesaComboBox.repaint();
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo obtener la informacion de las mesas en la base de datos.",
                    ex.getMessage(),
                    "Error - Carga de tipos mesa",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        try {
            List<ClienteDTO> clientes = this.clientesBO.obtenerClientesTodos();

            DefaultComboBoxModel modelClientes = new DefaultComboBoxModel();
            modelClientes.addAll(clientes);
            this.clientesComboBox.setModel(modelClientes);
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

            ClienteDTO cliente = (ClienteDTO) this.clientesComboBox.getSelectedItem();
            if (cliente != null) {
                reservaciones = reservaciones
                        .stream()
                        .filter(res -> res.getCliente().getId().equals(cliente.getId()))
                        .toList();
            }

            final TipoMesaDTO tipoMesa;
            
            /*
            Object seteado = this.tiposMesaComboBox.getSelectedItem();
            if (!seteado.equalsIgnoreCase("n/a")) {
                tipoMesa = (TipoMesaDTO) this.tiposMesaComboBox.getSelectedItem();

                if (tipoMesa != null) {
                    reservaciones = reservaciones
                            .stream()
                            .filter(res -> res.getMesa()
                            .getTipoMesa()
                            .getNombre()
                            .equalsIgnoreCase(tipoMesa.getNombre()))
                            .toList();
                }
            }*/

            Object seteado = this.tiposMesaComboBox.getSelectedItem();
            if (seteado instanceof String && ((String) seteado).equalsIgnoreCase("n/a")) {
                tipoMesa = null; 
            } else if (seteado instanceof TipoMesaDTO) {
                tipoMesa = (TipoMesaDTO) seteado;

                if (tipoMesa != null) {
                    reservaciones = reservaciones
                            .stream()
                            .filter(res -> res.getMesa()
                            .getTipoMesa()
                            .getNombre()
                            .equalsIgnoreCase(tipoMesa.getNombre()))
                            .toList();
                }
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
        tiposMesaComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        finalizarReservacionBtn = new javax.swing.JButton();
        tablaReservacionesScrollpane = new javax.swing.JScrollPane();
        tablaReservaciones = new javax.swing.JTable();
        volverBtn = new javax.swing.JLabel();
        generarReporteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Historial Reservaciones");
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

        tiposMesaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tiposMesaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposMesaComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo de mesa:");

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(fechaHoraInicioDateTimePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tiposMesaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fechaHoraFinDateTimePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(clientesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 19, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(107, 107, 107)
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
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tiposMesaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(buscarReservacionesBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        finalizarReservacionBtn.setText("Finalizar Reservacion");
        finalizarReservacionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarReservacionBtnActionPerformed(evt);
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

        generarReporteBtn.setText("Generar Reporte");
        generarReporteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarReporteBtnActionPerformed(evt);
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
                        .addComponent(generarReporteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finalizarReservacionBtn)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finalizarReservacionBtn)
                    .addComponent(generarReporteBtn))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void finalizarReservacionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarReservacionBtnActionPerformed

        if (idReservacion == null || idReservacion == 0) {
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea dar por concluida la reservacion?", "Finalizar Reservacion", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        ReservacionDTO reservacion = null;
        try {
            reservacion = this.reservacionesBO.obtenerReservacionPorID(idReservacion);
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error - Finalizar Reservacion",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        try {
            reservacion.setEstado(EstadoReservacionDTO.FINALIZADA);
            this.reservacionesBO.actualizarReservacion(reservacion);

            JOptionPane.showMessageDialog(
                    this,
                    "La reservación se finalizó con éxito.",
                    "Error - Finalizar Reservacion",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.dispose();

        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error - Finalizar Reservacion",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_finalizarReservacionBtnActionPerformed


    private void volverBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volverBtnMouseClicked
        dispose();
    }//GEN-LAST:event_volverBtnMouseClicked

    private void buscarReservacionesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarReservacionesBtnActionPerformed
        this.cargarReservaciones();
    }//GEN-LAST:event_buscarReservacionesBtnActionPerformed

    private void tiposMesaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiposMesaComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiposMesaComboBoxActionPerformed

    private void generarReporteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarReporteBtnActionPerformed

    }//GEN-LAST:event_generarReporteBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarReservacionesBtn;
    private javax.swing.JComboBox<String> clientesComboBox;
    private com.github.lgooddatepicker.components.DateTimePicker fechaHoraFinDateTimePicker;
    private com.github.lgooddatepicker.components.DateTimePicker fechaHoraInicioDateTimePicker;
    private javax.swing.JButton finalizarReservacionBtn;
    private javax.swing.JButton generarReporteBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable tablaReservaciones;
    private javax.swing.JScrollPane tablaReservacionesScrollpane;
    private javax.swing.JComboBox<String> tiposMesaComboBox;
    private javax.swing.JLabel volverBtn;
    // End of variables declaration//GEN-END:variables

}
