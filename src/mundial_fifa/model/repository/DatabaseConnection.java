package mundial_fifa.model.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

	private static Connection connection = null;

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// throws (con S, en la firma del método): Es una advertencia pasiva. 
	// Avisa qué errores podrían salir de este método
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			// Instanciamos la clase Properties de Java
			Properties props = new Properties();

			// Leemos el archivo de configuración
			try (FileInputStream fis = new FileInputStream("config.properties")) {
				props.load(fis);
			} catch (IOException e) {
				throw new SQLException("No se pudo cargar el archivo config.properties", e);
			}

			// Extraemos los valores usando las claves del archivo
			String url = props.getProperty("db.url");
			String user = props.getProperty("db.user");
			String password = props.getProperty("db.password");

			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				throw new SQLException("No se encontró el driver de PostgreSQL", e);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
