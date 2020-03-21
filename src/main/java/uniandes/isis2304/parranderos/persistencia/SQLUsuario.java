package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Usuario;

public class SQLUsuario {
	

	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLUsuario(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarUsuario (PersistenceManager pm, long idUsuario, String nombre, String email, String telefono, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaUsuario() + "(id, nombre, email, telefono, tipo) values (?, ?, ?, ?, ?)");
        q.setParameters(idUsuario, nombre, email, telefono, tipo);
        return (long) q.executeUnique();
	}
	
	public long eliminarUsuarioPorId (PersistenceManager pm, long idUsuario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaUsuario() + " WHERE id = ?");
        q.setParameters(idUsuario);
        return (long) q.executeUnique();
	}
	
	public Usuario darUsuarioPorId(PersistenceManager pm, long idUsuario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaUsuario() + " WHERE id = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(idUsuario);
		return (Usuario) q.executeUnique();
	}
	
	public Usuario darUsuarioPorEmail(PersistenceManager pm, String email) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaUsuario() + " WHERE email = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(email);
		return (Usuario) q.executeUnique();
	}
	
	public List<Usuario> darUsuarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaUsuario());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}
	
	public List<Usuario> darUsuariosPorTipo (PersistenceManager pm, String tipo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaUsuario() + " WHERE tipo = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(tipo);
		return (List<Usuario>) q.executeList();
	}
}
