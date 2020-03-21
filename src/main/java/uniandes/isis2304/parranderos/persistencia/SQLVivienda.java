package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Vivienda;

public class SQLVivienda {
	
private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLVivienda(PersistenciaAlohAndes ppa){
		this.paa = paa;
	}
	
	public long adicionarVivienda (PersistenceManager pm, long idVivienda, int numeroHabitaciones, double costoNoche, int diasUtilizado, long idPersona) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaVivienda() + "(id, numeroHabitaciones, costoNoche, diasUtilizado, idPersona) values (?, ?, ?, ?, ?)");
        q.setParameters(idVivienda, numeroHabitaciones, costoNoche, diasUtilizado, idPersona);
        return (long) q.executeUnique();
	}
	
	public long eliminarViviendaPorId (PersistenceManager pm, long idVivienda)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaVivienda() + " WHERE id = ?");
        q.setParameters(idVivienda);
        return (long) q.executeUnique();
	}
	
	public Vivienda darViviendaPorId (PersistenceManager pm, long idVivienda) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaVivienda() + " WHERE id = ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(idVivienda);
		return (Vivienda) q.executeUnique();
	}
	
	public List<Vivienda> darViviendas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaVivienda());
		q.setResultClass(Vivienda.class);
		return (List<Vivienda>) q.executeList();
	}
	
	public List<Vivienda> darViviendasPorIdPersona (PersistenceManager pm, long idPersona) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaVivienda() + " WHERE idPersona = ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(idPersona);
		return (List<Vivienda>) q.executeList();
	}
	
	public List<Vivienda> darViviendasConCostoNocheMenorIgual (PersistenceManager pm, double costoUmbral) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaVivienda() + " WHERE costoNoche <= ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(costoUmbral);
		return (List<Vivienda>) q.executeList();
	}
	
	public List<Vivienda> darViviendasConNumeroHabitacionesMayor (PersistenceManager pm, int numeroHabitacionesUmbral) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaVivienda() + " WHERE numeroHabitaciones > ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(numeroHabitacionesUmbral);
		return (List<Vivienda>) q.executeList();
	}

}
