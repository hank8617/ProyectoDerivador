package database;

import java.util.List;

import models.Recorrido;
import models.User;
import models.UsuarioXRecorrido;

public class UsuariosXRecorridoDAO {

	public void agregarUsuarioXRecorrido(UsuarioXRecorrido uR){
		uR.save();
	}

	public void eliminarUsuarioXRecorrido(UsuarioXRecorrido uR){
		uR.delete();
	}
	
	public List<UsuarioXRecorrido> consultarUsuarioEnRecorridoPorRecorridoYUsuario(Recorrido r, User u){
		
		System.out.println(r.getIdRecorrido() + "   " + u.id);
		
		
		return UsuarioXRecorrido.find.where().eq("usuario_id", u.id)
				.eq("recorrido_id_recorrido", r.getIdRecorrido()).findList();
	}
}
