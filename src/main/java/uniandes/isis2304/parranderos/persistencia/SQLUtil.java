package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Inmueble;
import uniandes.isis2304.parranderos.negocio.ReqConsulta1;
import uniandes.isis2304.parranderos.negocio.ReqConsulta2;
import uniandes.isis2304.parranderos.negocio.ReqConsulta3;
import uniandes.isis2304.parranderos.negocio.ReqConsulta4;
import uniandes.isis2304.parranderos.negocio.ReqConsulta5;
import uniandes.isis2304.parranderos.negocio.ReqConsulta6;
import uniandes.isis2304.parranderos.negocio.ReqConsulta7;
import uniandes.isis2304.parranderos.negocio.Usuario;

public class SQLUtil {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLUtil(PersistenciaAlohAndes paa){
		this.paa = paa;
	}
	
	
	public List<ReqConsulta1> RFC1(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT IC, DC, DA " + 
				"FROM " + 
				"(SELECT OP.ID AS IC, SUM(COALESCE(PAGADO,0)) AS DC " + 
				"FROM OPERADOR OP " + 
				"LEFT OUTER JOIN " + 
				"    (SELECT * " + 
				"    FROM RESERVA RES " + 
				"    WHERE RES.FECHAINICIO>= ? AND RES.FECHAFIN<= ?) ON OP.ID = IDOPERADOR " + 
				"GROUP BY OP.ID) " + 
				"INNER JOIN " + 
				"(SELECT OP.ID AS IA, SUM(COALESCE(PAGADO,0)) AS DA " + 
				"FROM OPERADOR OP " + 
				"LEFT OUTER JOIN " + 
				"    (SELECT * " + 
				"    FROM RESERVA RES " + 
				"    WHERE RES.FECHAINICIO>= ? AND RES.FECHAFIN<= ?) ON OP.ID = IDOPERADOR " + 
				"GROUP BY OP.ID) ON IA=IC");
		q.setResultClass(ReqConsulta1.class);
		Date actualDate = new Date();
		@SuppressWarnings("deprecation")
		Date anoCorridoDate = new Date(actualDate.getYear()-1, actualDate.getMonth(), actualDate.getDate());
		@SuppressWarnings("deprecation")
		Date anoActualDate = new Date(actualDate.getYear(), 0, 1);
		Timestamp actualTimestamp = new Timestamp(actualDate.getTime());
		Timestamp anoCorridoTimestamp = new Timestamp(anoCorridoDate.getTime());
		Timestamp anoActualTimestamp = new Timestamp(anoActualDate.getTime());
		q.setParameters(anoCorridoTimestamp,actualTimestamp, anoActualTimestamp, actualTimestamp);
		return (List<ReqConsulta1>) q.executeList();
	}
	
	public List<ReqConsulta2> RFC2(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT IDINMUEBLE, CNT " + 
				"FROM " + 
				"(SELECT INM.ID AS IDINMUEBLE, COUNT(*) AS CNT " + 
				"FROM INMUEBLE INM " + 
				"INNER JOIN RESERVA RES ON INM.ID=RES.IDINMUEBLE " + 
				"GROUP BY INM.ID " + 
				"ORDER BY COUNT(*) DESC) " + 
				"WHERE ROWNUM  <= 20");
		q.setResultClass(ReqConsulta2.class);
		return (List<ReqConsulta2>) q.executeList();
	}
	
	public List<ReqConsulta3> RFC3(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT INMUEBLE.ID AS IDINMUEBLE, AVG(RESERVA.CAPACIDAD/INMUEBLE.CAPACIDAD) AS TASAOCUPACION "
				+ "FROM INMUEBLE LEFT OUTER JOIN RESERVA ON RESERVA.IDINMUEBLE=INMUEBLE.ID "
				+ "GROUP BY INMUEBLE.ID");
		q.setResultClass(ReqConsulta3.class);
		return (List<ReqConsulta3>) q.executeList();
	}
	
	public List<ReqConsulta4> RFC4(PersistenceManager pm, Date X, Date Y, List<String> servicios){
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
				"	GROUP BY INM.ID) " + 
				"WHERE CNT =  ? ";
		Query q=pm.newQuery(SQL, queryString);
		Timestamp xTimestamp = new Timestamp(X.getTime());
		Timestamp yTimestamp = new Timestamp(Y.getTime());
		q.setParameters(xTimestamp, yTimestamp, xTimestamp, yTimestamp, servicios.size());
		q.setResultClass(ReqConsulta4.class);
		
		

		return (List<ReqConsulta4>)q.executeList();
	}
	
	public List<ReqConsulta5> RFC5(PersistenceManager pm){
		Query q=pm.newQuery(SQL, "select usuario.tipo as tipo, count(reserva.id) as numeroReservas, "
				+ "sum(reserva.fechafin - reserva.fechainicio) as nochesContratado, sum(valortotal) as valorPagado " + 
				"from usuario " + 
				"left outer join reserva on usuario.id = reserva.idusuario " + 
				"group by usuario.tipo");
		q.setResultClass(ReqConsulta5.class);
		return (List<ReqConsulta5>)q.executeList();
	}
	
	public ReqConsulta6 RFC6(PersistenceManager pm, long idUsuario){
		Query q=pm.newQuery(SQL, "select usuario.id as idusuario,  count(reserva.id) as numeroReservas, "
				+ "sum(reserva.fechafin - reserva.fechainicio) as nochesContratado, sum(valortotal) as valorPagado " + 
				"from usuario " + 
				"left outer join reserva on usuario.id = reserva.idusuario " + 
				"where usuario.id = ? " + 
				"group by usuario.id");
		q.setResultClass(ReqConsulta6.class);
		q.setParameters(idUsuario);
		return (ReqConsulta6)q.executeUnique();
	}
	
	
	
	
	public List<Usuario> RFC8(PersistenceManager pm, long idInmueble){
		Query q = pm.newQuery(SQL, "select * " + 
				"from usuario " + 
				"where id in " + 
				"( " + 
				"select idusuario " + 
				"from " + 
				"(select idinmueble, idusuario, count(*) as cnt, sum(fechafin-fechainicio) as suma " + 
				"from reserva " + 
				"where estado<>2 and idinmueble = ? " + 
				"group by idinmueble, idusuario) " + 
				"where cnt >= 3 or suma>=15)");
		q.setParameters(idInmueble);
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}
	
	
	
	
	public List<ReqConsulta7>  mayorDemanda(PersistenceManager pm, String tipo){
		
		Query q = pm.newQuery(SQL, " Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos " + 
				"    from reserva " + 
				"    where estado=0 and idInmueble in (" + 
				"        select id " + 
				"        from inmueble " + 
				"        where tipo=? " + 
				"    ) " + 
				"    group by to_char(fechainicio, 'YYYY-MM') " + 
				"    having count(*) in ( " + 
				"        Select max(cuantos) " + 
				"        from( " + 
				"            Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos " + 
				"            from reserva " + 
				"            where estado=0 and idInmueble in ( " + 
				"                select id " + 
				"                from inmueble " + 
				"                where tipo=? " + 
				"            )" + 
				"            group by to_char(fechainicio, 'YYYY-MM') " + 
				"        )  " + 
				"    ) ");
		
		
		q.setParameters(tipo,tipo);
		q.setResultClass(ReqConsulta7.class);

		return (List<ReqConsulta7>) q.executeList();
	}
	
	public List<ReqConsulta7>  mayorIngresos(PersistenceManager pm, String tipo){
		Query q = pm.newQuery(SQL, "  Select to_char(fechainicio, 'YYYY-MM') as meses, sum (valortotal) as cuantos" + 
				"    from reserva " + 
				"    where estado=0 and idInmueble in ( " + 
				"        select id " + 
				"        from inmueble " + 
				"        where tipo= ? " + 
				"    ) " + 
				"    group by to_char(fechainicio, 'YYYY-MM') " + 
				"    having sum(valortotal) in ( " + 
				"        Select  max(dinero) " + 
				"        from( " + 
				"            Select to_char(fechainicio, 'YYYY-MM') as meses, sum (valortotal) as dinero " + 
				"            from reserva " + 
				"            where estado=0 and idInmueble in ( " + 
				"                select id " + 
				"                from inmueble " + 
				"                where tipo=? " + 
				"            ) " + 
				"            group by to_char(fechainicio, 'YYYY-MM') " + 
				"        )  " + 
				"    ) ");
		q.setResultClass(ReqConsulta7.class);
	
		q.setParameters(tipo,tipo);
		return (List<ReqConsulta7>) q.executeList();
	}
	
	public List<ReqConsulta7>  menorOcupacion(PersistenceManager pm, String tipo){
		Query q = pm.newQuery(SQL, " Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos " + 
				"    from reserva " + 
				"    where estado=0 and idInmueble in (\r\n" + 
				"        select id " + 
				"        from inmueble " + 
				"        where tipo=? " + 
				"    ) " + 
				"    group by to_char(fechainicio, 'YYYY-MM') " + 
				"    having count(*) in ( " + 
				"        Select min(cuantos) " + 
				"        from( " + 
				"            Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos " + 
				"            from reserva " + 
				"            where estado=0 and idInmueble in ( " + 
				"                select id " + 
				"                from inmueble " + 
				"                where tipo=? " + 
				"            )" + 
				"            group by to_char(fechainicio, 'YYYY-MM') " + 
				"        )  " + 
				"    ) ");
		q.setResultClass(ReqConsulta7.class);
		q.setParameters(tipo,tipo);
		return (List<ReqConsulta7>) q.executeList();
	}
	
	public long nextvalInmueble (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqInmueble () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	public long nextvalOperador (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqOperador () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	public long nextvalReserva (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqReserva () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	public long nextvalUsuario (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqUsuario () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	public long nextvalReservaColectiva (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqReservaColectiva() + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}
	
	

	public long [] limpiarAlohAndes (PersistenceManager pm){
		Query qApartamento = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaApartamento ());
		Query qHabitacion = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacion ());
		Query qHabitacionHostal = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionHostal ());
		Query qHabitacionHotel = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionHotel ());
		Query qHabitacionVivienda = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHabitacionVivienda ());
		Query qHorario = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaHorario ());
		Query qInmueble = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaInmueble ());
		Query qOfreceServicio = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaOfreceServicio ());
		Query qOperador = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaOperador ());
		Query qPersonaJuridica = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaPersonaJuridica ());
		Query qPersonaNatural = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaPersonaNatural ());
		Query qReserva = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaReserva ());
		Query qServicioMenaje = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaServicioMenaje ());
		Query qUsuario = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaUsuario ());
		Query qVivienda = pm.newQuery(SQL, "DELETE FROM " + paa.darTablaVivienda ());
		
        
        
        long apartamentoEliminados = (long) qApartamento.executeUnique();
        long habitacionEliminados = (long) qHabitacion.executeUnique();
        long habitacionHostalEliminados = (long) qHabitacionHostal.executeUnique();
        long habitacionHotelEliminados = (long) qHabitacionHotel.executeUnique();
        long habitacionViviendaEliminados = (long) qHabitacionVivienda.executeUnique();
        long horarioEliminados = (long) qHorario.executeUnique();
        long inmuebleEliminados = (long) qInmueble.executeUnique();
        long ofreceServicioEliminados = (long) qOfreceServicio.executeUnique();
        long operadorEliminados = (long) qOperador.executeUnique();
        long personaJuridicaEliminados = (long) qPersonaJuridica.executeUnique();
        long personaNaturalEliminados = (long) qPersonaNatural.executeUnique();
        long reservaEliminados = (long) qReserva.executeUnique();
        long servicioMenajeEliminados = (long) qServicioMenaje.executeUnique();
        long usuarioEliminados = (long) qUsuario.executeUnique();
        long viviendaEliminados = (long) qVivienda.executeUnique();
        
        
        
        
        return new long[] {apartamentoEliminados, habitacionEliminados, habitacionHostalEliminados, 
        		habitacionHotelEliminados, habitacionViviendaEliminados, horarioEliminados, 
        		inmuebleEliminados, ofreceServicioEliminados, operadorEliminados, 
        		personaJuridicaEliminados, personaNaturalEliminados, reservaEliminados, 
        		servicioMenajeEliminados, usuarioEliminados, viviendaEliminados};
	}

	
	
}
