package database;

import java.util.List;

import models.MetricasXRecorrido;
import models.Recorrido;


public class RecorridoDAO {
	
	/**
	 * Agregar una recorrido al repositorio 
	 * 
	 * @param r El recorrido que se desea agregar
	 */
	public void agregarRecorrido(Recorrido r){
		r.save();
	}
	
	public List<Recorrido> listarRecorridos(){
		return Recorrido.find.all();
	}
	
	public Recorrido consultarRecorridoPorId(Long id){
		return Recorrido.find.byId(id);
	}
	
	public void actualizarRecorridoConMetricas(Recorrido r){
		System.out.println("Entra a Actualizar recorridos");
		Recorrido recorridoActual = consultarRecorridoPorId(r.getIdRecorrido());
		recorridoActual.setMetricasXRecorrido(r.getMetricasXRecorrido());
		/*for(MetricasXRecorrido mRe : recorridoActual.getMetricasXRecorrido()){
			System.out.println(mRe.getMetrica().getNombreMetrica() + " " + mRe.getValorMetrica());
			mRe.save();
		}*/
		recorridoActual.save();
	}
}
