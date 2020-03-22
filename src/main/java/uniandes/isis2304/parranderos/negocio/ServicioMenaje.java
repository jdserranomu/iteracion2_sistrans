package uniandes.isis2304.parranderos.negocio;

public class ServicioMenaje implements VOServicioMenaje{
	
	public final static String TIPO_MENAJE= "Menaje";
	public final static String TIPO_SERVICIO= "Servicio";
	
	private String nombre;
	private String tipo;
	
	public ServicioMenaje() {
		this.nombre="";
		this.tipo="";
	}
	public ServicioMenaje(String nombre, String tipo) {
	
		this.nombre = nombre;
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "ServicioMenaje [nombre=" + nombre + ", tipo=" + tipo + "]";
	}
	
	

}
