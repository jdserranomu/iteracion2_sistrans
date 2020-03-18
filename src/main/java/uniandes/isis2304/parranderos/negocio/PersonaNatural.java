package uniandes.isis2304.parranderos.negocio;

public class PersonaNatural implements VOPersonaNatural{
	private long id;
	private String tipo;
	
	public PersonaNatural() {
		this.id=0;
		this.tipo="";
	}
	public PersonaNatural(long id, String tipo) {
		
		this.id = id;
		this.tipo = tipo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "PersonaNatural [id=" + id + ", tipo=" + tipo + "]";
	}
	
	

}
