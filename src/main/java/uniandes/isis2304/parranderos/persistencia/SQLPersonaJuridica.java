package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.PersonaJuridica;

public class SQLPersonaJuridica {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLPersonaJuridica(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarPersonaJuridica (PersistenceManager pm, long idPersonaJuridica, 
			long idSuperIntendenciaTurismo, long idCamaraComercio, int categoria, double precioNoche, String tipo) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaPersonaJuridica() + "(id, idSuperIntendenciaTurismo, idCamaraComercio, categoria, precioNoche, tipo) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idPersonaJuridica, idSuperIntendenciaTurismo, idCamaraComercio, categoria, precioNoche, tipo);
        return (long) q.executeUnique();
	}
	
	public long eliminarPersonaJuridicaPorId (PersistenceManager pm, long idPersonaJuridica){
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaPersonaJuridica() + " WHERE id = ?");
        q.setParameters(idPersonaJuridica);
        return (long) q.executeUnique();
	}
	
	public PersonaJuridica darPersonaJuridicaPorId (PersistenceManager pm, long idPersonaJuridica) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaJuridica() + " WHERE id = ?");
		q.setResultClass(PersonaJuridica.class);
		q.setParameters(idPersonaJuridica);
		return (PersonaJuridica) q.executeUnique();
	}
	
	public PersonaJuridica darPersonaJuridicaPorIdSuperIntendenciaTurismo (PersistenceManager pm, long idSuperIntendenciaTurismo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaJuridica() + " WHERE idSuperIntendenciaTurismo = ?");
		q.setResultClass(PersonaJuridica.class);
		q.setParameters(idSuperIntendenciaTurismo);
		return (PersonaJuridica) q.executeUnique();
	}
	
	public PersonaJuridica darPersonaJuridicaPorIdCamaraComercio (PersistenceManager pm, long idCamaraComercio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaJuridica() + " WHERE idCamaraComercio = ?");
		q.setResultClass(PersonaJuridica.class);
		q.setParameters(idCamaraComercio);
		return (PersonaJuridica) q.executeUnique();
	}
	
	public List<PersonaJuridica> darPersonaJuridicaPorTipo (PersistenceManager pm, String tipo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaJuridica() + " WHERE tipo = ?");
		q.setResultClass(PersonaJuridica.class);
		q.setParameters(tipo);
		return (List<PersonaJuridica>) q.executeList();
	}
	
	public List<PersonaJuridica> darPersonasJuridicas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaPersonaJuridica());
		q.setResultClass(PersonaJuridica.class);
		return (List<PersonaJuridica>) q.executeList();
	}
	
	

}
