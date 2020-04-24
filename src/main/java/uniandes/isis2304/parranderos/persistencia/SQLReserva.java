package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.Date;
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
		Timestamp iniTimestamp = new Timestamp(fechaInicio.getTime());
		Timestamp finTimestamp = new Timestamp(fechaFin.getTime());
		Timestamp cancelacionTimestamp = null;
		if(fechaCancelacion!=null)
			cancelacionTimestamp = new Timestamp(fechaCancelacion.getTime());
        Query q = pm.newQuery(SQL, "INSERT INTO " + paa.darTablaReserva() + "(id, fechaInicio, fechaFin, valorTotal,"
        		+ "fechaCancelacion, pagado, descuento, capacidad, estado, idOperador, idUsuario, idInmueble) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReserva, iniTimestamp, finTimestamp, valorTotal, cancelacionTimestamp, pagado, descuento, capacidad, estado, idOperador, idUsuario, idInmueble);
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
	
	public List<Reserva> darReservaPorIdUsuario (PersistenceManager pm, long idUsuario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idusuario = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idUsuario);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasNoCanceladasEnFechasPorIdUsuario (PersistenceManager pm, long idUsuario, Date fechaStart, Date fechaEnd) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idUsuario = ? AND "
				+ "(fechaInicio BETWEEN ? AND ? OR fechaFin BETWEEN ? AND ?) AND estado <> ? ");
		q.setResultClass(Reserva.class);
		Timestamp fechaStarTimestamp = new Timestamp(fechaStart.getTime());
		Timestamp fechaEndTimestamp = new Timestamp(fechaEnd.getTime());
		q.setParameters(idUsuario, fechaStarTimestamp, fechaEndTimestamp, fechaStarTimestamp, fechaEndTimestamp, Reserva.ESTADO_CANCELADO);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasEnFechasParaInmueble (PersistenceManager pm, Date fechaStart, Date fechaEnd, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idInmueble = ? AND "
				+ "(fechaInicio BETWEEN ? AND ? OR fechaFin BETWEEN ? AND ?) ");
		q.setResultClass(Reserva.class);
		Timestamp fechaStarTimestamp = new Timestamp(fechaStart.getTime());
		Timestamp fechaEndTimestamp = new Timestamp(fechaEnd.getTime());
		q.setParameters(idInmueble, fechaStarTimestamp, fechaEndTimestamp, fechaStarTimestamp, fechaEndTimestamp);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasNoCanceladasEnFechasParaInmueble (PersistenceManager pm, Date fechaStart, Date fechaEnd, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idInmueble = ? AND "
				+ "(fechaInicio BETWEEN ? AND ? OR fechaFin BETWEEN ? AND ?) AND estado <> ? ");
		q.setResultClass(Reserva.class);
		Timestamp fechaStarTimestamp = new Timestamp(fechaStart.getTime());
		Timestamp fechaEndTimestamp = new Timestamp(fechaEnd.getTime());
		q.setParameters(idInmueble, fechaStarTimestamp, fechaEndTimestamp, fechaStarTimestamp, fechaEndTimestamp, Reserva.ESTADO_CANCELADO);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasDespuesDeFechaPorIdInmueble (PersistenceManager pm, Date fecha, long idInmueble) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + paa.darTablaReserva() + " WHERE idInmueble = ? AND fechaFin >= ?");
		q.setResultClass(Reserva.class);
		Timestamp fechaTimestamp = new Timestamp(fecha.getTime());
		q.setParameters(idInmueble, fechaTimestamp);
		return (List<Reserva>) q.executeList();
	}
	
	public long actualizarReservaPorId(PersistenceManager pm, long id, Reserva re) {
		Query q= pm.newQuery(SQL, "UPDATE "+ paa.darTablaReserva()+ " SET FECHAINICIO= ?, FECHAFIN=?, VALORTOTAL=?, "
				+ "FECHACANCELACION=?, PAGADO=?, DESCUENTO=? , CAPACIDAD=?, ESTADO=?, IDOPERADOR=?, IDUSUARIO=?, IDINMUEBLE=? WHERE ID= ?");
		q.setParameters(re.getFechaInicio(),re.getFechaFin(), re.getValorTotal(), re.getFechaCancelacion(), re.getPagado(), 
				re.getDescuento(), re.getCapacidad(), re.getEstado(), re.getIdOperador(), re.getIdUsuario(), re.getIdInmueble(), re.getId());
		return (long) q.executeUnique();
	}
	
}
