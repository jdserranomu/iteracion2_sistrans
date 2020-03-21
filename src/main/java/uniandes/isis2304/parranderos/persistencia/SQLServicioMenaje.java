package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.ServicioMenaje;

public class SQLServicioMenaje {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLServicioMenaje(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarServicioMenaje (PersistenceManager pm, String nombre, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaServicioMenaje() + "(nombre, tipo) values (?, ?)");
        q.setParameters(nombre, tipo);
        return (long) q.executeUnique();
	}
	
	public long eliminarServicioMenajePorNombre (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaServicioMenaje() + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}
	
	public ServicioMenaje darServicioMenajePorNombre (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaServicioMenaje() + " WHERE nombre = ?");
		q.setResultClass(ServicioMenaje.class);
		q.setParameters(nombre);
		return (ServicioMenaje) q.executeUnique();
	}
	
	public List<ServicioMenaje> darServiciosMenajesPorTipo (PersistenceManager pm, String tipo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaServicioMenaje() + " WHERE tipo = ?");
		q.setResultClass(ServicioMenaje.class);
		q.setParameters(tipo);
		return (List<ServicioMenaje>) q.executeList();
	}
	
	public List<ServicioMenaje> darServiciosMenajes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaServicioMenaje());
		q.setResultClass(ServicioMenaje.class);
		return (List<ServicioMenaje>) q.executeList();
	}
	
}
