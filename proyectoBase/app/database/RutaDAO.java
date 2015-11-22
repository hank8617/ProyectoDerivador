package database;

import java.sql.SQLException;
import java.util.List;

import models.Ruta;

public class RutaDAO {
	
	/**
	 * Agregar una ruta al repositorio 
	 * 
	 * @param rt La ruta que se debe agregar
	 */
	public void agregarRuta(Ruta rt) throws SQLException
	{
		rt.save();
	}
	
	public List<Ruta> listarRutas(){
		return Ruta.find.all();
	}
}