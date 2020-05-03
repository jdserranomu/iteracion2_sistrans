package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.Inmueble;
import uniandes.isis2304.parranderos.negocio.Reserva;
public class RF7Test {

	private static Logger log = Logger.getLogger(RF7Test.class.getName());
	
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
    private JsonObject tableConfig;
    
    private AlohAndes alohAndes;
	
    @Test
	public void creacionReservaColectiva() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre TipoBebida");
			alohAndes = new AlohAndes(openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
			log.info ("Error en prueba insercion reserva colectiva. No se pudo conectar a la base de datos !!.\\n. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Error en prueba insercion reserva colectiva. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		try
		{
    		Date fechaInicio = new SimpleDateFormat("yyy/MM/dd").parse("2023/01/02");
    		Date fechaFin = new SimpleDateFormat("yyy/MM/dd").parse("2023/01/03"); 
    		List<String> servicios = new ArrayList<String>();
    		servicios.add("Limpieza");
    		servicios.add("Radio");
    		alohAndes.adicionarReservaColectiva(fechaInicio, fechaFin, servicios, Inmueble.TIPO_VIVIENDA, 12, 1, 22);
    		fail("La reserva deberia generar excepcion");
		}
		catch (Exception e)
		{
			assertEquals ("La excepcion que se origino no era la adecuada","No hay suficientes inmuebles para oferta" ,e.getMessage());
			System.out.println("Sirvio");
		}
    	try
		{
    		
    		Date fechaInicio = new SimpleDateFormat("yyy/MM/dd").parse("2023/01/02");
    		Date fechaFin = new SimpleDateFormat("yyy/MM/dd").parse("2023/01/03"); 
    		List<String> servicios = new ArrayList<String>();
    		servicios.add("Limpieza");
    		servicios.add("Radio");
    		List<Reserva> reservas = alohAndes.adicionarReservaColectiva(fechaInicio, fechaFin, servicios, Inmueble.TIPO_VIVIENDA, 11, 1, 22);
    		assertEquals ("El tamaño de la lista de reservas no es el correcto", 11, reservas.size ());
    		alohAndes.cancelarReservaColectivaPorId(reservas.get(0).getIdReservaColectiva());
		}
		catch (Exception e)
		{
			fail("La reserva se debia crear sin problema: "+e.getMessage());
			
		}
		finally
		{
			//.limpiarParranderos ();
    		alohAndes.cerrarUnidadPersistencia ();    		
		}
	}
    
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
