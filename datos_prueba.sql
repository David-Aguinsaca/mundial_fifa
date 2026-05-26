select *
from estadistica_partido_equipo epe
where epe.id_seleccion in (1,3)
and epe.id_partido = 1;

select p.id_mundial, p.id_partido, 
s.nombre as local, s.id_seleccion as id_local, 
sv.nombre as visitante, sv.id_seleccion as id_visitante,
p.fecha 
from partido p
left join seleccion s on p.id_seleccion_local = s.id_seleccion
left join seleccion sv on p.id_seleccion_visitante  = sv.id_seleccion ;

-- ==========================================
-- 1. INSERTAR CONTINENTES
-- ==========================================
INSERT INTO continente (nombre) VALUES 
('América del Sur'),
('Europa'),
('América del Norte, Central y Caribe'),
('África'),
('Asia'),
('Oceanía');

-- ==========================================
-- 2. INSERTAR CONFEDERACIONES
-- ==========================================
-- Nota: Relacionamos cada confederación con su respectivo continente
INSERT INTO confederacion (nombre, siglas, id_continente) VALUES 
('Sudamericana', 'CONMEBOL', 1),
('Europeas', 'UEFA', 2),
('Norteamérica, Centroamérica y el Caribe de Fútbol', 'CONCACAF', 3),
('Africana', 'CAF', 4),
('Asiática ', 'AFC', 5);

-- ==========================================
-- 3. INSERTAR SELECCIONES (PAÍSES)
-- ==========================================
INSERT INTO seleccion (nombre, id_confederacion) VALUES 
('Argentina', 1), -- CONMEBOL
('Brasil', 1),     -- CONMEBOL
('Francia', 2),    -- UEFA
('Croacia', 2),    -- UEFA
('Marruecos', 4),  -- CAF
('Japón', 5);      -- AFC

-- ==========================================
-- 4. INSERTAR MUNDIALES
-- ==========================================
INSERT INTO mundial (anio, pais_anfitrion) VALUES 
(2022, 'Qatar');

-- ==========================================
-- 5. INSERTAR PARTIDO (Ejemplo: Gran Final de Qatar 2022)
-- ==========================================
-- Se asume id_mundial = 1, id_seleccion_local (Argentina) = 1, id_seleccion_visitante (Francia) = 3
-- El resultado reglamentario + prórroga fue 3 - 3 antes de los penales
INSERT INTO partido (id_mundial, fecha, fase, id_seleccion_local, id_seleccion_visitante, goles_local, goles_visitante) VALUES 
(1, '2022-12-18', 'Final', 1, 3, 3, 3);

-- ==========================================
-- 6. INSERTAR ESTADÍSTICAS DEL PARTIDO POR EQUIPO
-- ==========================================
-- Estadísticas para Argentina (id_seleccion = 1) en el partido (id_partido = 1)
INSERT INTO estadistica_partido_equipo (
    id_partido, id_seleccion, posesion_porcentaje, tiros_al_arco, 
    tiros_esquina, tiros_libres, faltas, precision_pases_porcentaje, 
    fuera_de_juego, salvadas_portero    
) VALUES (
    1, 1, 54.00, 10, 6, 19, 26, 82.00, 4, 2
);

-- Estadísticas para Francia (id_seleccion = 3) en el partido (id_partido = 1)
INSERT INTO estadistica_partido_equipo (
    id_partido, id_seleccion, posesion_porcentaje, tiros_al_arco, 
    tiros_esquina, tiros_libres, faltas, precision_pases_porcentaje, 
    fuera_de_juego, salvadas_portero
) VALUES (
    1, 3, 46.00, 5, 5, 26, 19, 78.00, 4, 7
);