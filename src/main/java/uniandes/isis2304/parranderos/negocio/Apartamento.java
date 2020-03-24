package uniandes.isis2304.parranderos.negocio;

public class Apartamento implements VOApartamento{
	/*
	 * Atributo del id del apartamento
	 */
	private long id;
	/*
	 * atributo que indica si un apto esta amoblado o no
	 */
	private int amoblado;
	/*
	 * atributo que indica el precio mensual del apto
	 */
	private double precioMes;
	/*
	 * atributo que indica el id del dueño del apto
	 */
	private long idPersona;
	
	/*
	 * constructor sin parametros del apartamento
	 */
	public Apartamento() {
		this.id=0;
		this.amoblado=0;
		this.precioMes=0;
		this.idPersona=0;
	}
	/*
	 * COnstructor con parametros del apartamento
	 */
	public Apartamento(long id, int amoblado, double precioMes, long idPersona) {
		
		this.id = id;
		this.amoblado = amoblado;
		this.precioMes = precioMes;
		this.idPersona = idPersona;
	}
	
	/*
	 * metodo que retorna el id del apto
	 *
	 */
	public long getId() {
		return id;
	}
	/*
	 * funcion que cambia el valor del id del apto
	 */
	public void setId(long id) {
		this.id = id;
	}
	/*
	 * (non-Javadoc)
	 * @see uniandes.isis2304.parranderos.negocio.VOApartamento#getAmoblado()
	 */
	public int getAmoblado() {
		return amoblado;
	}
	/*
	 * Funcion que cambia el atributo que indica si un apto esta amoblado o no
	 */
	public void setAmoblado(int amoblado) {
		this.amoblado = amoblado;
	}
	/*
	 * (non-Javadoc)
	 * @see uniandes.isis2304.parranderos.negocio.VOApartamento#getPrecioMes()
	 */
	public double getPrecioMes() {
		return precioMes;
	}
	/*
	 * Funcion que cambia el precio mensual del apto
	 */
	public void setPrecioMes(double precioMes) {
		this.precioMes = precioMes;
	}
	/*
	 * (non-Javadoc)
	 * @see uniandes.isis2304.parranderos.negocio.VOApartamento#getIdPersona()
	 */
	public long getIdPersona() {
		return idPersona;
	}
	/*
	 * Funcion que cambia el id del dueño del apto
	 */
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Apartamento [id=" + id + ", amoblado=" + amoblado + ", precioMes=" + precioMes + ", idPersona="
				+ idPersona + "]";
	}

}
