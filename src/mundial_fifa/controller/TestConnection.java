package mundial_fifa.controller;

import java.sql.Connection;
import java.sql.SQLException;

import mundial_fifa.model.repository.DatabaseConnection;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Conexión exitosa a PostgreSQL!");
            System.out.println("Base de datos: mundial_fifa");
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
	}

}
