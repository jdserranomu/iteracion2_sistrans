package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public interface VOInmueble {
	
	public long getId();
	public String getDireccion();
	public String getTipo();
	public int getCapacidad();
	public int getDisponible();
	public Date getFechaReservaFinal();
	public String toString();


}
