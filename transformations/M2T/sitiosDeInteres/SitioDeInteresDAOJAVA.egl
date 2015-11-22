package database;

import java.util.List;

import models.Recorrido;
import models.SitioDeInteres;
import models.User;

public class SitioDeInteresDAO {
	
	public List<SitioDeInteres> listarSitiosDeInteres(){
		return SitioDeInteres.find.all();
	}	
	
	/**
	 * Agrega un sitio de alquiler a la BD 
	 * 
	 * @param sitio El sitio de alquiler
	 */
	public boolean agregarSitioDeInteres(SitioDeInteres sitio){
		if(sitio!=null){
			sitio.save();
			return true;
		}		
		return false;
	}
	
	public boolean actualizarSitioDeInteres(SitioDeInteres sitio){
		SitioDeInteres sitioActual = consultarSitioDeInteresPorId(sitio.id);
		boolean actualizo = false;
		if(sitioActual!=null){
			sitioActual = sitio;
			actualizo = true;
			sitioActual.update();
		}
		return actualizo;
	}
	
	public void eliminarSitioDeInteres(SitioDeInteres sitio){
		SitioDeInteres sitioActual = consultarSitioDeInteresPorId(sitio.id);
		sitioActual.delete();
	}
	
	public SitioDeInteres consultarSitioDeInteresPorId(Long id){
		return SitioDeInteres.find.byId(id);
	}
	
	public List<SitioDeInteres> consultarSitioDeInteresPorNombre(String nombre){
		return SitioDeInteres.find.where().eq("nombre", nombre).findList();
	}

}
