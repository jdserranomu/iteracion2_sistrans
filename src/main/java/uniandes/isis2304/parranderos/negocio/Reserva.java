package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public class Reserva implements VOReserva {
	private long id;
	private Date fechaInicio;
	private Date fechaFin;
	private double valorTotal;
	private Date fechaCancelacion;
	private int pagado;
	private double descuento;
	private int capacidad;
	private int estado;
	private long idOperador;
	private long idUsuario;
	private long idInmueble;
	
	public Reserva() {
		this.id=0;
		this.fechaInicio=new Date(2020,1,1);
		this.fechaFin=new Date(2020, 1, 1);
		this.valorTotal=0;
		this.fechaCancelacion=new Date(2020, 1, 1);
		this.pagado=0;
		this.descuento=0;
		this.capacidad=0;
		this.estado=0;
		this.idOperador=0;
		this.idUsuario=0;
		this.idInmueble=0;
	}
	public Reserva(long id, Date fechaInicio, Date fechaFin, double valorTotal, Date fechaCancelacion, int pagado,
			double descuento, int capacidad, int estado, long idOperador, long idUsuario, long idInmueble) {
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.valorTotal = valorTotal;
		this.fechaCancelacion = fechaCancelacion;
		this.pagado = pagado;
		this.descuento = descuento;
		this.capacidad = capacidad;
		this.estado = estado;
		this.idOperador = idOperador;
		this.idUsuario = idUsuario;
		this.idInmueble = idInmueble;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public int getPagado() {
		return pagado;
	}
	public void setPagado(int pagado) {
		this.pagado = pagado;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public long getIdOperador() {
		return idOperador;
	}
	public void setIdOperador(long idOperador) {
		this.idOperador = idOperador;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdInmueble() {
		return idInmueble;
	}
	public void setIdInmueble(long idInmueble) {
		this.idInmueble = idInmueble;
	}
	@Override
	public String toString() {
		return "Reserva [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", valorTotal="
				+ valorTotal + ", fechaCancelacion=" + fechaCancelacion + ", pagado=" + pagado + ", descuento="
				+ descuento + ", capacidad=" + capacidad + ", estado=" + estado + ", idOperador=" + idOperador
				+ ", idUsuario=" + idUsuario + ", idInmueble=" + idInmueble + "]";
	}

}
