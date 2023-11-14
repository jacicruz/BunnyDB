package interfasmysql;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import packeteria.Paqueteria;

public class Conectar extends JPanel {
    private Inicio inicio;
    
    public Conectar(Inicio inicio) {
        
        this.inicio = inicio; // Guardar la referencia de la ventana Inicio
        

        JPanel panelConexion = new JPanel();
        panelConexion.setLayout(new BoxLayout(panelConexion, BoxLayout.Y_AXIS));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
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

        panelConexion.add(lblHost);
        JTextField txtHost = new JTextField(20);
                txtHost.setText("127.0.0.1");

        panelConexion.add(txtHost);
        panelConexion.add(lblPuerto);
        JTextField txtPuerto = new JTextField(20);
                txtPuerto.setText("3306");

        panelConexion.add(txtPuerto);
        panelConexion.add(lblUsuario);
        JTextField txtUsuario = new JTextField(20);
                txtUsuario.setText("root");

        panelConexion.add(txtUsuario);
        panelConexion.add(lblContraseña);
        JPasswordField txtContraseña = new JPasswordField(20);
                txtContraseña.setText("");

        panelConexion.add(txtContraseña);

        JButton btnConectar = new JButton("Conectar");
        btnConectar.setFont(labelFont);
        btnConectar.setBackground(new Color(0, 128, 0));
        btnConectar.setForeground(Color.WHITE);
        btnConectar.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        panelConexion.add(btnConectar);

        add(panelConexion);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
 Inicio inicio = new Inicio(); // Crea una instancia de Inicio
        inicio.setVisible(true);
        });
    }


}
