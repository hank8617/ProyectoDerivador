package controllers;

import java.util.Date;

import java.util.List;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import database.RecompensaDAO;
import database.RecompensaUsuarioDAO;
import models.Recompensa;
import models.RecompensaUsuario;
import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.*;
import views.html.*;

@Restrict({@Group(Application.USER_ROLE), @Group(Application.ADMIN_ROLE)})
public class ControllerRecompensas extends Controller{
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result listarRecompensasDisponiblesUsuario(){
		
		RecompensaDAO recompensaDAO = new RecompensaDAO();
		User usuario = Application.getLocalUser(session());
		List<Recompensa> recompensas = recompensaDAO.consultarRecompensasDisponiblesUsuario(usuario);
		
		return ok(views.html.recompensasDisponibles.render(recompensas,usuario.puntajeRetos));
		
	}	
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result guardarRecompensaUsuario(Long idRecompensa){
		RecompensaDAO recompensaDAO = new RecompensaDAO();
		RecompensaUsuarioDAO dao = new RecompensaUsuarioDAO();
		User usuario = Application.getLocalUser(session());
		Recompensa recom = recompensaDAO.consultarRecompensaPorId(idRecompensa);
		Date date = new Date();
		
		boolean guardo = false;
		if(recom!=null && usuario.puntajeRetos!=null && usuario.puntajeRetos>=recom.puntajeRequerido){
			RecompensaUsuario ru = new RecompensaUsuario();
			ru.recompensa = recom;
			usuario.puntajeRetos = usuario.puntajeRetos-recom.puntajeRequerido;
			usuario.update();
			ru.usuario = usuario;
			ru.fecha = date;
			guardo = dao.guardarRecompensaUsuario(ru);
		}
		
		if(guardo){

			return redirect(routes.ControllerRecompensas.listarRecompensasUsuario());
		}else{
			flash("error", "La recompensa ya fue reclamada o no tiene los puntos suficientes para reclamar la recompensa.");
			return redirect(routes.ControllerRecompensas.listarRecompensasDisponiblesUsuario());
		}
		
		
	}
	

	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result listarRecompensas(){
		
		RecompensaDAO recompensaDAO = new RecompensaDAO();
		
		List<Recompensa> recompensas = recompensaDAO.consultarRecompensas();
		
		return ok(views.html.recompensas.render(recompensas));
		
	}	
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result eliminarRecompensa(Long idRecompensa){
		RecompensaDAO recompensaDAO = new RecompensaDAO();
		
		final Recompensa recompensa = recompensaDAO.consultarRecompensaPorId(idRecompensa);
		if(recompensa == null) {
			return notFound(String.format("La Recompensa %s no existe.", idRecompensa));
		}
		recompensaDAO.eliminarRecompensa(recompensa);
		return redirect(routes.ControllerRecompensas.listarRecompensas());
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result details(Long idRecompensa) {
		RecompensaDAO recompensaDAO = new RecompensaDAO();		
		final Recompensa recompensa = recompensaDAO.consultarRecompensaPorId(idRecompensa);
		if (recompensa == null) {
			return notFound(String.format("La Recompensa %s no existe.", idRecompensa));
		}
		
		FormularioRecompensa form = new FormularioRecompensa();		
		form.id = recompensa.id;
		form.celularContacto = recompensa.celularContacto;
		form.fechaLimite = recompensa.fechaLimite;
		form.estado = recompensa.estado;
		form.puntajeRequerido = recompensa.puntajeRequerido;
		form.recompensa = recompensa.recompensa;
		form.telefonoContacto = recompensa.telefonoContacto;				
		
		return ok(detalleRecompensa.render(form,false));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result guardar(boolean nuevaRecompensa){
		Form<FormularioRecompensa> boundForm = Form.form(FormularioRecompensa.class).bindFromRequest();		

		if(boundForm.hasErrors()) {
			flash("error", "Por favor corregir el formulario.");
			//return badRequest(detalleRecompensa.render(Form.form(FormularioRecompensa.class),nuevaRecompensa));
			return redirect(routes.ControllerRecompensas.listarRecompensas());
		}
		
		FormularioRecompensa formulario = boundForm.get();		
		RecompensaDAO recompensaDAO = new RecompensaDAO();
		
		Recompensa recompensaA = new Recompensa();
		recompensaA.estado = formulario.estado;
		recompensaA.celularContacto = formulario.celularContacto;
		recompensaA.fechaLimite = formulario.fechaLimite;
		recompensaA.id = formulario.id;
		recompensaA.puntajeRequerido = formulario.puntajeRequerido;
		recompensaA.recompensa = formulario.recompensa;
		recompensaA.telefonoContacto = formulario.telefonoContacto;
		
		Recompensa recompensaBD = null;
		if(recompensaA.id!=null){
			recompensaBD = recompensaDAO.consultarRecompensaPorId(recompensaA.id);
		}
		
		if(recompensaBD!=null){
			recompensaDAO.actualizarRecompensa(recompensaA);
		}else{
			recompensaDAO.agregarRecompensa(recompensaA);
		}		
		flash("success",
		String.format("La recompensa %s ha sido añadida.", recompensaA.recompensa));
		return redirect(routes.ControllerRecompensas.listarRecompensas());
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result agregarRecompensa(){
		FormularioRecompensa form = new FormularioRecompensa();
		return ok(detalleRecompensa.render(form,true));
	}
	
	@Restrict(@Group(Application.USER_ROLE))
	public static Result listarRecompensasUsuario(){
		
		RecompensaUsuarioDAO recomUsuarioDAO = new RecompensaUsuarioDAO();
		
		User usuario = Application.getLocalUser(session());
		List<RecompensaUsuario> recom = recomUsuarioDAO.consultarRecompensasUsuario(usuario);
		
		return ok(views.html.recompensasUsuario.render(recom));
		
	}	
	
	public static class FormularioRecompensa {		
		public Long id;
		@Required public String recompensa;
		@Required public Long puntajeRequerido;		
		@Required public String telefonoContacto;
		@Required public String celularContacto;
		@Required public Date fechaLimite;
		@Required public String estado;	
    }
	
	
	
	
	
}




