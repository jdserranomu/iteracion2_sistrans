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
	
	public List<Long> darInmueblesDisponiblesReservaColectiva(PersistenceManager pm, Date X, Date Y, List<String> servicios, String tipoInmueble, int capacidad){
		String a="";
		if( servicios.size() > 0)
			a += "AND (";
		for (int i=0;i<servicios.size();i++) {
			a=a+"OFR.IDSERVICIOMENAJE= '"+ servicios.get(i) +"' ";
			if (i!= servicios.size()-1) {
				a=a+"OR ";
			}
		}
		if(!a.isEmpty())
			a+=") ";
		String queryString = "SELECT IDINMUEBLE " + 
				"FROM (SELECT INM.ID AS IDINMUEBLE, COUNT(*) AS CNT " + 
				"	FROM INMUEBLE INM " + 
				"	INNER JOIN OFRECESERVICIO OFR ON INM.ID=OFR.IDINMUEBLE " + 
				"	WHERE INM.DISPONIBLE = 1 " + 
				a + 
				"		AND NOT EXISTS (SELECT * " + 
				"				FROM RESERVA RES " + 
				"				WHERE RES.IDINMUEBLE = INM.ID " + 
				"				AND (RES.FECHAINICIO BETWEEN ? AND ? OR RES.FECHAFIN BETWEEN ? AND ? )) " + 
				"		AND INM.CAPACIDAD >= ? AND INM.TIPO = ? " +
				"	GROUP BY INM.ID) " + 
				"WHERE CNT =  ? ";
		Query q=pm.newQuery(SQL, queryString);
		Timestamp xTimestamp = new Timestamp(X.getTime());
		Timestamp yTimestamp = new Timestamp(Y.getTime());
		q.setParameters(xTimestamp, yTimestamp, xTimestamp, yTimestamp, capacidad, tipoInmueble, servicios.size());
		
		q.setResultClass(Long.class);
		return (List<Long>)q.executeList();
	}
	
	public List<Inmueble> darInmueblesDisponiblesEnFechasPorMayorCapacidad(PersistenceManager pm,Date fechaInicio1, Date fechaFin1,int capacidad){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaInmueble() + " WHERE id NOT IN (" + 
				"SELECT IDINMUEBLE " + 
				"FROM RESERVA " + 
				"WHERE (RESERVA.FECHAINICIO< ? AND RESERVA.FECHAFIN> ?) OR (RESERVA.FECHAINICIO< ? AND RESERVA.FECHAFIN> ?) OR (RESERVA.FECHAINICIO BETWEEN ? AND ? AND RESERVA.FECHAFIN BETWEEN ? AND ?)" + 
				"GROUP BY RESERVA.IDINMUEBLE" + 
				") AND DISPONIBLE=1 AND CAPACIDAD>= ?");
		q.setResultClass(Inmueble.class);
		Timestamp fechaInicio = new Timestamp(fechaInicio1.getTime());
		Timestamp fechaFin = new Timestamp(fechaFin1.getTime());
		q.setParameters(fechaInicio, fechaInicio, fechaFin, fechaFin, fechaInicio, fechaFin, fechaInicio, fechaFin, capacidad);
		
		return (List<Inmueble>) q.executeList();
	}
	
	
	public List<Inmueble> darInmueblesSinReservas(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "select * " + 
				"from inmueble " + 
				"where inmueble.id not in (select idinmueble from reserva)");
		q.setResultClass(Inmueble.class);
		return (List<Inmueble>) q.executeList();
	}
	
	public List<Inmueble> darInmueblesConReservas(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "select * " + 
				"from inmueble " + 
				"where inmueble.id in (select idinmueble from reserva)");
		q.setResultClass(Inmueble.class);
		return (List<Inmueble>) q.executeList();
	}

}
