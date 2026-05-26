package mundial_fifa.model.service;


import java.util.List;

import mundial_fifa.model.entity.Continente;
import mundial_fifa.model.repository.ContinenteRepository;
import mundial_fifa.model.repository.GenericRepository;

public class ContinenteService {

    // Apuntando a la interfaz base
    private final GenericRepository<Continente, Integer> repository;

    public ContinenteService() {
        // Instanciamos la implementación concreta que maneja JDBC nativo
        this.repository = new ContinenteRepository();
    }

    /**
     * Lógica para registrar un nuevo continente con validaciones previas.
     */
    public void registrarContinente(Continente continente) {
        // 1. Regla de Negocio: Validar que el nombre no sea nulo ni vacío
        if (continente == null || continente.getNombre() == null || continente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del continente es obligatorio y no puede estar vacío.");
        }

        // Limpiar espacios en blanco innecesarios en los extremos (Ej: "  Europa " -> "Europa")
        continente.setNombre(continente.getNombre().trim());

        try {
            // 2. Si pasa las reglas, se envía al repositorio para guardarlo en PostgreSQL
            repository.insertar(continente);
        } catch (RuntimeException e) {
            // Aquí capturamos la excepción técnica que lanzó el repositorio
            // Podríamos evaluar el error o simplemente relanzarlo con un mensaje de negocio
            throw new RuntimeException("Error en el sistema: No se pudo guardar el continente. " + e.getMessage());
        }
    }

    /**
     * Obtiene la lista completa de continentes para cargar en la tabla del ERP.
     */
    public List<Continente> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de continentes en este momento.", e);
        }
    }

    /**
     * Busca un continente por su ID.
     */
    public Continente buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }
        
        Continente continente = repository.buscarPorId(id);
        if (continente == null) {
            throw new IllegalArgumentException("No se encontró ningún continente con el ID: " + id);
        }
        return continente;
    }

    /**
     * Aplica lógica antes de actualizar un registro existente.
     */
    public void actualizarContinente(Continente continente) {
        if (continente == null || continente.getIdContinente() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (continente.getNombre() == null || continente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre actualizado no puede estar vacío.");
        }

        continente.setNombre(continente.getNombre().trim());
        repository.actualizar(continente);
    }

    /**
     * Elimina un continente asegurando que el ID cumpla los requisitos mínimos.
     */
    public void eliminarContinente(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un continente válido para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            // Las fallas por llaves foráneas (FK) de PostgreSQL se transforman aquí
            // en un mensaje de advertencia limpio para la interfaz gráfica del usuario
            throw new RuntimeException("Operación cancelada: El continente no se puede eliminar porque "
                    + "tiene países o torneos asociados en el sistema.", e);
        }
    }
}