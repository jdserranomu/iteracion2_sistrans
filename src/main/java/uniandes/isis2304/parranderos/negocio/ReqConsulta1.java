package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta1 {
	
	private long ic;
	
	private double dc;
	
	private double da;
	
	public ReqConsulta1(long ic, double dc, double da) {
		this.setIc(ic);
		this.setDc(dc);
		this.setDa(da);
	}
	
	public ReqConsulta1() {
		this.setIc(0);
		this.setDc(0);
		this.setDa(0);
	}

	public long getIc() {
		return ic;
	}

	public void setIc(long ic) {
		this.ic = ic;
	}

	public double getDc() {
		return dc;
	}

	public void setDc(double dc) {
		this.dc = dc;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}
	@Override
	public String toString() {
		return "id operador: "+ic+" dinero recibido año corrido: "
				+dc+" dinero recibido año actual: "+da;
	}
	
	
	

}
