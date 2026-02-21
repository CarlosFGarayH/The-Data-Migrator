-- =============================================================
-- GE VERNOVA GRIDOS - DATA MIGRATOR INITIALIZATION
-- Target: PostgreSQL with PostGIS extension
-- Description: Sets up the landing zone for VMDS record persistence.
-- =============================================================

-- Enable the spatial engine
CREATE EXTENSION IF NOT EXISTS postgis;

-- 1. Roads Table (Landing Zone)
CREATE TABLE roads (
    id BIGSERIAL PRIMARY KEY,
    sw_id UUID UNIQUE NOT NULL,      -- Reference to the original Smallworld Object ID (OID)
    name VARCHAR(20),               -- Precision (20) defined in the CASE Tool Logical Model
    length_km NUMERIC(10,2),        -- Value calculated by the Java-based trigger logic
    geom GEOMETRY(LineString, 4326) -- SRID 4326 corresponds to WGS84 standard (Lat/Lon)
);

-- 2. Towns Table
CREATE TABLE towns (
    id BIGSERIAL PRIMARY KEY,
    sw_id UUID UNIQUE NOT NULL,      -- Mapping the object pointer from VMDS to a relational UUID
    name VARCHAR(20),               -- Metadata parity with Smallworld CASE Tool
    population INTEGER,
    geom GEOMETRY(Point, 4326)      -- Spatial representation for Town locations
);

-- 3. Create Spatial Indexes (Vital for GridOS Data Fabric performance)
CREATE INDEX idx_roads_geom ON roads USING GIST (geom);
CREATE INDEX idx_towns_geom ON towns USING GIST (geom);

SELECT id, sw_id, name, length_km, geom FROM roads;