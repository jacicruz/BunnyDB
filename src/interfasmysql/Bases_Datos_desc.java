package interfasmysql;

import packeteria.Paqueteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Bases_Datos_desc extends JPanel {
    private DefaultListModel<String> listModelTablas;
    private JList<String> listaTablas;
    private JButton describirTablaButton;
    private JButton ingresarDatosButton;
    private Paqueteria paqueteria;

    private Inicio inicio;

    public Bases_Datos_desc(Inicio inicio) {
        this.inicio = inicio;

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

        ingresarDatosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = listaTablas.getSelectedValue();
                if (selectedTable != null) {
                    String nombreBd = inicio.getSelectedDatabase();
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
                }
            }
        });

        describirTablaButton = new JButton("Describir Tabla");
        describirTablaButton.setEnabled(false);
        describirTablaButton.setFont(new Font("Arial", Font.BOLD, 16));

        describirTablaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = listaTablas.getSelectedValue();
                if (selectedTable != null) {
                    String nombreBd = inicio.getSelectedDatabase();
                    try {
                        String descripcion = paqueteria.describirTabla(nombreBd, selectedTable);
                        // Mostrar la descripción en un panel emergente
                        JOptionPane.showMessageDialog(inicio, descripcion, "Descripción de la Tabla", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(inicio, "Error: Datos nulos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        listaTablas.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    describirTablaButton.setEnabled(true);
                    ingresarDatosButton.setEnabled(true);
                }
            }
        });

        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.add(listaTablas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(describirTablaButton);
        buttonPanel.add(ingresarDatosButton);

        panelDerecho.add(buttonPanel, BorderLayout.SOUTH);

        add(panelDerecho, BorderLayout.CENTER);

        cargarTablas();
    }

    private void cargarTablas() {
        listModelTablas.clear();

        String selectedDb = inicio.getSelectedDatabase();

        if (selectedDb != null) {
            var tablas = paqueteria.listarTablas(selectedDb);

            for (String nombreTabla : tablas) {
                listModelTablas.addElement(nombreTabla);
            }
        }
    }

    public void onDatabaseSelected(String selectedDb) {
        listModelTablas.clear();
        if (selectedDb != null) {
            var tablas = paqueteria.listarTablas(selectedDb);
            for (String nombreTabla : tablas) {
                listModelTablas.addElement(nombreTabla);
            }
            describirTablaButton.setEnabled(false);
            ingresarDatosButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

        });
    }
}
