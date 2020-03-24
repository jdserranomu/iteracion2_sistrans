package uniandes.isis2304.parranderos.negocio;

public class HabitacionHostal implements VOHabitacionHostal{
	/*
	 * atributo que indica el id del la habitacion hostal
	 */
	private long id;
	/*
	 * atributo que indica el id del hostal que es dueño de la habitacion
	 */
	private long idHostal;
	/*
	 * atibuto del numero de la habitacion del hostal
	 */
	private int numero;
	
	/*
	 * constructor sin parametros de la habitacion del hostal
	 */
	public HabitacionHostal() {
		this.id=0;
		this.idHostal=0;
		this.numero=0;
		
	}
	/*
	 * Constructor con parametros de la habitacion del hostal
	 */
	public HabitacionHostal(long id, long idHostal, int numero) {
	
		this.id = id;
		this.idHostal = idHostal;
		this.numero = numero;
	}
	public long getId() {
		return id;
	}
	/*
	 * Funcion que cambia el id de la habitacion del hostal
	 */
	public void setId(long id) {
		this.id = id;
	}
	public long getIdHostal() {
		return idHostal;
	}
	/*
	 * funcion que cambia el hostal que es dueño de la habitacion
	 */
	public void setIdHostal(long idHostal) {
		this.idHostal = idHostal;
	}
	public long getNumero() {
		return numero;
	}
	/*
	 * funcion que cambia el numero de la habitacion hostal
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "HabitacionHostal [id=" + id + ", idHostal=" + idHostal + ", numero=" + numero + "]";
	}
	

}
