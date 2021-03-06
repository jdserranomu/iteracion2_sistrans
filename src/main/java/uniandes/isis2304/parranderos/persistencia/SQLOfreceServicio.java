package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.OfreceServicio;

public class SQLOfreceServicio {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLOfreceServicio(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarOfreceServicio (PersistenceManager pm, String idServicioMenaje, long idInmueble, Double costo, Integer cantidad) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaOfreceServicio() + "(idServicioMenaje, idInmueble, costo, cantidad) values (?, ?, ?, ?)");
        q.setParameters(idServicioMenaje, idInmueble, costo, cantidad);
        return (long) q.executeUnique();
	}
	
	public long eliminarOfreceServicio (PersistenceManager pm, String idServicioMenaje, long idInmueble)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaOfreceServicio() + " WHERE idServicioMenaje = ? AND idInmueble = ?");
        q.setParameters(idServicioMenaje, idInmueble);
        return (long) q.executeUnique();
	}
	
	public OfreceServicio darOfreceServicio (PersistenceManager pm, String idServicioMenaje, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOfreceServicio() + " WHERE idServicioMenaje = ? AND idInmueble = ?");
		q.setResultClass(OfreceServicio.class);
		q.setParameters(idServicioMenaje, idInmueble);
		return (OfreceServicio) q.executeUnique();
	}
	
	public List<OfreceServicio> darOfrecenServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOfreceServicio());
		q.setResultClass(OfreceServicio.class);
		return (List<OfreceServicio>) q.executeList();
	}
	
	public List<OfreceServicio> darOfreceServicios (PersistenceManager pm, long idInmueble)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOfreceServicio() + " WHERE idInmueble = ?");
		q.setResultClass(OfreceServicio.class);
		q.setParameters(idInmueble);
		return (List<OfreceServicio>) q.executeList();
	}
	
	public List<OfreceServicio> darOfrecenServicio (PersistenceManager pm, String idServicioMenaje)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaOfreceServicio() + " WHERE idServicioMenaje = ?");
		q.setResultClass(OfreceServicio.class);
		q.setParameters(idServicioMenaje);
		return (List<OfreceServicio>) q.executeList();
	}
	
	

}
