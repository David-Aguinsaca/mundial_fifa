package mundial_fifa.model.entity;

import java.sql.Timestamp;


public class Base {
  public Boolean estado;
  public Timestamp fechaCreacion;
  public Timestamp fechaModificacion;
  
  public Base() {}
  
  public Boolean getEstado() {
	return estado;
  }
  public void setEstado(Boolean estado) {
	this.estado = estado;
  }
  public Timestamp getFechaCreacion() {
	return fechaCreacion;
  }
  public void setFechaCreacion(Timestamp fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
  }
  public Timestamp getFechaModificacion() {
	return fechaModificacion;
  }
  public void setFechaModificacion(Timestamp fechaModificacion) {
	this.fechaModificacion = fechaModificacion;
  }
  
  
  
  
}
