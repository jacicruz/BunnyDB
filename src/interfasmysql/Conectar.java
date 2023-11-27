package interfasmysql;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import packeteria.Paqueteria;

public class Conectar extends JPanel {
    private Inicio inicio;

    public Conectar(Inicio inicio) {
        this.inicio = inicio;

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));

        JPanel panelConexion = new JPanel();
        panelConexion.setLayout(new BoxLayout(panelConexion, BoxLayout.Y_AXIS));
        panelConexion.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font inputFont = new Font("Arial", Font.PLAIN, 16);

        JLabel lblTitulo = new JLabel("Conectar a MySQL");
        lblTitulo.setFont(labelFont);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setFont(labelFont);
        btnConectar.setBackground(new Color(0, 128, 0));
        btnConectar.setForeground(Color.WHITE);

        btnConectar.addActionListener(e -> {
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
            }
        });

        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(lblTitulo);
        panelConexion.add(Box.createVerticalStrut(20));
        
        for (int i = 0; i < labels.length; i++) {
            panelConexion.add(labels[i]);
            panelConexion.add(textFields[i]);
        }

        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(btnConectar);
        panelConexion.add(Box.createVerticalStrut(20));

        add(panelConexion, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inicio inicio = new Inicio();
            inicio.setVisible(true);
        });
    }
}
