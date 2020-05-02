package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta5 {
	
	private String tipo;
	
	private int numeroReservas;
	
	private int nochesContratado;
	
	private double valorPagado;
	
	
	public ReqConsulta5() {
		this.setTipo(null);
		this.setNumeroReservas(0);
		this.setNochesContratado(0);
		this.setValorPagado(0.0);
	}

	public ReqConsulta5(String tipo, int numeroReservas, int nochesContratado, double valorPagado) {
		this.setTipo(tipo);
		this.setNumeroReservas(numeroReservas);
		this.setNochesContratado(nochesContratado);
		this.setValorPagado(valorPagado);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumeroReservas() {
		return numeroReservas;
	}

	public void setNumeroReservas(int numeroReservas) {
		this.numeroReservas = numeroReservas;
	}

	public int getNochesContratado() {
		return nochesContratado;
	}

	public void setNochesContratado(Integer nochesContratado) {
		if(nochesContratado==null)
			this.nochesContratado = 0;
		else {
			this.nochesContratado = nochesContratado;
		}
	}

	public double getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(Double valorPagado) {
		if( valorPagado == null)
			this.valorPagado = 0.0;
		else {
			this.valorPagado = valorPagado;
		}
	}
	
	public String toString() {
		return "ReqConsulta5 [tipo =" + this.tipo + ", numeroReservas = " + this.numeroReservas
				+ ", nochesContratado = "+ this.nochesContratado+ ", valorPagado = "+ this.valorPagado+"]";
	}
	
	
	
	

}
