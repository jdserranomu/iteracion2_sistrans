package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Horario;

public class SQLHorario {

	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLHorario(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarHorario (PersistenceManager pm, long idHostal, String dia, int horaAbre, int horaCierra) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaHorario() + "(idHostal, dia, horaAbre, horaCierra) values (?, ?, ?, ?)");
        q.setParameters(idHostal, dia, horaAbre, horaCierra);
        return (long) q.executeUnique();
	}

	public long eliminarHorarioPorIdHostalYDia (PersistenceManager pm, long idHostal, String dia)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHorario() + " WHERE idHostal = ? AND dia = ?");
        q.setParameters(idHostal, dia);
        return (long) q.executeUnique();
	}

	public Horario darHorarioPorIdHostalYDia (PersistenceManager pm, long idHostal, String dia) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHorario() + " WHERE idHostal = ? AND dia = ?");
		q.setResultClass(Horario.class);
		q.setParameters(idHostal, dia);
		return (Horario) q.executeUnique();
	}

	public List<Horario> darHorariosPorIdHostal (PersistenceManager pm, long idHostal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHorario() + " WHERE idHostal = ?");
		q.setResultClass(Horario.class);
		q.setParameters(idHostal);
		return (List<Horario>) q.executeList();
	}

	public List<Horario> darHorarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaHorario());
		q.setResultClass(Horario.class);
		return (List<Horario>) q.executeList();
	}
	
}
