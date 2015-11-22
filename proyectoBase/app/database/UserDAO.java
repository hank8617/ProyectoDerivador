package database;

import java.util.List;

import models.Metrica;
import models.Reto;
import models.SecurityRole;
import models.User;

public class UserDAO {
	
	public List<User> listarUsuarios(){
		
		SecurityRole userRole = SecurityRole.findByRoleName(controllers.Application.USER_ROLE);
		
		return User.find.where().eq("roles.id", userRole.id).findList();
	}
	
	public User consultarUsuarioPorId(Long id){
		return User.find.byId(id);
	}
	
	public void actualizarUsuario(User user){
		User userActual = consultarUsuarioPorId(user.id);
		
		if(userActual!=null){
			userActual = user;
			userActual.update();
		}
	}

}