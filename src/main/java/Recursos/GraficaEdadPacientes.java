/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Recursos;

/**
 *
 * @author erick
 */

import org.knowm.xchart.*;
import javax.swing.*;
import java.util.*;

public class GraficaEdadPacientes implements IGraficador {

    private final IEstadisticasPaciente dao;

    public GraficaEdadPacientes(IEstadisticasPaciente dao) {
        this.dao = dao;
    }

    @Override
    public void graficar() {

        List<Integer> edades = dao.obtenerEdadesPacientes();

        if (edades.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No hay pacientes registrados para generar la gráfica.");
            return;
        }

        // -------------------------
        // Agrupar por rangos de edad
        // -------------------------
        Map<String, Integer> rangos = new LinkedHashMap<>();
        rangos.put("1–10", 0);
        rangos.put("11–20", 0);
        rangos.put("21–30", 0);
        rangos.put("31–40", 0);
        rangos.put("41–50", 0);
        rangos.put("51–60", 0);
        rangos.put("60+", 0);

        for (int edad : edades) {
            if      (edad <= 10) rangos.put("1–10", rangos.get("1–10")+1);
            else if (edad <= 20) rangos.put("11–20", rangos.get("11–20")+1);
            else if (edad <= 30) rangos.put("21–30", rangos.get("21–30")+1);
            else if (edad <= 40) rangos.put("31–40", rangos.get("31–40")+1);
            else if (edad <= 50) rangos.put("41–50", rangos.get("41–50")+1);
            else if (edad <= 60) rangos.put("51–60", rangos.get("51–60")+1);
            else                 rangos.put("60+", rangos.get("60+")+1);
        }

        // -------------------------
        // Construir gráfica XChart
        // -------------------------
        CategoryChart chart = new CategoryChartBuilder()
                .width(900)
                .height(550)
                .title("Distribución de Edades de Pacientes")
                .xAxisTitle("Rango de Edad")
                .yAxisTitle("Número de Pacientes")
                .build();

        chart.addSeries(
                "Pacientes",
                new ArrayList<>(rangos.keySet()),
                new ArrayList<>(rangos.values())
        );

        new Thread(() -> {
            SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(chart);
            JFrame frame = wrapper.displayChart();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }).start();
    }
}

