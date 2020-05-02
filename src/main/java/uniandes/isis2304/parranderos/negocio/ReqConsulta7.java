package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public class ReqConsulta7 {
	
	private Date fecha;
	
	private double cantidad;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public ReqConsulta7(Date fecha, double cantidad) {
		super();
		this.fecha = fecha;
		this.cantidad = cantidad;
	}
	
	

}
