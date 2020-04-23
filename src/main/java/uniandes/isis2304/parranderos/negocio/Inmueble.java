package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class Inmueble implements VOInmueble {
	
	public final static String TIPO_APARTAMENTO = "Apartamento";
	public final static String TIPO_HABITACION = "Habitacion";
	public final static String TIPO_VIVIENDA = "Vivienda";
	public final static String TIPO_HABITACIONHOTEL = "Habitacion Hotel";
	public final static String TIPO_HABITACIONHOSTAL = "Habitacion Hostal";
	public final static String TIPO_HABITACIONVIVIENDA = "Habitacion Vivienda";
	

	private long id;
	private String direccion;
	private String tipo;
	private int capacidad;
	private int disponible;
	private Date fechaReservaFinal;
	
	public Inmueble() {
		this.id=0;
		this.direccion="";
		this.tipo="";
		this.capacidad=0;
		this.disponible=0;
		this.fechaReservaFinal= new Date(2020,1,1);
		
	}
	public Inmueble(long id, String direccion, String tipo, int capacidad, int disponible, Date fechaReservaFinal) {
		
		this.id = id;
		this.direccion = direccion;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.disponible = disponible;
		this.fechaReservaFinal = fechaReservaFinal;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getDisponible() {
		return disponible;
	}
	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}
	public Date getFechaReservaFinal() {
		return fechaReservaFinal;
	}
	public void setFechaReservaFinal(Date fechaReservaFinal) {
		this.fechaReservaFinal = fechaReservaFinal;
	}
	@Override
	public String toString() {
		return "Inmueble [id=" + id + ", direccion=" + direccion + ", tipo=" + tipo + ", capacidad=" + capacidad
				+ ", disponible=" + disponible + ", fechaReservaFinal=" + fechaReservaFinal + "]";
	}
}
