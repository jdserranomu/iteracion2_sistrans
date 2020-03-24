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
import java.io.FileReader;
import java.util.List;

import javax.jdo.PersistenceManagerFactory;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.PersonaNatural;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.persistencia.PersistenciaAlohAndes;
import uniandes.isis2304.parranderos.persistencia.SQLOperador;

/**
 * Clase con los métdos de prueba de funcionalidad sobre TIPOBEBIDA
 * @author Germán Bravo
 *
 */
public class OperadorTest
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(OperadorTest.class.getName());
	
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
    private SQLOperador alohAndes;
    
    
    private PersistenciaAlohAndes paa;
    private PersistenceManagerFactory pmf;
	
    
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
	public void CRDOperadorTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre Operador");
			alohAndes = new SQLOperador(paa);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Operador incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Operador incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <Operador> lista = alohAndes.darOperadores(pmf.getPersistenceManager());
			assertEquals ("No debe haber operadores creados!!", 0, lista.size ());

			// Lectura de los tipos de bebida con un tipo de bebida adicionado
			String nombre = "Juan Perez";
			String email="juan@hotmail.com";
			String telefono="6773212";
			PersonaNatural op = alohAndes.adicionarPersonaNatural(PersonaNatural.TIPO_MEMBROCOMUNIDAD, nombre, email, telefono);
			lista = alohAndes.darOperadores(pmf.getPersistenceManager());
			assertEquals ("Debe haber un operador creado creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", op, lista.get (0));

			// Lectura de los tipos de bebida con dos tipos de bebida adicionados
			String nombre2 = "David Escalante";
			String email2="david@hotmail.com";
			String telefono2="6745452";
			PersonaNatural op2 = alohAndes.adicionarPersonaNatural(PersonaNatural.TIPO_MEMBROCOMUNIDAD, nombre2, email2, telefono2);
			lista = alohAndes.darOperadores();
			assertEquals ("Debe haber dos operadores creador !!", 2, lista.size ());
			assertTrue ("El primer operador adicionado debe estar en la tabla", op.equals (lista.get (0)) || op.equals (lista.get (1)));
			assertTrue ("El segundo operador adicionado debe estar en la tabla", op2.equals (lista.get (0)) || op2.equals (lista.get (1)));

			// Prueba de eliminación de un tipo de bebida, dado su identificador
			long tbEliminados = alohAndes.eliminarOperadorPorId(op.getId());
			assertEquals ("Debe haberse eliminado un operador !!", 1, tbEliminados);
			lista = alohAndes.darOperadores();
			assertEquals ("Debe haber un operador !!", 1, lista.size ());
			assertFalse ("El primer operador adicionado NO debe estar en la tabla", op.equals (lista.get (0)));
			assertTrue ("El segundo operador adicionado debe estar en la tabla", op2.equals (lista.get (0)));
			
			// Prueba de eliminación de un tipo de bebida, dado su identificador
			tbEliminados = alohAndes.eliminarOperadorPorId(op2.getId());
			assertEquals ("Debe haberse eliminado un operador !!", 1, tbEliminados);
			lista = alohAndes.darOperadores();
			assertEquals ("La tabla debió quedar vacía !!", 0, lista.size ());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla operador.\n";
			msg += "Revise el log de alohAndes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla TipoBebida");
		}
		finally
		{
			alohAndes.limpiarAlohAndes();
    		alohAndes.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de TipoBebida
     */
	@Test
	public void unicidadOperador() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Operador");
			alohAndes = new AlohAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohAndes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <Operador> lista = alohAndes.darOperadores();
			assertEquals ("No debe haber operadorres!!", 0, lista.size ());

			String nombre2 = "David Escalante";
			String email2="david@hotmail.com";
			String telefono2="6745452";
			PersonaNatural op2 = alohAndes.adicionarPersonaNatural(PersonaNatural.TIPO_MEMBROCOMUNIDAD, nombre2, email2, telefono2);
			lista = alohAndes.darOperadores();
			assertEquals ("Debe haber un Operador creado !!", 1, lista.size ());

			PersonaNatural op = alohAndes.adicionarPersonaNatural(PersonaNatural.TIPO_MEMBROCOMUNIDAD, nombre2, email2, telefono2);
			assertNull ("No puede adicionar dos tipos de bebida con el mismo nombre !!", op);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla TipoBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla TipoBebida");
		}    				
		finally
		{
			alohAndes.limpiarAlohAndes();
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
