package uniandes.isis2304.parranderos.negocio;

public class HabitacionHotel implements VOHabitacionHotel{
	
	/*
	 * constante de tipo suite de habitacion hotel
	 */
	public final static String TIPO_SUITE = "Suite";
	/*
	 * constante de tipo semisuite de la habitacion hotel
	 */
	public final static String TIPO_SEMISUITE = "Semisuite";
	/*
	 * constante de tipo estandar de la habitacion hotel
	 */
	public final static String TIPO_ESTANDAR = "Estandar";

	/*
	 * atributo del id de la habitacion del hotel
	 */
	private long id;
	/*
	 * atributo del id del hotel que es dueño de la habitacion
	 */
	private long idHotel;
	/*
	 * atributo del numero de la habitacion del hotel
	 */
	private int numero;
	/*
	 * atributo del tipo de la habitacion hotel
	 */
	private String tipo;
	/*
	 * precio por noche en la habitacion hotel
	 */
	private double precioNoche;
	/*
	 * tamaño de la habitacion del hotel
	 */
	private double tamanho;
	/*
	 * ubicacion de la habitacion hotel
	 */
	private String ubicacion;
	
	/*
	 * constructor de la habitacion hotel sin parametros
	 */
	public HabitacionHotel() {
		this.id=0;
		this.idHotel=0;
		this.numero=0;
		this.tipo="";
		this.precioNoche=0;
		this.tamanho=0;
		this.ubicacion="";
	}
	/*
	 * constructor con parametros de la habitacion del hotel
	 */
	public HabitacionHotel(long id, long idHotel, int numero, String tipo, double precioNoche, double tamanho,
			String ubicacion) {
		
		this.id = id;
		this.idHotel = idHotel;
		this.numero = numero;
		this.tipo = tipo;
		this.precioNoche = precioNoche;
		this.tamanho = tamanho;
		this.ubicacion = ubicacion;
	}
	public long getId() {
		return id;
	}
	/*
	 * funcion que cambia el id de la habitacion hotel
	 */
	public void setId(long id) {
		this.id = id;
	}
	public long getIdHotel() {
		return idHotel;
	}
	/*
	 * funcion que cambia el id del hotel
	 */
	public void setIdHotel(long idHotel) {
		this.idHotel = idHotel;
	}
	public int getNumero() {
		return numero;
	}
	/*
	 * funcion que cambia el numero de la habitacion del hotel
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	/*
	 * funcion que cambia el tipo de la habitacion 
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getPrecioNoche() {
		return precioNoche;
	}
	/*
	 * funcion que cambia el precio por noche de la habitacion del hotel
	 */
	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}
	public double getTamanho() {
		return tamanho;
	}
	/*
	 * funcion que cambia el tamaño de la habitacion del hotel
	 */
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	/*
	 * funcion que cambia la ubicacion de la habitacion del hotel
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	@Override
	public String toString() {
		return "HabitacionHotel [id=" + id + ", idHotel=" + idHotel + ", numero=" + numero + ", tipo=" + tipo
				+ ", precioNoche=" + precioNoche + ", tamanho=" + tamanho + ", ubicacion=" + ubicacion + "]";
	}
}
