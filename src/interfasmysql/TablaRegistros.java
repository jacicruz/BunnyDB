package interfasmysql;

import packeteria.Paqueteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablaRegistros extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private Paqueteria paqueteria;
    private String nombreBd;
    private String nombreTabla;
    private JButton agregarRegistroButton; // Botón de agregar registro
    private JButton eliminarRegistroButton; // Botón de eliminar registro
    private JButton actualizarRegistroButton; // Botón de actualizar registro
    private JLabel consultaLabel; // Label para mostrar la consulta

    public TablaRegistros(String nombreBd, String nombreTabla) {
        this.nombreBd = nombreBd;
        this.nombreTabla = nombreTabla;

        setTitle("Registros de " + nombreTabla);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        paqueteria = new Paqueteria("localhost", "root", "", 3306);

        // Crear el panel principal con un color de fondo morado claro
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(200, 180, 255)); // Morado claro

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        agregarRegistroButton = new JButton("Agregar Registro");
        eliminarRegistroButton = new JButton("Eliminar Registro");
        actualizarRegistroButton = new JButton("Actualizar Registro"); // Botón de actualizar registro
        consultaLabel = new JLabel("Consulta: ");

        consultaLabel.setFont(new Font("Times new roman", Font.BOLD, 20)); // Fuente en negrita y tamaño 20
        consultaLabel.setForeground(new Color(0,0,0)); // Color verde claro

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        buttonsPanel.add(agregarRegistroButton);
        buttonsPanel.add(eliminarRegistroButton);
        buttonsPanel.add(actualizarRegistroButton); // Agregar el botón al panel de botones

        panel.add(buttonsPanel, BorderLayout.SOUTH);
        panel.add(consultaLabel, BorderLayout.NORTH);

        agregarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarNuevoRegistro();
            }
        });

        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRegistroSeleccionado();
            }
        });

        // Agregar ActionListener para el botón de "Actualizar Registro"
        actualizarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarRegistroSeleccionado();
            }
        });

        add(panel);

        String[] columnNames = paqueteria.obtenerNombresColumnas(nombreBd, nombreTabla);
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        cargarDatos();

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                table.setColumnSelectionAllowed(true);
                table.setRowSelectionAllowed(false);
                table.setColumnSelectionInterval(col, col);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mostrarMenuContextual(e);
                }
            }
        });


    }   
private void agregarNuevoRegistro() {
        // Obtener los nombres de las columnas
        String[] columnNames = paqueteria.obtenerNombresColumnas(nombreBd, nombreTabla);

        // Crear un nuevo objeto para almacenar los datos del nuevo registro
        String[] nuevoRegistro = new String[columnNames.length];

        // Puedes abrir un cuadro de diálogo para que el usuario ingrese los valores,
        // o puedes implementar la lógica según tus necesidades.

        // Ejemplo: abrir un cuadro de diálogo para ingresar los valores
        for (int i = 0; i < columnNames.length; i++) {
            String valor = JOptionPane.showInputDialog("Ingrese el valor para " + columnNames[i]);
            nuevoRegistro[i] = valor;
        }

        // Guardar el nuevo registro en la base de datos
        boolean exitoso = paqueteria.insertarRegistro(nombreBd, nombreTabla, nuevoRegistro);

        // Si la inserción fue exitosa, agrega el nuevo registro a la tabla
        if (exitoso) {
            tableModel.addRow(nuevoRegistro);
            // Mostrar la consulta en el label
            consultaLabel.setText("Consulta: INSERT INTO " + nombreTabla + " VALUES (" + String.join(", ", nuevoRegistro) + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Error al insertar el nuevo registro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void eliminarRegistroSeleccionado() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow >= 0) {
        int modelRow = table.convertRowIndexToModel(selectedRow);
        String[] rowData = new String[tableModel.getColumnCount()];
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            rowData[col] = (String) tableModel.getValueAt(modelRow, col);
        }
        boolean exitoso = paqueteria.eliminarRegistro(nombreBd, nombreTabla, rowData);
        if (exitoso) {
            tableModel.removeRow(modelRow);
            // Mostrar la consulta de eliminar completa en el label
            consultaLabel.setText("Consulta: " + construirConsultaEliminar(rowData));
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}


    private void actualizarRegistroSeleccionado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String[] rowData = new String[tableModel.getColumnCount()];
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                rowData[col] = (String) tableModel.getValueAt(modelRow, col);
            }
            // Añadir el código necesario para abrir un cuadro de diálogo o
            // utilizar la lógica que necesites para obtener los nuevos valores
            // para actualizar el registro.
            String[] columnNames = paqueteria.obtenerNombresColumnas(nombreBd, nombreTabla);
            String[] newValues = new String[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                String newValue = JOptionPane.showInputDialog("Ingrese el nuevo valor para " + columnNames[i]);
                newValues[i] = newValue;
            }
            // Construir la condición WHERE (puedes personalizar según tus necesidades)
            String condition = columnNames[0] + " = '" + rowData[0] + "'";
            // Actualizar el registro en la base de datos
            boolean exitoso = paqueteria.actualizarRegistro(nombreBd, nombreTabla, columnNames, newValues, condition);
            if (exitoso) {
                // Actualizar los datos en la tabla
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    tableModel.setValueAt(newValues[col], modelRow, col);
                }
                // Mostrar la consulta de actualizar completa en el label
                consultaLabel.setText("Consulta: " + construirConsultaActualizar(rowData, newValues, condition));
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para construir la consulta de eliminar completa
   private String construirConsultaEliminar(String[] rowData) {
    StringBuilder deleteQuery = new StringBuilder("DELETE FROM ");
    deleteQuery.append(nombreBd).append(".").append(nombreTabla).append(" WHERE ");

    String[] columnNames = paqueteria.obtenerNombresColumnas(nombreBd, nombreTabla);

    for (int i = 0; i < columnNames.length; i++) {
        deleteQuery.append(columnNames[i]).append(" = '").append(rowData[i]).append("'");
        if (i < columnNames.length - 1) {
            deleteQuery.append(" AND ");
        }
    }

    return deleteQuery.toString();
}
   

    // Método para construir la consulta de actualizar completa
    private String construirConsultaActualizar(String[] oldValues, String[] newValues, String condition) {
        // Implementa la lógica según tus necesidades
        // Por ejemplo: "UPDATE nombreTabla SET columna1 = nuevoValor1, columna2 = nuevoValor2 WHERE columna1 = valor1 AND ..."
        return "UPDATE " + nombreTabla + " SET " + construirSetClause(oldValues, newValues) + " WHERE " + condition;
    }

    // Método para construir la cláusula SET de una consulta UPDATE
    private String construirSetClause(String[] oldValues, String[] newValues) {
        StringBuilder setClause = new StringBuilder();
        for (int i = 0; i < oldValues.length; i++) {
            setClause.append(oldValues[i]).append(" = '").append(newValues[i]).append("'");
            if (i < oldValues.length - 1) {
                setClause.append(", ");
            }
        }
        return setClause.toString();
    }

    private void cargarDatos() {
      List<String[]> registros = paqueteria.obtenerRegistros(nombreBd, nombreTabla);
        for (String[] registro : registros) {
            tableModel.addRow(registro);
        }
    }

    private void mostrarMenuContextual(MouseEvent e) {
        // Implementa la lógica del menú contextual aquí (si es necesario)
        // ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TablaRegistros ventana = new TablaRegistros("NombreBaseDeDatos", "NombreTabla");
            ventana.setVisible(true);
        });
    }
}
  
