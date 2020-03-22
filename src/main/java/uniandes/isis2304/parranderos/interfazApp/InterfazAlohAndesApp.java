package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.Apartamento;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.HabitacionHostal;
import uniandes.isis2304.parranderos.negocio.HabitacionHotel;
import uniandes.isis2304.parranderos.negocio.HabitacionVivienda;
import uniandes.isis2304.parranderos.negocio.Horario;
import uniandes.isis2304.parranderos.negocio.Inmueble;
import uniandes.isis2304.parranderos.negocio.OfreceServicio;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.PersonaJuridica;
import uniandes.isis2304.parranderos.negocio.PersonaNatural;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.ServicioMenaje;
import uniandes.isis2304.parranderos.negocio.Usuario;
import uniandes.isis2304.parranderos.negocio.VOApartamento;
import uniandes.isis2304.parranderos.negocio.Vivienda;

@SuppressWarnings("serial")
public class InterfazAlohAndesApp extends JFrame implements ActionListener{
	
	private static Logger log = Logger.getLogger(InterfazAlohAndesApp.class.getName());
	
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigAppAlohAndes.json";
	
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	private JsonObject tableConfig;
	
	private AlohAndes alohAndes;
	
    private JsonObject guiConfig;
    
    private PanelDatos panelDatos;
    
    private JMenuBar menuBar;
    
