package controllers;

import play.*;
import play.api.libs.json.Json;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import generated.Alt;
import generated.And;
import generated.Constraints;
import generated.Feature;
import generated.Imp;
import generated.Not;
import generated.Or;
import generated.FeatureModel;
import generated.ObjectFactory;
import generated.Rule;
import generated.Struct;

import javax.swing.text.html.HTML;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;


public class ConfiguradorController extends Controller {

	public static Integer pantallaActual;
    public static List<Caracteristica> listCaracteristicas;
    public static List<Restriccion> listRestricciones;
    public static Caracteristica caracteristica;
    
    public static Result verResultados() {
    	
    	//return ok("ok");
    	Form<ResultadosSeleccion> form = Form.form(ResultadosSeleccion.class).bindFromRequest();

		if(form.hasErrors()) {
			flash("error", "Se encontraron errores al consultar el recorrido.");
			return badRequest(indexConfigurador.render(Form.form(ResultadosSeleccion.class), null, null));

		} else {
				
			ResultadosSeleccion resumenSeleccion = new ResultadosSeleccion();
			
			ResultadosSeleccion resultadosSeleccion = form.get();
			String[] arrTipoBici = resultadosSeleccion.sel_tipo_bicicleta.split("/");
			String selTipoBicicleta = arrTipoBici[0] + ":";
			for (int i = 1; i < arrTipoBici.length; i++) {
	        	selTipoBicicleta +=  " - " + arrTipoBici[i];
	        }	
			resumenSeleccion.sel_tipo_bicicleta = selTipoBicicleta;
			resumenSeleccion.sel_rueda = "Tamaño rueda: " + resultadosSeleccion.sel_rueda.split("/")[1] + " pulgadas ";
			resumenSeleccion.sel_material = "Material: " + resultadosSeleccion.sel_material.split("/")[1];
			resumenSeleccion.sel_color = "Color: " + resultadosSeleccion.sel_color.split("/")[1];
	        
			String[] arrAccesorios = resultadosSeleccion.sel_accesorios_bicicleta.split("/");
			String selAccesorios = arrAccesorios[0] + ":";
			for (int i = 1; i < arrAccesorios.length; i++) {
				selAccesorios +=  " - " + arrAccesorios[i];
	        }
			resumenSeleccion.sel_accesorios_bicicleta = selAccesorios;
			
			return ok(views.html.resumenConfigurador.render(resumenSeleccion));
		}
    }
    
    public static Result index(){
        
    	try {
    		
    		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
    	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	    
    	    File file = Play.application().getFile("conf/model.xml");
    	    FeatureModel featureModel = (FeatureModel)unmarshaller.unmarshal(file);
    	    Struct struct = featureModel.getStruct();
    	    And and = struct.getAnd();
    	        
    	    caracteristica=new Caracteristica();
    	    caracteristica.setNombre(and.getName());
    	    listCaracteristicas = new ArrayList<Caracteristica>();
    	    listRestricciones = new ArrayList<Restriccion>();
    	    
    	    agregarListaCaracteristicas(and.getAndOrAltOrOr(), caracteristica);
    	    String cadRestricciones = agregarListaRestricciones(featureModel.getConstraints());
        	    
    	    return ok(indexConfigurador.render(Form.form(ResultadosSeleccion.class), caracteristica, cadRestricciones));
    	    
		} catch (Exception e) {
			e.printStackTrace();
			return ok("error carger XML");
		}
    	
    }

    public static void agregarImagenes(Caracteristica caracteristica, String descripcion)
    {
    	if(descripcion.contains("tieneImagen")){

 			String sDirectorio = "public/images/" + descripcion.split("-")[0].trim();
 			File f = new File(sDirectorio);
 			if (f.exists()){  
 				File[] ficheros = f.listFiles();
 				for (int x = 0; x < ficheros.length; x++){
 				  caracteristica.getImagenes().add("/assets/images/" + descripcion.split("-")[0].trim() + "/" + ficheros[x].getName());
 				  //System.out.println("/assets/images/" + descripcion.split("-")[0].trim() + "/" + ficheros[x].getName());
 				}
 			}
 		}
 		caracteristica.titulo = descripcion.split("-")[1].trim();
    }
    
	public static String agregarListaRestricciones(Constraints constraints) {

		List<Rule> listRules = constraints.getRule();
		
		for (Rule rule : listRules) {
			List<Imp> listImp = rule.getImp();
			for (int i = 0; i < listImp.size(); i++) {
				Restriccion restriccion = new Restriccion();
				List<Object> listObj = listImp.get(i).getVarOrNot();
				for (Object object : listObj) {
					if(object instanceof String){
						restriccion.aplicaA = object.toString();
						listRestricciones.add(restriccion);
						//System.out.println("Aplica a --> " + object.toString());
					}
					else if(object instanceof Not){
		 	    		Not caracteristicaNot = (Not)object;
		 	    		List<Object> listObjectNot = caracteristicaNot.getVarOrNot();
		 	    		if(listObjectNot.size() > 0)
		 	    			agregarNot(restriccion, listObjectNot);
					}
				}
				
			}
		}
		return new Gson().toJson(listRestricciones);
	}
    
