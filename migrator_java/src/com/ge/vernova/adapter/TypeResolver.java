package com.ge.vernova.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * ARCHITECT'S NOTE:
 * This class implements a 'Mapping Strategy'. 
 * It decouples the Smallworld source from the SQL target.
 * Aligned with GE Vernova GridOS Data Fabric standards.
 */
public class TypeResolver {
    
    // Internal registry for type mapping
    private static final Map<String, String> typeRegistry = new HashMap<>();

    static {
        // Based on Tuesday's Real Metadata Trace:
        typeRegistry.put("ds_uint", "BIGINT");          // Smallworld unsigned to SQL Bigint
        typeRegistry.put("ds_charci", "VARCHAR(255)");  // Case-insensitive string
        typeRegistry.put("ds_vstamp", "BIGINT");        // Versioning counter
        typeRegistry.put("sys_id", "BIGINT");           // Unique identifier
        typeRegistry.put("ds_int", "INTEGER");          // Standard integer
        typeRegistry.put("ds_float", "NUMERIC(18,4)");  // Precision data
    }

    /**
     * Resolves a Smallworld storage type to a PostgreSQL-compliant type.
     * @param swType The storage type from CASE Tool (e.g., "ds_charci")
     * @return The corresponding SQL type string.
     */
    public static String resolveToSql(String swType) {
        if (swType == null) return "TEXT";
        
        // Find mapping or return TEXT as safe fallback (Architect's Safety Strategy)
        return typeRegistry.getOrDefault(swType.toLowerCase(), "TEXT");
    }
}
