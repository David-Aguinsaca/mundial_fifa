package mundial_fifa.model.service;

import java.util.List;

import mundial_fifa.model.entity.Seleccion;
import mundial_fifa.model.repository.GenericRepository;
import mundial_fifa.model.repository.SeleccionRepository;

public class SeleccionService {

    private final GenericRepository<Seleccion, Integer> repository;

    public SeleccionService() {
        this.repository = new SeleccionRepository();
    }

    public void registrarSeleccion(Seleccion seleccion) {
        if (seleccion == null) {
            throw new IllegalArgumentException("La selección no puede ser nula.");
        }
        if (seleccion.getNombre() == null || seleccion.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la selección es obligatorio y no puede estar vacío.");
        }
        if (seleccion.getIdConfederacion() == null || seleccion.getIdConfederacion() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una confederación válida para la selección.");
        }

        seleccion.setNombre(seleccion.getNombre().trim());

        try {
            repository.insertar(seleccion);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo guardar la selección. " + e.getMessage());
        }
    }

    public List<Seleccion> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de selecciones en este momento.", e);
        }
    }

    public List<Seleccion> listarTodosByEstado() {
        try {
            return ((SeleccionRepository) repository).listarTodosByEstado();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de selecciones en este momento.", e);
        }
    }

    public Seleccion buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Seleccion seleccion = repository.buscarPorId(id);
        if (seleccion == null) {
            throw new IllegalArgumentException("No se encontró ninguna selección con el ID: " + id);
        }
        return seleccion;
    }

    public void actualizarSeleccion(Seleccion seleccion) {
        if (seleccion == null || seleccion.getIdSeleccion() == null || seleccion.getIdSeleccion() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (seleccion.getNombre() == null || seleccion.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre actualizado no puede estar vacío.");
        }
        if (seleccion.getIdConfederacion() == null || seleccion.getIdConfederacion() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una confederación válida.");
        }

        seleccion.setNombre(seleccion.getNombre().trim());

        try {
            repository.actualizar(seleccion);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo actualizar la selección. " + e.getMessage());
        }
    }

    public void eliminarSeleccion(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una selección válida para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Operación cancelada: La selección no se puede eliminar porque "
                    + "tiene partidos o estadísticas asociados en el sistema.", e);
        }
    }
}