package database;

import java.util.List;

import models.Recompensa;
import models.RecompensaUsuario;
import models.User;

public class RecompensaUsuarioDAO {
	
	public List<RecompensaUsuario> consultarRecompensasUsuario(User usuario){
		return RecompensaUsuario.find.where().eq("usuario", usuario).findList();
	}	
	
	public boolean guardarRecompensaUsuario(RecompensaUsuario recompensaUsuario){
		RecompensaUsuario recompensaUsuarioActual = consultarRecompensaUsuarioPorUsuarioRecompensa(recompensaUsuario);
		boolean retorno = false;
		if(recompensaUsuarioActual==null){
			recompensaUsuario.save();
			retorno =  true;
		}else{
			retorno = false;
		}
		return retorno;
	}
	
	public void actualizarRecompensaUsuario(RecompensaUsuario recompensaUsuario){
		RecompensaUsuario recompensaUsuarioActual = consultarRecompensaUsuarioPorId(recompensaUsuario.id);
		
		if(recompensaUsuarioActual!=null){
			recompensaUsuarioActual = recompensaUsuario;		
			recompensaUsuarioActual.update();
		}
	}
	
	public void eliminarRecompensaUsuario(RecompensaUsuario recompensaUsuario){
		RecompensaUsuario recompensaUsuarioBorrar = consultarRecompensaUsuarioPorId(recompensaUsuario.id);
		recompensaUsuarioBorrar.delete();
	}
	
	public RecompensaUsuario consultarRecompensaUsuarioPorId(Long id){
		return RecompensaUsuario.find.byId(id);
	}	

	public RecompensaUsuario consultarRecompensaUsuarioPorUsuarioRecompensa(RecompensaUsuario recompensaUsuario){
		return RecompensaUsuario.find.where().eq("recompensa", recompensaUsuario.recompensa)
				.eq("usuario", recompensaUsuario.usuario).findUnique();
	}
}
