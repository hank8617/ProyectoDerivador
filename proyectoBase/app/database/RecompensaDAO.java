package database;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;

import models.EstadoRecompensa;
import models.EstadoReto;
import models.Recompensa;
import models.RecompensaUsuario;
import models.Reto;
import models.RetoUsuario;
import models.User;

public class RecompensaDAO {
	
	public List<Recompensa> consultarRecompensasActivas(){
		return Recompensa.find.where().eq("estado", EstadoRecompensa.ACTIVA.getEstado()).findList();
	}	
	
	public List<Recompensa> consultarRecompensasDisponiblesUsuario(User user){
		List<RecompensaUsuario> recompensasUsuario = RecompensaUsuario.find.where().eq("usuario", user).findList();
		List<Long> idsRecoms = new ArrayList<>();
		for(RecompensaUsuario recomUsuario : recompensasUsuario){
			idsRecoms.add(recomUsuario.recompensa.id);
		}
		
		Query<?> q = Ebean.find(Reto.class);
		Expression exp = q.getExpressionFactory().in("id", idsRecoms);
		List<Recompensa> recompensas = Recompensa.find.where().not(exp).eq("estado",EstadoRecompensa.ACTIVA.getEstado()).findList();
		
		return recompensas;
		
	}
	
	public List<Recompensa> consultarRecompensas(){
		return Recompensa.find.all();
	}
	
	public void agregarRecompensa(Recompensa recompensa){
		if(recompensa.id==null){
			recompensa.id = Recompensa.find.nextId();
			recompensa.save();
		}

	}
	
	public void actualizarRecompensa(Recompensa recompensa){
		Recompensa recompensaActual = consultarRecompensaPorId(recompensa.id);
		
		if(recompensaActual!=null){
			recompensaActual = recompensa;		
			recompensaActual.update();
		}
	}
	
	public void eliminarRecompensa(Recompensa recompensa){
		Recompensa recompensaBorrar = consultarRecompensaPorId(recompensa.id);
		if(recompensaBorrar!=null){
			recompensaBorrar.delete();
		}		
	}
	
	public Recompensa consultarRecompensaPorId(Long id){
		return Recompensa.find.byId(id);
	}	

}
