/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

/**
 *
 * @author erick
 */

import ConexionBD.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasPacienteDAO implements IEstadisticasPaciente {

    private final ConexionBD con = ConexionBD.getInstancia();

    @Override
    public List<Integer> obtenerEdadesPacientes() {
        List<Integer> edades = new ArrayList<>();

        String sql = "SELECT Edad FROM Pacientes";

        try {
            con.reconectar();
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                edades.add(rs.getInt("Edad"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return edades;
    }
}

