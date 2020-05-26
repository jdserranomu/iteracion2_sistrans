package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class ReqConsulta12Inmueble {
	
	private long id;
	private String direccion;
	private String tipo;
	private int capacidad;
	private int disponible;
	private Date fechaReservaFinal;
	private Date semana;
	
	public ReqConsulta12Inmueble() {
		this.id=0;
		this.direccion="";
		this.tipo="";
		this.capacidad=0;
		this.disponible=0;
		this.fechaReservaFinal= null;
		this.semana = null;
		
	}
	public ReqConsulta12Inmueble(long id, String direccion, String tipo, int capacidad, int disponible, Date fechaReservaFinal, Date semana) {
		
		this.id = id;
		this.direccion = direccion;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.disponible = disponible;
		this.fechaReservaFinal = fechaReservaFinal;
		this.semana = semana;
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
				+ ", disponible=" + disponible + ", fechaReservaFinal=" + fechaReservaFinal + ", semana="+semana+"]";
	}
	public Date getSemana() {
		return semana;
	}
	public void setSemana(Date semana) {
		this.semana = semana;
	}

}
