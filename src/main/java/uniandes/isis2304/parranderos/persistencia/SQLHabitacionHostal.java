package uniandes.isis2304.parranderos.persistencia;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.HabitacionHostal;

public class SQLHabitacionHostal {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLHabitacionHostal(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarHabitacionHostal (PersistenceManager pm, long idHabitacionHostal, int numero, long idHostal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaHabitacionHostal() + "(id, numero, idHostal) values (?, ?, ?)");
        q.setParameters(idHabitacionHostal, numero, idHostal);
        return (long) q.executeUnique();
	}
	
	public long eliminarHabitacionHostalPorId (PersistenceManager pm, long idHabitacionHostal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionHostal() + " WHERE id = ?");
        q.setParameters(idHabitacionHostal);
        return (long) q.executeUnique();
	}
	
	public HabitacionHostal darHabitacionHostalPorId (PersistenceManager pm, long idHabitacionHostal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHostal() + " WHERE id = ?");
		q.setResultClass(HabitacionHostal.class);
		q.setParameters(idHabitacionHostal);
		return (HabitacionHostal) q.executeUnique();
	}
	
	public List<HabitacionHostal> darHabitacionesHostales (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHostal());
		q.setResultClass(HabitacionHostal.class);
		return (List<HabitacionHostal>) q.executeList();
	}
	
	public List<HabitacionHostal> darHabitacionesHostal (PersistenceManager pm, long idHostal)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHostal() + " WHERE idHostal = ? ");
		q.setResultClass(HabitacionHostal.class);
		q.setParameters(idHostal);
		return (List<HabitacionHostal>) q.executeList();
	}

}
