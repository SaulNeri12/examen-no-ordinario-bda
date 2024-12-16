/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.restaurante.presentacion.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.restaurante.negocio.bo.implementaciones.FabricaMesasBO;
import com.restaurante.negocio.bo.implementaciones.FabricaRestaurantesBO;
import com.restaurante.negocio.bo.implementaciones.FabricaTiposMesaBO;
import com.restaurante.negocio.bo.interfaces.IMesasBO;
import com.restaurante.negocio.bo.interfaces.IRestaurantesBO;
import com.restaurante.negocio.bo.interfaces.ITiposMesaBO;
import com.restaurante.negocio.dtos.MesaDTO;
import com.restaurante.negocio.dtos.RestauranteDTO;
import com.restaurante.negocio.dtos.TipoMesaDTO;
import com.restaurante.negocio.dtos.UbicacionMesaDTO;
import com.restaurante.negocio.excepciones.BOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neri
 */
public class AdminMesasFrm extends javax.swing.JFrame {

    private RestauranteDTO restaurante = InformacionRestaurante.getInstance().getRestaurante();
    private IRestaurantesBO restaurantesBO = FabricaRestaurantesBO.obtenerRestaurantesBO();
    private IMesasBO mesasBO = FabricaMesasBO.obtenerMesasBO();
    private ITiposMesaBO tiposMesaBO = FabricaTiposMesaBO.obtenerTiposMesaBO();

    private String codigoMesa = "";

