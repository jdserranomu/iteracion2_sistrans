package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Vivienda;

public class SQLVivienda {
	
private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLVivienda(PersistenciaAlohAndes paa){
		this.paa = paa;
	}
	
	public long adicionarVivienda (PersistenceManager pm, long idVivienda, int numeroHabitaciones, double costoNoche, int diasUtilizado, long idPersona) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaVivienda() + "(id, numeroHabitaciones, costoNoche, diasUtilizado, idPersona) values (?, ?, ?, ?, ?)");
        q.setParameters(idVivienda, numeroHabitaciones, costoNoche, diasUtilizado, idPersona);
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
	
	public long actualizarViviendaDiasUtilizado (PersistenceManager pm, long idVivienda,int  diasUtilizado) {
		Query q= pm.newQuery(SQL, "UPDATE "+ paa.darTablaVivienda()+ " SET DIASUTILIZADO= ? WHERE ID= ?");
		q.setParameters(diasUtilizado,idVivienda);
		return (long) q.executeUnique();
	}
	
	public List<Long> darViviendasDisponiblesReservaColectiva(PersistenceManager pm, Date X, Date Y, List<String> servicios, int capacidad, long days){
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
				"	FROM VIVIENDA VIV" + 
				"	INNER JOIN INMUEBLE INM ON INM.ID=VIV.ID" + 
				"	INNER JOIN OFRECESERVICIO OFR ON INM.ID=OFR.IDINMUEBLE " + 
				"	WHERE INM.DISPONIBLE = 1 " + a + 
				"		AND NOT EXISTS (SELECT * " + 
				"				FROM RESERVA RES " + 
				"				WHERE RES.IDINMUEBLE = INM.ID AND RES.ESTADO<>2 " + 
				"				AND (RES.FECHAINICIO BETWEEN ? AND ? OR RES.FECHAFIN BETWEEN ? AND ? )) " + 
				"		AND INM.CAPACIDAD >= ? " +
				"		AND VIV.DIASUTILIZADO+?<=30"+
				"	GROUP BY INM.ID) " + 
				"WHERE CNT =  ? ";
		Query q=pm.newQuery(SQL, queryString);
		Timestamp xTimestamp = new Timestamp(X.getTime());
		Timestamp yTimestamp = new Timestamp(Y.getTime());
		q.setParameters(xTimestamp, yTimestamp, xTimestamp, yTimestamp, capacidad, days, servicios.size());
		q.setResultClass(Long.class);
		return (List<Long>)q.executeList();
	}
	
	
	/**
	public long eliminarViviendaPorId (PersistenceManager pm, long idVivienda)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaVivienda() + " WHERE id = ?");
        q.setParameters(idVivienda);
        return (long) q.executeUnique();
	}
	*/
	
	/**
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
	*/
}
