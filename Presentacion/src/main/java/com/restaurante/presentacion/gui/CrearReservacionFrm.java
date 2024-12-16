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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neri
 */
public class CrearReservacionFrm extends javax.swing.JFrame {

    private RestauranteDTO restaurante = InformacionRestaurante.getInstance().getRestaurante();
    private IMesasBO mesasBO = FabricaMesasBO.obtenerMesasBO();
    private IClientesBO clientesBO = FabricaClientesBO.obtenerClientesDAO();
    private IReservacionesBO reservacionesBO = FabricaReservacionesBO.obtenerReservacionesDAO();

    private String codigoMesa = "";

    /**
     * Creates new form CrearReservacionFrm
     */
    public CrearReservacionFrm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.cargarMesas();
        this.cargarInformacionCampos();
        this.cantidadPersonasSpinner.setEnabled(false);

        this.fechaDatePicker.addDateChangeListener(d -> {
            if (d.getNewDate().isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        //"No se pudo obtener la informacion de las mesas en la base de datos.",
                        "La fecha no debe ser anterior a la actual.",
                        "Error - Fecha erronea",
                        JOptionPane.ERROR_MESSAGE
                );
                fechaDatePicker.setDate(LocalDate.now());
            }
        });

        this.horaTimePicker.addTimeChangeListener(t -> {
            LocalTime horaApertura = restaurante.getHoraApertura();
            LocalTime horaCierre = restaurante.getHoraCierre();
            LocalTime horaSeleccionada = t.getNewTime();

            if (horaSeleccionada.isBefore(horaApertura)) {
                horaTimePicker.setTime(horaApertura);
                JOptionPane.showMessageDialog(
                        this,
                        "La hora seleccionada es antes de la hora de apertura del restaurante.",
                        "Error - Hora no permitida",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (horaSeleccionada.isAfter(horaCierre.minusHours(1))) {
                horaTimePicker.setTime(horaCierre.minusHours(1));
                JOptionPane.showMessageDialog(
                        this,
                        "La hora seleccionada es después de la hora de cierre del restaurante.",
                        "Error - Hora no permitida",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            horaTimePicker.setTime(horaSeleccionada);
        });

        this.tablaMesas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaMesas.getSelectedRow();

                if (filaSeleccionada != -1) {
                    codigoMesa = (String) tablaMesas.getValueAt(filaSeleccionada, 0);
                    int capacidadMin = (Integer) tablaMesas.getValueAt(filaSeleccionada, 2);
                    int capacidadMax = (Integer) tablaMesas.getValueAt(filaSeleccionada, 3);

                    int valorInicial = capacidadMin;
                    int valorMinimo = capacidadMin;
                    int valorMaximo = capacidadMax;
                    int incremento = 1;

                    SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(
                            valorInicial,
                            valorMinimo,
                            valorMaximo,
                            incremento
                    );

                    cantidadPersonasSpinner.setModel(modeloSpinner);
                    cantidadPersonasSpinner.setEnabled(true);
                } else {
                    codigoMesa = null;
                    cantidadPersonasSpinner.setEnabled(true);
                }
            }
        });

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

        this.fechaDatePicker.setDate(LocalDate.now());
        this.horaTimePicker.setTime(restaurante.getHoraApertura());
    }

    /**
     *
     */
    private void cargarMesas() {
        try {
            List<MesaDTO> mesas = this.mesasBO.obtenerMesasDisponibles(restaurante.getId());

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Codigo");
            model.addColumn("Tipo de Mesa");
            model.addColumn("Capacidad Minima");
            model.addColumn("Capacidad Maxima");
            model.addColumn("Ubicación");

            for (MesaDTO mesa : mesas) {

                model.addRow(new Object[]{
                    mesa.getCodigo(),
                    mesa.getTipoMesa().getNombre(),
                    mesa.getTipoMesa().getMinimoPersonas(),
                    mesa.getTipoMesa().getMaximoPersonas(),
                    mesa.getUbicacion().toString()
                });
            }

            this.tablaMesas.setModel(model);
            this.tablaMesas.repaint();
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    //"No se pudo obtener la informacion de las mesas en la base de datos.",
                    ex.getMessage(),
                    "Error - Carga de mesas",
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
        jLabel3 = new javax.swing.JLabel();
        cantidadPersonasSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        fechaDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jLabel5 = new javax.swing.JLabel();
        horaTimePicker = new com.github.lgooddatepicker.components.TimePicker();
        completarReservacionBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMesas = new javax.swing.JTable();
        volverBtn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crear Reservación");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel2.setText("Clientes");

        clientesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Cantidad de personas");

        jLabel4.setText("Fecha");

        jLabel5.setText("Hora");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clientesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(0, 12, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(cantidadPersonasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(horaTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantidadPersonasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(horaTimePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        completarReservacionBtn.setText("Completar Reservación");
        completarReservacionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completarReservacionBtnActionPerformed(evt);
            }
        });

        tablaMesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Tipo de Mesa", "Capacidad Minima", "Capacidad Máxima", "Ubicación", "Nueva Disponibilidad"
            }
        ));
        jScrollPane1.setViewportView(tablaMesas);

        volverBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        volverBtn.setText("<-");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(completarReservacionBtn))
                .addContainerGap(17, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(completarReservacionBtn)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void completarReservacionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completarReservacionBtnActionPerformed

        if (codigoMesa == null || codigoMesa == "") {
            this.cantidadPersonasSpinner.setEnabled(false);
            return;
        }

        MesaDTO mesaSeleccionada = null;

        try {
            mesaSeleccionada = this.mesasBO.obtenerMesaPorCodigo(restaurante.getId(), codigoMesa);
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error - Carga de mesa",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        ClienteDTO cliente = (ClienteDTO) this.clientesComboBox.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se ha seleccionado un cliente.",
                    "Error - Carga de cliente",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        LocalDateTime fechaHoraReservacion = LocalDateTime.of(
                this.fechaDatePicker.getDate(),
                this.horaTimePicker.getTime()
        );

        int cantidadPersonas = (Integer) this.cantidadPersonasSpinner.getValue();

        int opcion = JOptionPane.showConfirmDialog(this, "Desea completar la reservacion?", "Realizar Reservacion", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        ReservacionDTO reservacion = new ReservacionDTO();

        reservacion.setCliente(cliente);
        reservacion.setEstado(EstadoReservacionDTO.PENDIENTE);
        reservacion.setFechaHora(fechaHoraReservacion);
        reservacion.setMesa(mesaSeleccionada);
        reservacion.setNumeroPersonas(cantidadPersonas);

        try {
            this.reservacionesBO.agregarReservacion(reservacion);

            JOptionPane.showMessageDialog(
                    this,
                    "Se agregó la reservación con éxito.",
                    "Agregar Reservacion",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.dispose();
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error - Agregar Reservacion",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }//GEN-LAST:event_completarReservacionBtnActionPerformed


    private void volverBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volverBtnMouseClicked
        dispose();
    }//GEN-LAST:event_volverBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner cantidadPersonasSpinner;
    private javax.swing.JComboBox<String> clientesComboBox;
    private javax.swing.JButton completarReservacionBtn;
    private com.github.lgooddatepicker.components.DatePicker fechaDatePicker;
    private com.github.lgooddatepicker.components.TimePicker horaTimePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMesas;
    private javax.swing.JLabel volverBtn;
    // End of variables declaration//GEN-END:variables

}
