package uniandes.isis2304.parranderos.persistencia;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PersistenciaAlohAndes {
	
	private static Logger log = Logger.getLogger(PersistenciaAlohAndes.class.getName());
	
	public final static String SQL = "javax.jdo.query.SQL";
	
	private static PersistenciaAlohAndes instance;
	
	private PersistenceManagerFactory pmf;
	
	private List <String> tablas;
	
	private SQLUtil sqlUtil;
	
	private SQLApartamento sqlApartamento;
	
	private SQLHabitacion sqlHabitacion;
	
	private SQLHabitacionHostal sqlHabitacionHostal;
	
	private SQLHabitacionHotel sqlHabitacionHotel;
	
	private SQLHabitacionVivienda sqlHabitacionVivienda;
	
	private SQLHorario sqlHorario;
	
	private SQLInmueble sqlInmueble;
	
	private SQLOfreceServicio sqlOfreceServicio;
	
	private SQLOperador sqlOperador;
	
	private SQLPersonaJuridica sqlPersonaJuridica;
	
	private SQLPersonaNatural sqlPersonaNatural;
	
	private SQLReserva sqlReserva;
	
	private SQLServicioMenaje sqlServicioMenaje;
	
	private SQLUsuario sqlUsuario;
	
	private SQLVivienda sqlVivienda;
	
	private PersistenciaAlohAndes() {
		pmf = JDOHelper.getPersistenceManagerFactory("AlohAndes");		
		crearClasesSQL ();
		tablas = new LinkedList<String> ();
		tablas.add ("AlohAndes_sequence");
		tablas.add ("APARTAMENTO");
		tablas.add ("HABITACION");
		tablas.add ("HABITACION_HOSTAL");
		tablas.add ("HABITACION_HOTEL");
		tablas.add ("HABITACION_VIVIENDA");
		tablas.add ("HORARIO");
		tablas.add ("INMUEBLE");
		tablas.add ("OFRECE_SERVICIO");
		tablas.add ("OPERADOR");
		tablas.add ("PERSONA_JURIDICA");
		tablas.add ("PERSONA_NATURAL");
		tablas.add ("RESERVA");
		tablas.add ("SERVICIO_MENAJE");
		tablas.add ("USUARIO");
		tablas.add ("VIVIENDA");
	}
	
	private PersistenciaAlohAndes (JsonObject tableConfig){
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}
	
	private List <String> leerNombresTablas (JsonObject tableConfig){
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;
		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres){
			resp.add (nom.getAsString ());
		}
		return resp;
	}
	
	public static PersistenciaAlohAndes getInstance (){
		if (instance == null){
			instance = new PersistenciaAlohAndes();
		}
		return instance;
	}
	
	public static PersistenciaAlohAndes getInstance (JsonObject tableConfig){
		if (instance == null)
		{
			instance = new PersistenciaAlohAndes (tableConfig);
		}
		return instance;
	}
	
	public void cerrarUnidadPersistencia (){
		pmf.close ();
		instance = null;
	}
	
	private void crearClasesSQL(){
		sqlApartamento = new SQLApartamento(this);
		sqlHabitacion = new SQLHabitacion(this);
		sqlHabitacionHostal = new SQLHabitacionHostal(this);
		sqlHabitacionHotel = new SQLHabitacionHotel(this);
		sqlHabitacionVivienda = new SQLHabitacionVivienda(this);
		sqlHorario = new SQLHorario(this);
		sqlInmueble = new SQLInmueble(this);
		sqlOfreceServicio = new SQLOfreceServicio(this);
		sqlOperador = new SQLOperador(this);
		sqlPersonaJuridica = new SQLPersonaJuridica(this);
		sqlPersonaNatural = new SQLPersonaNatural(this);
		sqlReserva = new SQLReserva(this);
		sqlServicioMenaje = new SQLServicioMenaje(this);
		sqlUsuario = new SQLUsuario(this);
		sqlVivienda = new SQLVivienda(this);
		sqlUtil = new SQLUtil(this);
	}
	
	public String darSeqAlohAndes() {
		return tablas.get(0);
	}
	
	public String darTablaApartamento() {
		return tablas.get(1);
	}
	
	public String darTablaHabitacion() {
		return tablas.get(2);
	}
	
	public String darTablaHabitacionHostal() {
		return tablas.get(3);
	}
	
	public String darTablaHabitacionHotel() {
		return tablas.get(4);
	}
	
	public String darTablaHabitacionVivienda() {
		return tablas.get(5);
	}
	
	public String darTablaHorario() {
		return tablas.get(6);
	}
	
	public String darTablaInmueble() {
		return tablas.get(7);
	}
	
	public String darTablaOfreceServicio() {
		return tablas.get(8);
	}
	
	public String darTablaOperador() {
		return tablas.get(9);
	}
	
	public String darTablaPersonaJuridica() {
		return tablas.get(10);
	}
	
	public String darTablaPersonaNatural() {
		return tablas.get(11);
	}
	
	public String darTablaReserva() {
		return tablas.get(12);
	}
	
	public String darTablaServicioMenaje() {
		return tablas.get(13);
	}
	
	public String darTablaUsuario() {
		return tablas.get(14);
	}
	
	public String darTablaVivienda() {
		return tablas.get(15);
	}
	
	private long nextval(){
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	private String darDetalleException(Exception e) {
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException")){
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	
}
