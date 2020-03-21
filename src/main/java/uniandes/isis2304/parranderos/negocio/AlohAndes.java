/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import sun.security.util.Length;
import uniandes.isis2304.parranderos.persistencia.PersistenciaAlohAndes;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class AlohAndes
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AlohAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public AlohAndes ()
	{
		pp = PersistenciaAlohAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public AlohAndes (JsonObject tableConfig)
	{
		pp = PersistenciaAlohAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	public String aTexto(int var) {
		if (var==1) {
			return "Si";
		}else {
			return "No";
		}
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/
	
	public Apartamento adicionarApartamento (int amoblado, double precioMes, long idPersona, String direccion, int capacidad, int disponible, Date fechaReservaFinal)
	{
		
        log.info ("Adicionando Apartamento en: " +direccion +" con capacidad: "+ capacidad+ ", precio mes: "+ precioMes+ ", amoblado: "+aTexto(amoblado)+", disponible: "+ aTexto(disponible)+ " y dueño: "+ idPersona  );
        Apartamento apto = pp.adicionarApartamento(amoblado, precioMes, idPersona, direccion, capacidad, disponible, fechaReservaFinal);
        log.info ("Adicionando Apartamento: " + apto);
        return apto;
	}
	
	
	
	public List<Apartamento> darApartamentos ()
	{
		log.info ("Consultando Apartamentos");
        List<Apartamento> aptos = pp.darApartamentos();	
        log.info ("Consultando Apartamentos: " + aptos.size() + " existentes");
        return aptos;
	}
	
	
	public Apartamento darApartamentosPorId (int id)
	{
		log.info ("Consultando Apartamento");
        Apartamento apto = pp.darApartamentoPorId(id);
        log.info ("Consultando Apartamento: " + id);
        return apto;
	}
	
	public List<Apartamento> darApartamentosPorIdPersona (int idPersona)
	{
		log.info ("Consultando Apartamentos");
        List<Apartamento> aptos = pp.darApartamentosPorIdPersona(idPersona);	
        log.info ("Consultando "+aptos.size()+" Apartamentos de: " + idPersona);
        return aptos;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION
	 *****************************************************************/	
	public Habitacion adicionarHabitacion (double tamanho, double precioMes, long idPersona, String direccion, int capacidad, int disponible, Date fechaReservaFinal)
	{
		
        log.info ("Adicionando Habitacion en: " +direccion +" con capacidad: "+ capacidad+ ", precio mes: "+ precioMes+ ", tamaño: "+tamanho+", disponible: "+ aTexto(disponible)+ " y dueño: "+ idPersona  );
        Habitacion habitacion = pp.adicionarHabitacion(tamanho, precioMes, idPersona, direccion, capacidad, disponible, fechaReservaFinal);
        log.info ("Adicionando Habitacion: " + habitacion);
        return habitacion;
	}
	
	public List<Habitacion> darHabitaciones ()
	{
		log.info ("Consultando Habitaciones");
        List<Habitacion> hab = pp.darHabitaciones();	
        log.info ("Consultando Habitaciones : " + hab.size() + " existentes");
        return hab;
	}
	
	public Habitacion darHabitacionPorId (int id)
	{
		log.info ("Consultando Habitacion");
        Habitacion hab = pp.darHabitacionPorId(id);
        log.info ("Consultando Habitacion: " + id);
        return hab;
	}
	
	public List<Habitacion> darHabitacionesPorIdPersona (int idPersona)
	{
		log.info ("Consultando Habitaciones");
        List<Habitacion> habs = pp.darHabitacionesPorIdPersona(idPersona);
        log.info ("Consultando "+ habs.size()  +"Habitaciones de: " + idPersona);
        return habs;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACIONES HOSTAL
	 *****************************************************************/	
	public HabitacionHostal adicionarHabitacionHostal(int numero, long idHostal, String direccion, int capacidad, int disponible, Date fechaReservaFinal)
	{
		
        log.info ("Adicionando Habitacion Hostal en: " +direccion +" con capacidad: "+ capacidad+ ", numero: "+ numero+ ", disponible: "+ aTexto(disponible)+ " y del hostal: "+ idHostal  );
        HabitacionHostal habitacion = pp.adicionarHabitacionHostal(numero, idHostal, direccion, capacidad, disponible, fechaReservaFinal);
        log.info ("Adicionando Habitacion: " + habitacion);
        return habitacion;
	}
	
	public List<HabitacionHostal> darHabitacionesHostal ()
	{
		log.info ("Consultando Habitaciones Hostal");
        List<HabitacionHostal> hab = pp.darHabitacionesHostales();	
        log.info ("Consultando Habitaciones : " + hab.size() + " existentes");
        return hab;
	}
	
	public HabitacionHostal darHabitacionHostalPorId (int id)
	{
		log.info ("Consultando Habitacion Hostal");
        HabitacionHostal hab = pp.darHabitacionHostalPorId(id);
        log.info ("Consultando Habitacion: " + id);
        return hab;
	}
	
	public List<HabitacionHostal> darHabitacionesHostal (int idHostal)
	{
		log.info ("Consultando Habitaciones Hostal");
        List<HabitacionHostal> habs = pp.darHabitacionesHostal(idHostal);
        log.info ("Consultando "+ habs.size()  +"Habitaciones de: " + idHostal);
        return habs;
	}
	
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION HOTEL
	 *****************************************************************/
	public HabitacionHotel adicionarHabitacionHotel(long idHotel, int numero, String tipo, double precioNoche, double tamanho, String ubicacion, String direccion, int capacidad, int disponible, Date fechaReservaFinal)
	{
		
        log.info ("Adicionando Habitacion Hotel en: " +direccion +" con capacidad: "+ capacidad+ ", numero: "+ numero+ ", disponible: "+ aTexto(disponible)+", tipo: "+tipo+", tamanho: "+tamanho +" y del hotel: "+ idHotel  );
        HabitacionHotel habitacion = pp.adicionarHabitacionHotel(idHotel, numero, tipo, precioNoche, tamanho, ubicacion, direccion, capacidad, disponible, fechaReservaFinal);
        log.info ("Adicionando Habitacion Hotel: " + habitacion);
        return habitacion;
	}
	
	public List<HabitacionHotel> darHabitacionesHoteles ()
	{
		log.info ("Consultando Habitaciones Hoteles");
        List<HabitacionHotel> hab = pp.darHabitacionesHoteles();	
        log.info ("Consultando Habitaciones : " + hab.size() + " existentes");
        return hab;
	}
	
	public HabitacionHotel darHabitacionHotelPorId (int id)
	{
		log.info ("Consultando Habitacion Hotel");
        HabitacionHotel hab = pp.darHabitacionHotelPorId(id);
        log.info ("Consultando Habitacion: " + id);
        return hab;
	}
	
	public List<HabitacionHotel> darHabitacionesHotel (int idHotel)
	{
		log.info ("Consultando Habitaciones Hotel");
        List<HabitacionHotel> habs = pp.darHabitacionesHotel(idHotel);
        log.info ("Consultando "+ habs.size()  +"Habitaciones de: " + idHotel);
        return habs;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los HABITACION VIVIENDA
	 *****************************************************************/
	public HabitacionVivienda adicionarHabitacionVivienda(long idVivienda, int numero, double precioSemestre, double precioMes,
			double precioNoche, String ubicacion, int individual, String direccion, int capacidad, int disponible, Date fechaReservaFinal)
	{
		
        log.info ("Adicionando Habitacion Vivienda en: " +direccion +" con capacidad: "+ capacidad+ ", numero: "+ numero+ ", disponible: "+ aTexto(disponible)+", precio Mes: "+precioMes+", precio semestre: "+precioSemestre+", precio noche: "+precioNoche+", individual: "+aTexto(individual) +" y de la vivienda: "+ idVivienda  );
        HabitacionVivienda habitacion = pp.adicionarHabitacionVivienda(idVivienda, numero, precioSemestre, precioMes, precioNoche, ubicacion, individual, direccion, capacidad, disponible, fechaReservaFinal);
        log.info ("Adicionando Habitacion Vivienda: " + habitacion);
        return habitacion;
	}
	
	public List<HabitacionVivienda> darHabitacionesViviendas ()
	{
		log.info ("Consultando Habitaciones Vivienda");
        List<HabitacionVivienda> hab = pp.darHabitacionesViviendas();
        log.info ("Consultando Habitaciones Vivienda : " + hab.size() + " existentes");
        return hab;
	}
	
	public HabitacionVivienda darHabitacionViviendaPorId (int id)
	{
		log.info ("Consultando Habitacion Vivienda");
        HabitacionVivienda hab = pp.darHabitacionViviendaPorId(id);
        log.info ("Consultando Habitacion Vivienda: " + id);
        return hab;
	}
	
	public List<HabitacionVivienda> darHabitacionesVivienda (int idVivienda)
	{
		log.info ("Consultando Habitaciones Vivienda");
        List<HabitacionVivienda> habs = pp.darHabitacionesVivienda(idVivienda);
        log.info ("Consultando "+ habs.size()  +"Habitaciones de: " + idVivienda);
        return habs;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los HORARIO
	 *****************************************************************/
	public Horario adicionarHorario(long idHostal, String dia, int horaAbre, int horaCierra)
	{
        log.info ("Adicionando Horario en el dia: " +dia +" con hora abre: "+horaAbre + ", horaCierra: "+ horaCierra+" y del hostal: "+ idHostal  );
        Horario horario = pp.adicionarHorario(idHostal, dia, horaAbre, horaCierra);
        log.info ("Adicionando Horario: " + horario);
        return horario;
	}
	
	public long eliminarHorarioPorIdHostalYDia(long idHostal, String dia) {
		log.info("Borrando el Horario del dia: "+ dia +" del hostal: "+ idHostal);
		long horario=pp.eliminarHorarioPorIdHostalYDia(idHostal, dia);
		log.info("eliminando el horario");
		return horario;
	}

	public Horario darHorarioPorIdHostalYDia (long idHostal, String dia)
	{
		log.info ("Consultando Horario");
        Horario ho = pp.darHorarioPorIdHostalYDia(idHostal, dia);
        log.info ("Consultando Horario del dia: " + dia+ " del hostal: "+ idHostal);
        return ho;
	}
	
	public List<Horario> darHorario ()
	{
		log.info ("Consultando Horarios");
        List<Horario> ho = pp.darHorarios();
        log.info ("Consultando Horarios : " + ho.size() + " existentes");
        return ho;
	}
	
	public List<Horario> darHorariosPorIdHostal(int idHostal)
	{
		log.info ("Consultando Horarios");
        List<Horario> ho = pp.darHorariosPorIdHostal(idHostal);
        log.info ("Consultando "+ ho.size()  +"Horarios de: " + idHostal);
        return ho;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Inmueble
	 *****************************************************************/
	public long eliminarInmueblePorId(long idInmueble) {
		log.info("Eliminando el Inmueble: "+idInmueble);
		long inm=pp.eliminarInmueblePorId(idInmueble);
		log.info("eliminando el inmueble");
		return inm;
	}
	
	public Inmueble darInmueblePorId(long idInmueble)
	{
		log.info ("Consultando Inmueble");
        Inmueble inm = pp.darInmueblePorId(idInmueble);
        log.info ("Consultando Imnueble: " + idInmueble);
        return inm;
	}
	
	public List<Inmueble> darInmuebles ()
	{
		log.info ("Consultando Inmuebles");
        List<Inmueble> inm = pp.darInmuebles();
        log.info ("Consultando Inmuebles: " + inm.size() + " existentes");
        return inm;
	}
	
	public List<Inmueble> darInmueblesPorMayorCapacidad (int capacidad)
	{
		log.info ("Consultando Inmuebles por capacidad");
        List<Inmueble> inm = pp.darInmueblesPorMayorCapacidad(capacidad);
        log.info ("Consultando Inmuebles: " + inm.size() + " existentes");
        return inm;
	}
	
	public List<Inmueble> darInmueblesPorTipo (String tipo)
	{
		log.info ("Consultando Inmuebles por tipo");
        List<Inmueble> inm = pp.darInmueblesPorTipo(tipo);
        log.info ("Consultando Inmuebles: " + inm.size() + " existentes");
        return inm;
	}
	
	public List<Inmueble> darInmueblesPorDisponibilidad(int disponibilidad)
	{
		log.info ("Consultando Inmuebles por disponibilidad");
        List<Inmueble> inm = pp.darInmueblesPorDisponibilidad(disponibilidad);
        log.info ("Consultando Inmuebles: " + inm.size() + " existentes");
        return inm;
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
}
