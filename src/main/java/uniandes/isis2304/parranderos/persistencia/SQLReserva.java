package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLReserva {

	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLReserva(PersistenciaAlohAndes paa) {
		this.paa = paa;
	}
	
	public long adicionarReserva (PersistenceManager pm, long idReserva, Date fechaInicio, Date fechaFin,
			double valorTotal, Date fechaCancelacion, int pagado, double descuento, int capacidad, 
			int estado, long idOperador, long idUsuario, long idInmueble) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaReserva() + "(id, fechaInicio, fechaFin, valorTotal,"
        		+ "fechaCancelacion, pagado, descuento, capacidad, estado, idOperador, idUsuario, idInmueble) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReserva, fechaInicio, fechaFin, valorTotal, fechaCancelacion, pagado, descuento, capacidad, estado, idOperador, idUsuario, idInmueble);
        return (long) q.executeUnique();
	}
	
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaReserva() + " WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}
	
	public Reserva darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
	}
	
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasEnFechasParaInmueble (PersistenceManager pm, Date fechaStart, Date fechaEnd, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idInmueble = ? AND ((? >= fechaInicio AND ? <= fechaFin) OR (? >= fechaInicio AND ? <= fechaFin))");
		q.setResultClass(Reserva.class);
		q.setParameters(idInmueble, fechaStart, fechaStart, fechaEnd, fechaEnd);
		return (List<Reserva>) q.executeList();
	}
}
