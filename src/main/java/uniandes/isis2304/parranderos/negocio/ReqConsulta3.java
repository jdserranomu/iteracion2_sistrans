package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta3 {

	private long idInmueble;
	
	private Double tasaOcupacion;
	
	public ReqConsulta3() {
		this.idInmueble=0;
		this.tasaOcupacion=0.0;
	}
	
	public ReqConsulta3(long idInmueble, Double tasaOcupacion) {
		this.idInmueble=idInmueble;
		this.tasaOcupacion=tasaOcupacion;
	}
	

	public long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(long idInmueble) {
		this.idInmueble = idInmueble;
	}

	public double getTasaOcupacion() {
		return tasaOcupacion;
	}

	public void setTasaOcupacion(Double tasaOcupacion) {
		this.tasaOcupacion = tasaOcupacion;
	}
	
	public String toString() {
		return "id Inmueble: "+idInmueble+" tasa ocupacion: "+tasaOcupacion;
	}
	
}
