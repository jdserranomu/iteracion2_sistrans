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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import com.sun.org.apache.xpath.internal.operations.And;

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
import uniandes.isis2304.parranderos.negocio.ReqConsulta1;
import uniandes.isis2304.parranderos.negocio.ReqConsulta12Inmueble;
import uniandes.isis2304.parranderos.negocio.ReqConsulta12Operador;
import uniandes.isis2304.parranderos.negocio.ReqConsulta2;
import uniandes.isis2304.parranderos.negocio.ReqConsulta3;
import uniandes.isis2304.parranderos.negocio.ReqConsulta4;
import uniandes.isis2304.parranderos.negocio.ReqConsulta5;
import uniandes.isis2304.parranderos.negocio.ReqConsulta6;
import uniandes.isis2304.parranderos.negocio.ReqConsulta7;
import uniandes.isis2304.parranderos.negocio.ReqFun9;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.ServicioMenaje;
import uniandes.isis2304.parranderos.negocio.Usuario;
import uniandes.isis2304.parranderos.negocio.Vivienda;

@SuppressWarnings("serial")
public class InterfazAlohAndesApp extends JFrame implements ActionListener {

	private static Logger log = Logger.getLogger(InterfazAlohAndesApp.class.getName());

	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigAppAlohAndes.json";

	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json";

	private JsonObject tableConfig;

	private AlohAndes alohAndes;

	private JsonObject guiConfig;

	private PanelDatos panelDatos;

	private JMenuBar menuBar;

