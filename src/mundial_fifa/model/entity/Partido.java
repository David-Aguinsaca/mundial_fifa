package mundial_fifa.model.entity;

import java.time.LocalDate;

public class Partido {

    private Integer idPartido;
    private Integer idMundial;
    private LocalDate fecha;
    private String fase;
    private Integer idSeleccionLocal;
    private Integer idSeleccionVisitante;
    private int golesLocal;
    private int golesVisitante;

    public Partido() {
    }

    public Partido(Integer idPartido, Integer idMundial, LocalDate fecha, String fase,
                   Integer idSeleccionLocal, Integer idSeleccionVisitante,
                   int golesLocal, int golesVisitante) {
        this.idPartido = idPartido;
        this.idMundial = idMundial;
        this.fecha = fecha;
        this.fase = fase;
        this.idSeleccionLocal = idSeleccionLocal;
        this.idSeleccionVisitante = idSeleccionVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public Integer getIdMundial() {
        return idMundial;
    }

    public void setIdMundial(Integer idMundial) {
        this.idMundial = idMundial;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Integer getIdSeleccionLocal() {
        return idSeleccionLocal;
    }

    public void setIdSeleccionLocal(Integer idSeleccionLocal) {
        this.idSeleccionLocal = idSeleccionLocal;
    }

    public Integer getIdSeleccionVisitante() {
        return idSeleccionVisitante;
    }

    public void setIdSeleccionVisitante(Integer idSeleccionVisitante) {
        this.idSeleccionVisitante = idSeleccionVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido=" + idPartido +
                ", idMundial=" + idMundial +
                ", fecha=" + fecha +
                ", fase='" + fase + '\'' +
                ", idSeleccionLocal=" + idSeleccionLocal +
                ", idSeleccionVisitante=" + idSeleccionVisitante +
                ", golesLocal=" + golesLocal +
                ", golesVisitante=" + golesVisitante +
                '}';
    }
}
