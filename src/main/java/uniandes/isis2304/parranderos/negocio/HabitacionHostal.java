package uniandes.isis2304.parranderos.negocio;

public class HabitacionHostal implements VOHabitacionHostal{
	
	private long id;
	private long idHostal;
	private int numero;
	
	public HabitacionHostal() {
		this.id=0;
		this.idHostal=0;
		this.numero=0;
		
	}
	public HabitacionHostal(long id, long idHostal, int numero) {
	
		this.id = id;
		this.idHostal = idHostal;
		this.numero = numero;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdHostal() {
		return idHostal;
	}
	public void setIdHostal(long idHostal) {
		this.idHostal = idHostal;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "HabitacionHostal [id=" + id + ", idHostal=" + idHostal + ", numero=" + numero + "]";
	}
	

}
