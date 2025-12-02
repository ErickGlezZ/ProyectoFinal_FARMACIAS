/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import ConexionBD.ConexionBD;
import Modelo.Medico;
import Modelo.ResultSetTableModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author erick
 */
public class MedicoDAO {
    
    private static MedicoDAO instancia;
    
    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private MedicoDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    
    // Método público para obtener la instancia única
    public static MedicoDAO getInstancia() {
        if (instancia == null) {
            instancia = new MedicoDAO();
        }
        return instancia;
    }
    
    
    public void actualizarTabla(JTable tabla) {

        JOptionPane loading = new JOptionPane("Consultando datos...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = loading.createDialog("Espere");
        dialog.setModal(false);
        dialog.setVisible(true);


        new Thread(() -> {
            final String CONTROLADOR_JDBC = conexionBD.getDriver();
            final String URL = conexionBD.getURL();
            final String CONSULTA = "SELECT * FROM medicos";

            try {

                Thread.sleep(1000); // 1 segundos

                ResultSetTableModel modelo = new ResultSetTableModel(CONTROLADOR_JDBC, URL, CONSULTA);

                SwingUtilities.invokeLater(() -> {
                    tabla.setModel(modelo);
                    dialog.dispose(); // Oculta mensaje cuando termine

                    if (modelo.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No se encontraron registros con ese valor.");
                        return;
                    }

                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                dialog.dispose();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                dialog.dispose();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null, "Error al consultar la base de datos.")
                );
            }
        }).start();
    }
    
    
    //============================ALTAS=====================
    /*
    public boolean agregarMedico(Medico medico){
        String sql = "INSERT INTO Medicos (SSN, Nombre, Ape_Paterno, Ape_Materno, Especialidad, Años_Experiencia) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        
        return conexionBD.ejecutarInstruccionLMD(sql, 
                medico.getSsn(),
                medico.getNombre(),
                medico.getApePaterno(),
                medico.getApeMaterno(),
                medico.getEspecialidad(),
                medico.getAños());
    }
    */
    
    public boolean agregarMedico(Medico medico) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            con = conexionBD.getConexion(); 

            cs = con.prepareCall("{ call sp_insertar_medico(?, ?, ?, ?, ?, ?) }");
            cs.setString(1, medico.getSsn());
            cs.setString(2, medico.getNombre());
            cs.setString(3, medico.getApePaterno());
            cs.setString(4, medico.getApeMaterno());
            cs.setString(5, medico.getEspecialidad());
            cs.setInt(6, medico.getAños());

            cs.execute();
            return true;

        } catch (SQLException ex) {

            String msg = ex.getMessage();

            if (msg.contains("ORA-00001")) {
                JOptionPane.showMessageDialog(null,
                        "Error: Ya existe un médico con ese SSN.",
                        "SSN duplicado", JOptionPane.WARNING_MESSAGE);
            }
            else if (msg.contains("ORA-20020")) {
                JOptionPane.showMessageDialog(null,
                        "Error desde el procedimiento: El SSN del Medico ya existe.",
                        "SSN duplicado", JOptionPane.WARNING_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Error al insertar el médico:\n" + msg,
                        "Error SQL", JOptionPane.ERROR_MESSAGE);
            }

        return false;

        } finally {
            try { if (cs != null) cs.close(); } catch (Exception e) {}
            
        }
}

    
    
    
    
    public boolean existeMedico(String SSN) {
        String sql = "SELECT SSN FROM Medicos WHERE SSN = ?";
        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, SSN.trim());
        try {
            return rs != null && rs.next(); // Ya existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    


    
    
    //==========================BAJAS==========================
    public boolean eliminarMedico(String SSN){
        String sql = "DELETE FROM Medicos WHERE SSN = ?";
        return conexionBD.ejecutarInstruccionLMD(sql, SSN);
    }
    /*
    public ResultSetTableModel actualizarTablaFiltrada(String campo, String valor){
    String consulta = "SELECT * FROM Medicos WHERE " + campo + " LIKE ?";
    try {
        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                "%" + valor + "%"  
        );
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Error al obtener medicos filtrados", e);
    }
    
    }
    */
    public ResultSetTableModel actualizarTablaFiltrada(String campo, String valor){
    String consulta = "SELECT * FROM Medicos WHERE LOWER(" + campo + ") LIKE ?";
    try {
        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                "%" + valor.toLowerCase() + "%"  
        );
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Error al obtener medicos filtrados", e);
    }
}
    
    public int contarPacientesDeMedico(String ssnMedico) {
    String sql = "SELECT COUNT(*) AS total FROM pacientes WHERE ssn_medico_cabecera = ?";
    ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, ssnMedico);

    try {
        if (rs.next()) {
            return rs.getInt("total");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


    //=====================CAMBIOS==========================
    public boolean editarMedico(Medico medico){
        String sql = "UPDATE Medicos SET Nombre = ?, Ape_Paterno = ?, Ape_Materno = ?, Especialidad = ?, Años_Experiencia = ? WHERE SSN = ?";

        
        return conexionBD.ejecutarInstruccionLMD(sql, 
                medico.getNombre(),
                medico.getApePaterno(),
                medico.getApeMaterno(),
                medico.getEspecialidad(),
                medico.getAños(),
                medico.getSsn());
    }
    
    //====================CONSULTAS===================
    /*
    public ResultSetTableModel obtenerMedicosFiltrados(String campo, Object valor){
        String consulta = "SELECT * FROM Medicos WHERE " + campo + " = ?";
        try {

            return new ResultSetTableModel(
                    conexionBD.getDriver(),
                    conexionBD.getURL(),
                    consulta,
                    valor
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al obtener medicos filtrados", e);
        }
    }
    */
    
    public ResultSetTableModel obtenerMedicosFiltrados(String campo, Object valor){
    String consulta;

    // Si el valor es texto -> LIKE
    if (valor instanceof String) {
        consulta = "SELECT * FROM Medicos WHERE LOWER(" + campo + ") LIKE ?";
        valor = "%" + valor.toString().toLowerCase() + "%";
    } 
    // Si es número -> '=' exacto
    else {
        consulta = "SELECT * FROM Medicos WHERE " + campo + " = ?";
    }

    try {
        return new ResultSetTableModel(
            conexionBD.getDriver(),
            conexionBD.getURL(),
            consulta,
            valor
        );
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Error al filtrar medicos", e);
    }
}
    
    
    public ResultSetTableModel obtenerMedicos() {
        String consulta = "SELECT * FROM Medicos";
        try {
            return new ResultSetTableModel(
                    conexionBD.getDriver(),
                    conexionBD.getURL(),
                    consulta
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al obtener medicos", e);
        }
    }
    
    
    //=========================
    
    public String obtenerNombreCompleto(String ssn) {
    String sql = "SELECT Nombre, Ape_Paterno, Ape_Materno FROM Medicos WHERE SSN = ?";
    ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, ssn);

    try {
        if (rs != null && rs.next()) {
            return rs.getString("Nombre") + " " +
                   rs.getString("Ape_Paterno") + " " +
                   rs.getString("Ape_Materno");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "No encontrado";
}

}
