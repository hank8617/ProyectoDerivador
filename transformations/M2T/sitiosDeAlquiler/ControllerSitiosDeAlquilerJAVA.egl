package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import database.EstacionDAO;
import database.RetoDAO;
import database.SitioDeAlquilerDAO;
import models.Estacion;
import models.Reto;
import models.SitioDeAlquiler;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

@Restrict({@Group(Application.USER_ROLE), @Group(Application.ADMIN_ROLE)})
public class ControllerSitiosDeAlquiler extends Controller{
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result listarSitiosDeAlquiler(){
		
		SitioDeAlquilerDAO sitioDAO = new SitioDeAlquilerDAO();
		
		List<SitioDeAlquiler> sitios = sitioDAO.consultarSitiosDeAlquilerPorUsuario(Application.getLocalUser(session()));
		
		EstacionDAO estacionDAO = new EstacionDAO();
		
		List<Estacion> estaciones = new ArrayList<Estacion>();
		if(sitios.size()>0){
			estaciones = estacionDAO.consultarEstacionPorSitioAlquiler(sitios.get(0));
		}
				
		return ok(views.html.misSitiosAlquiler.render( sitios,estaciones));
		
	}	
    
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result save(boolean sitioNuevo) {
		Form<FormularioSitioDeAlquiler> boundForm = Form.form(FormularioSitioDeAlquiler.class).bindFromRequest();
		SitioDeAlquilerDAO sitioDAO = new SitioDeAlquilerDAO();
		if(boundForm.hasErrors()) {
			flash("error", "Por favor corregir el formulario.");
			return badRequest(detalleAlquiler.render(boundForm.get(),sitioNuevo));
		}
		
		FormularioSitioDeAlquiler form = boundForm.get(); 
		SitioDeAlquiler sitioA = new SitioDeAlquiler();
		sitioA.celular = form.celular;
		sitioA.email = form.email;
		sitioA.id = form.id;
		sitioA.indicativoTelefonoFijo = form.indicativoTelefonoFijo;
		sitioA.nombre = form.nombre;
		sitioA.tarifa_x_h = form.tarifa_x_h;
		sitioA.telefonoFijo = form.telefonoFijo;		
		
		SitioDeAlquiler sitioBD = sitioDAO.consultarSitioDeAlquilerPorId(sitioA.id);

		if(sitioBD!=null){
			sitioA.usuario = sitioBD.usuario;
			sitioA.estaciones = sitioBD.estaciones;
			sitioDAO.actualizarSitioDeAlquiler(sitioA);
			flash("success",
					String.format("El sitio %s ha sido actualizado.", sitioA));
		}else{
			sitioA.usuario = Application.getLocalUser(session());
			sitioA.save();
			flash("success",
					String.format("El sitio %s ha sido añadido.", sitioA));
		}		
		
		return redirect(routes.ControllerSitiosDeAlquiler.listarSitiosDeAlquiler());
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result guardarEstacion(boolean nuevaEstacion){
		Form<FormularioEstacion> boundForm = Form.form(FormularioEstacion.class).bindFromRequest();
		
		if(boundForm.hasErrors()) {
			flash("error", "Por favor corregir el formulario."+boundForm.errorsAsJson());
			//return badRequest(detalleReto.render(Form.form(FormularioReto.class),nuevoReto));
			return redirect(routes.ControllerSitiosDeAlquiler.listarSitiosDeAlquiler());
		}
		
		FormularioEstacion formulario = boundForm.get();		
		EstacionDAO estacionDAO = new EstacionDAO();
		SitioDeAlquilerDAO saDAO = new SitioDeAlquilerDAO();
		
		Estacion estacion = new Estacion();
		estacion.horaFinAtencion = formulario.horaFinAtencion;
		estacion.horaInicioAtencion = formulario.horaInicioAtencion;
		estacion.id = formulario.id;
		estacion.latitud = formulario.latitud;
		estacion.longitud = formulario.longitud;
		estacion.nombre = formulario.nombre;
		estacion.paraAlquiler = formulario.paraAlquiler;
		estacion.paraEntrega = formulario.paraEntrega;
		estacion.sitioDeAlquiler = saDAO.consultarSitiosDeAlquilerPorUsuario(Application.getLocalUser(session())).get(0);
		
		Estacion estacionBD = null;
		if(estacion.id!=null){
			estacionBD = estacionDAO.consultarEstacionPorId(estacion.id);
		}
		
		if(estacionBD!=null){
			estacionDAO.actualizarEstacion(estacion);
			flash("success",
					String.format("La estacion '%s' ha sido actualizada.", estacion.nombre));
		}else{
			estacionDAO.agregarEstacion(estacion);
			flash("success",
					String.format("La estacion '%s' ha sido añadida.", estacion.nombre));
		}		
		
		return redirect(routes.ControllerSitiosDeAlquiler.listarSitiosDeAlquiler());
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result detailsEstacion(Long idEstacion) {
		EstacionDAO estacionDAO = new EstacionDAO();
		final Estacion sitio = estacionDAO.consultarEstacionPorId(idEstacion);
		if (sitio == null) {
			return notFound(String.format("Estacion %s no existe.", idEstacion));
		}
		
		FormularioEstacion form = new FormularioEstacion();
		
		form.horaFinAtencion = sitio.horaFinAtencion;
		form.horaInicioAtencion = sitio.horaInicioAtencion;
		form.id = sitio.id;
		form.latitud = sitio.latitud;
		form.longitud = sitio.longitud;
		form.nombre = sitio.nombre;
		form.paraAlquiler = sitio.paraAlquiler;
		form.paraEntrega = sitio.paraEntrega;
		
		return ok(detalleEstacion.render(form,false));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result detailsSitioAlquiler(Long idSitio) {
		SitioDeAlquilerDAO sitioDAO = new SitioDeAlquilerDAO();
		final SitioDeAlquiler sitio = sitioDAO.consultarSitioDeAlquilerPorId(idSitio);
		if (sitio == null) {
			return notFound(String.format("Estacion %s no existe.", idSitio));
		}else{
			System.out.println("Sitio encontrado: "+sitio.nombre);
		}
		
		FormularioSitioDeAlquiler form = new FormularioSitioDeAlquiler(); 

		form.email = sitio.email;
		form.id = sitio.id;
		form.indicativoTelefonoFijo = sitio.indicativoTelefonoFijo;
		form.nombre = sitio.nombre;
		form.tarifa_x_h = sitio.tarifa_x_h;
		form.telefonoFijo = sitio.telefonoFijo;		
		form.celular = sitio.celular;		
		
		return ok(detalleAlquiler.render(form,false));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result newSitio() {
		return ok(detalleAlquiler.render(new FormularioSitioDeAlquiler(),true));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result newEstacion() {
		return ok(detalleEstacion.render(new FormularioEstacion(),true));
	}
	
	@Restrict(@Group(Application.ADMIN_ROLE))
	public static Result eliminarEstacion(Long idEstacion){
		EstacionDAO estacionDAO = new EstacionDAO();
		
		final Estacion estacion = estacionDAO.consultarEstacionPorId(idEstacion);
		if(estacion == null) {
			return notFound(String.format("La estacion %s no existe.", idEstacion));
		}
		estacionDAO.eliminarEstacion(estacion);
		return redirect(routes.ControllerSitiosDeAlquiler.listarSitiosDeAlquiler());
	}
	
	public static class FormularioEstacion {		
		public Long id;
		@Required public String nombre;		
		@Required public double latitud;
		@Required public double longitud;
		@Required public String horaInicioAtencion;
		@Required public String horaFinAtencion;
		@Required public boolean paraAlquiler;
		@Required public boolean paraEntrega;
    }
	
	public static class FormularioSitioDeAlquiler {		
		public Long id;
		@Required public String nombre;		
		@Required public String indicativoTelefonoFijo;
		@Required public String telefonoFijo;
		@Required public String email;
		@Required public String celular;
		@Required public String tarifa_x_h;
    }
	
}
