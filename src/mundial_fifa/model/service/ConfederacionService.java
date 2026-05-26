package mundial_fifa.model.service;

import java.util.List;

import mundial_fifa.model.entity.Confederacion;
import mundial_fifa.model.repository.ConfedaracionRepository;
import mundial_fifa.model.repository.GenericRepository;

public class ConfederacionService {

    private final GenericRepository<Confederacion, Integer> repository;

    public ConfederacionService() {
        this.repository = new ConfedaracionRepository();
    }

    public void registrarConfederacion(Confederacion confederacion) {
        if (confederacion == null) {
            throw new IllegalArgumentException("La confederación no puede ser nula.");
        }
        if (confederacion.getNombre() == null || confederacion.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la confederación es obligatorio y no puede estar vacío.");
        }
        if (confederacion.getSiglas() == null || confederacion.getSiglas().trim().isEmpty()) {
            throw new IllegalArgumentException("Las siglas de la confederación son obligatorias y no pueden estar vacías.");
        }
        if (confederacion.getIdContinente() == null || confederacion.getIdContinente() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un continente válido para la confederación.");
        }

        confederacion.setNombre(confederacion.getNombre().trim());
        confederacion.setSiglas(confederacion.getSiglas().trim().toUpperCase());

        try {
            repository.insertar(confederacion);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo guardar la confederación. " + e.getMessage());
        }
    }

    public List<Confederacion> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de confederaciones en este momento.", e);
        }
    }

    public Confederacion buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Confederacion confederacion = repository.buscarPorId(id);
        if (confederacion == null) {
            throw new IllegalArgumentException("No se encontró ninguna confederación con el ID: " + id);
        }
        return confederacion;
    }

    public void actualizarConfederacion(Confederacion confederacion) {
        if (confederacion == null || confederacion.getIdConfederacion() == null || confederacion.getIdConfederacion() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (confederacion.getNombre() == null || confederacion.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre actualizado no puede estar vacío.");
        }
        if (confederacion.getSiglas() == null || confederacion.getSiglas().trim().isEmpty()) {
            throw new IllegalArgumentException("Las siglas actualizadas no pueden estar vacías.");
        }
        if (confederacion.getIdContinente() == null || confederacion.getIdContinente() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un continente válido.");
        }

        confederacion.setNombre(confederacion.getNombre().trim());
        confederacion.setSiglas(confederacion.getSiglas().trim().toUpperCase());

        try {
            repository.actualizar(confederacion);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo actualizar la confederación. " + e.getMessage());
        }
    }

    public void eliminarConfederacion(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una confederación válida para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Operación cancelada: La confederación no se puede eliminar porque "
                    + "tiene países o torneos asociados en el sistema.", e);
        }
    }
}
