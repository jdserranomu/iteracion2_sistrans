package uniandes.isis2304.parranderos.negocio;

public class Usuario implements VOUsuario{

	public final static String TIPO_PROFESOR = "Profesor";
	public final static String TIPO_PROFESORINVITADO = "Profesor Invitado";
	public final static String TIPO_ESTUDIANTE = "Estudiante";
	public final static String TIPO_EGRESADO = "Egresado";
	public final static String TIPO_EMPLEADO = "Empleado";
	public final static String TIPO_PADRAESTUDIANTE = "Padre Estudiante";
	public final static String TIPO_INVITADO = "Invitado";
	
	private long id;
	private String nombre;
	private String email;
	private String telefono;
	private String tipo;
	
	/**
     * Constructor por defecto
     */
	public Usuario() 
    {
    	this.id = 0;
		this.nombre = "";
		this.email = "";
		this.telefono = "";
		this.tipo="";
		
	}

	public Usuario(long id, String nombre, String email, String telefono, String tipo) {
		
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.tipo = tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + ", tipo="
				+ tipo + "]";
	}
}
