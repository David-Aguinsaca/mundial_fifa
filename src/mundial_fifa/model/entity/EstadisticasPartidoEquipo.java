package mundial_fifa.model.entity;

import java.math.BigDecimal;

public class EstadisticasPartidoEquipo extends Base {

    private Integer idEstadistica;
    private Integer idPartido;
    private Integer idSeleccion;
    //tipo de dato BigDecimal para tener mas precision
    private BigDecimal posesionPorcentaje;
    private int tirosAlArco;
    private int tirosEsquina;
    private int tirosLibres;
    private int faltas;
    private BigDecimal precisionPasesPorcentaje;
    private int fueraDeJuego;
    private int salvadasPortero;
    private Partido partido;
    private Seleccion seleccion;

    public EstadisticasPartidoEquipo() {
    }

    public Integer getIdEstadistica() {
        return idEstadistica;
    }

    public void setIdEstadistica(Integer idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public Integer getIdSeleccion() {
        return idSeleccion;
    }

    public void setIdSeleccion(Integer idSeleccion) {
        this.idSeleccion = idSeleccion;
    }

    public BigDecimal getPosesionPorcentaje() {
        return posesionPorcentaje;
    }

    public void setPosesionPorcentaje(BigDecimal posesionPorcentaje) {
        this.posesionPorcentaje = posesionPorcentaje;
    }

    public int getTirosAlArco() {
        return tirosAlArco;
    }

    public void setTirosAlArco(int tirosAlArco) {
        this.tirosAlArco = tirosAlArco;
    }

    public int getTirosEsquina() {
        return tirosEsquina;
    }

    public void setTirosEsquina(int tirosEsquina) {
        this.tirosEsquina = tirosEsquina;
    }

    public int getTirosLibres() {
        return tirosLibres;
    }

    public void setTirosLibres(int tirosLibres) {
        this.tirosLibres = tirosLibres;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public BigDecimal getPrecisionPasesPorcentaje() {
        return precisionPasesPorcentaje;
    }

    public void setPrecisionPasesPorcentaje(BigDecimal precisionPasesPorcentaje) {
        this.precisionPasesPorcentaje = precisionPasesPorcentaje;
    }

    public int getFueraDeJuego() {
        return fueraDeJuego;
    }

    public void setFueraDeJuego(int fueraDeJuego) {
        this.fueraDeJuego = fueraDeJuego;
    }

    public int getSalvadasPortero() {
        return salvadasPortero;
    }

    public void setSalvadasPortero(int salvadasPortero) {
        this.salvadasPortero = salvadasPortero;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Seleccion getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Seleccion seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public String toString() {
        return "EstadisticasPartidoEquipo{" +
                "idEstadistica=" + idEstadistica +
                ", idPartido=" + idPartido +
                ", idSeleccion=" + idSeleccion +
                ", posesionPorcentaje=" + posesionPorcentaje +
                ", tirosAlArco=" + tirosAlArco +
                ", tirosEsquina=" + tirosEsquina +
                ", tirosLibres=" + tirosLibres +
                ", faltas=" + faltas +
                ", precisionPasesPorcentaje=" + precisionPasesPorcentaje +
                ", fueraDeJuego=" + fueraDeJuego +
                ", salvadasPortero=" + salvadasPortero +
                '}';
    }
}
