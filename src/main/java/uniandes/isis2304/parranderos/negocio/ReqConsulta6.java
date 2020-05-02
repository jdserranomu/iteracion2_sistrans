package uniandes.isis2304.parranderos.negocio;

public class ReqConsulta6 {

	private long idUsuario;
	
	private int numeroReservas;
	
	private int nochesContratado;
	
	private double valorPagado;
	
	
	public ReqConsulta6() {
		this.setIdUsuario(0);
		this.setNumeroReservas(0);
		this.setNochesContratado(0);
		this.setValorPagado(0.0);
	}

	public ReqConsulta6(long idUsuario, int numeroReservas, int nochesContratado, double valorPagado) {
		this.setIdUsuario(idUsuario);
		this.setNumeroReservas(numeroReservas);
		this.setNochesContratado(nochesContratado);
		this.setValorPagado(valorPagado);
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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
		return "ReqConsulta6 [idUsuario =" + this.idUsuario + ", numeroReservas = " + this.numeroReservas
				+ ", nochesContratado = "+ this.nochesContratado+ ", valorPagado = "+ this.valorPagado+"]";
	}

}
