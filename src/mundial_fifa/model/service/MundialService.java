package mundial_fifa.model.service;

import java.util.List;

import mundial_fifa.model.entity.Mundial;
import mundial_fifa.model.repository.GenericRepository;
import mundial_fifa.model.repository.MundialRepository;

public class MundialService {

    private final GenericRepository<Mundial, Integer> repository;

    public MundialService() {
        this.repository = new MundialRepository();
    }

    public void registrarMundial(Mundial mundial) {
        if (mundial == null) {
            throw new IllegalArgumentException("El mundial no puede ser nulo.");
        }
        if (mundial.getAnio() == null || mundial.getAnio() < 1930) {
            throw new IllegalArgumentException("El año debe ser un valor válido (mayor o igual a 1930).");
        }
        if (mundial.getPaisAnfitrion() == null || mundial.getPaisAnfitrion().trim().isEmpty()) {
            throw new IllegalArgumentException("El país anfitrión es obligatorio y no puede estar vacío.");
        }

        mundial.setPaisAnfitrion(mundial.getPaisAnfitrion().trim());

        try {
            repository.insertar(mundial);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en el sistema: No se pudo guardar el mundial. " + e.getMessage());
        }
    }

    public List<Mundial> obtenerTodos() {
        try {
            return repository.listarTodos();
        } catch (RuntimeException e) {
            throw new RuntimeException("No se pudo cargar la lista de mundiales en este momento.", e);
        }
    }

    public Mundial buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID proporcionado no es válido.");
        }

        Mundial mundial = repository.buscarPorId(id);
        if (mundial == null) {
            throw new IllegalArgumentException("No se encontró ningún mundial con el ID: " + id);
        }
        return mundial;
    }

    public void actualizarMundial(Mundial mundial) {
        if (mundial == null || mundial.getIdMundial() == null || mundial.getIdMundial() <= 0) {
            throw new IllegalArgumentException("No se puede actualizar un registro sin un ID válido.");
        }
        if (mundial.getAnio() == null || mundial.getAnio() < 1930) {
            throw new IllegalArgumentException("El año actualizado no es válido.");
        }
        if (mundial.getPaisAnfitrion() == null || mundial.getPaisAnfitrion().trim().isEmpty()) {
            throw new IllegalArgumentException("El país anfitrión actualizado no puede estar vacío.");
        }

        mundial.setPaisAnfitrion(mundial.getPaisAnfitrion().trim());
        repository.actualizar(mundial);
    }

    public void eliminarMundial(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un mundial válido para eliminar.");
        }

        try {
            repository.eliminar(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Operación cancelada: El mundial no se puede eliminar porque "
                    + "tiene partidos o torneos asociados en el sistema.", e);
        }
    }
}
