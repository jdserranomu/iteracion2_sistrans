package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta2 {

	private long idInmueble;
	
	private int cnt;
	
	public ReqConsulta2(long idInmueble, int cnt) {
		this.idInmueble = idInmueble;
		this.cnt = cnt;
	}
	
	public ReqConsulta2() {
		this.idInmueble = 0;
		this.cnt = 0;
	}

	public long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(long idInmueble) {
		this.idInmueble = idInmueble;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String toString() {
		return "Id inmueble: "+idInmueble+" cantidad reservas: "+cnt;
	}
	
	
}
