package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.HabitacionHotel;

public class SQLHabitacionHotel {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLHabitacionHotel(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarHabitacionHotel (PersistenceManager pm, long idHabitacionHotel, 
			long idHotel, int numero, String tipo, double precioNoche, double tamanho, String ubicacion) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaHabitacionHotel() + 
        		"(id, idHotel, numero, tipo, precioNoche, tamanho, ubicacion) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idHabitacionHotel, idHotel, numero, tipo, precioNoche, tamanho, ubicacion);
        return (long) q.executeUnique();
	}
	/**
	public long eliminarHabitacionHotelPorId (PersistenceManager pm, long idHabitacionHotel)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionHotel() + " WHERE id = ?");
        q.setParameters(idHabitacionHotel);
        return (long) q.executeUnique();
	}
	*/
	public HabitacionHotel darHabitacionHotelPorId (PersistenceManager pm, long idHabitacionHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHotel() + " WHERE id = ?");
		q.setResultClass(HabitacionHotel.class);
		q.setParameters(idHabitacionHotel);
		return (HabitacionHotel) q.executeUnique();
	}
	
	public List<HabitacionHotel> darHabitacionesHoteles (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHotel());
		q.setResultClass(HabitacionHotel.class);
		return (List<HabitacionHotel>) q.executeList();
	}
	
	public List<HabitacionHotel> darHabitacionesHotel (PersistenceManager pm, long idHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionHotel() + " WHERE idHotel = ?");
		q.setResultClass(HabitacionHotel.class);
		q.setParameters(idHotel);
		return (List<HabitacionHotel>) q.executeList();
	}
	

}
