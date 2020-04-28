package uniandes.isis2304.parranderos.negocio;

public class ReqFun9 {

	
	Reserva aCambiar;
	Reserva nueva;
	public Reserva getaCambiar() {
		return aCambiar;
	}
	public void setaCambiar(Reserva aCambiar) {
		this.aCambiar = aCambiar;
	}
	public Reserva getNueva() {
		return nueva;
	}
	public void setNueva(Reserva nueva) {
		this.nueva = nueva;
	}
	public ReqFun9(Reserva aCambiar, Reserva nueva) {
		super();
		this.aCambiar = aCambiar;
		this.nueva = nueva;
	}
}
