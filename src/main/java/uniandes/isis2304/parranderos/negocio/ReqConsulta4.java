package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta4 {
	private long idInmueble;

	public long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(long idInmueble) {
		this.idInmueble = idInmueble;
	}

	public ReqConsulta4(long idInmueble) {
	
		this.idInmueble = idInmueble;
	}
	
	public ReqConsulta4() {
		this.idInmueble=0;
	}

	@Override
	public String toString() {
		return "ReqConsulta4 [idInmueble=" + idInmueble + "]";
	}
	
}
