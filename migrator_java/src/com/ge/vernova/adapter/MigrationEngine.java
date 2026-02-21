package com.ge.vernova.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MigrationEngine {
    public static void main(String[] args) {
        System.out.println("=== GE VERNOVA LAB: MIGRATING ASSETS TO POSTGRESQL ===\n");

        try (Connection conn = DatabaseConnector.getConnection()) {
            System.out.println("[SUCCESS]: Connection established to 'vernova_migration_db'.");

            GisAsset road = new GisAsset("roads");
            road.setAttribute("sw_id", UUID.randomUUID());
            road.setAttribute("name", "Highland Avenue");
            road.setAttribute("length_km", 12.5);
            
            road.setAttribute("geom_wkt", "LINESTRING(-77.04 12.04, -77.05 12.05)");

            insertRoadIntoPostGIS(conn, road);

            System.out.println("\n[FINISH]: Data persisted in PostgreSQL successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertRoadIntoPostGIS(Connection conn, GisAsset asset) throws SQLException {

        String sql = "INSERT INTO roads (sw_id, name, length_km, geom) VALUES (?, ?, ?, ST_GeomFromText(?, 4326))";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, asset.getAttributes().get("sw_id"));
            pstmt.setString(2, (String) asset.getAttributes().get("name"));
            pstmt.setDouble(3, (Double) asset.getAttributes().get("length_km"));
            pstmt.setString(4, (String) asset.getAttributes().get("geom_wkt"));
            
            pstmt.executeUpdate();
            System.out.println("[SQL ADAPTER]: Inserted asset '" + asset.getAttributes().get("name") + "' with GEOMETRY into table 'roads'.");
        }
    }
}