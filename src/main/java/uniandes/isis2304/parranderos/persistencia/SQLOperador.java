package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Operador;

public class SQLOperador {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLOperador(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarOperador (PersistenceManager pm, long idOperador, String nombre, String email, String telefono) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaOperador() + "(id, nombre, email, telefono) values (?, ?, ?, ?)");
        q.setParameters(idOperador, nombre, email, telefono);
        return (long) q.executeUnique();
	}
	
	
	public long eliminarOperadorPorId (PersistenceManager pm, long idOperador)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaOperador() + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();
	}
	
	public Operador darOperadorPorId (PersistenceManager pm, long idOperador) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOperador () + " WHERE id = ?");
		q.setResultClass(Operador.class);
		q.setParameters(idOperador);
		return (Operador) q.executeUnique();
	}
	
	public List<Operador> darOperadores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOperador ());
		q.setResultClass(Operador.class);
		return (List<Operador>) q.executeList();
	}

}
