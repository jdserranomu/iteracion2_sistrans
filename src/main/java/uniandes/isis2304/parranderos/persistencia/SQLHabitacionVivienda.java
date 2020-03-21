package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.HabitacionVivienda;

public class SQLHabitacionVivienda {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLHabitacionVivienda(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}

	public long adicionarHabitacionVivienda (PersistenceManager pm, long idHabitacionVivienda, long idVivenda, int numero, double precioSemestre, double precioMes, 
			double precioNoche, String ubicacion, int individual) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaHabitacionVivienda() + "(id, idVivienda, numero, precioSemestre, precioMes, "
        		+ "precioNoche, ubicacion, individual) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idHabitacionVivienda, idVivenda, numero, precioSemestre, precioMes, precioNoche, ubicacion, individual);
        return (long) q.executeUnique();
	}
	
	public long eliminarHabitacionViviendaPorId (PersistenceManager pm, long idHabitacionVivienda){
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionVivienda() + " WHERE id = ?");
        q.setParameters(idHabitacionVivienda);
        return (long) q.executeUnique();
	}
	
	public HabitacionVivienda darHabitacionViviendaPorId (PersistenceManager pm, long idHabitacionVivienda) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionVivienda() + " WHERE id = ?");
		q.setResultClass(HabitacionVivienda.class);
		q.setParameters(idHabitacionVivienda);
		return (HabitacionVivienda) q.executeUnique();
	}
	
	public List<HabitacionVivienda> darHabitacionesVivienda (PersistenceManager pm, long idVivienda) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionVivienda() + " WHERE idVivienda = ?");
		q.setResultClass(HabitacionVivienda.class);
		q.setParameters(idVivienda);
		return (List<HabitacionVivienda>) q.executeList();
	}
	
	public List<HabitacionVivienda> darHabitacionesViviendas (PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHabitacionVivienda());
		q.setResultClass(HabitacionVivienda.class);
		return (List<HabitacionVivienda>) q.executeList();
	}
	
}
