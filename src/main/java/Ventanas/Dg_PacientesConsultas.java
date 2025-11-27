/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Ventanas;

import Controlador.PacienteDAO;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author erick
 */
public class Dg_PacientesConsultas extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dg_PacientesConsultas.class.getName());

    /**
     * Creates new form Dg_PacientesConsultas
     */
    PacienteDAO pacienteDAO = PacienteDAO.getInstancia();
    private javax.swing.JTable tablaRegPacientes;
    public Dg_PacientesConsultas(java.awt.Frame parent, boolean modal, javax.swing.JTable tablaRegPacientes) {
        super(parent, modal);
        this.tablaRegPacientes = tablaRegPacientes;
        initComponents();
        setTitle("Consultar Paciente");  
        setSize(380, 620);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        
        //cargarMedicosEnCombo();
        
        buttonGroup1.add(rbTodos);
        buttonGroup1.add(rbNombre);
        buttonGroup1.add(rbPaterno);
        buttonGroup1.add(rbMaterno);
        buttonGroup1.add(rbEdad);
        buttonGroup1.add(rbSSNMedico);
        buttonGroup1.add(rbCalle);
        buttonGroup1.add(rbNumero);
        buttonGroup1.add(rbColonia);
        buttonGroup1.add(rbCodPostal);
        
        rbTodos.setSelected(true);
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(false);
        cajaNumeroConsultas.setEnabled(false);
        cajaColoniaConsultas.setEnabled(false);
        cajaCodPostalConsultas.setEnabled(false);
    }
    
    
    /*
    public void actualizarTablaFiltro(JTable tabla){
        ResultSetTableModel modelo;
        //"INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) "
        try {
            if (rbNombre.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Nombre", cajaNombreConsultas.getText());
            }else if (rbPaterno.isSelected()) {
                modelo = pacienteDAO.obtenerPacientesFiltrados("Ape_Paterno", cajaPaternoConsultas.getText());
            }else if (rbMaterno.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Ape_Materno", cajaMaternoConsultas.getText());
            }else if (rbEdad.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Edad", (Integer) spEdadConsultas.getValue());
            }else if (rbSSNMedico.isSelected()){
                cargarMedicosEnCombo();
                modelo = pacienteDAO.obtenerPacientesFiltrados("SSN_Medico_Cabecera", cbSSNMedicoConsultas.getSelectedItem().toString());
            }else if(rbCalle.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Calle", cajaCalleConsultas.getText());
            }else if (rbNumero.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Numero", cajaNumeroConsultas.getText());
            }else if (rbColonia.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Colonia", cajaColoniaConsultas.getText());
            }else if (rbCodPostal.isSelected()){
                modelo = pacienteDAO.obtenerPacientesFiltrados("Codigo_Postal", cajaCodPostalConsultas.getText());
            }else{
                modelo = pacienteDAO.obtenerPacientes();
            }
            tabla.setModel(modelo);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar medicos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */
    public void actualizarTablaFiltro(JTable tabla) {
    ResultSetTableModel modelo;

    try {
        if (rbNombre.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Nombre", cajaNombreConsultas.getText()
            );

        } else if (rbPaterno.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Ape_Paterno", cajaPaternoConsultas.getText()
            );

        } else if (rbMaterno.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Ape_Materno", cajaMaternoConsultas.getText()
            );

        } else if (rbEdad.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Edad", spEdadConsultas.getValue().toString()
            );

        } else if (rbSSNMedico.isSelected()) {
            // ❗ NO llamar cargarMedicosEnCombo() aquí
            // Solo usamos lo que ya contiene el combo.
            Object seleccionado = cbSSNMedicoConsultas.getSelectedItem();
            String valor = (seleccionado != null) ? seleccionado.toString() : "";

            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "SSN_Medico_Cabecera", valor
            );

        } else if (rbCalle.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Calle", cajaCalleConsultas.getText()
            );

        } else if (rbNumero.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Numero", cajaNumeroConsultas.getText()
            );

        } else if (rbColonia.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Colonia", cajaColoniaConsultas.getText()
            );

        } else if (rbCodPostal.isSelected()) {
            modelo = pacienteDAO.obtenerPacientesFiltrados(
                "Codigo_Postal", cajaCodPostalConsultas.getText()
            );

        } else {
            modelo = pacienteDAO.obtenerPacientes();
        }

        tabla.setModel(modelo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al filtrar pacientes: " + e.getMessage());
        e.printStackTrace();
    }
}

    
    public void limpiarCampos(){
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        spEdadConsultas.setValue(0);
        cbSSNMedicoConsultas.setSelectedIndex(-1);
        cajaCalleConsultas.setText("");
        cajaNumeroConsultas.setText("");
        cajaColoniaConsultas.setText("");
        cajaCodPostalConsultas.setText("");
        
    }
    
    private void desactivarCampos() {
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(false);
        cajaNumeroConsultas.setEnabled(false);
        cajaColoniaConsultas.setEnabled(false);
        cajaCodPostalConsultas.setEnabled(false);
    }

    
    public void cargarMedicosEnCombo() {
    cbSSNMedicoConsultas.removeAllItems(); 

    ResultSet rs = pacienteDAO.obtenerTodosLosMedicos();

    try {
        while (rs.next()) {
            String ssn = rs.getString("SSN");
            
            cbSSNMedicoConsultas.addItem(ssn);
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar médicos");
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
        rbTodos = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbPaterno = new javax.swing.JRadioButton();
        rbMaterno = new javax.swing.JRadioButton();
        rbEdad = new javax.swing.JRadioButton();
        rbSSNMedico = new javax.swing.JRadioButton();
        rbCalle = new javax.swing.JRadioButton();
        rbNumero = new javax.swing.JRadioButton();
        rbColonia = new javax.swing.JRadioButton();
        rbCodPostal = new javax.swing.JRadioButton();
        cajaNombreConsultas = new javax.swing.JTextField();
        cajaPaternoConsultas = new javax.swing.JTextField();
        cajaMaternoConsultas = new javax.swing.JTextField();
        spEdadConsultas = new javax.swing.JSpinner();
        cbSSNMedicoConsultas = new javax.swing.JComboBox<>();
        cajaCalleConsultas = new javax.swing.JTextField();
        cajaNumeroConsultas = new javax.swing.JTextField();
        cajaColoniaConsultas = new javax.swing.JTextField();
        cajaCodPostalConsultas = new javax.swing.JTextField();
        btnRestablecerPacConsultas = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rbTodos.setText("Todos");
        rbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTodosActionPerformed(evt);
            }
        });

        rbNombre.setText("Nombre");
        rbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNombreActionPerformed(evt);
            }
        });

        rbPaterno.setText("Apellido Paterno");
        rbPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPaternoActionPerformed(evt);
            }
        });

        rbMaterno.setText("Apellido Materno");
        rbMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMaternoActionPerformed(evt);
            }
        });

        rbEdad.setText("Edad");
        rbEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEdadActionPerformed(evt);
            }
        });

        rbSSNMedico.setText("SSN Medico");
        rbSSNMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSSNMedicoActionPerformed(evt);
            }
        });

        rbCalle.setText("Calle");
        rbCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCalleActionPerformed(evt);
            }
        });

        rbNumero.setText("Numero");
        rbNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNumeroActionPerformed(evt);
            }
        });

        rbColonia.setText("Colonia");
        rbColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbColoniaActionPerformed(evt);
            }
        });

        rbCodPostal.setText("Codigo Postal");
        rbCodPostal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodPostalActionPerformed(evt);
            }
        });

        cajaNombreConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaNombreConsultasKeyReleased(evt);
            }
        });

        cajaPaternoConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaPaternoConsultasKeyReleased(evt);
            }
        });

        cajaMaternoConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaMaternoConsultasKeyReleased(evt);
            }
        });

        spEdadConsultas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spEdadConsultasStateChanged(evt);
            }
        });

        cbSSNMedicoConsultas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSSNMedicoConsultasItemStateChanged(evt);
            }
        });

        cajaCalleConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaCalleConsultasKeyReleased(evt);
            }
        });

        cajaNumeroConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaNumeroConsultasKeyReleased(evt);
            }
        });

        cajaColoniaConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaColoniaConsultasKeyReleased(evt);
            }
        });

        cajaCodPostalConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaCodPostalConsultasKeyReleased(evt);
            }
        });

        btnRestablecerPacConsultas.setText("Restablecer");

        jButton2.setText("jButton2");

        jButton3.setText("jButton3");

        jButton4.setText("jButton4");

        jButton6.setText("jButton6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rbTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(rbCodPostal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbColonia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbNumero, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbCalle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbSSNMedico, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbEdad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbMaterno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(rbPaterno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaNombreConsultas)
                            .addComponent(cajaPaternoConsultas)
                            .addComponent(cajaMaternoConsultas)
                            .addComponent(spEdadConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSSNMedicoConsultas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cajaCalleConsultas)
                            .addComponent(cajaNumeroConsultas)
                            .addComponent(cajaColoniaConsultas)
                            .addComponent(cajaCodPostalConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(btnRestablecerPacConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaNombreConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaPaternoConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaMaternoConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spEdadConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbSSNMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSSNMedicoConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaCalleConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaNumeroConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaColoniaConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaCodPostalConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRestablecerPacConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cajaNombreConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaNombreConsultasKeyReleased
    
            ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Nombre", cajaNombreConsultas.getText());
            tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaNombreConsultasKeyReleased

    private void rbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTodosActionPerformed
       
    }//GEN-LAST:event_rbTodosActionPerformed

    private void rbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNombreActionPerformed
       
        limpiarCampos();
        desactivarCampos();

        cajaNombreConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbNombreActionPerformed

    private void rbPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPaternoActionPerformed
       
        limpiarCampos();
        desactivarCampos();

        cajaPaternoConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
        
    }//GEN-LAST:event_rbPaternoActionPerformed

    private void rbMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMaternoActionPerformed
    
        limpiarCampos();
        desactivarCampos();

        cajaMaternoConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbMaternoActionPerformed

    private void rbEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEdadActionPerformed
       
        
        limpiarCampos();
        desactivarCampos();

        spEdadConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbEdadActionPerformed

    private void rbSSNMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSSNMedicoActionPerformed
    
        limpiarCampos();
        desactivarCampos();
        cbSSNMedicoConsultas.setEnabled(true);

        // MUY IMPORTANTE
        cargarMedicosEnCombo();
        cbSSNMedicoConsultas.setSelectedIndex(-1);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbSSNMedicoActionPerformed

    private void rbCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCalleActionPerformed
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(true);
        cajaNumeroConsultas.setEnabled(false);
        cajaColoniaConsultas.setEnabled(false);
        cajaCodPostalConsultas.setEnabled(false);
        
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        spEdadConsultas.setValue(0);
        cbSSNMedicoConsultas.setSelectedIndex(-1);
       
        cajaNumeroConsultas.setText("");
        cajaColoniaConsultas.setText("");
        cajaCodPostalConsultas.setText("");
    }//GEN-LAST:event_rbCalleActionPerformed

    private void rbNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNumeroActionPerformed
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(false);
        cajaNumeroConsultas.setEnabled(true);
        cajaColoniaConsultas.setEnabled(false);
        cajaCodPostalConsultas.setEnabled(false);
        
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        spEdadConsultas.setValue(0);
        cbSSNMedicoConsultas.setSelectedIndex(-1);
        cajaCalleConsultas.setText("");
        
        cajaColoniaConsultas.setText("");
        cajaCodPostalConsultas.setText("");
    }//GEN-LAST:event_rbNumeroActionPerformed

    private void rbColoniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbColoniaActionPerformed
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(false);
        cajaNumeroConsultas.setEnabled(false);
        cajaColoniaConsultas.setEnabled(true);
        cajaCodPostalConsultas.setEnabled(false);
        
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        spEdadConsultas.setValue(0);
        cbSSNMedicoConsultas.setSelectedIndex(-1);
        cajaCalleConsultas.setText("");
        cajaNumeroConsultas.setText("");
        
        cajaCodPostalConsultas.setText("");
    }//GEN-LAST:event_rbColoniaActionPerformed

    private void rbCodPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodPostalActionPerformed
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        spEdadConsultas.setEnabled(false);
        cbSSNMedicoConsultas.setEnabled(false);
        cajaCalleConsultas.setEnabled(false);
        cajaNumeroConsultas.setEnabled(false);
        cajaColoniaConsultas.setEnabled(false);
        cajaCodPostalConsultas.setEnabled(true);
        
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        spEdadConsultas.setValue(0);
        cbSSNMedicoConsultas.setSelectedIndex(-1);
        cajaCalleConsultas.setText("");
        cajaNumeroConsultas.setText("");
        cajaColoniaConsultas.setText("");
        
    }//GEN-LAST:event_rbCodPostalActionPerformed