	public static void agregarNot(Restriccion restriccion, List<Object> listObjectNot)
	{
		for (Object object : listObjectNot) {
			if(object instanceof String){
				restriccion.getNoImplica().add(object.toString());
				//System.out.println(object.toString());
			}
			else
			{
				if(object instanceof Imp){
					agregarNot(restriccion, ((Imp) object).getVarOrNot());
				}
			}
		}
	}
	
    public static void agregarListaCaracteristicas(List<Object> lstObj, Caracteristica padre)
	{
    	List<Object> lstObjChild = new ArrayList<Object>();
    	Caracteristica caracteristica;
 	    for (Object object : lstObj) {
 	    	
 	    	caracteristica = new Caracteristica();
 	    	caracteristica.setClase(object.getClass().getSimpleName());
 	    	
 	    	if(object instanceof Alt){
 	    		Alt caracteristicaAlt = (Alt)object;
 	    		caracteristica.setNombre(caracteristicaAlt.getName());
 	    		caracteristica.setOpcional(caracteristicaAlt.isMandatory());
 	    		padre.getCaracteristicas().add(caracteristica);
 	    		String descripcion = caracteristicaAlt.getDescription();
 	    		agregarImagenes(caracteristica, descripcion);
 	    		lstObjChild =  caracteristicaAlt.getAndOrAltOrOr();
 	    		if(lstObjChild.size() > 0)
 	    			agregarListaCaracteristicas(lstObjChild, caracteristica);
 	    	}
 	    	
 	    	if(object instanceof Or){
 	    		Or caracteristicaOr = (Or)object;
 	    		caracteristica.setNombre(caracteristicaOr.getName());
 	    		caracteristica.setOpcional(caracteristicaOr.isMandatory());
 	    		padre.getCaracteristicas().add(caracteristica);
 	    		String descripcion = caracteristicaOr.getDescription();
 	    		agregarImagenes(caracteristica, descripcion);
 	    		lstObjChild =  caracteristicaOr.getAndOrAltOrOr();
 	    		if(lstObjChild.size() > 0)
 	    			agregarListaCaracteristicas(lstObjChild, caracteristica);
 	    	}
 	    	
 	    	if(object instanceof Feature){
 	    		Feature caracteristicaFeature = (Feature)object;
 	    		caracteristica.setNombre(caracteristicaFeature.getName());
 	    		caracteristica.setOpcional(caracteristicaFeature.isMandatory());
 	    		padre.getCaracteristicas().add(caracteristica);
 	    		String descripcion = caracteristicaFeature.getDescription();
 	    		if(descripcion != null)
 	    			agregarImagenes(caracteristica, descripcion);
 	    	}
 	    }
	}
	
	public static class Restriccion{
		public String aplicaA;
		public List<String> noImplica;

		public Restriccion()
		{}
		
		public String getAplicaA() {
			return aplicaA;
		}

		public void setAplicaA(String aplicaA) {
			this.aplicaA = aplicaA;
		}

		public List<String> getNoImplica(){
			if(this.noImplica==null){
				this.noImplica=new ArrayList<String>();
			}
			return this.noImplica;
		}

		public void setNoImplica(List<String> noImplica) {
			this.noImplica = noImplica;
		}
	}
	
	public static class Caracteristica
	{
		public String nombre;
		public Boolean opcional;
		public String nombrePadre;
		public String clase;
		public List<String> imagenes;
		public List<Caracteristica> caracteristicas;
		public String titulo;
		
		public Caracteristica()
		{}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public Boolean getOpcional() {
			return opcional;
		}

		public void setOpcional(Boolean opcional) {
			this.opcional = opcional;
		}

		public String getNombrePadre() {
			return nombrePadre;
		}

		public void setNombrePadre(String nombrePadre) {
			this.nombrePadre = nombrePadre;
		}

		public String getClase() {
			return clase;
		}

		public void setClase(String clase) {
			this.clase = clase;
		}
		
		public List<Caracteristica> getCaracteristicas(){
			if(this.caracteristicas==null){
				this.caracteristicas=new ArrayList<Caracteristica>();
			}
			return this.caracteristicas;
		}
		
		public void setCaracteristicas(List<Caracteristica> caracteristicas){
			this.caracteristicas=caracteristicas;
		}
		
		public List<String> getImagenes(){
			if(this.imagenes==null){
				this.imagenes=new ArrayList<String>();
			}
			return this.imagenes;
		}
		
		public void setImagenes(List<String> imagenes){
			this.imagenes=imagenes;
		}
		
		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	}

	public static class ResultadosSeleccion
	{
		public String sel_tipo_bicicleta;
		public String sel_rueda;
		public String sel_material;
		public String sel_color;
		public String sel_accesorios_bicicleta;
	}
}