    /**
     * Creates new form AdminMesasFrm
     */
    public AdminMesasFrm() {
        FlatLightLaf.setup();
        initComponents();
        this.cargarMesas();
        this.cargarInformacionCampos();
        this.setLocationRelativeTo(null);
        this.setTitle("Administrar Mesas");

        this.tablaMesas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaMesas.getSelectedRow();

                if (filaSeleccionada != -1) {
                    codigoMesa = (String) tablaMesas.getValueAt(filaSeleccionada, 0);
                } else {
                    codigoMesa = null;
                }
            }
        });
    }

    /**
     *
     */
    private void cargarInformacionCampos() {
        // cargar tipos disponibles de mesa

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        List<TipoMesaDTO> tiposMesa = null;
        try {

            tiposMesa = this.tiposMesaBO.obtenerTiposMesaTodos();

            if (tiposMesa.isEmpty()) {
                this.tiposMesaComboBox.setEnabled(false);
                throw new BOException("No se encontraron tipos de mesa en el sistema.");
            }

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

        // cargar las ubicaciones de las mesas en el restaurante...
        DefaultComboBoxModel modeloUbicaciones = new DefaultComboBoxModel();
        modeloUbicaciones.addAll(Arrays.asList(UbicacionMesaDTO.values()));
        this.ubicacionMesaComboBox.setModel(modeloUbicaciones);
        this.ubicacionMesaComboBox.setSelectedIndex(0);
        this.ubicacionMesaComboBox.repaint();

        int valorInicial = 1;
        int valorMinimo = 1;
        int valorMaximo = 100;
        int incremento = 1;
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(
                valorInicial,
                valorMinimo,
                valorMaximo,
                incremento
        );

        this.cantidadMesasSpinner.setModel(modeloSpinner);
        this.cantidadMesasSpinner.repaint();
    }

    /**
     * Carga las mesas presentes en la base de datos.
     */
    private void cargarMesas() {
        try {
            List<MesaDTO> mesas = this.mesasBO.obtenerMesasTodas(restaurante.getId());

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Codigo");
            model.addColumn("Tipo de Mesa");
            model.addColumn("Capacidad Minima");
            model.addColumn("Capacidad Maxima");
            model.addColumn("Ubicación");
            model.addColumn("Nueva Disponibilidad");

            for (MesaDTO mesa : mesas) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                model.addRow(new Object[]{
                    mesa.getCodigo(),
                    mesa.getTipoMesa().getNombre(),
                    mesa.getTipoMesa().getMinimoPersonas(),
                    mesa.getTipoMesa().getMaximoPersonas(),
                    mesa.getUbicacion().toString(),
                    (mesa.getFechaNuevaDisponibilidad() != null)
                    ? mesa.getFechaNuevaDisponibilidad().format(formatter)
                    : ""
                });
                /*
                model.addRow(new Object[]{
                    mesa.getCodigo(),
                    mesa.getTipoMesa().getNombre(),
                    mesa.getTipoMesa().getMinimoPersonas(),
                    mesa.getTipoMesa().getMaximoPersonas(),
                    mesa.getUbicacion().toString(),
                    (mesa.getFechaNuevaDisponibilidad() != null) ? mesa.getFechaNuevaDisponibilidad() : ""
                });*/
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

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cantidadMesasSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        tiposMesaComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        ubicacionMesaComboBox = new javax.swing.JComboBox<>();
        agregarMesasBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMesas = new javax.swing.JTable();
        volverBtn = new javax.swing.JLabel();
        eliminarMesaBtn = new javax.swing.JButton();
        eliminarTodasBtn = new javax.swing.JButton();

        jButton2.setText("Eliminar Todas");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Administrador Mesas");

        jLabel2.setText("Cantidad:");

        jLabel3.setText("Tipo de mesa:");

        tiposMesaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Ubicación:");

        ubicacionMesaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        agregarMesasBtn.setText("Agregar");
        agregarMesasBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarMesasBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                            .addComponent(cantidadMesasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tiposMesaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ubicacionMesaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(agregarMesasBtn)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantidadMesasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tiposMesaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ubicacionMesaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(agregarMesasBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        eliminarMesaBtn.setText("Eliminar Mesa");
        eliminarMesaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarMesaBtnActionPerformed(evt);
            }
        });

        eliminarTodasBtn.setText("Eliminar Todas");
        eliminarTodasBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarTodasBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(eliminarMesaBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eliminarTodasBtn))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eliminarTodasBtn)
                            .addComponent(eliminarMesaBtn))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volverBtnMouseClicked
        this.dispose();
    }//GEN-LAST:event_volverBtnMouseClicked

    private void agregarMesasBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarMesasBtnActionPerformed
        try {
            int numeroMesas = (Integer) this.cantidadMesasSpinner.getValue();

            TipoMesaDTO tipoMesa = (TipoMesaDTO) this.tiposMesaComboBox.getSelectedItem();
            if (tipoMesa == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un tipo de mesa válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UbicacionMesaDTO ubicacionMesa = (UbicacionMesaDTO) this.ubicacionMesaComboBox.getSelectedItem();
            if (ubicacionMesa == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una ubicación válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(this, "Desea confirmar la operacion?", "Insertar Mesas", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                return;
            }

            mesasBO.insertarMesas(this.restaurante.getId(), tipoMesa, ubicacionMesa, numeroMesas);

            JOptionPane.showMessageDialog(this, "Mesas registradas con éxito.");

            this.cargarMesas();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido de mesas.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la mesa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_agregarMesasBtnActionPerformed

    private void eliminarMesaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarMesaBtnActionPerformed

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Se eliminará la mesa con el código %s. ¿Desea continuar con la operación?".formatted(this.codigoMesa),
                "Eliminar Mesa",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        try {
            this.mesasBO.eliminarMesa(restaurante.getId(), codigoMesa);

            JOptionPane.showMessageDialog(
                    this,
                    "Se elimino la mesa con el código: %s".formatted(this.codigoMesa),
                    "Eliminar Mesa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.cargarMesas();
        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_eliminarMesaBtnActionPerformed

    private void eliminarTodasBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarTodasBtnActionPerformed

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Se eliminarán TODAS las mesas registradas en el sistema. ¿Desea continuar con la operación?",
                "Eliminar Mesas Sistema",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String confirmar = JOptionPane.showInputDialog(this, "Escriba 'Aceptar' para continuar con la acción");
        if (confirmar == null || !confirmar.equalsIgnoreCase("aceptar")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Se canceló la acción.",
                    "Eliminar Mesas Sistema",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            List<MesaDTO> mesasTodas = this.mesasBO.obtenerMesasTodas(restaurante.getId());

            mesasTodas.forEach(mesa -> {
                try {
                    mesasBO.eliminarMesa(restaurante.getId(), mesa.getCodigo());
                } catch (BOException ex) {
                    // no hace nada...
                }
            });

            JOptionPane.showMessageDialog(
                    this,
                    "Se eliminaron todas las mesas en el sistema",
                    "Eliminar Mesas Sistema",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.cargarMesas();

        } catch (BOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo realizar la eliminación de mesas",
                    "Eliminar Mesas Sistema",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_eliminarTodasBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarMesasBtn;
    private javax.swing.JSpinner cantidadMesasSpinner;
    private javax.swing.JButton eliminarMesaBtn;
    private javax.swing.JButton eliminarTodasBtn;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMesas;
    private javax.swing.JComboBox<String> tiposMesaComboBox;
    private javax.swing.JComboBox<String> ubicacionMesaComboBox;
    private javax.swing.JLabel volverBtn;
    // End of variables declaration//GEN-END:variables

}
