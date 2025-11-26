/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Ventanas;

import ConexionBD.ConexionBD;
import Controlador.PacienteDAO;
import Modelo.Paciente;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author erick
 */
public class Dg_PacientesCambios extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dg_PacientesCambios.class.getName());

    /**
     * Creates new form Dg_PacientesCambios
     */
    PacienteDAO pacienteDAO = PacienteDAO.getInstancia();
    ConexionBD conexionBD = ConexionBD.getInstancia();
    
    private javax.swing.JTable tablaRegPacientes;
    public Dg_PacientesCambios(java.awt.Frame parent, boolean modal, javax.swing.JTable tablaRegPacientes) {
        super(parent, modal);
        this.tablaRegPacientes = tablaRegPacientes;
        initComponents();
        setTitle("Modificar Paciente");  
        setSize(380, 620);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        
        cajaNombreCambios.setEnabled(false);
        cajaPaternoCambios.setEnabled(false);
        cajaMaternoCambios.setEnabled(false);
        spEdadCambios.setEnabled(false);
        cbSSNMedicoCambios.setEnabled(false);
        cajaCalleCambios.setEnabled(false);
        cajaNumCambios.setEnabled(false);
        cajaColoniaCambios.setEnabled(false);
        cajaCodPostalCambios.setEnabled(false);
        
    }
    
    
    public void obtenerDatosPaciente() {

    String sql = "SELECT * FROM Pacientes WHERE SSN LIKE ?";
    String texto = cajaSSNCambios.getText().trim() + "%";

    ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, texto);

    // Actualizar tabla según coincidencias
    pacienteDAO.actualizarTablaFiltrada(cajaSSNCambios.getText(), texto);
    
    try {
        if (rs != null && rs.next()) {
            // Mostrar datos del primero, si coincide
            cajaNombreCambios.setText(rs.getString("Nombre"));
            cajaPaternoCambios.setText(rs.getString("Ape_Paterno"));
            cajaMaternoCambios.setText(rs.getString("Ape_Materno"));
            spEdadCambios.setValue(rs.getInt("Edad"));
            //cbSSNMedicoCambios.setSelectedItem(rs.getString("SSN_Medico_Cabecera"));
            cajaCalleCambios.setText(rs.getString("Calle"));
            cajaNumCambios.setText(String.valueOf(rs.getInt("Numero")));
            cajaColoniaCambios.setText(rs.getString("Colonia"));
            cajaCodPostalCambios.setText(rs.getString("Codigo_Postal"));
            
            String ssnMedico = rs.getString("SSN_Medico_Cabecera");

            // Primero carga todos los médicos
            cargarMedicosEnCombo();

            // Luego selecciona el correcto
            cbSSNMedicoCambios.setSelectedItem(ssnMedico);
            
            habilitarCamposEdicion(true);
            
        } else {
            // No hay coincidencias → limpiar
            cajaNombreCambios.setText("");
            cajaPaternoCambios.setText("");
            cajaMaternoCambios.setText("");
            spEdadCambios.setValue(0);

            // ⭐ IMPORTANTE: solo limpiar si el combo NO está vacío
            if (cbSSNMedicoCambios.getItemCount() > 0) {
                cbSSNMedicoCambios.setSelectedIndex(0);
            }

            cajaCalleCambios.setText("");
            cajaNumCambios.setText("");
            cajaColoniaCambios.setText("");
            cajaCodPostalCambios.setText("");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener los datos del Paciente.");
    }
}
    
    public void habilitarCamposEdicion(boolean habilitar){
        
        cajaNombreCambios.setEnabled(habilitar);
        cajaPaternoCambios.setEnabled(habilitar);
        cajaMaternoCambios.setEnabled(habilitar);
        spEdadCambios.setEnabled(habilitar);
        cbSSNMedicoCambios.setEnabled(false);
        cajaCalleCambios.setEnabled(habilitar);
        cajaNumCambios.setEnabled(habilitar);
        cajaColoniaCambios.setEnabled(habilitar);
        cajaCodPostalCambios.setEnabled(habilitar);
    }
    
    
    public void cargarMedicosEnCombo() {
    cbSSNMedicoCambios.removeAllItems(); 

    ResultSet rs = pacienteDAO.obtenerTodosLosMedicos();

    try {
        while (rs.next()) {
            String ssn = rs.getString("SSN");
            
            cbSSNMedicoCambios.addItem(ssn);
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar médicos");
    }
}
    
    
    public void limpiarCampos(){
        /*
        if (cbSSNMedicoCambios.getItemCount() > 0) {
                cbSSNMedicoCambios.setSelectedIndex(0);
            }
        */
        cbSSNMedicoCambios.setSelectedIndex(-1);
        cajaSSNCambios.setText("");
        cajaNombreCambios.setText("");
        cajaPaternoCambios.setText("");
        cajaMaternoCambios.setText("");
        spEdadCambios.setValue(0);
        cajaCalleCambios.setText("");
        cajaNumCambios.setText("");
        cajaColoniaCambios.setText("");
        cajaCodPostalCambios.setText("");
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cajaSSNCambios = new javax.swing.JTextField();
        cajaNombreCambios = new javax.swing.JTextField();
        cajaPaternoCambios = new javax.swing.JTextField();
        cajaMaternoCambios = new javax.swing.JTextField();
        spEdadCambios = new javax.swing.JSpinner();
        cajaCalleCambios = new javax.swing.JTextField();
        cbSSNMedicoCambios = new javax.swing.JComboBox<>();
        cajaNumCambios = new javax.swing.JTextField();
        cajaColoniaCambios = new javax.swing.JTextField();
        cajaCodPostalCambios = new javax.swing.JTextField();
        btnRestablecerPacCambios = new javax.swing.JButton();
        btnEditarPacCambios = new javax.swing.JButton();
        btnBuscarCambios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("SSN");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido Paterno");

        jLabel4.setText("Apellido Materno");

        jLabel5.setText("Edad");

        jLabel6.setText("SSN Medico");

        jLabel7.setText("Calle");

        jLabel8.setText("Numero");

        jLabel9.setText("Colonia");

        jLabel10.setText("Codigo Postal");

        cajaSSNCambios.setBackground(new java.awt.Color(51, 153, 255));
        cajaSSNCambios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaSSNCambiosKeyReleased(evt);
            }
        });

        btnRestablecerPacCambios.setBackground(new java.awt.Color(255, 153, 0));
        btnRestablecerPacCambios.setForeground(new java.awt.Color(0, 0, 0));
        btnRestablecerPacCambios.setText("Restablecer");
        btnRestablecerPacCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestablecerPacCambiosActionPerformed(evt);
            }
        });

        btnEditarPacCambios.setBackground(new java.awt.Color(255, 153, 0));
        btnEditarPacCambios.setForeground(new java.awt.Color(0, 0, 0));
        btnEditarPacCambios.setText("Editar");
        btnEditarPacCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPacCambiosActionPerformed(evt);
            }
        });

        btnBuscarCambios.setText("Buscar");
        btnBuscarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRestablecerPacCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditarPacCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaCalleCambios)
                            .addComponent(cajaNombreCambios)
                            .addComponent(cajaPaternoCambios)
                            .addComponent(cajaMaternoCambios)
                            .addComponent(spEdadCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSSNMedicoCambios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cajaNumCambios)
                            .addComponent(cajaColoniaCambios)
                            .addComponent(cajaCodPostalCambios)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cajaSSNCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaSSNCambios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaNombreCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaPaternoCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaMaternoCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spEdadCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(cbSSNMedicoCambios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaCalleCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaNumCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaColoniaCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaCodPostalCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRestablecerPacCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarPacCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRestablecerPacCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestablecerPacCambiosActionPerformed
        
        
        habilitarCamposEdicion(false);
            if (cajaSSNCambios.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"No hay datos para borrar");
            }
            limpiarCampos();
            ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN", cajaSSNCambios.getText());
        tablaRegPacientes.setModel(modelo);
        
    }//GEN-LAST:event_btnRestablecerPacCambiosActionPerformed

    private void btnEditarPacCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPacCambiosActionPerformed
        
        Paciente p = new Paciente(cajaSSNCambios.getText(),
                cajaNombreCambios.getText(),
                cajaPaternoCambios.getText(),
                cajaMaternoCambios.getText(),
                Byte.parseByte(spEdadCambios.getValue().toString()),
                cbSSNMedicoCambios.getSelectedItem().toString(),
                cajaCalleCambios.getText(),
                Integer.parseInt(cajaNumCambios.getText()),
                cajaColoniaCambios.getText(),
                Integer.parseInt(cajaCodPostalCambios.getText()));
        
        if (pacienteDAO.editarPaciente(p)) {
                JOptionPane.showMessageDialog(this,"Registro Editado CORRECTAMENTE");
                System.out.println("Registro Agregado CORRECTAMENTE");
                
                pacienteDAO.actualizarTabla(tablaRegPacientes);
        }else{
                    JOptionPane.showMessageDialog(this,"Error en la insercion");
                    System.out.println("ERROR en la insercion");
             }
    }//GEN-LAST:event_btnEditarPacCambiosActionPerformed

    private void btnBuscarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCambiosActionPerformed
        
         if (cajaSSNCambios.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Campo vacio, verifica campo 'SSN'");
            } else {

                obtenerDatosPaciente();
                //cargarMedicosEnCombo();
            }
        
    }//GEN-LAST:event_btnBuscarCambiosActionPerformed

    private void cajaSSNCambiosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaSSNCambiosKeyReleased
        
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN", cajaSSNCambios.getText());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_cajaSSNCambiosKeyReleased

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
                Dg_PacientesCambios dialog = new Dg_PacientesCambios(new javax.swing.JFrame(), true,tablaDummy);
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
    private javax.swing.JButton btnBuscarCambios;
    private javax.swing.JButton btnEditarPacCambios;
    private javax.swing.JButton btnRestablecerPacCambios;
    private javax.swing.JTextField cajaCalleCambios;
    private javax.swing.JTextField cajaCodPostalCambios;
    private javax.swing.JTextField cajaColoniaCambios;
    private javax.swing.JTextField cajaMaternoCambios;
    private javax.swing.JTextField cajaNombreCambios;
    private javax.swing.JTextField cajaNumCambios;
    private javax.swing.JTextField cajaPaternoCambios;
    private javax.swing.JTextField cajaSSNCambios;
    private javax.swing.JComboBox<String> cbSSNMedicoCambios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner spEdadCambios;
    // End of variables declaration//GEN-END:variables
}
