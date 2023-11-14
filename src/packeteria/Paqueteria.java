package packeteria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Paqueteria {
    private String host;
    private String usuario;
    private String contraseña;
    private int puerto;
    public Paqueteria(String host, String usuario, String contraseña, int puerto) {
        this.host = host;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.puerto = puerto;
    }

    public Connection getConexion() {
        Connection conexion = null;
        try {
            String url = "jdbc:mysql://" + host + ":" + puerto + "/";
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos MySQL.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public boolean eliminarRegistro(String nombreBd, String nombreTabla, String[] valoresColumnas) {
    Connection conexion = getConexion();

    if (conexion != null) {
        try {
            // Construir la consulta de eliminación
            StringBuilder deleteQuery = new StringBuilder("DELETE FROM ");
            deleteQuery.append(nombreBd).append(".").append(nombreTabla).append(" WHERE ");

            String[] columnNames = obtenerNombresColumnas(nombreBd, nombreTabla);

            for (int i = 0; i < columnNames.length; i++) {
                deleteQuery.append(columnNames[i]).append(" = ?");
                if (i < columnNames.length - 1) {
                    deleteQuery.append(" AND ");
                }
            }

            PreparedStatement preparedStatement = conexion.prepareStatement(deleteQuery.toString());

            for (int i = 0; i < valoresColumnas.length; i++) {
                preparedStatement.setString(i + 1, valoresColumnas[i]);
            }

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el registro: " + e.getMessage());
        } finally {
            desconectar(conexion);
        }
    }

    return false;
}



    public void desconectar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public void crearBd(String nombreBd) {
        Connection conexion = null;
        Statement statement = null;

        try {
            conexion = getConexion();
            statement = conexion.createStatement();
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + nombreBd;
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Base de datos '" + nombreBd + "' creada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear la base de datos: " + e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar el Statement: " + e.getMessage());
                }
            }
            desconectar(conexion);
        }
    }

    public List<String> listarBasesDeDatos() {
        List<String> basesDeDatos = new ArrayList<>();
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "SHOW DATABASES";
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String nombreBd = resultSet.getString(1);
                    basesDeDatos.add(nombreBd);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al listar las bases de datos: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return basesDeDatos;
    }

    public String describirTabla(String nombreBd, String nombreTabla) {
        StringBuilder descripcion = new StringBuilder();
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "DESCRIBE " + nombreBd + "." + nombreTabla;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String campo = resultSet.getString("Field");
                    String tipo = resultSet.getString("Type");
                    String nulo = resultSet.getString("Null");
                    String clave = resultSet.getString("Key");
                    String porDefecto = resultSet.getString("Default");
                    String extra = resultSet.getString("Extra");

                    descripcion.append("Campo: ").append(campo).append("\n");
                    descripcion.append("Tipo: ").append(tipo).append("\n");
                    descripcion.append("Nulo: ").append(nulo).append("\n");
                    descripcion.append("Clave: ").append(clave).append("\n");
                    descripcion.append("Por defecto: ").append(porDefecto).append("\n");
                    descripcion.append("Extra: ").append(extra).append("\n\n");
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al describir la tabla: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return descripcion.toString();
    }

    public List<String> listarTablas(String nombreBd) {
        List<String> tablas = new ArrayList<>();
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "SHOW TABLES IN " + nombreBd;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String nombreTabla = resultSet.getString(1);
                    tablas.add(nombreTabla);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al listar las tablas: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return tablas;
    }

    public boolean insertarRegistro(String nombreBd, String nombreTabla, String[] valores) {
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
                queryBuilder.append(nombreBd).append(".").append(nombreTabla).append(" VALUES (");

                for (int i = 0; i < valores.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(", ");
                    }
                    queryBuilder.append("?");
                }

                queryBuilder.append(")");

                String insertQuery = queryBuilder.toString();

                PreparedStatement preparedStatement = conexion.prepareStatement(insertQuery);

                for (int i = 0; i < valores.length; i++) {
                    preparedStatement.setString(i + 1, valores[i]);
                }

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Error al insertar el registro: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return false;
    }

    public String[] obtenerNombresColumnas(String nombreBd, String nombreTabla) {
        Connection conexion = getConexion();
        List<String> nombresColumnas = new ArrayList<>();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "DESCRIBE " + nombreBd + "." + nombreTabla;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String nombreColumna = resultSet.getString("Field");
                    nombresColumnas.add(nombreColumna);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al obtener los nombres de las columnas: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return nombresColumnas.toArray(new String[0]);
    }

    public List<String[]> obtenerRegistros(String nombreBd, String nombreTabla) {
        List<String[]> registros = new ArrayList<>();
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "SELECT * FROM " + nombreBd + "." + nombreTabla;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int columnCount = resultSet.getMetaData().getColumnCount();
                    String[] registro = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        registro[i] = resultSet.getString(i + 1);
                    }
                    registros.add(registro);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al obtener los registros: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return registros;
    }
    public String[] listarAtributos(String nombreBd, String nombreTabla) {
        Connection conexion = getConexion();
        List<String> nombresAtributos = new ArrayList<>();

        if (conexion != null) {
            try {
                Statement statement = conexion.createStatement();
                String query = "DESCRIBE " + nombreBd + "." + nombreTabla;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String nombreAtributo = resultSet.getString("Field");
                    nombresAtributos.add(nombreAtributo);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al obtener los nombres de los atributos: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return nombresAtributos.toArray(new String[0]);
    }
    
    public boolean actualizarRegistro(String nombreBd, String nombreTabla, String[] columnas, String[] valores, String condicion) {
        Connection conexion = getConexion();

        if (conexion != null) {
            try {
                // Construir la consulta de actualización
                StringBuilder updateQuery = new StringBuilder("UPDATE ");
                updateQuery.append(nombreBd).append(".").append(nombreTabla).append(" SET ");

                for (int i = 0; i < columnas.length; i++) {
                    updateQuery.append(columnas[i]).append(" = ?");
                    if (i < columnas.length - 1) {
                        updateQuery.append(", ");
                    }
                }

                // Agregar la condición WHERE
                updateQuery.append(" WHERE ").append(condicion);

                PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery.toString());

                // Establecer los valores en la consulta
                for (int i = 0; i < valores.length; i++) {
                    preparedStatement.setString(i + 1, valores[i]);
                }

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar el registro: " + e.getMessage());
            } finally {
                desconectar(conexion);
            }
        }

        return false;
    }
}
