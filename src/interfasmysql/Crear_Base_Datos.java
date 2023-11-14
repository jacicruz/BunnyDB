package interfasmysql;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import packeteria.Paqueteria;

public class Crear_Base_Datos extends JPanel {
    private JTextField txtNombreBd;

    public Crear_Base_Datos(Inicio inicio) {
       

        // Crear un panel para los componentes de creación de base de datos con disposición BoxLayout vertical
        JPanel panelCreacion = new JPanel();
        panelCreacion.setLayout(new BoxLayout(panelCreacion, BoxLayout.Y_AXIS));

        // Crear etiqueta y campo de texto para el nombre de la base de datos
        JLabel lblNombreBd = new JLabel("Nombre de la Base de Datos:");
        txtNombreBd = new JTextField(20);
        panelCreacion.add(lblNombreBd);
        panelCreacion.add(txtNombreBd);

        // Crear el botón "Crear" para crear la base de datos
        JButton btnCrearBd = new JButton("Crear");
        panelCreacion.add(btnCrearBd);

        // Agregar el panel de creación a la ventana de Crear_Base_Datos
        add(panelCreacion);

        // Agregar un ActionListener al botón "Crear"
        btnCrearBd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre de la base de datos desde el campo de texto
                String nombreBd = txtNombreBd.getText();

                // Crear una instancia de Paqueteria
                int tu_puerto = 3306; // Reemplaza 3306 con el número de puerto correcto
                Paqueteria paqueteria = new Paqueteria("localhost", "root","", 3306);

                // Llama al método crearBd con el nombre de la base de datos
                paqueteria.crearBd(nombreBd);

                // Muestra un mensaje emergente
                JOptionPane.showMessageDialog(Crear_Base_Datos.this, "La base de datos '" + nombreBd + "' ha sido creada.", "Base de Datos Creada", JOptionPane.INFORMATION_MESSAGE);

                // Cierra la ventana de Crear_Base_Datos
            }
        });

        // Hacer visible la ventana de Crear_Base_Datos
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        });
    }
}

