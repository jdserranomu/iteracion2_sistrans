package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLUtil {
	
	private final static String SQL = PersistenciaAlohAndes.SQL;
	
	private PersistenciaAlohAndes paa;
	
	public SQLUtil(PersistenciaAlohAndes paa){
		this.paa = paa;
	}
	
	public long nextval (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT "+ paa.darSeqAlohAndes () + ".nextval FROM DUAL");
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
