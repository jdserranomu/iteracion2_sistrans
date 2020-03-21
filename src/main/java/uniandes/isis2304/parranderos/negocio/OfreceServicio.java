package uniandes.isis2304.parranderos.negocio;

public class OfreceServicio implements VOOfreceServicio{

	private String nombreServicioMenaje;
	private long idInmueble;
	private double costo;
	private int cantidad;
	
	public OfreceServicio() {
		this.nombreServicioMenaje="";
		this.idInmueble=0;
		this.costo=0;
		this.cantidad=0;
		
	}
	public OfreceServicio(String nombreServicioMenaje, long idInmueble, double costo, int cantidad) {
		
		this.nombreServicioMenaje = nombreServicioMenaje;
		this.idInmueble = idInmueble;
		this.costo = costo;
		this.cantidad = cantidad;
	}
	public String getNombreServicioMenaje() {
		return nombreServicioMenaje;
	}
	public void setNombreServicioMenaje(String nombreServicioMenaje) {
		this.nombreServicioMenaje = nombreServicioMenaje;
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
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "OfreceServicio [idServicioMenaje=" + nombreServicioMenaje + ", idInmueble=" + idInmueble + ", costo="
				+ costo + ", cantidad=" + cantidad + "]";
	}
	
	
}
