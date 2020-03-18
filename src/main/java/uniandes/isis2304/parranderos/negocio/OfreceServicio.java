package uniandes.isis2304.parranderos.negocio;

public class OfreceServicio {

	private long idServicioMenaje;
	private long idInmueble;
	private double costo;
	private double cantidad;
	
	public OfreceServicio() {
		this.idServicioMenaje=0;
		this.idInmueble=0;
		this.costo=0;
		this.cantidad=0;
		
	}
	public OfreceServicio(long idServicioMenaje, long idInmueble, double costo, double cantidad) {
		
		this.idServicioMenaje = idServicioMenaje;
		this.idInmueble = idInmueble;
		this.costo = costo;
		this.cantidad = cantidad;
	}
	public long getIdServicioMenaje() {
		return idServicioMenaje;
	}
	public void setIdServicioMenaje(long idServicioMenaje) {
		this.idServicioMenaje = idServicioMenaje;
	}
	public long getIdInmueble() {
		return idInmueble;
	}
	public void setIdInmueble(long idInmueble) {
		this.idInmueble = idInmueble;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "OfreceServicio [idServicioMenaje=" + idServicioMenaje + ", idInmueble=" + idInmueble + ", costo="
				+ costo + ", cantidad=" + cantidad + "]";
	}
	
	
}
