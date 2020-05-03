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

package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.RSAKeyValueResolver;

import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Inmueble;
import uniandes.isis2304.parranderos.negocio.ReqFun9;
import uniandes.isis2304.parranderos.negocio.Reserva;


/**
 * Clase con los métdos de prueba de funcionalidad sobre TIPOBEBIDA
 * @author Germán Bravo
 *
 */
public class RF9_10Test
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(RF9_10Test.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private AlohAndes alohAndes;
	
    /* ****************************************************************
	 * 			Métodos de prueba para la tabla TipoBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla TipoBebida
	 * 1. Adicionar un tipo de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un tipo de bebida por su identificador
	 * 4. Borrar un tipo de bebida por su nombre
	 */
    @Test
	public void RF9Test() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre RF9");
			alohAndes = new AlohAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		
    		
    		Habitacion hab= alohAndes.adicionarHabitacion(500, 50, 1, "kdejd", 1, 1, null);
    		Date inicio= new Date(3500, 1, 1);
    		Date fin= new Date(3500, 2, 10);
			Reserva prueba= alohAndes.adicionarReserva(inicio, fin,1, 1,hab.getId());
			List<ReqFun9> lista= alohAndes.deshabilitarOfertaAlojamiento(hab.getId());
			for (int i=0; i<lista.size();i++) {
				ReqFun9 actual= lista.get(i);
				if (actual.getaCambiar().getId()==prueba.getId()) {
					assertNotNull("debe poder reubicar la reserva",actual.getNueva());
				}
				alohAndes.eliminarReservaporId(actual.getNueva().getId());
			}
			alohAndes.eliminarReservaporId(prueba.getId());
		//	alohAndes.habilitadrOfertaDeInmueble(hab.getId());
			alohAndes.eliminarInmueblePorId(hab.getId());
			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla reserva.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Rf9");
		}
		finally
		{
			
			//alohAndes.limpiarAlohAndes();
    		alohAndes.cerrarUnidadPersistencia ();    		
		}
	}

    
    
    
    @Test
	public void RF9TestGrande() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre RF9");
			alohAndes = new AlohAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		List<Long> ids= new ArrayList<>();
    		Habitacion hab= alohAndes.adicionarHabitacion(500, 50, 1, "kdejd", 1, 1, null);

    		for (int i=1; i<6;i++) {
    			Date inicio= new Date(3500+i, 1, 1);
        		Date fin= new Date(3500+i, 2, 10);
        		Reserva prueba= alohAndes.adicionarReserva(inicio, fin,1, 1,hab.getId());
        		ids.add(prueba.getId());
    		}
    		
			List<ReqFun9> lista= alohAndes.deshabilitarOfertaAlojamiento(hab.getId());
			for (int i=0; i<lista.size();i++) {
				ReqFun9 actual= lista.get(i);
			
				assertNotNull("debe poder reubicar la reserva",actual.getNueva());
				alohAndes.eliminarReservaporId(actual.getNueva().getId());
				
			}
			//alohAndes.habilitadrOfertaDeInmueble(42);
			alohAndes.eliminarInmueblePorId(hab.getId());
			
			for (int i=1; i<ids.size();i++) {
    			alohAndes.eliminarReservaporId(ids.get(i));
    		}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla reserva.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Rf9");
		}
		finally
		{
			//alohAndes.limpiarAlohAndes();
    		alohAndes.cerrarUnidadPersistencia ();    		
		}
	}
    
    
   
       
    
    
    @Test
	public void RF10() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre RF9");
			alohAndes = new AlohAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de RF9 incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
    		List<Long> ids= new ArrayList<>();
    		Habitacion hab= alohAndes.adicionarHabitacion(500, 50, 1, "kdejd", 1, 0, null);

    		
			alohAndes.habilitadrOfertaDeInmueble(hab.getId());
			Inmueble a= alohAndes.darInmueblePorId(hab.getId());
			assertTrue("Debe estar habilitado", a.getDisponible()==1);
			alohAndes.eliminarInmueblePorId(hab.getId());
			
			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla reserva.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Rf9");
		}
		finally
		{
			//alohAndes.limpiarAlohAndes();
    		alohAndes.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
