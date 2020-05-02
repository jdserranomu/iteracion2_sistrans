package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

public class ReqConsulta7 {
	
	private String meses;
	
	private double cuantos;

	public String getMeses() {
		return meses;
	}

	public void setMeses(String meses) {
		this.meses = meses;
	}

	public double getCuantos() {
		return cuantos;
	}

	public void setCuantos(double cuantos) {
		this.cuantos = cuantos;
	}

	public ReqConsulta7(String meses, double cuantos) {
		super();
		this.meses = meses;
		this.cuantos = cuantos;
	}

	public ReqConsulta7() {
		this.meses="";
		this.cuantos=0;
	}
	
	

}
