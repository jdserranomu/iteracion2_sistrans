package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion {
	/*
	 * atributo del id de la habitacion
	 */
	private long id;
	/*
	 * atributo del tamaño de la habitacion
	 */
	private double tamanho;
	/*
	 * atributo que indica el precio mensual de la habitacion
	 */
	private double precioMes;
	/*
	 * atributo que indica el id del dueño de la habitacion
	 */
	private long idPersona;
	
	/*
	 * Constructor de habitacion sin parametros
	 */
	public Habitacion() {
		this.id=0;
		this.tamanho=0;
		this.precioMes=0;
		this.idPersona=0;
		
	}
	/*
	 * Constructor de habitacion con parametros
	 */
	public Habitacion(long id, double tamanho, double precioMes, long idPersona) {
		
		this.id = id;
		this.tamanho = tamanho;
		this.precioMes = precioMes;
		this.idPersona = idPersona;
	}
	/*
	 * (non-Javadoc)
	 * @see uniandes.isis2304.parranderos.negocio.VOHabitacion#getId()
	 */
	public long getId() {
		return id;
	}
	/*
	 * funcion que cambia el id de la habitacion
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public double getTamanho() {
		return tamanho;
	}
	/*
	 * funcion que cambia el tamaño de la habitacion
	 */
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	public double getPrecioMes() {
		return precioMes;
	}
	/*
	 * funcion que cambia el precio mensual de la habitacion
	 */
	public void setPrecioMes(double precioMes) {
		this.precioMes = precioMes;
	}
	public long getIdPersona() {
		return idPersona;
	}
	/*
	 * funcion que cambia el id del dueño de la habitacion
	 */
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", tamanho=" + tamanho + ", precioMes=" + precioMes + ", idPersona=" + idPersona
				+ "]";
	}

}
