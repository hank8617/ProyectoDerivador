package controllers;

import java.util.Date;

import java.util.List;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import database.MetricaDAO;
import database.RetoDAO;
import database.RetoUsuarioDAO;
import database.UserDAO;
import models.EstadoRetoUsuario;
import models.Metrica;
import models.Reto;
import models.RetoUsuario;
import models.User;
import play.data.Form;
import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.*;
import views.html.*;

@Restrict({@Group(Application.USER_ROLE), @Group(Application.ADMIN_ROLE)})
public class ControllerRetos extends Controller{
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result listarRetosActivos(){
		
		RetoDAO retoDAO = new RetoDAO();
		
		List<Reto> retos = retoDAO.consultarRetosActivos();
		
		return ok(views.html.retosDisponibles.render(retos));
		
	}	
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result listarRetos(){
		
		RetoDAO retoDAO = new RetoDAO();
		
		List<Reto> retos = retoDAO.consultarRetos();
		
		return ok(views.html.retos.render(retos));
		
	}	
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result eliminarReto(Long idReto){
		RetoDAO retoDAO = new RetoDAO();
		
		final Reto reto = retoDAO.consultarRetoPorId(idReto);
		if(reto == null) {
			return notFound(String.format("El Reto %s no existe.", idReto));
		}
		retoDAO.eliminarReto(reto);
		return redirect(routes.ControllerRetos.listarRetos());
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result details(Long idReto) {
		RetoDAO retoDAO = new RetoDAO();
		final Reto reto = retoDAO.consultarRetoPorId(idReto);
		if (reto == null) {
			return notFound(String.format("El Reto %s no existe.", idReto));
		}
		
		FormularioReto form = new FormularioReto();		
		form.estado = reto.estado;
		form.fechaFin = reto.fechaFin;
		form.fechaIni = reto.fechaIni;
		form.funcion = reto.funcion;
		form.id = reto.id;
		form.idMetrica = reto.metrica.getIdMetrica();
		form.nombre = reto.nombre;
		form.operador = reto.operador;
		form.puntaje = reto.puntaje;
		form.valorCondicion = reto.valorCondicion;		
		
		List<Metrica> metricas = Metrica.find.all();
		
		return ok(detalleReto.render(form,false,metricas));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result guardar(boolean nuevoReto){
		Form<FormularioReto> boundForm = Form.form(FormularioReto.class).bindFromRequest();
		
		if(boundForm.hasErrors()) {
			flash("error", "Por favor corregir el formulario.");
			//return badRequest(detalleReto.render(Form.form(FormularioReto.class),nuevoReto));
			return redirect(routes.ControllerRetos.listarRetos());
		}
		
		FormularioReto formulario = boundForm.get();		
		RetoDAO retoDAO = new RetoDAO();
		MetricaDAO metricaDAO = new MetricaDAO();
		Metrica metrica = null;
		if(formulario.idMetrica!=null){
			 metrica = metricaDAO.consultarMetricaPorId(formulario.idMetrica);
		}		
		
		if(metrica==null){
			flash("error", "La métrica especificada no existe.");
			//return badRequest(detalleReto.render(Form.form(FormularioReto.class),nuevoReto));
			return redirect(routes.ControllerRetos.listarRetos());
		}
		
		Reto retoA = new Reto();
		retoA.estado = formulario.estado;
		retoA.fechaFin = formulario.fechaFin;
		retoA.fechaIni = formulario.fechaIni;
		retoA.funcion = formulario.funcion;
		retoA.id = formulario.id;
		retoA.metrica = metrica;
		retoA.nombre = formulario.nombre;
		retoA.operador = formulario.operador;
		retoA.puntaje = formulario.puntaje;
		retoA.valorCondicion = formulario.valorCondicion;
		
		Reto retoBD = null;
		if(retoA.id!=null){
			retoBD = retoDAO.consultarRetoPorId(retoA.id);
		}
		
		if(retoBD!=null){
			retoDAO.actualizarReto(retoA);
			flash("success",
					String.format("El reto '%s' ha sido actualizado.", retoA.nombre));
		}else{
			retoDAO.agregarReto(retoA);
			flash("success",
					String.format("El reto '%s' ha sido añadido.", retoA.nombre));
		}		
		
		return redirect(routes.ControllerRetos.listarRetos());
	}
	
	public static void actualizarRetosUsuario(){
		
		RetoDAO retoDAO = new RetoDAO();
		User usuario = Application.getLocalUser(session());
		List<Reto> retos = retoDAO.consultarRetosDisponiblesUsuario(usuario);
		System.out.println(" ");
		System.out.println("---------------------- RETOS DISPONIBLES ---------------------");
		for(Reto reto:retos){
			System.out.println("Reto: "+reto.nombre);
			if(retoDAO.cumpleReto(reto,usuario)){
				RetoUsuarioDAO retoUsuarioDAO = new RetoUsuarioDAO();
				
				RetoUsuario retoUsuario = new RetoUsuario();
				retoUsuario.estado = EstadoRetoUsuario.VALIDO.getEstado();
				retoUsuario.fecha = new Date();
				retoUsuario.reto = reto;
				retoUsuario.usuario = usuario;
				
				retoUsuarioDAO.agregarRetoUsuario(retoUsuario);
				
				//Actualización del puntaje
				UserDAO userDAO = new UserDAO();
				if(usuario.puntajeRetos==null){
					usuario.puntajeRetos = reto.puntaje;
				}else{
					usuario.puntajeRetos = usuario.puntajeRetos+reto.puntaje;	
				}
				
				userDAO.actualizarUsuario(usuario);
				
				
			}			
		}
		
	}
	

	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result agregarReto(){
		FormularioReto form = new FormularioReto();
		List<Metrica> metricas = Metrica.find.all();
		return ok(detalleReto.render(form,true,metricas));
	}
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result listarRetosUsuario(){
		
		actualizarRetosUsuario();
		
		RetoUsuarioDAO retoUsuarioDAO = new RetoUsuarioDAO();
		
		List<RetoUsuario> retos = retoUsuarioDAO.consultarRetosUsuario(Application.getLocalUser(session()));
		
		return ok(views.html.retosUsuario.render(retos));
		
	}	
	
	public static class FormularioReto {		
		public Long id;
		@Required public String nombre;		
		@Required
		@Formats.DateTime(pattern = "yyyy-MM-dd") 
		public Date fechaIni;		
		@Required 
		@Formats.DateTime(pattern = "yyyy-MM-dd") 
		public Date fechaFin;
		@Required public Long idMetrica;
		@Required public String funcion;
		@Required public String operador;
		@Required public String valorCondicion;
		@Required public Long puntaje;
		@Required public String estado;
    }
	
	
	
}




