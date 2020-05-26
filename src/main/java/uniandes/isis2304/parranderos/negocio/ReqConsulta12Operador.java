package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class ReqConsulta12Operador {

	private long id;
	


	/**
	 * El nombre del bar
	 */
	private String nombre;

	/**
	 * La ciudad donde se encuentra el bar
	 */
	private String email;
	
	/**
	 * El presupuesto del bar (ALTO, MEDIO, BAJO)
	 */
	private String telefono;
	
	private Date semana;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public ReqConsulta12Operador() 
    {
    	this.id = 0;
		this.nombre = "";
		this.email = "";
		this.telefono = "";
		semana = null;
		
	}

	public ReqConsulta12Operador(long id, String nombre, String email, String telefono, Date semana) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.semana = semana;
	}
    
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "Operador [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + 
				", semana="+semana+"]";
	}

	public Date getSemana() {
		return semana;
	}

	public void setSemana(Date semana) {
		this.semana = semana;
	}

}
