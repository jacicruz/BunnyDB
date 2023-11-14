package interfasmysql;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import packeteria.Paqueteria;

public class Conectar extends JPanel {
    private Inicio inicio;

    public Conectar(Inicio inicio) {
        this.inicio = inicio; // Guardar la referencia de la ventana Inicio

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230)); // Color de fondo azul pálido

        JPanel panelConexion = new JPanel();
        panelConexion.setLayout(new BoxLayout(panelConexion, BoxLayout.Y_AXIS));
        panelConexion.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        JLabel lblTitulo = new JLabel("Conectar a MySQL");
        lblTitulo.setFont(labelFont);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setFont(labelFont);
        btnConectar.setBackground(new Color(0, 128, 0));
        btnConectar.setForeground(Color.WHITE);

        btnConectar.addActionListener(e -> {
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
            }
        });

        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(lblTitulo);
        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(lblHost);
        panelConexion.add(txtHost);
        panelConexion.add(lblPuerto);
        panelConexion.add(txtPuerto);
        panelConexion.add(lblUsuario);
        panelConexion.add(txtUsuario);
        panelConexion.add(lblContraseña);
        panelConexion.add(txtContraseña);
        panelConexion.add(Box.createVerticalStrut(20));
        panelConexion.add(btnConectar);
        panelConexion.add(Box.createVerticalStrut(20));

        add(panelConexion, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inicio inicio = new Inicio(); // Crea una instancia de Inicio
            inicio.setVisible(true);
        });
    }
}
