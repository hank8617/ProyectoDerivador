package database;

import java.util.List;


import models.Metrica;

public class MetricaDAO {
	
	/**
	 * Agregar una métrica al repositorio 
	 * 
	 * @param m La métrica que se desea agregar
	 */
	public void agregarMetrica(Metrica m){
		m.save();
	}
	
	public void actualizarMetrica(Metrica m){
		Metrica metricaActual = consultarMetricaPorId(m.getIdMetrica());
		metricaActual.setNombreMetrica(m.getNombreMetrica());
		metricaActual.setUnidadMedida(m.getUnidadMedida());
		metricaActual.save();
	}
	
	public void eliminarMetrica(Metrica m){
		Metrica metricaActual = consultarMetricaPorId(m.getIdMetrica());
		metricaActual.delete();
	}
	
	public Metrica consultarMetricaPorId(Long id){
		return Metrica.find.byId(id);
	}
	
	public List<Metrica> consultarMetricaPorNombre(String nombre){
		return Metrica.find.where().eq("nombre_metrica", nombre).findList();
	}

}
