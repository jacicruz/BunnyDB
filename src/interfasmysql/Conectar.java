package interfasmysql;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import packeteria.Paqueteria;

public class Conectar extends JPanel {
    private Inicio inicio;

    public Conectar(Inicio inicio) {
<<<<<<< HEAD
        this.inicio = inicio;

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
=======
        this.inicio = inicio; // Guardar la referencia de la ventana Inicio

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230)); // Color de fondo azul pálido
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

        JPanel panelConexion = new JPanel();
        panelConexion.setLayout(new BoxLayout(panelConexion, BoxLayout.Y_AXIS));
        panelConexion.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
<<<<<<< HEAD
        Font inputFont = new Font("Arial", Font.PLAIN, 16);

=======
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        JLabel lblTitulo = new JLabel("Conectar a MySQL");
        lblTitulo.setFont(labelFont);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

<<<<<<< HEAD
        JLabel[] labels = { new JLabel("Host:"), new JLabel("Puerto:"), new JLabel("Usuario:"), new JLabel("Contraseña:") };
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(Color.BLACK);
        }

        JTextField[] textFields = {
                new JTextField(10),
                new JTextField(10),
                new JTextField(10),
                new JPasswordField(10)
        };

        textFields[0].setText("127.0.0.1");
        textFields[1].setText("3306");
        textFields[2].setText("root");
        textFields[3].setText("");
        
        for (JTextField textField : textFields) {
            textField.setFont(inputFont);
            textField.setBackground(new Color(120, 170, 255));
        }
=======
        JLabel lblHost = new JLabel("Host:");
        lblHost.setFont(labelFont);
        JLabel lblPuerto = new JLabel("Puerto:");
        lblPuerto.setFont(labelFont);
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(labelFont);
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(labelFont);

        lblHost.setForeground(Color.BLACK);
        lblPuerto.setForeground(Color.BLACK);
        lblUsuario.setForeground(Color.BLACK);
        lblContraseña.setForeground(Color.BLACK);

        JTextField txtHost = new JTextField(10); // Ajustado el tamaño
        txtHost.setText("127.0.0.1");
        txtHost.setBackground(new Color(120,170,255));

        JTextField txtPuerto = new JTextField(10); // Ajustado el tamaño
        txtPuerto.setText("3306");
        txtPuerto.setBackground(new Color(120,170,255));

        JTextField txtUsuario = new JTextField(10); // Ajustado el tamaño
        txtUsuario.setText("root");
        txtUsuario.setBackground(new Color(120,170,255));

        JPasswordField txtContraseña = new JPasswordField(10); // Ajustado el tamaño
        txtContraseña.setText("");
        txtContraseña.setBackground(new Color(120,170,255));
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setFont(labelFont);
        btnConectar.setBackground(new Color(0, 128, 0));
        btnConectar.setForeground(Color.WHITE);

        btnConectar.addActionListener(e -> {
<<<<<<< HEAD
            try {
                String host = textFields[0].getText();
                int puerto = Integer.parseInt(textFields[1].getText());
                String usuario = textFields[2].getText();
                String contraseña = new String(textFields[3].getText());

                if (host.isEmpty() || usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Paqueteria paqueteria = new Paqueteria(host, usuario, contraseña, puerto);
                Connection conexion = paqueteria.getConexion();

                if (conexion != null) {
                    JOptionPane.showMessageDialog(this, "Conexión exitosa a la base de datos MySQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    inicio.cargarBasesDeDatos(paqueteria);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en el formato del puerto.", "Error", JOptionPane.ERROR_MESSAGE);
=======
            String host = txtHost.getText();
            int puerto = Integer.parseInt(txtPuerto.getText());
            String usuario = txtUsuario.getText();
            String contraseña = new String(txtContraseña.getPassword());

            Paqueteria paqueteria = new Paqueteria(host, usuario, contraseña, puerto);
            Connection conexion = paqueteria.getConexion();

            if (conexion != null) {
                JOptionPane.showMessageDialog(this, "Conexión exitosa a la base de datos MySQL.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                inicio.cargarBasesDeDatos(paqueteria); // Cargar bases de datos en la ventana Inicio
            } else {
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
            }
        });

        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(lblTitulo);
        panelConexion.add(Box.createVerticalStrut(20));
<<<<<<< HEAD
        
        for (int i = 0; i < labels.length; i++) {
            panelConexion.add(labels[i]);
            panelConexion.add(textFields[i]);
        }

=======
        panelConexion.add(lblHost);
        panelConexion.add(txtHost);
        panelConexion.add(lblPuerto);
        panelConexion.add(txtPuerto);
        panelConexion.add(lblUsuario);
        panelConexion.add(txtUsuario);
        panelConexion.add(lblContraseña);
        panelConexion.add(txtContraseña);
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(btnConectar);
        panelConexion.add(Box.createVerticalStrut(20));

        add(panelConexion, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
<<<<<<< HEAD
            Inicio inicio = new Inicio();
=======
            Inicio inicio = new Inicio(); // Crea una instancia de Inicio
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
            inicio.setVisible(true);
        });
    }
}
