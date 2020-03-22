package uniandes.isis2304.parranderos.negocio;

public interface VOOfreceServicio {
	
	
	public String getNombreServicioMenaje();
	public void setNombreServicioMenaje( String nombreServicioMenaje);
	public long getIdInmueble();
	public void setIdInmueble(long idInmueble);
	public double getCosto();
	public void setCosto(double costo);
	public int getCantidad();
	public void setCantidad(int cantidad);
	public String toString();
	
}
