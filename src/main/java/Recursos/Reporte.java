/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

import ConexionBD.ConexionBD;
import com.lowagie.text.alignment.HorizontalAlignment;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;

/**
 *
 * @author erick
 */
public class Reporte {
    
    
    public void generarReportePacientesPorMedico() {

    Connection cn = ConexionBD.getInstancia().getConexion();

    if (cn == null) {
        JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        return;
    }

    try {

        String sql = "SELECT * FROM vista_pacientes_por_medico";
        PreparedStatement ps = cn.prepareStatement(sql);

        // Archivo destino
        String nombreArchivo = "Reporte_Pacientes_Por_Medico.pdf";
        File archivo = new File(nombreArchivo);

        DynamicReports.report()
                .setDataSource(ps.executeQuery())
                .columns(
                        Columns.column("SSN Médico", "ssn", DataTypes.stringType()),
                        Columns.column("Nombre", "nombre", DataTypes.stringType()),
                        Columns.column("Apellido Paterno", "ape_paterno", DataTypes.stringType()),
                        Columns.column("Apellido Materno", "ape_materno", DataTypes.stringType()),
                        Columns.column("Especialidad", "especialidad", DataTypes.stringType()),
                        Columns.column("Total Pacientes", "total_pacientes", DataTypes.integerType())
                )
                .title(
                        Components.text("Reporte: Pacientes por Médico")
                                //.setHorizontalAlignment(HorizontalAlignment.CENTER)
                                //.setFontSize(16)
                )
                .pageFooter(Components.pageXofY())
                .toPdf(new FileOutputStream(nombreArchivo));

        // Abrir PDF al terminar
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(archivo);
        }

        JOptionPane.showMessageDialog(null, "Reporte generado correctamente");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Error al generar el PDF:\n" + e.getMessage());
    }
}

}
