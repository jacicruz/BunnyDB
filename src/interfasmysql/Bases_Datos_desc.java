package interfasmysql;

import packeteria.Paqueteria;
<<<<<<< HEAD

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
=======
import javax.swing.*;
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
<<<<<<< HEAD
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
=======
import java.sql.SQLException;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

public class Bases_Datos_desc extends JPanel {
    private DefaultListModel<String> listModelTablas;
    private JList<String> listaTablas;
    private JButton describirTablaButton;
    private JButton ingresarDatosButton;
    private Paqueteria paqueteria;
<<<<<<< HEAD

    private Inicio inicio;
=======
    
    private Inicio inicio; // Referencia a la instancia de la clase Inicio
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

    public Bases_Datos_desc(Inicio inicio) {
        this.inicio = inicio;

<<<<<<< HEAD
        paqueteria = new Paqueteria("localhost", "root", "", 3307);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setPreferredSize(new Dimension(550, 600));
        panelDerecho.setBackground(new Color(173, 216, 230)); // Fondo azul claro

        listModelTablas = new DefaultListModel<>();
        listaTablas = new JList<>(listModelTablas);
        listaTablas.setFont(new Font("Arial", Font.PLAIN, 16));

        ingresarDatosButton = new JButton("Ingresar Datos");
        ingresarDatosButton.setEnabled(false);
        ingresarDatosButton.setFont(new Font("Arial", Font.BOLD, 16));
        ingresarDatosButton.setBackground(new Color(0, 128, 0));
        ingresarDatosButton.setForeground(Color.WHITE);

=======
        // Crear la instancia de Paqueteria y ajustar los valores de conexión según tus necesidades
        paqueteria = new Paqueteria("localhost", "root", "", 3306);

        // Panel derecho para mostrar las tablas
        JPanel panelDerecho = new JPanel();
        panelDerecho.setPreferredSize(new Dimension(550, 600));
        panelDerecho.setBackground(new Color(153, 153, 255)); // Color morado más oscuro

        // Crear un modelo de lista para las tablas
        listModelTablas = new DefaultListModel<>();
        listaTablas = new JList<>(listModelTablas);

        // Botón "Ingresar Datos"
        ingresarDatosButton = new JButton("Ingresar Datos");
        ingresarDatosButton.setEnabled(false); // Inicialmente deshabilitado

        // ActionListener para el botón "Ingresar Datos"
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        ingresarDatosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = listaTablas.getSelectedValue();
                if (selectedTable != null) {
                    String nombreBd = inicio.getSelectedDatabase();
<<<<<<< HEAD
                    try {
                        // Abre la ventana que muestra registros existentes
                        TablaRegistros ventanaRegistros = new TablaRegistros(nombreBd, selectedTable);
                        ventanaRegistros.setVisible(true);
                        DefaultTableModel tableModel = new DefaultTableModel() {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                // Habilita la edición en todas las celdas
                                return true;
                            }
                        };

                        JTable table = new JTable(tableModel);
                        table.setAutoCreateRowSorter(true);

                        tableModel.addTableModelListener(new TableModelListener() {
                            @Override
                            public void tableChanged(TableModelEvent e) {
                                int row = e.getFirstRow();
                                int column = e.getColumn();
                                if (e.getType() == TableModelEvent.UPDATE) {
                                    Object data = tableModel.getValueAt(row, column);
                                    // Aquí puedes procesar los datos ingresados por el usuario (data)
                                    if (data != null) {
                                        // Procesar el dato, por ejemplo, guardarlo en la base de datos
                                        System.out.println("Dato ingresado: " + data.toString());
                                    }
                                }
                            }
                        });

                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(inicio, "Error: Datos nulos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
=======
                    // Abre la ventana que muestra registros existentes
                    TablaRegistros ventanaRegistros = new TablaRegistros(nombreBd, selectedTable);
                    ventanaRegistros.setVisible(true);
                    DefaultTableModel tableModel = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        // Habilita la edición en todas las celdas (puedes personalizar esto según tus necesidades)
        return true;
    }
};

                    JTable table = new JTable(tableModel);
table.setAutoCreateRowSorter(true);
tableModel.addTableModelListener(new TableModelListener() {
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (e.getType() == TableModelEvent.UPDATE) {
            Object data = tableModel.getValueAt(row, column);
            // Aquí puedes procesar los datos ingresados por el usuario (data)
        }
    }
});

>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
                }
            }
        });

