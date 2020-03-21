package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Apartamento;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.HabitacionHostal;
import uniandes.isis2304.parranderos.negocio.HabitacionHotel;
import uniandes.isis2304.parranderos.negocio.HabitacionVivienda;
import uniandes.isis2304.parranderos.negocio.Horario;
import uniandes.isis2304.parranderos.negocio.Inmueble;

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
		tablas.add ("ALOHANDES_SEQUENCE");
		tablas.add ("APARTAMENTO");
		tablas.add ("HABITACION");
		tablas.add ("HABITACIONHOSTAL");
		tablas.add ("HABITACIONHOTEL");
		tablas.add ("HABITACIONVIVIENDA");
		tablas.add ("HORARIO");
		tablas.add ("INMUEBLE");
		tablas.add ("OFRECESERVICIO");
		tablas.add ("OPERADOR");
		tablas.add ("PERSONAJURIDICA");
		tablas.add ("PERSONANATURAL");
		tablas.add ("RESERVA");
		tablas.add ("SERVICIOMENAJE");
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
	
	/* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTOS
	 *****************************************************************/
	
	public Apartamento adicionarApartamento(int amoblado, double precioMes, long idPersona, String direccion, int capacidad, int disponible, Date fechaReservaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long idInmueble = nextval ();
            long tuplaInmuble = sqlInmueble.adicionarInmueble(pm, idInmueble, direccion, Inmueble.TIPO_APARTAMENTO, capacidad, disponible, fechaReservaFinal);
            long tuplasInsertadas = sqlApartamento.adicionarApartamento(pm, idInmueble, amoblado,precioMes,idPersona);
            tx.commit();
            log.trace ("Inserción de Apartamento: " + idInmueble + ": " + tuplasInsertadas + " tuplas insertadas" + " tuples inmueble "+tuplaInmuble);
            return new Apartamento(idInmueble, amoblado, precioMes, idPersona);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Apartamento> darApartamentos (){
		return sqlApartamento.darApartamentos (pmf.getPersistenceManager());
	}
	
	public Apartamento darApartamentoPorId (long idApartamento)
	{
		return sqlApartamento.darApartamentoPorId(pmf.getPersistenceManager(), idApartamento);
	}
	
	public List<Apartamento> darApartamentosPorIdPersona (long idPersona){
		return sqlApartamento.darApartamentosPorIdPersona (pmf.getPersistenceManager(), idPersona);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION
	 *****************************************************************/
	
	public Habitacion adicionarHabitacion(double tamanho, double precioMes, long idPersona, String direccion, int capacidad, int disponible, Date fechaReservaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long idInmueble = nextval ();
            long tuplaInmuble = sqlInmueble.adicionarInmueble(pm, idInmueble, direccion, Inmueble.TIPO_HABITACION, capacidad, disponible, fechaReservaFinal);
            long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, idInmueble, tamanho ,precioMes,idPersona);
            tx.commit();
            log.trace ("Inserción de Habitacion: " + idInmueble + ": " + tuplasInsertadas + " tuplas insertadas" + " tuples inmueble "+tuplaInmuble);
            return new Habitacion(idInmueble, tamanho, precioMes, idPersona);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<Habitacion> darHabitaciones (){
		return sqlHabitacion.darHabitaciones(pmf.getPersistenceManager());
	}
	
	public Habitacion darHabitacionPorId (long idHabitacion)
	{
		return sqlHabitacion.darHabitacionPorId(pmf.getPersistenceManager(), idHabitacion);
	}
	
	public List<Habitacion> darHabitacionesPorIdPersona (long idPersona){
		return sqlHabitacion.darHabitacionesPorIdPersona (pmf.getPersistenceManager(), idPersona);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION HOSTAL
	 *****************************************************************/
	
	public HabitacionHostal adicionarHabitacionHostal(int numero, long idHostal, String direccion, int capacidad, int disponible, Date fechaReservaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long idInmueble = nextval ();
            long tuplaInmuble = sqlInmueble.adicionarInmueble(pm, idInmueble, direccion, Inmueble.TIPO_HABITACIONHOSTAL, capacidad, disponible, fechaReservaFinal);
            long tuplasInsertadas = sqlHabitacionHostal.adicionarHabitacionHostal(pm, idInmueble, numero, idHostal);
            tx.commit();
            log.trace ("Inserción de Habitacion Hostal: " + idInmueble + ": " + tuplasInsertadas + " tuplas insertadas" + " tuples inmueble "+tuplaInmuble);
            return new HabitacionHostal(idInmueble, idHostal, numero);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<HabitacionHostal> darHabitacionesHostales (){
		return sqlHabitacionHostal.darHabitacionesHostales(pmf.getPersistenceManager());
	}
	
	public HabitacionHostal darHabitacionHostalPorId (long idHabitacionHostal)
	{
		return sqlHabitacionHostal.darHabitacionHostalPorId(pmf.getPersistenceManager(), idHabitacionHostal);
	}
	
	public List<HabitacionHostal> darHabitacionesHostal (long idHostal){
		return sqlHabitacionHostal.darHabitacionesHostal (pmf.getPersistenceManager(), idHostal);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION HOTEL
	 *****************************************************************/
	
	public HabitacionHotel adicionarHabitacionHotel(long idHotel, int numero, String tipo, double precioNoche, double tamanho, String ubicacion, String direccion, int capacidad, int disponible, Date fechaReservaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long idInmueble = nextval ();
            long tuplaInmuble = sqlInmueble.adicionarInmueble(pm, idInmueble, direccion, Inmueble.TIPO_HABITACIONHOTEL, capacidad, disponible, fechaReservaFinal);
            long tuplasInsertadas = sqlHabitacionHotel.adicionarHabitacionHotel(pm, idInmueble, idHotel, numero, tipo, precioNoche, tamanho, ubicacion);
            tx.commit();
            log.trace ("Inserción de Habitacion Hotel: " + idInmueble + ": " + tuplasInsertadas + " tuplas insertadas" + " tuples inmueble "+tuplaInmuble);
            return new HabitacionHotel(idInmueble, idHotel, numero, tipo, precioNoche, tamanho, ubicacion);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<HabitacionHotel> darHabitacionesHoteles (){
		return sqlHabitacionHotel.darHabitacionesHoteles(pmf.getPersistenceManager());
	}
	
	public HabitacionHotel darHabitacionHotelPorId (long idHabitacionHotel)
	{
		return sqlHabitacionHotel.darHabitacionHotelPorId(pmf.getPersistenceManager(), idHabitacionHotel);
	}
	
	public List<HabitacionHotel> darHabitacionesHotel (long idHotel){
		return sqlHabitacionHotel.darHabitacionesHotel (pmf.getPersistenceManager(), idHotel);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION VIVIENDA
	 *****************************************************************/
	
	public HabitacionVivienda adicionarHabitacionVivienda(long idVivienda, int numero, double precioSemestre, double precioMes,
			double precioNoche, String ubicacion, int individual, String direccion, int capacidad, int disponible, Date fechaReservaFinal) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long idInmueble = nextval ();
            long tuplaInmuble = sqlInmueble.adicionarInmueble(pm, idInmueble, direccion, Inmueble.TIPO_HABITACIONVIVIENDA, capacidad, disponible, fechaReservaFinal);
            long tuplasInsertadas = sqlHabitacionVivienda.adicionarHabitacionVivienda(pm, idInmueble, idVivienda, numero, precioSemestre, precioMes, precioNoche, ubicacion, individual);
            tx.commit();
            log.trace ("Inserción de Habitacion Hotel: " + idInmueble + ": " + tuplasInsertadas + " tuplas insertadas" + " tuples inmueble "+tuplaInmuble);
            return new HabitacionVivienda(idInmueble, idVivienda, numero, precioSemestre, precioMes, precioNoche, ubicacion, individual);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public List<HabitacionVivienda> darHabitacionesViviendas (){
		return sqlHabitacionVivienda.darHabitacionesViviendas(pmf.getPersistenceManager());
	}
	
	public HabitacionVivienda darHabitacionViviendaPorId (long idHabitacionVivienda)
	{
		return sqlHabitacionVivienda.darHabitacionViviendaPorId(pmf.getPersistenceManager(), idHabitacionVivienda);
	}
	
	public List<HabitacionVivienda> darHabitacionesVivienda (long idVivienda){
		return sqlHabitacionVivienda.darHabitacionesVivienda (pmf.getPersistenceManager(), idVivienda);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HORARIO
	 *****************************************************************/
	
	public Horario adicionarHorario(long idHostal, String dia, int horaAbre, int horaCierra) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long tuplasInsertadas = sqlHorario.adicionarHorario(pm, idHostal, dia, horaAbre, horaCierra);
            tx.commit();
            log.trace ("Inserción de Horario: " + idHostal + " " + dia + ": " + tuplasInsertadas + " tuplas insertadas");
            return new Horario(idHostal, dia, horaAbre, horaCierra);
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long eliminarHorarioPorIdHostalYDia (long idHostal, String dia) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long resp = sqlHorario.eliminarHorarioPorIdHostalYDia(pm, idHostal, dia);
            tx.commit();
            return resp;
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally{
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public Horario darHorarioPorIdHostalYDia (long idHostal, String dia)
	{
		return sqlHorario.darHorarioPorIdHostalYDia(pmf.getPersistenceManager(), idHostal, dia);
	}
	
	public List<Horario> darHorariosPorIdHostal (long idHostal){
		return sqlHorario.darHorariosPorIdHostal(pmf.getPersistenceManager(), idHostal);
	}
	
	public List<Horario> darHorarios(){
		return sqlHorario.darHorarios(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Inmueble
	 *****************************************************************/
	public long eliminarInmueblePorId (long idInmueble) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long resp = sqlInmueble.eliminarInmueblePorId(pm, idInmueble);
            tx.commit();
            return resp;
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally{
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public Inmueble darInmueblePorId (long idInmueble)
	{
		return sqlInmueble.darInmueblePorId(pmf.getPersistenceManager(), idInmueble);
	}
	
	public List<Inmueble> darInmuebles (){
		return sqlInmueble.darInmuebles(pmf.getPersistenceManager());
	}
	
	public List<Inmueble> darInmueblesPorMayorCapacidad(int capacidad){
		return sqlInmueble.darInmueblesPorMayorCapacidad(pmf.getPersistenceManager(), capacidad);
	}
	
	public List<Inmueble> darInmueblesPorTipo(String tipo){
		return sqlInmueble.darInmueblesPorTipo(pmf.getPersistenceManager(), tipo);
	}
	
	public List<Inmueble> darInmueblesPorDisponibilidad(int disponibilidad){
		return sqlInmueble.darInmueblesPorDisponibilidad(pmf.getPersistenceManager(), disponibilidad);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los OfreceServicio
	 *****************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public long [] limpiarAlohAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try{
            tx.begin();
            long [] resp = sqlUtil.limpiarAlohAndes (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e){
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
		
	}
}
