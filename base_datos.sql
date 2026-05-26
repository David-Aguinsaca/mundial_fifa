CREATE TABLE continente (
    id_continente SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL unique,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE confederacion (
    id_confederacion SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    siglas VARCHAR(10) NOT NULL UNIQUE,
    id_continente INT NOT NULL,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_confederacion_continente FOREIGN KEY (id_continente) 
        REFERENCES continente(id_continente) ON DELETE RESTRICT
);

CREATE TABLE seleccion (
    id_seleccion SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    id_confederacion INT NOT NULL,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_seleccion_confederacion FOREIGN KEY (id_confederacion) 
        REFERENCES confederacion(id_confederacion) ON DELETE RESTRICT
);

CREATE TABLE mundial (
    id_mundial SERIAL PRIMARY KEY,
    anio INT NOT NULL UNIQUE,
    pais_anfitrion VARCHAR(100) NOT NULL,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_anio CHECK (anio >= 1930)
);

CREATE TABLE partido (
    id_partido SERIAL PRIMARY KEY,
    id_mundial INT NOT NULL,
    fecha DATE NOT NULL,
    fase VARCHAR(50) NOT NULL, -- Ej: 'Fase de Grupos', 'Cuartos', 'Final'
    id_seleccion_local INT NOT NULL,
    id_seleccion_visitante INT NOT NULL,
    goles_local INT NOT NULL DEFAULT 0,
    goles_visitante INT NOT NULL DEFAULT 0,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_partido_mundial FOREIGN KEY (id_mundial) 
        REFERENCES mundial(id_mundial) ON DELETE RESTRICT,
    CONSTRAINT fk_partido_local FOREIGN KEY (id_seleccion_local) 
        REFERENCES seleccion(id_seleccion) ON DELETE RESTRICT,
    CONSTRAINT fk_partido_visitante FOREIGN KEY (id_seleccion_visitante) 
        REFERENCES seleccion(id_seleccion) ON DELETE RESTRICT,
    CONSTRAINT chk_equipos_distintos CHECK (id_seleccion_local <> id_seleccion_visitante),
    CONSTRAINT chk_goles_local CHECK (goles_local >= 0),
    CONSTRAINT chk_goles_visitante CHECK (goles_visitante >= 0)
);

CREATE TABLE estadistica_partido_equipo (
    id_estadistica SERIAL PRIMARY KEY,
    id_partido INT NOT NULL,
    id_seleccion INT NOT NULL,
    posesion_porcentaje NUMERIC(5,2) NOT NULL,
    tiros_al_arco INT NOT NULL DEFAULT 0,
    tiros_esquina INT NOT NULL DEFAULT 0,
    tiros_libres INT NOT NULL DEFAULT 0,
    faltas INT NOT NULL DEFAULT 0,
    precision_pases_porcentaje NUMERIC(5,2) NOT NULL,
    fuera_de_juego INT NOT NULL DEFAULT 0,
    salvadas_portero INT NOT NULL DEFAULT 0,
    estado BOOL not null DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_est_partido FOREIGN KEY (id_partido) 
        REFERENCES partido(id_partido) ON DELETE CASCADE,
    CONSTRAINT fk_est_seleccion FOREIGN KEY (id_seleccion) 
        REFERENCES seleccion(id_seleccion) ON DELETE RESTRICT,
    CONSTRAINT uq_partido_equipo UNIQUE (id_partido, id_seleccion),
    CONSTRAINT chk_posesion CHECK (posesion_porcentaje >= 0.00 AND posesion_porcentaje <= 100.00),
    CONSTRAINT chk_precision CHECK (precision_pases_porcentaje >= 0.00 AND precision_pases_porcentaje <= 100.00),
    CONSTRAINT chk_tiros_arco CHECK (tiros_al_arco >= 0),
    CONSTRAINT chk_corners CHECK (tiros_esquina >= 0),
    CONSTRAINT chk_tiros_libres CHECK (tiros_libres >= 0),
    CONSTRAINT chk_faltas CHECK (faltas >= 0),
    CONSTRAINT chk_offsides CHECK (fuera_de_juego >= 0),
    CONSTRAINT chk_salvadas CHECK (salvadas_portero >= 0)
);