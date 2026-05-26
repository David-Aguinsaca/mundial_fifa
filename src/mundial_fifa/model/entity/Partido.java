package mundial_fifa.model.entity;

import java.time.LocalDate;

public class Partido extends Base {

    private Integer idPartido;
    private Integer idMundial;
    private LocalDate fecha;
    private String fase;
    private Integer idSeleccionLocal;
    private Integer idSeleccionVisitante;
    private int golesLocal;
    private int golesVisitante;
    private Mundial mundial;
    private Seleccion seleccionLocal;
    private Seleccion seleccionVisitante;

    public Partido() {
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

    public Mundial getMundial() {
        return mundial;
    }

    public void setMundial(Mundial mundial) {
        this.mundial = mundial;
    }

    public Seleccion getSeleccionLocal() {
        return seleccionLocal;
    }

    public void setSeleccionLocal(Seleccion seleccionLocal) {
        this.seleccionLocal = seleccionLocal;
    }

    public Seleccion getSeleccionVisitante() {
        return seleccionVisitante;
    }

    public void setSeleccionVisitante(Seleccion seleccionVisitante) {
        this.seleccionVisitante = seleccionVisitante;
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
