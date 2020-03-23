package uniandes.isis2304.parranderos.negocio;

public class PersonaJuridica implements VOPersonaJuridica{
	
	
	public final static String TIPO_HOTEL = "Hotel";
	public final static String TIPO_HOSTAL = "Hostal";
	public final static String TIPO_VIVIENDAUNIVERSITARIA="Vivienda Universitaria";

	private long id;
	private long idSuperIntendenciaTurismo;
	private long idCamaraComercio;
	private Integer categoria;
	private Double precioNoche;
	private String tipo;
	
	public PersonaJuridica() {
		this.id=0;
		this.idSuperIntendenciaTurismo=0;
		this.idCamaraComercio=0;
		this.categoria=0;
		this.precioNoche=0.0;
		this.tipo="";
	}
	public PersonaJuridica(long id, long idSuperintendeciaTurismo, long idCamaraComercio, Integer categoria,
			Double precioNoche, String tipo) {
	
		this.id = id;
		this.idSuperIntendenciaTurismo=idSuperintendeciaTurismo;
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
	public long getIdCamaraComercio() {
		return idCamaraComercio;
	}
	public void setIdCamaraComercio(Long idCamaraComercio) {
		this.idCamaraComercio = idCamaraComercio;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	public double getPrecioNoche() {
		return precioNoche;
	}
	public void setPrecioNoche(Double precioNoche) {
		this.precioNoche = precioNoche;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String toString() {
		return "PersonaJuridica [id=" + id + ", idSuperintendeciaTurismo=" + getIdSuperIntendenciaTurismo()
				+ ", idCamaraComercio=" + idCamaraComercio + ", categoria=" + categoria + ", precioNoche=" + precioNoche
				+ ", tipo=" + tipo + "]";
	}
	public long getIdSuperIntendenciaTurismo() {
		return idSuperIntendenciaTurismo;
	}
	public void setIdSuperIntendenciaTurismo(Long idSuperIntendenciaTurismo) {
		this.idSuperIntendenciaTurismo = idSuperIntendenciaTurismo;
	}
	
	
	
}