//"INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) "
    private void cajaPaternoConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaPaternoConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Ape_Paterno", cajaPaternoConsultas.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaPaternoConsultasKeyReleased

    private void cajaMaternoConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaMaternoConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Ape_Materno", cajaMaternoConsultas.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaMaternoConsultasKeyReleased

    private void spEdadConsultasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spEdadConsultasStateChanged
        int edad = (Integer) spEdadConsultas.getValue(); 
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Edad", String.valueOf(edad));
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_spEdadConsultasStateChanged

    private void cbSSNMedicoConsultasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSSNMedicoConsultasItemStateChanged
        /*
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN_Medico_Cabecera", cbSSNMedicoConsultas.getSelectedItem().toString());
        tablaRegPacientes.setModel(modelo);
        */
        if (cbSSNMedicoConsultas.getSelectedItem() == null) {
        tablaRegPacientes.setModel(new DefaultTableModel()); // opcional, tabla vacía
        return;
        }

        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN_Medico_Cabecera", cbSSNMedicoConsultas.getSelectedItem().toString());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cbSSNMedicoConsultasItemStateChanged

    private void cajaCalleConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaCalleConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Calle", cajaCalleConsultas.getText());
        tablaRegPacientes.setModel(modelo); 
    }//GEN-LAST:event_cajaCalleConsultasKeyReleased

    private void cajaNumeroConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaNumeroConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Numero", cajaNumeroConsultas.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaNumeroConsultasKeyReleased

    private void cajaColoniaConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaColoniaConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Colonia", cajaColoniaConsultas.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaColoniaConsultasKeyReleased

    private void cajaCodPostalConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaCodPostalConsultasKeyReleased
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Codigo_Postal", cajaCodPostalConsultas.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaCodPostalConsultasKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            JTable tablaDummy = new JTable();
            @Override
            public void run() {
                Dg_PacientesConsultas dialog = new Dg_PacientesConsultas(new javax.swing.JFrame(), true, tablaDummy);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestablecerPacConsultas;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cajaCalleConsultas;
    private javax.swing.JTextField cajaCodPostalConsultas;
    private javax.swing.JTextField cajaColoniaConsultas;
    private javax.swing.JTextField cajaMaternoConsultas;
    private javax.swing.JTextField cajaNombreConsultas;
    private javax.swing.JTextField cajaNumeroConsultas;
    private javax.swing.JTextField cajaPaternoConsultas;
    private javax.swing.JComboBox<String> cbSSNMedicoConsultas;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JRadioButton rbCalle;
    private javax.swing.JRadioButton rbCodPostal;
    private javax.swing.JRadioButton rbColonia;
    private javax.swing.JRadioButton rbEdad;
    private javax.swing.JRadioButton rbMaterno;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JRadioButton rbNumero;
    private javax.swing.JRadioButton rbPaterno;
    private javax.swing.JRadioButton rbSSNMedico;
    private javax.swing.JRadioButton rbTodos;
    private javax.swing.JSpinner spEdadConsultas;
    // End of variables declaration//GEN-END:variables
}
