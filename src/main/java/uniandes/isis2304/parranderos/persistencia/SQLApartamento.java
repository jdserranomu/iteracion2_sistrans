package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Apartamento;

public class SQLApartamento {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLApartamento(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarApartamento (PersistenceManager pm, long idApartamento, int amoblado, double precioMes, long idPersona) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaApartamento() + "(id, amoblado, precioMes, idPersona) values (?, ?, ?, ?)");
        q.setParameters(idApartamento, amoblado, precioMes, idPersona);
        return (long) q.executeUnique();
	}
	
	public long eliminarApartamentoPorId (PersistenceManager pm, long idApartamento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaApartamento () + " WHERE id = ?");
        q.setParameters(idApartamento);
        return (long) q.executeUnique();
	}
	
	public Apartamento darApartamentoPorId (PersistenceManager pm, long idApartamento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaApartamento() + " WHERE id = ?");
		q.setResultClass(Apartamento.class);
		q.setParameters(idApartamento);
		return (Apartamento) q.executeUnique();
	}
	
	public List<Apartamento> darApartamentos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaApartamento());
		q.setResultClass(Apartamento.class);
		return (List<Apartamento>) q.executeList();
	}
	
	public List<Apartamento> darApartamentosPorIdPersona (PersistenceManager pm, long idPersona) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaApartamento () + " WHERE idPersona = ?");
		q.setResultClass(Apartamento.class);
		q.setParameters(idPersona);
		return (List<Apartamento>) q.executeList();
	}

}
