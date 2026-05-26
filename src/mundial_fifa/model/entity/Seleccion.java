package mundial_fifa.model.entity;

public class Seleccion extends Base {

    private Integer idSeleccion;
    private String nombre;
    private Integer idConfederacion;
    private Confederacion confederacion;

    public Seleccion() {
    }

    public Seleccion(String nombre, Integer idConfederacion) {
        this.nombre = nombre;
        this.idConfederacion = idConfederacion;
    }

    public Seleccion(Integer idSeleccion, String nombre, Integer idConfederacion) {
        this.idSeleccion = idSeleccion;
        this.nombre = nombre;
        this.idConfederacion = idConfederacion;
    }

    public Integer getIdSeleccion() {
        return idSeleccion;
    }

    public void setIdSeleccion(Integer idSeleccion) {
        this.idSeleccion = idSeleccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(Integer idConfederacion) {
        this.idConfederacion = idConfederacion;
    }

    public Confederacion getConfederacion() {
        return confederacion;
    }

    public void setConfederacion(Confederacion confederacion) {
        this.confederacion = confederacion;
    }

    @Override
    public String toString() {
        return "Seleccion{" +
                "idSeleccion=" + idSeleccion +
                ", nombre='" + nombre + '\'' +
                ", idConfederacion=" + idConfederacion +
                '}';
    }
}
