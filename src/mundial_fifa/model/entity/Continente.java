package mundial_fifa.model.entity;

public class Continente extends Base {

	private int idContinente;
	private String nombre;

	public Continente() {}

	public Continente(int idContinente, String nombre) {
		super();
		this.idContinente = idContinente;
		this.nombre = nombre;
	}

	public int getIdContinente() {
		return idContinente;
	}

	public void setIdContinente(int idContinente) {
		this.idContinente = idContinente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Continente{" + "idContinente=" + idContinente + ", nombre='" + nombre + '\'' + '}';
	}
}
