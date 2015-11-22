package controllers;

import static play.libs.Json.toJson;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import database.RecorridoDAO;
import database.UserDAO;
import database.UsuariosXRecorridoDAO;
import models.Recorrido;
import models.Ruta;
import models.User;
import models.UsuarioXRecorrido;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;

@Restrict(@Group(Application.USER_ROLE))
public class ControllerRecorrido extends Controller{

	public static List<String> tipoRecorrido = new ArrayList<String>();
	public static Map<String, Boolean> diaFrecuente = new TreeMap<String, Boolean>();
	public static Map<String, Boolean> horaSalida = new TreeMap<String, Boolean>();
	public static Map<String, Boolean> lstAmigos = new TreeMap<String, Boolean>();

	public static Result postFormRecorridos() {
		
		FormularioRecorrido formRecorrido1 = new FormularioRecorrido();

		Form<FormularioRecorrido> form = Form.form(FormularioRecorrido.class).bindFromRequest();
		if(form.hasErrors()) {
			flash("error", "Se encontraron errores al crear el recorrido.");
			return badRequest(views.html.recorridos.render(Form.form(FormularioRecorrido.class), tipoRecorrido, diaFrecuente, horaSalida, lstAmigos, null, null));

		} else {
			FormularioRecorrido formRecorrido = form.get();

			Recorrido recorrido = new Recorrido();
			recorrido.setTipo(0);
			if (formRecorrido.tipoRecorrido.contains("Recreacion"))
				recorrido.setTipo(1);

			recorrido.setNombre(formRecorrido.nombre);
			recorrido.setDescripcion(formRecorrido.descripcion);
			recorrido.setHoraFrecuente(formRecorrido.horaFrecuente);

			if(formRecorrido.diaFrecuente != null)
			{
				String cadDias = "";
				for (String dia : formRecorrido.diaFrecuente) {

					String solDia = dia.split("-")[1];

					cadDias += solDia + ",";
					System.out.println(solDia);
				}
				recorrido.setDiaFrecuente(cadDias);
			}

			Ruta ruta = new Ruta();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			if(formRecorrido.tipoRecorrido.contains("Recreacion"))
			{
				try {
					Date fechaInicio = format.parse(formRecorrido.fechaInicioRuta);//new Date();
					Date fechaFin = format.parse(formRecorrido.fechaFinRuta);//new Date();

					ruta.setFechaInicioRuta(fechaInicio);
					ruta.setFechaFinRuta(fechaFin);

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			ruta.setLatitudInicio(Float.parseFloat(formRecorrido.latitudInicio));
			ruta.setLongitudInicio(Float.parseFloat(formRecorrido.longitudInicio));
			ruta.setLatitudFin(Float.parseFloat(formRecorrido.latitudFin));
			ruta.setLongitudFin(Float.parseFloat(formRecorrido.longitudFin));

			ruta.setLugarInicio(formRecorrido.lugarInicio);
			ruta.setLugarFin(formRecorrido.lugarFin);

			List<UsuarioXRecorrido> listUsuarioRecorrido = new ArrayList<UsuarioXRecorrido>();

			User usuario = Application.getLocalUser(session()); 
			UsuarioXRecorrido usuarioRecorrido = new UsuarioXRecorrido();
			usuarioRecorrido.setUsuario(usuario);
			usuarioRecorrido.setIndAdministrador(true);
			usuarioRecorrido.setIndConfirmado(true);

			listUsuarioRecorrido.add(usuarioRecorrido);
			UserDAO userDao = new UserDAO();

			for (String amigo : formRecorrido.lstAmigos) {
				usuario = userDao.consultarUsuarioPorId(Long.parseLong(amigo.split("-")[1]));
				usuarioRecorrido = new UsuarioXRecorrido();
				usuarioRecorrido.setUsuario(usuario);
				usuarioRecorrido.setIndAdministrador(false);
				usuarioRecorrido.setIndConfirmado(false);
				listUsuarioRecorrido.add(usuarioRecorrido);
			}

			insertarRecorrido(recorrido, ruta, listUsuarioRecorrido);
			
			flash("success", "Se ha creado correctamente el recorrido.");
			return templateCrear( Form.form(FormularioRecorrido.class), tipoRecorrido, diaFrecuente, horaSalida, lstAmigos);
			}
	}
	
	
private static Result templateCrear(Form<FormularioRecorrido> form, List<String> tipoRecorrido,
			Map<String, Boolean> diaFrecuente, Map<String, Boolean> horaSalida, Map<String, Boolean> lstAmigos) {
	String mensaje = "Acabo de crear un recorrido quieres unirte";
	String url = "http://i844.photobucket.com/albums/ab7/MARTIN3280/recreacion_zpsjiekvk3i.jpg";
	//return ok(views.html.recorridos.render(form, tipoRecorrido, diaFrecuente, horaSalida, lstAmigos, null, null));
		return ok(views.html.recorridos.render(form, tipoRecorrido, diaFrecuente, horaSalida, lstAmigos, null, null));
	}

public static Result getFormRecorridos()
{
	cargarListas();
	response().setContentType("text/html; charset=utf-8");
	return ok(views.html.recorridos.render(Form.form(FormularioRecorrido.class), tipoRecorrido, diaFrecuente, horaSalida, lstAmigos, null, null));
}

	private static void cargarListas()
	{
		tipoRecorrido = new ArrayList<String>();
		tipoRecorrido.add("Frecuente");
		tipoRecorrido.add("Recreacion");

		diaFrecuente = new TreeMap<String, Boolean>();

		diaFrecuente.put("1-Lunes", false);
		diaFrecuente.put("2-Martes", false);
		diaFrecuente.put("3-Miercoles", false);
		diaFrecuente.put("4-Jueves", false);
		diaFrecuente.put("5-Viernes", false);
		diaFrecuente.put("6-Sabado", false);
		diaFrecuente.put("7-Domingo", false);

		horaSalida = new TreeMap<String, Boolean>();
		for (int i = 0; i < 24; i++) {
			horaSalida.put(String.format("%02d", i) + ":00", false);
			horaSalida.put(String.format("%02d", i) + ":30", false);
		}


		UserDAO userDao = new UserDAO();
		List<User> lstUser = userDao.listarUsuarios();

		User usuarioSession = Application.getLocalUser(session()); 
		lstAmigos = new TreeMap<String, Boolean>();
		for (User usuario : lstUser) {
			if(usuarioSession.id != usuario.id)
				lstAmigos.put(usuario.name + "-" + usuario.id, false);
		}
	}

	private static void insertarRecorrido(Recorrido recorrido, Ruta ruta, List<UsuarioXRecorrido> listUsuarioRecorrido)
	{
		RecorridoDAO recorridoDao = new RecorridoDAO();
		recorrido.getLstRuta().add(ruta);
		recorrido.setLstUsuarioXRecorrido(listUsuarioRecorrido);
		recorridoDao.agregarRecorrido(recorrido);
	}

	public static Result listarRecorridos(){

		RecorridoDAO recorridoDAO = new RecorridoDAO();
		List<Recorrido> lstRecorridos = recorridoDAO.listarRecorridos();
		return ok(toJson(lstRecorridos));
	}

	public static Result listarRecorridosWeb(){

		RecorridoDAO recorridoDAO = new RecorridoDAO();
		List<Recorrido> lstRecorridos = recorridoDAO.listarRecorridos();
		response().setContentType("text/html; charset=utf-8");
		return ok(views.html.recorridosConsulta.render(Form.form(FormularioConsultaRecorrido.class), lstRecorridos, null, null));
	}

	public static Result detallesRecorridos(){

		RecorridoDAO recorridoDAO = new RecorridoDAO();
		//ojo ajustar
		List<Recorrido> lstRecorridos = recorridoDAO.listarRecorridos();

		Form<FormularioConsultaRecorrido> form = Form.form(FormularioConsultaRecorrido.class).bindFromRequest();

		if(form.hasErrors()) {
			flash("error", "Se encontraron errores al consultar el recorrido.");
			return badRequest(views.html.recorridosConsulta.render(Form.form(FormularioConsultaRecorrido.class), lstRecorridos, null, null));

		} else {
			FormularioConsultaRecorrido formularioConsultaRecorrido = form.get();

			Recorrido recorrido = recorridoDAO.consultarRecorridoPorId(formularioConsultaRecorrido.idRecorrido);     
			FormularioRecorrido formRecorrido = new FormularioRecorrido();
			formRecorrido.tipoRecorrido = String.valueOf(recorrido.getTipo());
			formRecorrido.nombre = recorrido.getNombre();
			formRecorrido.descripcion = recorrido.getDescripcion();
			formRecorrido.horaFrecuente = recorrido.getHoraFrecuente();

			String diasFrecuentes = recorrido.getDiaFrecuente();
			formRecorrido.diaFrecuente = new ArrayList<String>();
			if(diasFrecuentes != null)
			{
				String[] arrDias = diasFrecuentes.split(",");
				for (int i = 0; i < arrDias.length; i++) {
					formRecorrido.diaFrecuente.add(arrDias[i]);
				}
			}

			formRecorrido.fechaInicioRuta = String.valueOf(recorrido.getLstRuta().get(0).getFechaInicioRuta());
			formRecorrido.fechaFinRuta = String.valueOf(recorrido.getLstRuta().get(0).getFechaFinRuta());
			formRecorrido.latitudInicio = String.valueOf(recorrido.getLstRuta().get(0).getLatitudInicio());
			formRecorrido.longitudInicio = String.valueOf(recorrido.getLstRuta().get(0).getLongitudInicio());
			formRecorrido.latitudFin = String.valueOf(recorrido.getLstRuta().get(0).getLatitudFin());
			formRecorrido.longitudFin = String.valueOf(recorrido.getLstRuta().get(0).getLongitudFin());
			formRecorrido.lugarInicio = recorrido.getLstRuta().get(0).getLugarInicio();
			formRecorrido.lugarFin = recorrido.getLstRuta().get(0).getLugarFin();
			formRecorrido.lstAmigos = new ArrayList<String>();

			Boolean existe = false;
			User usuario = Application.getLocalUser(session());
			for (UsuarioXRecorrido usuarioRecorrido : recorrido.getLstUsuarioXRecorrido()) {
				formRecorrido.lstAmigos.add(usuarioRecorrido.getUsuario().name);
				if(usuario.id == usuarioRecorrido.getUsuario().id)
					existe = true;
			}

			formRecorrido.idRecorrido = recorrido.getIdRecorrido();

			return ok(views.html.recorridosDetalle.render(formRecorrido, existe));

		}

	}

	public static Result inscribirARecorrido(){

		String mensaje = "";
		Form<FormularioConsultaRecorrido> form = Form.form(FormularioConsultaRecorrido.class).bindFromRequest();
		FormularioConsultaRecorrido formularioConsultaRecorrido = form.get();

		RecorridoDAO recorridoDao = new RecorridoDAO();
		Recorrido recorrido = recorridoDao.consultarRecorridoPorId(formularioConsultaRecorrido.idRecorrido);

		User usuarioSession = Application.getLocalUser(session());
		UsuariosXRecorridoDAO usuarioRecorridoDao = new UsuariosXRecorridoDAO();
		List<UsuarioXRecorrido> lstUsuarioRecorrido = usuarioRecorridoDao.consultarUsuarioEnRecorridoPorRecorridoYUsuario(recorrido, usuarioSession);

		if(lstUsuarioRecorrido.size() > 0)
		{
			usuarioRecorridoDao.eliminarUsuarioXRecorrido(lstUsuarioRecorrido.get(0));
			mensaje = "<div style='padding: 5px 5px 5px 5px; background-color:#c4ead0'>Se ha retirado del recorrido satisfactoriamente</div>";
			
		}
		else
		{
			UsuarioXRecorrido usuarioRecorrido = new UsuarioXRecorrido();
			usuarioRecorrido.setRecorrido(recorrido);
			usuarioRecorrido.setUsuario(usuarioSession);
			usuarioRecorrido.setIndAdministrador(false);
			usuarioRecorrido.setIndConfirmado(true);
			usuarioRecorridoDao.agregarUsuarioXRecorrido(usuarioRecorrido);
			mensaje="<div style='padding: 5px 5px 5px 5px; background-color:#c4ead0'>Se ha unido al recorrido satisfactoriamente</div>";
			
		}

		
		return ok(mensaje);
	}

	public static class FormularioRecorrido {
		@Required public String tipoRecorrido;
		@Required public String nombre;
		@Required public String descripcion;
		@Required public String horaFrecuente;
		public List<String> diaFrecuente;
		public List<String> lstAmigos;
		public String fechaInicioRuta;
		public String fechaFinRuta;
		@Required public String latitudInicio;
		@Required public String longitudInicio;
		@Required public String latitudFin;
		@Required public String longitudFin;
		@Required public String lugarInicio;
		@Required public String lugarFin;
		public Long idRecorrido;
	}

	public static class FormularioConsultaRecorrido {
		public Long idRecorrido;
	}
	
	
	
	
	
	
}




