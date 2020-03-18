package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion {
	
	private long id;
	private double tamanho;
	private double precioMes;
	private long idPersona;
	
	public Habitacion() {
		this.id=0;
		this.tamanho=0;
		this.precioMes=0;
		this.idPersona=0;
		
	}
	public Habitacion(long id, double tamanho, double precioMes, long idPersona) {
		
		this.id = id;
		this.tamanho = tamanho;
		this.precioMes = precioMes;
		this.idPersona = idPersona;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getTamanho() {
		return tamanho;
	}
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	public double getPrecioMes() {
		return precioMes;
	}
	public void setPrecioMes(double precioMes) {
		this.precioMes = precioMes;
	}
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", tamanho=" + tamanho + ", precioMes=" + precioMes + ", idPersona=" + idPersona
				+ "]";
	}

}
