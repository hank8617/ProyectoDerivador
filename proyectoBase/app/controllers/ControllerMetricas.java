package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import controllers.ControllerRecorrido.FormularioConsultaRecorrido;
import models.Metrica;
import models.MetricasXRecorrido;
import models.Recorrido;
import models.User;
import database.MetricaDAO;
import database.MetricasXRecorridoDAO;
import database.RecorridoDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ControllerMetricas extends Controller{
	
	public static Result iniciarRecorridoWeb(Long idRecorridoSeleccionado){
		System.out.println(idRecorridoSeleccionado.longValue());
		RecorridoDAO recorridoDAO = new RecorridoDAO();
		Recorrido recorrido = recorridoDAO.consultarRecorridoPorId(idRecorridoSeleccionado);
		return ok(views.html.ejecucionRecorrido.render(recorrido,null, null));
				
	}	
	
	
	public static Result guardarMetricas(){
		
		RecorridoDAO recorridoDAO = new RecorridoDAO();
		MetricaDAO metricaDAO = new MetricaDAO();
		MetricasXRecorridoDAO metricasXRecorridoDAO = new MetricasXRecorridoDAO();
		
		DynamicForm requestData = Form.form().bindFromRequest();
		
		String txtRecorridoId = requestData.get("recorridoId");
		
        String txtDistanciaRealVal = requestData.get("txtDistanciaRealVal");
        String txtTiempoEstimadoVal = requestData.get("txtTiempoEstimadoVal"); 
        String txtDistanciaEstimadaVal = requestData.get("txtDistanciaEstimadaVal");
        String txtTiempoRealVal = requestData.get("txtTiempoRealVal");
        
        String txtClimaCondicionOrigenVal = requestData.get("txtClimaCondicionOrigenVal");
        String txtClimaCondicionDestinoVal = requestData.get("txtClimaCondicionDestinoVal");
        String txtClimaTemperaturaOrigenVal = requestData.get("txtClimaTemperaturaOrigenVal");
        String txtClimaTemperaturaDestinoVal = requestData.get("txtClimaTemperaturaDestinoVal");
        String txtClimaHumedadOrigenVal = requestData.get("txtClimaHumedadOrigenVal");
        String txtClimaHumedadDestinoVal = requestData.get("txtClimaHumedadDestinoVal");
        String txtClimaNubosidadOrigenVal = requestData.get("txtClimaNubosidadOrigenVal");
        String txtClimaNubosidadDestinoVal = requestData.get("txtClimaNubosidadDestinoVal");
        String txtClimaVientoOrigenVal = requestData.get("txtClimaVientoOrigenVal");
        String txtClimaVientoDestinoVal = requestData.get("txtClimaVientoDestinoVal");
        
        try{
        	Long recorridoPK = Long.parseLong(txtRecorridoId);
        	Recorrido recorrido = recorridoDAO.consultarRecorridoPorId(recorridoPK);
        	
        	List<MetricasXRecorrido> lstMetricas = new ArrayList<MetricasXRecorrido>();
        	
        	//Distancia Real
        	if(metricaDAO.consultarMetricaPorNombre("Distancia Real").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Distancia Real");
        		m.setUnidadMedida("Km");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoDistReal = new MetricasXRecorrido();
        	metricasRecorridoDistReal.setRecorrido(recorrido);
        	metricasRecorridoDistReal.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoDistReal.setMetrica(metricaDAO.consultarMetricaPorNombre("Distancia Real").get(0));
        	metricasRecorridoDistReal.setValorMetrica(Double.parseDouble(txtDistanciaRealVal.isEmpty()?"0.0":txtDistanciaRealVal));
        	metricasRecorridoDistReal.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoDistReal);
        	
        	//Tiempo Real
        	if(metricaDAO.consultarMetricaPorNombre("Tiempo Real").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Tiempo Real");
        		m.setUnidadMedida("min");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoTiemReal = new MetricasXRecorrido();
        	metricasRecorridoTiemReal.setRecorrido(recorrido);
        	metricasRecorridoTiemReal.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoTiemReal.setMetrica(metricaDAO.consultarMetricaPorNombre("Tiempo Real").get(0));
        	metricasRecorridoTiemReal.setValorMetrica(Double.parseDouble(txtTiempoRealVal.isEmpty()?"0.0":txtTiempoRealVal));
        	metricasRecorridoTiemReal.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoTiemReal);
        	
        	//Velocidad Media
        	if(metricaDAO.consultarMetricaPorNombre("Velocidad Media").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Velocidad Media");
        		m.setUnidadMedida("Km/h");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	double velocidad = 60*(Double.parseDouble(txtDistanciaRealVal.isEmpty()?"0.0":txtDistanciaRealVal)/Double.parseDouble(txtTiempoRealVal.isEmpty()?"1.0":txtTiempoRealVal));
        	
        	MetricasXRecorrido metricasRecorridoVeloMedi = new MetricasXRecorrido();
        	metricasRecorridoVeloMedi.setRecorrido(recorrido);
        	metricasRecorridoVeloMedi.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoVeloMedi.setMetrica(metricaDAO.consultarMetricaPorNombre("Velocidad Media").get(0));
        	metricasRecorridoVeloMedi.setValorMetrica(velocidad);
        	metricasRecorridoVeloMedi.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoVeloMedi);
        	
        	//Distancia Estimada
        	if(metricaDAO.consultarMetricaPorNombre("Distancia Estimada").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Distancia Estimada");
        		m.setUnidadMedida("Km");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoDistEsti = new MetricasXRecorrido();
        	metricasRecorridoDistEsti.setRecorrido(recorrido);
        	metricasRecorridoDistEsti.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoDistEsti.setMetrica(metricaDAO.consultarMetricaPorNombre("Distancia Estimada").get(0));
        	metricasRecorridoDistEsti.setValorMetrica(Double.parseDouble(txtDistanciaEstimadaVal));
        	metricasRecorridoDistEsti.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoDistEsti);
        	
        	//Tiempo Estimado
        	if(metricaDAO.consultarMetricaPorNombre("Tiempo Estimado").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Tiempo Estimado");
        		m.setUnidadMedida("min");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoTiemEsti = new MetricasXRecorrido();
        	metricasRecorridoTiemEsti.setRecorrido(recorrido);
        	metricasRecorridoTiemEsti.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoTiemEsti.setMetrica(metricaDAO.consultarMetricaPorNombre("Tiempo Estimado").get(0));
        	metricasRecorridoTiemEsti.setValorMetrica(Double.parseDouble(txtTiempoEstimadoVal));
        	metricasRecorridoTiemEsti.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoTiemEsti);
        	
        	//Clima Condición Origen
        	/*if(metricaDAO.consultarMetricaPorNombre("Clima Condición Origen").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Condición Origen");
        		m.setUnidadMedida("txt");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	metricasRecorrido = new MetricasXRecorrido();
        	metricasRecorrido.setRecorrido(recorrido);
        	metricasRecorrido.setUsuario(Application.getLocalUser(session()));
        	metricasRecorrido.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Condición Origen").get(0));
        	metricasRecorrido.setValorMetrica(Double.parseDouble(txtClimaCondicionOrigenVal));
        	metricasRecorrido.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorrido);*/
        	
        	//Clima Condición Destino
        	/*if(metricaDAO.consultarMetricaPorNombre("Clima Condición Destino").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Condición Destino");
        		m.setUnidadMedida("txt");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	metricasRecorrido = new MetricasXRecorrido();
        	metricasRecorrido.setRecorrido(recorrido);
        	metricasRecorrido.setUsuario(Application.getLocalUser(session()));
        	metricasRecorrido.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Condición Destino").get(0));
        	metricasRecorrido.setValorMetrica(Double.parseDouble(txtClimaCondicionDestinoVal));
        	metricasRecorrido.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorrido);*/
        	
        	//Clima Temperatura Origen
        	if(metricaDAO.consultarMetricaPorNombre("Clima Temperatura Origen").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Temperatura Origen");
        		m.setUnidadMedida("°C");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoTempOrig = new MetricasXRecorrido();
        	metricasRecorridoTempOrig.setRecorrido(recorrido);
        	metricasRecorridoTempOrig.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoTempOrig.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Temperatura Origen").get(0));
        	metricasRecorridoTempOrig.setValorMetrica(Double.parseDouble(txtClimaTemperaturaOrigenVal));
        	metricasRecorridoTempOrig.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoTempOrig);
        	
        	//Clima Temperatura Destino
        	if(metricaDAO.consultarMetricaPorNombre("Clima Temperatura Destino").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Temperatura Destino");
        		m.setUnidadMedida("°C");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoTempDest = new MetricasXRecorrido();
        	metricasRecorridoTempDest.setRecorrido(recorrido);
        	metricasRecorridoTempDest.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoTempDest.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Temperatura Destino").get(0));
        	metricasRecorridoTempDest.setValorMetrica(Double.parseDouble(txtClimaTemperaturaDestinoVal));
        	metricasRecorridoTempDest.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoTempDest);
        	
        	//Clima Humedad Origen
        	if(metricaDAO.consultarMetricaPorNombre("Clima Humedad Origen").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Humedad Origen");
        		m.setUnidadMedida("%");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoHumeOrig = new MetricasXRecorrido();
        	metricasRecorridoHumeOrig.setRecorrido(recorrido);
        	metricasRecorridoHumeOrig.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoHumeOrig.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Humedad Origen").get(0));
        	metricasRecorridoHumeOrig.setValorMetrica(Double.parseDouble(txtClimaHumedadOrigenVal));
        	metricasRecorridoHumeOrig.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoHumeOrig);
        	
        	//Clima Humedad Destino
        	if(metricaDAO.consultarMetricaPorNombre("Clima Humedad Destino").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Humedad Destino");
        		m.setUnidadMedida("%");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoHumeDest = new MetricasXRecorrido();
        	metricasRecorridoHumeDest.setRecorrido(recorrido);
        	metricasRecorridoHumeDest.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoHumeDest.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Humedad Destino").get(0));
        	metricasRecorridoHumeDest.setValorMetrica(Double.parseDouble(txtClimaHumedadDestinoVal));
        	metricasRecorridoHumeDest.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoHumeDest);
        	
        	//Clima Nubosidad Origen
        	if(metricaDAO.consultarMetricaPorNombre("Clima Nubosidad Origen").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Nubosidad Origen");
        		m.setUnidadMedida("%");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoNuboOrig = new MetricasXRecorrido();
        	metricasRecorridoNuboOrig.setRecorrido(recorrido);
        	metricasRecorridoNuboOrig.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoNuboOrig.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Nubosidad Origen").get(0));
        	metricasRecorridoNuboOrig.setValorMetrica(Double.parseDouble(txtClimaNubosidadOrigenVal));
        	metricasRecorridoNuboOrig.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoNuboOrig);
        	
        	//Clima Humedad Destino
        	if(metricaDAO.consultarMetricaPorNombre("Clima Nubosidad Destino").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Nubosidad Destino");
        		m.setUnidadMedida("%");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoNuboDest = new MetricasXRecorrido();
        	metricasRecorridoNuboDest.setRecorrido(recorrido);
        	metricasRecorridoNuboDest.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoNuboDest.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Nubosidad Destino").get(0));
        	metricasRecorridoNuboDest.setValorMetrica(Double.parseDouble(txtClimaNubosidadDestinoVal));
        	metricasRecorridoNuboDest.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoNuboDest);
        	
        	//Clima Viento Origen
        	if(metricaDAO.consultarMetricaPorNombre("Clima Viento Origen").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Viento Origen");
        		m.setUnidadMedida("m/s");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoVienOrig = new MetricasXRecorrido();
        	metricasRecorridoVienOrig.setRecorrido(recorrido);
        	metricasRecorridoVienOrig.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoVienOrig.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Viento Origen").get(0));
        	metricasRecorridoVienOrig.setValorMetrica(Double.parseDouble(txtClimaVientoOrigenVal));
        	metricasRecorridoVienOrig.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoVienOrig);
        	
        	//Clima Humedad Destino
        	if(metricaDAO.consultarMetricaPorNombre("Clima Viento Destino").size() <= 0){
        		Metrica m = new Metrica();
        		m.setNombreMetrica("Clima Viento Destino");
        		m.setUnidadMedida("m/s");
        		metricaDAO.agregarMetrica(m);
        	}
        	
        	MetricasXRecorrido metricasRecorridoVienDest = new MetricasXRecorrido();
        	metricasRecorridoVienDest.setRecorrido(recorrido);
        	metricasRecorridoVienDest.setUsuario(Application.getLocalUser(session()));
        	metricasRecorridoVienDest.setMetrica(metricaDAO.consultarMetricaPorNombre("Clima Viento Destino").get(0));
        	metricasRecorridoVienDest.setValorMetrica(Double.parseDouble(txtClimaVientoDestinoVal));
        	metricasRecorridoVienDest.setFecha(new Date(new java.util.Date().getTime()));
        	
        	lstMetricas.add(metricasRecorridoVienDest);
        	
        	recorrido.setMetricasXRecorrido(lstMetricas);
        	
        	recorridoDAO.actualizarRecorridoConMetricas(recorrido);
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        		
		List<Recorrido> lstRecorridos = recorridoDAO.listarRecorridos();
		response().setContentType("text/html; charset=utf-8");
		flash("success", "Se ha almacenado las métricas correctamente.");
		String mensaje ="Acabo de recorrer  22 km  ";
		System.out.println(mensaje);
		String url = "http://i844.photobucket.com/albums/ab7/MARTIN3280/af0821f4-f925-4b64-87e8-3a0f6454b19d_zpsbgsh2pue.jpg";
		return templateRecorridoWeb(Form.form(FormularioConsultaRecorrido.class), lstRecorridos, mensaje, url);
	}


	private static Result templateRecorridoWeb(Form<FormularioConsultaRecorrido> form, List<Recorrido> lstRecorridos,
			String mensaje, String url) {

		//return ok(views.html.recorridosConsulta.render(form, lstRecorridos, null , null));
		//return ok(views.html.recorridosConsulta.render(form, lstRecorridos, null , views.html.publicadorfacebook.render("publicador", mensaje, url)));
		return ok(views.html.recorridosConsulta.render(form, lstRecorridos, null , null));
	}
	
	

	
	
		
	//return templateRecorridoWeb(recorrido, mensaje, url);
	/*
	private static Result templateRecorridoWeb(Recorrido recorrido, String mensaje, String url) {
		
		System.out.println(mensaje);
		//return ok(views.html.ejecucionRecorrido.render(recorrido,null, null));
		//return ok(views.html.ejecucionRecorrido.render(recorrido,views.html.publicadorfacebook.render("publicador"), null));
		return ok(views.html.ejecucionRecorrido.render(recorrido,null , views.html.publicadorfacebook.render("publicador", mensaje, url)));
*/		 
		
	}
