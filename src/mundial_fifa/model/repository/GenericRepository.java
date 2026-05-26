package mundial_fifa.model.repository;

import java.util.List;

/* Los genéricos en Java son una característica del lenguaje
que permite definir clases, interfaces y métodos que pueden 
operar con tipos de datos parametrizables, es decir, tipos que 
se especifican en el momento de la instanciación o llamada.  
Esto permite escribir código flexible y reutilizable que funciona 
con cualquier tipo de dato sin perder la seguridad de tipos en 
tiempo de compilación.
 */

public interface GenericRepository<T, ID> {
    void insertar(T entidad);
    List<T> listarTodos();
    void actualizar(T entidad);
    void eliminar(ID id);
    T buscarPorId(ID id);
}