	public InterfazAlohAndesApp() {
		guiConfig = openConfig("Interfaz", CONFIG_INTERFAZ);
		configurarFrame();
		if (guiConfig != null) {
			crearMenu(guiConfig.getAsJsonArray("menuBar"));
		}

		tableConfig = openConfig("Tablas BD", CONFIG_TABLAS);
		alohAndes = new AlohAndes(tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos();

		setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon(path)), BorderLayout.NORTH);
		add(panelDatos, BorderLayout.CENTER);
	}

	/*
	 * **************************************************************** Métodos de
	 * configuración de la interfaz
	 *****************************************************************/

	private JsonObject openConfig(String tipo, String archConfig) {
		JsonObject config = null;
		try {
			Gson gson = new Gson();
			FileReader file = new FileReader(archConfig);
			JsonReader reader = new JsonReader(file);
			config = gson.fromJson(reader, JsonObject.class);
			log.info("Se encontró un archivo de configuración válido: " + tipo);
		} catch (Exception e) {
			log.info("NO se encontró un archivo de configuración válido");
			JOptionPane.showMessageDialog(null,
					"No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App",
					JOptionPane.ERROR_MESSAGE);
		}
		return config;
	}

	private void configurarFrame() {
		int alto = 0;
		int ancho = 0;
		String titulo = "";
		if (guiConfig == null) {
			log.info("Se aplica configuración por defecto");
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		} else {
			log.info("Se aplica configuración indicada en el archivo de configuración");
			titulo = guiConfig.get("title").getAsString();
			alto = guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);
		setResizable(true);
		setBackground(Color.WHITE);
		setTitle(titulo);
		setSize(ancho, alto);
	}

	private void crearMenu(JsonArray jsonMenu) {
		menuBar = new JMenuBar();
		for (JsonElement men : jsonMenu) {
			JsonObject jom = men.getAsJsonObject();
			String menuTitle = jom.get("menuTitle").getAsString();
			JsonArray opciones = jom.getAsJsonArray("options");
			JMenu menu = new JMenu(menuTitle);
			for (JsonElement op : opciones) {
				JsonObject jo = op.getAsJsonObject();
				String lb = jo.get("label").getAsString();
				String event = jo.get("event").getAsString();
				JMenuItem mItem = new JMenuItem(lb);
				mItem.addActionListener(this);
				mItem.setActionCommand(event);
				menu.add(mItem);
			}
			menuBar.add(menu);
		}
		setJMenuBar(menuBar);
	}

	/*
	 * **************************************************************** CRUD de
	 * Apartamento
	 *****************************************************************/

	public void adicionarApartamento() {
		// int amoblado, double precioMes, long idPersona, String direccion, int
		// capacidad, int disponible, Date fechaReservaFinal
		try {
			int amoblado = Integer.parseInt(JOptionPane.showInputDialog(this, "Amoblado?", "Adicionar apartamento",
					JOptionPane.QUESTION_MESSAGE));
			double precioMes = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio mes?",
					"Adicionar apartamento", JOptionPane.QUESTION_MESSAGE));
			long idPersona = Long.parseLong(JOptionPane.showInputDialog(this, "Id Dueño?", "Adicionar apartamento",
					JOptionPane.QUESTION_MESSAGE));
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar apartamento",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar apartamento",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar apartamento",
					JOptionPane.QUESTION_MESSAGE));
			Apartamento apto = alohAndes.adicionarApartamento(amoblado, precioMes, idPersona, direccion, capacidad,
					disponible, null);
			if (apto == null) {
				throw new Exception("No se pudo crear apartamento");
			} else {
				String resultado = "En adicionarApartamento\n\n";
				resultado += "Apartamento adicionado exitosamente: " + apto;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darApartamentos() {
		try {
			List<Apartamento> lista = alohAndes.darApartamentos();

			String resultado = "En darApartamentos";
			resultado += "\n" + listarApartamento(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darApartamentoPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar apartamento por Id",
					JOptionPane.QUESTION_MESSAGE));
			Apartamento apto = alohAndes.darApartamentoPorId(id);
			String resultado = "En darApartamentoPorId\n\n";
			if (apto != null) {
				resultado += "El apartamento es: " + apto;
			} else {
				resultado += "El apto con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darApartamentosPorIdPersona() {
		try {
			int idPersona = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?",
					"Buscar apartamento por Id Persona", JOptionPane.QUESTION_MESSAGE));
			List<Apartamento> lista = alohAndes.darApartamentosPorIdPersona(idPersona);

			String resultado = "En darApartamentosPorIdPersona";
			resultado += "\n" + listarApartamento(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Habitacion
	 *****************************************************************/

	public void adicionarHabitacion() {
		// double tamanho, double precioMes, long idPersona, String direccion, int
		// capacidad, int disponible, Date fechaReservaFinal
		try {
			double tamanho = Double.parseDouble(
					JOptionPane.showInputDialog(this, "Tamaño?", "Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			double precioMes = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio mes?",
					"Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			long idPersona = Long.parseLong(JOptionPane.showInputDialog(this, "Id Dueño?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			Habitacion habitacion = alohAndes.adicionarHabitacion(tamanho, precioMes, idPersona, direccion, capacidad,
					disponible, null);
			if (habitacion == null) {
				throw new Exception("No se pudo crear habitacion");
			} else {
				String resultado = "En adicionarHabitacion\n\n";
				resultado += "Habitacion adicionada exitosamente: " + habitacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darHabitaciones() {
		try {
			List<Habitacion> lista = alohAndes.darHabitaciones();

			String resultado = "En darHabitaciones";
			resultado += "\n" + listarHabitacion(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar habitacion por Id", JOptionPane.QUESTION_MESSAGE));
			Habitacion habitacion = alohAndes.darHabitacionPorId(id);
			String resultado = "En darHabitacionPorId\n\n";
			if (habitacion != null) {
				resultado += "La habitacion es: " + habitacion;
			} else {
				resultado += "La habitacion con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionesPorIdPersona() {
		try {
			int idPersona = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?",
					"Buscar habitacion por Id Persona", JOptionPane.QUESTION_MESSAGE));
			List<Habitacion> lista = alohAndes.darHabitacionesPorIdPersona(idPersona);

			String resultado = "En darHabitacionesPorIdPerson";
			resultado += "\n" + listarHabitacion(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Habitacion Hostal
	 *****************************************************************/

	public void adicionarHabitacionHostal() {
		// int numero, long idHostal, String direccion, int capacidad, int disponible,
		// Date fechaReservaFinal
		try {
			int numero = Integer.parseInt(JOptionPane.showInputDialog(this, "Numero?", "Adicionar habitacion hostal",
					JOptionPane.QUESTION_MESSAGE));
			long idHostal = Long.parseLong(JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionHostal habitacion = alohAndes.adicionarHabitacionHostal(numero, idHostal, direccion, capacidad,
					disponible, null);
			if (habitacion == null) {
				throw new Exception("No se pudo crear habitacion");
			} else {
				String resultado = "En adicionarHabitacionHostal\n\n";
				resultado += "Habitacion Hostal adicionada exitosamente: " + habitacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darHabitacionesHostales() {
		try {
			List<HabitacionHostal> lista = alohAndes.darHabitacionesHostales();

			String resultado = "En darHabitacionesHostales";
			resultado += "\n" + listarHabitacionHostal(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionHostalPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar habitacion hostal por Id",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionHostal habitacion = alohAndes.darHabitacionHostalPorId(id);
			String resultado = "En darHabitacionHostalPorId\n\n";
			if (habitacion != null) {
				resultado += "La habitacion hostal es: " + habitacion;
			} else {
				resultado += "La habitacion hostal con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionesHostal() {
		try {
			int idHostal = Integer.parseInt(JOptionPane.showInputDialog(this, "Id Hostal?",
					"Buscar habitacion por Id Persona", JOptionPane.QUESTION_MESSAGE));
			List<HabitacionHostal> lista = alohAndes.darHabitacionesHostal(idHostal);

			String resultado = "En darHabitacionesHostal";
			resultado += "\n" + listarHabitacionHostal(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Habitacion Hotel
	 *****************************************************************/
	public void adicionarHabitacionHotel() {
		// long idHotel, int numero, String tipo, double precioNoche, double tamanho,
		// String ubicacion, String direccion, int capacidad, int disponible, Date
		// fechaReservaFinal
		try {
			int numero = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Numero?", "Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			long idHotel = Long.parseLong(JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			String[] choices = { HabitacionHotel.TIPO_ESTANDAR, HabitacionHotel.TIPO_SEMISUITE,
					HabitacionHotel.TIPO_SUITE };
			String tipo = (String) JOptionPane.showInputDialog(null, "Elegir tipo habitacion", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[2]);
			double precioNoche = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio noche?",
					"Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			double tamanho = Double.parseDouble(
					JOptionPane.showInputDialog(this, "Tamaño?", "Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicacion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionHotel habitacion = alohAndes.adicionarHabitacionHotel(idHotel, numero, tipo, precioNoche, tamanho,
					ubicacion, direccion, capacidad, disponible, null);
			if (habitacion == null) {
				throw new Exception("No se pudo crear habitacion");
			} else {
				String resultado = "En adicionarHabitacionHotel\n\n";
				resultado += "Habitacion Hotel adicionada exitosamente: " + habitacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darHabitacionesHoteles() {
		try {
			List<HabitacionHotel> lista = alohAndes.darHabitacionesHoteles();

			String resultado = "En darHabitacionesHoteles";
			resultado += "\n" + listarHabitacionHotel(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionHotelPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar habitacion hotel por Id",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionHotel habitacion = alohAndes.darHabitacionHotelPorId(id);
			String resultado = "En darHabitacionHostelPorId\n\n";
			if (habitacion != null) {
				resultado += "La habitacion hotel es: " + habitacion;
			} else {
				resultado += "La habitacion hotel con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionesHotel() {
		try {
			int idHotel = Integer.parseInt(JOptionPane.showInputDialog(this, "Id Hotel?",
					"Buscar habitacion por Id Hotel", JOptionPane.QUESTION_MESSAGE));
			List<HabitacionHotel> lista = alohAndes.darHabitacionesHotel(idHotel);

			String resultado = "En darHabitacionesHotel";
			resultado += "\n" + listarHabitacionHotel(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Habitacion Vivienda
	 *****************************************************************/

	public void adicionarHabitacionVivienda() {
		// long idVivienda, int numero, double precioSemestre, double precioMes, double
		// precioNoche, String ubicacion, int individual, String direccion, int
		// capacidad, int disponible, Date fechaReservaFinal
		try {
			int numero = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Numero?", "Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			long idVivienda = Long.parseLong(JOptionPane.showInputDialog(this, "Id Vivienda?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			double precioNoche = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio noche?",
					"Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			double precioMes = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio mes?",
					"Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			double precioSemestre = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio semestre?",
					"Adicionar habitacion", JOptionPane.QUESTION_MESSAGE));
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicacion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			int individual = Integer.parseInt(JOptionPane.showInputDialog(this, "Individual?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar habitacion",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionVivienda habitacion = alohAndes.adicionarHabitacionVivienda(idVivienda, numero, precioSemestre,
					precioMes, precioNoche, ubicacion, individual, direccion, capacidad, disponible, null);
			if (habitacion == null) {
				throw new Exception("No se pudo crear habitacion vivienda");
			} else {
				String resultado = "En adicionarHabitacionVivienda\n\n";
				resultado += "Habitacion Vivienda adicionada exitosamente: " + habitacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darHabitacionesViviendas() {
		try {
			List<HabitacionVivienda> lista = alohAndes.darHabitacionesViviendas();

			String resultado = "En darHabitacionesViviendas";
			resultado += "\n" + listarHabitacionVivienda(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionViviendaPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar habitacion vivienda por Id",
					JOptionPane.QUESTION_MESSAGE));
			HabitacionVivienda habitacion = alohAndes.darHabitacionViviendaPorId(id);
			String resultado = "En darHabitacionViviendaPorId\n\n";
			if (habitacion != null) {
				resultado += "La habitacion vivienda es: " + habitacion;
			} else {
				resultado += "La habitacion vivienda con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHabitacionesVivienda() {
		try {
			int idVivienda = Integer.parseInt(JOptionPane.showInputDialog(this, "Id Vivienda?",
					"Buscar habitacion por Id Vivienda", JOptionPane.QUESTION_MESSAGE));
			List<HabitacionVivienda> lista = alohAndes.darHabitacionesVivienda(idVivienda);

			String resultado = "En darHabitacionesVivienda";
			resultado += "\n" + listarHabitacionVivienda(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Horario
	 *****************************************************************/

	public void adicionarHorario() {
		try {
			// long idHostal, String dia, int horaAbre, int horaCierra
			int horaAbre = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Hora abre?", "Adicionar horario", JOptionPane.QUESTION_MESSAGE));
			int horaCierra = Integer.parseInt(JOptionPane.showInputDialog(this, "Hora cierra?", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE));
			long idHostal = Long.parseLong(
					JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar horario", JOptionPane.QUESTION_MESSAGE));
			String[] choices = { Horario.DIA_LUNES, Horario.DIA_MARTES, Horario.DIA_MIERCOLES, Horario.DIA_JUEVES,
					Horario.DIA_VIERNES, Horario.DIA_SABADO, Horario.DIA_DOMINGO };
			String dia = (String) JOptionPane.showInputDialog(null, "Elegir dia", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			Horario horario = alohAndes.adicionarHorario(idHostal, dia, horaAbre, horaCierra);
			if (horario == null) {
				throw new Exception("No se pudo crear horario");
			} else {
				String resultado = "En adicionarHorario\n\n";
				resultado += "Horario adicionado exitosamente: " + horario;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void eliminarHorarioPorIdHostalYDia() {
		try {
			// long idHostal, String dia
			long idHostal = Long.parseLong(
					JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar horario", JOptionPane.QUESTION_MESSAGE));
			String[] choices = { Horario.DIA_LUNES, Horario.DIA_MARTES, Horario.DIA_MIERCOLES, Horario.DIA_JUEVES,
					Horario.DIA_VIERNES, Horario.DIA_SABADO, Horario.DIA_DOMINGO };
			String dia = (String) JOptionPane.showInputDialog(null, "Elegir dia", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			long tbEliminados = alohAndes.eliminarHorarioPorIdHostalYDia(idHostal, dia);
			String resultado = "En eliminarHorarioPorIdHostalYDia\n\n";
			resultado += tbEliminados + " Horarios eliminados\n";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHorarioPorIdHostalYDia() {
		try {
			// long idHostal, String dia
			long idHostal = Long.parseLong(
					JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar horario", JOptionPane.QUESTION_MESSAGE));
			String[] choices = { Horario.DIA_LUNES, Horario.DIA_MARTES, Horario.DIA_MIERCOLES, Horario.DIA_JUEVES,
					Horario.DIA_VIERNES, Horario.DIA_SABADO, Horario.DIA_DOMINGO };
			String dia = (String) JOptionPane.showInputDialog(null, "Elegir dia", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			Horario horario = alohAndes.darHorarioPorIdHostalYDia(idHostal, dia);
			String resultado = "En buscar horario por id hostal y dia\n\n";
			if (horario != null) {
				resultado += "El horario es: " + horario;
			} else {
				resultado += "Un horario: " + dia + " " + idHostal + " NO EXISTE\n";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHorarios() {
		try {
			List<Horario> lista = alohAndes.darHorarios();

			String resultado = "En darHorario";
			resultado += "\n" + listarHorario(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darHorariosPorIdHostal() {
		try {
			long idHostal = Long.parseLong(
					JOptionPane.showInputDialog(this, "Id Hostal?", "Adicionar horario", JOptionPane.QUESTION_MESSAGE));
			List<Horario> lista = alohAndes.darHorariosPorIdHostal((int) idHostal);
			String resultado = "En darHorario";
			resultado += "\n" + listarHorario(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Inmueble
	 *****************************************************************/

	public void eliminarInmueblePorId() {
		try {
			// long idInmueble
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Id Inmueble?", "Borrar Inmueble por Id",
					JOptionPane.QUESTION_MESSAGE));
			long tbEliminados = alohAndes.eliminarInmueblePorId(idInmueble);

			String resultado = "En eliminar Inmueble\n\n";
			resultado += tbEliminados + " Inmuebles eliminados\n";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darInmueblePorId() {
		try {
			int idInmueble = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar inmueble por id", JOptionPane.QUESTION_MESSAGE));
			Inmueble inmueble = alohAndes.darInmueblePorId(idInmueble);
			String resultado = "En darHabitacionViviendaPorId\n\n";
			if (inmueble != null) {
				resultado += "El inmueble es: " + inmueble;
			} else {
				resultado += "El inmueble con id :" + idInmueble + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darInmuebles() {
		try {
			List<Inmueble> lista = alohAndes.darInmuebles();
			String resultado = "En darInmuebles";
			resultado += "\n" + listarInmueble(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darInmueblesPorMayorCapacidad() {
		try {
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?",
					"Listar inmubles por capacidad mayor", JOptionPane.QUESTION_MESSAGE));
			List<Inmueble> lista = alohAndes.darInmueblesPorMayorCapacidad(capacidad);
			String resultado = "En darInmueblesPorMayorCapacidad";
			resultado += "\n" + listarInmueble(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darInmueblesPorTipo() {
		try {
			String[] choices = { Inmueble.TIPO_APARTAMENTO, Inmueble.TIPO_HABITACION, Inmueble.TIPO_HABITACIONHOSTAL,
					Inmueble.TIPO_HABITACIONHOTEL, Inmueble.TIPO_HABITACIONVIVIENDA, Inmueble.TIPO_VIVIENDA };
			String tipo = (String) JOptionPane.showInputDialog(null, "Elegir tipo", "Listar inmueble por tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			List<Inmueble> lista = alohAndes.darInmueblesPorTipo(tipo);
			String resultado = "En darInmueblesPorMayorCapacidad";
			resultado += "\n" + listarInmueble(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darInmueblesPorDisponibilidad() {
		try {
			int disponibilidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponibilidad?",
					"Listar inmuebles por disponibilidad", JOptionPane.QUESTION_MESSAGE));
			List<Inmueble> lista = alohAndes.darInmueblesPorDisponibilidad(disponibilidad);
			String resultado = "En darInmueblesPorMayorCapacidad";
			resultado += "\n" + listarInmueble(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void deshabilitarOfertaAlojamiento() {
		try {
			int idInmueble = Integer.parseInt(JOptionPane.showInputDialog(this, "id inmueble?",
					JOptionPane.QUESTION_MESSAGE));
			List<ReqFun9> lista = alohAndes.deshabilitarOfertaAlojamiento(idInmueble);
			String resultado = "En deshabilitar Alojamiento";
			resultado += "\n" + listarReqFun9(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	

	public void habilitarOfertaInmueblePorId() {
		try {
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Id Inmueble?", "Inmueble para habilitar",
					JOptionPane.QUESTION_MESSAGE));
			long resp = alohAndes.habilitadrOfertaDeInmueble(idInmueble);
			String resultado = "En habilitarOfertaInmueble: " + idInmueble;
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void retirarOfertaInmueblePorId() {
		try {
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Id Inmueble?", "Inmueble para retirar",
					JOptionPane.QUESTION_MESSAGE));
			long resp = alohAndes.retirarOfertaInmueblePorId(idInmueble);
			String resultado = "En retirarOfertaInmueble: " + idInmueble;
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * OfreceServicio
	 *****************************************************************/

	public void adicionarOfreceServicio() {
		// String idServicioMenaje, long idInmueble, double costo, int cantidad
		try {

			String idServicioMenaje = JOptionPane.showInputDialog(this, "Nombre servicio?", "Adicionar ofrece servicio",
					JOptionPane.QUESTION_MESSAGE);
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Id inmueble?",
					"Adicionar ofrece servicio", JOptionPane.QUESTION_MESSAGE));
			String costoString = JOptionPane.showInputDialog(this, "Costo?", "Adicionar ofrece servicio",
					JOptionPane.QUESTION_MESSAGE);
			String cantidadString = JOptionPane.showInputDialog(this, "Cantidad?", "Adicionar ofrece servicio",
					JOptionPane.QUESTION_MESSAGE);
			Double costo;
			Integer cantidad;
			if (costoString.isEmpty())
				costo = null;
			else {
				costo = Double.parseDouble(costoString);
			}
			if (cantidadString.isEmpty())
				cantidad = null;
			else {
				cantidad = Integer.parseInt(cantidadString);
			}
			OfreceServicio ofreceServicio = alohAndes.adicionarOfreceServicio(idServicioMenaje, idInmueble, costo,
					cantidad);
			if (ofreceServicio == null) {
				throw new Exception("No se pudo crear Ofrece Servicio");
			} else {
				String resultado = "En adicionarOfreceServicio\n\n";
				resultado += "OfreceServicio adicionado exitosamente: " + ofreceServicio;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void eliminarOfreceServicio() {
		// String idServicioMenaje, long idInmueble
		try {
			String idServicioMenaje = JOptionPane.showInputDialog(this, "Nombre servicio?", "Adicionar ofrece servicio",
					JOptionPane.QUESTION_MESSAGE);
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Id inmueble?",
					"Adicionar ofrece servicio", JOptionPane.QUESTION_MESSAGE));
			long tbEliminados = alohAndes.eliminarOfreceServicio(idServicioMenaje, idInmueble);
			String resultado = "En eliminar Ofrece Servicio\n\n";
			resultado += tbEliminados + " Ofrece Servicio eliminados\n";
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darOfreceServicio() {
		// String idServicioMenaje, long idInmueble
		try {
			String idServicioMenaje = JOptionPane.showInputDialog(this, "Hora abre?", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE);
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Hora cierra?", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE));
			OfreceServicio ofrece = alohAndes.darOfreceServicio(idServicioMenaje, idInmueble);
			String resultado = "En darOfreceServicio\n\n";
			if (ofrece != null) {
				resultado += "El ofrece servicio es: " + ofrece;
			} else {
				resultado += "El ofrece con id :" + idInmueble + " " + idServicioMenaje + " no existe";
			}
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darOfrecenServicios() {
		try {
			List<OfreceServicio> lista = alohAndes.darOfrecenServicios();
			String resultado = "En darOfrecenServicios";
			resultado += "\n" + listarOfreceServicio(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darOfreceServicios() {
		// long idInmueble
		try {
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "Hora cierra?", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE));
			List<OfreceServicio> lista = alohAndes.darOfreceServicios(idInmueble);
			String resultado = "En darOfreceServicios";
			resultado += "\n" + listarOfreceServicio(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darOfrecenServicio() {
		// String idServicioMenaje
		try {
			String idServicioMenaje = JOptionPane.showInputDialog(this, "Hora abre?", "Adicionar horario",
					JOptionPane.QUESTION_MESSAGE);
			List<OfreceServicio> lista = alohAndes.darOfrecenServicio(idServicioMenaje);
			String resultado = "En darOfrecenServicio";
			resultado += "\n" + listarOfreceServicio(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Operador
	 *****************************************************************/
	public void eliminarOperadorPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar id Operador", JOptionPane.QUESTION_MESSAGE));
			long us = alohAndes.eliminarOperadorPorId(id);
			String resultado = "En eliminar operador por Id\n\n";
			if (us != -1) {
				resultado += "fue eliminada el operador con id: " + us;
			} else {
				resultado += "el operador con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darOperadores() {
		try {
			List<Operador> lista = alohAndes.darOperadores();

			String resultado = "En listaOperadores";
			resultado += "\n" + listarOperador(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darOperadorPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar operador por Id", JOptionPane.QUESTION_MESSAGE));
			Operador us = alohAndes.darOperadorPorId(id);
			String resultado = "En buscar operador por Id\n\n";
			if (us != null) {
				resultado += "La persona juridica es: " + us;
			} else {
				resultado += "el operador con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	/*
	 * **************************************************************** CRUD de
	 * Persona Juridica
	 *****************************************************************/
	public void adicionarPersonaJuridica() {
		try {
			String categoriaString = JOptionPane.showInputDialog(this, "categoria?", "Adicionar categoria",
					JOptionPane.QUESTION_MESSAGE);
			String precioNocheString = JOptionPane.showInputDialog(this, "precio noche?", "Adicionar precio noche",
					JOptionPane.QUESTION_MESSAGE);
			long idCamaraComercio = Long.parseLong(JOptionPane.showInputDialog(this, "id camara?",
					"Adicionar id del camara de comercio", JOptionPane.QUESTION_MESSAGE));
			long idSuperIntendenciaTurismo = Long.parseLong(JOptionPane.showInputDialog(this, "superintendencia?",
					"Adicionar id superintendencia", JOptionPane.QUESTION_MESSAGE));
			String[] choices = { PersonaJuridica.TIPO_HOSTAL, PersonaJuridica.TIPO_HOTEL,
					PersonaJuridica.TIPO_VIVIENDAUNIVERSITARIA };
			String tipo = (String) JOptionPane.showInputDialog(null, "Elegir tipo", "Adicionar tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			String nombre = JOptionPane.showInputDialog(this, "nombre?", "Adicionar nombre",
					JOptionPane.QUESTION_MESSAGE);
			String email = JOptionPane.showInputDialog(this, "email?", "Adicionar email", JOptionPane.QUESTION_MESSAGE);
			String telefono = JOptionPane.showInputDialog(this, "telefono?", "Adicionar telefono",
					JOptionPane.QUESTION_MESSAGE);

			if (tipo != null && nombre != null && email != null && telefono != null) {
				Integer categoria = 0;
				Double precioNoche = 0.0;
				if (categoriaString.isEmpty())
					categoria = null;
				else {
					categoria = Integer.parseInt(categoriaString);
				}
				if (precioNocheString.isEmpty())
					precioNoche = null;
				else {
					precioNoche = Double.parseDouble(precioNocheString);
				}
				PersonaJuridica re = alohAndes.adicionarPersonaJuridica(idSuperIntendenciaTurismo, idCamaraComercio,
						categoria, precioNoche, tipo, nombre, email, telefono);
				if (re == null) {
					throw new Exception("No se pudo crear la persona juridica con nombre: " + nombre);
				}
				String resultado = "En adicionarPersonaNatural\n\n";
				resultado += "Reserva adicionado exitosamente: " + re;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darPersonasJuridicas() {
		try {
			List<PersonaJuridica> lista = alohAndes.darPersonasJuridicas();

			String resultado = "En listaPersonaJuridica";
			resultado += "\n" + listarPersonaJuridica(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darPersonaJuridicaPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar persona juridica por Id",
					JOptionPane.QUESTION_MESSAGE));
			PersonaJuridica us = alohAndes.darPersonaJuridicaPorId(id);
			String resultado = "En buscar persona juridica por Id\n\n";
			if (us != null) {
				resultado += "La persona juridica es: " + us;
			} else {
				resultado += "la persona juridica con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darPersonaJuridicaPorIdSuperIntendenciaTurismo() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id superintendencia?",
					"Buscar persona juridica por Id superintendencia", JOptionPane.QUESTION_MESSAGE));
			PersonaJuridica us = alohAndes.darPersonaJuridicaPorIdSuperIntendenciaTurismo(id);
			String resultado = "En buscar persona juridica por Id superintendencia\n\n";
			if (us != null) {
				resultado += "La persona juridica es: " + us;
			} else {
				resultado += "la persona juridica con id superintendencia :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darPersonaJuridicaPorIdCamaraComercio() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id camara?",
					"Buscar persona juridica por Id camara", JOptionPane.QUESTION_MESSAGE));
			PersonaJuridica us = alohAndes.darPersonaJuridicaPorIdCamaraComercio(id);
			String resultado = "En buscar persona juridica por Id camara\n\n";
			if (us != null) {
				resultado += "La persona juridica es: " + us;
			} else {
				resultado += "la persona juridica con id camara :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darPersonaJuridicaPorTipo() {

		try {

			String[] choices = { PersonaJuridica.TIPO_HOSTAL, PersonaJuridica.TIPO_HOTEL };
			String tipo = (String) JOptionPane.showInputDialog(null, "Selecciones el tipo", "Selecciones el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
			List<PersonaJuridica> lista = alohAndes.darPersonaJuridicaPorTipo(tipo);

			String resultado = "En listaPersonaJuridica";
			resultado += "\n" + listarPersonaJuridica(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Persona Natural
	 *****************************************************************/
	public void adicionarPersonaNatural() {
		try {
			String[] choices = { PersonaNatural.TIPO_MEMBROCOMUNIDAD, PersonaNatural.TIPO_VECINO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Elegir tipo", "Adicionar tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			String nombre = JOptionPane.showInputDialog(this, "nombre?", "Adicionar nombre",
					JOptionPane.QUESTION_MESSAGE);
			String email = JOptionPane.showInputDialog(this, "email?", "Adicionar email", JOptionPane.QUESTION_MESSAGE);
			String telefono = JOptionPane.showInputDialog(this, "telefono?", "Adicionar telefono",
					JOptionPane.QUESTION_MESSAGE);

			if (tipo != null && nombre != null && email != null && telefono != null) {
				PersonaNatural re = alohAndes.adicionarPersonaNatural(tipo, nombre, email, telefono);
				if (re == null) {
					throw new Exception("No se pudo crear la persona natural con nombre: " + nombre);
				}
				String resultado = "En adicionarPersonaNatural\n\n";
				resultado += "Reserva adicionado exitosamente: " + re;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darPersonasNaturales() {
		try {
			List<PersonaNatural> lista = alohAndes.darPersonasNaturales();

			String resultado = "En listaUsuarios";
			resultado += "\n" + listarPersonaNatural(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darPersonaNaturalPorId() {
		try {
			int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar persona natural por Id",
					JOptionPane.QUESTION_MESSAGE));
			PersonaNatural us = alohAndes.darPersonaNaturalPorId(id);
			String resultado = "En buscar persona natural por Id\n\n";
			if (us != null) {
				resultado += "La persona natural es: " + us;
			} else {
				resultado += "la persona natural con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darPersonaNaturalPorTipo() {

		try {

			String[] choices = { PersonaNatural.TIPO_MEMBROCOMUNIDAD, PersonaNatural.TIPO_VECINO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Selecciones el tipo", "Selecciones el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
			List<PersonaNatural> lista = alohAndes.darPersonaNaturalPorTipo(tipo);

			String resultado = "En listaPersonaNatural";
			resultado += "\n" + listarPersonaNatural(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Reserva
	 *****************************************************************/
	
	public void adicionarReservaColectiva() {
		try {
			int serv = Integer.parseInt(JOptionPane.showInputDialog(this, "Por cuantos servicios quiere buscar?",
					"Por cuantos servicios quiere buscar?", JOptionPane.QUESTION_MESSAGE));
			List<String> servicios = new ArrayList<String>();
			for (int i = 0; i < serv; i++) {
				String servi = JOptionPane.showInputDialog(this, "ingrese el servicio?", "ingrese el servicio?",
						JOptionPane.QUESTION_MESSAGE);
				boolean repetido = repetido(servicios, servi);
				if (repetido) {
					JOptionPane.showMessageDialog(this, "No repita servicio vuelva a intentar");
					i--;
				} else {
					servicios.add(servi);
				}

			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = dateFormat.parse(fechaIni);

			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = dateFormat.parse(fechafi);
			if(fechaFin!=null && fechaInicio!=null) {
				long idUsuario = Long.parseLong(
						JOptionPane.showInputDialog(this, "id?", "Adicionar id del usuario", JOptionPane.QUESTION_MESSAGE));
				int cantidad = Integer.parseInt(
						JOptionPane.showInputDialog(this, "cantidad?", "Adicionar cantidad inmuebles", JOptionPane.QUESTION_MESSAGE));
				int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "capacidad?", "Adicionar capacidad",
						JOptionPane.QUESTION_MESSAGE));
				String[] choices = { Inmueble.TIPO_APARTAMENTO, Inmueble.TIPO_HABITACION, Inmueble.TIPO_HABITACIONHOSTAL, Inmueble.TIPO_HABITACIONHOTEL, Inmueble.TIPO_HABITACIONVIVIENDA,
						Inmueble.TIPO_VIVIENDA};
				String tipo = (String) JOptionPane.showInputDialog(null, "Choose now...", "The Choice of a Lifetime",
						JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
				List<Reserva> reservas = alohAndes.adicionarReservaColectiva(fechaInicio, fechaFin, servicios, tipo, cantidad, capacidad, idUsuario);
				String resultado = "En adicionarReserva\n\n";
				resultado += "Reserva adicionado exitosamente: ";
				resultado += "\n" + listarReserva(reservas);
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
			
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	
	@SuppressWarnings("unused")
	public void adicionarReserva() {
		try {
			SimpleDateFormat formatDates = new SimpleDateFormat("yyyy-MM-dd");
			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = formatDates.parse(fechaIni);
			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = formatDates.parse(fechafi);

			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "capacidad?", "Adicionar capacidad",
					JOptionPane.QUESTION_MESSAGE));
			long idUsuario = Long.parseLong(
					JOptionPane.showInputDialog(this, "id?", "Adicionar id del usuario", JOptionPane.QUESTION_MESSAGE));

			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "inmueble?", "Adicionar id del inmueble",
					JOptionPane.QUESTION_MESSAGE));
		
			
	
		
			if (fechaIni != null && fechafi != null) {
				Reserva re = alohAndes.adicionarReserva(fechaInicio,fechaFin, capacidad, idUsuario,idInmueble);

				if (re == null) {
					throw new Exception("No se pudo crear un reserva con fecha de inicio: " + fechaIni
							+ " y fecha fin : " + fechafi);
				}

				String resultado = "En adicionarReserva\n\n";
				resultado += "Reserva adicionado exitosamente: " + re;
				resultado += "\n Operación terminada";

				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void cancelarReservaPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar reserva por Id", JOptionPane.QUESTION_MESSAGE));
			Long num = alohAndes.cancelarReservaPorId(id);
			String resultado = "En cancelar Reserva por Id\n\n";
			if (num != -1) {
				resultado += "fue cancelada la reserva con id: " + num;
			} else {
				resultado += "La reserva con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);

		}
	}
	
	public void cancelarReservaColectivaPorId() {
		try {
			long idReservaColectiva = Long.parseLong(
					JOptionPane.showInputDialog(this, "Id Reserva Colectiva?", "Buscar reserva por Id", JOptionPane.QUESTION_MESSAGE));
			List<Long> resp = alohAndes.cancelarReservaColectivaPorId(idReservaColectiva);
			String resultado = "En cancelar Reserva Colectiva por Id\n\n";
			resultado += "fue cancelada la reserva colectiva con id: " + idReservaColectiva;
			resultado += " y tamaño: " + resp.size();
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);

		}
	}
	
	

	public void eliminarReservaPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar reserva por Id", JOptionPane.QUESTION_MESSAGE));
			long us = alohAndes.eliminarReservaporId(id);
			String resultado = "En buscar Reserva por Id\n\n";
			if (us != -1) {
				resultado += "fue eliminada la reserva con id: " + us;
			} else {
				resultado += "La reserva con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darReservaPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar reserva por Id", JOptionPane.QUESTION_MESSAGE));
			Reserva us = alohAndes.darReservaPorId(id);
			String resultado = "En buscar reserva por Id\n\n";
			if (us != null) {
				resultado += "La reserva es: " + us;
			} else {
				resultado += "la reserva con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darReservasPorIdUsuario() {
		try {
			int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar reservas por Id usuario",
					JOptionPane.QUESTION_MESSAGE));
			List<Reserva> lista = alohAndes.darReservasporIdUsuario(idUsuario);
			String resultado = "En listaReserva";
			resultado += "\n" + listarReserva(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darReservas() {
		try {
			List<Reserva> lista = alohAndes.darReservas();

			String resultado = "En listaReserva";
			resultado += "\n" + listarReserva(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darReservasEnFechasParaInmueble() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = dateFormat.parse(fechaIni);

			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = dateFormat.parse(fechafi);
			long idInmueble = Long.parseLong(JOptionPane.showInputDialog(this, "inmueble?", "Adicionar id del inmueble",
					JOptionPane.QUESTION_MESSAGE));
			List<Reserva> lista = alohAndes.darReservasEnFechasParaInmueble(fechaInicio, fechaFin, idInmueble);
			String resultado = "En listaReserva";
			resultado += "\n" + listarReserva(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Servicio Menaje
	 *****************************************************************/
	public void adicionarServicioMenaje() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre?", "Adicionar nombre del servicio/menaje",
					JOptionPane.QUESTION_MESSAGE);

			String[] choices = { ServicioMenaje.TIPO_MENAJE, ServicioMenaje.TIPO_SERVICIO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Selecciones el tipo", "Seleccione el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

			if (nombre != null && tipo != null) {
				ServicioMenaje sm = alohAndes.adicionarServicioMenaje(nombre, tipo);
				if (sm == null) {
					throw new Exception("No se pudo crear un servicio/menaje con nombre: " + nombre);
				}
				String resultado = "En adicionarServicioMenaje\n\n";
				resultado += "Servicio Menaje adicionado exitosamente: " + sm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void eliminarServicioMenajePorNombre() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "nombre?", "Buscar servicio/menaje por nombre",
					JOptionPane.QUESTION_MESSAGE);
			long us = alohAndes.eliminarServicioMenajePorNombre(nombre);
			String resultado = "En buscar servicio/menaje por Id\n\n";
			if (us != -1) {
				resultado += "fue eliminado el servicio/menaje con nombre: " + us;
			} else {
				resultado += "El serivicio/menaje con nombre :" + nombre + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darServicioMenajePorNombre() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Id?", "Buscar servicio menaje por nombre",
					JOptionPane.QUESTION_MESSAGE);
			ServicioMenaje us = alohAndes.darServicioMenajePorNombre(nombre);
			String resultado = "En buscar Servicio/Menaje por nombre\n\n";
			if (us != null) {
				resultado += "El Servicio/menaje es: " + us;
			} else {
				resultado += "El servicio/menaje con nombre :" + nombre + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darServiciosMenajesPorTipo() {
		try {
			String[] choices = { ServicioMenaje.TIPO_MENAJE, ServicioMenaje.TIPO_SERVICIO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo", "Selecciones el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

			List<ServicioMenaje> lista = alohAndes.darServiciosMenajesPorTipo(tipo);

			String resultado = "En listaServicioMenaje";
			resultado += "\n" + listarServicioMenaje(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darServiciosMenajes() {
		try {
			List<ServicioMenaje> lista = alohAndes.darServiciosMenajes();

			String resultado = "En listaServicioMenaje";
			resultado += "\n" + listarServicioMenaje(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Usuario
	 *****************************************************************/

	public void adicionarUsuario() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre?", "Adicionar nombre del usuario",
					JOptionPane.QUESTION_MESSAGE);
			String email = JOptionPane.showInputDialog(this, "Email?", "Adicionar email del usuario",
					JOptionPane.QUESTION_MESSAGE);
			String telefono = JOptionPane.showInputDialog(this, "Telefono?", "Adicionar telefono de usuario",
					JOptionPane.QUESTION_MESSAGE);
			String[] choices = { Usuario.TIPO_ESTUDIANTE, Usuario.TIPO_PROFESOR, Usuario.TIPO_PROFESORINVITADO,
					Usuario.TIPO_EMPLEADO, Usuario.TIPO_EGRESADO, Usuario.TIPO_PADRAESTUDIANTE, Usuario.TIPO_INVITADO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Choose now...", "The Choice of a Lifetime",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

			if (nombre != null && email != null && telefono != null && tipo != null) {
				Usuario usuario = alohAndes.adicionarUsuario(nombre, email, telefono, tipo);
				if (usuario == null) {
					throw new Exception("No se pudo crear un usuario con nombre: " + nombre);
				}
				String resultado = "En adicionarUsuario\n\n";
				resultado += "Usuario adicionado exitosamente: " + usuario;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darUsuarios() {
		try {
			List<Usuario> lista = alohAndes.darUsuarios();

			String resultado = "En listaUsuarios";
			resultado += "\n" + listarUsuario(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void eliminarUsuarioPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar usuarios por Id", JOptionPane.QUESTION_MESSAGE));
			long us = alohAndes.eliminarUsuario(id);
			String resultado = "En buscar Usuario por Id\n\n";
			if (us != -1) {
				resultado += "fue eliminado cel usuario con id: " + us;
			} else {
				resultado += "El usuario con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void darUsuarioPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar usuarios por Id", JOptionPane.QUESTION_MESSAGE));
			Usuario us = alohAndes.darUsuarioPorId(id);
			String resultado = "En buscar Usuario por Id\n\n";
			if (us != null) {
				resultado += "El usuario es es: " + us;
			} else {
				resultado += "El usuario con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darUsuarioPorEmail() {
		try {
			String mail = JOptionPane.showInputDialog(this, "Email?", "Buscar usuario por email",
					JOptionPane.QUESTION_MESSAGE);
			Usuario bus = alohAndes.darUsuarioPorEmail(mail);

			String resultado = "En buscar Usuario por mail\n\n";
			if (bus != null) {
				resultado += "El usuario es es: " + bus;
			} else {
				resultado += "El usuario con email :" + mail + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darUsuariosPorTipo() {

		try {

			String[] choices = { Usuario.TIPO_EGRESADO, Usuario.TIPO_EMPLEADO, Usuario.TIPO_ESTUDIANTE,
					Usuario.TIPO_INVITADO, Usuario.TIPO_PADRAESTUDIANTE, Usuario.TIPO_PROFESOR,
					Usuario.TIPO_PROFESORINVITADO };
			String tipo = (String) JOptionPane.showInputDialog(null, "Selecciones el tipo", "Selecciones el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
			List<Usuario> lista = alohAndes.darUsuariosPorTipo(tipo);

			String resultado = "En listaUsuarios";
			resultado += "\n" + listarUsuario(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** CRUD de
	 * Vivienda
	 *****************************************************************/
	public void adicionarVivienda() {
		try {
			int numeroHabitaciones = Integer.parseInt(JOptionPane.showInputDialog(this, "Numero Habitaciones?",
					"Adicionar Numero de Habitaciones", JOptionPane.QUESTION_MESSAGE));
			double costoNoche = Double.parseDouble(JOptionPane.showInputDialog(this, "Costo Noche?",
					"Adicionar Costo por noche", JOptionPane.QUESTION_MESSAGE));
			long idPersona = Long.parseLong(JOptionPane.showInputDialog(this, "id del dueño?", "Adicionar id del dueño",
					JOptionPane.QUESTION_MESSAGE));
			String direccion = JOptionPane.showInputDialog(this, "Direccion?", "Adicionar direccion",
					JOptionPane.QUESTION_MESSAGE);
			int capacidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad?", "Adicionar capacidad",
					JOptionPane.QUESTION_MESSAGE));
			int disponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Disponible?", "Adicionar Disponible",
					JOptionPane.QUESTION_MESSAGE));

			Vivienda vivienda = alohAndes.adicionarVivienda(numeroHabitaciones, costoNoche, 0, idPersona, direccion,
					capacidad, disponible, null);
			if (vivienda == null) {
				throw new Exception("No se pudo crear la vivienda");
			} else {
				String resultado = "En adicionarVivienda\n\n";
				resultado += "Vivienda adicionada exitosamente: " + vivienda;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darViviendas() {
		try {
			List<Vivienda> lista = alohAndes.darViviendas();

			String resultado = "En listaViviendas";
			resultado += "\n" + listarVivenda(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darViviendaPorId() {
		try {
			int id = Integer.parseInt(
					JOptionPane.showInputDialog(this, "Id?", "Buscar vivienda por Id", JOptionPane.QUESTION_MESSAGE));
			Vivienda viv = alohAndes.darViviendaPorId(id);
			String resultado = "En buscar Vivienda por Id\n\n";
			if (viv != null) {
				resultado += "La vivienda es: " + viv;
			} else {
				resultado += "La vivienda con id :" + id + " no existe";
			}
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void darViviendaPorIdPersona() {
		try {
			int idPersona = Integer.parseInt(JOptionPane.showInputDialog(this, "Id?", "Buscar vivienda por Id Persona",
					JOptionPane.QUESTION_MESSAGE));
			List<Vivienda> lista = alohAndes.darViviendaPorIdPersona(idPersona);

			String resultado = "En listarVivienda";
			resultado += "\n" + listarVivenda(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** Métodos
	 * administrativos
	 *****************************************************************/

	public void mostrarLogAlohAndes() {
		mostrarArchivo("alohAndes.log");
	}

	public void mostrarLogDatanuecleus() {
		mostrarArchivo("datanucleus.log");
	}

	public void limpiarLogAlohAndes() {
		boolean resp = limpiarArchivo("alohAndes.log");
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	public void limpiarLogDatanucleus() {
		boolean resp = limpiarArchivo("datanucleus.log");
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";
		panelDatos.actualizarInterfaz(resultado);
	}

	public void limpiarBD() {
		try {
			long eliminados[] = alohAndes.limpiarAlohAndes();
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados[0] + " Apartamentos eliminados\n";
			resultado += eliminados[1] + " Habitaciones eliminados\n";
			resultado += eliminados[2] + " Hab. Hostal eliminados\n";
			resultado += eliminados[3] + " Hab. Hotel eliminados\n";
			resultado += eliminados[4] + " Hab. Vivienda eliminados\n";
			resultado += eliminados[5] + " Horario eliminados\n";
			resultado += eliminados[6] + " Inmueble eliminados\n";
			resultado += eliminados[7] + " Ofrece Servicio eliminados\n";
			resultado += eliminados[8] + " Operador eliminados\n";
			resultado += eliminados[9] + " Per. Juridica eliminados\n";
			resultado += eliminados[10] + " Per. Natural eliminados\n";
			resultado += eliminados[11] + " Reserva eliminados\n";
			resultado += eliminados[12] + " Servicio/Menaje eliminados\n";
			resultado += eliminados[13] + " Usuario eliminados\n";
			resultado += eliminados[14] + " Vivienda eliminados\n";
			resultado += "\nLimpieza terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * public void mostrarPresentacionGeneral (){ mostrarArchivo
	 * ("data/00-ST-ParranderosJDO.pdf"); }
	 * 
	 * public void mostrarModeloConceptual (){ mostrarArchivo
	 * ("data/Modelo Conceptual Parranderos.pdf"); }
	 * 
	 * public void mostrarEsquemaBD (){ mostrarArchivo
	 * ("data/Esquema BD Parranderos.pdf"); }
	 * 
	 * public void mostrarScriptBD (){ mostrarArchivo
	 * ("data/EsquemaParranderos.sql"); }
	 * 
	 * public void mostrarArqRef (){ mostrarArchivo
	 * ("data/ArquitecturaReferencia.pdf"); }
	 * 
	 * public void mostrarJavadoc (){ mostrarArchivo ("doc/index.html"); }
	 */
	/*
	 * **************************************************************** Métodos
	 * privados para la presentación de resultados y otras operaciones
	 *****************************************************************/

	private String listarApartamento(List<Apartamento> lista) {
		String resp = "Los Apartamentos existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarUsuarios(List<Usuario> lista) {
		String resp = "Los usuarios existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarHabitacion(List<Habitacion> lista) {
		String resp = "Las Habitaciones existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarHabitacionHostal(List<HabitacionHostal> lista) {
		String resp = "Las Habitaciones de Hostal existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarHabitacionHotel(List<HabitacionHotel> lista) {
		String resp = "Las Habitaciones de Hotel existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarHabitacionVivienda(List<HabitacionVivienda> lista) {
		String resp = "Los Habitaciones de Vivienda existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarHorario(List<Horario> lista) {
		String resp = "Los Horarios existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarInmueble(List<Inmueble> lista) {
		String resp = "Los Inmuebles existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarReqFun9(List<ReqFun9> lista) {
		String resp="Deshabilitando...";
		if (lista.size()==0) {
			resp+=" no hay reservas para relocalizar";
		}
		for (int i=0;i<lista.size();i++) {
			resp=resp+ "La reserva con id: "+ lista.get(i).getaCambiar().getId();
			Reserva nueva= lista.get(i).getNueva();
			if (nueva==null) {
				resp=resp+" No se pudo cambiar \n";
			}else {
				resp=resp+" se cambio por la reserva con id: "+ lista.get(i).getNueva().getId();
				resp+=" en el inmueble:"+ nueva.getIdInmueble()+"\n";
			}
			
		}
		return resp;
	}

	private String listarOfreceServicio(List<OfreceServicio> lista) {
		String resp = "Los Servicios Ofrecidos existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarOperador(List<Operador> lista) {
		String resp = "Los Operadores existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarPersonaJuridica(List<PersonaJuridica> lista) {
		String resp = "Las Personas Juridicas existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarPersonaNatural(List<PersonaNatural> lista) {
		String resp = "Las Personas Naturales existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarReserva(List<Reserva> lista) {
		String resp = "Las reservas existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarServicioMenaje(List<ServicioMenaje> lista) {
		String resp = "Los Servicios/Menajes existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarUsuario(List<Usuario> lista) {
		String resp = "Los usuarios existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarVivenda(List<Vivienda> lista) {
		String resp = "Las vivendas existentes son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarRFC1(List<ReqConsulta1> lista) {
		String resp = "Los datos del RFC1 son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarRFC2(List<ReqConsulta2> lista) {
		String resp = "Los datos del RFC2 son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	private String listarRFC3(List<ReqConsulta3> lista) {
		String resp = "Los datos del RFC3 son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarRFC7(List<ReqConsulta7> mayorDemanda, List<ReqConsulta7> mayorDineros, List<ReqConsulta7> menorOcupacion, String tipo) {
		if (mayorDemanda.size()==0) {
			return "No ha habido reservas para "+ tipo;
		}
		
		String resp = "Los datos del RFC7 para "+tipo+" son:\n";
		
		resp+="Meses de mayor demanda: \n";
		for (int i=0;i<mayorDemanda.size();i++) {
			resp+= "Mes: "+ mayorDemanda.get(i).getMeses()+ " con: "+ mayorDemanda.get(i).getCuantos()+ " alojamientos ocupados "; 
		}
		resp+="\n Meses de mayores ingresos: \n";
		for (int i=0;i<mayorDineros.size();i++) {
			resp+= "Mes: "+ mayorDineros.get(i).getMeses()+ " con: "+ mayorDineros.get(i).getCuantos()+ " pesos ganados "; 
		}
		
		resp+="\n Meses de menor ocupacion: \n";
		for (int i=0;i<menorOcupacion.size();i++) {
			resp+= "Mes: "+ menorOcupacion.get(i).getMeses()+ " con: "+ menorOcupacion.get(i).getCuantos()+ " alojamientos ocupados "; 
		}
		return resp;
	}

	private String listarRFC4(List<ReqConsulta4> lista) {
		String resp = "Los datos del RFC4 son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarRFC5(List<ReqConsulta5> lista) {
		String resp = "Los datos del RFC5 son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarRFC12Inmueble(List<ReqConsulta12Inmueble> lista) {
		String resp = "Los datos del RFC12 inmueble son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarRFC12Operador(List<ReqConsulta12Operador> lista) {
		String resp = "Los datos del RFC12 operador son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	
	private String listarRFC13Operador(List<Long> lista) {
		String resp = "Los datos del RFC13 operador son:\n";
		int i = 1;
		for (Object tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	

	private boolean repetido(List<String> listaServicios, String servicio) {
		boolean repetido = false;
		for (int i = 0; i < listaServicios.size() && !repetido; i++) {
			repetido = listaServicios.get(i).equals(servicio);
		}
		return repetido;
	}

	private String darDetalleException(Exception e) {
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException")) {
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions()[0].getMessage();
		}
		return resp;
	}

	private String generarMensajeError(Exception e) {
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	private boolean limpiarArchivo(String nombreArchivo) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(nombreArchivo)));
			bw.write("");
			bw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void mostrarArchivo(String nombreArchivo) {
		try {
			Desktop.getDesktop().open(new File(nombreArchivo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cerrarUnidadPersistencia() {
		alohAndes.cerrarUnidadPersistencia();
	}

	/*
	 * **************************************************************** Métodos de
	 * requerimientos de consulta
	 *****************************************************************/

	public void RFC1() {

		try {
			List<ReqConsulta1> listaConsulta = alohAndes.RFC1();
			String resultado = "En RFC1";
			resultado += "\n" + listarRFC1(listaConsulta);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC2() {

		try {
			List<ReqConsulta2> listaConsulta = alohAndes.RFC2();
			String resultado = "En RFC2";
			resultado += "\n" + listarRFC2(listaConsulta);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC3() {

		try {
			List<ReqConsulta3> listaConsulta = alohAndes.RFC3();
			String resultado = "En RFC3";
			resultado += "\n" + listarRFC3(listaConsulta);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC4() {

		try {
			int serv = Integer.parseInt(JOptionPane.showInputDialog(this, "Por cuantos servicios quiere buscar?",
					"Por cuantos servicios quiere buscar?", JOptionPane.QUESTION_MESSAGE));
			List<String> servicios = new ArrayList<String>();
			for (int i = 0; i < serv; i++) {
				String servi = JOptionPane.showInputDialog(this, "ingrese el servicio?", "ingrese el servicio?",
						JOptionPane.QUESTION_MESSAGE);
				boolean repetido = repetido(servicios, servi);
				if (repetido) {
					JOptionPane.showMessageDialog(this, "No repita servicio vuelva a intentar");
					i--;
				} else {
					servicios.add(servi);
				}

			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = dateFormat.parse(fechaIni);

			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = dateFormat.parse(fechafi);

			List<ReqConsulta4> listaConsulta = alohAndes.RFC4(fechaInicio, fechaFin, servicios);
			String resultado = "En RFC4";
			resultado += "\n" + listarRFC4(listaConsulta);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void RFC5() {

		try {
			List<ReqConsulta5> listaConsulta = alohAndes.RFC5();
			String resultado = "En RFC5";
			resultado += "\n" + listarRFC5(listaConsulta);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	
	public void RFC6() {

		try {
			long idUsuario = Long.parseLong( JOptionPane.showInputDialog(this, 
					"ID usuario?","Mostrar uso de AlohAndes para usuario", JOptionPane.QUESTION_MESSAGE));
			ReqConsulta6 listaConsulta = alohAndes.RFC6(idUsuario);
			String resultado = "En RFC6";
			resultado += "\n" + listaConsulta.toString();
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	
	public void RFC7() {

		try {
			String[] choices = { Inmueble.TIPO_APARTAMENTO,Inmueble.TIPO_HABITACION, Inmueble.TIPO_HABITACIONHOSTAL
					, Inmueble.TIPO_HABITACIONHOTEL, Inmueble.TIPO_HABITACIONVIVIENDA, Inmueble.TIPO_VIVIENDA};
			String tipo = (String) JOptionPane.showInputDialog(null, "Selecciones el tipo", "Seleccione el tipo",
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
			List<ReqConsulta7> mayorDemanda = alohAndes.mayorDemanda(tipo);
			List<ReqConsulta7> mayorDineros = alohAndes.mayorIngresos(tipo);
			List<ReqConsulta7> menorOcupacion = alohAndes.menorOcupacion(tipo);
			String resultado = "En RFC7";
			resultado += "\n" + listarRFC7(mayorDemanda,mayorDineros, menorOcupacion, tipo);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void RFC8() {

		try {
			
			long idInmueble = Long.parseLong( JOptionPane.showInputDialog(this, 
					"ID inmueble?","Encontrar los clientes frecuentes", JOptionPane.QUESTION_MESSAGE));
			List<Usuario> usuarios = alohAndes.RFC8(idInmueble);
			String resultado = "En RFC8";
			resultado += "\n" + listarUsuario(usuarios);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	public void RFC9() {

		try {
			List<Inmueble> inmuebles = alohAndes.RFC9();
			String resultado = "En RFC9";
			resultado += "\n" + listarInmueble(inmuebles);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		
		
		
	}
	
	public void RFC11() {

		try {
			long idInmueble = Long.parseLong( JOptionPane.showInputDialog(this, 
					"ID inmueble?","Encontrar los clientes frecuentes", JOptionPane.QUESTION_MESSAGE));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = dateFormat.parse(fechaIni);

			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = dateFormat.parse(fechafi);
			long usuario = Long.parseLong(JOptionPane.showInputDialog(this, "id usuario?", "Usuario identificacion",
					JOptionPane.QUESTION_MESSAGE));
			int intOrdId = Integer.parseInt(JOptionPane.showInputDialog(this, "1->Si, 0->No", "Ordenar por id",
					JOptionPane.QUESTION_MESSAGE));
			int intOrdNombre = Integer.parseInt(JOptionPane.showInputDialog(this, "1->Si, 0->No", "Ordenar por nombre",
					JOptionPane.QUESTION_MESSAGE));
			int intOrdEmail = Integer.parseInt(JOptionPane.showInputDialog(this, "1->Si, 0->No", "Ordenar por email",
					JOptionPane.QUESTION_MESSAGE));
			List<Usuario> usuarios = alohAndes.RFC11(idInmueble, fechaInicio, fechaFin, intOrdId==1, intOrdNombre==1, intOrdEmail==1, usuario);
			
			String resultado = "En RFC11";
			resultado += "\n" + listarUsuario(usuarios);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	
	public void RFC10() {

		try {
			List<String>carac=new ArrayList<>();
			JList list = new JList<>(new String[] {"ID", "NOMBRE", "EMAIL", "TIPO"});
			JOptionPane.showMessageDialog(
			  null, list, "seleccion por que parametros quiere ordenar", JOptionPane.PLAIN_MESSAGE);
			
			carac=list.getSelectedValuesList();
			
			long idUsuario = Long.parseLong( JOptionPane.showInputDialog(this, 
					"ID usuario?","id usuario", JOptionPane.QUESTION_MESSAGE));
			long idInmueble = Long.parseLong( JOptionPane.showInputDialog(this, 
					"ID inmueble?","Encontrar los clientes frecuentes", JOptionPane.QUESTION_MESSAGE));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String fechaIni = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha Inicio",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaInicio = dateFormat.parse(fechaIni);

			String fechafi = JOptionPane.showInputDialog(this, "Fecha (YYYY-MM-DD)?", "Adicionar fecha fin",
					JOptionPane.QUESTION_MESSAGE);
			Date fechaFin = dateFormat.parse(fechafi);
			List<Usuario>usuarios= new ArrayList<>();
			String resultado = "En RFC10";
			if (idUsuario==0) {
			resultado+= " para administrador";
				
			usuarios = alohAndes.RFC10(idInmueble, fechaInicio, fechaFin, carac);
			
			
			}else{
			if (alohAndes.darUsuarioPorId(idUsuario)!=null) {
				usuarios=alohAndes.RFC10Usuario(idInmueble, fechaInicio, fechaFin, idUsuario, carac);
			}
				
			}
			resultado = "En RFC10";
			resultado += "\n" + listarUsuario(usuarios);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	
	public void RFC12InmuebleMayor() {

		try {
			List<ReqConsulta12Inmueble> lista = alohAndes.RFC12InmuebleMayor();
			
			String resultado = "En RFC12 Inmueble mayor";
		
			resultado += "\n" + listarRFC12Inmueble(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	
	public void RFC12InmuebleMenor() {

		try {
			List<ReqConsulta12Inmueble> lista = alohAndes.RFC12InmuebleMenor();
			
			String resultado = "En RFC12 Inmueble menor";
			resultado += "\n" + listarRFC12Inmueble(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	public void RFC12OperadorMayor() {

		try {
			List<ReqConsulta12Operador> lista = alohAndes.RFC12OperadorMayor();
			
			String resultado = "En RFC12 Inmueble menor";
			resultado += "\n" + listarRFC12Operador(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	public void RFC12OperadorMenor() {

		try {
			List<ReqConsulta12Operador> lista = alohAndes.RFC12OperadorMenor();
			
			String resultado = "En RFC12 Inmueble menor";
			resultado += "\n" + listarRFC12Operador(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	
	public void RFC13() {

		try {
			List<Long> lista = alohAndes.RFC13();
			
			String resultado = "En RFC13 Inmueble menor";
			resultado += "\n" + listarRFC13Operador(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}	
		
	}
	
	
	
	
	
	

	/*
	 * **************************************************************** Métodos de
	 * la Interacción
	 *****************************************************************/

	public void actionPerformed(ActionEvent e) {
		String evento = e.getActionCommand();
		try {
			Method req = InterfazAlohAndesApp.class.getMethod(evento);
			req.invoke(this);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/*
	 * **************************************************************** Programa
	 * principal
	 *****************************************************************/

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			InterfazAlohAndesApp interfaz = new InterfazAlohAndesApp();
			interfaz.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
