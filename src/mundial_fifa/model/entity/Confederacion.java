package mundial_fifa.model.entity;

public class Confederacion extends Base {

    private Integer idConfederacion;
    private String nombre;
    private String siglas;
    private Integer idContinente;
    private Continente continente;
    
    public Confederacion() {
    }


    public Confederacion(Integer idConfederacion, String nombre, String siglas, Integer idContinente, Continente continente) {
        this.idConfederacion = idConfederacion;
        this.nombre = nombre;
        this.siglas = siglas;
        this.idContinente = idContinente;
        this.continente = continente;
    }

    public Integer getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(Integer idConfederacion) {
        this.idConfederacion = idConfederacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public Integer getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(Integer idContinente) {
        this.idContinente = idContinente;
    }

    public Continente getContinente() {
        return continente;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }

    @Override
    public String toString() {
        return "Confederacion{" +
                "idConfederacion=" + idConfederacion +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", idContinente=" + idContinente +
                '}';
    }
}
