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
import java.util.ArrayList;
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
    ArrayList<Paciente> listaPacientes = new ArrayList<>();
    int posActual = -1;
    public Dg_PacientesConsultas(java.awt.Frame parent, boolean modal, javax.swing.JTable tablaRegPacientes) {
        super(parent, modal);
        this.tablaRegPacientes = tablaRegPacientes;
        initComponents();
        setTitle("Consultar Paciente");  
        setSize(380, 620);           
        setLocationRelativeTo(null);  
        setResizable(false); 
        
        //cargarMedicosEnCombo();
        
        
        buttonGroup1.add(rbNombre);
        buttonGroup1.add(rbPaterno);
        buttonGroup1.add(rbMaterno);
        buttonGroup1.add(rbEdad);
        buttonGroup1.add(rbSSNMedico);
        buttonGroup1.add(rbCalle);
        buttonGroup1.add(rbNumero);
        buttonGroup1.add(rbColonia);
        buttonGroup1.add(rbCodPostal);
        
       
        desactivarCampos();
        listaPacientes = añadirPacientes();
    }
    

    
    /*
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
    */
    
    public ArrayList<Paciente> añadirPacientes(){
        
        cargarMedicosEnCombo();
        String sql = "SELECT * FROM Pacientes";
        ResultSet rs = null;
        
        try {
            rs = ConexionBD.getInstancia().ejecutarConsultaSQL(sql);
            
            if (rs != null && rs.next()){
                do{
                    String ssn = rs.getString("SSN");
                    String nom = rs.getString("Nombre");
                    String app = rs.getString("Ape_Paterno");
                    String apm = rs.getString("Ape_Materno");
                    Byte edad = rs.getByte("Edad");
                    String ssnMedico = rs.getString("SSN_Medico_Cabecera");
                    String calle = rs.getString("Calle");
                    int num = rs.getInt("Numero");
                    String col = rs.getString("Colonia");
                    int codPostal = rs.getInt("Codigo_Postal");
                    
                    
                    Paciente p = new Paciente(ssn, nom, app, apm, edad, ssnMedico, calle, num, col, codPostal);
                    listaPacientes.add(p);
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
        return listaPacientes;
    }
    
    
    
    public void mostrarRegistros(int indice){
        if (indice >= 0 && indice < listaPacientes.size()){
            Paciente reg = listaPacientes.get(indice);
            
           
            cajaNombreConsultas.setText(reg.getNombre());
            cajaPaternoConsultas.setText(reg.getApePaterno());
            cajaMaternoConsultas.setText(reg.getApeMaterno());
            spEdadConsultas.setValue(reg.getEdad());
            cbSSNMedicoConsultas.setSelectedItem(reg.getSsnMedicoCabecera());
            cajaCalleConsultas.setText(reg.getCalle());
            cajaNumeroConsultas.setText(String.valueOf(reg.getNumero()));
            cajaColoniaConsultas.setText(reg.getColonia());
            cajaCodPostalConsultas.setText(String.valueOf(reg.getCodigoPostal()));
            
            cajaIndicePac.setText(String.valueOf(indice + 1));
            cajaIndicePac.setEnabled(false);
            posActual = indice;
            
            actualizarEstadoBotones();
        }
    }
    
    
    
    
    public void actualizarEstadoBotones(){
        
        if (posActual != 0 || listaPacientes.isEmpty()){
            btnPrimerRegPacientes.setEnabled(true);
        } else {
            btnPrimerRegPacientes.setEnabled(false);
        }
        
        btnAnteriorRegPacientes.setEnabled(posActual > 0);
        btnSiguienteRegPacientes.setEnabled(posActual < listaPacientes.size() - 1);
        btnUltimoRegPacientes.setEnabled(posActual < listaPacientes.size() - 1);
    }
    
    
    
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
        cajaIndicePac.setText("0");
        actualizarTablaFiltro(tablaRegPacientes);
        
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
        btnPrimerRegPacientes = new javax.swing.JButton();
        btnAnteriorRegPacientes = new javax.swing.JButton();
        cajaIndicePac = new javax.swing.JTextField();
        btnSiguienteRegPacientes = new javax.swing.JButton();
        btnUltimoRegPacientes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        btnRestablecerPacConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestablecerPacConsultasActionPerformed(evt);
            }
        });

        btnPrimerRegPacientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\doble_flecha_izq.png")); // NOI18N
        btnPrimerRegPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerRegPacientesActionPerformed(evt);
            }
        });

        btnAnteriorRegPacientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\flecha_izq.png")); // NOI18N
        btnAnteriorRegPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorRegPacientesActionPerformed(evt);
            }
        });

        cajaIndicePac.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cajaIndicePac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cajaIndicePac.setText("0");

        btnSiguienteRegPacientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\flecha_der.png")); // NOI18N
        btnSiguienteRegPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteRegPacientesActionPerformed(evt);
            }
        });

        btnUltimoRegPacientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\erick\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal_FARMACIAS\\src\\main\\java\\img\\doble_flecha_der.png")); // NOI18N
        btnUltimoRegPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoRegPacientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnPrimerRegPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnteriorRegPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cajaIndicePac, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSiguienteRegPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUltimoRegPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
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
                            .addComponent(cajaCodPostalConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(btnRestablecerPacConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAnteriorRegPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaIndicePac, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnUltimoRegPacientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSiguienteRegPacientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPrimerRegPacientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        limpiarCampos();
        desactivarCampos();

        cajaCalleConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbCalleActionPerformed

    private void rbNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNumeroActionPerformed
        limpiarCampos();
        desactivarCampos();

        cajaNumeroConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbNumeroActionPerformed

    private void rbColoniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbColoniaActionPerformed
        limpiarCampos();
        desactivarCampos();

        cajaColoniaConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
    }//GEN-LAST:event_rbColoniaActionPerformed

    private void rbCodPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodPostalActionPerformed
        limpiarCampos();
        desactivarCampos();

        cajaCodPostalConsultas.setEnabled(true);

        actualizarTablaFiltro(tablaRegPacientes);
        
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
       // byte edad = (byte) spEdadConsultas.getValue(); 
        ResultSetTableModel modelo = pacienteDAO.actualizarTablaFiltrada("Edad", spEdadConsultas.getValue().toString());
        tablaRegPacientes.setModel(modelo);
    }//GEN-LAST:event_spEdadConsultasStateChanged

    private void cbSSNMedicoConsultasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSSNMedicoConsultasItemStateChanged
 
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

    private void btnRestablecerPacConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestablecerPacConsultasActionPerformed
        limpiarCampos();
        desactivarCampos();
        posActual = -1;
        actualizarEstadoBotones();
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_btnRestablecerPacConsultasActionPerformed

    private void btnPrimerRegPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerRegPacientesActionPerformed
       if (!listaPacientes.isEmpty()) {
                mostrarRegistros(0);
            }
               
    }//GEN-LAST:event_btnPrimerRegPacientesActionPerformed

    private void btnAnteriorRegPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorRegPacientesActionPerformed
        if (posActual > 0) {
                mostrarRegistros(posActual - 1);
            }
    }//GEN-LAST:event_btnAnteriorRegPacientesActionPerformed

    private void btnSiguienteRegPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteRegPacientesActionPerformed
        if (posActual < listaPacientes.size() - 1) {
                mostrarRegistros(posActual + 1);
            }
    }//GEN-LAST:event_btnSiguienteRegPacientesActionPerformed

    private void btnUltimoRegPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoRegPacientesActionPerformed
        if (!listaPacientes.isEmpty()) {
                mostrarRegistros(listaPacientes.size() - 1);
            }
    }//GEN-LAST:event_btnUltimoRegPacientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

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
    private javax.swing.JButton btnAnteriorRegPacientes;
    private javax.swing.JButton btnPrimerRegPacientes;
    private javax.swing.JButton btnRestablecerPacConsultas;
    private javax.swing.JButton btnSiguienteRegPacientes;
    private javax.swing.JButton btnUltimoRegPacientes;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cajaCalleConsultas;
    private javax.swing.JTextField cajaCodPostalConsultas;
    private javax.swing.JTextField cajaColoniaConsultas;
    private javax.swing.JTextField cajaIndicePac;
    private javax.swing.JTextField cajaMaternoConsultas;
    private javax.swing.JTextField cajaNombreConsultas;
    private javax.swing.JTextField cajaNumeroConsultas;
    private javax.swing.JTextField cajaPaternoConsultas;
    private javax.swing.JComboBox<String> cbSSNMedicoConsultas;
    private javax.swing.JRadioButton rbCalle;
    private javax.swing.JRadioButton rbCodPostal;
    private javax.swing.JRadioButton rbColonia;
    private javax.swing.JRadioButton rbEdad;
    private javax.swing.JRadioButton rbMaterno;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JRadioButton rbNumero;
    private javax.swing.JRadioButton rbPaterno;
    private javax.swing.JRadioButton rbSSNMedico;
    private javax.swing.JSpinner spEdadConsultas;
    // End of variables declaration//GEN-END:variables
}
