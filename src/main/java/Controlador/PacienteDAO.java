/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import ConexionBD.ConexionBD;
import Modelo.Paciente;
import Modelo.ResultSetTableModel;
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
public class PacienteDAO {
    
    private static PacienteDAO instancia;
    
    // Instancia única de conexión
    private ConexionBD conexionBD;

    // Constructor privado
    private PacienteDAO() {
        conexionBD = ConexionBD.getInstancia();
    }
    
    // Método público para obtener la instancia única
    public static PacienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new PacienteDAO();
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
            final String CONSULTA = "SELECT * FROM pacientes";

            try {

                Thread.sleep(2000); // 2 segundos

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
    public boolean agregarPaciente(Paciente paciente){
    String sql = "INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    return conexionBD.ejecutarInstruccionLMD(sql, 
            paciente.getSsn(),
            paciente.getNombre(),
            paciente.getApePaterno(),
            paciente.getApeMaterno(),
            paciente.getEdad(),
            paciente.getSsnMedicoCabecera(),
            paciente.getCalle(),
            paciente.getNumero(),
            paciente.getColonia(),
            paciente.getCodigoPostal());
}

    
    
    public boolean existePaciente(String SSN) {
        String sql = "SELECT SSN FROM Pacientes WHERE SSN = ?";
        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, SSN.trim());
        try {
            return rs != null && rs.next(); // Ya existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public ResultSet obtenerTodosLosMedicos() {
    String sql = "SELECT SSN, Nombre FROM Medicos ORDER BY Nombre";

    return conexionBD.ejecutarConsultaSQL(sql);
    }
    
    
    //==========================BAJAS==========================
    public boolean eliminarPaciente(String SSN){
        String sql = "DELETE FROM Pacientes WHERE SSN = ?";
        return conexionBD.ejecutarInstruccionLMD(sql, SSN);
    }
    
    public ResultSetTableModel actualizarTablaFiltrada(String campo, String valor){
    String consulta = "SELECT * FROM Pacientes WHERE " + campo + " LIKE ?";
    try {
        return new ResultSetTableModel(
                conexionBD.getDriver(),
                conexionBD.getURL(),
                consulta,
                "%" + valor + "%"  
        );
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Error al obtener pacientes filtrados", e);
    }
}
    //"INSERT INTO pacientes (SSN, Nombre, Ape_Paterno, Ape_Materno, Edad, SSN_Medico_Cabecera, Calle, Numero, Colonia, Codigo_Postal) "
    //========================CAMBIOS=======================
    
    public boolean editarPaciente(Paciente paciente){
        String sql = "UPDATE Pacientes SET Nombre = ?, Ape_Paterno = ?, Ape_Materno = ?, Edad = ?, SSN_Medico_Cabecera = ?, Calle = ?, Numero = ?, Colonia = ?, Codigo_Postal = ? WHERE SSN = ?";

        
        return conexionBD.ejecutarInstruccionLMD(sql, 
                paciente.getNombre(),
                paciente.getApePaterno(),
                paciente.getApeMaterno(),
                paciente.getEdad(),
                paciente.getSsnMedicoCabecera(),
                paciente.getCalle(),
                paciente.getNumero(),
                paciente.getColonia(),
                paciente.getCodigoPostal(),
                paciente.getSsn());
    }
    

    //===================CONSULTAS================
    
     public ResultSetTableModel obtenerPacientesFiltrados(String campo, Object valor){
        String consulta = "SELECT * FROM Pacientes WHERE " + campo + " = ?";
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
    
    
    public ResultSetTableModel obtenerPacientes() {
        String consulta = "SELECT * FROM Pacientes";
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
    String sql = "SELECT Nombre, Ape_Paterno, Ape_Materno FROM Pacientes WHERE SSN = ?";
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
