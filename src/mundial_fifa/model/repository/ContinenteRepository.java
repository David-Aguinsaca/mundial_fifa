package mundial_fifa.model.repository;

import mundial_fifa.model.entity.Continente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// implements para polimorfimo

public class ContinenteRepository implements GenericRepository<Continente, Integer> {

  @Override
  public void insertar(Continente entidad) {
    String sql = "INSERT INTO mundial_fifa.continente (nombre) VALUES (?)";

    // Usamos try-with-resources para qusse el PreparedStatement se cierre
    // automáticamente al terminar
    try {

      // Conexion base de datos
      Connection conn = DatabaseConnection.getConnection();

      // Encargada de preparar y ejecutar consultas SQL
      // Debe preparar un canal para devolverte los valores de las llaves primarias
      // autogeneradas
      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Asignar valores a la consulta INSERT remplazando el value "?"
        stmt.setString(1, entidad.getNombre());

        // Ejecutar consulta
        stmt.executeUpdate();

        // Obtener el ID autogenerado por PostgreSQL
        // Estructura de datos que actúa como una tabla virtual
        // donde se almacenan los resultados devueltos por la base de dato

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) { // el cursor avanza a la siguiente fila disponible
            entidad.setIdContinente(rs.getInt(1));
          }
        }
      }
    } catch (SQLException e) {
      // 1. Imprimir el error en la consola del desarrollador (o usar un Logger)
      System.err.println("Error SQL al insertar el continente: " + e.getMessage());
      System.err.println("Código de estado SQL: " + e.getSQLState());

      // 2. Lanza una excepción personalizada o una RuntimeException hacia la capa
      // Service.
      // Hacemos esto para que el SERVICE sepa que algo falló y pueda decidir qué
      // hacer.
      throw new RuntimeException("No se pudo registrar el continente debido a un problema en la base de datos.",
          e);
    }
  }

  @Override
  public List<Continente> listarTodos() {
    String sql = "SELECT * FROM mundial_fifa.continente ORDER BY fecha_creacion DESC";
   
    List<Continente> lista = new ArrayList<>();

    // consulta a la base de datos
    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) { // Como no lleva '?', podemos ejecutar directamente

      while (rs.next()) {
        Continente entidad = new Continente();
        entidad.setIdContinente(rs.getInt("id_continente"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));

        lista.add(entidad); // Añadimos cada registro a la lista dinámica
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar continentes: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de continentes.", e);
    }

    return lista;
  }

  @Override
  public void actualizar(Continente continente) {
    String sql = "UPDATE mundial_fifa.continente SET nombre = ?, estado = ? WHERE id_continente = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      // Asignamos los parámetros en el orden de los '?'
      stmt.setString(1, continente.getNombre());
      stmt.setBoolean(2, continente.getEstado());
      stmt.setInt(3, continente.getIdContinente());

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException(
            "No se pudo actualizar. El continente con ID " + continente.getIdContinente() + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al actualizar continente: " + e.getMessage());
      throw new RuntimeException("No se pudo modificar el continente en la base de datos.", e);
    }
  }

  @Override
  public void eliminar(Integer id) {
    String sql = "DELETE FROM mundial_fifa.continente WHERE id_continente = ?";

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id);

      int filasAfectadas = stmt.executeUpdate();

      if (filasAfectadas == 0) {
        throw new SQLException("No se pudo eliminar. El continente con ID " + id + " no existe.");
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al eliminar continente: " + e.getMessage());
      // Si PostgreSQL lanza un error aquí, probablemente sea por una restricción de
      // llave foránea (FK)
      // (ej. intentas borrar un continente que ya tiene países asociados)
      throw new RuntimeException("No se pudo eliminar el continente. Verifique que no tenga registros asociados.",
          e);
    }
  }

  @Override
  public Continente buscarPorId(Integer id) {
    String sql = "SELECT id_continente, nombre, estado FROM mundial_fifa.continente WHERE id_continente = ?";
    Continente entidad = null;

    // El try-with-resources cierra automáticamente el stmt y el rs al terminar
    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {

      stmt.setInt(1, id); // Asignamos el ID al único '?'

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          entidad = new Continente();
          // Extraemos los datos del ResultSet y los pasamos al objeto Java
          entidad.setIdContinente(rs.getInt("id_continente"));
          entidad.setNombre(rs.getString("nombre"));
          entidad.setEstado(rs.getBoolean("estado"));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al buscar continente por ID: " + e.getMessage());
      throw new RuntimeException("No se pudo consultar el continente en la base de datos.", e);
    }

    return entidad; // Devuelve el continente encontrado o null si no existía
  }

  @Override
  public List<Continente> listarTodoByEstado() {
    String sql = "SELECT * FROM mundial_fifa.continente " +
            "WHERE estado = true " +
            "ORDER BY fecha_creacion DESC";
    List<Continente> lista = new ArrayList<>();

    try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Continente entidad = new Continente();
        entidad.setIdContinente(rs.getInt("id_continente"));
        entidad.setNombre(rs.getString("nombre"));
        entidad.setEstado(rs.getBoolean("estado"));
        entidad.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        entidad.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));

        lista.add(entidad);
      }
    } catch (SQLException e) {
      System.err.println("Error SQL al listar continentes: " + e.getMessage());
      throw new RuntimeException("No se pudo obtener la lista de continentes.", e);
    }

    return lista;
  }

}
