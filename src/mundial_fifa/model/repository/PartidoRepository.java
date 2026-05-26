package mundial_fifa.model.repository;

import mundial_fifa.model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoRepository implements GenericRepository<Partido, Integer> {

  @Override
  public void insertar(Partido entidad) {
    String sql = "INSERT INTO mundial_fifa.partido (id_mundial, fecha, fase, id_seleccion_local, id_seleccion_visitante, goles_local, goles_visitante) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {
      Connection conn = DatabaseConnection.getConnection();

      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, entidad.getIdMundial());
        stmt.setDate(2, Date.valueOf(entidad.getFecha()));
        stmt.setString(3, entidad.getFase());
        stmt.setInt(4, entidad.getIdSeleccionLocal());
        stmt.setInt(5, entidad.getIdSeleccionVisitante());
        stmt.setInt(6, entidad.getGolesLocal());
        stmt.setInt(7, entidad.getGolesVisitante());

        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            entidad.setIdPartido(rs.getInt(1));
          }
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al insertar el partido: " + e.getMessage());
      System.err.println("Código de estado SQL: " + e.getSQLState());
      throw new RuntimeException("No se pudo registrar el partido debido a un problema en la base de datos.",
          e);
    }
  }

  @Override
  public List<Partido> listarTodos() {
    String sql = "SELECT p.*, " +
            "m.anio, m.pais_anfitrion, " +
            "sl.nombre AS nombre_seleccion_local, " +
            "sv.nombre AS nombre_seleccion_visitante " +
            "FROM mundial_fifa.partido p " +
            "INNER JOIN mundial_fifa.mundial m ON p.id_mundial = m.id_mundial " +
            "INNER JOIN mundial_fifa.seleccion sl ON p.id_seleccion_local = sl.id_seleccion " +
            "INNER JOIN mundial_fifa.seleccion sv ON p.id_seleccion_visitante = sv.id_seleccion " +
            "ORDER BY p.fecha_creacion DESC";

    List<Partido> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Partido entidad = new Partido();
        entidad.setIdPartido(rs.getInt("id_partido"));
        entidad.setIdMundial(rs.getInt("id_mundial"));
        entidad.setFecha(rs.getDate("fecha").toLocalDate());
        entidad.setFase(rs.getString("fase"));
        entidad.setIdSeleccionLocal(rs.getInt("id_seleccion_local"));
        entidad.setIdSeleccionVisitante(rs.getInt("id_seleccion_visitante"));
        entidad.setGolesLocal(rs.getInt("goles_local"));
        entidad.setGolesVisitante(rs.getInt("goles_visitante"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));

        Mundial mundial = new Mundial();
        mundial.setIdMundial(rs.getInt("id_mundial"));
        mundial.setAnio(rs.getInt("anio"));
        mundial.setPaisAnfitrion(rs.getString("pais_anfitrion"));
        entidad.setMundial(mundial);

        Seleccion seleccionLocal = new Seleccion();
        seleccionLocal.setIdSeleccion(rs.getInt("id_seleccion_local"));
        seleccionLocal.setNombre(rs.getString("nombre_seleccion_local"));
        entidad.setSeleccionLocal(seleccionLocal);

        Seleccion seleccionVisitante = new Seleccion();
        seleccionVisitante.setIdSeleccion(rs.getInt("id_seleccion_visitante"));
        seleccionVisitante.setNombre(rs.getString("nombre_seleccion_visitante"));
        entidad.setSeleccionVisitante(seleccionVisitante);

        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar partidos: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de partidos.", e);
    }

    return lista;
  }

  @Override
  public void actualizar(Partido entidad) {
    String sql = "UPDATE mundial_fifa.partido SET id_mundial = ?, fecha = ?, fase = ?, id_seleccion_local = ?, id_seleccion_visitante = ?, goles_local = ?, goles_visitante = ?, estado = ? WHERE id_partido = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, entidad.getIdMundial());
      stmt.setDate(2, Date.valueOf(entidad.getFecha()));
      stmt.setString(3, entidad.getFase());
      stmt.setInt(4, entidad.getIdSeleccionLocal());
      stmt.setInt(5, entidad.getIdSeleccionVisitante());
      stmt.setInt(6, entidad.getGolesLocal());
      stmt.setInt(7, entidad.getGolesVisitante());
      stmt.setBoolean(8, entidad.getEstado());
      stmt.setInt(9, entidad.getIdPartido());

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException(
            "No se pudo actualizar. El partido con ID " + entidad.getIdPartido() + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al actualizar partido: " + e.getMessage());
      throw new RuntimeException("No se pudo modificar el partido en la base de datos.", e);
    }
  }

  @Override
  public void eliminar(Integer id) {
    String sql = "DELETE FROM mundial_fifa.partido WHERE id_partido = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException("No se pudo eliminar. El partido con ID " + id + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al eliminar partido: " + e.getMessage());
      throw new RuntimeException("No se pudo eliminar el partido. Verifique que no tenga registros asociados.",
          e);
    }
  }

  @Override
  public Partido buscarPorId(Integer id) {
    String sql = "SELECT id_partido, id_mundial, fecha, fase, id_seleccion_local, id_seleccion_visitante, goles_local, goles_visitante, estado FROM mundial_fifa.partido WHERE id_partido = ?";
    Partido entidad = null;

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          entidad = new Partido();
          entidad.setIdPartido(rs.getInt("id_partido"));
          entidad.setIdMundial(rs.getInt("id_mundial"));
          entidad.setFecha(rs.getDate("fecha").toLocalDate());
          entidad.setFase(rs.getString("fase"));
          entidad.setIdSeleccionLocal(rs.getInt("id_seleccion_local"));
          entidad.setIdSeleccionVisitante(rs.getInt("id_seleccion_visitante"));
          entidad.setGolesLocal(rs.getInt("goles_local"));
          entidad.setGolesVisitante(rs.getInt("goles_visitante"));
          entidad.setEstado(rs.getBoolean("estado"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al buscar partido por ID: " + e.getMessage());
      throw new RuntimeException("No se pudo consultar el partido en la base de datos.", e);
    }

    return entidad;
  }
}