package uniandes.isis2304.parranderos.negocio;

public class HabitacionHotel implements VOHabitacionHotel{
	
	public final static String TIPO_SUITE = "Suite";
	public final static String TIPO_SEMISUITE = "Semisuite";
	public final static String TIPO_ESTANDAR = "Estandar";

	
	private long id;
	private long idHotel;
	private int numero;
	private String tipo;
	private double precioNoche;
	private double tamanho;
	private String ubicacion;
	
	private HabitacionHotel() {
		this.id=0;
		this.idHotel=0;
		this.numero=0;
		this.tipo="";
		this.precioNoche=0;
		this.tamanho=0;
		this.ubicacion="";
	}
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
	public void setId(long id) {
		this.id = id;
	}
	public long getIdHotel() {
		return idHotel;
	}
	public void setIdHotel(long idHotel) {
		this.idHotel = idHotel;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getPrecioNoche() {
		return precioNoche;
	}
	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}
	public double getTamanho() {
		return tamanho;
	}
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	@Override
	public String toString() {
		return "HabitacionHotel [id=" + id + ", idHotel=" + idHotel + ", numero=" + numero + ", tipo=" + tipo
				+ ", precioNoche=" + precioNoche + ", tamanho=" + tamanho + ", ubicacion=" + ubicacion + "]";
	}
}
