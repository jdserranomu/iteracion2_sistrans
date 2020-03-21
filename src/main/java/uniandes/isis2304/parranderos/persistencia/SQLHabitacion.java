package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Habitacion;

public class SQLHabitacion {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLHabitacion(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarHabitacion (PersistenceManager pm, long idHabitacion, double tamanho, double precioMes, long idPersona) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaHabitacion () + "(id, tamanho, precioMes, idPersona) values (?, ?, ?, ?)");
        q.setParameters(idHabitacion , tamanho, precioMes, idPersona);
        return (long) q.executeUnique();
	}
	
	public long eliminarHabitacionPorId (PersistenceManager pm, long idHabitacion){
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacion() + " WHERE id = ?");
        q.setParameters(idHabitacion);
        return (long) q.executeUnique();
	}
	
	public Habitacion darHabitacionPorId (PersistenceManager pm, long idHabitacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacion () + " WHERE id = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(idHabitacion);
		return (Habitacion) q.executeUnique();
	}
	
	public List<Habitacion> darHabitaciones (PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacion ());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	
	public List<Habitacion> darHabitacionesPorIdPersona (PersistenceManager pm, long idPersona) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacion () + " WHERE idPersona = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(idPersona);
		return (List<Habitacion>) q.executeList();
	}
	
	
}
