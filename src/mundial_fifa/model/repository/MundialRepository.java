package mundial_fifa.model.repository;

import mundial_fifa.model.entity.Mundial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MundialRepository implements GenericRepository<Mundial, Integer> {

	@Override
	public void insertar(Mundial entidad) {
		String sql = "INSERT INTO mundial_fifa.mundial (anio, pais_anfitrion) VALUES (?, ?)";

		try {
			Connection conn = DatabaseConnection.getConnection();

			try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				stmt.setInt(1, entidad.getAnio());
				stmt.setString(2, entidad.getPaisAnfitrion());

				stmt.executeUpdate();

				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						entidad.setIdMundial(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error SQL al insertar el mundial: " + e.getMessage());
			System.err.println("Código de estado SQL: " + e.getSQLState());
			throw new RuntimeException("No se pudo registrar el mundial debido a un problema en la base de datos.", e);
		}
	}

	@Override
	public List<Mundial> listarTodos() {
		String sql = "SELECT * FROM mundial_fifa.mundial ORDER BY anio DESC";
		List<Mundial> lista = new ArrayList<>();

		try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Mundial entidad = new Mundial();
				entidad.setIdMundial(rs.getInt("id_mundial"));
				entidad.setAnio(rs.getInt("anio"));
				entidad.setPaisAnfitrion(rs.getString("pais_anfitrion"));

				lista.add(entidad);
			}
		} catch (SQLException e) {
			System.err.println("Error SQL al listar mundiales: " + e.getMessage());
			throw new RuntimeException("No se pudo obtener la lista de mundiales.", e);
		}

		return lista;
	}

	@Override
	public void actualizar(Mundial entidad) {
		String sql = "UPDATE mundial_fifa.mundial SET anio = ?, pais_anfitrion = ? WHERE id_mundial = ?";

		try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

			stmt.setInt(1, entidad.getAnio());
			stmt.setString(2, entidad.getPaisAnfitrion());
			stmt.setInt(3, entidad.getIdMundial());

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new SQLException("No se pudo actualizar. El mundial con ID " + entidad.getIdMundial() + " no existe.");
			}
		} catch (SQLException e) {
			System.err.println("Error SQL al actualizar mundial: " + e.getMessage());
			throw new RuntimeException("No se pudo modificar el mundial en la base de datos.", e);
		}
	}

	@Override
	public void eliminar(Integer id) {
		String sql = "DELETE FROM mundial_fifa.mundial WHERE id_mundial = ?";

		try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

			stmt.setInt(1, id);

			int filasAfectadas = stmt.executeUpdate();

			if (filasAfectadas == 0) {
				throw new SQLException("No se pudo eliminar. El mundial con ID " + id + " no existe.");
			}
		} catch (SQLException e) {
			System.err.println("Error SQL al eliminar mundial: " + e.getMessage());
			throw new RuntimeException("No se pudo eliminar el mundial. Verifique que no tenga registros asociados.", e);
		}
	}

	@Override
	public Mundial buscarPorId(Integer id) {
		String sql = "SELECT id_mundial, anio, pais_anfitrion FROM mundial_fifa.mundial WHERE id_mundial = ?";
		Mundial entidad = null;

		try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					entidad = new Mundial();
					entidad.setIdMundial(rs.getInt("id_mundial"));
					entidad.setAnio(rs.getInt("anio"));
					entidad.setPaisAnfitrion(rs.getString("pais_anfitrion"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error SQL al buscar mundial por ID: " + e.getMessage());
			throw new RuntimeException("No se pudo consultar el mundial en la base de datos.", e);
		}

		return entidad;
	}
}
