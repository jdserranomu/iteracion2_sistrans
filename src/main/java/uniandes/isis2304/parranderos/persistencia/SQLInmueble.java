package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Inmueble;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SQLInmueble {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLInmueble(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarInmueble (PersistenceManager pm, long idInmueble, String direccion, String tipo, int capacidad, int disponible, Date fechaReservaFinal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaInmueble() + "(id, direccion, tipo, capacidad, disponible, fechaReservaFinal) values (?, ?,  ?, ?, ?, ?)");
        Timestamp fechaReservaFinalTimestamp= null;
        if (fechaReservaFinal!=null)
        	fechaReservaFinalTimestamp = new Timestamp(fechaReservaFinal.getTime());
        q.setParameters(idInmueble, direccion, tipo, capacidad, disponible, fechaReservaFinalTimestamp);
        return (long) q.executeUnique();
	}
	
	public long eliminarInmueblePorId (PersistenceManager pm, long idInmueble)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaInmueble() + " WHERE id = ?");
        q.setParameters(idInmueble);
        return (long) q.executeUnique();
	}
	
	public Inmueble darInmueblePorId (PersistenceManager pm, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble() + " WHERE id = ?");
		q.setResultClass(Inmueble.class);
		q.setParameters(idInmueble);
		return (Inmueble) q.executeUnique();
	}
	
	public List<Inmueble> darInmuebles (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble());
		q.setResultClass(Inmueble.class);
		return (List<Inmueble>) q.executeList();
	}
	
	public List<Inmueble> darInmueblesPorTipo (PersistenceManager pm, String tipo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble() + " WHERE tipo = ?");
		q.setResultClass(Inmueble.class);
		q.setParameters(tipo);
		return (List<Inmueble>) q.executeList();
	}
	
	public List<Inmueble> darInmueblesPorMayorCapacidad (PersistenceManager pm, int capacidad) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble() + " WHERE capacidad > ?");
		q.setResultClass(Inmueble.class);
		q.setParameters(capacidad);
		return (List<Inmueble>) q.executeList();
	}
	
	public List<Inmueble> darInmueblesPorDisponibilidad (PersistenceManager pm, int disponibilidad) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble() + " WHERE disponible = ?");
		q.setResultClass(Inmueble.class);
		q.setParameters(disponibilidad);
		return (List<Inmueble>) q.executeList();
	}
	
	public long retirarOfertaInmueblePorId(PersistenceManager pm, long idInmueble) {
		Query q = pm.newQuery(SQL, "UPDATE " + paa.darTablaInmueble() + " SET disponible = 0 WHERE id = ?");
		q.setParameters(idInmueble);
		return (long) q.executeUnique();
	}
	
	public long habilitarOfertaInmueblePorId(PersistenceManager pm, long idInmueble) {
		Query q = pm.newQuery(SQL, "UPDATE " + paa.darTablaInmueble() + " SET disponible = 1 WHERE id = ?");
		q.setParameters(idInmueble);
		return (long) q.executeUnique();
	}
	
	

}
