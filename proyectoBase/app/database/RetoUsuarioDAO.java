package database;

import java.util.List;

import models.Reto;
import models.RetoUsuario;
import models.User;

public class RetoUsuarioDAO {
	
	public List<RetoUsuario> consultarRetosUsuario(User usuario){
		return RetoUsuario.find.where().eq("usuario", usuario).findList();
	}
	
	public void agregarRetoUsuario(RetoUsuario retoUsuario){
		retoUsuario.save();
	}
	
	public void actualizarRetoUsuario(RetoUsuario retoUsuario){
		RetoUsuario retoUsuarioActual = consultarRetoUsuarioPorId(retoUsuario.id);
		
		if(retoUsuarioActual!=null){
			retoUsuarioActual = retoUsuario;
			retoUsuarioActual.save();
		}

	}
	
	public void eliminarRetoUsuario(RetoUsuario retoUsuario){
		RetoUsuario retoUsuarioBorrar = consultarRetoUsuarioPorId(retoUsuario.id);
		if(retoUsuarioBorrar!=null){
			retoUsuarioBorrar.delete();			
		}
	}
	
	public RetoUsuario consultarRetoUsuarioPorId(Long id){
		return RetoUsuario.find.byId(id);
	}
	
	public RetoUsuario consultarRetoUsuarioPorUsuarioReto(User usuario, Reto reto){
		return RetoUsuario.find.where().eq("usuario", usuario).eq("reto", reto).findUnique();
	}

}
