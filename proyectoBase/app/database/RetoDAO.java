package database;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import models.EstadoReto;
import models.FuncionReto;
import models.OperadorCondicionReto;
import models.Reto;
import models.RetoUsuario;
import models.User;

public class RetoDAO {
	
	public List<Reto> consultarRetosActivos(){
		return Reto.find.where().eq("estado", EstadoReto.ACTIVO.getEstado()).findList();
	}
	
	public List<Reto> consultarRetos(){
		return Reto.find.all();
	}
	
	public void agregarReto(Reto reto){
		reto.id = Reto.find.nextId();
		System.out.println("Id calculado para reto: "+reto.id);
		reto.save();
	}
	
	public void actualizarReto(Reto reto){
		Reto retoActual = consultarRetoPorId(reto.id);
		
		if(retoActual!=null){
			retoActual = reto;
			retoActual.update();
		}

	}
	
	/**
	 * Devuelve los retos activos que no ha cumplido el usuario
	 * @param usuario
	 * @return
	 */
	public List<Reto> consultarRetosDisponiblesUsuario(User usuario){
		
		List<RetoUsuario> retosUsuario = RetoUsuario.find.where().eq("usuario", usuario).findList();
		List<Long> idsRetos = new ArrayList<>();
		for(RetoUsuario retoUsuario : retosUsuario){
			idsRetos.add(retoUsuario.reto.id);
		}
		
		Query<?> q = Ebean.find(Reto.class);
		Expression exp = q.getExpressionFactory().in("id", idsRetos);
		List<Reto> retos = Reto.find.where().not(exp).eq("estado",EstadoReto.ACTIVO.getEstado()).findList();
		
		return retos;
		
	}
	
	/**
	 * Devuelve verdadero si el usuario cumple el reto 
	 * 
	 * @param reto
	 * @param usuario
	 * @return
	 */
	public boolean cumpleReto(Reto reto, User usuario){
		Long valorCalculadoMetricas = calcularValorMetricas(reto, usuario);
		if(valorCalculadoMetricas==null || valorCalculadoMetricas==-1){
			return false;
		}else{
			boolean cumpleReto = compararValoresMetricas(valorCalculadoMetricas, reto);
			if(cumpleReto){
				System.out.println("El usuario "+usuario.name+" cumplió el reto "+reto.nombre);
			}
			return cumpleReto;
		}
	}
	
	/**
	 * Calcula el valor de ejecutar la función de agregación (suma, contar, etc) que viene definida en el reto
	 * para las metricas que cumplen con las condiciones definidas en el reto y que han sido realizadas por el usuario. 
	 *  
	 * @param reto
	 * @param usuario
	 * @return
	 */
	public Long calcularValorMetricas(Reto reto, User usuario){
		/*MetricasXRecorrido.find.
			where().eq("usuario", usuario)
					.eq("metrica", reto.metrica)
					.ge("fecha",reto.fechaIni)
					.le("fecha", reto.fechaFin)*/
		FuncionReto funcionReto = consultarFuncionReto(reto.funcion);
		if(funcionReto!=null){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT "+funcionReto.getFuncion()+"(valor_metrica) calculado_metricas FROM metricas_x_recorridos WHERE usuario_id=:idUsuario ");
			sql.append("and metrica_id_metrica=:idMetrica and fecha>=:fechaIni and fecha<=:fechaFin");
			
			SqlQuery sqlQuery = Ebean.createSqlQuery(sql.toString());
			sqlQuery.setParameter("idUsuario", usuario.id);
			sqlQuery.setParameter("idMetrica", reto.metrica.getIdMetrica());
			sqlQuery.setParameter("fechaIni", reto.fechaIni);
			sqlQuery.setParameter("fechaFin", reto.fechaFin);

			// execute the query returning a List of MapBean objects
			List<SqlRow> list = sqlQuery.findList();
			if(!list.isEmpty()){
				SqlRow row = list.get(0);
				Long resultado = row.getLong("calculado_metricas");
				System.out.println("---------------------- VALOR CALCULADO METRICA "+reto.metrica.getNombreMetrica()+"-----------------------"); 
				if(resultado!=null){
					System.out.println("Resultado: "+resultado);
					return resultado;
				}else{
					return new Long(-1);
				}
			}else{
				return new Long(-1);
			}
		}
		
		return new Long(-1);
		
		
	}
	
	/**
	 * Compara el valor calculado para la metrica con el limite que se encuentra en el reto a traves del operador definido en el reto
	 * Devuelve verdadero si cumple la condición.
	 */
	public boolean compararValoresMetricas(Long valorCalculadoMetricas,Reto reto){
		boolean resultado = false;
		if(OperadorCondicionReto.DIFERENTE.getOperador().equals(reto.operador)){
			resultado = (valorCalculadoMetricas != Long.valueOf(reto.valorCondicion)) ? true : false; 
		}else if(OperadorCondicionReto.MAYOR.getOperador().equals(reto.operador)){
			resultado = (valorCalculadoMetricas > Long.valueOf(reto.valorCondicion)) ? true : false;
		}else if(OperadorCondicionReto.MAYOR_O_IGUAL.getOperador().equals(reto.operador)){
			resultado = (valorCalculadoMetricas >= Long.valueOf(reto.valorCondicion)) ? true : false;
		}else if(OperadorCondicionReto.MENOR.getOperador().equals(reto.operador)){
			resultado = (valorCalculadoMetricas < Long.valueOf(reto.valorCondicion)) ? true : false;
		}else if(OperadorCondicionReto.MENOR_O_IGUAL.getOperador().equals(reto.operador)){
			resultado = (valorCalculadoMetricas <= Long.valueOf(reto.valorCondicion)) ? true : false;
		}else{
			//Si es igual
			resultado = (valorCalculadoMetricas == Long.valueOf(reto.valorCondicion)) ? true : false;
		}
		return resultado;
		
	}
	
	public FuncionReto consultarFuncionReto(String nombre){
		for(FuncionReto funcion: FuncionReto.values()){
			if(funcion.name().equals(nombre)){
				return funcion;
			}
		}
		return null;
	}
	
	public void eliminarReto(Reto reto){
		Reto retoBorrar = consultarRetoPorId(reto.id);
		if(retoBorrar!=null){
			retoBorrar.delete();			
		}
	}
	
	public Reto consultarRetoPorId(Long id){
		return Reto.find.byId(id);
	}	

}
