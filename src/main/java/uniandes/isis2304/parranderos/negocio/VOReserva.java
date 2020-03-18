package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public interface VOReserva {
	
	public long getId();
	public Date getFechaInicio();
	public Date getFechaFin();
	public double getValorTotal();
	public Date getFechaCancelacion();
	public int getPagado();
	public double getDescuento();
	public int getCapacidad();
	public int getEstado();
	public long getIdOperador();
	public long getIdUsuario();
	public long getIdInmueble();
	public String toString();

}