<<<<<<< HEAD
        describirTablaButton = new JButton("Describir Tabla");
        describirTablaButton.setEnabled(false);
        describirTablaButton.setFont(new Font("Arial", Font.BOLD, 16));

=======
        // Botón "Describir Tabla"
        describirTablaButton = new JButton("Describir Tabla");
        describirTablaButton.setEnabled(false); // Inicialmente deshabilitado

        // ActionListener para el botón "Describir Tabla"
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        describirTablaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = listaTablas.getSelectedValue();
                if (selectedTable != null) {
                    String nombreBd = inicio.getSelectedDatabase();
<<<<<<< HEAD
                    try {
                        String descripcion = paqueteria.describirTabla(nombreBd, selectedTable);
                        // Mostrar la descripción en un panel emergente
                        JOptionPane.showMessageDialog(inicio, descripcion, "Descripción de la Tabla", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(inicio, "Error: Datos nulos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
=======
                    String descripcion = paqueteria.describirTabla(nombreBd, selectedTable);
                    // Mostrar la descripción en un panel emergente
                    JOptionPane.showMessageDialog(inicio, descripcion, "Descripción de la Tabla", JOptionPane.INFORMATION_MESSAGE);
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
                }
            }
        });

<<<<<<< HEAD
=======
        // Activa el botón "Describir Tabla" y "Ingresar Datos" cuando se selecciona una tabla
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        listaTablas.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    describirTablaButton.setEnabled(true);
                    ingresarDatosButton.setEnabled(true);
                }
            }
        });

<<<<<<< HEAD
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.add(listaTablas, BorderLayout.CENTER);

=======
        // Agregar los componentes al panel derecho
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.add(listaTablas, BorderLayout.CENTER);

        // Crear un panel para los botones
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(describirTablaButton);
        buttonPanel.add(ingresarDatosButton);

        panelDerecho.add(buttonPanel, BorderLayout.SOUTH);

<<<<<<< HEAD
        add(panelDerecho, BorderLayout.CENTER);

=======
        // Agregar el panel derecho al contenido de la ventana
        add(panelDerecho, BorderLayout.CENTER);

        // Cargar las tablas al iniciar la aplicación
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        cargarTablas();
    }

    private void cargarTablas() {
        listModelTablas.clear();

        String selectedDb = inicio.getSelectedDatabase();

        if (selectedDb != null) {
<<<<<<< HEAD
            var tablas = paqueteria.listarTablas(selectedDb);
=======
            List<String> tablas = paqueteria.listarTablas(selectedDb);
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84

            for (String nombreTabla : tablas) {
                listModelTablas.addElement(nombreTabla);
            }
        }
    }
<<<<<<< HEAD

    public void onDatabaseSelected(String selectedDb) {
        listModelTablas.clear();
        if (selectedDb != null) {
            var tablas = paqueteria.listarTablas(selectedDb);
            for (String nombreTabla : tablas) {
                listModelTablas.addElement(nombreTabla);
            }
            describirTablaButton.setEnabled(false);
            ingresarDatosButton.setEnabled(false);
=======
public void onDatabaseSelected(String selectedDb) {
        listModelTablas.clear();
        if (selectedDb != null) {
            List<String> tablas = paqueteria.listarTablas(selectedDb);
            for (String nombreTabla : tablas) {
                listModelTablas.addElement(nombreTabla);
            }
            describirTablaButton.setEnabled(false); // Inicialmente deshabilita el botón "Describir Tabla"
            ingresarDatosButton.setEnabled(false); // Inicialmente deshabilita el botón "Ingresar Datos"
>>>>>>> 363d3d0b72261fcf0f5f64bf83c8b0ed4b559d84
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

        });
    }
}
