package uniandes.isis2304.parranderos.negocio;

public class Horario implements VOHorario{

	private long idHostal;
	
	private String dia;
	private int horaAbre;
	private int horaCierra;
	
	public Horario() {
		this.idHostal=0;
		this.dia="";
		this.horaAbre=0;
		this.horaCierra=0;
	}
	public Horario(long idHostal, String dia, int horaAbre, int horaCierra) {
		
		this.idHostal = idHostal;
		this.dia = dia;
		this.horaAbre = horaAbre;
		this.horaCierra = horaCierra;
	}
	public long getIdHostal() {
		return idHostal;
	}
	public void setIdHostal(long idHostal) {
		this.idHostal = idHostal;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public int getHoraAbre() {
		return horaAbre;
	}
	public void setHoraAbre(int horaAbre) {
		this.horaAbre = horaAbre;
	}
	public int getHoraCierra() {
		return horaCierra;
	}
	public void setHoraCierra(int horaCierra) {
		this.horaCierra = horaCierra;
	}
	@Override
	public String toString() {
		return "Horario [idHostal=" + idHostal + ", dia=" + dia + ", horaAbre=" + horaAbre + ", horaCierra="
				+ horaCierra + "]";
	}
	
	
}
