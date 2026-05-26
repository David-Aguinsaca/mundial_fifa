package mundial_fifa.model.entity;

import java.math.BigDecimal;

public class EstadisticasPartidoEquipo {

    private Integer idEstadistica;
    private Integer idPartido;
    private Integer idSeleccion;
    private BigDecimal posesionPorcentaje;
    private int tirosAlArco;
    private int tirosEsquina;
    private int tirosLibres;
    private int faltas;
    private BigDecimal precisionPasesPorcentaje;
    private int fueraDeJuego;
    private int salvadasPortero;

    public EstadisticasPartidoEquipo() {
    }

    public EstadisticasPartidoEquipo(Integer idPartido, Integer idSeleccion,
                                     BigDecimal posesionPorcentaje, BigDecimal precisionPasesPorcentaje) {
        this.idPartido = idPartido;
        this.idSeleccion = idSeleccion;
        this.posesionPorcentaje = posesionPorcentaje;
        this.precisionPasesPorcentaje = precisionPasesPorcentaje;
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
