package mundial_fifa.model.entity;

public class Mundial {

    private Integer idMundial;
    private Integer anio;
    private String paisAnfitrion;

    public Mundial() {
    }

    public Mundial(Integer anio, String paisAnfitrion) {
        this.anio = anio;
        this.paisAnfitrion = paisAnfitrion;
    }

    public Mundial(Integer idMundial, Integer anio, String paisAnfitrion) {
        this.idMundial = idMundial;
        this.anio = anio;
        this.paisAnfitrion = paisAnfitrion;
    }

    public Integer getIdMundial() {
        return idMundial;
    }

    public void setIdMundial(Integer idMundial) {
        this.idMundial = idMundial;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getPaisAnfitrion() {
        return paisAnfitrion;
    }

    public void setPaisAnfitrion(String paisAnfitrion) {
        this.paisAnfitrion = paisAnfitrion;
    }

    @Override
    public String toString() {
        return "Mundial{" +
                "idMundial=" + idMundial +
                ", anio=" + anio +
                ", paisAnfitrion='" + paisAnfitrion + '\'' +
                '}';
    }
}
