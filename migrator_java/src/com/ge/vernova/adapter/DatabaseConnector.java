package com.ge.vernova.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Aligned with the database 'vernova_migration_db' from your image
    private static final String URL = "jdbc:postgresql://localhost:5432/vernova_migration_db";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "postgre"; // <-- CAMBIA ESTO

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("[ERROR]: No se pudo conectar a la DB 'vernova_migration_db'.");
            throw e;
        }
    }
}