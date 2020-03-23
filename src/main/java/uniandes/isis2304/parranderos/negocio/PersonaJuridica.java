package uniandes.isis2304.parranderos.negocio;

public class PersonaJuridica implements VOPersonaJuridica{
	
	
	public final static String TIPO_HOTEL = "Hotel";
	public final static String TIPO_HOSTAL = "Hostal";

	private long id;
	private long idSuperIntendenciaTurismo;
	private long idCamaraComercio;
	private int categoria;
	private double precioNoche;
	private String tipo;
	
	public PersonaJuridica() {
		this.id=0;
		this.idSuperIntendenciaTurismo=0;
		this.idCamaraComercio=0;
		this.categoria=0;
		this.precioNoche=0;
		this.tipo="";
	}
	public PersonaJuridica(Long id, Long idSuperintendeciaTurismo, Long idCamaraComercio, int categoria,
			double precioNoche, String tipo) {
	
		this.id = id;
		this.idSuperIntendenciaTurismo = idSuperintendeciaTurismo;
		this.idCamaraComercio = idCamaraComercio;
		this.categoria = categoria;
		this.precioNoche = precioNoche;
		this.tipo = tipo;
	}
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getIdSuperintendeciaTurismo() {
		return idSuperIntendenciaTurismo;
	}
	public void setIdSuperintendeciaTurismo(Long idSuperintendeciaTurismo) {
		this.idSuperIntendenciaTurismo = idSuperintendeciaTurismo;
	}
	public long getIdCamaraComercio() {
		return idCamaraComercio;
	}
	public void setIdCamaraComercio(Long idCamaraComercio) {
		this.idCamaraComercio = idCamaraComercio;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public double getPrecioNoche() {
		return precioNoche;
	}
	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public long getIdSuperIntendenciaTurismo() {
		return idSuperIntendenciaTurismo;
	}
	public String toString() {
		return "PersonaJuridica [id=" + id + ", idSuperintendeciaTurismo=" + idSuperIntendenciaTurismo
				+ ", idCamaraComercio=" + idCamaraComercio + ", categoria=" + categoria + ", precioNoche=" + precioNoche
				+ ", tipo=" + tipo + "]";
	}
	
	
	
}
