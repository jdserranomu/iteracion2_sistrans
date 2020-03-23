package uniandes.isis2304.parranderos.negocio;

public class OfreceServicio implements VOOfreceServicio{

	private String idServicioMenaje;
	private long idInmueble;
	private Double costo;
	private Integer cantidad;
	
	public OfreceServicio() {
		this.idServicioMenaje="";
		this.idInmueble=0;
		this.costo=0.0;
		this.cantidad=0;
		
	}
	public OfreceServicio(String idServicioMenaje, long idInmueble, Double costo, Integer cantidad) {
		
		this.idServicioMenaje = idServicioMenaje;
		this.idInmueble = idInmueble;
		this.costo = costo;
		this.cantidad = cantidad;
	}
	public String getIdServicioMenaje() {
		return idServicioMenaje;
	}
	public void setIdServicioMenaje(String nombreServicioMenaje) {
		this.idServicioMenaje = nombreServicioMenaje;
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
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "OfreceServicio [idServicioMenaje=" + idServicioMenaje + ", idInmueble=" + idInmueble + ", costo="
				+ costo + ", cantidad=" + cantidad + "]";
	}
	
	
}
