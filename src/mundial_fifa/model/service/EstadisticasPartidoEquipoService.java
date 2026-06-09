package mundial_fifa.model.service;

import java.util.List;

import mundial_fifa.model.entity.EstadisticasPartidoEquipo;
import mundial_fifa.model.repository.EstadisticasPartidoEquipoRepository;
import mundial_fifa.model.repository.GenericRepository;

public class EstadisticasPartidoEquipoService {

    private final GenericRepository<EstadisticasPartidoEquipo, Integer> repository;

    public EstadisticasPartidoEquipoService() {
        this.repository = new EstadisticasPartidoEquipoRepository();
    }

    public void registrarEstadistica(EstadisticasPartidoEquipo estadistica) {
        if (estadistica == null) {
            throw new IllegalArgumentException("La estadística no puede ser nula.");
        }
        if (estadistica.getIdPartido() == null || estadistica.getIdPartido() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un partido válido.");
        }
        if (estadistica.getIdSeleccion() == null || estadistica.getIdSeleccion() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una selección válida.");
        }
        if (estadistica.getPosesionPorcentaje() == null ||
                estadistica.getPosesionPorcentaje().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                estadistica.getPosesionPorcentaje().compareTo(new java.math.BigDecimal("100.00")) > 0) {
            throw new IllegalArgumentException("La posesión debe estar entre 0 y 100.");
        }
        if (estadistica.getPrecisionPasesPorcentaje() == null ||
                estadistica.getPrecisionPasesPorcentaje().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                estadistica.getPrecisionPasesPorcentaje().compareTo(new java.math.BigDecimal("100.00")) > 0) {
            throw new IllegalArgumentException("La precisión de pases debe estar entre 0 y 100.");
        }

        try {
            repository.insertar(estadistica);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo guardar la estadística. " + e.getMessage());
        }
    }

    public List<EstadisticasPartidoEquipo> obtenerPorPartido(Integer idPartido) {
        if (idPartido == null || idPartido <= 0) {
            throw new IllegalArgumentException("El ID del partido no es válido.");
        }

        try {
            return ((EstadisticasPartidoEquipoRepository) repository).listarPorPartido(idPartido);
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar las estadísticas del partido.", e);
        }
    }

    public List<EstadisticasPartidoEquipo> obtenerByEstado() {
        try {
            return repository.listarTodoByEstado();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de estadísticas activas en este momento.", e);
        }
    }

    public List<EstadisticasPartidoEquipo> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de estadísticas en este momento.", e);
        }
    }

    public EstadisticasPartidoEquipo buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        EstadisticasPartidoEquipo estadistica = repository.buscarPorId(id);
        if (estadistica == null) {
            throw new IllegalArgumentException("No se encontró ninguna estadística con el ID: " + id);
        }
        return estadistica;
    }

    public void actualizarEstadistica(EstadisticasPartidoEquipo estadistica) {
        if (estadistica == null || estadistica.getIdEstadistica() == null || estadistica.getIdEstadistica() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (estadistica.getIdPartido() == null || estadistica.getIdPartido() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un partido válido.");
        }
        if (estadistica.getIdSeleccion() == null || estadistica.getIdSeleccion() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una selección válida.");
        }
        if (estadistica.getPosesionPorcentaje() == null ||
                estadistica.getPosesionPorcentaje().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                estadistica.getPosesionPorcentaje().compareTo(new java.math.BigDecimal("100.00")) > 0) {
            throw new IllegalArgumentException("La posesión debe estar entre 0 y 100.");
        }
        if (estadistica.getPrecisionPasesPorcentaje() == null ||
                estadistica.getPrecisionPasesPorcentaje().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                estadistica.getPrecisionPasesPorcentaje().compareTo(new java.math.BigDecimal("100.00")) > 0) {
            throw new IllegalArgumentException("La precisión de pases debe estar entre 0 y 100.");
        }

        try {
            repository.actualizar(estadistica);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo actualizar la estadística. " + e.getMessage());
        }
    }

    public void eliminarEstadistica(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una estadística válida para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Operación cancelada: No se pudo eliminar la estadística.", e);
        }
    }
}