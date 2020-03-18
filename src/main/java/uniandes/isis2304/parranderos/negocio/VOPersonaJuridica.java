package uniandes.isis2304.parranderos.negocio;

public interface VOPersonaJuridica {

	
	public long getId();
	public long getIdSuperIntendenciaTurismo();
	public long getIdCamaraComercio();
	public int getCategoria();
	public double getPrecioNoche();
	public String getTipo();
	
	public String toString();
}
