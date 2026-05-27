package mundial_fifa.model.repository;

import mundial_fifa.model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasPartidoEquipoRepository implements GenericRepository<EstadisticasPartidoEquipo, Integer> {

  @Override
  public void insertar(EstadisticasPartidoEquipo entidad) {
    String sql = "INSERT INTO mundial_fifa.estadistica_partido_equipo " +
            "(id_partido, id_seleccion, posesion_porcentaje, tiros_al_arco, tiros_esquina, tiros_libres, " +
            "faltas, precision_pases_porcentaje, fuera_de_juego, salvadas_portero) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
      Connection conn = DatabaseConnection.getConnection();

      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, entidad.getIdPartido());
        stmt.setInt(2, entidad.getIdSeleccion());
        stmt.setBigDecimal(3, entidad.getPosesionPorcentaje());
        stmt.setInt(4, entidad.getTirosAlArco());
        stmt.setInt(5, entidad.getTirosEsquina());
        stmt.setInt(6, entidad.getTirosLibres());
        stmt.setInt(7, entidad.getFaltas());
        stmt.setBigDecimal(8, entidad.getPrecisionPasesPorcentaje());
        stmt.setInt(9, entidad.getFueraDeJuego());
        stmt.setInt(10, entidad.getSalvadasPortero());

        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            entidad.setIdEstadistica(rs.getInt(1));
          }
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al insertar la estadistica: " + e.getMessage());
      System.err.println("Código de estado SQL: " + e.getSQLState());
      throw new RuntimeException("No se pudo registrar la estadistica debido a un problema en la base de datos.",
          e);
    }
  }

  @Override
  public List<EstadisticasPartidoEquipo> listarTodos() {
    String sql = "SELECT e.*, " +
            "p.fecha AS partido_fecha, p.fase AS partido_fase, " +
            "s.nombre AS nombre_seleccion " +
            "FROM mundial_fifa.estadistica_partido_equipo e " +
            "INNER JOIN mundial_fifa.partido p ON e.id_partido = p.id_partido " +
            "INNER JOIN mundial_fifa.seleccion s ON e.id_seleccion = s.id_seleccion " +
            "ORDER BY e.fecha_creacion DESC";

    List<EstadisticasPartidoEquipo> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        EstadisticasPartidoEquipo entidad = new EstadisticasPartidoEquipo();
        entidad.setIdEstadistica(rs.getInt("id_estadistica"));
        entidad.setIdPartido(rs.getInt("id_partido"));
        entidad.setIdSeleccion(rs.getInt("id_seleccion"));
        entidad.setPosesionPorcentaje(rs.getBigDecimal("posesion_porcentaje"));
        entidad.setTirosAlArco(rs.getInt("tiros_al_arco"));
        entidad.setTirosEsquina(rs.getInt("tiros_esquina"));
        entidad.setTirosLibres(rs.getInt("tiros_libres"));
        entidad.setFaltas(rs.getInt("faltas"));
        entidad.setPrecisionPasesPorcentaje(rs.getBigDecimal("precision_pases_porcentaje"));
        entidad.setFueraDeJuego(rs.getInt("fuera_de_juego"));
        entidad.setSalvadasPortero(rs.getInt("salvadas_portero"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));

        Partido partido = new Partido();
        partido.setIdPartido(rs.getInt("id_partido"));
        partido.setFecha(rs.getDate("partido_fecha").toLocalDate());
        partido.setFase(rs.getString("partido_fase"));
        entidad.setPartido(partido);

        Seleccion seleccion = new Seleccion();
        seleccion.setIdSeleccion(rs.getInt("id_seleccion"));
        seleccion.setNombre(rs.getString("nombre_seleccion"));
        entidad.setSeleccion(seleccion);

        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar estadisticas: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de estadisticas.", e);
    }

    return lista;
  }

  @Override
  public void actualizar(EstadisticasPartidoEquipo entidad) {
    String sql = "UPDATE mundial_fifa.estadistica_partido_equipo SET " +
            "id_partido = ?, id_seleccion = ?, posesion_porcentaje = ?, tiros_al_arco = ?, " +
            "tiros_esquina = ?, tiros_libres = ?, faltas = ?, precision_pases_porcentaje = ?, " +
            "fuera_de_juego = ?, salvadas_portero = ?, estado = ? " +
            "WHERE id_estadistica = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, entidad.getIdPartido());
      stmt.setInt(2, entidad.getIdSeleccion());
      stmt.setBigDecimal(3, entidad.getPosesionPorcentaje());
      stmt.setInt(4, entidad.getTirosAlArco());
      stmt.setInt(5, entidad.getTirosEsquina());
      stmt.setInt(6, entidad.getTirosLibres());
      stmt.setInt(7, entidad.getFaltas());
      stmt.setBigDecimal(8, entidad.getPrecisionPasesPorcentaje());
      stmt.setInt(9, entidad.getFueraDeJuego());
      stmt.setInt(10, entidad.getSalvadasPortero());
      stmt.setBoolean(11, entidad.getEstado());
      stmt.setInt(12, entidad.getIdEstadistica());

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException(
            "No se pudo actualizar. La estadistica con ID " + entidad.getIdEstadistica() + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al actualizar estadistica: " + e.getMessage());
      throw new RuntimeException("No se pudo modificar la estadistica en la base de datos.", e);
    }
  }

  @Override
  public void eliminar(Integer id) {
    String sql = "DELETE FROM mundial_fifa.estadistica_partido_equipo WHERE id_estadistica = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException("No se pudo eliminar. La estadistica con ID " + id + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al eliminar estadistica: " + e.getMessage());
      throw new RuntimeException("No se pudo eliminar la estadistica.", e);
    }
  }

  public List<EstadisticasPartidoEquipo> listarPorPartido(Integer idPartido) {
    String sql = "SELECT e.*, " +
            "p.fecha AS partido_fecha, p.fase AS partido_fase, " +
            "s.nombre AS nombre_seleccion " +
            "FROM mundial_fifa.estadistica_partido_equipo e " +
            "INNER JOIN mundial_fifa.partido p ON e.id_partido = p.id_partido " +
            "INNER JOIN mundial_fifa.seleccion s ON e.id_seleccion = s.id_seleccion " +
            "WHERE e.id_partido = ? " +
            "ORDER BY e.id_estadistica";

    List<EstadisticasPartidoEquipo> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
      stmt.setInt(1, idPartido);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          EstadisticasPartidoEquipo entidad = new EstadisticasPartidoEquipo();
          entidad.setIdEstadistica(rs.getInt("id_estadistica"));
          entidad.setIdPartido(rs.getInt("id_partido"));
          entidad.setIdSeleccion(rs.getInt("id_seleccion"));
          entidad.setPosesionPorcentaje(rs.getBigDecimal("posesion_porcentaje"));
          entidad.setTirosAlArco(rs.getInt("tiros_al_arco"));
          entidad.setTirosEsquina(rs.getInt("tiros_esquina"));
          entidad.setTirosLibres(rs.getInt("tiros_libres"));
          entidad.setFaltas(rs.getInt("faltas"));
          entidad.setPrecisionPasesPorcentaje(rs.getBigDecimal("precision_pases_porcentaje"));
          entidad.setFueraDeJuego(rs.getInt("fuera_de_juego"));
          entidad.setSalvadasPortero(rs.getInt("salvadas_portero"));
          entidad.setEstado(rs.getBoolean("estado"));
          entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
          entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));

          Partido partido = new Partido();
          partido.setIdPartido(rs.getInt("id_partido"));
          partido.setFecha(rs.getDate("partido_fecha").toLocalDate());
          partido.setFase(rs.getString("partido_fase"));
          entidad.setPartido(partido);

          Seleccion seleccion = new Seleccion();
          seleccion.setIdSeleccion(rs.getInt("id_seleccion"));
          seleccion.setNombre(rs.getString("nombre_seleccion"));
          entidad.setSeleccion(seleccion);

          lista.add(entidad);
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar estadisticas por partido: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener las estadisticas del partido.", e);
    }

    return lista;
  }

  @Override
  public EstadisticasPartidoEquipo buscarPorId(Integer id) {
    String sql = "SELECT id_estadistica, id_partido, id_seleccion, posesion_porcentaje, " +
            "tiros_al_arco, tiros_esquina, tiros_libres, faltas, precision_pases_porcentaje, " +
            "fuera_de_juego, salvadas_portero, estado " +
            "FROM mundial_fifa.estadistica_partido_equipo WHERE id_estadistica = ?";
    EstadisticasPartidoEquipo entidad = null;

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          entidad = new EstadisticasPartidoEquipo();
          entidad.setIdEstadistica(rs.getInt("id_estadistica"));
          entidad.setIdPartido(rs.getInt("id_partido"));
          entidad.setIdSeleccion(rs.getInt("id_seleccion"));
          entidad.setPosesionPorcentaje(rs.getBigDecimal("posesion_porcentaje"));
          entidad.setTirosAlArco(rs.getInt("tiros_al_arco"));
          entidad.setTirosEsquina(rs.getInt("tiros_esquina"));
          entidad.setTirosLibres(rs.getInt("tiros_libres"));
          entidad.setFaltas(rs.getInt("faltas"));
          entidad.setPrecisionPasesPorcentaje(rs.getBigDecimal("precision_pases_porcentaje"));
          entidad.setFueraDeJuego(rs.getInt("fuera_de_juego"));
          entidad.setSalvadasPortero(rs.getInt("salvadas_portero"));
          entidad.setEstado(rs.getBoolean("estado"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al buscar estadistica por ID: " + e.getMessage());
      throw new RuntimeException("No se pudo consultar la estadistica en la base de datos.", e);
    }

    return entidad;
  }
}