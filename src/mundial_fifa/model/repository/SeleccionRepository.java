package mundial_fifa.model.repository;

import mundial_fifa.model.entity.Confederacion;
import mundial_fifa.model.entity.Seleccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionRepository implements GenericRepository<Seleccion, Integer> {

  @Override
  public void insertar(Seleccion entidad) {
    String sql = "INSERT INTO mundial_fifa.seleccion (nombre, id_confederacion) VALUES (?, ?)";

    try {
      Connection conn = DatabaseConnection.getConnection();

      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, entidad.getNombre());
        stmt.setInt(2, entidad.getIdConfederacion());

        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            entidad.setIdSeleccion(rs.getInt(1));
          }
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al insertar la seleccion: " + e.getMessage());
      System.err.println("Código de estado SQL: " + e.getSQLState());
      throw new RuntimeException("No se pudo registrar la seleccion debido a un problema en la base de datos.",
          e);
    }
  }

  @Override
  public List<Seleccion> listarTodos() {
    String sql = "SELECT s.*, " +
            "c.nombre AS nombre_confederacion, " +
            "c.siglas AS siglas_confederacion " +
            "FROM mundial_fifa.seleccion s " +
            "INNER JOIN mundial_fifa.confederacion c ON s.id_confederacion = c.id_confederacion " +
            "ORDER BY s.fecha_creacion DESC";

    List<Seleccion> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Seleccion entidad = new Seleccion();
        entidad.setIdSeleccion(rs.getInt("id_seleccion"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        entidad.setIdConfederacion(rs.getInt("id_confederacion"));

        Confederacion confederacion = new Confederacion();
        confederacion.setIdConfederacion(rs.getInt("id_confederacion"));
        confederacion.setNombre(rs.getString("nombre_confederacion"));
        confederacion.setSiglas(rs.getString("siglas_confederacion"));

        entidad.setConfederacion(confederacion);

        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar selecciones: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de selecciones.", e);
    }

    return lista;
  }

  @Override
  public void actualizar(Seleccion entidad) {
    String sql = "UPDATE mundial_fifa.seleccion SET nombre = ?, estado = ?, id_confederacion = ? WHERE id_seleccion = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setString(1, entidad.getNombre());
      stmt.setBoolean(2, entidad.getEstado());
      stmt.setInt(3, entidad.getIdConfederacion());
      stmt.setInt(4, entidad.getIdSeleccion());

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException(
            "No se pudo actualizar. La seleccion con ID " + entidad.getIdSeleccion() + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al actualizar seleccion: " + e.getMessage());
      throw new RuntimeException("No se pudo modificar la seleccion en la base de datos.", e);
    }
  }

  @Override
  public void eliminar(Integer id) {
    String sql = "DELETE FROM mundial_fifa.seleccion WHERE id_seleccion = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException("No se pudo eliminar. La seleccion con ID " + id + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al eliminar seleccion: " + e.getMessage());
      throw new RuntimeException("No se pudo eliminar la seleccion. Verifique que no tenga registros asociados.",
          e);
    }
  }

  @Override
  public Seleccion buscarPorId(Integer id) {
    String sql = "SELECT id_seleccion, nombre, estado, id_confederacion FROM mundial_fifa.seleccion WHERE id_seleccion = ?";
    Seleccion entidad = null;

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          entidad = new Seleccion();
          entidad.setIdSeleccion(rs.getInt("id_seleccion"));
          entidad.setNombre(rs.getString("nombre"));
          entidad.setEstado(rs.getBoolean("estado"));
          entidad.setIdConfederacion(rs.getInt("id_confederacion"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al buscar seleccion por ID: " + e.getMessage());
      throw new RuntimeException("No se pudo consultar la seleccion en la base de datos.", e);
    }

    return entidad;
  }


  @Override
  public List<Seleccion> listarTodoByEstado() {
    String sql = "SELECT s.* " +
            "FROM mundial_fifa.seleccion s " +
            "WHERE s.estado = true "+
            "ORDER BY s.fecha_creacion DESC";

    List<Seleccion> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Seleccion entidad = new Seleccion();
        entidad.setIdSeleccion(rs.getInt("id_seleccion"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        entidad.setIdConfederacion(rs.getInt("id_confederacion"));
        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar selecciones: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de selecciones.", e);
    }

    return lista;
  }

}