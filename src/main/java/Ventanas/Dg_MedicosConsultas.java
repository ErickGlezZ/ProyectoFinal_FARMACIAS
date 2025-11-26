/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Ventanas;

import ConexionBD.ConexionBD;
import Controlador.MedicoDAO;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author erick
 */
public class Dg_MedicosConsultas extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dg_MedicosConsultas.class.getName());

    /**
     * Creates new form Dg_MedicosConsultas
     */
    
    MedicoDAO medicoDAO = MedicoDAO.getInstancia();
    
    private javax.swing.JTable tablaRegMedicos;
    
    public Dg_MedicosConsultas(java.awt.Frame parent, boolean modal, javax.swing.JTable tablaRegMedicos) {
        super(parent, modal);
        this.tablaRegMedicos = tablaRegMedicos;
        initComponents();
        setTitle("Consultar Médico");  
        setSize(380, 450);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        
        buttonGroup1.add(rbTodos);
        buttonGroup1.add(rbNombres);
        buttonGroup1.add(rbPaterno);
        buttonGroup1.add(rbMaterno);
        buttonGroup1.add(rbEspecialidad);
        buttonGroup1.add(rbExperiencia);
        
        rbTodos.setSelected(true);
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
        
        listaMedicos = añadirMedicos();
    }
    
    
    public void actualizarTablaFiltro(JTable tabla){
        ResultSetTableModel modelo;
        
        try {
            if (rbNombres.isSelected()){
                modelo = medicoDAO.obtenerMedicosFiltrados("Nombre", cajaNombreConsultas.getText());
            }else if (rbPaterno.isSelected()) {
                modelo = medicoDAO.obtenerMedicosFiltrados("Ape_Paterno", cajaPaternoConsultas.getText());
            }else if (rbMaterno.isSelected()){
                modelo = medicoDAO.obtenerMedicosFiltrados("Ape_Materno", cajaMaternoConsultas.getText());
            }else if (rbEspecialidad.isSelected()){
                modelo = medicoDAO.obtenerMedicosFiltrados("Especialidad", cbEspecialidadConsultas.getSelectedItem().toString());
            }else if(rbExperiencia.isSelected()){
                modelo = medicoDAO.obtenerMedicosFiltrados("Años_Experiencia", cajaExperienciaConsultas.getText());
            }else{
                modelo = medicoDAO.obtenerMedicos();
            }
            tabla.setModel(modelo);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar medicos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /*
    public boolean algunaOpcionSeleccionada(){
        return rbTodos.isSelected() || rbNombres.isSelected() || 
               rbPaterno.isSelected() || rbMaterno.isSelected() || 
               rbEspecialidad.isSelected() || rbExperiencia.isSelected();
    }
    */
    public void limpiarCampos(){
        
        cajaNombreConsultas.setText("");
        cajaPaternoConsultas.setText("");
        cajaMaternoConsultas.setText("");
        cbEspecialidadConsultas.setSelectedIndex(0);
        cajaExperienciaConsultas.setText("");
        cajaIndiceMed.setText("0");
        buttonGroup1.clearSelection();
        //posActual = -1;
        actualizarTablaFiltro(tablaRegMedicos);
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
        
    }
     
    ArrayList<Medico> listaMedicos = new ArrayList<>();
    int posActual = -1;
    
    
    public ArrayList<Medico> añadirMedicos(){
        
        
        String sql = "SELECT * FROM Medicos";
        ResultSet rs = null;
        
        try {
            rs = ConexionBD.getInstancia().ejecutarConsultaSQL(sql);
            
            if (rs != null && rs.next()){
                do{
                    String ssn = rs.getString("SSN");
                    String nom = rs.getString("Nombre");
                    String app = rs.getString("Ape_Paterno");
                    String apm = rs.getString("Ape_Materno");
                    String esp = rs.getString("Especialidad");
                    Byte exp = rs.getByte("Años_Experiencia");
                    
                    Medico m = new Medico(ssn, nom, app, apm, esp, exp);
                    listaMedicos.add(m);
                }while (rs.next());
            }else {
                 JOptionPane.showMessageDialog(this, "No se encontraron registros de Medico");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los medicos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaMedicos;
    }
    
    public void mostrarRegistros(int indice){
        if (indice >= 0 && indice < listaMedicos.size()){
            Medico reg = listaMedicos.get(indice);
            
            cajaNombreConsultas.setText(reg.getNombre());
            cajaPaternoConsultas.setText(reg.getApePaterno());
            cajaMaternoConsultas.setText(reg.getApeMaterno());
            cbEspecialidadConsultas.setSelectedItem(reg.getEspecialidad());
            cajaExperienciaConsultas.setText(String.valueOf(reg.getAños()));
            
            cajaIndiceMed.setText(String.valueOf(indice + 1));
            cajaIndiceMed.setEnabled(false);
            posActual = indice;
            
            actualizarEstadoBotones();
        }
    }
    
    public void actualizarEstadoBotones(){
        
        if (posActual != 0 || listaMedicos.isEmpty()){
            btnPrimerRegMedicos.setEnabled(true);
        } else {
            btnPrimerRegMedicos.setEnabled(false);
        }
        
        btnAnteriorRegMedicos.setEnabled(posActual > 0);
        btnSiguienteRegMedicos.setEnabled(posActual < listaMedicos.size() - 1);
        btnUltimoRegMedicos.setEnabled(posActual < listaMedicos.size() - 1);
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
        rbNombres = new javax.swing.JRadioButton();
        rbPaterno = new javax.swing.JRadioButton();
        rbMaterno = new javax.swing.JRadioButton();
        rbEspecialidad = new javax.swing.JRadioButton();
        rbExperiencia = new javax.swing.JRadioButton();
        btnPrimerRegMedicos = new javax.swing.JButton();
        btnAnteriorRegMedicos = new javax.swing.JButton();
        btnSiguienteRegMedicos = new javax.swing.JButton();
        btnUltimoRegMedicos = new javax.swing.JButton();
        cajaIndiceMed = new javax.swing.JTextField();
        cajaNombreConsultas = new javax.swing.JTextField();
        cajaPaternoConsultas = new javax.swing.JTextField();
        cajaMaternoConsultas = new javax.swing.JTextField();
        cajaExperienciaConsultas = new javax.swing.JTextField();
        cbEspecialidadConsultas = new javax.swing.JComboBox<>();
        btnRestablecerMedConsultas = new javax.swing.JButton();
        btnBuscarMedConsultas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rbTodos.setText("Todos");
        rbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTodosActionPerformed(evt);
            }
        });

        rbNombres.setText("Nombre(s)");
        rbNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNombresActionPerformed(evt);
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

        rbEspecialidad.setText("Especialidad");
        rbEspecialidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEspecialidadActionPerformed(evt);
            }
        });

        rbExperiencia.setText("Años Experiencia");
        rbExperiencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbExperienciaActionPerformed(evt);
            }
        });

        btnPrimerRegMedicos.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\doble_flecha_izq.png")); // NOI18N
        btnPrimerRegMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerRegMedicosActionPerformed(evt);
            }
        });

        btnAnteriorRegMedicos.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\flecha_izq.png")); // NOI18N
        btnAnteriorRegMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorRegMedicosActionPerformed(evt);
            }
        });

        btnSiguienteRegMedicos.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\flecha_der.png")); // NOI18N
        btnSiguienteRegMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteRegMedicosActionPerformed(evt);
            }
        });

        btnUltimoRegMedicos.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\doble_flecha_der.png")); // NOI18N
        btnUltimoRegMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoRegMedicosActionPerformed(evt);
            }
        });

        cajaIndiceMed.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cajaIndiceMed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaIndiceMed.setText("0");

        cajaNombreConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaNombreConsultasKeyReleased(evt);
            }
        });

        cbEspecialidadConsultas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige Especialidad...", "Cardiología", "Pediatría", "Ginecología", "Medicina General", "Dermatología", "Neurología", "Oncología", "Oftalmología" }));

        btnRestablecerMedConsultas.setBackground(new java.awt.Color(0, 102, 255));
        btnRestablecerMedConsultas.setForeground(new java.awt.Color(0, 0, 0));
        btnRestablecerMedConsultas.setText("Restablecer");
        btnRestablecerMedConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestablecerMedConsultasActionPerformed(evt);
            }
        });

        btnBuscarMedConsultas.setBackground(new java.awt.Color(0, 102, 255));
        btnBuscarMedConsultas.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscarMedConsultas.setText("Buscar");
        btnBuscarMedConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMedConsultasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbExperiencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbNombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaMaternoConsultas)
                            .addComponent(cajaPaternoConsultas)
                            .addComponent(cajaNombreConsultas)
                            .addComponent(cajaExperienciaConsultas)
                            .addComponent(cbEspecialidadConsultas, 0, 184, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrimerRegMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAnteriorRegMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cajaIndiceMed, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSiguienteRegMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUltimoRegMedicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRestablecerMedConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuscarMedConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAnteriorRegMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(btnSiguienteRegMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(btnUltimoRegMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(rbTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrimerRegMedicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cajaIndiceMed, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaNombreConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(rbNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaPaternoConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(rbPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaMaternoConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rbEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cbEspecialidadConsultas))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbExperiencia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaExperienciaConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRestablecerMedConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarMedConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTodosActionPerformed
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
    }//GEN-LAST:event_rbTodosActionPerformed

    private void rbNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNombresActionPerformed
        cajaNombreConsultas.setEnabled(true);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
    }//GEN-LAST:event_rbNombresActionPerformed

    private void rbPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPaternoActionPerformed
       
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(true);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
    }//GEN-LAST:event_rbPaternoActionPerformed

    private void rbMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMaternoActionPerformed
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(true);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(false);
    }//GEN-LAST:event_rbMaternoActionPerformed

    private void rbEspecialidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEspecialidadActionPerformed
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(true);
        cajaExperienciaConsultas.setEnabled(false);
    }//GEN-LAST:event_rbEspecialidadActionPerformed

    private void rbExperienciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbExperienciaActionPerformed
        
        cajaNombreConsultas.setEnabled(false);
        cajaPaternoConsultas.setEnabled(false);
        cajaMaternoConsultas.setEnabled(false);
        cbEspecialidadConsultas.setEnabled(false);
        cajaExperienciaConsultas.setEnabled(true);
    }//GEN-LAST:event_rbExperienciaActionPerformed

    private void btnBuscarMedConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMedConsultasActionPerformed
        //Validacion de los RdioButtons
        /*
        if (!algunaOpcionSeleccionada()) {
                    JOptionPane.showMessageDialog(this, "¡Selecciona alguna opción de búsqueda!");
                    return;
                }
            */
            if (rbNombres.isSelected()){
                String nombre = cajaNombreConsultas.getText();
                if (nombre.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Asegurate de llenar el campo 'Nombre(s)'");
                    return;
                }
                if (nombre.length() > 11){
                    JOptionPane.showMessageDialog(this, "El campo 'Nombre(s)' no debe exceder los 11 caracteres");
                    return;
                }
                    
            }
            
            if (rbPaterno.isSelected()){
                String apPaterno = cajaPaternoConsultas.getText();
                if (apPaterno.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Asegurate de llenar el campo 'Apellido Paterno'");
                    return;
                }
            }
            
            if (rbMaterno.isSelected()){
                String apMaterno = cajaMaternoConsultas.getText();
                if (apMaterno.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Asegurate de llenar el campo 'Apellido Materno'");
                    return;
                }
            }
            
            if (rbEspecialidad.isSelected() && cbEspecialidadConsultas.getSelectedItem().toString().equals("Elige Especialidad...")){
                JOptionPane.showMessageDialog(this, "Asegurate de Seleccionar una Especialidad valida");
                return;
            }
            
            if (rbExperiencia.isSelected()){
                String experiencia = cajaExperienciaConsultas.getText();
                if (experiencia.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Asegurate de llenar el campo 'Años Experiencia'");
                    return;
                }
            }
            
            
            actualizarTablaFiltro(tablaRegMedicos);
    }//GEN-LAST:event_btnBuscarMedConsultasActionPerformed

    private void btnRestablecerMedConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestablecerMedConsultasActionPerformed
        
        limpiarCampos();
        rbTodos.setSelected(true);
        posActual = -1;
        actualizarEstadoBotones();
    }//GEN-LAST:event_btnRestablecerMedConsultasActionPerformed

    private void btnPrimerRegMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerRegMedicosActionPerformed
        
        if (!listaMedicos.isEmpty()) {
                mostrarRegistros(0);
            }
    }//GEN-LAST:event_btnPrimerRegMedicosActionPerformed

    private void btnAnteriorRegMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorRegMedicosActionPerformed
        
        if (posActual > 0) {
                mostrarRegistros(posActual - 1);
            }
    }//GEN-LAST:event_btnAnteriorRegMedicosActionPerformed

    private void btnSiguienteRegMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteRegMedicosActionPerformed
        
        if (posActual < listaMedicos.size() - 1) {
                mostrarRegistros(posActual + 1);
            }
    }//GEN-LAST:event_btnSiguienteRegMedicosActionPerformed

    private void btnUltimoRegMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoRegMedicosActionPerformed
        
        if (!listaMedicos.isEmpty()) {
                mostrarRegistros(listaMedicos.size() - 1);
            }
    }//GEN-LAST:event_btnUltimoRegMedicosActionPerformed

    private void cajaNombreConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaNombreConsultasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaNombreConsultasKeyReleased

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
                Dg_MedicosConsultas dialog = new Dg_MedicosConsultas(new javax.swing.JFrame(), true, tablaDummy);
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
    private javax.swing.JButton btnAnteriorRegMedicos;
    private javax.swing.JButton btnBuscarMedConsultas;
    private javax.swing.JButton btnPrimerRegMedicos;
    private javax.swing.JButton btnRestablecerMedConsultas;
    private javax.swing.JButton btnSiguienteRegMedicos;
    private javax.swing.JButton btnUltimoRegMedicos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cajaExperienciaConsultas;
    private javax.swing.JTextField cajaIndiceMed;
    private javax.swing.JTextField cajaMaternoConsultas;
    private javax.swing.JTextField cajaNombreConsultas;
    private javax.swing.JTextField cajaPaternoConsultas;
    private javax.swing.JComboBox<String> cbEspecialidadConsultas;
    private javax.swing.JRadioButton rbEspecialidad;
    private javax.swing.JRadioButton rbExperiencia;
    private javax.swing.JRadioButton rbMaterno;
    private javax.swing.JRadioButton rbNombres;
    private javax.swing.JRadioButton rbPaterno;
    private javax.swing.JRadioButton rbTodos;
    // End of variables declaration//GEN-END:variables
}
