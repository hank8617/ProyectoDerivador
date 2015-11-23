package database;

import java.util.List;

import models.Recorrido;
import models.SitioDeAlquiler;
import models.User;

public class SitioDeAlquilerDAO {
	
	public List<SitioDeAlquiler> listarSitiosDeAlquiler(){
		return SitioDeAlquiler.find.all();
	}	
	
	/**
	 * Agrega un sitio de alquiler a la BD 
	 * 
	 * @param sitio El sitio de alquiler
	 */
	public void agregarSitioDeAlquiler(SitioDeAlquiler sitio){
		sitio.save();
	}
	
	public boolean actualizarSitioDeAlquiler(SitioDeAlquiler sitio){
		SitioDeAlquiler sitioActual = consultarSitioDeAlquilerPorId(sitio.id);
		boolean actualizo = false;
		if(sitioActual!=null){
			sitioActual = sitio;
			actualizo = true;
			sitioActual.update();
		}
		return actualizo;
	}
	
	public void eliminarSitioDeAlquiler(SitioDeAlquiler sitio){
		SitioDeAlquiler sitioActual = consultarSitioDeAlquilerPorId(sitio.id);
		sitioActual.delete();
	}
	
	public SitioDeAlquiler consultarSitioDeAlquilerPorId(Long id){

		if(id!=null){
			return SitioDeAlquiler.find.byId(id);
		}else{
			return null;
		}		
	}
	
	public List<SitioDeAlquiler> consultarSitioDeAlquilerPorNombre(String nombre){
		return SitioDeAlquiler.find.where().eq("nombre", nombre).findList();
	}
	
	public List<SitioDeAlquiler> consultarSitiosDeAlquilerPorUsuario(User usuario){
		return SitioDeAlquiler.find.where().eq("usuario", usuario).findList();
	}

}
