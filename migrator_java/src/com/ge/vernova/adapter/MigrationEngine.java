package com.ge.vernova.adapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class MigrationEngine {
    public static void main(String[] args) {
        System.out.println("=== GE VERNOVA LAB: DYNAMIC METADATA RESOLUTION ===\n");

        // Simulación de la salida Magik que obtuvimos el Martes (Metadata Discovery)
        String[][] discoveredMetadata = {
            {"min_road_id", "ds_uint"},
            {"name", "ds_charci"},
            {"carriage_type", "ds_uint"},
            {"road_type", "ds_uint"},
            {"rwo_id", "ds_uint"},
            {"ds!version", "ds_vstamp"}
        };

        System.out.println("Building Dynamic SQL Schema using TypeResolver...");
        
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE roads_dynamic (");

        for (String[] field : discoveredMetadata) {
            String fieldName = field[0];
            String swType = field[1];

            // AQUI USAMOS EL RESOLVER:
            String sqlType = TypeResolver.resolveToSql(swType);

            sqlBuilder.append("\n  ").append(fieldName).append(" ").append(sqlType).append(",");
            System.out.println("[RESOLVER]: SW '" + swType + "' -> SQL '" + sqlType + "'");
        }

        // Limpiar la última coma y cerrar paréntesis
        String finalSql = sqlBuilder.substring(0, sqlBuilder.length() - 1) + "\n);";

        System.out.println("\nGenerated SQL for GridOS Persistence:");
        System.out.println(finalSql);

        // Prueba de conexión JDBC (Database Access Layer)
        try (Connection conn = DatabaseConnector.getConnection()) {
            System.out.println("\n[SUCCESS]: Connection verified. SQL Adapter is ready for Lab Day 2.");
        } catch (SQLException e) {
            System.err.println("[WARN]: DB Connection failed, but SQL Generation was successful.");
        }
    }
}