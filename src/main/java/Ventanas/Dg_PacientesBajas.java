/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Ventanas;

import ConexionBD.ConexionBD;
import Controlador.PacienteDAO;
import Modelo.ResultSetTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author erick
 */
public class Dg_PacientesBajas extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dg_PacientesBajas.class.getName());

    /**
     * Creates new form Dg_PacientesBajas
     */
    
    PacienteDAO pacienteDAO = PacienteDAO.getInstancia();
    ConexionBD conexionBD = ConexionBD.getInstancia();
    
    private javax.swing.JTable tablaRegPacientes;
    public Dg_PacientesBajas(java.awt.Frame parent, boolean modal, javax.swing.JTable tablaRegPacientes) {
        super(parent, modal);
        this.tablaRegPacientes = tablaRegPacientes;
        initComponents();
        setTitle("Bajas Paciente");  
        setSize(380, 620);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        
        //cargarMedicosEnCombo();
        
        cajaNombreBajas.setEnabled(false);
        cajaPaternoBajas.setEnabled(false);
        cajaMaternoBajas.setEnabled(false);
        spEdadBajas.setEnabled(false);
        cbSSNMedicoBajas.setEnabled(false);
        cajaCalleBajas.setEnabled(false);
        cajaNumBajas.setEnabled(false);
        cajaColoniaBajas.setEnabled(false);
        cajaCodPostalBajas.setEnabled(false);
        
        
    }
    
    public void cargarMedicosEnCombo() {
    cbSSNMedicoBajas.removeAllItems();  

    ResultSet rs = pacienteDAO.obtenerTodosLosMedicos();

    try {
        while (rs.next()) {
            String ssn = rs.getString("SSN");
            
            cbSSNMedicoBajas.addItem(ssn);
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar médicos");
    }
}
    
    public void limpiarCampos(){
        /*
        if (cbSSNMedicoBajas.getItemCount() > 0) {
                cbSSNMedicoBajas.setSelectedIndex(0);
            }
        */
        cbSSNMedicoBajas.setSelectedIndex(-1); // sin selección

        cajaSSNBajas.setText("");
        cajaNombreBajas.setText("");
        cajaPaternoBajas.setText("");
        cajaMaternoBajas.setText("");
        spEdadBajas.setValue(0);
        cajaCalleBajas.setText("");
        cajaNumBajas.setText("");
        cajaColoniaBajas.setText("");
        cajaCodPostalBajas.setText("");
        
        
    }
    
    
    public void obtenerDatosPaciente() {

    String sql = "SELECT * FROM Pacientes WHERE SSN LIKE ?";
    String texto = cajaSSNBajas.getText().trim() + "%";

    ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, texto);

    // Actualizar tabla según coincidencias
    pacienteDAO.actualizarTablaFiltrada(cajaSSNBajas.getText(), texto);
    //"INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) "
    try {
        if (rs != null && rs.next()) {
            // Mostrar datos del primero, si coincide
            cajaNombreBajas.setText(rs.getString("Nombre"));
            cajaPaternoBajas.setText(rs.getString("Ape_Paterno"));
            cajaMaternoBajas.setText(rs.getString("Ape_Materno"));
            spEdadBajas.setValue(rs.getInt("Edad"));
            //cbSSNMedicoBajas.setSelectedItem(rs.getString("SSN_Medico_Cabecera"));
            cajaCalleBajas.setText(rs.getString("Calle"));
            cajaNumBajas.setText(String.valueOf(rs.getInt("Numero")));
            cajaColoniaBajas.setText(rs.getString("Colonia"));
            cajaCodPostalBajas.setText(rs.getString("Codigo_Postal"));
            
            String ssnMedico = rs.getString("SSN_Medico_Cabecera");

            // Primero carga todos los médicos
            cargarMedicosEnCombo();

            // Luego selecciona el correcto
            cbSSNMedicoBajas.setSelectedItem(ssnMedico);
            
        } else {
            // No hay coincidencias → limpiar
            cajaNombreBajas.setText("");
            cajaPaternoBajas.setText("");
            cajaMaternoBajas.setText("");
            spEdadBajas.setValue(0);

            // ⭐ IMPORTANTE: solo limpiar si el combo NO está vacío
            if (cbSSNMedicoBajas.getItemCount() > 0) {
                cbSSNMedicoBajas.setSelectedIndex(0);
            }

            cajaCalleBajas.setText("");
            cajaNumBajas.setText("");
            cajaColoniaBajas.setText("");
            cajaCodPostalBajas.setText("");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener los datos del Paciente.");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cajaNombreBajas = new javax.swing.JTextField();
        cajaPaternoBajas = new javax.swing.JTextField();
        cajaMaternoBajas = new javax.swing.JTextField();
        spEdadBajas = new javax.swing.JSpinner();
        cbSSNMedicoBajas = new javax.swing.JComboBox<>();
        cajaCalleBajas = new javax.swing.JTextField();
        cajaNumBajas = new javax.swing.JTextField();
        cajaColoniaBajas = new javax.swing.JTextField();
        cajaCodPostalBajas = new javax.swing.JTextField();
        btnRestablecerPacBajas = new javax.swing.JButton();
        btnEliminarPacBajas = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cajaSSNBajas = new javax.swing.JTextField();
        btnBuscarBajas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("SSN");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido Paterno");

        jLabel4.setText("Apellido Materno");

        jLabel5.setText("Edad");

        btnRestablecerPacBajas.setBackground(new java.awt.Color(255, 51, 51));
        btnRestablecerPacBajas.setForeground(new java.awt.Color(0, 0, 0));
        btnRestablecerPacBajas.setText("Restablecer");
        btnRestablecerPacBajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestablecerPacBajasActionPerformed(evt);
            }
        });

        btnEliminarPacBajas.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminarPacBajas.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminarPacBajas.setText("Eliminar");
        btnEliminarPacBajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPacBajasActionPerformed(evt);
            }
        });

        jLabel6.setText("SSN Medico");

        jLabel8.setText("Calle");

        jLabel9.setText("Numero");

        jLabel10.setText("Colonia");

        jLabel11.setText("Codigo Postal");

        cajaSSNBajas.setBackground(new java.awt.Color(51, 153, 255));
        cajaSSNBajas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cajaSSNBajasKeyReleased(evt);
            }
        });

        btnBuscarBajas.setText("Buscar");
        btnBuscarBajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarBajasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRestablecerPacBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(btnEliminarPacBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(48, 48, 48)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cajaMaternoBajas)
                                .addComponent(cajaCodPostalBajas)
                                .addComponent(cajaColoniaBajas)
                                .addComponent(cajaNumBajas)
                                .addComponent(cajaCalleBajas)
                                .addComponent(cbSSNMedicoBajas, 0, 200, Short.MAX_VALUE)
                                .addComponent(spEdadBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(48, 48, 48)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cajaPaternoBajas)
                                .addComponent(cajaNombreBajas)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(cajaSSNBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBuscarBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarBajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaSSNBajas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaNombreBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaPaternoBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaMaternoBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spEdadBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSSNMedicoBajas, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaCalleBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaNumBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaColoniaBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaCodPostalBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRestablecerPacBajas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarPacBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRestablecerPacBajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestablecerPacBajasActionPerformed
        
        limpiarCampos();
        //cargarMedicosEnCombo();
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN", cajaSSNBajas.getText());
        tablaRegPacientes.setModel(modelo);
        
    }//GEN-LAST:event_btnRestablecerPacBajasActionPerformed

    private void btnBuscarBajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarBajasActionPerformed
        
        
        if (cajaSSNBajas.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Campo vacio, verifica el campo 'SSN'");
            }
            try {
                obtenerDatosPaciente();
                //cargarMedicosEnCombo();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,"Campo vacio, verifica los datos");
            }
        
    }//GEN-LAST:event_btnBuscarBajasActionPerformed

    private void cajaSSNBajasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaSSNBajasKeyReleased
        
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("SSN", cajaSSNBajas.getText());
        tablaRegPacientes.setModel(modelo);
        
    }//GEN-LAST:event_cajaSSNBajasKeyReleased

    private void btnEliminarPacBajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPacBajasActionPerformed
        
          if (pacienteDAO.eliminarPaciente(cajaSSNBajas.getText())){

                pacienteDAO.actualizarTabla(tablaRegPacientes);
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
            }else {
                JOptionPane.showMessageDialog(this, "ERROR al eliminar el registro");
            }
        
    }//GEN-LAST:event_btnEliminarPacBajasActionPerformed

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
                Dg_PacientesBajas dialog = new Dg_PacientesBajas(new javax.swing.JFrame(), true, tablaDummy);
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
    private javax.swing.JButton btnBuscarBajas;
    private javax.swing.JButton btnEliminarPacBajas;
    private javax.swing.JButton btnRestablecerPacBajas;
    private javax.swing.JTextField cajaCalleBajas;
    private javax.swing.JTextField cajaCodPostalBajas;
    private javax.swing.JTextField cajaColoniaBajas;
    private javax.swing.JTextField cajaMaternoBajas;
    private javax.swing.JTextField cajaNombreBajas;
    private javax.swing.JTextField cajaNumBajas;
    private javax.swing.JTextField cajaPaternoBajas;
    private javax.swing.JTextField cajaSSNBajas;
    private javax.swing.JComboBox<String> cbSSNMedicoBajas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner spEdadBajas;
    // End of variables declaration//GEN-END:variables
}
