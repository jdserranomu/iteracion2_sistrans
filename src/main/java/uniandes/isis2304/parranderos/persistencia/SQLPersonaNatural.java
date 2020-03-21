package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.PersonaNatural;

public class SQLPersonaNatural {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLPersonaNatural(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarPersonaNatural (PersistenceManager pm, long idPersonaNatural, String tipo) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaPersonaNatural() + "(id, tipo) values (?, ?)");
        q.setParameters(idPersonaNatural, tipo);
        return (long) q.executeUnique();
	}
	/**
	public long eliminarPersonaNaturalPorId (PersistenceManager pm, long idPersonaNatural) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaPersonaNatural() + " WHERE id = ?");
        q.setParameters(idPersonaNatural);
        return (long) q.executeUnique();
	}
	*/
	public PersonaNatural darPersonaNaturalPorId (PersistenceManager pm, long idPersonaNatural) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaNatural() + " WHERE id = ?");
		q.setResultClass(PersonaNatural.class);
		q.setParameters(idPersonaNatural);
		return (PersonaNatural) q.executeUnique();
	}
	
	public List<PersonaNatural> darPersonasNaturales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaNatural());
		q.setResultClass(PersonaNatural.class);
		return (List<PersonaNatural>) q.executeList();
	}
	
	public List<PersonaNatural> darPersonasNaturalesPorTipo (PersistenceManager pm, String tipo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaNatural() + " WHERE tipo = ?");
		q.setResultClass(PersonaNatural.class);
		q.setParameters(tipo);
		return (List<PersonaNatural>) q.executeList();
	}

}
