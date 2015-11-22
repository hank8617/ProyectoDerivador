package controllers;

import java.util.List;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import database.SitioDeInteresDAO;
import models.SitioDeInteres;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

@Restrict({@Group(Application.ADMIN_ROLE)})
public class ControllerSitiosDeInteres extends Controller{
	
	public static Result listarSitiosDeInteres(){
		
		SitioDeInteresDAO sitioDAO = new SitioDeInteresDAO();
		
		List<SitioDeInteres> sitios = sitioDAO.listarSitiosDeInteres();
		
		return ok(views.html.sitiosDeInteres.render(sitios));
		
	}	
	
	public static Result eliminarSitioDeInteres(Long idSitio){
		SitioDeInteresDAO sitioDAO = new SitioDeInteresDAO();
		
		final SitioDeInteres sitio = sitioDAO.consultarSitioDeInteresPorId(idSitio);
		if(sitio == null) {
			return notFound(String.format("El sitio de interes %s no existe.", idSitio));
		}
		sitioDAO.eliminarSitioDeInteres(sitio);
		return redirect(routes.ControllerSitiosDeInteres.listarSitiosDeInteres());
	}
	
	public static Result details(Long idSitioDeInteres) {
		SitioDeInteresDAO sitioDAO = new SitioDeInteresDAO();
		final SitioDeInteres sitio = sitioDAO.consultarSitioDeInteresPorId(idSitioDeInteres);
		if (sitio == null) {
			return notFound(String.format("El sitio de interes %s no existe.", idSitioDeInteres));
		}
		
		FormularioSitioDeInteres form = new FormularioSitioDeInteres();		
		form.id = sitio.id;
		form.nombre = sitio.nombre;
		form.descripcion = sitio.descripcion;
		form.latitud = sitio.latitud;
		form.longitud = sitio.longitud;
		
		return ok(detalleSitioDeInteres.render(form,false));
	}
	
	public static Result guardar(boolean nuevoSitioDeInteres){
		Form<FormularioSitioDeInteres> boundForm = Form.form(FormularioSitioDeInteres.class).bindFromRequest();
		
		if(boundForm.hasErrors()) {
			flash("error", "Por favor corregir el formulario.");
			//return badRequest(detalleSitioDeInteres.render(Form.form(FormularioSitioDeInteres.class),nuevoSitioDeInteres));
			return redirect(routes.ControllerSitiosDeInteres.listarSitiosDeInteres());
		}
		
		FormularioSitioDeInteres formulario = boundForm.get();		
		SitioDeInteresDAO sitioDAO = new SitioDeInteresDAO();
		
		SitioDeInteres sitioA = new SitioDeInteres();
		sitioA.id = formulario.id;
		sitioA.nombre = formulario.nombre;
		sitioA.descripcion = formulario.descripcion;
		sitioA.latitud = formulario.latitud;
		sitioA.longitud = formulario.longitud;
		
		SitioDeInteres sitioBD = null;
		if(sitioA.id!=null){
			sitioBD = sitioDAO.consultarSitioDeInteresPorId(sitioA.id);
		}
		
		if(sitioBD!=null){
			sitioDAO.actualizarSitioDeInteres(sitioA);
			flash("success",
					String.format("El sitio '%s' ha sido actualizado.", sitioA.nombre));
		}else{
			sitioDAO.agregarSitioDeInteres(sitioA);
			flash("success",
					String.format("El sitio '%s' ha sido añadido.", sitioA.nombre));
		}		
		
		return redirect(routes.ControllerSitiosDeInteres.listarSitiosDeInteres());
	}	
	
	public static Result agregarSitioDeInteres(){
		FormularioSitioDeInteres form = new FormularioSitioDeInteres();
		return ok(detalleSitioDeInteres.render(form,true));
	}
	
	public static class FormularioSitioDeInteres {		
		public Long id;		
		@Required public double latitud;
		@Required public double longitud;
		@Required public String nombre;
		@Required public String descripcion;
    }
	
	
	
}




