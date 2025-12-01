/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinal_farmacias;

import Login.Login;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

/**
 *
 * @author erick
 */
public class ProyectoFinal_FARMACIAS {

    public static void main(String[] args) {

        try {
            FlatMacLightLaf.setup();  

            
            UIManager.put("Component.arc", 20);
            UIManager.put("Button.arc", 20);
            UIManager.put("TextComponent.arc", 20);
            UIManager.put("CheckBox.arc", 20);

            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.hoverBackground", new Color(220, 235, 255));
            UIManager.put("Button.pressedBackground", new Color(200, 220, 250));

            UIManager.put("defaultFont", new Font("Inter", Font.ITALIC, 12));

        } catch(Exception ex) {
            System.err.println("Error al cargar FlatLaf");
        }

        
        //new VentanaInicio().setVisible(true);
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}


