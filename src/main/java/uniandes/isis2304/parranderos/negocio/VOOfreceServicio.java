package uniandes.isis2304.parranderos.negocio;

public interface VOOfreceServicio {
	
	
	public String getIdServicioMenaje();
	public void setIdServicioMenaje( String idServicioMenaje);
	public long getIdInmueble();
	public void setIdInmueble(long idInmueble);
	public double getCosto();
	public void setCosto(Double costo);
	public int getCantidad();
	public void setCantidad(Integer cantidad);
	public String toString();
	
}
