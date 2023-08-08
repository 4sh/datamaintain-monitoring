CREATE TABLE IF NOT EXISTS dm_project (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),
    small_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS dm_environment (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),
    small_name VARCHAR(255),
    fk_project_ref uuid REFERENCES dm_project(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dm_module (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),
    fk_project_ref uuid REFERENCES dm_project(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dm_script (
    name VARCHAR(255),
    checksum VARCHAR(255) PRIMARY KEY,
    content VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS dm_batch_execution (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    fk_environment_ref uuid REFERENCES dm_environment(id) ON DELETE CASCADE,
    fk_module_ref uuid REFERENCES dm_module(id) ON DELETE CASCADE
);

CREATE TYPE script_execution_status AS ENUM (
    'OK',
    'KO'
);

CREATE TABLE IF NOT EXISTS dm_script_execution (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    duration_in_ms INTEGER,
    output TEXT,
    status script_execution_status,
    fk_script_ref VARCHAR(255) REFERENCES dm_script(checksum) ON DELETE CASCADE,
    fk_batch_execution_ref uuid REFERENCES dm_batch_execution(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dm_tag (
    name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS dm_script_execution_dm_tag (
    fk_script_execution_ref uuid REFERENCES dm_script_execution(id) ON DELETE CASCADE,
    fk_tag_ref VARCHAR(255) REFERENCES dm_tag(name) ON DELETE CASCADE,
    PRIMARY KEY (fk_script_execution_ref, fk_tag_ref)
);