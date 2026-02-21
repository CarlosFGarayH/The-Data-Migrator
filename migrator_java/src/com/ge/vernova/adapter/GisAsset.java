package com.ge.vernova.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * ARCHITECT'S NOTE:
 * This class represents the "Record Persistence" layer defined in GE Vernova's 
 * VMDS Cloud strategy. It allows dynamic mapping of GIS attributes to SQL columns.
 * 
 * Why use Map? Because Smallworld schemas are dynamic. Hard-coding classes for 
 * every asset (road, pole, etc.) would violate the Open/Closed Principle.
 */
public class GisAsset {
    private final String collectionName; // Maps to Smallworld Collection
    private final Map<String, Object> attributes; // name -> value (lift & shift)

    public GisAsset(String collectionName) {
        this.collectionName = collectionName;
        this.attributes = new HashMap<>();
    }

    public void setAttribute(String fieldName, Object value) {
        this.attributes.put(fieldName, value);
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void displayMapping() {
        System.out.println("----------------------------------------------");
        System.out.println("TRANSFORMING COLLECTION: " + collectionName.toUpperCase());
        attributes.forEach((k, v) -> System.out.println("  [SQL COLUMN]: " + k + " \t [VALUE]: " + v));
    }
}