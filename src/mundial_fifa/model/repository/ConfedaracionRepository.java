package mundial_fifa.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import mundial_fifa.model.entity.Confederacion;
import mundial_fifa.model.entity.Continente;

public class ConfedaracionRepository implements GenericRepository<Confederacion, Integer> {

  @Override
  public void insertar(Confederacion entidad) {

    String sql = "INSERT INTO mundial_fifa.confederacion (nombre, siglas, id_continente) VALUES (?, ?, ?)";

    try {

      Connection conn = DatabaseConnection.getConnection();

      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Asignar valores a la consulta INSERT remplazando el value "?"
        stmt.setString(1, entidad.getNombre());
        stmt.setString(2, entidad.getSiglas());
        stmt.setInt(3, entidad.getIdContinente());

        // Ejecutar consulta
        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) { // el cursor avanza a la siguiente fila disponible
            entidad.setIdContinente(rs.getInt(1));
          }
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al insertar el confederacion: " + e.getMessage());
      System.err.println("Código de estado SQL: " + e.getSQLState());
      throw new RuntimeException("No se pudo registrar el confederacion debido a un problema en la base de datos.",
          e);
    }
  }

  @Override
  public List<Confederacion> listarTodos() {

    String sql = "SELECT c.*, " +
            "c2.nombre AS nombre_continente " +
            "FROM mundial_fifa.confederacion c " +
            "INNER JOIN mundial_fifa.continente c2 ON c.id_continente = c2.id_continente " +
            "ORDER BY c.fecha_creacion DESC";
            
    List<Confederacion> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) { // Como no lleva '?', podemos ejecutar directamente

      while (rs.next()) {
        Confederacion entidad = new Confederacion();
        entidad.setIdConfederacion(rs.getInt("id_confederacion"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setSiglas(rs.getString("siglas"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        entidad.setIdContinente(rs.getInt("id_continente"));

        Continente continente = new Continente();
        continente.setNombre(rs.getString("nombre_continente"));
        continente.setIdContinente(rs.getInt("id_continente"));

        entidad.setContinente(continente);
        
        lista.add(entidad); // Añadimos cada registro a la lista dinámica
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar confederacion: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de confederacion.", e);
    }

    return lista;
  }

  @Override
  public void actualizar(Confederacion entidad) {

    String sql = "UPDATE mundial_fifa.confederacion SET nombre = ?, estado = ?, siglas = ?, id_continente = ? WHERE id_confederacion = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      // Asignamos los parámetros en el orden de los '?'
      stmt.setString(1, entidad.getNombre());
      stmt.setBoolean(2, entidad.getEstado());
      stmt.setString(3, entidad.getSiglas());
      stmt.setInt(4, entidad.getIdContinente());
      stmt.setInt(5, entidad.getIdConfederacion());

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException(
            "No se pudo actualizar. El confederacion con ID " + entidad.getIdConfederacion() + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al actualizar confederacion: " + e.getMessage());
      throw new RuntimeException("No se pudo modificar el confederacion en la base de datos.", e);
    }

  }

  @Override
  public void eliminar(Integer id) {

    String sql = "DELETE FROM mundial_fifa.confederacion WHERE id_confederacion = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException("No se pudo eliminar. El confederacion con ID " + id + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al eliminar confederacion: " + e.getMessage());
      // Si PostgreSQL lanza un error aquí, probablemente sea por una restricción de
      // llave foránea (FK)
      // (ej. intentas borrar un confederacion que ya tiene países asociados)
      throw new RuntimeException("No se pudo eliminar el confederacion. Verifique que no tenga registros asociados.",
          e);
    }

  }

  @Override
  public Confederacion buscarPorId(Integer id) {
    String sql = "SELECT id_confederacion, nombre, siglas, estado, id_continente FROM mundial_fifa.confederacion WHERE id_confederacion = ?";
    Confederacion entidad = null;

    // El try-with-resources cierra automáticamente el stmt y el rs al terminar
    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id); // Asignamos el ID al único '?'

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          entidad = new Confederacion();
          // Extraemos los datos del ResultSet y los pasamos al objeto Java
          entidad.setIdConfederacion(rs.getInt("id_confederacion"));
          entidad.setIdContinente(rs.getInt("id_continente"));
          entidad.setNombre(rs.getString("nombre"));
          entidad.setSiglas(rs.getString("siglas"));
          entidad.setEstado(rs.getBoolean("estado"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al buscar confederacion por ID: " + e.getMessage());
      throw new RuntimeException("No se pudo consultar el confederacion en la base de datos.", e);
    }

    return entidad;
  }

  public List<Confederacion> listarTodosConEstadoActivo() {
    String sql = "SELECT c.*, " +
            "c2.nombre AS nombre_continente " +
            "FROM mundial_fifa.confederacion c " +
            "INNER JOIN mundial_fifa.continente c2 ON c.id_continente = c2.id_continente " +
            "WHERE c.estado = true " +
            "ORDER BY c.fecha_creacion DESC";

    List<Confederacion> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Confederacion entidad = new Confederacion();
        entidad.setIdConfederacion(rs.getInt("id_confederacion"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setSiglas(rs.getString("siglas"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        entidad.setIdContinente(rs.getInt("id_continente"));

        Continente continente = new Continente();
        continente.setNombre(rs.getString("nombre_continente"));
        continente.setIdContinente(rs.getInt("id_continente"));

        entidad.setContinente(continente);

        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar confederaciones activas: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de confederaciones activas.", e);
    }

    return lista;
  }
}
