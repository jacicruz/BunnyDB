package interfasmysql;
<<<<<<< HEAD

=======
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import packeteria.Paqueteria;

public class Crear_Base_Datos extends JPanel {
<<<<<<< HEAD

    private JTextField txtNombreBd;

    public Crear_Base_Datos(Inicio inicio) {
        setBackground(new Color(173, 216, 230));

        // Crear un panel para los componentes de creación de base de datos con disposición GridBagLayout
        JPanel panelCreacion = new JPanel(new GridBagLayout());
        panelCreacion.setBackground(new Color(120, 170, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Crear etiqueta y campo de texto para el nombre de la base de datos
        JLabel lblNombreBd = new JLabel("Nombre de la Base de Datos:");
        lblNombreBd.setFont(new Font("Arial", Font.BOLD, 30));
        txtNombreBd = new JTextField(30);
        txtNombreBd.setFont(new Font("Arial", Font.PLAIN, 24));
        JButton btnCrearBd = new JButton("Crear");
        btnCrearBd.setFont(new Font("Arial", Font.BOLD, 24));
        btnCrearBd.setBackground(new Color(0, 128, 0));
        btnCrearBd.setForeground(Color.WHITE);

        // Agregar los componentes al panel con GridBagLayout
        panelCreacion.add(lblNombreBd, gbc);
        gbc.gridy++;
        panelCreacion.add(txtNombreBd, gbc);
        gbc.gridy++;
        panelCreacion.add(btnCrearBd, gbc);

        // Agregar el panel de creación al centro del JPanel
        setLayout(new GridBagLayout());
        add(panelCreacion, new GridBagConstraints());
=======
    private JTextField txtNombreBd;

    public Crear_Base_Datos(Inicio inicio) {
       

        // Crear un panel para los componentes de creación de base de datos con disposición BoxLayout vertical
        JPanel panelCreacion = new JPanel();
       panelCreacion.setBackground(new Color(120,170,255));
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
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

        // Agregar un ActionListener al botón "Crear"
        btnCrearBd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre de la base de datos desde el campo de texto
<<<<<<< HEAD
                String nombreBd = txtNombreBd.getText().trim(); // Trim para eliminar espacios en blanco

                // Verificar que el nombre de la base de datos no esté vacío
                if (nombreBd.isEmpty()) {
                    JOptionPane.showMessageDialog(Crear_Base_Datos.this, "Por favor, ingrese un nombre para la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Salir del método si el nombre de la base de datos está vacío
                }

                // Crear una instancia de Paqueteria
                Paqueteria paqueteria = new Paqueteria("localhost", "root", "", 3307);

                // Intentar crear la base de datos
                try {
                    paqueteria.crearBd(nombreBd);

                    // Muestra un mensaje emergente
                    JOptionPane.showMessageDialog(Crear_Base_Datos.this, "La base de datos '" + nombreBd + "' ha sido creada.", "Base de Datos Creada", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    // Muestra un mensaje de error si hay algún problema al crear la base de datos
                    System.out.println("Error: " + ex.getMessage()); // Imprimir el mensaje de la excepción

                    if (ex.getMessage().toLowerCase().contains("database exists")) {
                        JOptionPane.showMessageDialog(Crear_Base_Datos.this, "La base de datos '" + nombreBd + "' ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Crear_Base_Datos.this, "Error al crear la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace(); // Puedes eliminar esta línea en un entorno de producción
                    }
                }
=======
                String nombreBd = txtNombreBd.getText();

                // Crear una instancia de Paqueteria
                int tu_puerto = 3306; // Reemplaza 3306 con el número de puerto correcto
                Paqueteria paqueteria = new Paqueteria("localhost", "root","", 3306);

                // Llama al método crearBd con el nombre de la base de datos
                paqueteria.crearBd(nombreBd);

                // Muestra un mensaje emergente
                JOptionPane.showMessageDialog(Crear_Base_Datos.this, "La base de datos '" + nombreBd + "' ha sido creada.", "Base de Datos Creada", JOptionPane.INFORMATION_MESSAGE);

                // Cierra la ventana de Crear_Base_Datos
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
            }
        });

        // Hacer visible la ventana de Crear_Base_Datos
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
<<<<<<< HEAD
            // Agrega aquí la inicialización de la ventana si es necesario
        });
    }
}
=======
        });
    }
}

>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
