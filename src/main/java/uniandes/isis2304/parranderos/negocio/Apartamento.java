package uniandes.isis2304.parranderos.negocio;

public class Apartamento implements VOApartamento{
	private long id;
	private int amoblado;
	private double precioMes;
	private long idPersona;
	
	public Apartamento() {
		this.id=0;
		this.amoblado=0;
		this.precioMes=0;
		this.idPersona=0;
	}
	public Apartamento(long id, int amoblado, double precioMes, long idPersona) {
		
		this.id = id;
		this.amoblado = amoblado;
		this.precioMes = precioMes;
		this.idPersona = idPersona;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getAmoblado() {
		return amoblado;
	}
	public void setAmoblado(int amoblado) {
		this.amoblado = amoblado;
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
		return "Apartamento [id=" + id + ", amoblado=" + amoblado + ", precioMes=" + precioMes + ", idPersona="
				+ idPersona + "]";
	}

}
