package database;

import java.util.ArrayList;
import java.util.List;

import models.Estacion;
import models.SitioDeAlquiler;

public class EstacionDAO {
	
	public List<Estacion> listarEstaciones(){
		return Estacion.find.all();
	}	
	
	/**
	 * Agrega un sitio de alquiler a la BD 
	 * 
	 * @param sitio El sitio de alquiler
	 */
	public void agregarEstacion(Estacion sitio){
		sitio.save();
	}
	
	public boolean actualizarEstacion(Estacion sitio){
		Estacion sitioActual = consultarEstacionPorId(sitio.id);
		boolean actualizo = false;
		if(sitioActual!=null){
			sitioActual = sitio;
			actualizo = true;
			sitioActual.update();
		}
		return actualizo;
	}
	
	public void eliminarEstacion(Estacion sitio){
		Estacion sitioActual = consultarEstacionPorId(sitio.id);
		sitioActual.delete();
	}
	
	public Estacion consultarEstacionPorId(Long id){
		return Estacion.find.byId(id);
	}
	
	public List<Estacion> consultarEstacionPorNombre(String nombre){
		return Estacion.find.where().eq("nombre", nombre).findList();
	}

	public List<Estacion> consultarEstacionPorSitioAlquiler(SitioDeAlquiler sitio){
		if(sitio!=null){
			return Estacion.find.where().eq("sitioDeAlquiler", sitio).findList();
		}else{
			return new ArrayList<Estacion>();
		}
		
	}
}
