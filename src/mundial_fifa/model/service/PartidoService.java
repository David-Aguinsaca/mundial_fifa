package mundial_fifa.model.service;

import java.util.List;

import mundial_fifa.model.entity.Partido;
import mundial_fifa.model.repository.GenericRepository;
import mundial_fifa.model.repository.PartidoRepository;

public class PartidoService {

    private final GenericRepository<Partido, Integer> repository;

    public PartidoService() {
        this.repository = new PartidoRepository();
    }

    public void registrarPartido(Partido partido) {
        if (partido == null) {
            throw new IllegalArgumentException("El partido no puede ser nulo.");
        }
        if (partido.getIdMundial() == null || partido.getIdMundial() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un mundial válido.");
        }
        if (partido.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del partido es obligatoria.");
        }
        if (partido.getFase() == null || partido.getFase().trim().isEmpty()) {
            throw new IllegalArgumentException("La fase del partido es obligatoria.");
        }
        if (partido.getIdSeleccionLocal() == null || partido.getIdSeleccionLocal() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar la selección local.");
        }
        if (partido.getIdSeleccionVisitante() == null || partido.getIdSeleccionVisitante() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar la selección visitante.");
        }
        if (partido.getIdSeleccionLocal().equals(partido.getIdSeleccionVisitante())) {
            throw new IllegalArgumentException("La selección local y visitante no pueden ser la misma.");
        }

        partido.setFase(partido.getFase().trim());

        try {
            repository.insertar(partido);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo guardar el partido. " + e.getMessage());
        }
    }

    public List<Partido> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de partidos en este momento.", e);
        }
    }

    public Partido buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Partido partido = repository.buscarPorId(id);
        if (partido == null) {
            throw new IllegalArgumentException("No se encontró ningún partido con el ID: " + id);
        }
        return partido;
    }

    public void actualizarPartido(Partido partido) {
        if (partido == null || partido.getIdPartido() == null || partido.getIdPartido() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (partido.getIdMundial() == null || partido.getIdMundial() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un mundial válido.");
        }
        if (partido.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del partido es obligatoria.");
        }
        if (partido.getFase() == null || partido.getFase().trim().isEmpty()) {
            throw new IllegalArgumentException("La fase actualizada no puede estar vacía.");
        }
        if (partido.getIdSeleccionLocal() == null || partido.getIdSeleccionLocal() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar la selección local.");
        }
        if (partido.getIdSeleccionVisitante() == null || partido.getIdSeleccionVisitante() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar la selección visitante.");
        }
        if (partido.getIdSeleccionLocal().equals(partido.getIdSeleccionVisitante())) {
            throw new IllegalArgumentException("La selección local y visitante no pueden ser la misma.");
        }

        partido.setFase(partido.getFase().trim());

        try {
            repository.actualizar(partido);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo actualizar el partido. " + e.getMessage());
        }
    }

    public void eliminarPartido(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un partido válido para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Operación cancelada: El partido no se puede eliminar porque "
                    + "tiene estadísticas asociadas en el sistema.", e);
        }
    }
}