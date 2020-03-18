package uniandes.isis2304.parranderos.negocio;

public class Vivienda implements VOVivienda{
	private long id;
	private int numeroHabitaciones;
	private double costoNoche;
	private int diasUtilizado;
	private long idPersona;
	
	public Vivienda() {
		this.id=0;
		this.numeroHabitaciones=0;
		this.costoNoche=0;
		this.diasUtilizado=0;
		this.idPersona=0;
	}
	public Vivienda(long id, int numeroHabitaciones, double costoNoche, int diasUtilizado, long idPersona) {
		
		this.id = id;
		this.numeroHabitaciones = numeroHabitaciones;
		this.costoNoche = costoNoche;
		this.diasUtilizado = diasUtilizado;
		this.idPersona = idPersona;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}
	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}
	public double getCostoNoche() {
		return costoNoche;
	}
	public void setCostoNoche(double costoNoche) {
		this.costoNoche = costoNoche;
	}
	public int getDiasUtilizado() {
		return diasUtilizado;
	}
	public void setDiasUtilizado(int diasUtilizado) {
		this.diasUtilizado = diasUtilizado;
	}
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	@Override
	public String toString() {
		return "Vivienda [id=" + id + ", numeroHabitaciones=" + numeroHabitaciones + ", costoNoche=" + costoNoche
				+ ", diasUtilizado=" + diasUtilizado + ", idPersona=" + idPersona + "]";
	}

}