    public InterfazAlohAndesApp( ){
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        alohAndes = new AlohAndes(tableConfig);
        
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
    /* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try {
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e){
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    private void configurarFrame(  ){
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	if ( guiConfig == null ){
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );
        setTitle( titulo );
		setSize ( ancho, alto);        
    }
    
    
    private void crearMenu(  JsonArray jsonMenu ){    	
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu){
        	JsonObject jom = men.getAsJsonObject(); 
        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	JMenu menu = new JMenu( menuTitle);
        	for (JsonElement op : opciones){
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
    /* ****************************************************************
	 * 			CRUD de Apartamento
	 *****************************************************************/
    
    public void adicionarApartamento ()
	{
    	//int amoblado, double precioMes, long idPersona, String direccion, int capacidad, int disponible, Date fechaReservaFinal
		try {
			
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
        
	}
    
    public void darApartamentos (){
		try {
			List <Apartamento> lista = alohAndes.darApartamentos();

			String resultado = "En listarTipoBebida";
			resultado +=  "\n" + listarApartamento(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
    
    
    /* ****************************************************************
	 * 			CRUD de Habitacion
	 *****************************************************************/
    
    
    /* ****************************************************************
	 * 			CRUD de Habitacion Hostal
	 *****************************************************************/
    
    
    /* ****************************************************************
	 * 			CRUD de Habitacion Hotel
	 *****************************************************************/
    
    
    /* ****************************************************************
	 * 			CRUD de Habitacion Vivienda
	 *****************************************************************/
    
    
    /* ****************************************************************
	 * 			CRUD de Horario
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Inmueble
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de OfreceServicio
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Operador
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Persona Juridica
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Persona Natural
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Reserva
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Servicio Menaje
	 *****************************************************************/
    
    /* ****************************************************************
	 * 			CRUD de Usuario
	 *****************************************************************/
    
    public void adicionarUsuario( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Adicionar nombre del usuario", JOptionPane.QUESTION_MESSAGE);
    		String email = JOptionPane.showInputDialog (this, "Email?", "Adicionar email del usuario", JOptionPane.QUESTION_MESSAGE);
    		String telefono = JOptionPane.showInputDialog (this, "Telefono?", "Adicionar telefono de usuario", JOptionPane.QUESTION_MESSAGE);
    		String[] choices = {Usuario.TIPO_ESTUDIANTE, Usuario.TIPO_PROFESOR, Usuario.TIPO_PROFESORINVITADO, Usuario.TIPO_EMPLEADO,
    				Usuario.TIPO_EGRESADO, Usuario.TIPO_PADRAESTUDIANTE, Usuario.TIPO_INVITADO};
    		String tipo = (String) JOptionPane.showInputDialog(null, "Choose now...","The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,choices, choices[1]);
    	
    		if (nombre!=null && email!=null && telefono!=null && tipo!=null)
    		{
        		Usuario usuario = alohAndes.adicionarUsuario(nombre, email, telefono, tipo);
        		if (usuario == null)
        		{
        			throw new Exception ("No se pudo crear un usuario con nombre: " + nombre);
        		}
        		String resultado = "En adicionarUsuario\n\n";
        		resultado += "Usuario adicionado exitosamente: " + usuario;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /* ****************************************************************
	 * 			CRUD de Vivienda
	 *****************************************************************/
    
 
	
    /* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/

	public void mostrarLogParranderos (){
		mostrarArchivo ("parranderos.log");
	}
	
	public void mostrarLogDatanuecleus (){
		mostrarArchivo ("datanucleus.log");
	}
	
	public void limpiarLogParranderos (){
		boolean resp = limpiarArchivo ("parranderos.log");
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	public void limpiarLogDatanucleus (){
		boolean resp = limpiarArchivo ("datanucleus.log");
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";
		panelDatos.actualizarInterfaz(resultado);
	}

	public void limpiarBD (){
		try {
			long eliminados [] = alohAndes.limpiarAlohAndes();
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Apartamentos eliminados\n";
			resultado += eliminados [1] + " Habitaciones eliminados\n";
			resultado += eliminados [2] + " Hab. Hostal eliminados\n";
			resultado += eliminados [3] + " Hab. Hotel eliminados\n";
			resultado += eliminados [4] + " Hab. Vivienda eliminados\n";
			resultado += eliminados [5] + " Horario eliminados\n";
			resultado += eliminados [6] + " Inmueble eliminados\n";
			resultado += eliminados [7] + " Ofrece Servicio eliminados\n";
			resultado += eliminados [8] + " Operador eliminados\n";
			resultado += eliminados [9] + " Per. Juridica eliminados\n";
			resultado += eliminados [10] + " Per. Natural eliminados\n";
			resultado += eliminados [11] + " Reserva eliminados\n";
			resultado += eliminados [12] + " Servicio/Menaje eliminados\n";
			resultado += eliminados [13] + " Usuario eliminados\n";
			resultado += eliminados [14] + " Vivienda eliminados\n";
			resultado += "\nLimpieza terminada";
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void mostrarPresentacionGeneral (){
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	public void mostrarModeloConceptual (){
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}

	public void mostrarEsquemaBD (){
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}

	public void mostrarScriptBD (){
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}

	public void mostrarArqRef (){
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	public void mostrarJavadoc (){
		mostrarArchivo ("doc/index.html");
	}
    /* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/

    private String listarApartamento(List<Apartamento> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHabitacion(List<Habitacion> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHabitacionhostal(List<HabitacionHostal> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHbiatacionHotel(List<HabitacionHotel> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHabitacionVivienda(List<HabitacionVivienda> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHorario(List<Horario> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarInmueble(List<Inmueble> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarOfreceServicio(List<OfreceServicio> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    private String listarOperador(List<Operador> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarPersonaJuridica(List<PersonaJuridica> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarPersonaNatural(List<PersonaNatural> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarReserva(List<Reserva> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarServicioMenaje(List<ServicioMenaje> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarUsuario(List<Usuario> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarVivenda(List<Vivienda> lista) {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (Object tb : lista){
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	private String generarMensajeError(Exception e){
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	private boolean limpiarArchivo(String nombreArchivo) {
		BufferedWriter bw;
		try{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) {
			return false;
		}
	}

	private void mostrarArchivo (String nombreArchivo){
		try{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	
	public void actionPerformed(ActionEvent e) {
		String evento = e.getActionCommand( );		
        try 
        {
			Method req = InterfazAlohAndesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception exception) 
        {
			exception.printStackTrace();
		} 
	}
	
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	
	public static void main( String[] args ){
        try{
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazAlohAndesApp interfaz = new InterfazAlohAndesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e ){
            e.printStackTrace( );
        }
    }
	

}
